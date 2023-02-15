package org.aleksmurmur.kameleoon.common.dto;

public record ErrorResponse(
        String code,
        String message
) {
}
