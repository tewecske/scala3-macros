package base

import base.PainMacro.tryMe

object Main {
  case class ColDef[A](name: String)

  def main(args: Array[String]): Unit = {
    // logAST(println("Hi"))

    logAST {
      def printSomething(): Unit = ???
    }

    // tryMe()
  }

  // type CallArgs[Xs <: Tuple] <: Tuple = Xs match // (1)
  //   case EmptyTuple      => Xs // (2)
  //   case ColDef[b] *: xs => b *: CallArgs[xs] // (3)
  //
  // val cols = (ColDef[Int]("a"), ColDef[String]("b"))
  // val colsType: CallArgs[(ColDef[Int], ColDef[String])] =
  //   (1, "s")
}
