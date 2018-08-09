package com.qinicy.posenet.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KeyPoint implements Serializable{
    @SerializedName("position")
    public Position position;
    @SerializedName("part")
    public String part;
    @SerializedName("score")
    public float score;
}
