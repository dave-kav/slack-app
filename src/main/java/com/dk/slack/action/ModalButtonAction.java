package com.dk.slack.action;

import com.dk.slack.Registerable;
import com.dk.slack.view.MovieModalView;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Action performed on MovieModal button click
 */
@Component
public class ModalButtonAction implements Registerable {

    @Autowired
    private final MovieMessageAction movieMessageAction;

    public ModalButtonAction(MovieMessageAction movieMessageAction) {
        this.movieMessageAction = movieMessageAction;
    }

    @Override
    public void register(App app) {
        registerModalButtonAction(app);
    }

    private void registerModalButtonAction(App app) {
        app.viewSubmission(MovieModalView.MOVIE_ACTION_ID, this::getResponse);
    }

    private Response getResponse(ViewSubmissionRequest req, ViewSubmissionContext ctx) {
        var logger = ctx.getLogger();
        var selectedOptionValue = req.getPayload()
                .getView()
                .getState()
                .getValues().get(MovieModalView.MOVIE_BLOCK_ID)
                .get(MovieModalView.MOVIE_ACTION_ID)
                .getSelectedOption()
                .getValue();

        createMovieMessage(ctx, logger, selectedOptionValue);
        return ctx.ack();
    }

    private void createMovieMessage(ViewSubmissionContext ctx, Logger logger, String selectedOptionValue) {
        try {
            var result = movieMessageAction.createMessage(ctx, selectedOptionValue);
            logger.debug("result: {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }

}

