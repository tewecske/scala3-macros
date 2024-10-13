package base

import scala.compiletime.*
import scala.deriving.Mirror
import java.time.Instant

object intermediate {

  extension [Source <: Product](self: Source) {
    inline def convertTo[Dest](using
        Source: Mirror.ProductOf[Source],
        Dest: Mirror.ProductOf[Dest]
    ): Dest = {
      type SourceFields =
        Field.Of[Source.MirroredElemLabels, Source.MirroredElemTypes]
      type DestFields =
        Field.Of[Dest.MirroredElemLabels, Dest.MirroredElemTypes]

      val erasedSource =
        self.productElementNames.zip(self.productIterator).toMap
      val transformers =
        summonAll[Field.FieldWiseTransformers[SourceFields, DestFields]].toList
          .asInstanceOf[List[Transformer[Any, Any]]]
      val destLabels = constValueTuple[Dest.MirroredElemLabels].toList
        .asInstanceOf[List[String]]

      val transformerMap = destLabels.zip(transformers).toMap

      Dest.fromProduct(
        Tuple.fromArray[Any](
          destLabels.map { label =>
            val sourceValue = erasedSource(label)
            val transformer = transformerMap(label)
            transformer.transform(sourceValue)
          }.toArray
        )
      )
    }
  }

  def test() = {
    val single: Single = Single("You", "Plates", 1384300801000L)
    val track: Track =
      Track("You", "Plates", Instant.ofEpochMilli(1384300801000L))

    given Transformer[Long, Instant] = Instant.ofEpochMilli
    given Transformer[Instant, Long] = _.toEpochMilli()

    val actual = single.convertTo[Track]
    val actualReverse = track.convertTo[Single]

    println(actual)
    println(actual == track)
    println(actualReverse)
    println(actualReverse == single)

  }

}
