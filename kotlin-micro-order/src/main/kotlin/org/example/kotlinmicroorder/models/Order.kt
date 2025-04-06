package org.example.kotlinmicroorder.models

import java.time.LocalDate
import java.util.UUID

data class Order(var id: UUID?, val date: LocalDate, val status: String, val userId: UUID)