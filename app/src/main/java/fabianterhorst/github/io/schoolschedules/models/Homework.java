package fabianterhorst.github.io.schoolschedules.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Homework extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private String description;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
