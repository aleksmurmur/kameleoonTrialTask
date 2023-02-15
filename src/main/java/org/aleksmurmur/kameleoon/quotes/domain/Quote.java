package org.aleksmurmur.kameleoon.quotes.domain;

import jakarta.persistence.*;
import lombok.*;
import org.aleksmurmur.kameleoon.common.jpa.UUIDIdentifiableEntity;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "quotes")
public class Quote extends UUIDIdentifiableEntity {
    private String content;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate = null;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int score = 0;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "quote", orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public Quote(String content, LocalDateTime creationDate, User user) {
        this.content = content;
        this.creationDate = creationDate;
        this.user = user;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
