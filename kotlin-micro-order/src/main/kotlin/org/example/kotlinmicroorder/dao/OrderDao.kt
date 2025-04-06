package org.example.kotlinmicroorder.dao

import org.example.kotlinmicroorder.models.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
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


}