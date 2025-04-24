package org.example.kotlinmicroorder.services

import org.example.kotlinmicroorder.dao.OrderDao
import org.example.kotlinmicroorder.dao.ProductsOrderDao
import org.example.kotlinmicroorder.dto.UsersOrder
import org.example.kotlinmicroorder.dto.UsersOrderRequest
import org.example.kotlinmicroorder.dto.UsersOrderResponse
import org.example.kotlinmicroorder.models.Order
import org.example.kotlinmicroorder.models.ProductsOrder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
@Transactional(readOnly = true)
class OrderService(val orderDao: OrderDao, val productsOrderDao: ProductsOrderDao) {

    fun getAll(): List<Order> = orderDao.getAll()

    @Transactional
    fun create(order: Order) {
        order.id = UUID.randomUUID()
        orderDao.create(order)
    }

    @Transactional
    fun create(order: UsersOrderRequest): UsersOrderResponse {
        val newOrder = UsersOrderResponse(
            order.usersId,
            order.usersEmail,
            UUID.randomUUID(),
            LocalDate.now(),
            "Создан",
            order.listOfProducts
        )
        orderDao.create(Order(newOrder.ordersId, newOrder.ordersDate, newOrder.ordersStatus, newOrder.usersId))
        newOrder.listOfProducts.forEach { product ->
            productsOrderDao.create(
                ProductsOrder(
                    UUID.randomUUID(),
                    newOrder.ordersId,
                    product.id,
                    1
                )
            )
        }

        return newOrder
    }

    fun getUsersOrder(orderId: UUID): UsersOrder? {
        return orderDao.getOrderWithUsersAndListOfProducts(orderId)
    }
}