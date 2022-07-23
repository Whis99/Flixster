package com.example.flixster.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        }else {
            View movieView2 = LayoutInflater.from(context).inflate(R.layout.high_rated_movie, parent, false);
            return new ViewHolder(movieView2);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie at the passed position
        Movie movie = movies.get(position);
        // Bind movie data into ViewHolder
        holder.bind(movie);

    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        // check movie's vote rate to see if it is a high graded one or not
        if(movies.get(position).getVote_rating() < 5){
            // if it is low type is 0
            type = 0;
        }else{
//            if it's high type is 1
            type = 1;
        }
        return type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView highPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            highPoster = itemView.findViewById(R.id.ivPoster2);
        }

        public void bind(Movie movie) {
            String imageUrl;

            if(getItemViewType() == 0){
                // if movie is a low graded movie
                tvTitle.setText(movie.getTitle());
                tvOverview.setText(movie.getOverview());

                // if phone in landscape mode
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // imageUrl = backdrop image
                    imageUrl = movie.getBackdropPath();
                } else {
                    // else imageUrl = poster image
                    imageUrl = movie.getPosterPath();
                }
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.load_icon)
                        .into(ivPoster);
            }
            else{
                // if it is an high rated movie get only the backdrop image
                imageUrl = movie.getBackdropPath();
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.load_icon)
                        .into(highPoster);
            }

            }

    }

}
