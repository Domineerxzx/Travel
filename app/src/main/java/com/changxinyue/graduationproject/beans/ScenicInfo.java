package com.changxinyue.graduationproject.beans;

import java.io.Serializable;

public class ScenicInfo implements Serializable {

    private int _id;
    private int type_id;
    private String city_name;
    private String scenic_content;
    private String scenic_image;
    private String scenic_name;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getScenic_content() {
        return scenic_content;
    }

    public void setScenic_content(String scenic_content) {
        this.scenic_content = scenic_content;
    }

    public String getScenic_image() {
        return scenic_image;
    }

    public void setScenic_image(String scenic_image) {
        this.scenic_image = scenic_image;
    }

    public String getScenic_name() {
        return scenic_name;
    }

    public void setScenic_name(String scenic_name) {
        this.scenic_name = scenic_name;
    }

    @Override
    public String toString() {
        return "ScenicInfo{" +
                "_id=" + _id +
                ", type_id=" + type_id +
                ", city_name='" + city_name + '\'' +
                ", scenic_content='" + scenic_content + '\'' +
                ", scenic_image='" + scenic_image + '\'' +
                ", scenic_name='" + scenic_name + '\'' +
                '}';
    }
}
