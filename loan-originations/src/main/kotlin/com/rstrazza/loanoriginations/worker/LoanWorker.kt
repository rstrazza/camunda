package com.rstrazza.loanoriginations.worker

import com.rstrazza.loanoriginations.model.LoanApplicationStatus
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.JobWorker
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger { }

@Component
@EnableZeebeClient
class LoanWorker() {

    companion object {
        // Objects
        const val LOAN_APPLICATION_STATUS = "loanApplicationStatus"
        const val LOAN_APPLICATION_ID = "loanApplicationId"
        const val CREDIT_SCORE = "creditScore"

        // Service Tasks
        const val CREATE_LOAN_APPLICATION_SERVICE_TASK = "create-loan-application"
        const val LOAN_TERMS_SERVICE_TASK = "loan-terms"
        const val INELIGIBLE_LOAN_SERVICE_TASK = "ineligible_loan"
    }

    @JobWorker(type = CREATE_LOAN_APPLICATION_SERVICE_TASK)
    fun createLoanApplication(job: ActivatedJob): Map<String, Any> {

        logger.info("$CREATE_LOAN_APPLICATION_SERVICE_TASK job-worker variables: $job.variables")

        // Create Loan Application
        val loanApplicationId = UUID.randomUUID().toString()

//        val response = LoanApplicationResponse(
//            loanApplicationId,
//            job.processInstanceKey,
//            creditScore,
//            LoanApplicationStatus.NEW
//        )

        return Collections.singletonMap(LOAN_APPLICATION_ID, loanApplicationId)
    }

    @JobWorker(type = LOAN_TERMS_SERVICE_TASK)
    fun approvedLoan(job: ActivatedJob): Map<String, Any> {
        logger.info("$LOAN_TERMS_SERVICE_TASK variables: ${job.variables}")

        return Collections.singletonMap(LOAN_APPLICATION_STATUS, LoanApplicationStatus.APPROVED)
    }

    @JobWorker(type = INELIGIBLE_LOAN_SERVICE_TASK)
    fun declinedLoan(job: ActivatedJob): Map<String, Any> {
        logger.info("$INELIGIBLE_LOAN_SERVICE_TASK variables: ${job.variables}")

        return Collections.singletonMap(LOAN_APPLICATION_STATUS, LoanApplicationStatus.DENIED)
    }

}