package com.udacity.popularmoviesstage1.io;

import com.udacity.popularmoviesstage1.dto.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Accolite on 7/13/2017.
 */

public interface APIService {

    @GET("popular")
    Call<MovieList> requestPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieList> requestTopRatedMovies(@Query("api_key") String apiKey);

}
