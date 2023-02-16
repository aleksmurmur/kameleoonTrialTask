package org.aleksmurmur.kameleoon;

import org.aleksmurmur.kameleoon.quotes.domain.Quote;
import org.aleksmurmur.kameleoon.quotes.dto.QuoteCreateOrUpdateRequest;
import org.aleksmurmur.kameleoon.user.domain.User;
import org.aleksmurmur.kameleoon.user.dto.UserCreateRequest;

import java.time.LocalDateTime;
import java.util.Random;

public class DataGenerator {
    public static User user() {
        return new User(randomString(), randomString(), randomString(), LocalDateTime.now());
    }

    public static UserCreateRequest userCreateRequest() {
        return new UserCreateRequest(randomString(), randomString(), randomString());
    }


    public static Quote quote(User user) {
        return new Quote(randomString(), LocalDateTime.now(), user);
    }

    public static QuoteCreateOrUpdateRequest quoteCreateOrUpdateRequest() {
        return new QuoteCreateOrUpdateRequest(randomString());
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
