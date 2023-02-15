package org.aleksmurmur.kameleoon.quotes.service;

import org.aleksmurmur.kameleoon.common.dto.NamedElement;
import org.aleksmurmur.kameleoon.exception.BadRequestException;
import org.aleksmurmur.kameleoon.exception.EntityNotFoundException;
import org.aleksmurmur.kameleoon.quotes.domain.Quote;
import org.aleksmurmur.kameleoon.quotes.domain.Vote;
import org.aleksmurmur.kameleoon.quotes.domain.VoteType;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteCreateOrUpdateRequest;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteResponse;
import org.aleksmurmur.kameleoon.quotes.repository.QuoteRepository;
import org.aleksmurmur.kameleoon.quotes.repository.VoteRepository;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.aleksmurmur.kameleoon.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public QuoteService(QuoteRepository quoteRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.quoteRepository = quoteRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public QuoteResponse create(QuoteCreateOrUpdateRequest request) {
        User user = getRandomUser();
        Quote createdQuote = quoteRepository.save(convertToEntity(request, user));
        return convertToResponse(createdQuote);
    }

    @Transactional
    public QuoteResponse update(UUID quoteId, QuoteCreateOrUpdateRequest request) {
        User user = getRandomUser();
        Quote quote = quoteRepository.findByIdAndUserId(quoteId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Quote was not found by id %s", quoteId)));
        quote = quoteRepository.save(updateEntity(request, quote));
        return convertToResponse(quote);
    }


    @Transactional
    public void delete(UUID id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Quote was not found by id %s", id)));
        quoteRepository.delete(quote);
    }

    @Transactional(readOnly = true)
    public QuoteResponse getById(UUID id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Quote was not found by id %s", id)));
        return convertToResponse(quote);
    }

    @Transactional(readOnly = true)
    public QuoteResponse getRandomQuote() {
        Quote quote = quoteRepository.findRandom()
                .orElseThrow(() -> new EntityNotFoundException("No quotes in DB"));
        return convertToResponse(quote);
    }

    @Transactional(readOnly = true)
    public Page<QuoteResponse> getPage(Pageable pageable) {
        return quoteRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> getBest10() {
        return quoteRepository.findFirst10ByOrderByScoreDesc()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> getWorst10() {
        return quoteRepository.findFirst10ByOrderByScoreAsc()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public QuoteResponse vote(UUID quoteId, VoteType voteType) {
        User user = getRandomUser();
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Quote was not found by id %s", quoteId)));

        if (voteRepository.findByQuoteAndUser(quote, user).isPresent()) {
            throw new BadRequestException(String.format("User %s had already voted for quote %s", user.getId(), quote.getId()));
        }
        switch (voteType) {
            case UPVOTE -> quote.setScore(quote.getScore() + 1);
            case DOWNVOTE -> quote.setScore(quote.getScore() - 1);
        }
        voteRepository.save(new Vote(
                voteType,
                LocalDateTime.now(),
                quote,
                quote.getScore(),
                user
        ));
        quoteRepository.flush();
        return convertToResponse(quote);
    }

    private TreeMap<LocalDateTime, Integer> getVoteChart(Quote quote) {
        List<Vote> votes = quote.getVotes();

        return votes.stream()
                .collect(Collectors.toMap(Vote::getVoteTime,
                                Vote::getScoreAfterVote,
                        (k1, k2) -> k1, TreeMap::new));


    }

    //FIXME stub as it was stated not to add security. Normally should get user from SecurityContextHolder
    private User getRandomUser() {
        return userRepository.findRandom()
                .orElseThrow(() -> new EntityNotFoundException("No users in db"));
    }

    private Quote convertToEntity(QuoteCreateOrUpdateRequest request, User user) {
        return new Quote(request.content(),
                LocalDateTime.now(),
                user);
    }

    private Quote updateEntity(QuoteCreateOrUpdateRequest request, Quote quote) {
        quote.setContent(request.content());
        quote.setUpdateDate(LocalDateTime.now());
        return quote;
    }

    private QuoteResponse convertToResponse(Quote quote) {
        return new QuoteResponse(
                quote.getId(),
                quote.getContent(),
                new NamedElement<>(quote.getUser().getId(), quote.getUser().getName()),
                quote.getScore(),
                getVoteChart(quote)
        );
    }


}
