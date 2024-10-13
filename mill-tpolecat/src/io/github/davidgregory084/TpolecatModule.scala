package io.github.davidgregory084

import mill._
import mill.scalalib._

trait TpolecatModule extends ScalaModule {
  override def scalacOptions = T {
    super.scalacOptions() ++ Tpolecat.scalacOptionsFor(scalaVersion())
  }
}
