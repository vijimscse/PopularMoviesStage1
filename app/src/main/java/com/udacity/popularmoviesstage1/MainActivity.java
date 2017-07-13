package com.udacity.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.popularmoviesstage1.io.IOManager;
import com.udacity.popularmoviesstage1.utils.DialogUtility;
import com.udacity.popularmoviesstage1.utils.NetworkUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (NetworkUtility.isInternetConnected(this)) {
            IOManager.requestPopularMovies(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null && !TextUtils.isEmpty(response.body())) {
                        Log.d(TAG, response.body());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(TAG, "Something went wrong!!!");
                }
            });
        } else {
            DialogUtility.showToast(this, getString(R.string.no_internet_connection));
        }
    }
}
