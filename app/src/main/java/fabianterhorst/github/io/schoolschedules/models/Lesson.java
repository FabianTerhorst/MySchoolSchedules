package fabianterhorst.github.io.schoolschedules.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Lesson extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private String shortname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}
