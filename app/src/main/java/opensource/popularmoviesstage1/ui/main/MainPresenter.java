package opensource.popularmoviesstage1.ui.main;

import opensource.popularmoviesstage1.ui.base.BasePresenter;
import rx.Subscription;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {


    private Subscription mSubscription;

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


    }


}
