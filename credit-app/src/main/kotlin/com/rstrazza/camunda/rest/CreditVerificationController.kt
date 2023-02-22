package com.rstrazza.camunda.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.rstrazza.camunda.model.CreditVerificationRequest
import com.rstrazza.camunda.model.CreditVerificationResults
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/credit")
class CreditVerificationController(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    @Value("\${loan.app.hostname}") private val loanAppHostName: String
) {

    @PostMapping("/verification")
    fun creditVerification(@RequestBody request: CreditVerificationRequest): ResponseEntity<String> {

        logger.info("credit-verification: ${objectMapper.writeValueAsString(request)}")

        verifyCredit(request)

        return ResponseEntity.ok("ACK")
    }

    /**
     *
     */
    fun verifyCredit(request: CreditVerificationRequest) {

        logger.info("verify-credit")

        // advanced credit score solution
        val creditScore = (0..850).random()

        val creditVerificationResults = CreditVerificationResults(
            request.loanApplicationId,
            creditScore
        )

        val response = restTemplate.postForEntity(
            "$loanAppHostName/loan/credit/results",
            creditVerificationResults,
            String::class.java
        )

        logger.info("response: $response")
    }
}