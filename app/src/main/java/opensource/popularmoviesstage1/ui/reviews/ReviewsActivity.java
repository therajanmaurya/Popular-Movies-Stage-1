package opensource.popularmoviesstage1.ui.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.utils.ActivityUtils;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class ReviewsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent intent = getIntent();
        int movieId = intent.getIntExtra("MovieId",269149);

        //Setting the Fragment to FrameLayout
        ReviewsFragment mainFragment = (ReviewsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_container);
        if (mainFragment == null) {
            // Create the fragment
            mainFragment = ReviewsFragment.newInstance(movieId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mainFragment, R.id.frame_container);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
