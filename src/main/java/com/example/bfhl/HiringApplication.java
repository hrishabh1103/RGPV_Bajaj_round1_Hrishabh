package com.example.bfhl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@SpringBootApplication
public class HiringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiringApplication.class, args);
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner runner(WebClient webClient) {
        return args -> {
            // 🔑 Your details
            String name = "Hrishabh Gupta";       // change
            String regNo = "0002CD221026";        // change
            String email = "hrishabh1104@gmail.com";  // change

            // Step 1: Call generateWebhook
            Map<String, Object> genResp = webClient.post()
                    .uri("https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("name", name, "regNo", regNo, "email", email))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            System.out.println("Response from generateWebhook: " + genResp);

            String webhook = genResp.get("webhook").toString();
            String accessToken = genResp.get("accessToken").toString();

            // Step 2: Your SQL query (Question 2)
            String finalQuery = "SELECT e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME, COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT FROM EMPLOYEE e JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID LEFT JOIN EMPLOYEE e2 ON e.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e.DOB GROUP BY e.EMP_ID, e.FIRST_NAME, e.LAST_NAME, d.DEPARTMENT_NAME ORDER BY e.EMP_ID DESC;";

            // Step 3: Submit SQL query
            Map<String, Object> submitResp = webClient.post()
                    .uri(webhook)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", accessToken) // if 401, change to "Bearer " + accessToken
                    .bodyValue(Map.of("finalQuery", finalQuery))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            System.out.println("Response from submit: " + submitResp);
        };
    }
}
