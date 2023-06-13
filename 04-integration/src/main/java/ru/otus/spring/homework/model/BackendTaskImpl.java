package ru.otus.spring.homework.model;

public class BackendTaskImpl implements Task {
    private static final String TYPE = "BACKEND";

    private final String description;

    private boolean executed;

    private String result;

    public BackendTaskImpl(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    @Override
    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BackendTask {" +
                "description = '" + description + '\'' +
                ", executed = " + executed +
                ", result = '" + result + '\'' +
                '}';
    }
}
