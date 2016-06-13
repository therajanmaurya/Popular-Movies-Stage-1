package opensource.popularmoviesstage1.ui.movies;

import android.util.Log;

import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.ui.base.BasePresenter;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class MoviesPresenter extends BasePresenter<MoviesMvpView> {

    public final String LOG_TAG = getClass().getSimpleName();
    private DataManager mDataManager;
    private Subscription mSubscriptions;


    public MoviesPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MoviesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
    }

    public void loadMovies(String categories,int pageno){
        mSubscriptions = mDataManager.getMovies(categories,pageno)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PopularMovies>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG,e.getMessage());
                    }

                    @Override
                    public void onNext(PopularMovies popularMovies) {
                        getMvpView().showMovies(popularMovies);
                        Log.d(LOG_TAG, popularMovies.toString());
                    }
                });
    }

    public void loadFavoriteMovies() {

    }

}
