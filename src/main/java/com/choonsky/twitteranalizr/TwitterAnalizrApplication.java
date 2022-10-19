package com.choonsky.twitteranalizr;

import com.choonsky.twitteranalizr.azure.AzureSentimentService;
import com.choonsky.twitteranalizr.azure.SentimentAnalysis;
import com.choonsky.twitteranalizr.twilio.TwilioSender;
import com.choonsky.twitteranalizr.twitter.StreamResponse;
import com.choonsky.twitteranalizr.twitter.TwitterStreamingService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("!test")
@SpringBootApplication
public class TwitterAnalizrApplication implements CommandLineRunner {

    @Autowired
    private TwitterStreamingService twitterStreamingService;

    @Autowired
    private AzureSentimentService azureSentimentService;

    @Autowired
    private TwilioSender twilioSender;

    @Bean
    @Lazy
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterAnalizrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        this.twitterStreamingService.stream().subscribe(tweet -> {

            System.out.println("The tweet says: " + tweet);

            try {

                StreamResponse response = this.mapper().readValue(tweet, StreamResponse.class);

                SentimentAnalysis analysis = this.azureSentimentService
                        .requestSentimentAnalysis(response.getData().getText(), "en");

                String message = analysis.getSentiment().equalsIgnoreCase("positive")
                        ? "Received positive feedback on Twitter!"
                        : "Received negative feedback on Twitter!";

                System.out.println(message);

                System.out.println(this.twilioSender.sendSMS(message));

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });

    }

}