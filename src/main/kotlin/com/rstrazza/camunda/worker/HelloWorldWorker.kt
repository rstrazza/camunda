package com.rstrazza.camunda.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.JobWorker
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
@EnableZeebeClient
class HelloWorldWorker {

    @JobWorker(type = "hello-world")
    fun helloWorld(job: ActivatedJob): Map<String, Any> {
        logger.info("hello-world JobWorker: $job.variables")

        val msg = job.variablesAsMap["msg"] ?: "Nothing to say"

        return mapOf("newMsg" to "$msg From the Camunda Worker")
    }
}