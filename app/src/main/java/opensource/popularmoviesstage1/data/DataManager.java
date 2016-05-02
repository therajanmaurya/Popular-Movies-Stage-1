package opensource.popularmoviesstage1.data;

import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.data.remote.BaseApiManager;
import opensource.popularmoviesstage1.data.remote.MovieService;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class DataManager {

    public BaseApiManager mBaseApiManager = new BaseApiManager();

    public DataManager(){
    }

    /**
     *
     * @return List of Movies
     */
    public Observable<PopularMovies> getMovies(String categories,int pageNo){
        return mBaseApiManager.getmMovieApi().getMovies(categories,
                "c2e9da28f7b6430b8e29f24990cdd3de",
                pageNo);
    }
}
