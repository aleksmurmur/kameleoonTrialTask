package org.aleksmurmur.kameleoon.quotes.repository;

import org.aleksmurmur.kameleoon.quotes.domain.Quote;
import org.aleksmurmur.kameleoon.quotes.domain.Vote;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    Optional<Vote> findByQuoteAndUser(Quote quote, User user);
}
