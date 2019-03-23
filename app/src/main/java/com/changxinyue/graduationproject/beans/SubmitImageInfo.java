package com.changxinyue.graduationproject.beans;

import java.io.Serializable;

public class SubmitImageInfo implements Serializable {

    private long submit_id;

    private String submit_image;

    public long getSubmit_id() {
        return submit_id;
    }

    public void setSubmit_id(long submit_id) {
        this.submit_id = submit_id;
    }

    public String getSubmit_image() {
        return submit_image;
    }

    public void setSubmit_image(String submit_image) {
        this.submit_image = submit_image;
    }

    @Override
    public String toString() {
        return "SubmitImageInfo{" +
                "submit_id=" + submit_id +
                ", submit_image='" + submit_image + '\'' +
                '}';
    }
}
