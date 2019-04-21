package es.anjon.dyl.storysharing.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Story implements Serializable {

    public static final String TAG = "Story";

    private String id;
    private String title;
    private List<Scene> scenes;
    private List<String> groups;
    private String createdBy;

    @ServerTimestamp
    private Date createdAt;

    public Story() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        if (title == null) {
            this.title =  "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Scene> getScenes() {
        if (scenes == null) {
            this.scenes = new ArrayList<>();
        }
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Story story = (Story) o;

        return getId() != null ? getId().equals(story.getId()) : story.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
