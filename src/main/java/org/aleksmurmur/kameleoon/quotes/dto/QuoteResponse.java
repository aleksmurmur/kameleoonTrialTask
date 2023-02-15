package org.aleksmurmur.kameleoon.quotes.dto;

import org.aleksmurmur.kameleoon.common.dto.NamedElement;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.UUID;

public record QuoteResponse(
        UUID id,
        String content,
        NamedElement<UUID> userNamedElement,
        Integer score,
        TreeMap<LocalDateTime, Integer> voteChart

) {
}
