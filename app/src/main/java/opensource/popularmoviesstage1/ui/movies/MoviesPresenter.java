package opensource.popularmoviesstage1.ui.movies;

import android.graphics.Movie;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import opensource.popularmoviesstage1.data.DataManager;
import opensource.popularmoviesstage1.data.model.MovieResult;
import opensource.popularmoviesstage1.data.model.MovieResultSugar;
import opensource.popularmoviesstage1.data.model.Movies;
import opensource.popularmoviesstage1.ui.base.BasePresenter;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RajanMaurya on 02/05/16.
 */
public class MoviesPresenter extends BasePresenter<MoviesMvpView> {

    public final String LOG_TAG = getClass().getSimpleName();
    private DataManager mDataManager;
    private Subscription mSubscriptions;


    public MoviesPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MoviesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
    }

    public void loadMovies(String categories,int pageno){

        mSubscriptions = mDataManager.getMovies(categories,pageno)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG,e.getMessage());
                    }

                    @Override
                    public void onNext(Movies movies) {
                        getMvpView().showMovies(movies);
                        Log.d(LOG_TAG, movies.toString());
                    }
                });
    }

    public void loadFavoriteMovies() {

        List<MovieResultSugar> listAll = MovieResultSugar.find(MovieResultSugar.class,null);

        Movies movies = new Movies();
        List<MovieResult> movieResults = new ArrayList<>();
        for (int i = 0; i < listAll.size(); i++) {
            movieResults.add(parseObject(listAll.get(i)));
        }
        movies.setResults(movieResults);
        getMvpView().showFavoriteMovies(movies);
    }

    public MovieResult parseObject(MovieResultSugar movieResultSugar) {


        return new MovieResult(
                movieResultSugar.getOriginalLanguage(),
                movieResultSugar.getPosterPath(),
                movieResultSugar.getAdult(),
                movieResultSugar.getOverview(),
                movieResultSugar.getReleaseDate(),
                movieResultSugar.getMovieId(),
                movieResultSugar.getOriginalTitle(),
                movieResultSugar.getTitle(),
                movieResultSugar.getBackdropPath(),
                movieResultSugar.getPopularity(),
                movieResultSugar.getVoteCount(),
                movieResultSugar.getVideo(),
                movieResultSugar.getVoteAverage());
    }


}
