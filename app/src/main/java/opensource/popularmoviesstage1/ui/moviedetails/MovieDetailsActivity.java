package opensource.popularmoviesstage1.ui.moviedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;

import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.Result;
import opensource.popularmoviesstage1.utils.ActivityUtils;
import timber.log.Timber;

/**
 * Created by Rajan Maurya on 2/5/16.
 */
public class MovieDetailsActivity extends AppCompatActivity {


    public MovieDetailsActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        Result result = (new Gson()).fromJson(intent.getStringExtra("MOVIE_DETAILS"), Result.class);

        //Setting the Fragment to FrameLayout
        MovieDetailsFragment mainFragment = (MovieDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_container);
        if (mainFragment == null) {
            // Create the fragment
            mainFragment = new MovieDetailsFragment(result);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mainFragment, R.id.frame_container);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Timber.d("Home pressed");
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
