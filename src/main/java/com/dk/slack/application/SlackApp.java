package com.dk.slack.application;

import com.dk.slack.action.ModalButtonAction;
import com.dk.slack.action.MovieButtonAction;
import com.dk.slack.event.AppHomeEvent;
import com.slack.api.bolt.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SlackApp {

    @Autowired
    private final App app;
    @Autowired
    private final AppHomeEvent appHomeEvent;
    @Autowired
    private final ModalButtonAction modalButtonAction;
    @Autowired
    private final MovieButtonAction movieButtonAction;

    public SlackApp(App app,
                    AppHomeEvent appHomeEvent,
                    ModalButtonAction modalButtonAction,
                    MovieButtonAction movieButtonAction) {
        this.app = app;
        this.appHomeEvent = appHomeEvent;
        this.modalButtonAction = modalButtonAction;
        this.movieButtonAction = movieButtonAction;
    }

    @Bean
    @Primary
    public App initSlackApp() {
        modalButtonAction.register(app);
        movieButtonAction.register(app);
        appHomeEvent.register(app);

        return app;
    }

}