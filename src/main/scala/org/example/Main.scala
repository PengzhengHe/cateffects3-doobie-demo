package org.example

import cats.data.Kleisli
import cats.effect.kernel.Resource.ExitCase
import cats.effect.{ExitCode, IO, IOApp, Resource}
import cats.syntax.all.*
import com.comcast.ip4s.*
import org.example.route.HelloWorldRoute
import org.http4s.ember.server.*
import org.http4s.implicits.*
import org.http4s.server.{Router, Server}
import org.http4s.{Request, Response}

import scala.concurrent.duration.*

object Main extends IOApp.Simple {


  private val httpApp =
    Router("/" -> HelloWorldRoute.helloWorldRoute).orNotFound

  private val server = EmberServerBuilder
    .default[IO]
    .withHost(ipv4"127.0.0.1")
    .withPort(port"8084")
    .withHttpApp(httpApp)
    .build

  val run: IO[Unit] = server.use(_ => IO.never).as(ExitCode.Success)
}

