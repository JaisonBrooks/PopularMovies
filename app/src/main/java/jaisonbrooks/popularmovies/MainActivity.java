package jaisonbrooks.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collections;

import jaisonbrooks.popularmovies.adapter.PopularMoviesAdapter;
import jaisonbrooks.popularmovies.async.ExploreMovies;
import jaisonbrooks.popularmovies.interfaces.OnPopularMovie;
import jaisonbrooks.popularmovies.models.PopularMovieSort;
import jaisonbrooks.popularmovies.models.PopularMoviesParcel;
import jaisonbrooks.popularmovies.ui.MarginDecoration;

public class MainActivity extends AppCompatActivity implements OnPopularMovie {

    private PopularMoviesParcel popularMovies;
    private PopularMoviesAdapter moviesAdapter;
    private static final String OBJ_KEY = "popular_movies";
    private String default_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null || !savedInstanceState.containsKey(OBJ_KEY)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            default_sort = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_option_default));
            new ExploreMovies(this).execute(default_sort);

        } else {
            default_sort = savedInstanceState.getString(getString(R.string.pref_sort_key));
            popularMovies = savedInstanceState.getParcelable(OBJ_KEY);
        }

        moviesAdapter = new PopularMoviesAdapter(MainActivity.this, popularMovies);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(OBJ_KEY, popularMovies);
        outState.putString(getString(R.string.pref_sort_key), default_sort);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(settings, RESULT_OK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            // Set the default sort key
            default_sort = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_option_default));
            new ExploreMovies(this).execute(default_sort);
            /*if (default_sort.equals("local_sort")) {
                Collections.sort(popularMovies.results, new PopularMovieSort());
            } else {
                new ExploreMovies(this).execute(default_sort);
            }*/
        }
    }

    @Override
    public void OnSuccess(PopularMoviesParcel movies) {
        popularMovies = movies;
        moviesAdapter.addItems(popularMovies);
    }

    @Override
    public void OnError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_message), Snackbar.LENGTH_LONG).show();
    }
}
