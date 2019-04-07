package com.isep.T2;

import org.json.simple.JSONObject;
import org.junit.Test;
import twitter4j.JSONArray;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SQLiteTest {

    protected static String dbName = "Tweeter.db";
    protected static String TableName = "FEED";

    @Test
    public void connectionTest() {
        try {
            SQLite.connect(dbName);
        } catch (Exception e) {
            fail("Threw Exception" + e.toString());
        }
    }

    @Test
    public void createTable() {
        try {
            SQLite.connect(dbName);
            if (!SQLite.CheckTableExist(TableName)) {
                SQLite.CreateTable(TableName);
            }
        } catch (Exception e) {
            fail("Threw Exception" + e.toString());
        }
    }

    @Test
    public void checkTableExist() {
        try {
            SQLite.CheckTableExist(TableName);
        } catch (Exception e) {
            fail("Threw Exception" + e.toString());
        }
    }

    @Test
    public void sendData() {
        try {
            ArrayList<String> trends = GetTweeterFeed.GetFeed();
            SQLite.connect(dbName);

            if (!SQLite.CheckTableExist(TableName)) {
                SQLite.CreateTable(TableName);
            }

            JSONObject TrendsJson = new JSONObject();
            TrendsJson.put("Classement", new JSONArray(trends));
            String arrayList = TrendsJson.get("Classement").toString();
            SQLite.SendData(arrayList, TableName);

        } catch (Exception e) {
            fail("Threw Exception" + e.toString());
        }
    }
}