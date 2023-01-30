package org.example.route

import cats.effect.*
import doobie.hikari.HikariTransactor
import doobie.implicits.*
import doobie.util.transactor.Transactor.Aux
import io.circe.Encoder
import org.example.config.MysqlConfig.given
import org.example.model.{Card, Country}
import org.example.route.CardApp.given
import org.example.route.CountryApp.given
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.example.config.PostgreConfig.given

import java.time.LocalDateTime


object HelloWorldRoute {

  val helloWorldRoute: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case GET -> Root / "hello" / name =>
        Ok(s"Hello. $name.")
      case GET -> Root / "card" / num =>
        CardApp.getCard(num).flatMap(Ok(_))
      case POST -> Root / "card" / num =>
        CardApp.addCard(num).flatMap(affectedRows => Ok(s"Affected rows: $affectedRows"))
      case GET -> Root / "country" / name =>
        CountryApp.find(name).flatMap(Ok(_))
      case GET -> Root / "cards" =>
        CardApp.getAllCards().flatMap(cards => Ok(s"There are ${cards} in total"))
    }
}

object CountryApp {
  given Encoder[Country] = Encoder.AsObject.derived[Country]

  given EntityEncoder[IO, Country] = jsonEncoderOf

  def find(n: String)(using xa: Aux[IO, Unit]): IO[Country] = {
    sql"SELECT code, name FROM country WHERE name = $n".query[Country].option.transact(xa) flatMap {
      case Some(country) => IO(country)
      case None => IO(Country("0", "undefined"))
    }
  }
}


object CardApp {

  given Encoder[Card] = Encoder.AsObject.derived[Card]

  given EntityEncoder[IO, Card] = jsonEncoderOf


  def getCard(num: String)(using transactor: HikariTransactor[IO]): IO[Card] = {
    sql"SELECT id, number, create_at, update_at FROM card WHERE number = $num".query[Card].option.transact(transactor) flatMap {
      case Some(card) => IO(card)
      case None => IO(Card(-1L, "0", LocalDateTime.now(), LocalDateTime.now()))
    }
  }

  def addCard(num: String)(using transactor: HikariTransactor[IO]): IO[Int] = {
    sql"insert into card(number) value($num)".update.run.transact(transactor)
  }

  def getAllCards()(using transactor: HikariTransactor[IO]): IO[Int] = {
    sql"select count(*) updateAt from card order by create_at"
      .query[Int].unique.transact(transactor)
  }
}
