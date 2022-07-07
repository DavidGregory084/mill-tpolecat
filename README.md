## mill-tpolecat

[![CI](https://github.com/DavidGregory084/mill-tpolecat/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/DavidGregory084/mill-tpolecat/actions/workflows/ci.yml)
[![License](https://img.shields.io/github/license/DavidGregory084/mill-tpolecat.svg)](https://opensource.org/licenses/Apache-2.0)
![Maven Central](https://img.shields.io/maven-central/v/io.github.davidgregory084/mill-tpolecat_mill0.10_2.13)

### scalac options for the enlightened

mill-tpolecat is an extension for the [Mill build tool](https://github.com/lihaoyi/mill/) for automagically configuring scalac options according to the project Scala version, inspired by Rob Norris ([@tpolecat](https://github.com/tpolecat))'s excellent series of blog posts providing [recommended options](https://tpolecat.github.io/2017/04/25/scalac-flags.html) to get the most out of the compiler.

It has been tested with versions 0.10.x and 0.9.x of mill.

### Usage

Import the module in `build.sc` using mill's `$ivy` import syntax, and extend `TpolecatModule` in your build definition:

```scala
// build.sc

import $ivy.`io.github.davidgregory084::mill-tpolecat::0.3.0`

import io.github.davidgregory084.TpolecatModule

object core extends TpolecatModule {
  def scalaVersion = "2.13.7"
}
```

Don't forget to apply the mill platform suffix to your import according to the mill version that you are using.

If necessary you can filter out scalac options that are not appropriate for your project:

```scala
// build.sc

import $ivy.`io.github.davidgregory084::mill-tpolecat_mill0.10:0.3.0`

import io.github.davidgregory084.TpolecatModule

object core extends TpolecatModule {
  def scalaVersion = "2.13.7"
  def scalacOptions = T { super.scalacOptions().filterNot(Set("-Yno-imports")) }
}
```

### Caveat

I can't promise this plugin will work for old minor releases of Scala. It has been tested with:

* 2.13.7
* 2.12.11
* 2.11.12

### License

All code in this repository is licensed under the Apache License, Version 2.0.  See [LICENSE](./LICENSE).
