package com.movies.search;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movies.R;
import com.movies.adapter.searchmovies.SearchMovieAdapter;
import com.movies.adapter.showmovies.ShowAdapter;
import com.movies.constants.VariableConstant;
import com.movies.databinding.SearchFragmentBinding;
import com.movies.model.ShowModel;
import com.movies.responses.ShowResponse;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SearchFragment extends Fragment
{

    private SearchFragmentBinding searchFragmentBinding;
    private NavController navController;
    private SearchViewModel searchViewModel;
    private ArrayList<ShowModel> showModels;
    private SearchMovieAdapter searchMovieAdapter;
    private Timer timer;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        searchFragmentBinding = SearchFragmentBinding.inflate(inflater, container, false);
        return searchFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);


        initialViews();
        clickedViews();

    }

    private void initialViews()
    {
        showModels = new ArrayList<>();
        searchMovieAdapter = new SearchMovieAdapter(showModels);
        searchFragmentBinding.rVSearch.setAdapter(searchMovieAdapter);
        searchFragmentBinding.rVSearch.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @SuppressLint("NewApi")
    private void getShowSearch(String query)
    {
        progressLoading();
        searchViewModel.getSearchShow(query, VariableConstant.currentPage).observe(getViewLifecycleOwner(), new Observer<ShowResponse>()
        {
            @Override
            public void onChanged(ShowResponse showResponse)
            {
                progressLoading();
                if (showResponse != null)
                {
//                    VariableConstant.totalAvailblePage = showResponse.getTotalPages();
                    if (showResponse.getShowModels() != null)
                    {
                        int oldCount = showModels.size();
                        showModels.addAll(showResponse.getShowModels());
                        searchMovieAdapter.notifyItemRangeInserted(oldCount, showModels.size());
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void progressLoading()
    {
        if (VariableConstant.currentPage == 1)
        {
            if (searchFragmentBinding.progressSearchLoadingMovie.isAnimating())
            {
//                showFragmentBinding.setLoadingMovies(false);
                searchFragmentBinding.progressSearchLoadingMovie.setVisibility(View.GONE);
            }
            else
            {
//                showFragmentBinding.setLoadingMovies(true);
                searchFragmentBinding.progressSearchLoadingMovie.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            if (searchFragmentBinding.progressSearchLoadingMovieMore.isAnimating())
            {
//                showFragmentBinding.setLoadingMoviesMore(false);
                searchFragmentBinding.progressSearchLoadingMovieMore.setVisibility(View.GONE);
            }
            else
            {
//                showFragmentBinding.setLoadingMoviesMore(true);
                searchFragmentBinding.progressSearchLoadingMovieMore.setVisibility(View.VISIBLE);
            }
        }
    }

    private void clickedViews()
    {
        searchFragmentBinding.imgBackSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_searchFragment_to_showFragment);
            }
        });

        searchFragmentBinding.editSearchMovie.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (timer != null)
                {
                    timer.cancel();
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable editable)
            {
                if (!editable.toString().trim().isEmpty())
                {
                    timer = new Timer();
                    timer.schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            new Handler(Looper.getMainLooper()).post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    VariableConstant.currentPage = 1;
                                    VariableConstant.totalAvailblePage = 1;
                                    getShowSearch(editable.toString());
                                }
                            });
                        }
                    }, 300);
                }

                else
                {
                    showModels.clear();
                    searchMovieAdapter.notifyDataSetChanged();
                }
            }
        });

//        searchFragmentBinding.rVSearch.addOnScrollListener(new RecyclerView.OnScrollListener()
//        {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
//            {
//                super.onScrolled(recyclerView, dx, dy);
//
//                VariableConstant.currentPage++;
//                getShowSearch(searchFragmentBinding.editSearchMovie.getText().toString());
//            }
//        });
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        searchViewModel.getSearchShow(VariableConstant.sQuery, VariableConstant.currentPage).removeObservers(getViewLifecycleOwner());
    }
}