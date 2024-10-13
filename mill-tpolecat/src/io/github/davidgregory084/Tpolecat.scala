package io.github.davidgregory084

import org.typelevel.scalacoptions._

object Tpolecat {

  def scalacOptionsFor(scalaVersion: String): Seq[String] =
    ScalacOptions.defaultTokensForVersion(ScalaVersion.unsafeFromString(scalaVersion))

}
