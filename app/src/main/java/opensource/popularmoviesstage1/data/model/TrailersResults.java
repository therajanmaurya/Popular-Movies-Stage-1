package opensource.popularmoviesstage1.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class TrailersResults implements Parcelable {

    public String id;

    @SerializedName("iso_639_1")
    public String ISO_639_1;

    @SerializedName("iso_3166_1")
    public String ISO_3166_1;

    public String key;

    public String name;

    public String site;

    public int size;

    public String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getISO_639_1() {
        return ISO_639_1;
    }

    public void setISO_639_1(String ISO_639_1) {
        this.ISO_639_1 = ISO_639_1;
    }

    public String getISO_3166_1() {
        return ISO_3166_1;
    }

    public void setISO_3166_1(String ISO_3166_1) {
        this.ISO_3166_1 = ISO_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.ISO_639_1);
        dest.writeString(this.ISO_3166_1);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
    }

    public TrailersResults() {
    }

    protected TrailersResults(Parcel in) {
        this.id = in.readString();
        this.ISO_639_1 = in.readString();
        this.ISO_3166_1 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<TrailersResults> CREATOR = new Parcelable.Creator<TrailersResults>() {
        @Override
        public TrailersResults createFromParcel(Parcel source) {
            return new TrailersResults(source);
        }

        @Override
        public TrailersResults[] newArray(int size) {
            return new TrailersResults[size];
        }
    };
}
