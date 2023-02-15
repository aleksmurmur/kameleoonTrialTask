package org.aleksmurmur.kameleoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class KameleoonTrialTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(KameleoonTrialTaskApplication.class, args);
    }

}
