package com.example.dumbouniversity

import androidx.compose.runtime.mutableStateListOf

object EnrollRepository {
    val users = mutableStateListOf<User>()
    val students = mutableStateListOf<Student>()
    val documents = mutableStateListOf<Document>()
    val payments = mutableStateListOf<Payment>()
    val courses   = mutableStateListOf<Course>()
}