package com.rstrazza.loanoriginations.model

data class LoanApplicationResponse (
    var loadApplicationId: String,
    var processInstanceKey: Long,
    var creditScore: Int,
    var loanApplicationStatus: LoanApplicationStatus,
//    var loanApplicationRequest: LoanApplicationRequest?
)

enum class LoanApplicationStatus {
    NEW,
    UNDER_REVIEW,
    APPROVED,
    ACTIVE,
    INACTIVE,
    DENIED,
    CLOSED,
}