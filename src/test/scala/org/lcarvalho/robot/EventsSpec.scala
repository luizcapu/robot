package org.lcarvalho.robot

import org.lcarvalho.robot.Events._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class EventsSpec extends AnyFunSpec with Matchers {

  describe("Events enum") {
    it("should contain only known values") {
      Events.values.toList should contain only (Left, Right, Move, Quit, Help)
    }
  }

  describe("Left event") {
    it("should contain correct enum entry name") {
      Left.toString shouldEqual "L"
    }

    it("should return correct description") {
      Left.description shouldEqual "L - turn left"
    }
  }

  describe("Right event") {
    it("should contain correct enum entry name") {
      Right.toString shouldEqual "R"
    }

    it("should return correct description") {
      Right.description shouldEqual "R - turn right"
    }
  }

  describe("Move event") {
    it("should contain correct enum entry name") {
      Move.toString shouldEqual "M"
    }

    it("should return correct description") {
      Move.description shouldEqual "M - move forward"
    }
  }

  describe("Quit event") {
    it("should contain correct enum entry name") {
      Quit.toString shouldEqual "Q"
    }

    it("should return correct description") {
      Quit.description shouldEqual "Q - quit"
    }
  }

  describe("Help event") {
    it("should contain correct enum entry name") {
      Help.toString shouldEqual "?"
    }

    it("should return correct description") {
      Help.description shouldEqual "? - this message"
    }
  }
}
