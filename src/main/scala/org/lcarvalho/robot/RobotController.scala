package org.lcarvalho.robot

import org.lcarvalho.robot.EventSources.EventSource
import org.lcarvalho.robot.Events._
import org.lcarvalho.robot.Sinks.Sink

import scala.util.Try

/**
  * Controls the [[Robot]] by interfacing the ins and outs.
  *
  * Ingest the events from the [[EventSources.EventSource]], parse and react to them, forwarding the outputs to the provided [[Sinks.Sink]] and keeping track of the latest [[Robot]] state.
  *
  *
  * @param source the source where to ingest events from
  * @param sink the sink where to forward the outputs to
  * @param initializeEvent the event to be executed when initializing the [[RobotController]]
  * @param defaultEvent the default event to be executed upon an invalid event entry
  * @param initialState the initial [[Robot]] state
  */
class RobotController(
  source: EventSource,
  sink: Sink,
  initializeEvent: Option[Event] = Some(Help),
  defaultEvent: Option[Event] = Some(Help),
  initialState: Robot = Robot()
) {

  private var robotState = initialState
  private var isInitialized: Boolean = false

  /**
    * Initializes the controller to make it ready to accept events, executing initializeEvent, if initializeEvent defined.
    *
    * This method must be called before attempting to [[ingestEvent]]
    */
  def initialize(): Unit = {
    isInitialized = true
    sink.out("Hello! Robot coming online.")
    initializeEvent.foreach(parse)
  }

  /**
    * Whether or not the controller is initialized and accepting events
    * @return boolean
    */
  def isRunning: Boolean = isInitialized

  /**
    * The latest [[Robot]] state
    * @return [[Robot]]
    */
  def currentState: Robot = robotState

  /**
    * If [[isRunning]]:
    *   - ingest event from [[EventSources.EventSource]], falling back to defaultEvent if entry is invalid and defaultEvent is defined
    *   - appropriately reacts to the event, keeping track of the [[Robot]] state and logging the output to the [[Sinks.Sink]]
    *
    * Otherwise:
    *   - do nothing and log warning message to the [[Sinks.Sink]].
    */
  def ingestEvent(): Unit = {
    if (isInitialized) {
      val in: String = source.ingest()
      Try(Events.withName(in)).toOption.orElse(defaultEvent).foreach(parse)
    } else sink.out("Controller is not initialized.")
  }

  private def parse(event: Event): Unit = {
    event match {
      case Move  => assignRobotState(robotState.move())
      case Left  => assignRobotState(robotState.turnLeft())
      case Right => assignRobotState(robotState.turnRight())
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
