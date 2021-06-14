package aarkoub.moviequizory.apiclient;

import aarkoub.moviequizory.domain.actor.Actor;
import aarkoub.moviequizory.domain.movie.Movie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;

@Component
public class MovieDBAPIClient {

    @Value("${moviedb.api-key}")
    private String API_KEY;

    @Bean
    public WebClient getApiClient() {
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build())
                .baseUrl("https://api.themoviedb.org/3")
                .build();
    }

    public String getMostExpensiveMovies(int pageNum) {
        return getApiClient()
                .get()
                .uri("/discover/movie?api_key=" + API_KEY + "&sort_by=revenue.desc&page=" + pageNum)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getActorImages(int id) {
        return getApiClient()
                .get()
                .uri("/person/" + id + "/images?api_key=" + API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getMovieCredits(int id) throws WebClientResponseException {
        return getApiClient()
                .get()
                .uri("/movie/" + id + "/credits?api_key=" + API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    public Map<Integer, Movie> getMovies(int maxPages) {

        Map<Integer, Movie> moviesMap = new HashMap<>();

        for (int k = 1; k < maxPages+1; k++) {

            JSONObject movies = new JSONObject(getMostExpensiveMovies(k));
            JSONArray results = (JSONArray) movies.get("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject res = (JSONObject) results.get(i);
                Integer id = (Integer) res.get("id");
                String title = (String) res.get("original_title");
                String pictureUrl = null;
                if (!res.get("poster_path").toString().equals("null"))
                    pictureUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + res.get("poster_path");
                moviesMap.put(id, new Movie(title, pictureUrl));
            }
        }

        return moviesMap;
    }

    public Map<Integer, List<Actor>> getActorsPerMovie(Map<Integer, Movie> moviesMap) {

        Map<Integer, List<Actor>> actorsInMovieMap = new HashMap<>();

        int maxSizeActorsPerMovie = 2;

        List<Integer> movieIdsToRemove = new ArrayList<>();
        for (Integer id : moviesMap.keySet()) {
            String creditsString = null;
            try{
                creditsString = getMovieCredits(id);
            }catch (WebClientResponseException e){
                movieIdsToRemove.add(id);
                continue;
            }
            JSONObject credits = new JSONObject(creditsString);
            JSONArray cast = credits.getJSONArray("cast");
            List<Actor> actors = new ArrayList<>();
            if (cast.length() == 0) {
                movieIdsToRemove.add(id);
            } else {
                for (int i = 0; i < maxSizeActorsPerMovie && i < cast.length(); i++) {
                    JSONObject castActor = (JSONObject) cast.get(i);
                    Integer actorId = (Integer) castActor.get("id");
                    String actorName = (String) castActor.get("name");
                    JSONObject actor = new JSONObject(getActorImages(actorId));
                    JSONArray actorImages = (JSONArray) actor.get("profiles");
                    String actorPicUrl = null;
                    if (actorImages.length() != 0) {
                        actorPicUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" + ((JSONObject) actorImages.get(0)).get("file_path");
                    }
                    Actor a = new Actor(actorName, actorPicUrl);
                    actors.add(a);
                }
                actorsInMovieMap.put(id, actors);
            }

        }

        movieIdsToRemove.forEach(moviesMap::remove);

        return actorsInMovieMap;
    }

}
