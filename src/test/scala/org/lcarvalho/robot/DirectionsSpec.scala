package org.lcarvalho.robot

import org.lcarvalho.robot.Directions._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class DirectionsSpec extends AnyFunSpec with Matchers {

  describe("Directions enum") {
    it("should contain only known values") {
      Directions.values.toList should contain only (North, East, South, West)
    }
  }

  describe("North direction") {
    it("should contain correct enum entry name") {
      North.toString shouldEqual "North"
    }

    it("should return correct label") {
      North.label shouldEqual "North"
    }

    it("should return correct previous direction") {
      North.previous shouldEqual West
    }

    it("should return correct next direction") {
      North.next shouldEqual East
    }
  }

  describe("East direction") {
    it("should contain correct enum entry name") {
      East.toString shouldEqual "East"
    }

    it("should return correct label") {
      East.label shouldEqual "East"
    }

    it("should return correct previous direction") {
      East.previous shouldEqual North
    }

    it("should return correct next direction") {
      East.next shouldEqual South
    }
  }

  describe("South direction") {
    it("should contain correct enum entry name") {
      South.toString shouldEqual "South"
    }

    it("should return correct label") {
      South.label shouldEqual "South"
    }

    it("should return correct previous direction") {
      South.previous shouldEqual East
    }

    it("should return correct next direction") {
      South.next shouldEqual West
    }
  }

  describe("West direction") {
    it("should contain correct enum entry name") {
      West.toString shouldEqual "West"
    }

    it("should return correct label") {
      West.label shouldEqual "West"
    }

    it("should return correct previous direction") {
      West.previous shouldEqual South
    }

    it("should return correct next direction") {
      West.next shouldEqual North
    }
  }
}
