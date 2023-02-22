package com.rstrazza.loanoriginations.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()
}