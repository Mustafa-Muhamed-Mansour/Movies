package com.movies.showdetails;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movies.R;
import com.movies.adapter.imageslider.ImageSliderAdapter;
import com.movies.databinding.ShowDetailsFragmentBinding;
import com.movies.responses.ShowDetailsResponse;

public class ShowDetailsFragment extends Fragment
{

    private ShowDetailsFragmentBinding showDetailsFragmentBinding;
    private NavController navController;
    private ShowDetailsViewModel showDetailsViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        showDetailsFragmentBinding = ShowDetailsFragmentBinding.inflate(inflater, container, false);
        return showDetailsFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        showDetailsViewModel = new ViewModelProvider(requireActivity()).get(ShowDetailsViewModel.class);



        initialViews();
        retriveBundle();
        getShowDetails();


    }

    private void initialViews()
    {
        showDetailsFragmentBinding.progressLoadingDetailsMovie.setVisibility(View.VISIBLE);
    }

    private void retriveBundle()
    {
        String showDetailsName = getArguments().getString("name");
        String showDetailsStartDate = getArguments().getString("startDate");
        String showDetailsCountry = getArguments().getString("country");
        String showDetailsNetwork = getArguments().getString("network");
        String showDetailsStatus = getArguments().getString("status");
    }

    private void getShowDetails()
    {
        String showDetailsID = getArguments().getString("id");

        showDetailsViewModel.getShowDetails(showDetailsID).observe(getViewLifecycleOwner(), new Observer<ShowDetailsResponse>()
        {
            @Override
            public void onChanged(ShowDetailsResponse showDetailsResponse)
            {
                showDetailsFragmentBinding.progressLoadingDetailsMovie.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), showDetailsResponse.getShowDetailsModel().getImagePath(), Toast.LENGTH_SHORT).show();
                loadingImageSlider(showDetailsResponse.getShowDetailsModel().getPictures());
                if (showDetailsResponse.getShowDetailsModel() != null)
                {
                    if (showDetailsResponse.getShowDetailsModel().getPictures() != null)
                    {
                        loadingImageSlider(showDetailsResponse.getShowDetailsModel().getPictures());
                    }
                }
            }
        });
    }

    private void loadingImageSlider(String[] imageSlider)
    {
        showDetailsFragmentBinding.viewPagerImgSlider.setOffscreenPageLimit(1);
        showDetailsFragmentBinding.viewPagerImgSlider.setAdapter(new ImageSliderAdapter(imageSlider));
        showDetailsFragmentBinding.viewPagerImgSlider.setVisibility(View.VISIBLE);
//        showDetailsFragmentBinding.viewBgShowDetails.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onDestroyView()
//    {
//        super.onDestroyView();
//
//        showDetailsViewModel.getShowDetails(showDetailsID).removeObservers(getViewLifecycleOwner());
//    }
}