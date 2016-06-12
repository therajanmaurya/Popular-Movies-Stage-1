package opensource.popularmoviesstage1.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class Reviews implements Parcelable {

    public long id;

    public int page;

    public ArrayList<ReviewResults> results;

    public int total_pages;

    public int total_results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<ReviewResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReviewResults> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.page);
        dest.writeTypedList(results);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
    }

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        this.id = in.readLong();
        this.page = in.readInt();
        this.results = in.createTypedArrayList(ReviewResults.CREATOR);
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
    }

    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
