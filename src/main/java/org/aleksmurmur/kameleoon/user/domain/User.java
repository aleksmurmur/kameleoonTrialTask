package org.aleksmurmur.kameleoon.user.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aleksmurmur.kameleoon.common.jpa.UUIDIdentifiableEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends UUIDIdentifiableEntity {
    private String name;
    private String email;
    private String password;
    private LocalDateTime dateOfCreation;


}
