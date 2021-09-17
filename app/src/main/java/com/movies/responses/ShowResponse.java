package com.movies.responses;

import com.google.gson.annotations.SerializedName;
import com.movies.model.ShowModel;

import java.util.ArrayList;

public class ShowResponse
{

    @SerializedName("page")
    private int page;
    @SerializedName("pages")
    private int totalPages;
    @SerializedName("tv_shows")
    private ArrayList<ShowModel> showModels;

    public ShowResponse()
    {
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<ShowModel> getShowModels() {
        return showModels;
    }
}
