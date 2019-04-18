package es.anjon.dyl.storysharing.model;

import java.util.Date;

public class Story {

    public String title;
    public String createdBy;
    public Date createdAt;

    public Story() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
