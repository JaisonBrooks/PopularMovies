package jaisonbrooks.popularmovies.models;

import java.util.Comparator;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/13/15
 */
public class PopularMovieSort implements Comparator<PopularMovieParcel> {

    @Override
    public int compare(PopularMovieParcel left, PopularMovieParcel right) {
        if (left.vote_average < right.vote_average) return -1;
        if (left.vote_average > right.vote_average) return 1;

        return 0;
    }
}
