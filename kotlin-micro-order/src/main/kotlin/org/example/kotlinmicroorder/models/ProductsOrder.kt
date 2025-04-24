package org.example.kotlinmicroorder.models

import java.util.*

data class ProductsOrder(
    val id: UUID,
    val orderId: UUID,
    val productId: UUID,
    val amountOfProducts: Int
)
