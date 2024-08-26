package com.example.myapplication.data.model

import java.util.Date

data class Project(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var startDate: Date = Date(),
    var endDate: Date = Date(),
    var image: String? = null
)
