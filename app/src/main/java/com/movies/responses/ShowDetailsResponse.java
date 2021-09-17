package com.movies.responses;

import com.google.gson.annotations.SerializedName;
import com.movies.model.ShowDetailsModel;

public class ShowDetailsResponse
{

    @SerializedName("tvShow")
    private ShowDetailsModel showDetailsModel;

    public ShowDetailsModel getShowDetailsModel() {
        return showDetailsModel;
    }
}
