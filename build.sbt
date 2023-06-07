import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.11", "3.1.0")
ThisBuild / versionScheme := Some("early-semver")
ThisBuild / testFrameworks += new TestFramework("munit.Framework")

ThisBuild / githubWorkflowJavaVersions := Seq("adopt@1.11")

val catsV = "2.7.0"
val catsEffectV = "3.3.3"
val fs2V = "3.2.3"
val munitCatsEffectV = "1.0.7"


// Projects
lazy val `process` = project.in(file("."))
  .disablePlugins(MimaPlugin)
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

      "org.typelevel"               %%% "munit-cats-effect-3"        % munitCatsEffectV         % Test,
    ),
    libraryDependencies ++= {
      if (isDotty.value) Nil
      else List("org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided")
    },
  ).jsSettings(
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule)},
  )

lazy val examples = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("examples"))
  .disablePlugins(MimaPlugin)
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
  .disablePlugins(MimaPlugin)
  .enablePlugins(DavenverseMicrositePlugin)
  .dependsOn(core.jvm)
  .settings{
    import microsites._
    Seq(
      micrositeDescription := "Cross-Platform Process Support",
    )
  }
