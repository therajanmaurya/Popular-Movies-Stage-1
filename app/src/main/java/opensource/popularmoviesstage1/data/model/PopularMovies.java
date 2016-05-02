package opensource.popularmoviesstage1.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PopularMovies implements Parcelable {

    private int page;
    private List<Result> results;

    @SerializedName("total_results")
    private long totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeList(this.results);
        dest.writeLong(this.totalResults);
        dest.writeInt(this.totalPages);
    }

    public PopularMovies() {
    }

    protected PopularMovies(Parcel in) {
        this.page = in.readInt();
        this.results = new ArrayList<Result>();
        in.readList(this.results, Result.class.getClassLoader());
        this.totalResults = in.readLong();
        this.totalPages = in.readInt();
    }

    public static final Parcelable.Creator<PopularMovies> CREATOR = new Parcelable.Creator<PopularMovies>() {
        @Override
        public PopularMovies createFromParcel(Parcel source) {
            return new PopularMovies(source);
        }

        @Override
        public PopularMovies[] newArray(int size) {
            return new PopularMovies[size];
        }
    };
}