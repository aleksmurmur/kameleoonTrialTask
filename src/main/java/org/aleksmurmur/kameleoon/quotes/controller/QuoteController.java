package org.aleksmurmur.kameleoon.quotes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.aleksmurmur.kameleoon.quotes.domain.VoteType;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteCreateOrUpdateRequest;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteResponse;
import org.aleksmurmur.kameleoon.quotes.service.QuoteService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.aleksmurmur.kameleoon.api.Paths.QUOTES_V1_PATH;

@RestController
@RequestMapping(QUOTES_V1_PATH)
@Tag(name = "Quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping
    @Operation(summary = "Creating quote")
    public ResponseEntity<QuoteResponse> create(@RequestBody @Valid QuoteCreateOrUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quoteService.create(request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updating quote")
    public ResponseEntity<QuoteResponse> update(@PathVariable UUID id, @RequestBody @Valid QuoteCreateOrUpdateRequest request) {
        return ResponseEntity.ok(quoteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting quote")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        quoteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Getting quote by id")
    public ResponseEntity<QuoteResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(quoteService.getById(id));
    }

    @GetMapping("/random")
    @Operation(summary = "Getting random quote")
    public ResponseEntity<QuoteResponse> getRandom() {
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }

    @GetMapping
    @Operation(summary = "Getting page of quotes (from new to old)")
    public ResponseEntity<Page<QuoteResponse>> getPage(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(quoteService.getPage(pageable));
    }


    @GetMapping("/best")
    @Operation(summary = "Getting 10 best quotes")
    public ResponseEntity<List<QuoteResponse>> getBest() {
        return ResponseEntity.ok(quoteService.getBest10());
    }

    @GetMapping("/worst")
    @Operation(summary = "Getting 10 worst quotes")
    public ResponseEntity<List<QuoteResponse>> getWorst() {
        return ResponseEntity.ok(quoteService.getWorst10());
    }

    @PutMapping("/{quoteId}/vote")
    @Operation(summary = "Voting for quote")
    public ResponseEntity<QuoteResponse> vote(@PathVariable UUID quoteId, @RequestParam VoteType vote) {
        return ResponseEntity.ok(quoteService.vote(quoteId, vote));
    }
}
