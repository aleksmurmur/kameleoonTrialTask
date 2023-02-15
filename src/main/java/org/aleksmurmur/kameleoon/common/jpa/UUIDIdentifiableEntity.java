package org.aleksmurmur.kameleoon.common.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aleksmurmur.kameleoon.exception.EntityNotPersistedException;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
public class UUIDIdentifiableEntity  {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    protected UUID id = null;

    public UUID getId() {
        if (id == null) throw new EntityNotPersistedException("The entity hasn't persisted yet");
        return id;
    }
}
