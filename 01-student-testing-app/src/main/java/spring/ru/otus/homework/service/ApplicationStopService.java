package spring.ru.otus.homework.service;

public interface ApplicationStopService {
    boolean isApplicationRunning();

    void stopApplication();

    void stopTesting();

}
