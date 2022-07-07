package io.github.davidgregory084

case class ScalacOption(names: Seq[String], isSupported: ScalaVersion => Boolean = _ => true)
