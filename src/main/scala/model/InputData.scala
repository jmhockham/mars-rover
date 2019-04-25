package model

/**
  * Case class to hold the instructions for our rovers. We let it have a default grid size, so that it's easy to test
  * and reuse.
  * @param gridSizeX Grid size x axis value
  * @param gridSizeY Grid size y axis value
  * @param instructions The sequence of instructions to run
  */
case class InputData (
  gridSizeX: Integer = 5,
  gridSizeY: Integer = 5,
  instructions: Seq[Instructions],
)

object InputData {
  def fromString(strings: Seq[String]): InputData = {
    val gridLimits: Array[Int] = strings.head.split(" ").map(_.toInt)
    val commandsRaw: Seq[Seq[String]] = strings.tail.grouped(2).toSeq
    val instructions: Seq[Instructions] = commandsRaw.zipWithIndex.map { case (roverInstructionsRaw, index) =>
      Instructions.fromStrings(roverInstructionsRaw,index)
    }.map(x => x)
    new InputData(
      gridSizeX = gridLimits.head,
      gridSizeY = gridLimits(1),
      instructions = instructions
    )
  }

}

