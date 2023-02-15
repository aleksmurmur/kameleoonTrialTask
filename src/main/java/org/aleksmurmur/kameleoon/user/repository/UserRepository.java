package org.aleksmurmur.kameleoon.user.repository;

import org.aleksmurmur.kameleoon.common.jpa.UUIDIdentifiableEntity;
import org.aleksmurmur.kameleoon.quotes.domain.Quote;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<User> findRandom();
}
