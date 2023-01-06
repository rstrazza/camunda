package com.rstrazza.camunda.config

import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.Deployment
import org.springframework.context.annotation.Configuration

@Configuration
@EnableZeebeClient
@Deployment(resources = ["classpath:camunda/**"])
class CamundaConfig {

}