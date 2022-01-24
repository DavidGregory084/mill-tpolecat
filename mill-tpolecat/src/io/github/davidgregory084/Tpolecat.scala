package io.github.davidgregory084

import io.github.davidgregory084.ScalaVersion._
import scala.Ordering.Implicits._

object Tpolecat {
  private val allScalacOptions = Seq(
    ScalacOption("-Xsource:3", isSupported = v211 <= _),                                                                     // Treat compiler input as Scala source for the specified version, see scala/bug#8126.
    ScalacOption("-deprecation", isSupported = version => version < v213 || v300 <= version),                                // Emit warning and location for usages of deprecated APIs. Not really removed but deprecated in 2.13.
    ScalacOption("-migration", isSupported = v300 <= _),                                                                     // Emit warning and location for migration issues from Scala 2.
    ScalacOption("-explaintypes", isSupported = _ < v300),                                                                   // Explain type errors in more detail.
    ScalacOption("-explain-types", isSupported = v300 <= _),                                                                 // Explain type errors in more detail.
    ScalacOption("-explain", isSupported = v300 <= _),                                                                       // Explain errors in more detail.
    ScalacOption("-feature"),                                                                                                // Emit warning and location for usages of features that should be imported explicitly.
    ScalacOption("-language:existentials", isSupported = _ < v300),                                                          // Existential types (besides wildcard types) can be written and inferred
    ScalacOption("-language:experimental.macros", isSupported = _ < v300),                                                   // Allow macro definition (besides implementation and application)
    ScalacOption("-language:higherKinds", isSupported = _ < v300),                                                           // Allow higher-kinded types
    ScalacOption("-language:implicitConversions", isSupported = _ < v300),                                                   // Allow definition of implicit functions called views
    ScalacOption("-language:existentials,experimental.macros,higherKinds,implicitConversions", isSupported = v300 <= _),     // the four options above, dotty style
    ScalacOption("-unchecked"),                                                                                              // Enable additional warnings where generated code depends on assumptions.
    ScalacOption("-Xcheckinit", isSupported = _ < v300),                                                                     // Wrap field accessors to throw an exception on uninitialized access.
    ScalacOption("-Xfatal-warnings"),                                                                                        // Fail the compilation if there are any warnings.
    ScalacOption("-Xlint", isSupported = _ < v211),                                                                          // Used to mean enable all linting options but now the syntax for that is different (-Xlint:_ I think)
    ScalacOption("-Xlint:adapted-args", isSupported = version => v211 <= version && version < v300),                         // Warn if an argument list is modified to match the receiver.
    ScalacOption("-Xlint:by-name-right-associative", isSupported = version => v211 <= version && version < v213),            // By-name parameter of right associative operator.
    ScalacOption("-Xlint:constant", isSupported = version => v212 <= version && version < v300),                             // Evaluation of a constant arithmetic expression results in an error.
    ScalacOption("-Xlint:delayedinit-select", isSupported = version => v211 <= version && version < v300),                   // Selecting member of DelayedInit.
    ScalacOption("-Xlint:deprecation", isSupported = version => v213 <= version && version < v300),                          // Emit warning and location for usages of deprecated APIs.
    ScalacOption("-Xlint:doc-detached", isSupported = version => v211 <= version && version < v300),                         // A Scaladoc comment appears to be detached from its element.
    ScalacOption("-Xlint:inaccessible", isSupported = version => v211 <= version && version < v300),                         // Warn about inaccessible types in method signatures.
    ScalacOption("-Xlint:infer-any", isSupported = version => v211 <= version && version < v300),                            // Warn when a type argument is inferred to be `Any`.
    ScalacOption("-Xlint:missing-interpolator", isSupported = version => v211 <= version && version < v300),                 // A string literal appears to be missing an interpolator id.
    ScalacOption("-Xlint:nullary-override", isSupported = version => v211 <= version && version < ScalaVersion(2, 13, 3)),   // Warn when non-nullary `def f()' overrides nullary `def f'.
    ScalacOption("-Xlint:nullary-unit", isSupported = version => v211 <= version && version < v300),                         // Warn when nullary methods return Unit.
    ScalacOption("-Xlint:option-implicit", isSupported = version => v211 <= version && version < v300),                      // Option.apply used implicit view.
    ScalacOption("-Xlint:package-object-classes", isSupported = version => v211 <= version && version < v300),               // Class or object defined in package object.
    ScalacOption("-Xlint:poly-implicit-overload", isSupported = version => v211 <= version && version < v300),               // Parameterized overloaded implicit methods are not visible as view bounds.
    ScalacOption("-Xlint:private-shadow", isSupported = version => v211 <= version && version < v300),                       // A private field (or class parameter) shadows a superclass field.
    ScalacOption("-Xlint:stars-align", isSupported = version => v211 <= version && version < v300),                          // Pattern sequence wildcard must align with sequence component.
    ScalacOption("-Xlint:type-parameter-shadow", isSupported = version => v211 <= version && version < v300),                // A local type parameter shadows a type already in scope.
    ScalacOption("-Xlint:unsound-match", isSupported = version => v211 <= version && version < v213),                        // Pattern match may not be typesafe.
    ScalacOption("-Wunused:nowarn", isSupported = version => v213 <= version && version < v300),                             // Ensure that a `@nowarn` annotation actually suppresses a warning.
    ScalacOption("-Yno-adapted-args", isSupported = _ < v213),                                                               // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    ScalacOption("-Ywarn-dead-code", isSupported = _ < v213),                                                                // Warn when dead code is identified.
    ScalacOption("-Wdead-code", isSupported = version => v213 <= version && version < v300),                                 // ^ Replaces the above
    ScalacOption("-Ywarn-extra-implicit", isSupported = version => v212 <= version && version < v213),                       // Warn when more than one implicit parameter section is defined.
    ScalacOption("-Wextra-implicit", isSupported = version => v213 <= version && version < v300),                            // ^ Replaces the above
    ScalacOption("-Ywarn-inaccessible", isSupported = _ < v211),                                                             // Warn about inaccessible types in method signatures. Alias for -Xlint:inaccessible so can be removed as of 2.11.
    ScalacOption("-Ywarn-nullary-override", isSupported = _ < v213),                                                         // Warn when non-nullary `def f()' overrides nullary `def f'.
    ScalacOption("-Ywarn-nullary-unit", isSupported = _ < v213),                                                             // Warn when nullary methods return Unit.
    ScalacOption("-Ywarn-numeric-widen", isSupported = _ < v213),                                                            // Warn when numerics are widened.
    ScalacOption("-Wnumeric-widen", isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption("-Xlint:implicit-recursion", isSupported = version => ScalaVersion(2, 13, 3) <= version && version < v300), // Warn when an implicit resolves to an enclosing self-definition
    ScalacOption("-Ywarn-unused-import", isSupported = version => v211 <= version && version < v212),                        // Warn if an import selector is not referenced.
    ScalacOption("-Ywarn-unused:implicits", isSupported = version => v212 <= version && version < v213),                     // Warn if an implicit parameter is unused.
    ScalacOption("-Wunused:implicits", isSupported = version => v213 <= version && version < v300),                          // ^ Replaces the above
    ScalacOption("-Wunused:explicits", isSupported = version => v213 <= version && version < v300),                          // Warn if an explicit parameter is unused.
    ScalacOption("-Ywarn-unused:imports", isSupported = version => v212 <= version && version < v213),                       // Warn if an import selector is not referenced.
    ScalacOption("-Wunused:imports", isSupported = version => v213 <= version && version < v300),                            // ^ Replaces the above
    ScalacOption("-Ywarn-unused:locals", isSupported = version => v212 <= version && version < v213),                        // Warn if a local definition is unused.
    ScalacOption("-Wunused:locals", isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption("-Ywarn-unused:params", isSupported = version => v212 <= version && version < v213),                        // Warn if a value parameter is unused.
    ScalacOption("-Wunused:params", isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption("-Ywarn-unused:patvars", isSupported = version => v212 <= version && version < v213),                       // Warn if a variable bound in a pattern is unused.
    ScalacOption("-Wunused:patvars", isSupported = version => v213 <= version && version < v300),                            // ^ Replaces the above
    ScalacOption("-Ywarn-unused:privates", isSupported = version => v212 <= version && version < v213),                      // Warn if a private member is unused.
    ScalacOption("-Wunused:privates", isSupported = version => v213 <= version && version < v300),                           // ^ Replaces the above
    ScalacOption("-Ywarn-value-discard", isSupported = _ < v213),                                                            // Warn when non-Unit expression results are unused.
    ScalacOption("-Wvalue-discard", isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption("-Ykind-projector", isSupported = v300 <= _),                                                               // Enables a subset of kind-projector syntax (see https://github.com/lampepfl/dotty/pull/7775)
    ScalacOption("-Ypartial-unification", isSupported = version => ScalaVersion(2, 11, 9) <= version && version < v213)      // Enable partial unification in type constructor inference
  )

  def scalacOptionsFor(scalaVersion: String): Seq[String] = {
    val commonOpts = Seq("-encoding", "utf8")
    val scalaVer = ScalaVersion(scalaVersion)
    val versionedOpts = allScalacOptions.filter(_.isSupported(scalaVer)).map(_.name)

    commonOpts ++ versionedOpts
  }
}
