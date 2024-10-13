package base

object Main {

  def main(args: Array[String]): Unit = {
    // val album: Album = ???
    // val detailedAlbum: EverSoSlightlyMoreDetailedAlbum = ???

    // album.convertTo[EverSoSlightlyMoreDetailedAlbum] - fail
    // detailedAlbum.convertTo[Album]
    basic.test()
    intermediate.test()
  }
}
