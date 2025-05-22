package com.example.dumbouniversity

data class Student(val name: String, val number: String)

data class User(val username: String, val password: String, val role: Role)
enum class Role { STUDENT, ADMIN }

data class Document(val student: Student, val uri: String, var verified: Boolean = false)

data class Payment(val student: Student, val amount: Double, var paid: Boolean = false)
