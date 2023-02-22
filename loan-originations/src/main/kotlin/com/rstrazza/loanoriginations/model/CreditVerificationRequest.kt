package com.rstrazza.loanoriginations.model

data class CreditVerificationRequest(
    val loanApplicationId: String,
    val income: Int
)