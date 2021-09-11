package com.dk.slack.action;

import com.dk.slack.DataSource;
import com.dk.slack.Registerable;
import com.dk.slack.Viewable;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;

import java.io.IOException;

/**
 * Action defined on the button in App Home
 */
public class MovieButtonAction implements Registerable {

    public final static String MOVIE_BUTTON_ACTION_ID = "movie-button-action";

    private final Viewable movieModalView;

    public MovieButtonAction(Viewable modalView) {
        this.movieModalView = modalView;
    }

    @Override
    public void register(App app) {
        registerMovieButtonAction(app);
    }

    private void registerMovieButtonAction(App app) {
        app.blockAction(MOVIE_BUTTON_ACTION_ID, this::getResponse);
    }

    private Response getResponse(BlockActionRequest req, ActionContext ctx) throws IOException, SlackApiException {
        var logger = ctx.getLogger();
        var viewsOpenRes = ctx.client().viewsOpen(builder -> builder
                .triggerId(ctx.getTriggerId())
                .view(movieModalView.buildView()));

        logger.debug(viewsOpenRes.toString());

        if (viewsOpenRes.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();
    }

}
