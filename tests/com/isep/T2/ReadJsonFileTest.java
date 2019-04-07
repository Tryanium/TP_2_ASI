package com.isep.T2;

import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class ReadJsonFileTest {

    @Test
    public void read() throws IOException {
        File file = new File("tests/resources/Rift Between Officers and Residents as Killings Persist in South Bronx.json");
        JSONObject json = ReadJsonFile.read("benjamin-mueller-and-al-baker-2017-06-19.txt");

        // Test if the file transformed into JSON is the same as the expected JSON.
        assertEquals(new String(Files.readAllBytes(file.toPath())).replaceAll("—", "-"), json.toString());
    }
}