package opensource.popularmoviesstage1.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class ReviewResults implements Parcelable {

    public String id;

    public String author;

    public String content;

    public String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public ReviewResults() {
    }

    protected ReviewResults(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ReviewResults> CREATOR = new Parcelable
            .Creator<ReviewResults>() {
        @Override
        public ReviewResults createFromParcel(Parcel source) {
            return new ReviewResults(source);
        }

        @Override
        public ReviewResults[] newArray(int size) {
            return new ReviewResults[size];
        }
    };
}
