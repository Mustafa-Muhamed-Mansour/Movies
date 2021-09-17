package com.movies.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.movies.network.ApiClient;
import com.movies.network.ApiService;
import com.movies.responses.ShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailsRepository
{

    private ApiService apiService;
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();

    public ShowDetailsRepository()
    {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ShowDetailsResponse> getShowDetails(String showDetailsID)
    {
        MutableLiveData<ShowDetailsResponse> responseMutableLiveData = new MutableLiveData<>();
        apiService.getShowDeails(showDetailsID).enqueue(new Callback<ShowDetailsResponse>()
        {
            @Override
            public void onResponse(Call<ShowDetailsResponse> call, Response<ShowDetailsResponse> response)
            {
                responseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ShowDetailsResponse> call, Throwable t)
            {
                stringMutableLiveData.setValue(t.getMessage());
            }
        });

        return responseMutableLiveData;
    }
}
