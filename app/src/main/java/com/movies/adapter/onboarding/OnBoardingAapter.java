package com.movies.adapter.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movies.R;
import com.movies.model.OnBoardingModel;

import java.util.List;

public class OnBoardingAapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    List<OnBoardingModel> onBoardingModels;

    public OnBoardingAapter(List<OnBoardingModel> onBoardingModels)
    {
        this.onBoardingModels = onBoardingModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboarding, parent, false);
        return new OnBoardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        OnBoardingModel model = onBoardingModels.get(position);
        OnBoardingViewHolder onBoardingViewHolder = (OnBoardingViewHolder) holder;
        onBoardingViewHolder.imageViewOnBoarding.setImageResource(model.getImageOnBoarding());
        onBoardingViewHolder.textTitleOnBoarding.setText(model.getTitleOnBoarding());
        onBoardingViewHolder.textDescriptionOnBoarding.setText(model.getDescriptionOnBoarding());
    }

    @Override
    public int getItemCount()
    {
        return onBoardingModels.size();
    }


    public class OnBoardingViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView imageViewOnBoarding;
        private TextView textTitleOnBoarding, textDescriptionOnBoarding;

        public OnBoardingViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageViewOnBoarding = itemView.findViewById(R.id.item_img_onboarding);
            textTitleOnBoarding = itemView.findViewById(R.id.item_txt_title);
            textDescriptionOnBoarding = itemView.findViewById(R.id.item_txt_description);
        }
    }
}
