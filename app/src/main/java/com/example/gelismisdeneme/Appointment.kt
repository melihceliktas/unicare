package com.example.gelismisdeneme

data class Appointment(
    val id: String = "",
    val userId: String = "",
    val date: String = "",
    val time: String = "",
    val reason: String = "",
    val createdAt: Long = System.currentTimeMillis()
) 