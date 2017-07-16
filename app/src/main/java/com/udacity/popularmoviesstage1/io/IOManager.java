package com.udacity.popularmoviesstage1.io;

import com.udacity.popularmoviesstage1.BuildConfig;
import com.udacity.popularmoviesstage1.dto.MovieList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.udacity.popularmoviesstage1.utils.Config.BASE_URL;
import static com.udacity.popularmoviesstage1.utils.SortType.TOP_RATED;

/**
 * Created by Vijayalakshmi.IN on 7/13/2017.
 */

public class IOManager {

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

    public static void requestMovies(int sortType, Callback<MovieList> callback) {
        Retrofit retrofit = getRetrofit();
        APIService apiService = retrofit.create(APIService.class);
        Call<MovieList> call;

        switch (sortType) {
            case TOP_RATED:
                call = apiService.requestTopRatedMovies(BuildConfig.API_KEY);
                break;

            default:
                call = apiService.requestPopularMovies(BuildConfig.API_KEY);
                break;
        }

        call.enqueue(callback);
    }
}
