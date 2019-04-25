package model

import model.Direction.Direction

case class StartPosition (
  startPosX: Integer,
  startPosY: Integer,
  direction: Direction
)

object StartPosition {
  def fromString(rawString: String): StartPosition = {
    val inputValues = rawString.split(" ")
    val x: Integer = inputValues.head.toInt
    val y: Integer = inputValues(1).toInt
    val direction = Direction.withName(inputValues(2))
    new StartPosition(x, y, direction)
  }
}


