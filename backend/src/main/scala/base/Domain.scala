package base

import java.time.LocalDate
import java.time.Instant

case class Album(
    name: String,
    artist: String,
    releaseDate: LocalDate
)

case class EverSoSlightlyMoreDetailedAlbum(
    releaseDate: LocalDate,
    artist: String,
    name: String,
    numberOfTracks: Int,
    label: String
)

case class Single(
    name: String,
    album: String,
    releaseTimestamp: Long
)

case class Track(
    name: String,
    album: String,
    releaseTimestamp: Instant
)
