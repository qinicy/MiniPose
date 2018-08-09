package com.qinicy.posenet.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pose implements Serializable {
    private static Gson gson = new Gson();
    @SerializedName("score")
    public float score;
    @SerializedName("keypoints")
    public List<KeyPoint> keyPoints;

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
