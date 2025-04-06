package org.example.kotlinmicroorder.dao

import org.example.kotlinmicroorder.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.util.*


@Component
class UserDao {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    fun findAll(): List<User> {
        val listOfUsers = jdbcTemplate.query(
            "SELECT id, email, password FROM users"
        ) { rs, _ ->
            User(
                UUID.fromString(rs.getString("id")),
                rs.getString("email"),
                rs.getString("password")
            )
        }

        return listOfUsers
    }
}