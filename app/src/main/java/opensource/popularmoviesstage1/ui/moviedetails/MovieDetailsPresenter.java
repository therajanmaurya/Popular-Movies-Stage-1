package opensource.popularmoviesstage1.ui.moviedetails;

import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.Trailers;
import opensource.popularmoviesstage1.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class MovieDetailsPresenter extends BasePresenter<MovieDetailsMvpView> {


    public final String LOG_TAG = getClass().getSimpleName();
    private DataManager mDataManager;
    private Subscription mSubscriptions;

    public MovieDetailsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MovieDetailsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
    }

    public void loadTrailers(int id, int pageNo) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
        mSubscriptions = mDataManager.getTrailers(id, pageNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Trailers>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showFetchingError("Failed to load trailers");
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onNext(Trailers videos) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showTrailers(videos);
                    }
                });
    }

}
