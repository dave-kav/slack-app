package com.dk.slack;

import com.slack.api.model.block.composition.OptionObject;

import java.util.List;

public interface DataSource {

    List<OptionObject> getOptions();

}
