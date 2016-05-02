package opensource.popularmoviesstage1.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import opensource.popularmoviesstage1.data.model.PopularMovies;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public interface MovieService {


    String ENDPOINT = "http://api.themoviedb.org/3/";

    @GET("movie/{category}?api_key=" + "e22ade197291c2b6c56392801d8cd0a3")
    Observable<PopularMovies> getMovies();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static MovieService newMovieService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MovieService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(MovieService.class);
        }
    }
}
