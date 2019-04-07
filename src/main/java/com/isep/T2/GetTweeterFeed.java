package com.isep.T2;

import java.util.ArrayList;

import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class GetTweeterFeed {

    private static String consumerKey = "NMqaca1bzXsOcZhP2XlwA";
    private static String consumerSecret = "VxNQiRLwwKVD0K9mmfxlTTbVdgRpriORypnUbHhxeQw";
    private static String accessToken = "26693234-W0YjxL9cMJrC0VZZ4xdgFMymxIQ10LeL1K8YlbBY";
    private static String accessTokenSecret = "BZD51BgzbOdFstWZYsqB5p5dbuuDV12vrOdatzhY4E";

    public static ArrayList<String> TweeterTrends = new ArrayList<String>();

    public static ArrayList<String> GetFeed() throws Exception {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Trends trends = twitter.getPlaceTrends(1); //1 for Worlwide, 615702 for Paris
        for (int i = 0; i < 10; i++) {
            TweeterTrends.add(trends.getTrends()[i].getName());
        }
        return TweeterTrends;
    }

}
