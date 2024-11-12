package com.example.wellness.data

import com.example.wellness.auth.Sex

data class UserInfo(
    val name: String,
    val email: String,
    val password: String,
    val sex: Sex,
    val age: Int,
)

object MockUser {
    val user = UserInfo(
        name = "Sharikov",
        email = "sharicov@mail.ru",
        password = "qwerty",
        sex = Sex.Man,
        age = 45
    )
}