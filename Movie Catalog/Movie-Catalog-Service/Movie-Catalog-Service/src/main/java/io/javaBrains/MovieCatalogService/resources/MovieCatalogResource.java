package io.javaBrains.MovieCatalogService.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javaBrains.MovieCatalogService.Models.CatlogItem;
import io.javaBrains.MovieCatalogService.Models.Movie;
import io.javaBrains.MovieCatalogService.Models.Rating;
import io.javaBrains.MovieCatalogService.Models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    // Create Auto instances of RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    //@HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatlogItem> getCatalog(@PathVariable("userId") String userId){
        //WebClient.Builder builder = WebClient.builder()
        //Get All Rated Movie Id's based on UserID
        /*
        List<Rating> ratings=Arrays.asList(
                new Rating("123", 4),
                new Rating("234", 5),
                new Rating("345", 3)
        );
        */
        //Instaed of hardcoding , get the data using an API Call
        UserRating ratings = restTemplate.getForObject("http://Rating-Data-Service/ratingsdata/users/" + userId, UserRating.class);

        //for each MovieId , call Movie info service and get details
        //put them all together
        return ratings.getRatingList().stream().map(rating -> {
                Movie movie = restTemplate.getForObject("http://Movie-info-Service/movies/" + rating.getMovieId(), Movie.class);
                /*
                Movie movie=webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8090/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)// Getting Back Asynchronus object
                            .block();
                 */
                return new CatlogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
        // First Execution where we hardcoded the Return of Movie's rated by User.

        //Intially done to hardcode and see results
        // return Collections.singletonList(
        //        new CatlogItem("Transfomers" ,"Test" , 4)
        //);
    }
    /*
    public List<CatlogItem> getFallbackCatalog(@PathVariable("userId") String userId){
        return Arrays.asList(new CatlogItem("No Movie ", "N/A" , 0));
    }
     */
}
