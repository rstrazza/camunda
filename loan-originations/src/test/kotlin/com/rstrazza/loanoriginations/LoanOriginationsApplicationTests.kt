package com.rstrazza.loanoriginations

import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.spring.test.ZeebeSpringTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ZeebeSpringTest
class LoanOriginationsApplicationTests (
	@Autowired private val zeebe: ZeebeClient
) {

	@Test
	fun contextLoads() {
	}

}
