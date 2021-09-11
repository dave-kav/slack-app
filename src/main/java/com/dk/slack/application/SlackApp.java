package com.dk.slack.application;

import com.dk.slack.action.ModalButtonAction;
import com.dk.slack.action.MovieButtonAction;
import com.dk.slack.action.MovieMessageAction;
import com.dk.slack.data.MapBackedDataSource;
import com.dk.slack.event.AppHomeEvent;
import com.dk.slack.util.MovieLookUp;
import com.dk.slack.view.AppHomeView;
import com.dk.slack.view.MovieModalView;
import com.google.gson.Gson;
import com.slack.api.bolt.App;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {

    @Bean
    public App initSlackApp() {
        var app                 = new App();
        var gson                = new Gson();
        var client              = new OkHttpClient();
        var dataSource          = new MapBackedDataSource();

        var appHomeView         = new AppHomeView();
        var appHome             = new AppHomeEvent(appHomeView);
        var modalView           = new MovieModalView(dataSource);
        var movieLookUp         = new MovieLookUp(client, gson);
        var movieMessageAction  = new MovieMessageAction(movieLookUp);
        var modalButtonAction   = new ModalButtonAction(movieMessageAction);
        var movieButtonAction   = new MovieButtonAction(modalView);

        modalButtonAction.register(app);
        movieButtonAction.register(app);
        appHome.register(app);

        return app;
    }

}