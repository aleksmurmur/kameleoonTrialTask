package org.aleksmurmur.kameleoon.quotes.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aleksmurmur.kameleoon.common.jpa.UUIDIdentifiableEntity;
import org.aleksmurmur.kameleoon.user.domain.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Vote extends UUIDIdentifiableEntity {
    @Enumerated(EnumType.STRING)
    private VoteType type;
    private LocalDateTime voteTime;
    @ManyToOne
    private Quote quote;
    private int scoreAfterVote;
    @ManyToOne
    private User user;
}
