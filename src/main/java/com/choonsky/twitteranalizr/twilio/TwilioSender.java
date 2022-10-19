package com.choonsky.twitteranalizr.twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TwilioSender {

    @Value("${twilio.auth.token}")
    private static String TWILIO_AUTH_TOKEN;
    @Value("${twilio.from.number}")
    private static String TWILIO_FROM_NUMBER;
    @Value("${twilio.to.number}")
    private static String TWILIO_TO_NUMBER;
    @Value("${twilio.sid}")
    private static String TWILIO_SID;
    @Value("${twilio.domain}")
    private static String TWILIO_DOMAIN;
    @Value("${twilio.endpoint}")
    private static String TWILIO_ENDPOINT;

    @Autowired
    @Lazy
    private WebClient.Builder builder;

    public Mono<String> sendSMS(String text) {

        WebClient client = this.builder
                .baseUrl(TWILIO_DOMAIN)
                .defaultHeaders(headers -> headers.setBasicAuth(TWILIO_SID, TWILIO_AUTH_TOKEN))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        return client.post()
                .uri(TWILIO_ENDPOINT)
                .body(BodyInserters
                        .fromFormData("To", TWILIO_TO_NUMBER)
                        .with("From", TWILIO_FROM_NUMBER)
                        .with("Body", text))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> sendVoiceMessage(String twiML) {

        WebClient client = this.builder
                .baseUrl(TWILIO_DOMAIN)
                .defaultHeaders(headers -> headers.setBasicAuth(TWILIO_SID, TWILIO_AUTH_TOKEN))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        return client.post()
                .uri(TWILIO_ENDPOINT)
                .body(BodyInserters
                        .fromFormData("To", TWILIO_TO_NUMBER)
                        .with("From", TWILIO_FROM_NUMBER)
                        .with("Twiml", twiML))
                .retrieve()
                .bodyToMono(String.class);
    }
}
