import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest_mill0.9:0.4.0`
import de.tobiasroeser.mill.integrationtest._
import mill._
import mill.scalalib._
import publish._

object tpolecat extends ScalaModule with PublishModule {
  def scalaVersion = "2.13.4"
  def artifactName = "mill-tpolecat"

  def publishVersion = "0.2.0"
  def pomSettings = PomSettings(
    description = "scalac options for the enlightened",
    organization = "io.github.davidgregory084",
    url = "https://github.com/DavidGregory084/mill-tpolecat",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("DavidGregory084", "mill-tpolecat"),
    developers = Seq(Developer("DavidGregory084", "David Gregory", "https://github.com/DavidGregory084"))
  )

  lazy val millVersion = "0.9.3"
  def compileIvyDeps = Agg(ivy"com.lihaoyi::mill-scalalib:$millVersion")
}

object itest extends MillIntegrationTestModule {
  def millTestVersion  = tpolecat.millVersion
  def pluginsUnderTest = Seq(tpolecat)
}
