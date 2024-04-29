package com.springbootservicioitem.servicioitem;

import com.springbootservicioitem.servicioitem.clientes.CustomFeignConfiguration;
import feign.codec.ErrorDecoder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {
    @Bean("clienteRest")
    public RestTemplate registrarRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignConfiguration();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
        System.out.println("INICIA???¡¡¡¡¡¡¡¡¡¡¡¡¡");
        return factory -> factory.configureDefault(id -> {
            System.out.println("RESILENCE4J " + id);
            return new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .slidingWindowSize(10)
                            .failureRateThreshold(50)
                            .waitDurationInOpenState(Duration.ofSeconds(10L))
                            .build()
                    ).timeLimiterConfig(TimeLimiterConfig.ofDefaults())
                    .build();
        });
    }
}
