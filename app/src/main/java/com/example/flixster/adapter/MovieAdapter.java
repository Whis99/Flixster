package com.example.flixster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
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

    public List<Movie> getMovies() {
        return movies;
    }

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
//        TextView tvTitle;
//        TextView tvOverview;
//        ImageView ivPoster;
//        ImageView highPoster;
//        RelativeLayout container_layout;
//        RelativeLayout container_layout2;

        ItemMovieBinding movieBinding;
        HighRatedMovieBinding ratedMovieBinding;

        public ViewHolder(@NonNull HighRatedMovieBinding ratedMovieBinding) {
            super(ratedMovieBinding.getRoot());
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvOverview = itemView.findViewById(R.id.tvOverview);
//            ivPoster = itemView.findViewById(R.id.ivPoster);
//            highPoster = itemView.findViewById(R.id.ivPoster2);
//            container_layout = itemView.findViewById(R.id.container);
//            container_layout2 = itemView.findViewById(R.id.container2);

            this.ratedMovieBinding = ratedMovieBinding;
        }

        public ViewHolder(@NonNull  ItemMovieBinding movieBinding) {
            super(movieBinding.getRoot());
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvOverview = itemView.findViewById(R.id.tvOverview);
//            ivPoster = itemView.findViewById(R.id.ivPoster);
//            highPoster = itemView.findViewById(R.id.ivPoster2);
//            container_layout = itemView.findViewById(R.id.container);
//            container_layout2 = itemView.findViewById(R.id.container2);

            this.movieBinding = movieBinding;
        }

        public void bind(Movie movie) {
            String imageUrl;
            int radius = 20;

            if(getItemViewType() == 0){
                // if movie is a low graded movie
//                tvTitle.setText(movie.getTitle());
//                tvOverview.setText(movie.getOverview());

                // if phone in landscape mode
//                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    // imageUrl = backdrop image
//                    imageUrl = movie.getBackdropPath();
//                } else {
//                    // else imageUrl = poster image
//                    imageUrl = movie.getPosterPath();
//                }
//                Glide.with(context)
//                        .load(imageUrl)
//                        .fitCenter()
//                        .transform(new RoundedCorners(radius))
//                        .placeholder(R.drawable.load_icon)
//                        .into(ivPoster);

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
                // if it is an high rated movie get only the backdrop image

                ratedMovieBinding.container2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Intent intent2 = new Intent(context, DetailActivity.class);
                    intent2.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent2);
                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            }

    }
    public static class BindingAdapterUtils {
        @BindingAdapter({"imageUrl"})
        public static void loadImage(ImageView view, String url) {
            int radius = 20;

            Glide.with(context)
                    .load(url)
                    .fitCenter()
                    .transform(new RoundedCorners(radius))
                    .placeholder(R.drawable.load_icon)
                    .into(view);
        }

    }

}
