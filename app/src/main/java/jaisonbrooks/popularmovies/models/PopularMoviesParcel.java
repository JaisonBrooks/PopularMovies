package jaisonbrooks.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public class PopularMoviesParcel implements Parcelable {
    public ArrayList<PopularMovieParcel> results = new ArrayList<>();

    public PopularMoviesParcel(Parcel in) {
        in.readTypedList(results, PopularMovieParcel.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    public static final Parcelable.Creator<PopularMoviesParcel> CREATOR = new Parcelable.Creator<PopularMoviesParcel>() {
        @Override
        public PopularMoviesParcel createFromParcel(Parcel source) {
            return new PopularMoviesParcel(source);
        }

        @Override
        public PopularMoviesParcel[] newArray(int size) {
            return new PopularMoviesParcel[size];
        }
    };
}
