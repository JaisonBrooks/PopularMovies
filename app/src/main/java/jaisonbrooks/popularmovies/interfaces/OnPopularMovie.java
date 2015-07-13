package jaisonbrooks.popularmovies.interfaces;

import jaisonbrooks.popularmovies.models.PopularMoviesParcel;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public interface OnPopularMovie {
    void OnSuccess(PopularMoviesParcel popularMovies);
    void OnError();
}
