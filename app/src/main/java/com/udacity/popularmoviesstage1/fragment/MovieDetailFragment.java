package com.udacity.popularmoviesstage1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmoviesstage1.R;
import com.udacity.popularmoviesstage1.dto.Movie;
import com.udacity.popularmoviesstage1.utils.DateFormatter;
import com.udacity.popularmoviesstage1.utils.IBundleKeys;

import static com.udacity.popularmoviesstage1.utils.Config.IMAGE_BASE_URL;

/**
 * Created by VijayaLakshmi.IN on 7/14/2017.
 *
 * Showcases the selected movie details
 *
 */
public class MovieDetailFragment extends Fragment {

    private Movie mSelectedMovie;
    private TextView mTitle;
    private TextView mSynopsis;
    private TextView mUserRating;
    private TextView mReleaseDate;
    private ImageView mMoviePoster;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details, null, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if ((mSelectedMovie = bundle.getParcelable(IBundleKeys.SELECTED_MOVIE)) != null) {
                mTitle.setText(mSelectedMovie.getOriginalTitle());
                mSynopsis.setText(mSelectedMovie.getOverview());
                mUserRating.setText(String.valueOf(mSelectedMovie.getVoteAverage()));
                Picasso.with(getActivity()).load(IMAGE_BASE_URL + mSelectedMovie.getPosterPath()).into(mMoviePoster);
                mReleaseDate.setText(DateFormatter.getDateFormat(mSelectedMovie.getReleaseDate()));
            }
        }
    }

    private void initViews() {
        mTitle = (TextView) getView().findViewById(R.id.movie_title);
        mSynopsis = (TextView) getView().findViewById(R.id.synopsis);
        mUserRating = (TextView) getView().findViewById(R.id.userRating);
        mReleaseDate = (TextView) getView().findViewById(R.id.releaseDate);
        mMoviePoster = (ImageView) getView().findViewById(R.id.movie_poster);
    }
}
