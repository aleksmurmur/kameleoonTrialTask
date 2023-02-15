package org.aleksmurmur.kameleoon.common.dto;

public record ValidationError(
        String fieldName,
        String description
) {
}
