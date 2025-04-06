package org.example.kotlinmicroorder.services

import org.example.kotlinmicroorder.dao.UserDao
import org.example.kotlinmicroorder.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService {
    @Autowired
    private lateinit var userDao: UserDao

    fun findAll(): List<User> {
        return userDao.findAll()
    }
}