package com.rstrazza.camunda.rest

import com.rstrazza.camunda.model.SampleMessageRequest
import com.rstrazza.camunda.model.SampleMessageResponse
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.ZeebeFuture
import io.camunda.zeebe.client.api.response.ProcessInstanceResult
import io.camunda.zeebe.spring.client.EnableZeebeClient
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@EnableZeebeClient
class HelloWorldController(@Autowired private val client: ZeebeClient) {

    private val HELLO_WORLD_BPMN_PROCESS_ID = "hello-world-process"

    @PutMapping("/hello-world")
    fun helloWorld(@RequestBody request: SampleMessageRequest): ResponseEntity<SampleMessageResponse> {
        logger.info("hello-world")

        val requestMap = mapOf<String, Any>("msg" to request.msg)

        val result: ZeebeFuture<ProcessInstanceResult> = client.newCreateInstanceCommand()
            .bpmnProcessId(HELLO_WORLD_BPMN_PROCESS_ID)
            .latestVersion()
            .variables(requestMap)
            .withResult() // wait for the workflow to finish
            .send() // with this we get a future
//            .join() // synchronous call (not a Future anymore)
//            .exceptionally() // throw exception on error

        val workflowResult = result.join()

        logger.info("Controllers result: $workflowResult.variables")

        val msg = workflowResult.variablesAsMap["newMsg"] ?: "not found!"

        var response = SampleMessageResponse(
            msg = msg.toString(),
            processInstanceKey = workflowResult.processInstanceKey.toString()
        )

        return ResponseEntity.ok(response)
    }
}