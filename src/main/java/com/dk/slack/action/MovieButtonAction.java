package com.dk.slack.action;

import com.dk.slack.DataSource;
import com.dk.slack.Registerable;
import com.dk.slack.Viewable;
import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.context.builtin.BlockSuggestionContext;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.request.builtin.BlockSuggestionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Action defined on the button in App Home
 */
@Component
public class MovieButtonAction implements Registerable {

    public final static String MOVIE_BUTTON_ACTION_ID = "movie-button-action";

    @Autowired
    @Qualifier("movieModalView")
    private final Viewable movieModalView;

    @Autowired
    private final DataSource dataSource;

    public MovieButtonAction(Viewable movieModalView, DataSource dataSource) {
        this.movieModalView = movieModalView;
        this.dataSource = dataSource;
    }

    @Override
    public void register(App app) {
        registerMovieButtonAction(app);
    }

    private void registerMovieButtonAction(App app) {
        app.blockAction(MOVIE_BUTTON_ACTION_ID, this::getHandler);
        app.blockSuggestion(MOVIE_BUTTON_ACTION_ID, this::getSuggestionHandler);
    }

    private Response getHandler(BlockActionRequest req, ActionContext ctx) throws IOException, SlackApiException {
        var logger = ctx.getLogger();
        var viewsOpenRes = ctx.client().viewsOpen(builder -> builder
                .triggerId(ctx.getTriggerId())
                .view(movieModalView.buildView()));

        logger.debug(viewsOpenRes.toString());

        if (viewsOpenRes.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();
    }

    private Response getSuggestionHandler(BlockSuggestionRequest req, BlockSuggestionContext ctx) {
        String keyword = req.getPayload().getValue();

        List<Option> options = getOptions().stream()
                .filter(o -> o.getText().getText().contains(keyword))
                .collect(toList());

        return ctx.ack(r -> r.options(options.isEmpty() ? getOptions() : options));
    }

    @NotNull
    private List<Option> getOptions() {
        return dataSource.getOptions().stream()
                .map(o -> new Option(o.getText(), o.getValue()))
                .collect(toList());
    }

}
