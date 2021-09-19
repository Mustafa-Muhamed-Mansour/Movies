package com.movies.network;

import com.movies.responses.ShowDetailsResponse;
import com.movies.responses.ShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService
{

    @GET("most-popular")
    Call<ShowResponse> getShow(@Query("page") int page);

    @GET("show-details")
    Call<ShowDetailsResponse> getShowDeails(@Query("q") String showDetailsID);

    @GET("search")
    Call<ShowResponse> getShowSearch(@Query("q") String query, @Query("page") int page);


}
