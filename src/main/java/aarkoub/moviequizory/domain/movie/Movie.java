package aarkoub.moviequizory.domain.movie;

public class Movie {

    private String title;
    private String pictureURL;

    public Movie(String title, String pictureURL) {
        this.title = title;
        this.pictureURL = pictureURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }



}
