package org.lcarvalho.robot

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.lcarvalho.robot.Directions._
import org.lcarvalho.robot.Events._

import scala.util.Random

class RobotSpec extends AnyFunSpec with Matchers {

  describe("when created with default parameters") {
    it("should face North and have x and y axis set to zero") {
      Robot() shouldEqual Robot(direction = North, x = 0, y = 0)
    }
  }

  describe("when receiving a valid turn event") {
    it("should turn and return an updated object") {
      Robot(North).turn(Left) shouldEqual Robot(West)
      Robot(North).turn(Right) shouldEqual Robot(East)
      Robot(South).turn(Left) shouldEqual Robot(East)
      Robot(South).turn(Right) shouldEqual Robot(West)
      Robot(West).turn(Left) shouldEqual Robot(South)
      Robot(West).turn(Right) shouldEqual Robot(North)
      Robot(East).turn(Left) shouldEqual Robot(North)
      Robot(East).turn(Right) shouldEqual Robot(South)
    }
  }

  describe("when receiving an invalid turn event") {
    it("should not turn or raise exception and return itself") {
      val robot = Robot()
      Events.values.filterNot(e => e == Left || e == Right).foreach { event =>
        robot.turn(event) shouldEqual robot
      }
    }
  }

  describe("when moving") {
    it("should increment y axis by 1 when facing North") {
      val robot = Robot(North, Random.nextInt(), Random.nextInt())
      robot.move() shouldEqual robot.copy(y = robot.y + 1)
    }

    it("should decrement y axis by 1 when facing South") {
      val robot = Robot(South, Random.nextInt(), Random.nextInt())
      robot.move() shouldEqual robot.copy(y = robot.y - 1)
    }

    it("should increment x axis by 1 when facing East") {
      val robot = Robot(East, Random.nextInt(), Random.nextInt())
      robot.move() shouldEqual robot.copy(x = robot.x + 1)
    }

    it("should decrement x axis by 1 when facing West") {
      val robot = Robot(West, Random.nextInt(), Random.nextInt())
      robot.move() shouldEqual robot.copy(x = robot.x - 1)
    }
  }

  describe("toString") {
    it("should return the correct string representation for all directions") {
      Directions.values.foreach { direction =>
        val robot = Robot(direction, Random.nextInt(), Random.nextInt())
        robot.toString() shouldEqual s"Robot at (${robot.x}, ${robot.y}) facing ${direction.label}"
      }
    }
  }
}
