package com.changxinyue.graduationproject.beans;

import java.io.Serializable;

public class CityInfo implements Serializable {

   private int _id;
   private String city_name;
   private String city_name_spell;
   private float city_score;
   private String city_image;
   private String city_content;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_name_spell() {
        return city_name_spell;
    }

    public void setCity_name_spell(String city_name_spell) {
        this.city_name_spell = city_name_spell;
    }

    public float getCity_score() {
        return city_score;
    }

    public void setCity_score(float city_score) {
        this.city_score = city_score;
    }

    public String getCity_image() {
        return city_image;
    }

    public void setCity_image(String city_image) {
        this.city_image = city_image;
    }

    public String getCity_content() {
        return city_content;
    }

    public void setCity_content(String city_content) {
        this.city_content = city_content;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "_id=" + _id +
                ", city_name='" + city_name + '\'' +
                ", city_name_spell='" + city_name_spell + '\'' +
                ", city_score=" + city_score +
                ", city_image='" + city_image + '\'' +
                ", city_content='" + city_content + '\'' +
                '}';
    }
}
