package spring.ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.ru.otus.homework.config.AppProps;

@Service
public class ApplicationStopServiceImpl implements ApplicationStopService {

    private final IOService ioService;
    private final ApplicationStopServiceSettingsProvider settingsProvider;
    private boolean executionFlag;
    private final MessageSource messageSource;
    private final AppProps props;

    public ApplicationStopServiceImpl(IOService ioService,
                                      ApplicationStopServiceSettingsProvider settingsProvider,
                                      @Value("${application.execution.flag}") boolean executionFlag,
                                      MessageSource messageSource, AppProps props) {
        this.ioService = ioService;
        this.settingsProvider = settingsProvider;
        this.executionFlag = executionFlag;
        this.messageSource = messageSource;
        this.props = props;
    }

    @Override
    public boolean isApplicationRunning() {
        return executionFlag;
    }

    @Override
    public void stopApplication() {
        if (settingsProvider.isConfirmExit()) {
            String exitConfirmation = ioService.readStringWithPrompt(messageSource.getMessage("application.end.question",
                    null, props.getLocale()));
            if (exitConfirmation.equalsIgnoreCase("no") || exitConfirmation.equalsIgnoreCase("нет")) {
                return;
            }
        }
        executionFlag = false;
    }

    @Override
    public void stopTesting() {
        String decision = ioService.readStringWithPrompt(messageSource.getMessage("test.end.question",
                null, props.getLocale()));
        if (decision.equalsIgnoreCase("no") || decision.equalsIgnoreCase("нет")) {
            stopApplication();
        }
    }

}
