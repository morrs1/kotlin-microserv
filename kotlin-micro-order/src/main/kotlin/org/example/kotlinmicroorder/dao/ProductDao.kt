package org.example.kotlinmicroorder.dao

import org.example.kotlinmicroorder.models.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProductDao {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    fun getAll(): List<Product> = jdbcTemplate
        .query("select * from products")
        { rs, _ ->
            Product(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price")
            )
        }

}