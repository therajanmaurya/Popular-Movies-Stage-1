package opensource.popularmoviesstage1.ui.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import opensource.popularmoviesstage1.ui.adapter.EndlessRecyclerOnScrollListener;
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
    private String category;
    private int mPageNumber = 1;

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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mMainPresenter.attachView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitle(getString(R.string.app_name));

        final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),GRID_LAYOUT_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(), this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        category = sharedPreferences.getString(getString(R.string.category_key), getString(R.string.pref_default));


        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (mSwipeRefresh.isRefreshing()) {
                        mPageNumber = 1;
                        mMainPresenter.loadMovies(category,mPageNumber);
                        Log.i(LOG_TAG, "Swipe Refresh");
                    }
                }
                else {
                    Log.i(LOG_TAG, "NO Internet Connection");
                    if (mSwipeRefresh.isRefreshing()) {
                        mSwipeRefresh.setRefreshing(false);
                    }
                }

            }
        });


        mMainPresenter.loadMovies(category,mPageNumber);
        showProgressbar(true);

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected())
                {
                    mPopularMovies.getResults().add(null);
                    mMovieGridAdapter.notifyItemInserted(mPopularMovies.getResults().size());
                    mPageNumber = ++mPageNumber;
                    mMainPresenter.loadMovies(category,mPageNumber);
                    Log.i(LOG_TAG, "Loading more");
                }
                else
                {
                    Log.i(LOG_TAG, "Internet not available. Not loading more posts.");
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMainPresenter.detachView();
    }

    @Override
    public void showMovies(PopularMovies popularMovies) {
       if(mPageNumber == 1){
           mPopularMovies = popularMovies;
           mMovieGridAdapter = new MovieGridAdapter(getActivity(),popularMovies.getResults());
           mRecyclerView.setAdapter(mMovieGridAdapter);
           showProgressbar(false);
       }else {
           mPopularMovies.getResults().remove(mPopularMovies.getResults().size()-1);
           mPopularMovies.getResults().addAll(popularMovies.getResults());
       }

        mRecyclerView.setVisibility(View.VISIBLE);
        mMovieGridAdapter.notifyDataSetChanged();
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showProgressbar(boolean status) {
        if (status){
            mProgressBar.setVisibility(View.VISIBLE);
        }else
            mProgressBar.setVisibility(View.GONE);
    }

}
