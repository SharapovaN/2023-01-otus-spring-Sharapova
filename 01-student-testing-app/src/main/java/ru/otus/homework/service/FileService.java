package ru.otus.homework.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {

    public String[] parseCSVToStringArray(String url) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(url));
        List<String> strings = new ArrayList<>();

        scanner.useDelimiter("\r\n");

        while (scanner.hasNext()) {
            strings.add(scanner.next());
        }

        return strings.toArray(String[]::new);
    }
}
