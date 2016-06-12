package opensource.popularmoviesstage1.ui.moviedetails;

import opensource.popularmoviesstage1.data.model.Trailers;
import opensource.popularmoviesstage1.ui.base.MvpView;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public interface MovieDetailsMvpView extends MvpView {

    void showProgressbar(boolean b);

    void showMovieDetailsUI();

    void showTrailers(Trailers videos);

    void showFetchingError(String s);
}
