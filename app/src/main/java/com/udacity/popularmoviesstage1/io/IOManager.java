package com.udacity.popularmoviesstage1.io;

import com.udacity.popularmoviesstage1.dto.MovieList;
import com.udacity.popularmoviesstage1.utils.Config;
import com.udacity.popularmoviesstage1.utils.SortType;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Accolite on 7/13/2017.
 */

public class IOManager {

    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void requestMovies(SortType sortType, Callback<MovieList> callback) {
        Retrofit retrofit = getRetrofit();
        APIService apiService = retrofit.create(APIService.class);
        Call<MovieList> call;

        switch (sortType) {
            case TOP_RATED:
                call = apiService.requestTopRatedMovies(Config.API_KEY);
                break;

            default:
                call = apiService.requestPopularMovies(Config.API_KEY);
                break;
        }

        call.enqueue(callback);
    }
}
