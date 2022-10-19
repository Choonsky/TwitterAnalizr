package com.choonsky.twitteranalizr.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class TwitterStreamingService {

    @Value("${twitter.bearer.token}")
    private static String TWITTER_BEARER_TOKEN;

    @Value("${twitter.domain}")
    private static String TWITTER_DOMAIN;

    @Value("${twitter.endpoint}")
    private static String TWITTER_ENDPOINT;

    @Autowired
    @Lazy
    private WebClient.Builder builder;

    public Flux<String> stream() {

        WebClient client = this.builder
                .baseUrl(TWITTER_DOMAIN)
                .defaultHeaders(headers -> headers.setBearerAuth(TWITTER_BEARER_TOKEN))
                .build();

        return client.get()
                .uri(TWITTER_ENDPOINT)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(tweet -> !tweet.isBlank());

    }
}
