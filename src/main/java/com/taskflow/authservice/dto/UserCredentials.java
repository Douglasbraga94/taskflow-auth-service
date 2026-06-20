package com.taskflow.authservice.dto;

public record UserCredentials(Long id, String name, String email, String password) {
}
