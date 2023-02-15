package org.aleksmurmur.kameleoon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponse(
        UUID id,
        String name,
        String email,
        LocalDateTime dateOfCreation) {
}
