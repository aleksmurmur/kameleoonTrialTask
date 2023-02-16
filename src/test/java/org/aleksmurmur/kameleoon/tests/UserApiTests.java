package org.aleksmurmur.kameleoon.tests;

import org.aleksmurmur.kameleoon.configuration.ITContext;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.aleksmurmur.kameleoon.user.dto.UserCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.aleksmurmur.kameleoon.DataGenerator.userCreateRequest;
import static org.aleksmurmur.kameleoon.api.Paths.USERS_V1_PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UserApiTests extends ITContext {


    @BeforeEach
    public void before() {
        cleanDB();
    }


    @Test
    public void createUser() throws Exception {
        UserCreateRequest request = userCreateRequest();
        String userJson = createUser(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        User resultUser = mapper.readValue(userJson, User.class);
        Assertions.assertEquals(request.name(), resultUser.getName());
        Assertions.assertEquals(request.email(), resultUser.getEmail());
    }



    private ResultActions createUser(UserCreateRequest request) throws Exception {
        return testClient.perform(MockMvcRequestBuilders
                .post(USERS_V1_PATH)
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
    }
}
