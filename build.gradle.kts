plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot starter
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // JPA / Hibernate
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // PostgreSQL driver
    runtimeOnly("org.postgresql:postgresql:42.5.4")

    // Для метрик (Micrometer + Prometheus)
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Logback (обычно включён по умолчанию) или другой логгер
    // Для интеграции c Loki через Docker-лог драйвер, чаще всего достаточно стандартных логов

    // OpenTelemetry (для дополнительного трейсинга)
//    implementation(platform("io.opentelemetry:opentelemetry-bom:1.32.0"))
    implementation("io.opentelemetry:opentelemetry-api")
    implementation("io.opentelemetry:opentelemetry-sdk")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    archiveBaseName.set("demo-app")
}

tasks.register<Exec>("dockerBuild") {
    commandLine("docker", "build", "-t", "kotlin-app:latest", ".")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}