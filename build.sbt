name := "scala meta idea"

version := "0.0.1"

val scalametaVersion = "1.8.0"
val paradiseVersion = "3.0.0-M9"

val commonSettings = Seq(
  scalaVersion := "2.12.2"
)

lazy val root =
  (project in file("."))
    .aggregate(annotation, main)
    .settings(commonSettings)

lazy val annotation =
  (project in file("annotation"))
    .settings(commonSettings)
    .settings(macroAnnotationSettings)

lazy val main =
  (project in file("main"))
    .settings(commonSettings)
    .settings(macroAnnotationSettings)
    .dependsOn(annotation)

libraryDependencies += "org.scalameta" %% "scalameta" % scalametaVersion

lazy val macroAnnotationSettings: Seq[Def.Setting[_]] = Seq(
  addCompilerPlugin("org.scalameta" % "paradise" % paradiseVersion cross CrossVersion.full),
  libraryDependencies += "org.scalameta" %% "scalameta" % scalametaVersion % Provided,
  scalacOptions += "-Xplugin-require:macroparadise",
     // macroparadise plugin doesn't work in repl yet.
    scalacOptions in (Compile, console) ~= (_ filterNot (_ contains "paradise"))
)
