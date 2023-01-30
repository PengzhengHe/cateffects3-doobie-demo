package org.example.config

import cats.effect.IO
import doobie.Transactor
import doobie.util.transactor.Transactor.Aux

object PostgreConfig {
  val aux: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql:finance", "username", ""
  )

  given xa: Aux[IO, Unit] = aux
}
