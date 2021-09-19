package com.movies.showdetails;

import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.movies.R;
import com.movies.adapter.episodes.EpisodeAdapter;
import com.movies.adapter.imageslider.ImageSliderAdapter;
import com.movies.databinding.BottomSheetEpisodesBinding;
import com.movies.databinding.ShowDetailsFragmentBinding;
import com.movies.responses.ShowDetailsResponse;

import java.util.Locale;

public class ShowDetailsFragment extends Fragment
{

    private ShowDetailsFragmentBinding showDetailsFragmentBinding;
    private NavController navController;
    private ShowDetailsViewModel showDetailsViewModel;
    private int showDetailsID;
    private BottomSheetDialog bottomSheet;
    private BottomSheetEpisodesBinding bottomSheetEpisodesBinding;


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
        clickedViews();


    }

    private void initialViews()
    {
        showDetailsFragmentBinding.progressLoadingDetailsMovie.setVisibility(View.VISIBLE);
        showDetailsFragmentBinding.roundImgMovie.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void retriveBundle()
    {
        String showDetailsName = getArguments().getString("name");
        String showDetailsStartDate = getArguments().getString("startDate");
        String showDetailsCountry = getArguments().getString("country");
        String showDetailsNetwork = getArguments().getString("network");
        String showDetailsStatus = getArguments().getString("status");
        String showDetailsImageThumbnailPath = getArguments().getString("imageThumbnailPath");

        showDetailsFragmentBinding.txtNameShow.setText(showDetailsName);
        showDetailsFragmentBinding.txtNetworkShow.setText(showDetailsNetwork + " (" + showDetailsCountry + ")");
        showDetailsFragmentBinding.txtStartDataShow.setText(showDetailsStartDate);
        showDetailsFragmentBinding.txtStatusShow.setText(showDetailsStatus);
        Glide
                .with(getActivity())
                .load(showDetailsImageThumbnailPath)
                .into(showDetailsFragmentBinding.roundImgMovie);

    }

    private void getShowDetails()
    {
        showDetailsID = getArguments().getInt("id");

        showDetailsViewModel.getShowDetails(showDetailsID + "").observe(getViewLifecycleOwner(), new Observer<ShowDetailsResponse>()
        {
            @Override
            public void onChanged(ShowDetailsResponse showDetailsResponse)
            {
//                showDetailsFragmentBinding.setLoadingDetailsMovies(true);
                showDetailsFragmentBinding.progressLoadingDetailsMovie.setVisibility(View.GONE);
//                showDetailsFragmentBinding.setLoadingDetailsMovies(false);
                if (showDetailsResponse.getShowDetailsModel() != null)
                {
                    if (showDetailsResponse.getShowDetailsModel().getPictures() != null)
                    {
                        loadingImageSlider(showDetailsResponse.getShowDetailsModel().getPictures());
                    }
                }
                showDetailsFragmentBinding.txtDescriptionShow.setText(String.valueOf(HtmlCompat.fromHtml(showDetailsResponse.getShowDetailsModel().getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY)));
                showDetailsFragmentBinding.txtRating.setText(String.format(Locale.getDefault(), "%.1f", Double.parseDouble(showDetailsResponse.getShowDetailsModel().getRating())));

                if (showDetailsResponse.getShowDetailsModel() != null)
                {
                    if (showDetailsResponse.getShowDetailsModel().getGenres() != null)
                    {
                        showDetailsFragmentBinding.txtGenre.setText(showDetailsResponse.getShowDetailsModel().getGenres()[0]);
                    }

                    else
                    {
                        showDetailsFragmentBinding.txtGenre.setText("N/A");
                    }
                }

                else
                {
                    showDetailsFragmentBinding.txtGenre.setText("N/A");
                }

                showDetailsFragmentBinding.txtRuntime.setText(showDetailsResponse.getShowDetailsModel().getRunTime() + " Min");
                showDetailsFragmentBinding.viewBgOneShowDetails.setVisibility(View.VISIBLE);
                showDetailsFragmentBinding.viewBgTwoShowDetails.setVisibility(View.VISIBLE);
                showDetailsFragmentBinding.viewOne.setVisibility(View.VISIBLE);
                showDetailsFragmentBinding.viewTwo.setVisibility(View.VISIBLE);

                showDetailsFragmentBinding.btnWebsite.setVisibility(View.VISIBLE);
                showDetailsFragmentBinding.btnWebsite.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intentWebsite = new Intent(Intent.ACTION_VIEW);
                        intentWebsite.setData(Uri.parse(showDetailsResponse.getShowDetailsModel().getUrl()));
                        startActivity(intentWebsite);
                    }
                });

                showDetailsFragmentBinding.btmEpisode.setVisibility(View.VISIBLE);
                showDetailsFragmentBinding.btmEpisode.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
//                        if (bottomSheet == null)
//                        {
//
//                        }

                        bottomSheet = new BottomSheetDialog(getActivity());
                        bottomSheetEpisodesBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.bottom_sheet_episodes, getActivity().findViewById(R.id.linear_layout_episode), false);
                        bottomSheet.setContentView(bottomSheetEpisodesBinding.getRoot());
                        bottomSheetEpisodesBinding.rVEpisode.setAdapter(new EpisodeAdapter(showDetailsResponse.getShowDetailsModel().getEpisodeModels()));
                        bottomSheetEpisodesBinding.rVEpisode.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        bottomSheetEpisodesBinding.txtTitle.setText(String.format("Episodes | %s", getArguments().getString("name")));

                        bottomSheetEpisodesBinding.imgClose.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                bottomSheet.dismiss();
                            }
                        });

                        FrameLayout frameLayout = bottomSheet.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        BottomSheetBehavior<View> viewBottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                        viewBottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                        viewBottomSheetBehavior.setState(viewBottomSheetBehavior.STATE_EXPANDED);

