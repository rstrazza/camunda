package com.rstrazza.camunda.model

data class CreditVerificationRequest(
    val loanApplicationId: String,
    val income: Int
)