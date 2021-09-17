package com.movies.adapter.showmovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.movies.R;
import com.movies.model.ShowModel;

import java.util.ArrayList;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder>
{

    private ArrayList<ShowModel> showModels;


    public ShowAdapter(ArrayList<ShowModel> showModels)
    {
        this.showModels = showModels;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position)
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

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle showDetailsBundle = new Bundle();
                showDetailsBundle.putString("id", String.valueOf(model.getId()));
                showDetailsBundle.putString("name", model.getName());
                showDetailsBundle.putString("startDate", model.getStartDate());
                showDetailsBundle.putString("country", model.getCountry());
                showDetailsBundle.putString("network", model.getNetwork());
                showDetailsBundle.putString("status", model.getStatus());
                Navigation.findNavController(view).navigate(R.id.action_showFragment_to_showDetailsFragment, showDetailsBundle);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return showModels.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder
    {

        private RoundedImageView roundedImageView;
        private final TextView textName, textNetwork, textStartData, textStatus;

        public ShowViewHolder(@NonNull View itemView)
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
