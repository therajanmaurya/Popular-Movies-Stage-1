package opensource.popularmoviesstage1.data.remote;

import opensource.popularmoviesstage1.data.model.PopularMovies;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public interface MovieService {

    @GET("movie/popular?api_key=" + "c2e9da28f7b6430b8e29f24990cdd3de")
    Observable<PopularMovies> getMovies();

}
