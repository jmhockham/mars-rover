package service

import model.Movement.Movement
import model.{InputData, Instructions, Movement, Rover}

import scala.annotation.tailrec

object RoverService {

  def processInstructions(inputData: InputData) = {
    val rovers = inputData.instructions.map { x =>
      Rover(
        x.roverId,
        x.startPosition.startPosX,
        x.startPosition.startPosY,
        x.startPosition.direction
      )
    }
    recurThroughInstructions(inputData.instructions, inputData.gridSizeX, inputData.gridSizeY, roversFinishedMoving = Seq.empty[Rover], roversNotMoved = rovers)
  }

  @tailrec
  private def recurThroughInstructions(instructions: Seq[Instructions], maxX: Integer, maxY: Integer,
                                       roversFinishedMoving: Seq[Rover] = Seq.empty[Rover], roversNotMoved: Seq[Rover] = Seq.empty[Rover]): Seq[Rover] = {
    instructions match {
      case Nil =>
        roversFinishedMoving
      case stream =>
        val head = stream.head
        val tail = stream.tail
        val remainingUnmovedRovers = roversNotMoved.filterNot(r => head.roverId == r.id)
        val roverFinished = sendRoverCommands(head, maxX, maxY, roversFinishedMoving, remainingUnmovedRovers)
        recurThroughInstructions(tail, maxX, maxY, roversFinishedMoving ++ Seq(roverFinished), remainingUnmovedRovers)
    }
  }

  def sendRoverCommands(instructions: Instructions, maxX: Integer, maxY: Integer, existingRovers: Seq[Rover] = Seq.empty[Rover], unmovedRovers: Seq[Rover] = Seq.empty[Rover]) = {
    val rover = Rover(
      instructions.roverId,
      instructions.startPosition.startPosX,
      instructions.startPosition.startPosY,
      instructions.startPosition.direction
    )
    recurThroughMovement(rover, instructions.movements, maxX, maxY, existingRovers ++ unmovedRovers)
  }

  @tailrec
  private def recurThroughMovement(rover: Rover, movements: Seq[Movement], maxX: Integer, maxY: Integer, existingRovers: Seq[Rover]): Rover = {
    //    println(s"rover status ${rover.getStatus}")
    movements match {
      case Nil =>
//        println(s"Rover finished moving, ${rover.getStatus()}")
        rover
      case stream if stream.head != Movement.M =>
        val head = stream.head
        val tail = stream.tail
        recurThroughMovement(rover.turnRover(head), tail, maxX, maxY, existingRovers)
      case stream =>
        val tail = stream.tail
        if (!canMove(rover.moveRover(), maxX, maxY, existingRovers)) {
          println("Rover cannot move to next location, terminating")
          rover
        }
        else {
          recurThroughMovement(rover.moveRover(), tail, maxX, maxY, existingRovers)
        }
    }
  }

  private def canMove(rover: Rover, maxX: Integer, maxY: Integer, existingRovers: Seq[Rover]): Boolean = {
    rover match {
      case Rover(_, x, y, _) if (x < 0 || x > maxX) || (y < 0 || y > maxY) =>
        println("Rover trying to go outside grid")
        false
      case Rover(id, x, y, _) if existingRovers.exists(r => r.posX == x && r.posY == y) =>
        val otherRover = existingRovers.find(r => r.posX == x && r.posY == y).get
        println(s"Rover (id:$id) cannot displace existing rover (id:${otherRover.id}) at position x:$x, y:$y")
        false
      case _ => true
    }
  }

}
