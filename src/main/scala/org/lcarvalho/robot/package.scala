package org.lcarvalho

package object robot {

  /**
    * All known directions where the [[Robot]] can move to.
    */
  object Directions extends Enumeration {
    type Direction = Value
    val North: Direction = Value("North")
    val East: Direction = Value("East")
    val South: Direction = Value("South")
    val West: Direction = Value("West")
  }

  import Directions._

  /**
    * Enhances the [[Directions.Direction]] capabilities.
    * @param direction
    */
  implicit class RichDirection(direction: Direction) {

    /**
      * The [[Directions.Direction]] display name
      * @return String
      */
    def label: String = direction.toString

    /**
      * The next [[Directions.Direction]] for when turning clockwise
      * @return [[Directions.Direction]]
      */
    def next: Direction =
      values.find(_.id == direction.id + 1).getOrElse(values.head)

    /**
      * The previous [[Directions.Direction]] for when turning anti clockwise
      * @return [[Directions.Direction]]
      */
    def previous: Direction =
      values.find(_.id == direction.id - 1).getOrElse(values.last)
  }

  /**
    * All known events that [[RobotController]] is able to handle.
    */
  object Events extends Enumeration {
    type Event = Value

    /** Turn the [[Robot]] anti clockwise */
    val Left: Event = Value("L")

    /** Turn the [[Robot]] clockwise */
    val Right: Event = Value("R")

    /** Move the [[Robot]] to the [[Directions.Direction]] it is facing */
    val Move: Event = Value("M")

    /** Signs the [[RobotController]] to stop accepting events */
    val Quit: Event = Value("Q")

    /** Display instructions on the available [[Events]] to control the [[Robot]] */
    val Help: Event = Value("?")
  }

  import Events._

  /**
    * Enhances the [[Events.Event]] capabilities.
    * @param event
    */
  implicit class RichEvent(event: Events.Event) {

    /**
      * A friendly description of the event.
      * @return String
      */
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

  /**
    * Holds the current [[Robot]] state on the appropriate attributes.
    *
    * @param direction where the [[Robot]] is currently facing to
    * @param x the x axis position of the [[Robot]]
    * @param y the y axis position of the [[Robot]]
    */
  case class Robot(direction: Direction = North, x: Int = 0, y: Int = 0) {

    /**
      * Return a new instance of a [[Robot]] turned to the Left.
      *
      * @return a new [[Robot]] instance
      */
    def turnLeft(): Robot = copy(direction = direction.previous)

    /**
      * Return a new instance of a [[Robot]] turned to the Right.
      *
      * @return a new [[Robot]] instance
      */
    def turnRight(): Robot = copy(direction = direction.next)

    /**
      * Move the [[Robot]] forward on the correct axis, depending on the [[Directions.Direction]] it is currently facing to.
      *
      * @return a new [[Robot]] instance with the updated coordinates.
      */
    def move(): Robot = {
      direction match {
        case North => copy(y = y + 1)
        case South => copy(y = y - 1)
        case West  => copy(x = x - 1)
        case East  => copy(x = x + 1)
      }
    }

    /**
      * A friendly string representation of the [[Robot]] state for displaying purposes.
      *
      * @return String
      */
    override def toString(): String = s"Robot at ($x, $y) facing ${direction.label}"
  }
}
