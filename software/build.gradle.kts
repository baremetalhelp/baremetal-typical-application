plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "help.baremetal.service"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") // Just for annotations. This application definitely DOES NOT use JPA.
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.amazonaws.secretsmanager:aws-secretsmanager-jdbc:1.0.12")
	implementation("mysql:mysql-connector-java:8.0.30")
	implementation("com.h2database:h2:2.2.224")

	implementation("org.apache.commons:commons-lang3:3.14.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
