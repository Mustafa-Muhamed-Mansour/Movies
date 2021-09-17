package com.movies.adapter.imageslider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.movies.R;
import com.movies.databinding.ItemImageSliderBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>
{

    private String [] imageSlider;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] imageSlider)
    {
        this.imageSlider = imageSlider;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemImageSliderBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_image_slider, parent, false);
        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position)
    {
        holder.imageSliderBinding(imageSlider[position]);
    }

    @Override
    public int getItemCount()
    {
        return imageSlider.length;
    }

    public class ImageSliderViewHolder extends RecyclerView.ViewHolder
    {

        private ItemImageSliderBinding itemImageSliderBinding;

        public ImageSliderViewHolder(ItemImageSliderBinding itemImageSliderBinding)
        {
            super(itemImageSliderBinding.getRoot());
            this.itemImageSliderBinding = itemImageSliderBinding;
        }

        public void imageSliderBinding(String imageURL)
        {
            itemImageSliderBinding.setImageURL(imageURL);
        }
    }
}
