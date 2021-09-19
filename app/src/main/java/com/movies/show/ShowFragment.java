package com.movies.show;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movies.R;
import com.movies.adapter.showmovies.ShowAdapter;
import com.movies.constants.VariableConstant;
import com.movies.databinding.ShowFragmentBinding;
import com.movies.model.ShowModel;
import com.movies.responses.ShowResponse;

import java.util.ArrayList;


public class ShowFragment extends Fragment
{

    private ShowFragmentBinding showFragmentBinding;
    private NavController navController;
    private ShowViewModel showViewModel;
    private ArrayList<ShowModel> showModels;
    private ShowAdapter showAdapter;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        showFragmentBinding = ShowFragmentBinding.inflate(inflater, container, false);
        return showFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        showViewModel = new ViewModelProvider(requireActivity()).get(ShowViewModel.class);

        initialViews();
        clickedViews();
        getShow();

    }

    private void initialViews()
    {
        showModels = new ArrayList<>();
        showAdapter = new ShowAdapter(showModels);
        showFragmentBinding.rVShow.setAdapter(showAdapter);
        showFragmentBinding.rVShow.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        showFragmentBinding.rVShow.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                if (!showFragmentBinding.rVShow.canScrollVertically(dy))
                {
//                    if (VariableConstant.currentPage <= VariableConstant.totalAvailblePage)
//                    {
//
//                    }

                    VariableConstant.currentPage ++;
                    getShow();
                }
            }
        });
    }

    private void clickedViews()
    {
        showFragmentBinding.imgSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_showFragment_to_searchFragment);
            }
        });
    }

    @SuppressLint("NewApi")
    private void getShow()
    {
        progressLoading();
        showViewModel.getShow(VariableConstant.currentPage).observe(getViewLifecycleOwner(), new Observer<ShowResponse>()
        {
            @SuppressLint("NotifyDataSetChanged")
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
                        showAdapter.notifyItemRangeInserted(oldCount, showModels.size());
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
            if (showFragmentBinding.progressLoadingMovie.isAnimating())
            {
//                showFragmentBinding.setLoadingMovies(false);
                showFragmentBinding.progressLoadingMovie.setVisibility(View.GONE);
            }
            else
            {
//                showFragmentBinding.setLoadingMovies(true);
                showFragmentBinding.progressLoadingMovie.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            if (showFragmentBinding.progressLoadingMovieMore.isAnimating())
            {
//                showFragmentBinding.setLoadingMoviesMore(false);
                showFragmentBinding.progressLoadingMovieMore.setVisibility(View.GONE);
            }
            else
            {
//                showFragmentBinding.setLoadingMoviesMore(true);
                showFragmentBinding.progressLoadingMovieMore.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        showViewModel.getShow(VariableConstant.currentPage).removeObservers(getViewLifecycleOwner());
    }
}