package org.lcarvalho.robot

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.lcarvalho.robot.Directions._

import scala.util.Random

class RobotSpec extends AnyFunSpec with Matchers {

  describe("when created with default parameters") {
    it("should face North and have x and y axis set to zero") {
      Robot() shouldEqual Robot(direction = North, x = 0, y = 0)
    }
  }

  describe("when receiving a valid turn event") {
    it("should turn and return an updated object") {
      Robot(North).turnLeft() shouldEqual Robot(West)
      Robot(North).turnRight() shouldEqual Robot(East)
      Robot(South).turnLeft() shouldEqual Robot(East)
      Robot(South).turnRight() shouldEqual Robot(West)
      Robot(West).turnLeft() shouldEqual Robot(South)
      Robot(West).turnRight() shouldEqual Robot(North)
      Robot(East).turnLeft() shouldEqual Robot(North)
      Robot(East).turnRight() shouldEqual Robot(South)
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
