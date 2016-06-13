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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.MovieResult;
import opensource.popularmoviesstage1.data.model.Movies;
import opensource.popularmoviesstage1.ui.adapter.EndlessRecyclerOnScrollListener;
import opensource.popularmoviesstage1.ui.adapter.MovieGridAdapter;
import opensource.popularmoviesstage1.ui.adapter.RecyclerItemClickListner;
import opensource.popularmoviesstage1.ui.interfaces.ItemClickCallback;
import opensource.popularmoviesstage1.ui.moviedetails.MovieDetailsActivity;
import opensource.popularmoviesstage1.utils.ItemOffsetDecoration;
import opensource.popularmoviesstage1.utils.ScrollChildSwipeRefreshLayout;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class MoviesFragment extends Fragment implements RecyclerItemClickListner
        .OnItemClickListener, MoviesMvpView {

    public static final int GRID_LAYOUT_COUNT = 2;
    public final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.progress_circular)
    ProgressBar mProgressBar;

    @BindView(R.id.appbar)
    Toolbar mToolbar;

    private Movies mMovies;
    private DataManager dataManager;
    private MoviesPresenter mMainPresenter;
    private MovieGridAdapter mMovieGridAdapter;
    private String category;
    private int mPageNumber = 1;

    @Override
    public void onItemClick(View childView, int position) {

        ((ItemClickCallback) getActivity()).onItemSelected(mMovies.getResults().get(position));
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
        mMovies = new Movies();
        dataManager = new DataManager();
        mMainPresenter = new MoviesPresenter(dataManager);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mMainPresenter.attachView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitle(getString(R.string.app_name));

        final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                GRID_LAYOUT_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(), this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Setting the Equal column spacing
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen
                .item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences
                (getActivity());
        category = sharedPreferences.getString(getString(R.string.category_key), getString(R
                .string.pref_default));


        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent, R.color
                .colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (mSwipeRefresh.isRefreshing()) {
                        mPageNumber = 1;
                        mMainPresenter.loadMovies(category, mPageNumber);
                        Log.i(LOG_TAG, "Swipe Refresh");
                    }
                } else {
                    Log.i(LOG_TAG, "NO Internet Connection");
                    if (mSwipeRefresh.isRefreshing()) {
                        mSwipeRefresh.setRefreshing(false);
                    }
                }

            }
        });


        mMainPresenter.loadMovies(category, mPageNumber);
        showProgressbar(true);

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    mMovies.getResults().add(null);
                    mMovieGridAdapter.notifyItemInserted(mMovies.getResults().size());
                    mPageNumber = ++mPageNumber;
                    mMainPresenter.loadMovies(category, mPageNumber);
                    Log.i(LOG_TAG, "Loading more");
                } else {
                    Log.i(LOG_TAG, "Internet not available. Not loading more posts.");
                }
            }
        });

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMainPresenter.detachView();
    }

    @Override
    public void showMovies(Movies movies) {
        if (mPageNumber == 1) {
            mMovies = movies;
            mMovieGridAdapter = new MovieGridAdapter(getActivity(), movies.getResults());
            mRecyclerView.setAdapter(mMovieGridAdapter);
            showProgressbar(false);
        } else {
            mMovies.getResults().remove(mMovies.getResults().size() - 1);
            mMovies.getResults().addAll(movies.getResults());
        }

        mRecyclerView.setVisibility(View.VISIBLE);
        mMovieGridAdapter.notifyDataSetChanged();
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showFavoriteMovies(Movies movies) {

        mMovies.getResults().clear();
        mMovies.getResults().addAll(movies.getResults());
        mMovieGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressbar(boolean status) {
        if (status) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
        }
        return true;
    }


    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_movies, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popular:
                        mMainPresenter.loadMovies(getResources()
                                .getString(R.string.category_popular), mPageNumber);
                        break;
                    case R.id.latest:
                        mMainPresenter.loadMovies(getResources()
                                .getString(R.string.category_top_rated), mPageNumber);
                        break;
                    case R.id.favorite:
                        mMainPresenter.loadFavoriteMovies();
                        break;
                    default:
                        mMainPresenter.loadMovies(getResources()
                                .getString(R.string.category_popular), mPageNumber);
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("MoviesList", mMovies);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mMovies = savedInstanceState.getParcelable("MoviesList");
        }

    }

}
