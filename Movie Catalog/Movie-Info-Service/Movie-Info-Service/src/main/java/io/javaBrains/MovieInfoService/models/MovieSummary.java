package io.javaBrains.MovieInfoService.models;

public class MovieSummary {

    private String  id;
    private String title;
    private String Overview;

    public MovieSummary() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }
}
