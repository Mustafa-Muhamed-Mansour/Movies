package com.movies.adapter.episodes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movies.R;
import com.movies.model.EpisodeModel;
import com.movies.model.ShowDetailsModel;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>
{

    ArrayList<EpisodeModel> episodeModels;

    public EpisodeAdapter(ArrayList<EpisodeModel> episodeModels)
    {
        this.episodeModels = episodeModels;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position)
    {
       EpisodeModel model = episodeModels.get(position);

       String title = "S";

       String season = model.getSeason();
       if (season.length() == 1)
       {
           season = "0".concat(season);
       }

       String episodeNumber = model.getEpisode();
        if (episodeNumber.length() == 1)
        {
            episodeNumber = "0".concat(episodeNumber);
        }

        episodeNumber = "E".concat(episodeNumber);
        title = title.concat(season).concat(episodeNumber);

        holder.textTitle.setText(title);
        holder.textName.setText(model.getName());
        holder.textAirData.setText(model.getAirDate());
    }

    @Override
    public int getItemCount()
    {
        return episodeModels.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textTitle, textName, textAirData;

        public EpisodeViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textTitle = itemView.findViewById(R.id.item_txt_title_episode);
            textName = itemView.findViewById(R.id.item_txt_name_episode);
            textAirData = itemView.findViewById(R.id.item_txt_air_data_episode);
        }
    }
}
