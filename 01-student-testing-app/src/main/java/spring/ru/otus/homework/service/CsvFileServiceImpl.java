package spring.ru.otus.homework.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CsvFileServiceImpl implements FileService {

    private final String url;
    private final QuestionService questionService;

    public CsvFileServiceImpl(String url, QuestionService questionService) {
        this.url = url;
        this.questionService = questionService;
    }

    public void init() throws FileNotFoundException {
        questionService.createQuestionsList(parseFileToStringArray());
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
