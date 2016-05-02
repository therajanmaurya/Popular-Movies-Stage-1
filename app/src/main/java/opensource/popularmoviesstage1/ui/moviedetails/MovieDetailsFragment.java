package opensource.popularmoviesstage1.ui.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.Result;

/**
 * Created by Rajan Maurya on 2/5/16.
 */
public class MovieDetailsFragment extends Fragment {


    private Result mMovieDetails;

    @BindView(R.id.iv_image) ImageView mPosterImage;
    @BindView(R.id.tv_title) TextView mTitle;
    @BindView(R.id.plot) TextView mPlot;
    @BindView(R.id.rating) TextView mRating;
    @BindView(R.id.rdate) TextView mReleaseDate;

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    public MovieDetailsFragment(Result result){
        mMovieDetails = result;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);

        Picasso.with(getActivity())
                .load("http://image.tmdb.org/t/p/w500" + mMovieDetails.getPosterPath())
                .into(mPosterImage);

        mTitle.setText(mMovieDetails.getOriginalTitle());
        mPlot.setText("Overview \n" + mMovieDetails.getOverview());
        mRating.setText("User Rating : " + mMovieDetails.getVoteAverage());
        mReleaseDate.setText("Release Date : " + mMovieDetails.getReleaseDate());
        collapsingToolbarLayout.setTitle(mMovieDetails.getOriginalTitle());


        return rootView;
    }
}
