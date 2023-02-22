package com.rstrazza.loanoriginations.model

data class LoanApplicationRequest (
    val name: String,
    val address: Address,
    val income: Int
)

data class Address (
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String
)
