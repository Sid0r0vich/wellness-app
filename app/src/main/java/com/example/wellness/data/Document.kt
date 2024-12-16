package com.example.wellness.data

import com.example.wellness.R
import java.time.LocalDate

data class Document(
    val nameId: Int,
    val date: LocalDate
)

object InitDocument {
    val document = Document(
        nameId = R.string.init_document,
        date = LocalDate.parse("2011-11-11")
    )
}