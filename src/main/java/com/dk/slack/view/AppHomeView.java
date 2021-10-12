package com.dk.slack.view;

import com.dk.slack.Viewable;
import com.dk.slack.action.MovieButtonAction;
import com.slack.api.model.view.View;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;
import static com.slack.api.model.view.Views.view;

@Component
@Qualifier("appHomeView")
public class AppHomeView implements Viewable {

    private final String welcomeText = "*Welcome to _Move Info_* :tada:";
    private final String infoText    = "Click the button below to pick a movie!";
    private final String buttonText  = "Select a Movie!";

    @Override
    public View buildView() {
        return view(view -> view
                .type("home")
                .blocks(asBlocks(
                        section(builder -> builder.text(markdownText(mt -> mt.text(welcomeText)))),
                        section(builder -> builder.text(markdownText(mt -> mt.text(infoText)))),
                        actions(builder -> builder
                                .elements(asElements(button(btn -> btn
                                        .text(plainText(pt -> pt.text(buttonText)))
                                        .actionId(MovieButtonAction.MOVIE_BUTTON_ACTION_ID))
                                ))
                        )
                ))
        );
    }


}
