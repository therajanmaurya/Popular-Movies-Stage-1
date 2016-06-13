package opensource.popularmoviesstage1.ui.reviews;

import opensource.popularmoviesstage1.data.model.Reviews;
import opensource.popularmoviesstage1.ui.base.MvpView;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public interface ReviewsMvpView extends MvpView {

    void showProgressbar(boolean b);

    void showReviews(Reviews reviews);

    void showError(String s);

}
