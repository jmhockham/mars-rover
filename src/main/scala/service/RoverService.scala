package service

import model.Movement.Movement
import model._

import scala.annotation.tailrec

object RoverService {

  /**
    * Processes the instructions from the input data. We also send along the other rovers that aren't moving at the
    * moment, so that our currently moving rover can make sure it doesn't bump into them.
    * @param inputData The case class containing the instructions
    * @return A Seq of rovers that have finished moving.
    */
  def processInstructions(inputData: InputData): Seq[Rover] = {
    val rovers = inputData.instructions.map { x =>
      Rover(
        x.roverId,
        x.startPosition.startPosX,
        x.startPosition.startPosY,
        x.startPosition.direction
      )
    }
    for(
      i <- inputData.instructions
    ) yield {
      val otherRovers = rovers.filterNot(r => i.roverId==r.id)
      sendRoverCommands(i, inputData.gridSizeX, inputData.gridSizeY, otherRovers)
    }
  }

  /**
    * Sends instructions to a rover, passing along the grid size, and the lists of rovers that have/haven't finished moving
    * @param instructions Instructions for a rover
    * @param maxX Grid maximum x axis value
    * @param maxY Grid mximum y axis value
    * @return A rover that has finished moving
    */
  def sendRoverCommands(instructions: Instructions, maxX: Integer, maxY: Integer, otherRovers: Seq[Rover] = Seq.empty[Rover]): Rover = {
    val rover = Rover(
      instructions.roverId,
      instructions.startPosition.startPosX,
      instructions.startPosition.startPosY,
      instructions.startPosition.direction
    )
    recurThroughMovement(rover, instructions.movements, maxX, maxY, otherRovers)
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
