package io.github.davidgregory084

import io.github.davidgregory084.ScalaVersion._
import scala.Ordering.Implicits._

object Tpolecat {
  private val allScalacOptions = Seq(
    ScalacOption(Seq("-encoding", "utf8")),                                                                                       // Specify character encoding used by source files
    ScalacOption(Seq("-Xsource:3"), isSupported = version => v211 <= version && version < v300),                                  // Treat compiler input as Scala source for the specified version, see scala/bug#8126.
    ScalacOption(Seq("-deprecation"), isSupported = version => version < v213 || v300 <= version),                                // Emit warning and location for usages of deprecated APIs. Not really removed but deprecated in 2.13.
    ScalacOption(Seq("-explaintypes"), isSupported = _ < v300),                                                                   // Explain type errors in more detail.
    ScalacOption(Seq("-explain-types"), isSupported = v300 <= _),                                                                 // Explain type errors in more detail.
    ScalacOption(Seq("-explain"), isSupported = v300 <= _),                                                                       // Explain errors in more detail.
    ScalacOption(Seq("-feature")),                                                                                                // Emit warning and location for usages of features that should be imported explicitly.
    ScalacOption(Seq("-language:existentials"), isSupported = _ < v300),                                                          // Existential types (besides wildcard types) can be written and inferred
    ScalacOption(Seq("-language:experimental.macros")),                                                                           // Allow macro definition (besides implementation and application)
    ScalacOption(Seq("-language:higherKinds")),                                                                                   // Allow higher-kinded types
    ScalacOption(Seq("-language:implicitConversions")),                                                                           // Allow definition of implicit functions called views
    ScalacOption(Seq("-unchecked")),                                                                                              // Enable additional warnings where generated code depends on assumptions.
    ScalacOption(Seq("-Xcheckinit"), isSupported = _ < v300),                                                                     // Wrap field accessors to throw an exception on uninitialized access.
    ScalacOption(Seq("-Xfatal-warnings")),                                                                                        // Fail the compilation if there are any warnings.
    ScalacOption(Seq("-Xlint"), isSupported = _ < v211),                                                                          // Used to mean enable all linting options but now the syntax for that is different (-Xlint:_ I think)
    ScalacOption(Seq("-Xlint:adapted-args"), isSupported = version => v211 <= version && version < v300),                         // Warn if an argument list is modified to match the receiver.
    ScalacOption(Seq("-Xlint:by-name-right-associative"), isSupported = version => v211 <= version && version < v213),            // By-name parameter of right associative operator.
    ScalacOption(Seq("-Xlint:constant"), isSupported = version => v212 <= version && version < v300),                             // Evaluation of a constant arithmetic expression results in an error.
    ScalacOption(Seq("-Xlint:delayedinit-select"), isSupported = version => v211 <= version && version < v300),                   // Selecting member of DelayedInit.
    ScalacOption(Seq("-Xlint:deprecation"), isSupported = version => v213 <= version && version < v300),                          // Emit warning and location for usages of deprecated APIs.
    ScalacOption(Seq("-Xlint:doc-detached"), isSupported = version => v211 <= version && version < v300),                         // A Scaladoc comment appears to be detached from its element.
    ScalacOption(Seq("-Xlint:inaccessible"), isSupported = version => v211 <= version && version < v300),                         // Warn about inaccessible types in method signatures.
    ScalacOption(Seq("-Xlint:infer-any"), isSupported = version => v211 <= version && version < v300),                            // Warn when a type argument is inferred to be `Any`.
    ScalacOption(Seq("-Xlint:missing-interpolator"), isSupported = version => v211 <= version && version < v300),                 // A string literal appears to be missing an interpolator id.
    ScalacOption(Seq("-Xlint:nullary-override"), isSupported = version => v211 <= version && version < ScalaVersion(2, 13, 3)),   // Warn when non-nullary `def f()' overrides nullary `def f'.
    ScalacOption(Seq("-Xlint:nullary-unit"), isSupported = version => v211 <= version && version < v300),                         // Warn when nullary methods return Unit.
    ScalacOption(Seq("-Xlint:option-implicit"), isSupported = version => v211 <= version && version < v300),                      // Option.apply used implicit view.
    ScalacOption(Seq("-Xlint:package-object-classes"), isSupported = version => v211 <= version && version < v300),               // Class or object defined in package object.
    ScalacOption(Seq("-Xlint:poly-implicit-overload"), isSupported = version => v211 <= version && version < v300),               // Parameterized overloaded implicit methods are not visible as view bounds.
    ScalacOption(Seq("-Xlint:private-shadow"), isSupported = version => v211 <= version && version < v300),                       // A private field (or class parameter) shadows a superclass field.
    ScalacOption(Seq("-Xlint:stars-align"), isSupported = version => v211 <= version && version < v300),                          // Pattern sequence wildcard must align with sequence component.
    ScalacOption(Seq("-Xlint:type-parameter-shadow"), isSupported = version => v211 <= version && version < v300),                // A local type parameter shadows a type already in scope.
    ScalacOption(Seq("-Xlint:unsound-match"), isSupported = version => v211 <= version && version < v213),                        // Pattern match may not be typesafe.
    ScalacOption(Seq("-Wunused:nowarn"), isSupported = version => v213 <= version && version < v300),                             // Ensure that a `@nowarn` annotation actually suppresses a warning.
    ScalacOption(Seq("-Yno-adapted-args"), isSupported = _ < v213),                                                               // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    ScalacOption(Seq("-Ywarn-dead-code"), isSupported = _ < v213),                                                                // Warn when dead code is identified.
    ScalacOption(Seq("-Wdead-code"), isSupported = version => v213 <= version && version < v300),                                 // ^ Replaces the above
    ScalacOption(Seq("-Ywarn-extra-implicit"), isSupported = version => v212 <= version && version < v213),                       // Warn when more than one implicit parameter section is defined.
    ScalacOption(Seq("-Wextra-implicit"), isSupported = version => v213 <= version && version < v300),                            // ^ Replaces the above
    ScalacOption(Seq("-Ywarn-inaccessible"), isSupported = _ < v211),                                                             // Warn about inaccessible types in method signatures. Alias for -Xlint:inaccessible so can be removed as of 2.11.
    ScalacOption(Seq("-Ywarn-nullary-override"), isSupported = _ < v213),                                                         // Warn when non-nullary `def f()' overrides nullary `def f'.
    ScalacOption(Seq("-Ywarn-nullary-unit"), isSupported = _ < v213),                                                             // Warn when nullary methods return Unit.
    ScalacOption(Seq("-Ywarn-numeric-widen"), isSupported = _ < v213),                                                            // Warn when numerics are widened.
    ScalacOption(Seq("-Wnumeric-widen"), isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption(Seq("-Xlint:implicit-recursion"), isSupported = version => ScalaVersion(2, 13, 3) <= version && version < v300), // Warn when an implicit resolves to an enclosing self-definition
    ScalacOption(Seq("-Ywarn-unused"), isSupported = version => v211 <= version && version < v212),                               // Warn when local and private vals, vars, defs, and types are unused.
    ScalacOption(Seq("-Ywarn-unused-import"), isSupported = version => v211 <= version && version < v212),                        // Warn if an import selector is not referenced.
    ScalacOption(Seq("-Ywarn-unused:implicits"), isSupported = version => v212 <= version && version < v213),                     // Warn if an implicit parameter is unused.
    ScalacOption(Seq("-Wunused:implicits"), isSupported = version => v213 <= version && version < v300),                          // ^ Replaces the above
    ScalacOption(Seq("-Wunused:explicits"), isSupported = version => v213 <= version && version < v300),                          // Warn if an explicit parameter is unused.
    ScalacOption(Seq("-Ywarn-unused:imports"), isSupported = version => v212 <= version && version < v213),                       // Warn if an import selector is not referenced.
    ScalacOption(Seq("-Wunused:imports"), isSupported = version => v213 <= version && version < v300),                            // ^ Replaces the above
    ScalacOption(Seq("-Ywarn-unused:locals"), isSupported = version => v212 <= version && version < v213),                        // Warn if a local definition is unused.
    ScalacOption(Seq("-Wunused:locals"), isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption(Seq("-Ywarn-unused:params"), isSupported = version => v212 <= version && version < v213),                        // Warn if a value parameter is unused.
    ScalacOption(Seq("-Wunused:params"), isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption(Seq("-Ywarn-unused:patvars"), isSupported = version => v212 <= version && version < v213),                       // Warn if a variable bound in a pattern is unused.
    ScalacOption(Seq("-Wunused:patvars"), isSupported = version => v213 <= version && version < v300),                            // ^ Replaces the above
    ScalacOption(Seq("-Ywarn-unused:privates"), isSupported = version => v212 <= version && version < v213),                      // Warn if a private member is unused.
    ScalacOption(Seq("-Wunused:privates"), isSupported = version => v213 <= version && version < v300),                           // ^ Replaces the above
    ScalacOption(Seq("-Ywarn-value-discard"), isSupported = _ < v213),                                                            // Warn when non-Unit expression results are unused.
    ScalacOption(Seq("-Wvalue-discard"), isSupported = version => v213 <= version && version < v300),                             // ^ Replaces the above
    ScalacOption(Seq("-Ykind-projector"), isSupported = v300 <= _),                                                               // Enables a subset of kind-projector syntax (see https://github.com/lampepfl/dotty/pull/7775)
    ScalacOption(Seq("-Vimplicits"), isSupported = version => ScalaVersion(2, 13, 6) <= version && version < v300),               // Enables the tek/splain features to make the compiler print implicit resolution chains when no implicit value can be found
    ScalacOption(Seq("-Vtype-diffs"), isSupported = version => ScalaVersion(2, 13, 6) <= version && version < v300),              // Enables the tek/splain features to turn type error messages (found: X, required: Y) into colored diffs between the two types
    ScalacOption(Seq("-Ypartial-unification"), isSupported = version => ScalaVersion(2, 11, 9) <= version && version < v213)      // Enable partial unification in type constructor inference
  )

  def scalacOptionsFor(scalaVersion: String): Seq[String] = {
    val scalaVer = ScalaVersion(scalaVersion)
    allScalacOptions.filter(_.isSupported(scalaVer)).flatMap(_.names)
  }
}
