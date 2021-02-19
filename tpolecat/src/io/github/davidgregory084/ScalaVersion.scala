package io.github.davidgregory084

import mill.scalalib.api.Util._

case class ScalaVersion(maj: Int, min: Int, patch: Int)
object ScalaVersion {
  def apply(scalaVersion: String): ScalaVersion = scalaVersion match {
    case ReleaseVersion(major, minor, patch) => ScalaVersion(major.toInt, minor.toInt, patch.toInt)
    case MinorSnapshotVersion(major, minor, patch) => ScalaVersion(major.toInt, minor.toInt, patch.toInt)
    case Scala3Version(minor, patch, _) => ScalaVersion(3, minor.toInt, patch.toInt)
    case DottyVersion("0", minor, patch) => ScalaVersion(3, minor.toInt, patch.toInt)
  }

  val v211 = ScalaVersion(2, 11, 0)
  val v212 = ScalaVersion(2, 12, 0)
  val v213 = ScalaVersion(2, 13, 0)
  val v300 = ScalaVersion(3, 0, 0)

  implicit lazy val ordering: Ordering[ScalaVersion] = (x: ScalaVersion, y: ScalaVersion) => {
    if (x.maj > y.maj || (x.maj == y.maj && x.min > y.min) || (x.maj == y.maj && x.min == y.min && x.patch > y.patch)) 1
    else if (x.maj < y.maj || (x.maj == y.maj && x.min < y.min) || (x.maj == y.maj && x.min == y.min && x.patch < y.patch)) -1
    else 0
  }
}
