package com.movies.adapter.searchmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.movies.R;
import com.movies.adapter.showmovies.ShowAdapter;
import com.movies.model.ShowModel;

import java.util.ArrayList;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder>
{

    private ArrayList<ShowModel> showModels;

    public SearchMovieAdapter(ArrayList<ShowModel> showModels)
    {
        this.showModels = showModels;
    }

    @NonNull
    @Override
    public SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, parent, false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieViewHolder holder, int position)
    {
        ShowModel model = showModels.get(position);
        holder.textName.setText(model.getName());
        holder.textNetwork.setText(model.getNetwork());
        holder.textStartData.setText(model.getStartDate());
        holder.textStatus.setText(model.getStatus());
        Glide
                .with(holder.itemView.getContext())
                .load(model.getImageThumbnailPath())
                .into(holder.roundedImageView);
    }

    @Override
    public int getItemCount()
    {
        return showModels.size();
    }

    public class SearchMovieViewHolder extends RecyclerView.ViewHolder
    {

        private RoundedImageView roundedImageView;
        private TextView textName, textNetwork, textStartData, textStatus;

        public SearchMovieViewHolder(@NonNull View itemView)
        {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.item_img_show);
            textName = itemView.findViewById(R.id.item_txt_name_show);
            textNetwork = itemView.findViewById(R.id.item_txt_network_show);
            textStartData = itemView.findViewById(R.id.item_txt_start_data_show);
            textStatus = itemView.findViewById(R.id.item_txt_status_show);
        }
    }
}
