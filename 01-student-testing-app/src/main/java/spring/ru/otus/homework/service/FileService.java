package spring.ru.otus.homework.service;

import java.io.FileNotFoundException;

public interface FileService {
    String[] parseFileToStringArray() throws FileNotFoundException;

    void init() throws FileNotFoundException;
}
