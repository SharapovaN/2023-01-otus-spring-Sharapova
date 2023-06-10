package ru.otus.spring.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.homework.model.Task;
import ru.otus.spring.homework.service.BackendTaskServiceImpl;
import ru.otus.spring.homework.service.FrontendTaskServiceImpl;

@Configuration
public class IntegrationConfig {
    @Bean
    public QueueChannel inputTaskChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel outputTaskChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow productionFlow(BackendTaskServiceImpl backendTaskService,
                                          FrontendTaskServiceImpl frontendTaskService) {
        return IntegrationFlows.from(inputTaskChannel())
                .split().publishSubscribeChannel(subscription -> subscription
                        .subscribe(
                                subflow -> subflow
                                        .filter(Task.class, t -> t.getType().equals("BACKEND"))
                                        .handle(backendTaskService, "execute")
                                        .aggregate()
                                        .channel(outputTaskChannel())
                        )
                        .subscribe(
                                subflow -> subflow
                                        .filter(Task.class, t -> t.getType().equals("FRONTEND"))
                                        .handle(frontendTaskService, "execute")
                                        .aggregate()
                                        .channel(outputTaskChannel())
                        ))
                .get();
    }
}
