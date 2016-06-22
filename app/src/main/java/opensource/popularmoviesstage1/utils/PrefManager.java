package opensource.popularmoviesstage1.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import opensource.popularmoviesstage1.App;

/**
 * @author Rajan Maurya
 */
public class PrefManager {

    public static final String POPULAR_MOVIES = "popular";
    public static final String TOP_RATED_MOVIES = "top_rated";
    private static final String MOVIES_CATEGORIES = "categories";



    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance()
                .getApplicationContext());
    }

    public static void clearPrefs() {
        getPreferences().edit().clear().commit();
    }

    public static String getString(String preferenceKey, String preferenceDefaultValue) {
        return getPreferences().getString(preferenceKey, preferenceDefaultValue);
    }

    public static void putString(String preferenceKey, String preferenceValue) {
        getPreferences().edit().putString(preferenceKey, preferenceValue).commit();
    }


    /**
     *
     * @param category of the Movie
     */
    public static void setCaetgory(String category) {
        putString(MOVIES_CATEGORIES, category);
    }

    public static void clearCategory() {
        putString(MOVIES_CATEGORIES, "");
    }

    /**
     *
     * @return category of the movie
     * default value if popular
     */
    public static String getCategory() {
        return getString(MOVIES_CATEGORIES, POPULAR_MOVIES);
    }

}


