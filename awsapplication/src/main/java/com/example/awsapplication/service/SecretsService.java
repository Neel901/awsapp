package com.example.awsapplication.service;

import com.example.awsapplication.DbSecret;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
@RequiredArgsConstructor
public class SecretsService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SecretsManagerClient client;

    public DbSecret getDbSecret(String secretName, String region) {
        GetSecretValueResponse response = client.getSecretValue(
                GetSecretValueRequest.builder().secretId(secretName).build()
        );

        try {
            return objectMapper.readValue(response.secretString(), DbSecret.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read secret", e);
        }
    }
}

