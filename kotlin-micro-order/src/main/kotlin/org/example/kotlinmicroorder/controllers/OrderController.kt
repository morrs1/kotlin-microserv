package org.example.kotlinmicroorder.controllers

import org.example.kotlinmicroorder.models.Order
import org.example.kotlinmicroorder.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController {

    @Autowired
    private lateinit var orderService: OrderService

    @GetMapping
    fun getAll(): List<Order> = orderService.getAll()

    @PostMapping("/create")
    fun create(@RequestBody order: Order) = orderService.create(order)
}