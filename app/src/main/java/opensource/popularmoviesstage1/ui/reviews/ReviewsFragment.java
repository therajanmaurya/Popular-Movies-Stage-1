package opensource.popularmoviesstage1.ui.reviews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.Reviews;
import opensource.popularmoviesstage1.ui.adapter.EndlessRecyclerOnScrollListener;
import opensource.popularmoviesstage1.ui.adapter.ReviewsAdapter;
import opensource.popularmoviesstage1.utils.ScrollChildSwipeRefreshLayout;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class ReviewsFragment extends Fragment implements ReviewsMvpView {

    public final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.progress_circular)
    ProgressBar mProgressBar;

    @BindView(R.id.appbar)
    Toolbar mToolbar;

    private Reviews mReviews;
    private DataManager dataManager;
    private ReviewsPresenter mReviewsPresenter;
    private ReviewsAdapter mReviewsAdapter;
    private int mPageNumber = 1;

    public static ReviewsFragment newInstance() {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        //args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviews = new Reviews();
        dataManager = new DataManager();
        mReviewsPresenter = new ReviewsPresenter(dataManager);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);
        mReviewsPresenter.attachView(this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitle(getString(R.string.app_name));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


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
                        mReviewsPresenter.loadReviews(2412, mPageNumber);
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


        mReviewsPresenter.loadReviews(2412, mPageNumber);
        showProgressbar(true);

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    mReviews.getResults().add(null);
                    mReviewsAdapter.notifyItemInserted(mReviews.getResults().size());
                    mPageNumber = ++mPageNumber;
                    mReviewsPresenter.loadReviews(2412, mPageNumber);
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
        mReviewsPresenter.detachView();
    }

    @Override
    public void showProgressbar(boolean status) {
        if (status) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showReviews(Reviews reviews) {
        if (mPageNumber == 1) {
            mReviews = reviews;
            mReviewsAdapter = new ReviewsAdapter(getActivity(),mReviews);
            mRecyclerView.setAdapter(mReviewsAdapter);
            showProgressbar(false);
        } else {
            mReviews.getResults().remove(mReviews.getResults().size() - 1);
            mReviews.getResults().addAll(mReviews.getResults());
        }

        mRecyclerView.setVisibility(View.VISIBLE);
        mReviewsAdapter.notifyDataSetChanged();
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showError(String s) {
        Toast.makeText(getActivity(),s, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("Reviews", mReviews);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mReviews = savedInstanceState.getParcelable("Reviews");
        }

    }

}