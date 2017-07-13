package com.udacity.popularmoviesstage1.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static void requestPopularMovies(Callback<String> callback) {
        Retrofit retrofit = getRetrofit();
        APIService apiService = retrofit.create(APIService.class);

        Call<String> call = apiService.requestPopularMovies("91c5924150c1a4b649f2240dfd6603a5");
        call.enqueue(callback);
    }
}
