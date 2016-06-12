package opensource.popularmoviesstage1.ui.moviedetails;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.Result;
import opensource.popularmoviesstage1.data.model.Trailers;

/**
 * Created by Rajan Maurya on 2/5/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsMvpView{


    private Result mMovieDetails;

    @BindView(R.id.iv_image)
    ImageView mPosterImage;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.plot)
    TextView mPlot;

    @BindView(R.id.rating)
    TextView mRating;

    @BindView(R.id.rdate)
    TextView mReleaseDate;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    DataManager dataManager;
    MovieDetailsPresenter mMovieDetailsPresenter;


    public MovieDetailsFragment(Result result){
        mMovieDetails = result;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new DataManager();
        mMovieDetailsPresenter = new MovieDetailsPresenter(dataManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, rootView);
        mMovieDetailsPresenter.attachView(this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity) getActivity())
                    .getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        return rootView;
    }

    @Override
    public void showProgressbar(boolean b) {

    }

    @Override
    public void showMovieDetailsUI() {
        Picasso.with(getActivity())
                .load(getResources().getString(R.string.image_base_url)
                        + mMovieDetails.getPosterPath())
                .into(mPosterImage);

        mTitle.setText(mMovieDetails.getOriginalTitle());
        mPlot.setText("Overview \n" + mMovieDetails.getOverview());
        mRating.setText("User Rating : " + mMovieDetails.getVoteAverage());
        mReleaseDate.setText("Release Date : " + mMovieDetails.getReleaseDate());
        collapsingToolbarLayout.setTitle(mMovieDetails.getOriginalTitle());
    }

    @Override
    public void showTrailers(Trailers videos) {

    }

    @Override
    public void showFetchingError(String s) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMovieDetailsPresenter.detachView();
    }
}
