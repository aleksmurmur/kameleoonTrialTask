package org.aleksmurmur.kameleoon.quotes.controller;

import jakarta.validation.Valid;
import org.aleksmurmur.kameleoon.quotes.domain.VoteType;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteCreateOrUpdateRequest;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteResponse;
import org.aleksmurmur.kameleoon.quotes.service.QuoteService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.aleksmurmur.kameleoon.api.Paths.QUOTES_V1_PATH;

@RestController
@RequestMapping(QUOTES_V1_PATH)
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> create(@RequestBody @Valid QuoteCreateOrUpdateRequest request) {
        return ResponseEntity.ok(quoteService.create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuoteResponse> update(@PathVariable UUID id, @RequestBody @Valid QuoteCreateOrUpdateRequest request) {
        return ResponseEntity.ok(quoteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        quoteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(quoteService.getById(id));
    }

    @GetMapping("/random")
    public ResponseEntity<QuoteResponse> getRandom() {
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }

    @GetMapping
    public ResponseEntity<Page<QuoteResponse>> getPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(quoteService.getPage(pageable));
    }

    @GetMapping("/best")
    public ResponseEntity<List<QuoteResponse>> getBest() {
        return ResponseEntity.ok(quoteService.getBest10());
    }

    @GetMapping("/worst")
    public ResponseEntity<List<QuoteResponse>> getWorst() {
        return ResponseEntity.ok(quoteService.getWorst10());
    }

    @PutMapping("/{quoteId}/vote")
    public ResponseEntity<QuoteResponse> vote(@PathVariable UUID quoteId, @RequestParam VoteType vote) {
        return ResponseEntity.ok(quoteService.vote(quoteId, vote));
    }
}
