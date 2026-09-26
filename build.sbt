ThisBuild / tlBaseVersion := "0.2" // current series x.y

ThisBuild / organization := "io.chrisdavenport"
ThisBuild / organizationName := "Christopher Davenport"
ThisBuild / startYear := Some(2022)
ThisBuild / licenses := Seq(License.MIT)
ThisBuild / developers := List(
  tlGitHubDev("christopherdavenport", "Christopher Davenport")
)

// sbt-davenverse published a snapshot from main on every push. Dropped: the
// Central Portal will not enable snapshots for the io.chrisdavenport namespace.
ThisBuild / tlCiReleaseBranches := Seq()

val Scala213tl = "2.13.18"
ThisBuild / crossScalaVersions := Seq(Scala213tl, "3.3.8")
ThisBuild / scalaVersion := Scala213tl

// Compiler settings DavenversePlugin injected globally. sbt-typelevel-ci-release
// does not supply these (only sbt-typelevel-settings would). Scoped to ThisBuild
// so every project picks them up without editing each one.
ThisBuild / libraryDependencies ++= (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, _)) =>
    Seq(
      compilerPlugin("org.typelevel" % "kind-projector" % "0.13.4" cross CrossVersion.full),
      compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
    )
  case _ => Nil
})
ThisBuild / scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((3, _)) => Seq("-Ykind-projector")
  case Some((2, 12)) => Seq("-Ypartial-unification")
  case _ => Nil
})


ThisBuild / versionScheme := Some("early-semver")
ThisBuild / testFrameworks += new TestFramework("munit.Framework")

ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec.temurin("17"))

val catsV = "2.13.0"
val catsEffectV = "3.7.1"
val fs2V = "3.14.0"
val munitCatsEffectV = "2.2.1"


// Projects
lazy val `process` = project.in(file("."))
    .enablePlugins(NoPublishPlugin)
  .aggregate(core.jvm, core.js, examples.jvm, examples.js)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(
    name := "process",
    libraryDependencies ++= Seq(
      "org.typelevel"               %%% "cats-core"                  % catsV,
      "org.typelevel"               %%% "cats-effect"                % catsEffectV,
      "co.fs2"                      %%% "fs2-core"                   % fs2V,
      "co.fs2"                      %%% "fs2-io"                     % fs2V,
      "org.typelevel"               %%% "literally"                  % "1.0.2",

      "org.typelevel"               %%% "munit-cats-effect"        % munitCatsEffectV         % Test,
    ),
    libraryDependencies ++= {
      if (scalaVersion.value.startsWith("3.")) Nil
      else List("org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided")
    },
  ).jsSettings(
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule)},
  )

lazy val examples = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("examples"))
    .enablePlugins(NoPublishPlugin)
  .dependsOn(core)
  .settings(
    name := "process-examples",
    run / fork := true,
  ).jsSettings(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule)},
  )



lazy val site = project.in(file("site"))
    .enablePlugins(TypelevelSitePlugin)
  .settings(
    laikaTheme := tlSiteHelium.value.site
      .topNavigationBar(
        homeLink = laika.helium.config.IconLink.internal(laika.ast.Path.Root / "index.md", laika.helium.config.HeliumIcon.home)
      )
      .build
  )
  .dependsOn(core.jvm)
  .settings{
    Seq(
    )
  }
