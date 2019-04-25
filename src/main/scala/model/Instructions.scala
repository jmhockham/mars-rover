package model

import model.Movement.Movement

case class Instructions (
  roverId: Integer,
  startPosition: StartPosition,
  movements: Seq[Movement]
)

object Instructions {
  def fromStrings(strings: Seq[String], roverId: Integer): Instructions = {
    val startPosition = StartPosition.fromString(strings.head)
    val movements = strings(1).map(c => Movement.withName(c.toString))
    new Instructions(roverId, startPosition, movements)
  }
}
