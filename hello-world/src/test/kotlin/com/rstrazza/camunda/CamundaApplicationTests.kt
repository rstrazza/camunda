package com.rstrazza.camunda

import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.spring.test.ZeebeSpringTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ZeebeSpringTest
class CamundaApplicationTests (
	@Autowired private val zeebe: ZeebeClient
) {

	@Test
	fun contextLoads() {
	}

}
