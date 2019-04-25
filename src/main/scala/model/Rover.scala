package model

import model.Direction.Direction

/**
  * A mars rover. Can turn left or right, and move forward. Not responsible for knowing if it's
  * about to bump into another rover, or go off the grid (that's handled by the service, which has
  * information about every rover, and the grid they're on).
  * @param id The unique identifier for this rover. Not strictly necessary, but allows us an easy way
  *           of keeping track of these guys. We could just rely on the start position,
  *           but this seems to be safer.
  * @param posX The rovers starting position on the x axis
  * @param posY The rovers starting position on the y axis
  * @param facingDirection The direction the rover is currently facing
  */
case class Rover (
  id: Integer,
  posX: Integer,
  posY: Integer,
  facingDirection: Direction
) {

  def turnRover(turnMovement: Movement.Movement): Rover = {
    val direction = turnMovement match {
      case Movement.R if facingDirection == Direction.N => Direction.E
      case Movement.R if facingDirection == Direction.E => Direction.S
      case Movement.R if facingDirection == Direction.S => Direction.W
      case Movement.R if facingDirection == Direction.W => Direction.N
      case Movement.L if facingDirection == Direction.N => Direction.W
      case Movement.L if facingDirection == Direction.W => Direction.S
      case Movement.L if facingDirection == Direction.S => Direction.E
      case Movement.L if facingDirection == Direction.E => Direction.N
      case _ => facingDirection
    }
    Rover(id, posX, posY, direction)
  }

  def moveRover(): Rover = {
    facingDirection match {
      case Direction.N => Rover(id, posX, posY + 1, facingDirection)
      case Direction.E => Rover(id, posX + 1, posY, facingDirection)
      case Direction.S => Rover(id, posX, posY - 1, facingDirection)
      case Direction.W => Rover(id, posX - 1, posY, facingDirection)
    }
  }

  def getStatus(): String = {
    s"position x:$posX y:$posY, facing $facingDirection"
  }

  def getStatusSimple(): String = {
    s"$posX $posY $facingDirection"
  }

}
