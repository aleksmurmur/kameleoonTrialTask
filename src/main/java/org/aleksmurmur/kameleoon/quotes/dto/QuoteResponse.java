package org.aleksmurmur.kameleoon.quotes.dto;

import org.aleksmurmur.kameleoon.common.dto.NamedElement;
import org.aleksmurmur.kameleoon.user.domain.User;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public record QuoteResponse(
        UUID id,
        String content,
        NamedElement<UUID> userNamedElement,
        Integer score,
        Map<LocalDate, Integer> chart
        //TODO chart treemap??

) {
}
