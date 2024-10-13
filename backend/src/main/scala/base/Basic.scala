package base

import scala.compiletime.*
import scala.deriving.Mirror
import java.time.LocalDate
import scala.annotation.implicitNotFound

object basic {
  type Intersection[A <: Tuple] = Tuple.Fold[A, Any, [curr, acc] =>> curr & acc]

  type Source = Field["field1", Int] & Field["field2", String]
  type Dest = Field["field2", String] & Field["field1", Int]

  summon[Source <:< Dest]

  extension [Source <: Product](self: Source) {
    inline def convertTo[Dest](using
        Source: Mirror.ProductOf[Source],
        Dest: Mirror.ProductOf[Dest]
    )(using
        @implicitNotFound("${Source} not => ${Dest}") ev: Intersection[
          Field.Of[Source.MirroredElemLabels, Source.MirroredElemTypes]
        ] <:<
          Intersection[
            Field.Of[Dest.MirroredElemLabels, Dest.MirroredElemTypes]
          ]
    ): Dest = {
      val erasedSource =
        self.productElementNames.zip(self.productIterator).toMap
      val destLabels = constValueTuple[Dest.MirroredElemLabels].toList
        .asInstanceOf[List[String]]
      Dest.fromProduct(
        Tuple.fromArray[Any](
          destLabels.map(label => erasedSource(label)).toArray
        )
      )
    }
  }

  def test() = {
    val detailedAlbum =
      EverSoSlightlyMoreDetailedAlbum(
        releaseDate = LocalDate.of(2024, 9, 27),
        artist = "Xiu Xiu",
        name = "13'' Frank",
        numberOfTracks = 9,
        label = "Whitelabel"
      )
    val expected = Album(
      name = "13'' Frank",
      artist = "Xiu Xiu",
      releaseDate = LocalDate.of(2024, 9, 27)
    )

    val actual = detailedAlbum.convertTo[Album]

    println(actual)

    println(actual == expected)
  }
}
