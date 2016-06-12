package opensource.popularmoviesstage1.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class Videos implements Parcelable {

    public int id;

    public ArrayList<VideoResults> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<VideoResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<VideoResults> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(results);
    }

    public Videos() {
    }

    protected Videos(Parcel in) {
        this.id = in.readInt();
        this.results = in.createTypedArrayList(VideoResults.CREATOR);
    }

    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel source) {
            return new Videos(source);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}
