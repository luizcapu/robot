package org.lcarvalho.robot

import org.lcarvalho.robot.EventSources.EventSource
import org.lcarvalho.robot.Events._
import org.lcarvalho.robot.Sinks.Sink

import scala.util.Try

class RobotController(
  source: EventSource,
  sink: Sink,
  initializeEvent: Option[Event] = Some(Help),
  defaultEvent: Option[Event] = Some(Help),
  initialState: Robot = Robot()
) {

  private var robotState = initialState
  private var isInitialized: Boolean = false

  def initialize(): Unit = {
    isInitialized = true
    sink.out("Hello! Robot coming online.")
    initializeEvent.foreach(parse)
  }

  def isRunning: Boolean = isInitialized
  def currentState: Robot = robotState

  def ingestEvent(): Unit = {
    if (isInitialized) {
      val in: String = source.ingest()
      Try(Events.withName(in)).toOption.orElse(defaultEvent).foreach(parse)
    } else sink.out("Controller is not initialized.")
  }

  private def parse(event: Event): Unit = {
    event match {
      case Move         => assignRobotState(robotState.move())
      case Left | Right => assignRobotState(robotState.turn(event))
      case Help =>
        sink.out("Command the robot with:")
        Events.values.foreach(event => sink.out(event.description))
      case Quit => isInitialized = false
    }
  }

  private def assignRobotState(robot: Robot): Unit = {
    robotState = robot
    sink.out(robotState.toString())
  }
}
