package model

trait RoverEnum extends Enumeration {
  def hasName(facing: String):Boolean = {
    val directions: Set[String] = this.values.map(_.toString)
    directions.contains(facing)
  }
}
