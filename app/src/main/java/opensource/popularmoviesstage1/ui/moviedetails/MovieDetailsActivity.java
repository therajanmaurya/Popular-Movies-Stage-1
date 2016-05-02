package opensource.popularmoviesstage1.ui.moviedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.Result;
import opensource.popularmoviesstage1.utils.ActivityUtils;

/**
 * Created by Rajan Maurya on 2/5/16.
 */
public class MovieDetailsActivity extends AppCompatActivity {

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
}