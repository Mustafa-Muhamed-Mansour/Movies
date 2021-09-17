package com.movies.showdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.movies.repositories.ShowDetailsRepository;
import com.movies.responses.ShowDetailsResponse;

public class ShowDetailsViewModel extends ViewModel
{

    private ShowDetailsRepository showDetailsRepository;

    public ShowDetailsViewModel()
    {
        showDetailsRepository = new ShowDetailsRepository();
    }

    public LiveData<ShowDetailsResponse> getShowDetails(String showDetailsID)
    {
        return showDetailsRepository.getShowDetails(showDetailsID);
    }



}