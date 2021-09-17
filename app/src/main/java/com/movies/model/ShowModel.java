package com.movies.model;

import com.google.gson.annotations.SerializedName;

public class ShowModel
{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("country")
    private String country;
    @SerializedName("network")
    private String network;
    @SerializedName("status")
    private String status;
    @SerializedName("image_thumbnail_path")
    private String imageThumbnailPath;

    public ShowModel()
    {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public String getImageThumbnailPath() {
        return imageThumbnailPath;
    }
}
