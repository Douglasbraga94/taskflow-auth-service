package com.taskflow.authservice.service;

import com.taskflow.authservice.dto.UserCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    private final RestClient restClient;
    private final String userServiceUrl;
    public UserServiceClient(RestClient restClient,
                             @Value("${services.user-service.url}") String userServiceUrl) {
        this.restClient = restClient;
        this.userServiceUrl = userServiceUrl;
    }

    public UserCredentials findByEmail(String email) {
        return restClient.get()
                .uri(userServiceUrl + "/internal/users/by-email?email={email}", email)
                .retrieve()
                .body(UserCredentials.class);
    }
}
