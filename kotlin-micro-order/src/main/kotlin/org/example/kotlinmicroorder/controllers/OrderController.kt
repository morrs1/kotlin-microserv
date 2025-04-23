package org.example.kotlinmicroorder.controllers

import org.example.kotlinmicroorder.dto.UsersOrder
import org.example.kotlinmicroorder.dto.UsersOrderRequest
import org.example.kotlinmicroorder.dto.UsersOrderResponse
import org.example.kotlinmicroorder.models.Order
import org.example.kotlinmicroorder.services.OrderService
import org.example.kotlinmicroorder.utils.DataSender
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(private var orderService: OrderService, private var sender: DataSender) {

    @GetMapping
    fun getAll(): List<Order> = orderService.getAll()

    @PostMapping("/create")
    fun create(@RequestBody usersOrderRequest: UsersOrderRequest): UsersOrderResponse {
        val newOrder = orderService.create(usersOrderRequest)
        sender.send(
            UsersOrder(
                UUID.randomUUID(),
                newOrder.usersId,
                newOrder.usersEmail,
                newOrder.ordersId,
                newOrder.ordersDate,
                newOrder.ordersStatus,
                newOrder.listOfProducts
            )
        )
        return newOrder
    }

    @GetMapping("/test1")
    fun test1(): ResponseEntity<String> {
        println(orderService.getUsersOrder(UUID.fromString("16163114-2950-41b8-a389-07a2a43e7c8c")))
        return ResponseEntity.ok("123")
    }

}