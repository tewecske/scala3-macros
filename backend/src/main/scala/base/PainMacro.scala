package base
import scala.quoted.*
object PainMacro {

  inline def tryMe(): Unit = ${ tryMeImpl() }

  def tryMeImpl()(using q: Quotes): Expr[Unit] = {
    import q.reflect.*

    val pln = TermRef(TypeRepr.of[Predef.type], "println")
    val fnPrintln = Symbol.requiredMethod("println")
    val term =
      Apply(Ident(fnPrintln.termRef), List(Literal(StringConstant("Hello"))))
    // val term =
    //   Apply(Ident(pln), List(Literal(StringConstant("Hello"))))
    // val term =
    //   Apply(Ident("println"), List(Literal(StringConstant("Hello"))))

    '{ () }
  }

}
