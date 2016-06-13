package opensource.popularmoviesstage1.ui.movies;

import opensource.popularmoviesstage1.data.model.Movies;
import opensource.popularmoviesstage1.ui.base.MvpView;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public interface MoviesMvpView extends MvpView {

    void showMovies(Movies movies);

    void showFavoriteMovies(Movies movies);

    void showProgressbar(boolean b);
}
