package com.udacity.popularmoviesstage1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.popularmoviesstage1.R;
import com.udacity.popularmoviesstage1.adapter.MovieRecyclerViewAdapter;
import com.udacity.popularmoviesstage1.dto.Movie;
import com.udacity.popularmoviesstage1.dto.MovieList;
import com.udacity.popularmoviesstage1.io.IOManager;
import com.udacity.popularmoviesstage1.utils.DialogUtility;
import com.udacity.popularmoviesstage1.utils.NetworkUtility;
import com.udacity.popularmoviesstage1.utils.SortType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VijayaLakshmi.IN on 7/14/2017.
 *
 * Showcases the list of movies based on SortType
 */
public class MovieListFragment extends Fragment implements MovieRecyclerViewAdapter.MovieItemClickListener {

    private static final int MIN_GRID_SIZE = 2;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;
    private final List<Movie> mMovieList = new ArrayList<>();
    private IMovieListFragmentListener mMovieListFragmentListener;

    public interface IMovieListFragmentListener {
        void onMovieSelected(Movie selectedMovie);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (! (context instanceof IMovieListFragmentListener)) {
            throw new IllegalStateException(((FragmentActivity)context).getClass().getSimpleName() +  "must implement" + IMovieListFragmentListener.class.getSimpleName());
        }
        mMovieListFragmentListener = (IMovieListFragmentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.movie_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null && getView() != null) {

            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));

            mMovieRecyclerViewAdapter = new MovieRecyclerViewAdapter(getActivity(), mMovieList, this);
            mRecyclerView.setAdapter(mMovieRecyclerViewAdapter);

            if (mMovieList == null || mMovieList.isEmpty()) {
                requestMovies(SortType.POPULAR);
            }
        }
    }

    /**
     * Request movie list based on the selected sort type.
     * @param sortType
     */
    public void requestMovies(int sortType) {
        if (NetworkUtility.isInternetConnected(getActivity())) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            IOManager.requestMovies(sortType, new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    mProgressBar.setVisibility(View.GONE);
                    if (response != null && response.body() != null) {
                        mMovieList.clear();
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mMovieList.addAll(response.body().getMovies());
                        mMovieRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    mProgressBar.setVisibility(View.GONE);
                    DialogUtility.showToast(getActivity(), getString(R.string.generic_error));
                }
            });
        } else {
            DialogUtility.showToast(getActivity(), getString(R.string.no_internet_connection));
        }
    }

    private int numberOfColumns() {
        int nColumns = 0;

        if (getActivity() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int widthDivider = 400;
            int width = displayMetrics.widthPixels;
            nColumns = width / widthDivider;

            if (nColumns < MIN_GRID_SIZE) return MIN_GRID_SIZE;
        }

        return nColumns;
    }

    @Override
    public void onItemClick(int position) {
        mMovieListFragmentListener.onMovieSelected(mMovieList.get(position));
    }
}
