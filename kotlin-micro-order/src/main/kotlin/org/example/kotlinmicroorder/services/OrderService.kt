package org.example.kotlinmicroorder.services

import org.example.kotlinmicroorder.dao.OrderDao
import org.example.kotlinmicroorder.models.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class OrderService {
    @Autowired
    private lateinit var orderDao: OrderDao

    fun getAll(): List<Order> = orderDao.getAll()

    @Transactional
    fun create(order: Order) {
        order.id = UUID.randomUUID()
        orderDao.create(order)
    }
}