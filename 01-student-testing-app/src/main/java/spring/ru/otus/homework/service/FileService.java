package spring.ru.otus.homework.service;

import java.io.FileNotFoundException;

public interface FileService {

    String[] getDataFromFile() throws FileNotFoundException;

}
