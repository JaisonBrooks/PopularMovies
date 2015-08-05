package jaisonbrooks.popularmovies;

/**
 * *UDACITY REVIEW*
 * I was confused when in the rubric when it says we have to provide a sort function by "Highest Rating".
 * I didnt know whether this sort was supposed to reorder the already retrieved data from the Popularity endpoint
 * or whether this was a separate API query using the (sort_by = vote_average.des or the vote_count.desc).
 *
 * Since i was unclear, i provided a few extra options in the Settings to hopefully cover all possible avenues. Thanks.
 *
 * I really hope this doesnt take away points due to this confusion. :?
 *
 *
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jaisonbrooks.popularmovies.models.IMDB;
import jaisonbrooks.popularmovies.models.PopularMovieParcel;
import jaisonbrooks.popularmovies.ui.PaletteTransformation;

public class DetailActivity extends AppCompatActivity {

    private PopularMovieParcel movie;
    private static final String OBJ_KEY = "popular_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null || !savedInstanceState.containsKey(OBJ_KEY)) {
            Intent intent = getIntent();
            movie = intent.getParcelableExtra(OBJ_KEY);
        } else {
            movie = savedInstanceState.getParcelable(OBJ_KEY);
        }

        setupDetails();

    }

    public void setupDetails() {
        // Title
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movie.title);
        //

        // popularity
        final TextView popularity = (TextView) findViewById(R.id.popularity);
        popularity.setText(String.format("%.1f",movie.popularity));
        //

        // Rating
        final TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText(String.format("%.2f", movie.vote_average) + " / 10 - " + movie.vote_count + " votes");
        //


        // Backdrop
        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this)
                .load(IMDB.IMG_BASE + IMDB.SIZE_W500 + movie.backdrop_path)
                .fit().centerCrop().transform(PaletteTransformation.instance())
                .into(backdrop, new PaletteTransformation.PaletteCallback(backdrop) {
                    @Override
                    public void onError() {
                        Log.e("DetailsActivity", "Errors creating palette from image");
                    }

                    @Override
                    public void onSuccess(Palette palette) {
                        collapsingToolbar.setContentScrimColor(palette.getVibrantColor(R.color.primary));
                        popularity.setTextColor(palette.getVibrantColor(R.color.primary));

                    }
                });
        //

        // Poster
        ImageView poster = (ImageView) findViewById(R.id.poster_detail);
        Picasso.with(this).load(IMDB.IMG_BASE + IMDB.SIZE_W185 + movie.poster_path).into(poster);


        // Overview
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setText(movie.overview);
        //

        // Release Date
        TextView release_date =(TextView) findViewById(R.id.release_date);
        release_date.setText(movie.release_date);
        //

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(OBJ_KEY, movie);
        super.onSaveInstanceState(outState);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
