import org.scalatest.{FlatSpec, Matchers}
import service.InputDataService

class InputDataServiceSpec extends FlatSpec with Matchers{

  "checkCommands" should "return true from a correct input string" in {
    val stringSeq = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM".split("\n")
    InputDataService.checkCommands(stringSeq) shouldBe true

    val stringSeqTwo = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM\n5 4 E\nLLRRMM".split("\n")
    InputDataService.checkCommands(stringSeqTwo) shouldBe true

    val stringSeqThree = "5 5\n1 2 N\nM".split("\n")
    InputDataService.checkCommands(stringSeqThree) shouldBe true
  }

  it should "return false for an incorrect input string" in {
    val stringSeq = "5 5\n1 2 N\nLMLMLMLMM\nX X E\nMMRMMRMRRM".split("\n")
    InputDataService.checkCommands(stringSeq) shouldBe false

    val stringSeqTwo = "0 0\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM".split("\n")
    InputDataService.checkCommands(stringSeqTwo) shouldBe false

    val stringSeqThree = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\n123123123".split("\n")
    InputDataService.checkCommands(stringSeqThree) shouldBe false

    val stringSeqFour = "5 5 5 5\n1 2 N\nLMLMLMLMM".split("\n")
    InputDataService.checkCommands(stringSeqFour) shouldBe false
  }

}
