import org.scalatest.{FlatSpec, Matchers}
import service.InputServiceHelper

class InputServiceHelperSpec extends FlatSpec with Matchers {

  "checkInputGrid" should "check the grid size is within the correct boundaries" in {
    InputServiceHelper.checkInputGrid(4,5) shouldBe false
    InputServiceHelper.checkInputGrid(5,4) shouldBe false
    InputServiceHelper.checkInputGrid(0,0) shouldBe false
    InputServiceHelper.checkInputGrid(5, 5) shouldBe true
  }

  "checkStartingPosition" should "return false if incorrect arguments are supplied" in {
    InputServiceHelper.checkStartingPosition(5,5,6,5,"N") shouldBe false
    InputServiceHelper.checkStartingPosition(5,5,5,6,"N") shouldBe false
    InputServiceHelper.checkStartingPosition(5,5,0,5,"N") shouldBe false
    InputServiceHelper.checkStartingPosition(5,5,5,0,"N") shouldBe false
    InputServiceHelper.checkStartingPosition(5,5,5,5,"X") shouldBe false
  }

  it should "return true if correct arguments are supplied" in {
    InputServiceHelper.checkStartingPosition(5,5,5,5,"N") shouldBe true
    InputServiceHelper.checkStartingPosition(5,5,1,1,"E") shouldBe true
    InputServiceHelper.checkStartingPosition(5,5,5,1,"S") shouldBe true
    InputServiceHelper.checkStartingPosition(5,5,1,5,"W") shouldBe true
  }

}
