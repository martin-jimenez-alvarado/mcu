package com.collaborators.config;

import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
@EnableWebFlux
@EnableScheduling
@EnableReactiveMongoRepositories("com.collaborators.repository")
public class Config {

	@Bean
	public WebClient.Builder webClient() {
		return WebClient.builder();
	}
	
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
	    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
	            .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
	            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(2100)).build()).build());
	}
}
