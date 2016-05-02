package opensource.popularmoviesstage1.ui.main;

import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.ui.base.MvpView;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public interface MainMvpView extends MvpView {

    void showMovies(PopularMovies popularMovies);
    void showProgressbar(boolean b);
}
