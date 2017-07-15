package com.udacity.popularmoviesstage1.io;

import com.udacity.popularmoviesstage1.dto.MovieList;
import com.udacity.popularmoviesstage1.utils.Config;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.udacity.popularmoviesstage1.utils.Config.POPULAR;
import static com.udacity.popularmoviesstage1.utils.Config.TOP_RATED;

/**
 * Created by Vijayalakshmi.IN on 7/13/2017.
 */

public interface APIService {

    @GET(POPULAR)
    Call<MovieList> requestPopularMovies(@Query(Config.API_KEY) String apiKey);

    @GET(TOP_RATED)
    Call<MovieList> requestTopRatedMovies(@Query(Config.API_KEY) String apiKey);

}
