package org.example.kotlinmicroorder.services

import org.example.kotlinmicroorder.dao.ProductDao
import org.example.kotlinmicroorder.models.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductService {

    @Autowired
    private lateinit var productDao: ProductDao

    fun getAll(): List<Product> = productDao.getAll()

}