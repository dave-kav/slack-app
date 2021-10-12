package com.dk.slack;

import com.google.gson.Gson;
import com.slack.api.bolt.App;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public App getApp() {
        return new App();
    }

    @Bean
    public Gson getGson() {
        return new Gson();
    }

    @Bean
    public OkHttpClient getHttpClient() {
        return new OkHttpClient();
    }
}
