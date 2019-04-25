package model

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

