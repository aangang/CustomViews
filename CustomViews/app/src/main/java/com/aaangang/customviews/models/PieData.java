package com.aaangang.customviews.models;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/11/2.
 */
public class  PieData{
    private String name;
    private float value;
    private float percentage;

    private int color;
    private float angle;

    public PieData(@NonNull String name, @NonNull float value){
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public float getPercentage() {
        return percentage;
    }
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public float getAngle() {
        return angle;
    }
    public void setAngle(float angle) {
        this.angle = angle;
    }
}
