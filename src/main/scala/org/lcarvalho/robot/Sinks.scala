package org.lcarvalho.robot

object Sinks {
  trait Sink {
    def out(value: Any): Unit
  }

  object StdOutSink extends Sink {
    override def out(value: Any): Unit = println(value)
  }
}
