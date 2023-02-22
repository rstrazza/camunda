package com.rstrazza.loanoriginations.credit

import com.fasterxml.jackson.databind.ObjectMapper
import com.rstrazza.loanoriginations.model.CreditVerificationRequest
import com.rstrazza.loanoriginations.model.CreditVerificationResults
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.JobWorker
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/loan/credit")
@EnableZeebeClient
class CreditVerificationService(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    @Autowired private val client: ZeebeClient,
    @Value("\${credit.app.hostname}") private val creditAppHostName: String
) {

    companion object {
        const val SEND_CREDIT_VERIFICATION_TASK = "send-credit-verification"
        const val CREDIT_VERIFICATION_RECEIVED_TASK = "credit-verification-received"
    }

    @JobWorker(type = SEND_CREDIT_VERIFICATION_TASK)
    fun creditVerification(job: ActivatedJob) {
        logger.info("$SEND_CREDIT_VERIFICATION_TASK variables: ${job.variables}")

        val creditVerificationRequest = CreditVerificationRequest(
            job.variablesAsMap["loanApplicationId"] as String,
            job.variablesAsMap["income"] as Int
        )

        val response = restTemplate.postForEntity(
            "$creditAppHostName/credit/verification",
            creditVerificationRequest,
            String::class.java
        )

        logger.info("Response: $response")
    }

    @PostMapping("/results")
    fun creditVerificationResults(@RequestBody request: CreditVerificationResults): ResponseEntity<String> {
        logger.info("Credit Verification Response: ${objectMapper.writeValueAsString(request)} ")

        val variableMap: MutableMap<String, Any> =
            objectMapper.convertValue(request, Map::class.java) as MutableMap<String, Any>

        val sendCommand = client.newPublishMessageCommand()
            .messageName(CREDIT_VERIFICATION_RECEIVED_TASK)
            .correlationKey(request.loanApplicationId)
            .variables(variableMap)
            .send();

        return ResponseEntity.ok("ACK")
    }

}