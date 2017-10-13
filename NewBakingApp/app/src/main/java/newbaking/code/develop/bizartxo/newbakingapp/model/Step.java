package newbaking.code.develop.bizartxo.newbakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by izartxo on 9/12/17.
 */

public class Step implements Parcelable {

    private String rid;
    private int step;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Step(Parcel in){
        readFromParcel(in);
    }

    public Step(){}

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int get_id() {
        return step;
    }

    public void set_id(int _step) {
        this.step = _step;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getRid());
        parcel.writeInt(get_id());
        parcel.writeString(getShortDescription());
        parcel.writeString(getDescription());
        parcel.writeString(getVideoURL());
        parcel.writeString(getThumbnailURL());

    }


    private void readFromParcel(Parcel in) {
        rid = in.readString();
        step = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR
            = new Parcelable.Creator<Step>() {
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}

