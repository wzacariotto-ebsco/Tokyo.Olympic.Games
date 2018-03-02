package com.tokyoolympicgames.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
@EnableEurekaClient
@EnableCircuitBreaker
public class TokyoOlympicGamesApplication {

    public static void main(String[] args) {

        SpringApplication.run(TokyoOlympicGamesApplication.class, args);
    }
}
