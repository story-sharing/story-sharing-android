package es.anjon.dyl.storysharing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Story implements Serializable {

    private String title;
    private List<Scene> scenes;
    private String createdBy;
    private Date createdAt;

    public Story() {

    }

    public String getTitle() {
        if (title == null) {
            return "";
        } else {
            return title;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Scene> getScenes() {
        if (scenes == null) {
            return new ArrayList<>();
        } else {
            return scenes;
        }
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
