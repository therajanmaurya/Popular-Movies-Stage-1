package opensource.popularmoviesstage1;

import android.content.Context;

import com.orm.SugarApp;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class App extends SugarApp{

    private static App instance;


    public static Context getContext() {
        return instance;
    }

    public static App getInstance() {
        return instance;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
