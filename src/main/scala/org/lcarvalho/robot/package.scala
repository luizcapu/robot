package org.lcarvalho

package object robot {

  object Directions extends Enumeration {
    type Direction = Value
    val North: Direction = Value("North")
    val East: Direction = Value("East")
    val South: Direction = Value("South")
    val West: Direction = Value("West")
  }

  import Directions._
  implicit class RichDirection(direction: Direction) {

    def label: String = direction.toString

    def next: Direction =
      values.find(_.id == direction.id + 1).getOrElse(values.head)

    def previous: Direction =
      values.find(_.id == direction.id - 1).getOrElse(values.last)
  }

  object Events extends Enumeration {
    type Event = Value
    val Left: Event = Value("L")
    val Right: Event = Value("R")
    val Move: Event = Value("M")
    val Quit: Event = Value("Q")
    val Help: Event = Value("?")
  }

  import Events._

  implicit class RichEvent(event: Events.Event) {

    def description: String = s"${event.toString} - $getDescription"

    private def getDescription: String = {
      event match {
        case Left  => "turn left"
        case Right => "turn right"
        case Move  => "move forward"
        case Quit  => "quit"
        case Help  => "this message"
      }
    }
  }

  case class Robot(direction: Direction = North, x: Int = 0, y: Int = 0) {

    def turn(event: Event): Robot = {
      event match {
        case Left  => copy(direction = direction.previous)
        case Right => copy(direction = direction.next)
        case _     => this
      }
    }

    def move(): Robot = {
      direction match {
        case North => copy(y = y + 1)
        case South => copy(y = y - 1)
        case West  => copy(x = x - 1)
        case East  => copy(x = x + 1)
      }
    }

    override def toString(): String = s"Robot at ($x, $y) facing ${direction.label}"
  }
}
