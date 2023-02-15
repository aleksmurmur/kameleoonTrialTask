package org.aleksmurmur.kameleoon.common.dto;

public record NamedElement<T>(
        T id,
        String name
) {
}
