import model.{Direction, Movement, Rover}
import org.scalatest.{FlatSpec, Matchers}

class RoverSpec extends FlatSpec with Matchers {

  "turnRover" should "not turn if an incorrect movement is given" in {
    Rover(0, 1, 2, Direction.N).turnRover(Movement.M) shouldBe Rover(0, 1, 2, Direction.N)
  }

  it should "turn if a correct movement is given" in {
    Rover(0, 1, 2, Direction.N).turnRover(Movement.L) shouldBe Rover(0, 1, 2, Direction.W)
    Rover(0, 1, 2, Direction.W).turnRover(Movement.L) shouldBe Rover(0, 1, 2, Direction.S)
    Rover(0, 1, 2, Direction.S).turnRover(Movement.L) shouldBe Rover(0, 1, 2, Direction.E)
    Rover(0, 1, 2, Direction.E).turnRover(Movement.L) shouldBe Rover(0, 1, 2, Direction.N)
    Rover(0, 1, 2, Direction.N).turnRover(Movement.R) shouldBe Rover(0, 1, 2, Direction.E)
    Rover(0, 1, 2, Direction.E).turnRover(Movement.R) shouldBe Rover(0, 1, 2, Direction.S)
    Rover(0, 1, 2, Direction.S).turnRover(Movement.R) shouldBe Rover(0, 1, 2, Direction.W)
    Rover(0, 1, 2, Direction.W).turnRover(Movement.R) shouldBe Rover(0, 1, 2, Direction.N)
  }

  //We're not testing going into negatives here, or going beyond the grid bounds. That's
  //something for the service to worry about.
  "moveRover" should "correctly adjust the position" in {
    Rover(0, 3, 3, Direction.N).moveRover() shouldBe Rover(0, 3, 4, Direction.N)
    Rover(0, 3, 3, Direction.E).moveRover() shouldBe Rover(0, 4, 3, Direction.E)
    Rover(0, 3, 3, Direction.S).moveRover() shouldBe Rover(0, 3, 2, Direction.S)
    Rover(0, 3, 3, Direction.W).moveRover() shouldBe Rover(0, 2, 3, Direction.W)
  }

}
