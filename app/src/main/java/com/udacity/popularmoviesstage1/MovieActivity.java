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

public class MovieActivity extends AppCompatActivity implements MovieListFragment.IMovieListFragmentListener, FragmentManager.OnBackStackChangedListener {

    private static final String TAG = MovieActivity.class.getSimpleName();

    private MovieListFragment mMovieListFragment;
    private Menu mMenu;
    private ActionBar mActionBar;

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

    public void showPopup(){
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
        if (fragment != null && mMenu != null) {
            mActionBar = getSupportActionBar();
            if (fragment instanceof MovieListFragment) {
                mActionBar.setTitle(R.string.app_name);
                mActionBar.setDisplayHomeAsUpEnabled(false);
                mActionBar.setDisplayShowHomeEnabled(false);
                mMenu.findItem(R.id.sort_by).setVisible(true);
            } else {
                mActionBar.setTitle(R.string.movie_detail);
                mMenu.findItem(R.id.sort_by).setVisible(false);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setDisplayShowHomeEnabled(true);
            }
        }
    }
}
