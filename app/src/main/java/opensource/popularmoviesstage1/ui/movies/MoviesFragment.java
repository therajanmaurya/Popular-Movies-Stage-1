package opensource.popularmoviesstage1.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.ui.adapter.MovieGridAdapter;
import opensource.popularmoviesstage1.ui.adapter.RecyclerItemClickListner;
import opensource.popularmoviesstage1.ui.moviedetails.MovieDetailsActivity;
import opensource.popularmoviesstage1.utils.ScrollChildSwipeRefreshLayout;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class MoviesFragment extends Fragment implements RecyclerItemClickListner.OnItemClickListener, MoviesMvpView {

    public static final int GRID_LAYOUT_COUNT = 2;
    public  final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.rv_movies) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh) ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.progress_circular) ProgressBar mProgressBar;
    @BindView(R.id.appbar) Toolbar mToolbar;

    private PopularMovies mPopularMovies;
    private DataManager dataManager;
    private MoviesPresenter mMainPresenter;
    private MovieGridAdapter mMovieGridAdapter;

    @Override
    public void onItemClick(View childView, int position) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("MOVIE_DETAILS", (new Gson()).toJson(mPopularMovies.getResults().get(position)));
        startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        //args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPopularMovies = new PopularMovies();
        dataManager = new DataManager();
        mMainPresenter = new MoviesPresenter(dataManager);
        mMovieGridAdapter = new MovieGridAdapter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mMainPresenter.attachView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitle(getString(R.string.app_name));

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GRID_LAYOUT_COUNT));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(), this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMovieGridAdapter);

        mMainPresenter.loadMovies(getString(R.string.category_popular),1);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMainPresenter.detachView();
    }

    @Override
    public void showMovies(PopularMovies popularMovies) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mPopularMovies = popularMovies;
        mMovieGridAdapter.setResults(popularMovies.getResults());
        mMovieGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressbar(boolean status) {
        if (status){
            mProgressBar.setVisibility(View.VISIBLE);
        }else
            mProgressBar.setVisibility(View.GONE);
    }

}
