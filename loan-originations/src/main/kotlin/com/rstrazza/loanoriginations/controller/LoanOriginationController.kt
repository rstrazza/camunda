package com.rstrazza.loanoriginations.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.rstrazza.loanoriginations.model.LoanApplicationRequest
import com.rstrazza.loanoriginations.model.LoanApplicationResponse
import com.rstrazza.loanoriginations.model.LoanApplicationStatus
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.response.ProcessInstanceResult
import io.camunda.zeebe.spring.client.EnableZeebeClient
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger { }

/**
 * TODO:
 *  - implement async endpoint that returns processInstanceKey for future queries
 *  - implement endpoint to query current process state
 */

@RestController
@RequestMapping("/loan/origination")
@EnableZeebeClient
class LoanOriginationController(
    @Autowired private val client: ZeebeClient,
    private val objectMapper: ObjectMapper = ObjectMapper()
) {

    companion object {
        const val LOAN_ORIGINATION_PROCESS = "loan_origination_process"
    }

    @PostMapping("/application")
    fun loanApplication(@RequestBody request: LoanApplicationRequest): ResponseEntity<LoanApplicationResponse> {
        logger.info("Loan Application Request: ${objectMapper.writeValueAsString(request)} ")

        val variableMap: MutableMap<String, Any> =
            objectMapper.convertValue(request, Map::class.java) as MutableMap<String, Any>

        // ZeebeFuture<ProcessInstanceResult>
        val result: ProcessInstanceResult = client.newCreateInstanceCommand()
            .bpmnProcessId(LOAN_ORIGINATION_PROCESS)
            .latestVersion()
            .variables(variableMap)
            .withResult() // wait for the workflow to finish
            .send() // with this we get a future
            .join() // synchronous call
//            .exceptionally() // throw exception on error

        logger.info("Loan Application Response: $result.variables")

        val response = LoanApplicationResponse(
            result.variablesAsMap["loanApplicationId"] as String,
            result.processInstanceKey,
            result.variablesAsMap["creditScore"] as Int,
            objectMapper.convertValue(
                result.variablesAsMap["loanApplicationStatus"],
                LoanApplicationStatus::class.java
            ),
        )

        return ResponseEntity.ok(response)
    }
}