package example

object TestMain {

  @Main
  object Test

  def main(args: Array[String]): Unit = {
    Test.main(args)
    TestAux.main(args)
  }
}
