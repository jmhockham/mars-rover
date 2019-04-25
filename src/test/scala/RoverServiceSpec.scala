import org.scalatest.{FlatSpec, Matchers}
import model._
import service.RoverService

class RoverServiceSpec extends FlatSpec with Matchers {

  val gridX = 5
  val gridY = 5
  /*
  Test Input:

  5 5
  1 2 N
  LMLMLMLMM
  3 3 E
  MMRMMRMRRM


  Expected Output:

  1 3 N
  5 1 E
   */

  //  1 2 N
  //  LMLMLMLMM
  val circularInstructions = Instructions(
    roverId = 0,
    startPosition = StartPosition(1, 2, Direction.N),
    movements = Seq(
      Movement.L,
      Movement.M,
      Movement.L,
      Movement.M,
      Movement.L,
      Movement.M,
      Movement.L,
      Movement.M,
      Movement.M
    )
  )

  //  3 3 E
  //  MMRMMRMRRM
  val backtrackingInstructions = Instructions(
    roverId = 1,
    startPosition = StartPosition(3, 3, Direction.E),
    movements = Seq(
      Movement.M,
      Movement.M,
      Movement.R,
      Movement.M,
      Movement.M,
      Movement.R,
      Movement.M,
      Movement.R,
      Movement.R,
      Movement.M
    )
  )

  "sendRoverCommands" should "correctly move a rover, if the commands are allowed" in {
    val forwardInstructions = Instructions(
      roverId = 3,
      startPosition = StartPosition(3, 3, Direction.N),
      movements = Seq(Movement.M, Movement.M)
    )
    val resultsOne = RoverService.sendRoverCommands(forwardInstructions, gridX, gridY)
    resultsOne shouldBe Rover(3, 3, 5, Direction.N)

    val snakingInstructions = forwardInstructions.copy(
      startPosition = forwardInstructions.startPosition,
      movements = Seq(
        Movement.M,
        Movement.L,
        Movement.M,
        Movement.R,
        Movement.M
      )
    )
    val resultsTwo = RoverService.sendRoverCommands(snakingInstructions, gridX, gridY)
    resultsTwo shouldBe Rover(3, 2, 5, Direction.N)

    val resultsThree = RoverService.sendRoverCommands(circularInstructions, gridX, gridY)
    resultsThree shouldBe Rover(0, 1, 3, Direction.N)

    val resultsFour = RoverService.sendRoverCommands(backtrackingInstructions, gridX, gridY)
    resultsFour shouldBe Rover(1, 5, 1, Direction.E)
  }

  it should "not allow a rover to go outside the grid" in {
    val forwardInstructionsOne = Instructions(
      roverId = 3,
      startPosition = StartPosition(4, 4, Direction.N),
      movements = Seq(Movement.M, Movement.M)
    )
    val resultsOne = RoverService.sendRoverCommands(forwardInstructionsOne, gridX, gridY)
    resultsOne shouldBe Rover(3, 4, 5, Direction.N)

    val forwardInstructionsTwo = Instructions(
      roverId = 3,
      startPosition = StartPosition(4, 4, Direction.E),
      movements = Seq(Movement.M, Movement.M)
    )
    val resultsTwo = RoverService.sendRoverCommands(forwardInstructionsTwo, gridX, gridY)
    resultsTwo shouldBe Rover(3, 5, 4, Direction.E)

    val forwardInstructionsThree = Instructions(
      roverId = 3,
      startPosition = StartPosition(1, 1, Direction.S),
      movements = Seq(Movement.M, Movement.M)
    )
    val resultsThree = RoverService.sendRoverCommands(forwardInstructionsThree, gridX, gridY)
    resultsThree shouldBe Rover(3, 1, 0, Direction.S)

    val forwardInstructionsFour = Instructions(
      roverId = 3,
      startPosition = StartPosition(1, 1, Direction.W),
      movements = Seq(Movement.M, Movement.M)
    )
    val resultsFour = RoverService.sendRoverCommands(forwardInstructionsFour, gridX, gridY)
    resultsFour shouldBe Rover(3, 0, 1, Direction.W)
  }

  it should "not allow a rover to displace/move through an existing rover" in {
    val forwardInstructions = Instructions(
      roverId = 3,
      startPosition = StartPosition(4, 4, Direction.N),
      movements = Seq(Movement.M, Movement.M)
    )
    val resultsOne = RoverService.sendRoverCommands(forwardInstructions, gridX, gridY, Seq(Rover(3, 4, 5, Direction.N)))
    resultsOne shouldBe Rover(3, 4, 4, Direction.N)
  }

  "processInstructions" should "process input data, and sequentially move rovers as they appear" in {
    val inputData = InputData(gridX, gridY, Seq(circularInstructions, backtrackingInstructions))
    val roversFinalPositions = RoverService.processInstructions(inputData)
    roversFinalPositions shouldBe Seq(Rover(0, 1, 3, Direction.N), Rover(1, 5, 1, Direction.E))
  }

}
