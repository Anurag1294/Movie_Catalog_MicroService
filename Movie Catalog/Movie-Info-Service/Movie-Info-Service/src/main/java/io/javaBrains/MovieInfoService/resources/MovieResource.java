package io.javaBrains.MovieInfoService.resources;

import io.javaBrains.MovieInfoService.models.Movie;
import io.javaBrains.MovieInfoService.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public Movie getMovieId(@PathVariable("movieId") String movieId){

        MovieSummary movieSummary=restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId +
                "?api_key=" + apikey, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
