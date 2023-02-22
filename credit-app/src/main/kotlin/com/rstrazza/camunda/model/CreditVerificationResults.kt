package com.rstrazza.camunda.model

data class CreditVerificationResults(
    val loanApplicationId: String,
    val creditScore: Int
)