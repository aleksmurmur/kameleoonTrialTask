package org.aleksmurmur.kameleoon.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aleksmurmur.kameleoon.quotes.repository.QuoteRepository;
import org.aleksmurmur.kameleoon.quotes.repository.VoteRepository;
import org.aleksmurmur.kameleoon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration
public class ITContext {

    @Autowired
    protected MockMvc testClient;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected QuoteRepository quoteRepository;
    @Autowired
    protected VoteRepository voteRepository;

    public void cleanDB() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
    }
}
