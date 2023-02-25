package spring.ru.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.ru.otus.homework.config.AppProps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class CsvFileServiceImpl implements FileService {

    private final String url;

    public CsvFileServiceImpl(MessageSource messageSource, AppProps props) {
        this.url = messageSource.getMessage("csv.question.file.url", null, props.getLocale());
    }

    @Override
    public String[] getDataFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(url));
        List<String> strings = new ArrayList<>();

        scanner.useDelimiter("\n");

        while (scanner.hasNext()) {
            strings.add(scanner.next());
        }

        return strings.toArray(String[]::new);
    }

}
