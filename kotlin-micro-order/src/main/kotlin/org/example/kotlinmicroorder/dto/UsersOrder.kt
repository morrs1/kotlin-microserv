package org.example.kotlinmicroorder.dto

import org.example.kotlinmicroorder.models.Product
import java.time.LocalDate
import java.util.*

data class UsersOrder(
    val id: UUID,
    val usersId: UUID,
    val usersEmail: String,
    val ordersId: UUID,
    val ordersDate: LocalDate,
    val ordersStatus: String,
    val listOfProducts: List<Product>
)