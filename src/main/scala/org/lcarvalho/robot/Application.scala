package org.lcarvalho.robot

import org.lcarvalho.robot.EventSources.StdInSource
import org.lcarvalho.robot.Sinks.StdOutSink

object Application {

  def main(args: Array[String]): Unit = {
    val controller = new RobotController(StdInSource, StdOutSink)
    controller.initialize()
    while (controller.isRunning) controller.ingestEvent()
  }

}
