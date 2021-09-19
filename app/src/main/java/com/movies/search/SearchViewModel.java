package com.movies.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.movies.model.ShowModel;
import com.movies.repositories.SearchShowRepository;
import com.movies.responses.ShowResponse;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel
{

    private SearchShowRepository searchShowRepository;


    public SearchViewModel()
    {
        searchShowRepository = new SearchShowRepository();
    }

    public LiveData<ShowResponse> getSearchShow(String query, int page)
    {
        return searchShowRepository.getSearchShow(query, page);
    }
}