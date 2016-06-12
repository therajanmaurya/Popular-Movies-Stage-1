package opensource.popularmoviesstage1.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class Trailers implements Parcelable {

    public int id;

    public ArrayList<TrailersResults> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<TrailersResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<TrailersResults> results) {
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

    public Trailers() {
    }

    protected Trailers(Parcel in) {
        this.id = in.readInt();
        this.results = in.createTypedArrayList(TrailersResults.CREATOR);
    }

    public static final Parcelable.Creator<Trailers> CREATOR = new Parcelable.Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel source) {
            return new Trailers(source);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };
}
