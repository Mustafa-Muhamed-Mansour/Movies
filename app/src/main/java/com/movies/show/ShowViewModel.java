package com.movies.show;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.movies.repositories.ShowRepository;
import com.movies.responses.ShowResponse;

public class ShowViewModel extends ViewModel
{

    private ShowRepository showRepository;

    public ShowViewModel()
    {
        showRepository = new ShowRepository();
    }

    public LiveData<ShowResponse> getShow(int page)
    {
        return showRepository.getShow(page);
    }

}