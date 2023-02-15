package spring.ru.otus.homework.service;

import java.io.FileNotFoundException;

public interface FileService {
    void setUrl(String url);
    String[] parseFileToStringArray() throws FileNotFoundException;
}
