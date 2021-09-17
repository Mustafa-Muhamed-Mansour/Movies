package com.movies.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.movies.network.ApiClient;
import com.movies.network.ApiService;
import com.movies.responses.ShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowRepository
{

    private ApiService apiService;
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();

    public ShowRepository()
    {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ShowResponse> getShow(int page)
    {
        MutableLiveData<ShowResponse> responseMutableLiveData = new MutableLiveData<>();
        apiService.getShow(page).enqueue(new Callback<ShowResponse>()
        {
            @Override
            public void onResponse(Call<ShowResponse> call, Response<ShowResponse> response)
            {
                responseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ShowResponse> call, Throwable t)
            {
                stringMutableLiveData.setValue(t.getMessage());
            }
        });

        return responseMutableLiveData;
    }


}
