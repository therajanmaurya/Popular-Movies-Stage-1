package opensource.popularmoviesstage1.ui.moviedetails;

import opensource.popularmoviesstage1.data.model.Videos;
import opensource.popularmoviesstage1.ui.base.MvpView;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public interface MovieDetailsMvpView extends MvpView {

    void showProgressbar(boolean b);

    void showTrailers(Videos videos);

    void showFetchingError(String s);
}
