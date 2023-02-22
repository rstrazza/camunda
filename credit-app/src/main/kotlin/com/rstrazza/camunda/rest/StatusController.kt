package com.rstrazza.camunda.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatusController() {

    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

}