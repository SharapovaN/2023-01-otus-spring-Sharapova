package spring.ru.otus.homework.service;

import spring.ru.otus.homework.utils.StringConstants;

public class ApplicationStopServiceImpl implements ApplicationStopService {

    private final IOService ioService;
    private final ApplicationStopServiceSettingsProvider settingsProvider;
    private boolean executionFlag;

    public ApplicationStopServiceImpl(IOService ioService,
                                      ApplicationStopServiceSettingsProvider settingsProvider, boolean executionFlag) {
        this.ioService = ioService;
        this.settingsProvider = settingsProvider;
        this.executionFlag = executionFlag;
    }

    @Override
    public boolean isApplicationRunning() {
        return executionFlag;
    }

    @Override
    public void stopApplication() {
        if (settingsProvider.isConfirmExit()) {
            String exitConfirmation = ioService.readStringWithPrompt(StringConstants.APPLICATION_END_QUESTION);
            if (exitConfirmation.equalsIgnoreCase("no")) {
                return;
            }
        }
        executionFlag = false;
    }

    @Override
    public void stopTesting() {
        String decision = ioService.readStringWithPrompt(StringConstants.TEST_END_QUESTION);
        if (decision.equalsIgnoreCase("no")) {
            stopApplication();
        }
    }

}
