package org.example.kotlinmicroorder.dao

import org.example.kotlinmicroorder.dto.UsersOrder
import org.example.kotlinmicroorder.models.Order
import org.example.kotlinmicroorder.models.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.LocalDate
import java.util.*


@Component
class OrderDao {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    fun getAll(): List<Order> = jdbcTemplate.query("select * from orders")
    { rs, _ ->
        Order(
            UUID.fromString(rs.getString("id")),
            rs.getDate("date").toLocalDate(),
            rs.getString("status"),
            UUID.fromString(rs.getString("user_id"))
        )
    }

    fun create(order: Order) = jdbcTemplate
        .update(
            "INSERT INTO orders (id, date, status, user_id) VALUES (?, ?, ?, ?)",
            order.id,
            order.date,
            order.status,
            order.userId
        )

    fun getOrderWithUsersAndListOfProducts(orderId: UUID): UsersOrder? {
        val sql = """
            SELECT 
                o.id AS orders_id,
                o.date AS orders_date,
                o.status AS orders_status,
                u.id AS users_id,
                u.email AS users_email,
                p.id AS product_id,
                p.name AS product_name,
                p.description AS product_description,
                p.price AS product_price
            FROM orders o
            JOIN users u ON o.user_id = u.id
            LEFT JOIN products_order po ON o.id = po.order_id
            LEFT JOIN products p ON po.products_id = p.id
            WHERE o.id = ?
        """.trimIndent()

        return jdbcTemplate.query(sql, OrderResultSetExtractor(), orderId)
    }

    private class OrderResultSetExtractor : ResultSetExtractor<UsersOrder?> {
        override fun extractData(rs: ResultSet): UsersOrder? {
            var ordersId: UUID? = null
            var ordersDate: LocalDate? = null
            var ordersStatus: String? = null
            var usersId: UUID? = null
            var usersEmail: String? = null
            val products = mutableListOf<Product>()

            if (!rs.next()) return null

            do {
                if (ordersId == null) {
                    ordersId = rs.getObject("orders_id", UUID::class.java)
                    ordersDate = rs.getDate("orders_date").toLocalDate()
                    ordersStatus = rs.getString("orders_status")
                    usersId = rs.getObject("users_id", UUID::class.java)
                    usersEmail = rs.getString("users_email")
                }

                rs.getObject("product_id", UUID::class.java)?.let {
                    Product(
                        id = it,
                        name = rs.getString("product_name"),
                        description = rs.getString("product_description"),
                        price = rs.getBigDecimal("product_price")
                    ).let(products::add)
                }
            } while (rs.next())

            return try {
                UsersOrder(
                    id = UUID.randomUUID(),
                    usersId = usersId!!,
                    usersEmail = usersEmail!!,
                    ordersId = ordersId!!,
                    ordersDate = ordersDate!!,
                    ordersStatus = ordersStatus!!,
                    listOfProducts = products.toList()
                )
            } catch (e: NullPointerException) {
                null
            }
        }
    }

}