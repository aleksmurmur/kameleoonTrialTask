package org.aleksmurmur.kameleoon.quotes.repository;

import org.aleksmurmur.kameleoon.quotes.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, UUID> {

    Optional<Quote> findByIdAndUserId(UUID quoteId, UUID userId);
    @Query(value = "SELECT * FROM quotes q ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quote> findRandom();



    List<Quote> findFirst10ByOrderByScoreDesc();

    List<Quote> findFirst10ByOrderByScoreAsc();
}
