package opensource.popularmoviesstage1.data.remote;

import opensource.popularmoviesstage1.data.model.PopularMovies;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public interface MovieService {

    @GET("movie/{category}?api_key=" + "e22ade197291c2b6c56392801d8cd0a3")
    Observable<PopularMovies> getMovies();

}
