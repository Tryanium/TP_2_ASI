package com.isep.T2;

import org.junit.Test;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GetTweeterFeedTest {

    @Test
    public void getFeedAuth() {
        ConfigurationBuilder cb = this.authenticate();

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();

        // Test if the account settings exists, therefore if the auth process worked
        // If exeption is thrown, the auth process did not work
        try {
            twitter.getAccountSettings();
        } catch (TwitterException e) {
            fail("Threw exeption : " + e.toString());
        }
    }

    @Test
    public void getFeedTrendsIsNotEmpty()  {
        ConfigurationBuilder cb = this.authenticate();

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();

        ArrayList<String> TwitterTrends = new ArrayList<>();

        // Test if trends trew an exeption
        try {
            Trends trends = twitter.getPlaceTrends(1); //1 for Worlwide, 615702 for Paris
            for (int i = 0; i < 10; i++) {
                TwitterTrends.add(trends.getTrends()[i].getName());
            }
        } catch (TwitterException e) {
            fail("Threw Exeption : " + e.toString());
        }

        // Test if the trends list is empty
        assertFalse(TwitterTrends.isEmpty());
    }

    private ConfigurationBuilder authenticate() {
        String consumerKey = "NMqaca1bzXsOcZhP2XlwA";
        String consumerSecret = "VxNQiRLwwKVD0K9mmfxlTTbVdgRpriORypnUbHhxeQw";
        String accessToken = "26693234-W0YjxL9cMJrC0VZZ4xdgFMymxIQ10LeL1K8YlbBY";
        String accessTokenSecret = "BZD51BgzbOdFstWZYsqB5p5dbuuDV12vrOdatzhY4E";

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        return cb;
    }
}