package com.example.mycats;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

public class Car implements Serializable {

    String color;
    String name;
    String img;
    String dpl;
    String desc;


    public Car(String color ,String name,String img,String dpl,String desc) {
        this.color = color;
        this.name=name;
        this.img=img;
        this.dpl=dpl;
        this.desc=desc;

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getDpl() {
        return dpl;
    }

    public void setDpl(String dpl) {
        this.dpl = dpl;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}


