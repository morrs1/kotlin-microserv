package org.example.kotlinmicroorder.controllers

import org.example.kotlinmicroorder.models.Product
import org.example.kotlinmicroorder.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @GetMapping
    fun getAll(): List<Product> {
        return productService.getAll()
    }

}