package com.example.wellness.data

data class UserInfo(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val sex: Sex = Sex.Man,
    val age: Int = 18,
) {
    fun toUserUiInfo(): UserUiInfo =
        UserUiInfo(
            this.name,
            this.sex,
            this.age
        )
}

data class UserUiInfo(
    val name: String,
    val sex: Sex,
    val age: Int
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

object AnonymousUser {
    val user = UserUiInfo(
        name = "Anonymous",
        sex = Sex.Man,
        age = 18
    )
}

object NotFoundUser {
    val user = UserUiInfo(
        name = "Not found",
        sex = Sex.Man,
        age = 18
    )
}