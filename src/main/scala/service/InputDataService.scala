package service

import model.InputData

object InputDataService {

  def processCommands(commands: Seq[String]): Option[InputData] = {
    checkCommands(commands) match {
      case false => None
      case _ => Some(InputData.fromString(commands))
    }
  }

  def checkCommands(commands: Seq[String]): Boolean = {
    val gridSizeStr = commands.head

    if (!checkStringFormat("([1-9]+)\\s([1-9]+)", gridSizeStr)) {
      println(s"grid size format incorrect ($gridSizeStr)")
      return false
    }
    val ints = gridSizeStr.split(" ").map(_.toInt)
    val gridX = ints.head
    val gridY = ints(1)
    val gridSizeCorrect = ints.length==2
    val gridDataValid = gridSizeCorrect && InputServiceHelper.checkInputGrid(gridX, gridY)

    val commandsRawIterator = commands.tail.grouped(2)
    val roverDataValid = commandsRawIterator.forall { commandsRaw =>
      val startingDataRaw = commandsRaw.head
      val movementDataRaw = commandsRaw(1)
      checkRoverData(startingDataRaw, movementDataRaw, gridX, gridY)
    }

    val noOverlappingStartPositions = commandsRawIterator.map { commandsRaw =>
      val startingPosRegex = "([1-9]+)\\s([1-9]+)".r
      startingPosRegex.findFirstMatchIn(commandsRaw.head)
    }.toSet.count(_.nonEmpty)==commandsRawIterator.size

    gridDataValid && roverDataValid && noOverlappingStartPositions
  }

  private def checkRoverData(startingPosRaw: String, movementsRaw: String, gridX: Integer, gridY: Integer): Boolean = {
    if (!checkStartDataFormat(startingPosRaw)) {
      println(s"starting location format incorrect ($startingPosRaw)")
      return false
    }
    val startDataValid: Boolean = checkStartingPosition(startingPosRaw, gridX, gridY)

    if(!checkMovementDataFormat(movementsRaw)){
      println(s"movement format incorrect ($movementsRaw), terminating")
    }
    val firstMovements = movementsRaw.map(_.toString)
    val movementsValid = InputServiceHelper.checkMovementsFormat(firstMovements)

    startDataValid && movementsValid
  }

  private def checkStartingPosition(roverOneStartDataStr: String, gridX: Int, gridY: Int): Boolean = {
    val firstStartPosRaw = roverOneStartDataStr.split(" ")
    val startPosX = firstStartPosRaw.head.toInt
    val startPosY = firstStartPosRaw(1).toInt
    val directionStr = firstStartPosRaw(2)
    val startDataValid = InputServiceHelper.checkStartingPosition(gridX, gridY, startPosX, startPosY, directionStr)
    startDataValid
  }

  private def checkMovementDataFormat(rawString: String): Boolean = {
    checkStringFormat("[A-Z]+",rawString)
  }

  private def checkStartDataFormat(rawStr: String) = {
    checkStringFormat("([1-9]+)\\s([1-9]+)\\s([A-Z]+)", rawStr)
  }

  private def checkStringFormat(regex: String, rawString: String) = {
    regex.r.findFirstMatchIn(rawString) match {
      case None => false
      case _ => true
    }
  }

}
