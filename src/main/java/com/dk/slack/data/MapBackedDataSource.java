package com.dk.slack.data;

import com.dk.slack.DataSource;
import com.slack.api.model.block.composition.OptionObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.option;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Component
public class MapBackedDataSource implements DataSource {

    private final static LinkedHashMap<String, String> movies = new LinkedHashMap<>();
    private List<OptionObject> options;

    static {
        movies.put("Star Wars Episode I: The Phantom Menace", "1893");
        movies.put("Star Wars Episode II: Attack of the Clones", "1894");
        movies.put("Star Wars Episode III: Revenge of the Sith", "1895");
        movies.put("Solo: A Star Wars Story", "348350");
        movies.put("Rogue One: A Star Wars Story", "330459");
        movies.put("Star Wars Episode IV: A New Hope", "11");
        movies.put("Star Wars Episode V: The Empire Strikes Back", "1891");
        movies.put("Star Wars Episode VI: Return of the Jedi", "1892");
        movies.put("Star Wars Episode VII: The Force Awakens", "140607");
        movies.put("Star Wars Episode VIII: The Last Jedi", "181808");
        movies.put("Star Wars Episode IX: The Rise of Skywalker", "181812");
        movies.put("Robot Chicken: Star Wars", "42979");
    }

    @NotNull
    @Override
    public List<OptionObject> getOptions() {
        if (options == null) {
            options = new ArrayList<>();
            movies.keySet().iterator().forEachRemaining(movie -> {
                String movieId = movies.get(movie);
                options.add(option(plainText(movie), movieId));
            });
        }
        return options;
    }

}
