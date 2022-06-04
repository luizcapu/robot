package org.lcarvalho.robot

/**
  * EventSources available to be used by [[RobotController]]
  */
object EventSources {

  /**
    * The EventSource interface
    */
  trait EventSource {

    /**
      * Ingest one event at a time from the source
      * @return the event as a String
      */
    def ingest(): String
  }

  /**
    * Implements an EventSource for the standard input
    */
  object StdInSource extends EventSource {
    override def ingest(): String = scala.io.StdIn.readLine()
  }
}
