package newbaking.code.develop.bizartxo.newbakingapp.model;

/**
 * Created by izartxo on 9/12/17.
 */

public class Step {

    private String rid;
    private int step;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

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
}

