package org.example.kotlinmicroorder.dto

import lombok.Data
import org.example.kotlinmicroorder.models.Product
import java.time.LocalDate
import java.util.*

data class UsersOrderRequest(
    val usersId: UUID,
    val usersEmail: String,
    val listOfProducts: List<Product>
)