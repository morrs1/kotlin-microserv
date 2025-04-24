package org.example.kotlinmicroorder.dao

import org.example.kotlinmicroorder.models.ProductsOrder
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class ProductsOrderDao(private val jdbcTemplate: JdbcTemplate) {

    @Transactional
    fun create(productsOrder: ProductsOrder) {
        jdbcTemplate.update(
            "INSERT INTO products_order (id, order_id, products_id, amount_of_products) VALUES (?, ?, ?, ?)",
            productsOrder.id,
            productsOrder.orderId,
            productsOrder.productId,
            productsOrder.amountOfProducts
        )
    }

}