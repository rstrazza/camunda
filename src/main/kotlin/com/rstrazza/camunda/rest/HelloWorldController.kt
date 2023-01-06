package com.rstrazza.camunda.rest

import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.spring.client.EnableZeebeClient
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/v1")
@EnableZeebeClient
class HelloWorldController(@Autowired private val client: ZeebeClient) {

    private val HELLO_WORLD_BPMN_PROCESS_ID = "hello-world-process"

    @PutMapping("/hello-world")
    fun helloWorld(): ResponseEntity<String> {
        logger.info("hello-world")
        
        val result = client.newCreateInstanceCommand()
            .bpmnProcessId(HELLO_WORLD_BPMN_PROCESS_ID)
            .latestVersion()
            .send()
            .join()
        
        return ResponseEntity.ok(result.processInstanceKey.toString())
    }
}