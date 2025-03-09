package com.example.demo.config

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenTelemetryConfig {

    @Bean
    fun otelTracer(): Tracer {
        // Экспортёр будет слушать на локальном OTel Collector (если поднимем его в Docker/K8s).
        val exporter = OtlpGrpcSpanExporter.builder()
            .setEndpoint("http://localhost:4317") // или URL вашего OTel Collector
            .build()

        val tracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(
                BatchSpanProcessor.builder(exporter).build()
            )
            .setResource(
                Resource.getDefault()
            )
            .build()

        val openTelemetry = OpenTelemetrySdk.builder()
            .setTracerProvider(tracerProvider)
            .buildAndRegisterGlobal()

        // Получаем глобальный трейсер
        return GlobalOpenTelemetry.getTracer("demo-tracer")
    }
}
