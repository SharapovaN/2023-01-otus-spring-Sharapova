package spring.ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class CsvFileServiceImpl implements FileService {

    private final String url;

    public CsvFileServiceImpl(@Value("${csv.question.file.url}") String url) {
        this.url = url;
    }

    public String[] parseFileToStringArray() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(url));
        List<String> strings = new ArrayList<>();

        scanner.useDelimiter("\n");

        while (scanner.hasNext()) {
            strings.add(scanner.next());
        }

        return strings.toArray(String[]::new);
    }
}
