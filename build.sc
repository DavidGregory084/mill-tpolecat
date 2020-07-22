import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest:0.3.3`
import de.tobiasroeser.mill.integrationtest._
import mill._
import mill.scalalib._
import publish._

object tpolecat extends Cross[TpolecatModule](crossScalaVersions: _*)
class TpolecatModule(val crossScalaVersion: String) extends CrossScalaModule with PublishModule {
  def artifactName = T { "mill-tpolecat" }

  def publishVersion = "0.1.3"

  def pomSettings = PomSettings(
    description = "scalac options for the enlightened",
    organization = "io.github.davidgregory084",
    url = "https://github.com/DavidGregory084/mill-tpolecat",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("DavidGregory084", "mill-tpolecat"),
    developers = Seq(Developer("DavidGregory084", "David Gregory", "https://github.com/DavidGregory084"))
  )

  lazy val millVersion = millVersionFor(crossScalaVersion)
  def compileIvyDeps = Agg(ivy"""com.lihaoyi::mill-scalalib:$millVersion""")
}

object itest extends Cross[IntegrationTestModule](crossScalaVersions: _*)
class IntegrationTestModule(val crossScalaVersion: String) extends MillIntegrationTestModule {
  override def millSourcePath = super.millSourcePath / ammonite.ops.up

  def millTestVersion  = millVersionFor(crossScalaVersion)
  def pluginsUnderTest = Seq(tpolecat(crossScalaVersion))
}

lazy val crossScalaVersions = Seq("2.13.3", "2.12.12")
def millVersionFor(scalaVersion: String) = if (scalaVersion.startsWith("2.13")) "0.8.0" else "0.6.3"
