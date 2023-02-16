package org.aleksmurmur.kameleoon.tests;

import org.aleksmurmur.kameleoon.configuration.ITContext;
import org.aleksmurmur.kameleoon.quotes.domain.Quote;
import org.aleksmurmur.kameleoon.quotes.domain.VoteType;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteCreateOrUpdateRequest;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteResponse;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.aleksmurmur.kameleoon.DataGenerator.*;
import static org.aleksmurmur.kameleoon.api.Paths.QUOTES_V1_PATH;

public class QuoteApiTests extends ITContext {

    private Quote quote;
    private User user;

    @BeforeEach
    public void before() {
        cleanDB();
        user = userRepository.save(user());
        quote = quoteRepository.save(quote(user));
    }

    @Nested
    class getById {
    @Test
        public void successfullyGetsById() throws Exception{
            String quoteString = getQuote(quote.getId())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
            QuoteResponse quoteResponse = mapper.readValue(quoteString, QuoteResponse.class);
        Assertions.assertEquals(quote.getId(), quoteResponse.id());
        Assertions.assertEquals(quote.getContent(), quoteResponse.content());
        Assertions.assertEquals(quote.getCreationDate().truncatedTo(ChronoUnit.SECONDS), quoteResponse.creationDate().truncatedTo(ChronoUnit.SECONDS));
        Assertions.assertEquals(quote.getScore(), quoteResponse.score());
        Assertions.assertEquals(quote.getUser().getId(), quoteResponse.userNamedElement().id());
        Assertions.assertEquals(quote.getVotes().size(), quoteResponse.voteChart().size());
    }

    @Test
    public void returns404IfNonExistentIdHandled() throws Exception{
        getQuote(UUID.randomUUID())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private ResultActions getQuote(UUID id) throws Exception {
        return testClient.perform(MockMvcRequestBuilders.get(String.join("/", QUOTES_V1_PATH, id.toString())));
    }
    }

    @Nested
    class create {
        @Test
        public void createsQuote() throws Exception {
            QuoteCreateOrUpdateRequest request = quoteCreateOrUpdateRequest();
            String quoteString = createQuote(request)
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn().getResponse().getContentAsString();
            QuoteResponse quoteResponse = mapper.readValue(quoteString, QuoteResponse.class);
            Assertions.assertEquals(request.content(), quoteResponse.content());
            Assertions.assertNotNull(quoteResponse.id());
            Assertions.assertNotNull(quoteResponse.userNamedElement());
        }

        @Test
        public void returns400IfNonValidRequestHandled() throws Exception {
            createQuote(new QuoteCreateOrUpdateRequest(""))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        private ResultActions createQuote(QuoteCreateOrUpdateRequest request) throws Exception {
            return testClient.perform(MockMvcRequestBuilders
                    .post(QUOTES_V1_PATH)
                    .content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Nested
    class vote {
        @Test
        public void voteSuccessfully() throws Exception{
            String quoteString = putVote(quote.getId())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
            QuoteResponse quoteResponse = mapper.readValue(quoteString, QuoteResponse.class);
            Assertions.assertEquals(quote.getScore() + 1, quoteResponse.score());
            Assertions.assertEquals(1, quoteResponse.voteChart().size());
        }

        @Test
        public void returns400IfTriesToVoteAgain() throws Exception {
            putVote(quote.getId());
            putVote(quote.getId())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        private ResultActions putVote(UUID quoteId) throws Exception {
            return testClient.perform(MockMvcRequestBuilders
                    .put(String.join("/", QUOTES_V1_PATH, quoteId.toString(), "vote"))
                    .param("vote", VoteType.UPVOTE.name()));


        }
    }

    //TODO implement testing update, delete, getAll, getBest/Worst


}
