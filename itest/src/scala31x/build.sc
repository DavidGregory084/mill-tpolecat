import $exec.plugins
import io.github.davidgregory084.TpolecatModule
import mill._
import mill.scalalib.ScalaModule

object project extends ScalaModule with TpolecatModule {
  override def scalaVersion = "3.1.3"
}

def verify() = T.command {
  assert(project.scalacOptions() == Seq(
    "-encoding",
    "utf8",
    "-deprecation",
    "-migration",
    "-explain-types",
    "-explain",
    "-feature",
    "-language:existentials,experimental.macros,higherKinds,implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Ykind-projector"
  ), s"scalacOptions did not match, got instead: ${project.scalacOptions()}")
}
