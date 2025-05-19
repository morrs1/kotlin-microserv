package org.example.kotlinmicroorder.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.kotlinmicroorder.models.Product
import org.example.kotlinmicroorder.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Контроллер продуктов")
@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    fun getAll(): List<Product> {
        return productService.getAll()
    }

}