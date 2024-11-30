package com.example.wellness.auth.draft

import com.example.wellness.data.Sex

data class RegisterDraft(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val sex: Sex = Sex.Man,
    val age: Int = 18
)