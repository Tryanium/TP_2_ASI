package com.isep.T2;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FindFileInDirectoryTest {

    private String PATH_TO_FILE = "src/main/resources/articlesPending";

    @Test
    public void findFileNotEmpty() {
        ArrayList<String> files = this.createList();

        // Test whether the list is empty
        assertFalse(files.isEmpty());
    }

    @Test
    public void findFileListHasAllFiles() {
        ArrayList<String> files = this.createList();

        // Test if there are 5 files in the list as intended
        assertEquals(files.size(), new File(PATH_TO_FILE).listFiles().length);
    }

    private ArrayList<String> createList() {
        FindFileInDirectory fd = new FindFileInDirectory();
        ArrayList<String> files = fd.findFile(PATH_TO_FILE);

        return files;
    }
}