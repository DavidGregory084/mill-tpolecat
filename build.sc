import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest_mill0.9:0.4.1-30-f29f55`
import de.tobiasroeser.mill.integrationtest._
import mill._
import mill.scalalib._
import mill.scalalib.api.Util.scalaNativeBinaryVersion
import publish._

val millVersions                           = Seq("0.10.0", "0.9.12")
def millBinaryVersion(millVersion: String) = scalaNativeBinaryVersion(millVersion)

object `mill-tpolecat` extends Cross[MillTpolecatCross](millVersions: _*)
class MillTpolecatCross(millVersion: String) extends ScalaModule with PublishModule {
  override def millSourcePath = super.millSourcePath / os.up
  override def artifactName   = s"${super.artifactName()}_mill${millBinaryVersion(millVersion)}"
  override def scalaVersion   = "2.13.7"

  def compileIvyDeps = Agg(ivy"com.lihaoyi::mill-scalalib:$millVersion")

  def publishVersion = "0.2.0"
  def pomSettings = PomSettings(
    description = "scalac options for the enlightened",
    organization = "io.github.davidgregory084",
    url = "https://github.com/DavidGregory084/mill-tpolecat",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("DavidGregory084", "mill-tpolecat"),
    developers = Seq(Developer("DavidGregory084", "David Gregory", "https://github.com/DavidGregory084"))
  )
}

object itest extends Cross[ITestCross](millVersions: _*)
class ITestCross(millVersion: String) extends MillIntegrationTestModule {
  override def millSourcePath   = super.millSourcePath / os.up
  override def millTestVersion  = millVersion
  override def pluginsUnderTest = Seq(`mill-tpolecat`(millVersion))
}
