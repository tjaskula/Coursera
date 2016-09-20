import sbt._

/**
  * @param packageName     Used as the prefix for: (1) handout name, (2) the Scala package, (3) source folder.
  * @param key             Per assignment key specified by coursera.
  * @param partId          Identifies the part of the assignment. (We always have one-part assignments.)
  * @param maxScore        Maximum score that can be given for the assignment. Must match the value in the WebAPI.
  * @param styleScoreRatio Defines the portion of the grade that is assigned to style.
  * @param dependencies    Library dependencies specific to this module.
  * @param styleSheet      Path to the scalastyle configuration for this assignment.
  * @param options         Options passed to the java process or coursera infrastructure. Following values are
  *                        supported:
  *
  *                            NAME                               DEFAULT              DESCRIPTION
  *                            Xms                                10m                  -Xms for jvm
  *                            Xmx                                256m                 -Xmx for jvm, should less than `grader-memory`
  *                            individualTimeout                  240                  time out of one test case
  *                            totalTimeout                       850                  total time out, should less than `grader-timeout`
  *                            grader-cpu                         1                    number of cpu for coursera infrastructure
  *                            grader-memory                      1024                 memory for coursera infrastructure
  *                            grader-timeout                     1200                 grading timeout for coursera infrastructure
  */
case class Assignment(packageName: String,
                      key: String,
                      itemId: String,
                      partId: String,
                      maxScore: Double,
                      styleScoreRatio: Double = 0.0d,
                      styleSheet: String = "",
                      dependencies: Seq[ModuleID] = Seq(),
                      options: Map[String, String] = Map()) {
  assert(!(styleScoreRatio == 0.0d ^ styleSheet == ""), "Style sheet and style ratio should be defined in pair.")
}


trait CommonBuild extends Build {

  val course = SettingKey[String]("course")

  val assignment = SettingKey[String]("assignment")

  val assignmentsMap = SettingKey[Map[String, Assignment]]("assignmentsMap")

  val courseId = SettingKey[String]("courseId")

  val commonSourcePackages = SettingKey[Seq[String]]("commonSourcePackages")

  lazy val scalaTestDependency = "org.scalatest" %% "scalatest" % "2.2.4"
}
