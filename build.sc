import mill._
import mill.scalalib._
import publish._
import ammonite.ops._

object tpolecat extends ScalaModule with PublishModule {
  def artifactName = T { "mill-tpolecat" }

  def publishVersion = "0.1.1"

  def scalaVersion = "2.12.8"

  def pomSettings = PomSettings(
    description = artifactName(),
    organization = "io.github.davidgregory084",
    url = "https://github.com/DavidGregory084/mill-tpolecat",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("DavidGregory084", "mill-tpolecat"),
    developers = Seq(Developer("DavidGregory084", "David Gregory", "https://github.com/DavidGregory084"))
  )

  def compileIvyDeps = Agg(ivy"""com.lihaoyi::mill-scalalib:${sys.props("MILL_VERSION")}""")

   object test extends Tests {
    def ivyDeps = Agg(
      ivy"com.lihaoyi::ammonite-ops:1.6.3",
      ivy"io.get-coursier::coursier:1.1.0-M11",
      ivy"org.scalatest::scalatest:3.0.5"
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
   }
}
