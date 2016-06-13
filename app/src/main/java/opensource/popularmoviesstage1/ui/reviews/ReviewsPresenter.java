package opensource.popularmoviesstage1.ui.reviews;

import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.Reviews;
import opensource.popularmoviesstage1.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class ReviewsPresenter extends BasePresenter<ReviewsMvpView> {

    private DataManager mDataManager;
    private Subscription mSubscriptions;


    public ReviewsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void attachView(ReviewsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
    }

    public void loadReviews(int Id, int pageNo) {
        checkViewAttached();
        getMvpView().showProgressbar(true);
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
        mSubscriptions = mDataManager.getReviews(Id,pageNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Reviews>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showError("Failed to load reviews");
                    }

                    @Override
                    public void onNext(Reviews reviews) {
                        getMvpView().showProgressbar(false);
                        getMvpView().showReviews(reviews);
                    }
                });
    }

}

