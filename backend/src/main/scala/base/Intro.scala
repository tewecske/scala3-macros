package base

import scala.compiletime.*
import scala.deriving.Mirror
import scala.language.experimental.namedTuples

object Intro {

  type ZipAndWrapInOption[A <: Tuple, B <: Tuple] =
    Tuple.Map[Tuple.Zip[A, B], [a] =>> Option[a]]

  type Result =
    ZipAndWrapInOption[
      (Int, String, Double),
      (List[Int], Map[Int, String], Float)
    ]

  inline def summoner() = {
    val summonValues =
      summonAll[Tuple.Map[("value1", "value2", "value3"), ValueOf]].toList
    val values = summonValues.map(_.value)
    println(s"values: ${values}")
  }

  val shortened = constValueTuple[("value1", "value2", "value3")].toList

  case class Example(int: Int, str: String, list: List[Int])

  val mirror = summon[Mirror.Of[Example]]
  // scala.deriving.Mirror.Product {
  //   type MirroredMonoType = Example
  //   type MirroredType = Example
  //   type MirroredLabel = "Example"
  //   type MirroredElemTypes = (int, String, List[Int])
  //   type MirroredElemLabels = ("int", "str", "list")

  val result = mirror.fromProduct((1, "str", List(1, 2, 3)))

  // val namedTuple = (int: Int, str: String, list: List[Int])
  val namedTuple = (int = 1, str = "str", list = List(1, 2, 3))

  object Selector extends Selectable {
    def selectDynamic(name: String): name.type = name
    type Fields = (int: Int, str: String, list: List[Int])
  }

  // println(s"Selector.int: ${Selector.int}")
  def intro(args: Array[String]): Unit = {
    println(s"Result: ${classOf[Result]}")
    summoner()
    println(s"shortened: $shortened")
    println(s"result: $result")
    println(s"namedTuple: ${namedTuple.int}")

    println(literals["one" *: "two" *: "three" *: EmptyTuple])
    println(literalsAbyss["one" *: "two" *: "three" *: EmptyTuple])
  }
}
