package opensource.popularmoviesstage1.data;

import opensource.popularmoviesstage1.BuildConfig;
import opensource.popularmoviesstage1.data.model.Movies;
import opensource.popularmoviesstage1.data.model.Reviews;
import opensource.popularmoviesstage1.data.model.Trailers;
import opensource.popularmoviesstage1.data.remote.BaseApiManager;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class DataManager {

    public BaseApiManager mBaseApiManager = new BaseApiManager();
    private String API_KEY = BuildConfig.Themoviedb_API_key;

    public DataManager(){
    }

    /**
     *
     * @return List of Movies
     */
    public Observable<Movies> getMovies(String categories, int pageNo){
        return mBaseApiManager.getMovieApi().getMovies(categories, API_KEY, pageNo);
    }

    /**
     *
     * @param id of the Movie
     * @param pageNo Pagination of the Api
     * @return Model of the Trailer "Videos"
     */
    public Observable<Trailers> getTrailers(int id, int pageNo) {
        return mBaseApiManager.getMovieApi().getMovieTrailers(id, API_KEY,pageNo);
    }


    /**
     *
     * @param id of the Movie
     * @param pageNo Pagination of the API
     * @return Model of the Reviews "Reviews"
     */
    public Observable<Reviews> getReviews(int id, int pageNo) {
        return mBaseApiManager.getMovieApi().getMovieReviews(id, API_KEY,pageNo);
    }
}
