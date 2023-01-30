package org.example.model

import java.time.LocalDateTime

case class Card(
  id: Long,
  number: String,
  createAt: LocalDateTime,
  updateAt: LocalDateTime,
)
