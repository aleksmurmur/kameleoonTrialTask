package org.aleksmurmur.kameleoon.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;


public record UserCreateRequest(
        String name,
        @NotEmpty
        String email,
        @NotEmpty
        String password) {
}
