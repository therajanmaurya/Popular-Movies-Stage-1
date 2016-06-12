package opensource.popularmoviesstage1.ui.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.utils.ActivityUtils;

public class MoviesActivity extends AppCompatActivity {

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
}
