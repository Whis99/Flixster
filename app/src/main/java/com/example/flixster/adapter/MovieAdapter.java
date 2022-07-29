package com.example.flixster.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.HighRatedMovieBinding;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public static Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infleter = LayoutInflater.from(context);
        if(viewType == 0){
            ItemMovieBinding movieView = DataBindingUtil.inflate(infleter, R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        }else {
            HighRatedMovieBinding movieView2 = DataBindingUtil.inflate(infleter, R.layout.high_rated_movie, parent, false);
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
        if((float) movies.get(position).getVote_rating() < 7.5){
            // if it is low type is 0
            type = 0;
        }else{
//            if it's high type is 1
            type = 1;
        }
        return type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding movieBinding;
        HighRatedMovieBinding ratedMovieBinding;

        public ViewHolder(@NonNull HighRatedMovieBinding ratedMovieBinding) {
            super(ratedMovieBinding.getRoot());

            this.ratedMovieBinding = ratedMovieBinding;
        }

        public ViewHolder(@NonNull  ItemMovieBinding movieBinding) {
            super(movieBinding.getRoot());

            this.movieBinding = movieBinding;
        }

        public void bind(Movie movie) {

            if(getItemViewType() == 0){
                // Low rated movie
                movieBinding.setMovie(movie);
                movieBinding.executePendingBindings();
                movieBinding.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("movie", Parcels.wrap(movie));
                        context.startActivity(intent);
                    }
                });
            }
            else{
                // high rated movie
                ratedMovieBinding.setMovie(movie);
                ratedMovieBinding.executePendingBindings();

                ratedMovieBinding.container2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Intent intent2 = new Intent(context, DetailActivity.class);
                    intent2.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent2);

                        ActivityOptions options = ActivityOptions.
                                makeSceneTransitionAnimation((Activity) context, ratedMovieBinding.ivPoster2, "activityTransition");
                        context.startActivity(intent2, options.toBundle());
//                        startActivity(intent, options.toBundle());
                    }
                });
            }

            }

    }
    public static class BindingAdapterUtils {
        static int radius = 40;

        @BindingAdapter({"imageUrl"})
        public static void loadImage(ImageView view, String url) {

            Glide.with(context)
                    .load(url)
                    .fitCenter()
                    .transform(new RoundedCorners(radius))
                    .placeholder(R.drawable.load_icon)
                    .into(view);
        }

        @BindingAdapter({"imageUrl2"})
        public static void loadImage2(ImageView view, String url) {

            Glide.with(context)
                    .load(url)
                    .fitCenter()
                    .transform(new RoundedCorners(radius))
                    .placeholder(R.drawable.load_icon)
                    .into(view);
        }
    }

}
