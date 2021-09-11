package com.dk.slack.view;

import com.dk.slack.DataSource;
import com.dk.slack.Viewable;
import com.slack.api.model.view.View;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.staticSelect;
import static com.slack.api.model.view.Views.*;

/**
 * Modal shown to user, presenting movie choices
 */
public class MovieModalView implements Viewable {

    public final static String MOVIE_BLOCK_ID = "movie-block";
    public final static String MOVIE_ACTION_ID = "movie-search";
    private final String infoText = "Select a movie:";
    private final String placeHolderText = "Select an item";

    private final DataSource dataSource;

    public MovieModalView(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public View buildView() {
        return view(view -> view
                .callbackId(MOVIE_ACTION_ID)
                .type("modal")
                .notifyOnClose(false)
                .title(viewTitle(title -> title.type("plain_text").text("Movie Info")))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit")))
                .close(viewClose(close -> close.type("plain_text").text("Cancel")))
                .blocks(asBlocks(
                        input(builder -> builder
                                .blockId(MOVIE_BLOCK_ID)
                                .label(plainText(pt -> pt.text(infoText)))
                                .element(staticSelect(staticSelect -> staticSelect
                                                .actionId(MOVIE_ACTION_ID)
                                                .placeholder(plainText(placeHolderText))
                                                .options(dataSource.getOptions())
                                        )
                                )
                        )
                ))
        );
    }

}
