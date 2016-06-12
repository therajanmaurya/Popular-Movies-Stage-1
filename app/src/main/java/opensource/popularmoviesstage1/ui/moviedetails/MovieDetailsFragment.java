package opensource.popularmoviesstage1.ui.moviedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.Result;
import opensource.popularmoviesstage1.data.model.Trailers;
import opensource.popularmoviesstage1.ui.adapter.RecyclerItemClickListner;
import opensource.popularmoviesstage1.ui.adapter.TrailerYoutubeAdapter;

/**
 * Created by Rajan Maurya on 2/5/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsMvpView,
        RecyclerItemClickListner.OnItemClickListener {


    private Result mMovieDetails;
    private Trailers mTrailers;

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

    @BindView(R.id.rv_trailer)
    RecyclerView mRecyclerViewTrailer;

    @BindView(R.id.pb_trailer)
    ProgressBar mProgressBar;

    DataManager dataManager;
    MovieDetailsPresenter mMovieDetailsPresenter;
    LinearLayoutManager layoutManager;
    TrailerYoutubeAdapter mTrailerYoutubeAdapter;

    @Override
    public void onItemClick(View childView, int position) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                getResources().getString(R.string.google_api_key),
                mTrailers.getResults().get(position).getKey(), 100, true, false);
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

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

        showMovieDetailsUI();
        mMovieDetailsPresenter.loadTrailers(mMovieDetails.getId(),1);


        return rootView;
    }


    @Override
    public void showMovieDetailsUI() {

        layoutManager = new LinearLayoutManager(
                getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTrailer.setLayoutManager(layoutManager);
        mRecyclerViewTrailer.addOnItemTouchListener(
                new RecyclerItemClickListner(getActivity(), this));
        mRecyclerViewTrailer.setItemAnimator(new DefaultItemAnimator());

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
    public void showProgressbar(boolean b) {
        if (b) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTrailers(Trailers videos) {
        mTrailers = videos;
        mTrailerYoutubeAdapter = new TrailerYoutubeAdapter(getActivity(), mTrailers);
        mRecyclerViewTrailer.setAdapter(mTrailerYoutubeAdapter);
    }

    @Override
    public void showFetchingError(String s) {
        Toast.makeText(getActivity(), s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMovieDetailsPresenter.detachView();
    }
}
