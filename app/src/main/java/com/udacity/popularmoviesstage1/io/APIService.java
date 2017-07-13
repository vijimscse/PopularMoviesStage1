package com.udacity.popularmoviesstage1.io;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Accolite on 7/13/2017.
 */

public interface APIService {

    @GET("popular")
    Call<String> requestPopularMovies(@Query("api_key") String apiKey);

}
