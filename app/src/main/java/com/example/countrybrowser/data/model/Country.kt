package com.example.countrybrowser.data.model

data class Country(
    val name: Name,
    val capital: List<String>?,
    val region: String,
    val population: Long,
    val area: Double?,
    val flags: Flags,
    val languages: Map<String, String>?
)

data class Name(
    val common: String,
    val official: String
)

data class Flags(
    val png: String,
    val svg: String
)