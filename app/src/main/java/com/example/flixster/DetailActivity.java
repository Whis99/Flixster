package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapter.MovieAdapter;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String  YOUTUBE_API_KEY = "AIzaSyAK_9HjfzPhHsYzgKiZl61KFKLEpC1EhaQ";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    YouTubePlayerView youTubePlayerView;
    Movie movie_category;
    ActivityDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);


        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.ytubePlayer);
        movie_category = new Movie();


        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        binding.setMovie(movie);
        binding.executePendingBindings();


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length() == 0){
                        return;
                    }

                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubeKey);
                    initializeYoutube(youtubeKey,(float) movie.getVote_rating());

                } catch (JSONException e) {
                    Log.e("detailActivity", "Failed to parse JSON", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }
    private void initializeYoutube(final String youtubeKey, final float movieRating){
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        if(movieRating < 7.5){
                            Log.d("DetailActivity", "onInitializationSuccess");
                            youTubePlayer.cueVideo(youtubeKey);
                        }
                        else {
                            Log.d("DetailActivity", "onInitializationSuccess");
                            youTubePlayer.loadVideo(youtubeKey);
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("DetailActivity", "onInitializationFailure");
                    }
                }

        );
    }
}