//                        if (frameLayout != null)
//                        {
//                            BottomSheetBehavior<View> viewBottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
//                            viewBottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
//                            viewBottomSheetBehavior.setState(viewBottomSheetBehavior.STATE_EXPANDED);
//                        }
                        bottomSheet.show();
                    }
                });
            }
        });
    }

    private void loadingImageSlider(String[] imageSlider)
    {
        showDetailsFragmentBinding.viewPagerImgSlider.setOffscreenPageLimit(1);
        showDetailsFragmentBinding.viewPagerImgSlider.setAdapter(new ImageSliderAdapter(imageSlider));
        showDetailsFragmentBinding.viewPagerImgSlider.setVisibility(View.VISIBLE);
        showDetailsFragmentBinding.viewBgImgSliderShowDetails.setVisibility(View.VISIBLE);
        indicatorLinearLayout(imageSlider.length);
        showDetailsFragmentBinding.viewPagerImgSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                setCurrentImageSliderIndicator(position);
            }
        });
    }

    private void indicatorLinearLayout(int count)
    {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        for (int i = 0; i < indicators.length; i++)
        {
            indicators[i] = new ImageView(getActivity());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            showDetailsFragmentBinding.linearLayoutSliderIndicator.addView(indicators[i]);
        }
        showDetailsFragmentBinding.linearLayoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentImageSliderIndicator(0);
    }

    private void setCurrentImageSliderIndicator(int position)
    {
        int childCount = showDetailsFragmentBinding.linearLayoutSliderIndicator.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            ImageView imageView = (ImageView) showDetailsFragmentBinding.linearLayoutSliderIndicator.getChildAt(i);
            if (i == position)
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.onboarding_indicator_active));
            }
            else
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.onboarding_indicator_inactive));
            }
        }
    }

    private void clickedViews()
    {
        showDetailsFragmentBinding.imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                navController.navigate(R.id.action_showDetailsFragment_to_showFragment);
            }
        });

        showDetailsFragmentBinding.txtReadMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (showDetailsFragmentBinding.txtReadMore.getText().toString().equals("Read More"))
                {
                    showDetailsFragmentBinding.txtDescriptionShow.setMaxLines(Integer.MAX_VALUE);
                    showDetailsFragmentBinding.txtDescriptionShow.setEllipsize(null);
                    showDetailsFragmentBinding.txtReadMore.setText("Read Less");
                }

                else
                {
                    showDetailsFragmentBinding.txtDescriptionShow.setMaxLines(3);
                    showDetailsFragmentBinding.txtDescriptionShow.setEllipsize(TextUtils.TruncateAt.END);
                    showDetailsFragmentBinding.txtReadMore.setText("Read More");
                }
            }
        });

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        showDetailsViewModel.getShowDetails(showDetailsID + "").removeObservers(getViewLifecycleOwner());
    }
}