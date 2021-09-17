package com.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailsModel
{

    @SerializedName("url")
    private String url;
    @SerializedName("description")
    private String description;
    @SerializedName("runtime")
    private String runTime;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("rating")
    private String rating;
    @SerializedName("genres")
    private String [] genres;
    @SerializedName("pictures")
    private String [] pictures;
    @SerializedName("episodes")
    private ArrayList<EpisodeModel> episodeModels;

    public ShowDetailsModel()
    {
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getRating() {
        return rating;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public ArrayList<EpisodeModel> getEpisodeModels() {
        return episodeModels;
    }
}
