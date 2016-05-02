package opensource.popularmoviesstage1.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by Rajan Maurya on 2/5/16.
 */
public class BaseApiManager {


    String ENDPOINT = "http://api.themoviedb.org/3/";

    public MovieService mMovieService;

    public BaseApiManager(){

        mMovieService = createApi(MovieService.class,ENDPOINT);
    }

    /******** Helper class that sets up a new services *******/

    private <T> T createApi(Class<T> clazz, String ENDPOINT) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return  retrofit.create(clazz);
    }

    public MovieService getmMovieApi(){
        return mMovieService;
    }
}
