package base

import NamedTuple.*

final class Field[Name <: String, Type](val value: Name)

object Field {
  type Of[Labels <: Tuple, Types <: Tuple] =
    Tuple.Map[
      Tuple.Zip[Labels, Types],
      [a] =>> a match { case (label, tpe) => Field[label, tpe] }
    ]

  type FieldWiseTransformers[Source <: Tuple, Dest <: Tuple] =
    Tuple.Map[
      Dest,
      [a] =>> a match {
        case Field[label, tpe] =>
          Transformer[TypeOf[label, Source], tpe]
      }
    ]

  type TypeOf[Name <: String, Fields <: Tuple] =
    Fields match {
      case Field[Name, tpe] *: tail => tpe
      case head *: tail             => TypeOf[Name, tail]
    }

  type Remove[Name <: String, Fields <: Tuple] <: Tuple =
    Fields match {
      case EmptyTuple               => EmptyTuple
      case Field[Name, tpe] *: tail => Remove[Name, tail]
      case head *: tail             => head *: Remove[Name, tail]
    }

  // type Types[Fields <: Tuple] =
  //   Tuple.Map[Fields, ExtractType]

  type NamedOf[Names <: Tuple, Types <: Tuple] =
    NamedTuple[Names, Field.Of[Names, Types]]

  type TransformersOf[SourceFields <: Tuple, DestFields <: Tuple] =
    Tuple.Map[
      DestFields,
      [x] =>> x match {
        case Field[destName, destTpe] =>
          FieldTransformer[destName, TypeOf[destName, SourceFields], destTpe]
      }
    ]

  type FromPair[Pair] =
    Pair match {
      case (a, b) => Field[a, b]
    }

}

case class FieldTransformer[Name <: String, Source, Dest](
    name: Name,
    transformer: Transformer[Source, Dest]
)

object FieldTransformer {
  given derived[Name <: String, Source, Dest](using
      name: ValueOf[Name],
      transformer: Transformer[Source, Dest]
  ): FieldTransformer[Name, Source, Dest] =
    FieldTransformer(name.value, transformer)
}
