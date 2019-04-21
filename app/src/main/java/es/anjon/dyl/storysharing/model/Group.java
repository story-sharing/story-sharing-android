package es.anjon.dyl.storysharing.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group implements Serializable {

    public static final String TAG = "Group";

    private String id;
    private String title;
    private List<DocumentReference> members;
    private List<DocumentReference> owners;
    private String createdBy;

    @ServerTimestamp
    private Date createdAt;

    public Group() {

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

    public List<DocumentReference> getMembers() {
        if (members == null) {
            this.members = new ArrayList<>();
        }
        return members;
    }

    public void setMembers(List<DocumentReference> members) {
        this.members = members;
    }

    public List<DocumentReference> getOwners() {
        return owners;
    }

    public void setOwners(List<DocumentReference> owners) {
        this.owners = owners;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return getId() != null ? getId().equals(group.getId()) : group.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
