package opensource.popularmoviesstage1.ui.main;

import android.util.Log;

import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.ui.base.BasePresenter;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    public final String LOG_TAG = getClass().getSimpleName();
    private DataManager mDataManager;
    private CompositeSubscription mSubscriptions;


    public MainPresenter(DataManager dataManager){
        mDataManager = dataManager;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscriptions.clear();
    }

    public void loadMovies(String categories,int pageno){
        getMvpView().showProgressbar(true);
        Subscription mSubscription = mDataManager.getMovies(categories,pageno)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PopularMovies>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
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
        mSubscriptions.add(mSubscription);
    }


}
