package com.dk.slack.event;

import com.dk.slack.Registerable;
import com.dk.slack.Viewable;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.AppHomeOpenedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Triggered when user opens the app
 */
@Component
public class AppHomeEvent implements Registerable {

    @Autowired
    private final Viewable appHomeView;

    public AppHomeEvent(Viewable appHomeView) {
        this.appHomeView = appHomeView;
    }

    @Override
    public void register(App app) {
        registerAppHomeEvent(app);
    }

    private void registerAppHomeEvent(App app) {
        app.event(AppHomeOpenedEvent.class, this::response);
    }

    private Response response(EventsApiPayload<AppHomeOpenedEvent> payload, EventContext ctx) throws IOException, SlackApiException {
        ctx.client().viewsPublish(viewsPublish -> viewsPublish
                .userId(payload.getEvent().getUser())
                .view(appHomeView.buildView())
        );
        return ctx.ack();
    }

}
