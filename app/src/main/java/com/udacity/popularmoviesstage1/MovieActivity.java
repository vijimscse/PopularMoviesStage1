package com.udacity.popularmoviesstage1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.udacity.popularmoviesstage1.dto.Movie;
import com.udacity.popularmoviesstage1.fragment.MovieDetailFragment;
import com.udacity.popularmoviesstage1.fragment.MovieListFragment;
import com.udacity.popularmoviesstage1.utils.SortType;

import static com.udacity.popularmoviesstage1.utils.IBundleKeys.SELECTED_MOVIE;

/**
 * Created by Vijayalakshmi.IN on 14/07/2017
 * <p>
 * Movie screen displays the list of movies and its details
 */
public class MovieActivity extends AppCompatActivity implements MovieListFragment.IMovieListFragmentListener, FragmentManager.OnBackStackChangedListener {

    private static final String TAG = MovieActivity.class.getSimpleName();

    private MovieListFragment mMovieListFragment;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMovieListFragment();

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void addMovieListFragment() {
        if (mMenu != null) {
            mMenu.findItem(R.id.sort_by).setVisible(false);
        }
        mMovieListFragment = new MovieListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, mMovieListFragment);
        transaction.commit();
    }

    private void addMovieDetailFragment(Movie selectedMovie) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_MOVIE, selectedMovie);
        movieDetailFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, movieDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_options, menu);

        return true;
    }

    private void showPopup() {
        View menuItemView = findViewById(R.id.sort_by);
        PopupMenu popup = new PopupMenu(this, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.sort_menu_options, popup.getMenu());

        popup.setOnMenuItemClickListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.popular:
                            mMovieListFragment.requestMovies(SortType.POPULAR);
                            break;

                        case R.id.top_rated:
                            mMovieListFragment.requestMovies(SortType.TOP_RATED);
                            break;
                    }
                    return true;
                });

        popup.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by:
                showPopup();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onMovieSelected(Movie selectedMovie) {
        addMovieDetailFragment(selectedMovie);
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        ActionBar actionBar = getSupportActionBar();

        if (fragment != null && mMenu != null && actionBar != null) {
            if (fragment instanceof MovieListFragment) {
                actionBar.setTitle(R.string.app_name);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                mMenu.findItem(R.id.sort_by).setVisible(true);
            } else {
                actionBar.setTitle(R.string.movie_detail);
                mMenu.findItem(R.id.sort_by).setVisible(false);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
        }
    }
}
