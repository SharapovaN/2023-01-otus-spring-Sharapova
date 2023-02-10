package spring.ru.otus.homework.service;

public class ApplicationStopSettings implements ApplicationStopServiceSettingsProvider {
    private final boolean confirmExit;

    public ApplicationStopSettings(boolean confirmExit) {
        this.confirmExit = confirmExit;
    }

    @Override
    public boolean isConfirmExit() {
        return confirmExit;
    }

}
