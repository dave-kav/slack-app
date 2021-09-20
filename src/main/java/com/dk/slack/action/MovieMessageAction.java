package com.dk.slack.action;

import com.dk.slack.util.MovieLookUp;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.LayoutBlock;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.element.BlockElements.imageElement;

/**
 * Handler for posting message with movie info
 */
public class MovieMessageAction {

    private final MovieLookUp movieLookUp;

    private final String infoText = "Here's the movie info you requested!";

    public MovieMessageAction(MovieLookUp movieLookUp) {
        this.movieLookUp = movieLookUp;
    }

    public ChatPostMessageResponse createMessage(ViewSubmissionContext ctx, String selectedOptionValue) throws IOException, SlackApiException {
        var movieInfo = movieLookUp.lookup(selectedOptionValue);

        return ctx.client().chatPostMessage(r -> r
                .token(ctx.getBotToken())
                .channel(ctx.getRequestUserId())
                .blocks(getLayoutBlocks(movieInfo))
        );
    }

    @NotNull
    private List<LayoutBlock> getLayoutBlocks(MovieLookUp.MovieMetadata movieInfo) {
        return asBlocks(
                section(builder -> builder.text(markdownText(mt -> mt.text(infoText)))),
                section(builder -> builder.text(markdownText(mt -> mt.text(String.format("*%s*" , movieInfo.getTitle()))))),
                section(builder -> builder
                        .text(markdownText(mt -> mt.text(String.format("*Release date:* %s\n%s", movieInfo.getReleaseDate(), movieInfo.getOverview()))))
                        .accessory(imageElement(imageElementBuilder -> imageElementBuilder
                                .imageUrl(movieInfo.getPosterPath())
                                .altText(movieInfo.getPosterPath())
                        ))
                )
        );
    }
}
