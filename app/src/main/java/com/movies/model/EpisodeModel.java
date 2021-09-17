package com.movies.model;

import com.google.gson.annotations.SerializedName;

public class EpisodeModel
{

    @SerializedName("season")
    private String season;
    @SerializedName("episode")
    private String episode;
    @SerializedName("name")
    private String name;
    @SerializedName("air_date")
    private String airDate;

    public EpisodeModel()
    {
    }

    public String getSeason() {
        return season;
    }

    public String getEpisode() {
        return episode;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }
}
