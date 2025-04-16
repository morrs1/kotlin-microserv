package org.example.kotlinmicroorder.controllers

import org.example.kotlinmicroorder.dto.UsersOrder
import org.example.kotlinmicroorder.models.Order
import org.example.kotlinmicroorder.models.Product
import org.example.kotlinmicroorder.services.OrderService
import org.example.kotlinmicroorder.utils.DataSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController {

    @Autowired
    private lateinit var sender: DataSender

    @Autowired
    private lateinit var orderService: OrderService

    @GetMapping
    fun getAll(): List<Order> = orderService.getAll()

    @PostMapping("/create")
    fun create(@RequestBody order: Order) = orderService.create(order)

    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        sender.send(UsersOrder(UUID.randomUUID(), UUID.randomUUID(), "gg@gmail.com", UUID.randomUUID(), LocalDate.now(), "Готов", ArrayList<Product>()))
        return ResponseEntity.ok("123")
    }

    @GetMapping("/test1")
    fun test1(): ResponseEntity<String> {
        println(orderService.getUsersOrder(UUID.fromString("16163114-2950-41b8-a389-07a2a43e7c8c")))
        return ResponseEntity.ok("123")
    }

}