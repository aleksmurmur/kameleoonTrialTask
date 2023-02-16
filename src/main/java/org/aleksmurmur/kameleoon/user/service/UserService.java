package org.aleksmurmur.kameleoon.user.service;


import org.aleksmurmur.kameleoon.exception.BadRequestException;
import org.aleksmurmur.kameleoon.exception.EntityNotFoundException;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.aleksmurmur.kameleoon.user.dto.UserCreateRequest;
import org.aleksmurmur.kameleoon.user.dto.UserResponse;
import org.aleksmurmur.kameleoon.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        validateEmailIsNew(request.email());
        User createdUser = userRepository.save(toEntity(request));
        logger.debug(String.format("User %s created", createdUser.getId()));
        return toResponse(createdUser);
    }


    private User toEntity(UserCreateRequest request) {
        return new User(request.name(),
                request.email(),
                request.password(),
                LocalDateTime.now());
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getDateOfCreation());
    }

    private void validateEmailIsNew(String email) {
        if (userRepository.findByEmail(email).isPresent()) throw new BadRequestException("Email already exists. Choose another or log in");
    }
}
