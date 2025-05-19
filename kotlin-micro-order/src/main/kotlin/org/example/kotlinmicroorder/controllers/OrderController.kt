package org.example.kotlinmicroorder.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.kotlinmicroorder.dto.UsersOrder
import org.example.kotlinmicroorder.dto.UsersOrderRequest
import org.example.kotlinmicroorder.dto.UsersOrderResponse
import org.example.kotlinmicroorder.models.Order
import org.example.kotlinmicroorder.services.OrderService
import org.example.kotlinmicroorder.utils.DataSender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name=" Контроллер заказов")
@RestController
@RequestMapping("/orders")
class OrderController(private var orderService: OrderService, private var sender: DataSender) {

    private val log: Logger = LoggerFactory.getLogger(OrderController::class.java)

    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns all orders")
    fun getAll(): List<Order> = orderService.getAll()

    @PostMapping("/create")
    @Operation(summary = "Create an order", description = "Creates a new order and returns new order")
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
        log.info("New order with id: ${newOrder.ordersId} created successfully")
        return newOrder
    }

//    @GetMapping("/test1")
//    fun test1(): ResponseEntity<String> {
//        println(orderService.getUsersOrder(UUID.fromString("16163114-2950-41b8-a389-07a2a43e7c8c")))
//        return ResponseEntity.ok("123")
//    }

}