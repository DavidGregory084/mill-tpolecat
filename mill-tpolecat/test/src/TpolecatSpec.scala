
package io.github.davidgregory084

import ammonite.ops._
import coursier._
import coursier.util._
import java.nio.charset.StandardCharsets
import java.io.File
import java.nio.file.{Path, Files}
import org.scalatest._
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global

class TpolecatModuleSpec extends FlatSpec with Matchers {
  def withTmpFile[A](test: Path => A) = {
    val file = Files.createTempFile("hello", ".scala")

    Files.write(
      file,
      List("""object Hello { def main(args: Array[String]): Unit = println("hello") }""").asJava,
      StandardCharsets.UTF_8
    )

    try test(file)
    finally {
      Files.delete(file)
      ()
    }
  }

  def fetchCompiler(scalaVersion: String): Either[Seq[FileError], Seq[File]] = {
    val start = Resolution(Seq(Dependency(Module(org"org.scala-lang", name"scala-compiler"), scalaVersion)))

    val repositories = Seq(
      coursier.cache.LocalRepositories.ivy2Local,
      MavenRepository("https://repo1.maven.org/maven2")
    )

    val fetch = ResolutionProcess.fetch(repositories, Cache.fetch[Task]())
    val resolution = start.process.run(fetch).unsafeRun()

    val resolved = Gather[Task].gather(
      resolution.artifacts().map(Cache.file[Task](_).run)
    ).unsafeRun()

    resolved.foldLeft(Right(Seq.empty): Either[Seq[FileError], Seq[File]]) {
      case (Right(files), Right(file)) => Right(files :+ file)
      case (Left(errors), Left(error)) => Left(errors :+ error)
      case (Right(files), Left(error)) => Left(Seq(error))
      case (left @ Left(errors), Right(file)) => left
    }
  }

  def compileHelloWorld(scalaVersion: String) = withTmpFile { sourceFile =>
    val fetchJars = fetchCompiler(scalaVersion)
    val jars = fetchJars.getOrElse(fail(fetchJars.left.get.mkString(System.lineSeparator)))
    val cp = jars.mkString(File.pathSeparator)
    val wd = ammonite.ops.Path(sourceFile.getParent)
    val mainClass = "scala.tools.nsc.Main"
    val sourceFileName = sourceFile.getFileName.toString
    val scalacOptions = List("-bootclasspath", cp) ++ Tpolecat.scalacOptionsFor(scalaVersion)
    val args = scalacOptions :+ sourceFileName
    val doCompile = %%('java, "-cp", cp, mainClass, args)(wd)
    if (doCompile.exitCode != 0) fail(doCompile.err.string)
    val classFile = wd / "Hello.class"
    assert(exists! classFile, "Class file did not exist after compilation")
    rm! classFile
  }

  "Tpolecat" should "select compile options that are supported at 2.10.7" in compileHelloWorld("2.10.7")

  it should "select compile options that are supported at 2.11.12" in compileHelloWorld("2.11.12")

  it should "select compile options that are supported at 2.12.15" in compileHelloWorld("2.12.15")

  it should "select compile options that are supported at 2.13.8" in compileHelloWorld("2.13.8")

  it should "select compile options that are supported at 3.0.2" in compileHelloWorld("3.0.2")

  it should "select compile options that are supported at 3.1.3" in compileHelloWorld("3.1.3")
}
