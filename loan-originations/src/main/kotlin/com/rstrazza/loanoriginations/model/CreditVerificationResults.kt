package com.rstrazza.loanoriginations.model

data class CreditVerificationResults(
    val loanApplicationId: String,
    val creditScore: Int
)