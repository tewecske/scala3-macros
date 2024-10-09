package base

import scala.quoted.* // (1)

inline def logAST[T](inline expression: T) = ${ logASTImpl('expression) } // (2)

def logASTImpl[T: Type](expression: Expr[T])(using q: Quotes) : Expr[T]= { // (3)
  import quotes.reflect.* // (4)
  val term = expression.asTerm // (5)
  println(s"===========Tree of type ${Type.show}=========:") // (6)
  println()
  println(term.show(using Printer.TreeAnsiCode))  // (7)
  println()
  println(term.show(using Printer.TreeStructure)) // (8)
  println()
  println("===========================")
  expression // (9)
}
