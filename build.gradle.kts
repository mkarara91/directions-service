import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.carsharing"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:2.5.5")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
	implementation("org.springframework.boot:spring-boot-starter-data-cassandra:2.5.5")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.junit.jupiter:junit-jupiter:5.8.1")
	testImplementation("org.springframework.boot:spring-boot-starter-validation:2.5.5")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.5")
	testImplementation("com.ninja-squad:springmockk:3.0.1")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.3")
	testImplementation("io.kotest:kotest-property-jvm:4.6.3")
	testImplementation("org.cassandraunit:cassandra-unit-spring:4.3.1.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
