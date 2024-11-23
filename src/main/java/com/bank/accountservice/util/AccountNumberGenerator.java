package com.bank.accountservice.util;

import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Random;

public class AccountNumberGenerator {
    private AccountNumberGenerator() {
    }

    public static Mono<String> generateAccountNumber() {
        final Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(13);
        for (int i = 0; i < 13; i++) {
            sb.append(random.nextInt(10));
        }
        return Mono.just(sb.toString());
    }
}
