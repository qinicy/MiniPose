package com.qinicy.posenet.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Position implements Serializable{
    @SerializedName("x")
    public float x;
    @SerializedName("y")
    public float y;
}
