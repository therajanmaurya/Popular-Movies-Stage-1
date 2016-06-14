package opensource.popularmoviesstage1.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;

import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.MovieResult;
import opensource.popularmoviesstage1.ui.interfaces.ItemClickCallback;
import opensource.popularmoviesstage1.ui.moviedetails.MovieDetailsActivity;
import opensource.popularmoviesstage1.ui.moviedetails.MovieDetailsFragment;
import opensource.popularmoviesstage1.utils.ActivityUtils;

public class MoviesActivity extends AppCompatActivity implements ItemClickCallback{

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Setting the Fragment to FrameLayout
        MoviesFragment mainFragment = (MoviesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_container);
        if (mainFragment == null) {
            // Create the fragment
            mainFragment = MoviesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mainFragment, R.id.frame_container);
        }

        if (findViewById(R.id.details) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(MovieResult movie) {
        if (mTwoPane) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details, MovieDetailsFragment.newInstance(movie))
                    .commit();
        } else {

            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(getString(R.string.extra_details), (new Gson()).toJson(movie));
            startActivity(intent);

        }
    }
}
