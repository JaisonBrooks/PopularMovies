package jaisonbrooks.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public class PopularMovieParcel implements Parcelable {
    public boolean adult;
    public String backdrop_path;
    public int[] genre_ids;
    public int id;
    public String original_language;
    public String original_title;
    public String overview;
    public String release_date;
    public String poster_path; // Image path <
    public double popularity;
    public String title;
    public boolean video;
    public float vote_average;
    public int vote_count;

    public PopularMovieParcel(Parcel in) {
        this.adult = in.readByte() != 0;
        this.backdrop_path = in.readString();
        this.genre_ids = in.createIntArray();
        this.id = in.readInt();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.popularity = in.readDouble();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.vote_average = in.readFloat();
        this.vote_count = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeIntArray(genre_ids);
        dest.writeInt(id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeFloat(vote_average);
        dest.writeInt(vote_count);
    }


// Didnt get to the local soring :/
    /*@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PopularMovieParcel other = (PopularMovieParcel) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
*/


    public static final Parcelable.Creator<PopularMovieParcel> CREATOR = new Parcelable.Creator<PopularMovieParcel>() {
        @Override
        public PopularMovieParcel createFromParcel(Parcel source) {
            return new PopularMovieParcel(source);
        }

        @Override
        public PopularMovieParcel[] newArray(int size) {
            return new PopularMovieParcel[size];
        }
    };
}
