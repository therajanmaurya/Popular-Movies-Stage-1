package opensource.popularmoviesstage1.ui.main;

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
public class MainPresenter extends BasePresenter<MainMvpView> {

    private DataManager mDataManager;
    private Subscription mSubscription;

    public MainPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadMovies(){

        mSubscription = mDataManager.syncMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PopularMovies>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PopularMovies popularMovies) {

                    }
                });

    }


}
