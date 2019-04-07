package com.isep.T2;

import java.io.File;
import java.util.ArrayList;

public class FindFileInDirectory {

    public ArrayList<String> findFile(String name) {

        ArrayList<String> ListFile = new ArrayList<>();

        File folder = new File(name);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                ListFile.add(listOfFiles[i].getName());
            }
        }
        return ListFile;
    }
}
