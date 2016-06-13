package opensource.popularmoviesstage1.data.remote;

import opensource.popularmoviesstage1.data.model.Movies;
import opensource.popularmoviesstage1.data.model.Reviews;
import opensource.popularmoviesstage1.data.model.Trailers;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public interface MovieService {

    @GET("movie/{categories}")
    Observable<Movies> getMovies(@Path("categories") String s,
                                 @Query("api_key") String key,
                                 @Query("page") int pagination);


    @GET("movie/{movieId}/videos")
    Observable<Trailers> getMovieTrailers(@Path("movieId") int id,
                                          @Query("api_key") String key,
                                          @Query("page") int pagination);

    @GET("movie/{movieId}/reviews")
    Observable<Reviews> getMovieReviews(@Path("movieId") int id,
                                        @Query("api_key") String key,
                                        @Query("page") int pagination);

}
