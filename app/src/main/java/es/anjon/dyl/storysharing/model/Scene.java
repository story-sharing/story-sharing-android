package es.anjon.dyl.storysharing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable {

    private String backgroundColour;
    private String title;
    private List<Comment> comments;

    public Scene(String title) {
        this.title = title;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        if (comments == null) {
            return new ArrayList<>();
        } else {
            return comments;
        }    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
