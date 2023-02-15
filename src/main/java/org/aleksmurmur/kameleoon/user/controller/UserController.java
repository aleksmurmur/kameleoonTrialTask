package org.aleksmurmur.kameleoon.user.controller;


import jakarta.validation.Valid;
import org.aleksmurmur.kameleoon.user.dto.UserCreateRequest;
import org.aleksmurmur.kameleoon.user.dto.UserResponse;
import org.aleksmurmur.kameleoon.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.aleksmurmur.kameleoon.api.Paths.USERS_V1_PATH;

@RestController
@RequestMapping( USERS_V1_PATH)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) { //TODO validate request --check it
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }
}
