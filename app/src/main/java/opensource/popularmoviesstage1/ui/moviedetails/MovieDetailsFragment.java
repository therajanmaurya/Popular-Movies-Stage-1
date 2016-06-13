package opensource.popularmoviesstage1.ui.moviedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.MovieResult;
import opensource.popularmoviesstage1.data.model.MovieResultSugar;
import opensource.popularmoviesstage1.data.model.Trailers;
import opensource.popularmoviesstage1.ui.adapter.TrailerYoutubeAdapter;
import opensource.popularmoviesstage1.ui.reviews.ReviewsActivity;

/**
 * Created by Rajan Maurya on 2/5/16.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsMvpView{


    public final String LOG_TAG = getClass().getSimpleName();

    private MovieResult mMovieDetails;
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

    @BindView(R.id.fav)
    FloatingActionButton buttonFavorite;

    DataManager dataManager;
    MovieDetailsPresenter mMovieDetailsPresenter;
    LinearLayoutManager layoutManager;
    TrailerYoutubeAdapter mTrailerYoutubeAdapter;

    public MovieDetailsFragment(MovieResult result){
        mMovieDetails = result;
    }

    public MovieDetailsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTrailers = new Trailers();
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


    @OnClick(R.id.fav)
    public void saveToDB(){
        Select Query = Select.from(MovieResultSugar.class)
                .where(Condition.prop("id_Movie_Result").eq(
                        mMovieDetails.getId())).limit("1");
        long numberQuery = Query.count();

        if (numberQuery == 0) {
            MovieResultSugar movieResult = new MovieResultSugar(
                    mMovieDetails.getPosterPath(),
                    mMovieDetails.getAdult(),
                    mMovieDetails.getOverview(),
                    mMovieDetails.getReleaseDate(),
                    mMovieDetails.getGenreIds(),
                    mMovieDetails.getId(),
                    mMovieDetails.getOriginalTitle(),
                    mMovieDetails.getOriginalLanguage(),
                    mMovieDetails.getTitle(),
                    mMovieDetails.getBackdropPath(),
                    mMovieDetails.getPopularity(),
                    mMovieDetails.getVoteCount(), 
                    mMovieDetails.getVideo(),
                    mMovieDetails.getVoteAverage());
            movieResult.save();
            buttonFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            MovieResultSugar.deleteAll(MovieResultSugar.class, "id_Movie_Result = ?", mMovieDetails.getId()
                    +"");
            buttonFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @OnClick(R.id.review)
    public void showReviews(){
        Intent intent = new Intent(getActivity(), ReviewsActivity.class);
        //intent.putExtra("details", detail);
        startActivity(intent);
    }

    @Override
    public void showMovieDetailsUI() {

        layoutManager = new LinearLayoutManager(
                getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTrailer.setLayoutManager(layoutManager);
        mRecyclerViewTrailer.setItemAnimator(new DefaultItemAnimator());

        Log.d(LOG_TAG, mMovieDetails.getPosterPath()+ "Path");

        Picasso.with(getActivity())
                .load(getActivity().getResources().getString(R.string.image_base_url)
                        + mMovieDetails.getPosterPath())
                .into(mPosterImage);

        mTitle.setText(mMovieDetails.getOriginalTitle());
        mPlot.setText("Overview \n" + mMovieDetails.getOverview());
        mRating.setText("User Rating : " + mMovieDetails.getVoteAverage());
        mReleaseDate.setText("Release Date : " + mMovieDetails.getReleaseDate());
        collapsingToolbarLayout.setTitle(mMovieDetails.getOriginalTitle());

        Select Query = Select.from(MovieResultSugar.class)
                .where(Condition.prop("id_Movie_Result").eq(mMovieDetails.getId()));
        long numberQuery = Query.count();

        if (numberQuery == 0) {
            buttonFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            buttonFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
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
