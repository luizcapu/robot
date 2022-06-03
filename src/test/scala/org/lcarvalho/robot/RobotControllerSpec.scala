package org.lcarvalho.robot

import org.lcarvalho.robot.EventSources.EventSource
import org.lcarvalho.robot.Events._
import org.lcarvalho.robot.Directions._
import org.lcarvalho.robot.Sinks.Sink
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class RobotControllerSpec extends RobotControllerSpecHelper {

  describe("before initialize") {
    it("should not be running") {
      controller.isRunning shouldEqual false
    }

    it("should not accept ingestion and log message to the output") {
      controller.ingestEvent()
      validateRobotState(Robot())
      validateOutput("Controller is not initialized.")
    }
  }

  describe("when initializing") {
    it("should output the welcome message and execute the initializeEvent") {
      controller.initialize()
      validateHelpOutput(Some("Hello! Robot coming online."))
    }
  }

  describe("after initialized") {
    it("should be running") {
      controller.isRunning shouldEqual true
    }
  }

  describe("when receiving an invalid event") {
    it("should execute the defaultEvent") {
      EventSourceMock.enqueueInvalid()
      controller.ingestEvent()
      validateHelpOutput()
    }
  }

  describe("when receiving Left events") {
    it("should turn left and log state to the output") {
      // ensure Robot is on the expected initial state
      validateRobotState(Robot(North))

      ingestAndValidate(Left, Robot(West), Robot(West).toString())
      ingestAndValidate(Left, Robot(South), Robot(South).toString())
      ingestAndValidate(Left, Robot(East), Robot(East).toString())
      ingestAndValidate(Left, Robot(North), Robot(North).toString())
    }
  }

  describe("when receiving Right events") {
    it("should turn right and log state to the output") {
      // ensure Robot is on the expected initial state
      validateRobotState(Robot(North))

      ingestAndValidate(Right, Robot(East), Robot(East).toString())
      ingestAndValidate(Right, Robot(South), Robot(South).toString())
      ingestAndValidate(Right, Robot(West), Robot(West).toString())
      ingestAndValidate(Right, Robot(North), Robot(North).toString())
    }
  }

  describe("when receiving Move events") {
    it("should increment the correct axis and log state to the output") {
      // ensure Robot is on the expected initial state
      validateRobotState(Robot(North))

      // move North
      ingestAndValidate(Move, Robot(North, 0, 1), Robot(North, 0, 1).toString())

      // turn East and Move
      ingestAndDiscardOutput(Right)
      ingestAndValidate(Move, Robot(East, 1, 1), Robot(East, 1, 1).toString())

      // turn South and Move
      ingestAndDiscardOutput(Right)
      ingestAndValidate(Move, Robot(South, 1, 0), Robot(South, 1, 0).toString())

      // turn West and Move
      ingestAndDiscardOutput(Right)
      ingestAndValidate(Move, Robot(West, 0, 0), Robot(West, 0, 0).toString())

      // turn North to reset state
      ingestAndValidate(Right, Robot(), Robot().toString())
    }
  }

  describe("when receiving Help event") {
    it("should log Help instructions to the output and not change the state") {
      val stateBefore = controller.currentState
      ingest(Help)
      validateHelpOutput()
      controller.currentState shouldEqual stateBefore
    }
  }

  describe("when receiving Quit event") {
    it("should switch isRunning state and log nothing to the output") {
      val isRunningBefore = controller.isRunning
      val stateBefore = controller.currentState

      isRunningBefore shouldEqual true

      ingest(Quit)

      controller.isRunning shouldEqual false
      controller.currentState shouldEqual stateBefore
      SinkMock.isEmpty shouldEqual true
    }
  }
}

trait RobotControllerSpecHelper extends AnyFunSpec with Matchers with BeforeAndAfterEach {
  object EventSourceMock extends EventSource {
    private val eventQueue = mutable.Queue.empty[String]
    override def ingest(): String = eventQueue.dequeue()
    def enqueue(event: Event): Unit = eventQueue.enqueue(event.toString)
    def enqueueInvalid(): Unit = {
      val invalidEntry: String = "foo"
      assert(!Events.values.exists(_.toString == invalidEntry), s"'$invalidEntry' is not an invalid event. ")
      eventQueue.enqueue(invalidEntry)
    }
    def isEmpty: Boolean = eventQueue.isEmpty
  }

  object SinkMock extends Sink {
    private val outQueue = mutable.Queue.empty[Any]
    override def out(value: Any): Unit = outQueue.enqueue(value)
    def dequeue(): Any = outQueue.dequeue()
    def clear(): Unit = outQueue.clear()
    def isEmpty: Boolean = outQueue.isEmpty
  }

  protected val controller = new RobotController(EventSourceMock, SinkMock)

  override def beforeEach(): Unit = {
    super.beforeEach()
    // ensure that every previous events have been consumed
    EventSourceMock.isEmpty shouldEqual true
    // ensure that every previous output has been checked
    SinkMock.isEmpty shouldEqual true
    // ensure Robot is back to the initial state
    validateRobotState(Robot())
  }

  protected def validateRobotState(expectation: Robot): Unit = {
    controller.currentState shouldEqual expectation
  }

  protected def ingest(event: Event): Unit = {
    EventSourceMock.isEmpty shouldEqual true
    EventSourceMock.enqueue(event)
    controller.ingestEvent()
  }

  protected def ingestAndValidate(event: Event, expectedRobot: Robot, outputExpectations: String*): Unit = {
    ingest(event)
    validateRobotState(expectedRobot)
    validateOutput(outputExpectations: _*)
  }

  protected def ingestAndDiscardOutput(event: Event): Unit = {
    ingest(event)
    SinkMock.clear()
  }

  protected def validateOutput(expectations: String*): Unit = {
    expectations.foreach { expectation =>
      SinkMock.dequeue().toString shouldEqual expectation
    }
    SinkMock.isEmpty shouldEqual true
  }

  protected def validateHelpOutput(fooOpt: Option[String] = None): Unit = {
    val foo = fooOpt.map(List(_)).getOrElse(Nil)
    val expectations: List[String] = foo ++ List("Command the robot with:") ++ Events.values.toList.map(_.description)
    validateOutput(expectations: _*)
  }
}
