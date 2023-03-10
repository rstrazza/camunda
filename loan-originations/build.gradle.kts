import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.rstrazza"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val springZeebeVersion = "8.1.13"
val jacksonVersion = "2.14.+"

repositories {
	mavenCentral()
}

dependencies {
	// Use Spring Boot BOM?
	// implementation(platform("org.springframework.boot:spring-boot-dependencies:2.7.5")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// SpringBoot
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Web
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

	// Logging
	implementation("io.github.microutils:kotlin-logging:3.0.4")
	implementation("net.logstash.logback:logstash-logback-encoder:7.2")

	// Camunda
	implementation("io.camunda:spring-zeebe-starter:$springZeebeVersion")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.camunda:spring-zeebe-test:$springZeebeVersion")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
