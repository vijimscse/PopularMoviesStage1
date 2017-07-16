
package com.udacity.popularmoviesstage1.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList implements Parcelable {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private final List<Movie> movieList = null;

    public final static Creator<MovieList> CREATOR = new Creator<MovieList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MovieList createFromParcel(Parcel in) {
            MovieList instance = new MovieList();
            instance.page = in.readInt();
            instance.totalResults = in.readInt();
            instance.totalPages = in.readInt();
            in.readList(instance.movieList, (Movie.class.getClassLoader()));
            return instance;
        }

        public MovieList[] newArray(int size) {
            return (new MovieList[size]);
        }

    }
    ;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getMovies() {
        return movieList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
        dest.writeList(movieList);
    }

    public int describeContents() {
        return  0;
    }

}
