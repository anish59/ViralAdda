package com.ronak.viral.adda.viralModels;

import java.io.Serializable;

/**
 * Created by anish on 20-03-2018.
 */

public class DailyMotionData implements Serializable{
    private String name="";
    private String videoId="";
    private String imgUrl="";

    public DailyMotionData(String name, String videoId, String imgUrl) {
        this.name = name;
        this.videoId = videoId;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
