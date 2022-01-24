package io.github.davidgregory084

case class ScalacOption(name: String, isSupported: ScalaVersion => Boolean = _ => true)
