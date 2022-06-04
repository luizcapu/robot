package org.lcarvalho.robot

/**
  * Sinks available to be used by [[RobotController]]
  */
object Sinks {

  /**
    * The Sink interface
    */
  trait Sink {

    /**
      * Appropriately handles the value output
      * @param value the value output to handle
      */
    def out(value: Any): Unit
  }

  /**
    * Implements a Sink for the standard output
    */
  object StdOutSink extends Sink {
    override def out(value: Any): Unit = println(value)
  }
}
