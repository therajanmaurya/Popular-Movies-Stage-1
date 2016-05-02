package opensource.popularmoviesstage1.data;

import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.data.remote.MovieService;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class DataManager {

    private MovieService mMovieService;

    public DataManager(MovieService movieService){
        mMovieService = movieService;
    }

    public Observable<PopularMovies> syncMovies(){
        return mMovieService.getMovies();
    }
}
