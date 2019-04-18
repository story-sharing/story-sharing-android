package es.anjon.dyl.storysharing.model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private String text;
    private String createdBy;
    private Date createdAt;

    public Comment(String text) {
        this.text = text;
    }

}
