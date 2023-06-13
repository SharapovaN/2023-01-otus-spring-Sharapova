package ru.otus.spring.homework.model;

public interface Task {
    String getType();

    String getDescription();

    void setExecuted(boolean executed);

    void setResult(String result);
}
