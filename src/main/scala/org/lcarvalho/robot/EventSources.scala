package org.lcarvalho.robot

object EventSources {
  trait EventSource {
    def ingest(): String
  }

  object StdInSource extends EventSource {
    override def ingest(): String = scala.io.StdIn.readLine()
  }
}
