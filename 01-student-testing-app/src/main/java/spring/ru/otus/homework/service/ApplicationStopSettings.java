package spring.ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStopSettings implements ApplicationStopServiceSettingsProvider {

    private final boolean confirmExit;

    public ApplicationStopSettings(@Value("${application.confirm.exit.flag}") boolean confirmExit) {
        this.confirmExit = confirmExit;
    }

    @Override
    public boolean isConfirmExit() {
        return confirmExit;
    }

}
