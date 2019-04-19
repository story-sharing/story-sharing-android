package es.anjon.dyl.storysharing.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Scene implements Serializable {

    private static final String DEFAULT_BACKGROUND_COLOUR = "#ffffff";
    private static final String DEFAULT_TEXT_COLOUR = "#000000";

    private String id;
    private String title;
    private String backgroundColour;
    private String textColour;
    private List<Comment> comments;

    @ServerTimestamp
    private Date createdAt;

    public Scene() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundColour() {
        if (backgroundColour == null) {
            this.backgroundColour = DEFAULT_BACKGROUND_COLOUR;
        }
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public String getTextColour() {
        if (textColour == null) {
            this.textColour = DEFAULT_TEXT_COLOUR;
        }
        return textColour;
    }

    public void setTextColour(String textColour) {
        this.textColour = textColour;
    }

    public List<Comment> getComments() {
        if (comments == null) {
            return new ArrayList<>();
        } else {
            return comments;
        }
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scene scene = (Scene) o;

        return getId() != null ? getId().equals(scene.getId()) : scene.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
