import $exec.plugins
import io.github.davidgregory084.TpolecatModule
import mill._
import mill.scalalib.ScalaModule

object project extends ScalaModule with TpolecatModule {
  override def scalaVersion = "2.13.8"
}

def verify() = T.command {
  assert(project.scalacOptions() == Seq(
    "-encoding",
    "utf8",
    "-Xsource:3",
    "-explaintypes",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xcheckinit",
    "-Xfatal-warnings",
    "-Xlint:adapted-args",
    "-Xlint:constant",
    "-Xlint:delayedinit-select",
    "-Xlint:deprecation",
    "-Xlint:doc-detached",
    "-Xlint:inaccessible",
    "-Xlint:infer-any",
    "-Xlint:missing-interpolator",
    "-Xlint:nullary-unit",
    "-Xlint:option-implicit",
    "-Xlint:package-object-classes",
    "-Xlint:poly-implicit-overload",
    "-Xlint:private-shadow",
    "-Xlint:stars-align",
    "-Xlint:type-parameter-shadow",
    "-Wunused:nowarn",
    "-Wdead-code",
    "-Wextra-implicit",
    "-Wnumeric-widen",
    "-Xlint:implicit-recursion",
    "-Wunused:implicits",
    "-Wunused:explicits",
    "-Wunused:imports",
    "-Wunused:locals",
    "-Wunused:params",
    "-Wunused:patvars",
    "-Wunused:privates",
    "-Wvalue-discard",
    "-Vimplicits",
    "-Vtype-diffs"
  ), s"scalacOptions did not match, got instead: ${project.scalacOptions()}")
}
