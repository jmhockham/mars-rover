import model.{InputData, Rover}
import service.{InputDataService, RoverService}

import scala.io.Source
import scala.io.Source._

object MarsMain extends App {

  //  println("Hello! Starting rover input")
  //  println("Commands are as follows:")
  private val commands: Seq[String] = if (args.length == 0) {
    fromResource("input.txt").getLines().toSeq
  } else {
    Source.fromFile(args(0)).getLines().toSeq
  }
  //  commands.foreach(println)

  //  println("Running rover commands...")
  private val rovers: Seq[Rover] = InputDataService.processCommands(commands) match {
    case None => Seq.empty[Rover]
    case Some(inputData) => RoverService.processInstructions(inputData)
  }
  //  println("Rovers status: ")
  rovers.foreach(r => println(r.getStatusSimple()))

}
