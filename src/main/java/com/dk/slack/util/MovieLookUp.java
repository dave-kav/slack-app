package com.dk.slack.util;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Make HTTP call to The Movie DB API, parse and return required Metadata.
 */
public class MovieLookUp {

    public final String MOVIE_DB_API_KEY = "MOVIE_DB_API_KEY";
    public final String MOVIE_API = "https://api.themoviedb.org/3/movie/";
    private final String apiKey;
    private final OkHttpClient client;
    private final Gson gson;

    public MovieLookUp(OkHttpClient client, Gson gson) {
        apiKey = System.getenv(MOVIE_DB_API_KEY);
        this.client = client;
        this.gson = gson;
    }

    public MovieMetadata lookup(String movieId) throws IOException {
        String requestURL = new StringBuilder()
                .append(MOVIE_API).append(movieId)
                .append("?api_key=").append(apiKey)
                .append("&language=en-US").toString();

        Request request = new Request.Builder()
                .url(requestURL)
                .build();

        var response = client.newCall(request).execute();
        var body = Objects.requireNonNull(response.body()).string();
        return gson.fromJson(body, MovieMetadata.class);
    }

    public static class MovieMetadata {

        private String title;
        private String overview;
        private String poster_path; // camel case here so GSON can parse out of the box
        private Date release_date;

        public final String IMAGE_API = "https://image.tmdb.org/t/p/w500";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");

        public String getTitle() {
            return title;
        }

        public String getOverview() {
            return overview;
        }

        public String getReleaseDate() {
            return dateFormatter.format(release_date);
        }

        public String getPosterPath() {
            return IMAGE_API + poster_path;
        }

        // for local debugging/validation
        @Override
        public String toString() {
            return "MovieInfo{" +
                    "title='" + title + '\'' +
                    ", overview='" + overview + '\'' +
                    ", posterPath='" + poster_path + '\'' +
                    ", releaseDate='" + release_date + '\'' +
                    '}';
        }
    }

}
