package org.example.kotlinmicroorder.utils

import org.example.kotlinmicroorder.dto.UsersOrder

interface DataSender {
    fun send(usersOrder: UsersOrder)
}