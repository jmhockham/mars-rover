import model.{Direction, Movement}
import org.scalatest.{FlatSpec, Matchers}

class RoverEnumSpec extends FlatSpec with Matchers{

  "hasName" should "return false if the value isn't present" in {
    Direction.hasName("X") shouldBe false
    Movement.hasName("X") shouldBe false
  }

  it should "return true if the value is present" in {
    Direction.hasName("N") shouldBe true
    Direction.hasName("E") shouldBe true
    Direction.hasName("S") shouldBe true
    Direction.hasName("W") shouldBe true
    Movement.hasName("M") shouldBe true
    Movement.hasName("L") shouldBe true
    Movement.hasName("R") shouldBe true
  }

}
