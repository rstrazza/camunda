package com.rstrazza.camunda.rest

import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.spring.client.EnableZeebeClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableZeebeClient
class StatusController(@Autowired private val client: ZeebeClient) {

    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

    @GetMapping("/camunda/status")
    fun status(): ResponseEntity<String> {
        val topology = client.newTopologyRequest().send().join()
        return ResponseEntity.ok(topology.toString())
    }
}