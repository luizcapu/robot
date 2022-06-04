package org.lcarvalho.robot

import org.lcarvalho.robot.EventSources.StdInSource
import org.lcarvalho.robot.Sinks.StdOutSink

/**
  * The main Application to execute the code
  */
object Application {

  /**
    * Creates a [[RobotController]] with [[EventSources.StdInSource]] and [[Sinks.StdOutSink]].
    *
    * Keep ingesting events in a loop, until the controller receives a [[Events.Quit]] event.
    * @param args (not used)
    */
  def main(args: Array[String]): Unit = {
    val controller = new RobotController(StdInSource, StdOutSink)
    controller.initialize()
    while (controller.isRunning) controller.ingestEvent()
  }

}
