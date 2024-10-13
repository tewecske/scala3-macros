package base

import scala.quoted.*

inline def literals[A <: Tuple]: List[String] = ${ literalStrings[A] }

def literalStrings[A <: Tuple: Type](using Quotes): Expr[List[String]] = {
  def recurse[T <: Tuple: Type](using Quotes): List[String] = {
    import quotes.reflect.*
    Type.of[T] match {
      case '[type head <: String; head *: tail] =>
        val value = Type.valueOfConstant[head]
          .getOrElse(report.errorAndAbort("Not a literal String type"))
        value :: recurse[tail]
      case '[EmptyTuple] => Nil
    }
  }
  Expr.ofList(recurse[A].map(Expr.apply))
}

inline def literalsAbyss[A <: Tuple]: List[String] = ${ literalStringsAbyss[A] }

def literalStringsAbyss[A <: Tuple: Type](using Quotes): Expr[List[String]] = {
  import quotes.reflect.*
  def recurse(using Quotes)(tpe: quotes.reflect.TypeRepr): List[String] = {
    import quotes.reflect.*
    tpe match {
      case AppliedType(_, ConstantType(StringConstant(value)) :: tail :: Nil) =>
          value :: recurse(tail)
      case _ =>
        Nil
    }
  }
  Expr.ofList(recurse(TypeRepr.of[A]).map(Expr.apply))
}

