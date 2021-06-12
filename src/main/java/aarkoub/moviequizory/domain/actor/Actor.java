package aarkoub.moviequizory.domain.actor;

public class Actor {


    private String name;
    private String pictureURL;

    public Actor(String name, String pictureURL){
        this.name = name;
        this.pictureURL = pictureURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }




}
