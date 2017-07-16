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

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.popularmoviesstage1.utils.Config.IMAGE_BASE_URL;

/**
 * Created by VijayaLakshmi.IN on 7/14/2017.
 *
 * Showcases the selected movie details
 *
 */
public class MovieDetailFragment extends Fragment {
    @BindView(R.id.movie_title)
    TextView mTitle;

    @BindView(R.id.synopsis)
    TextView mSynopsis;

    @BindView(R.id.userRating)
    TextView mUserRating;

    @BindView(R.id.releaseDate)
    TextView mReleaseDate;

    @BindView(R.id.movie_poster)
    ImageView mMoviePoster;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            Movie selectedMovie;
            if ((selectedMovie = bundle.getParcelable(IBundleKeys.SELECTED_MOVIE)) != null) {
                mTitle.setText(selectedMovie.getOriginalTitle());
                mSynopsis.setText(selectedMovie.getOverview());
                mUserRating.setText(String.valueOf(selectedMovie.getVoteAverage()));
                Picasso.with(getActivity()).load(IMAGE_BASE_URL + selectedMovie.getPosterPath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.image_error)
                        .into(mMoviePoster);
                mReleaseDate.setText(DateFormatter.getDateFormat(selectedMovie.getReleaseDate()));
            }
        }
    }
}
