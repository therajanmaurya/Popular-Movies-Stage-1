package opensource.popularmoviesstage1.data.remote;

import opensource.popularmoviesstage1.data.model.PopularMovies;
import opensource.popularmoviesstage1.data.model.Reviews;
import opensource.popularmoviesstage1.data.model.Videos;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public interface MovieService {

    @GET("movie/{categories}")
    Observable<PopularMovies> getMovies(@Path("categories") String s,
                                        @Query("api_key") String key,
                                        @Query("page") int pagination);


    @GET("movie/{id}/videos")
    Observable<Videos> getMovieTrailers(@Path("id") int id,
                                        @Query("api_key") String key,
                                        @Query("page") int pagination);

    @GET("movie/{id}/reviews")
    Observable<Reviews> getMovieReviews(@Path("id") int id,
                                        @Query("api_key") String key,
                                        @Query("page") int pagination);

}
