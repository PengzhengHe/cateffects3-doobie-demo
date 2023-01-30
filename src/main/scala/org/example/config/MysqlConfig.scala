package org.example.config

import cats.effect.{Concurrent, IO}
import cats.effect.kernel.Resource
import com.typesafe.config.{Config, ConfigFactory}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie.Transactor
import doobie.hikari.HikariTransactor
import doobie.implicits.*

import scala.concurrent.ExecutionContext.global

object MysqlConfig {

  private val config: Config = ConfigFactory.load()
  private val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl(config.getString("database.jdbcUrl"))
  hikariConfig.setDriverClassName(config.getString("database.driver"))
  hikariConfig.setUsername(config.getString("database.user"))
  hikariConfig.setPassword(config.getString("database.password"))
  hikariConfig.setMaximumPoolSize(config.getInt("database.maximumPoolSize"))

  // transactor with config
  val transactor: HikariTransactor[IO] = HikariTransactor.apply[IO](new HikariDataSource(hikariConfig), global)

  given HikariTransactor[IO] = transactor
}

