package org.aleksmurmur.kameleoon.quotes.dto;

import jakarta.validation.constraints.NotBlank;

public record QuoteCreateOrUpdateRequest(
        @NotBlank
        String content
) {
}
