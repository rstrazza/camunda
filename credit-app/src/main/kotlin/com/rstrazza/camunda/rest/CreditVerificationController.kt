package com.rstrazza.camunda.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.rstrazza.camunda.model.CreditVerificationRequest
import com.rstrazza.camunda.model.CreditVerificationResults
import kotlinx.coroutines.*
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

    @OptIn(DelicateCoroutinesApi::class)
    @PostMapping("/verification")
    fun creditVerification(@RequestBody request: CreditVerificationRequest): ResponseEntity<String> {

        logger.info("credit-verification: ${objectMapper.writeValueAsString(request)}")

        // fire-and-forget global approach used for simplicity purposes
        // should only be used in prod for certain use cases and requires proper consideration
        GlobalScope.launch {
            verifyCredit(request)
        }

        return ResponseEntity.ok("ACK")
    }

    /**
     *
     */
    suspend fun verifyCredit(request: CreditVerificationRequest) {
        logger.info("verify-credit")

        // advanced credit score solution
        val creditScore = (0..850).random()

        val creditVerificationResults = CreditVerificationResults(
            request.loanApplicationId,
            creditScore
        )

        logger.info("wait some time...")
        delay(10000L)
        logger.info("done waiting!")

        val response = restTemplate.postForEntity(
            "$loanAppHostName/loan/credit/results",
            creditVerificationResults,
            String::class.java
        )

        logger.info("response: $response")
    }
}