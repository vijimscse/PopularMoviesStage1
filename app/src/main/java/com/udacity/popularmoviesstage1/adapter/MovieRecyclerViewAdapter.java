package com.udacity.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmoviesstage1.R;
import com.udacity.popularmoviesstage1.dto.Movie;
import com.udacity.popularmoviesstage1.utils.DateFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.udacity.popularmoviesstage1.utils.Config.IMAGE_BASE_URL;

/**
 * Created by VijayaLakshmi.IN on 7/14/2017.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private final Context mContext;
    private final List<Movie> mMovieList;
    private final MovieItemClickListener mMovieItemClickListener;

    public interface MovieItemClickListener {
        void onItemClick(int position);
    }

    public MovieRecyclerViewAdapter(Context context, List<Movie> movieList, MovieItemClickListener movieItemClickListener) {
        mContext = context;
        mMovieList = movieList;
        mMovieItemClickListener = movieItemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMovieList.get(position);

        movieViewHolder.mTitle.setText(movie.getTitle());
        movieViewHolder.mReleaseDate.setText(DateFormatter.getDateFormat(movie.getReleaseDate()));
        movieViewHolder.mUserRating.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(mContext)
                .load(IMAGE_BASE_URL + movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.image_error)
                .into(movieViewHolder.mPosterPath);
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (mMovieList != null) {
            count = mMovieList.size();
        }

        return count;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_title)
        TextView mTitle;

        @BindView(R.id.poster_path)
        ImageView mPosterPath;

        @BindView(R.id.userRating)
        TextView mUserRating;

        @BindView(R.id.releaseDate)
        TextView mReleaseDate;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.movie_row)
        public void onMovieClick(View view) {
            mMovieItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
