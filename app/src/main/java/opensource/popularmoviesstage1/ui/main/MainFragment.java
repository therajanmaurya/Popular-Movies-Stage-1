package opensource.popularmoviesstage1.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.ui.adapter.MovieGridAdapter;
import opensource.popularmoviesstage1.ui.adapter.RecyclerItemClickListner;
import opensource.popularmoviesstage1.utils.ScrollChildSwipeRefreshLayout;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class MainFragment extends Fragment implements RecyclerItemClickListner.OnItemClickListener, MainMvpView{

    public static final int GRID_LAYOUT_COUNT = 2;
    public  final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.rv_movies) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh) ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.progress_circular) ProgressBar mProgressBar;
    private Unbinder unbinder;

    private PopularMovies mPopularMovies;
    private DataManager dataManager;
    private MainPresenter mMainPresenter;
    private MovieGridAdapter mMovieGridAdapter;

    @Override
    public void onItemClick(View childView, int position) {

    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        mMainPresenter = new MainPresenter(dataManager);
        mMovieGridAdapter = new MovieGridAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mMainPresenter.attachView(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GRID_LAYOUT_COUNT));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListner(getActivity(), this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMovieGridAdapter);

        mMainPresenter.loadMovies();
        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mMainPresenter.detachView();
    }

    @Override
    public void showMovies(PopularMovies popularMovies) {
        mMovieGridAdapter.setResults(popularMovies.getResults());
        mMovieGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressbar(boolean status) {
        if (status){
            mProgressBar.setVisibility(View.INVISIBLE);
        }else
            mProgressBar.setVisibility(View.GONE);
    }

}
