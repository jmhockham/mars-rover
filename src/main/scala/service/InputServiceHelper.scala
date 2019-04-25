package service

import model.{Direction, Movement}

object InputServiceHelper {

  def checkInputGrid(x: Integer, y: Integer): Boolean = {
    (x, y) match {
      case (x, y) if x > 100 || y > 100 =>
        println("grid arguments (x/y) cannot be greater than 100")
        false
      case (x, y) if x < 5 || y < 5 =>
        println("grid arguments (x/y) cannot be less than 5")
        false
      case _ => true
    }
  }

  def checkStartingPosition(maxX:Integer, maxY:Integer, startX:Integer, startY:Integer, direction: String): Boolean = {
    (startX,startY,direction) match {
      case (x,y,_) if x <= 0 || y <= 0 => false
      case (x,y,_) if x > maxX || y > maxY => false
      case (_,_,facing) if !Direction.hasName(facing) => false
      case _ => true
    }
  }

  def checkMovementsFormat(movements: Seq[String]): Boolean = movements.forall(Movement.hasName)
}
