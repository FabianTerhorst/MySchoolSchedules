package fabianterhorst.github.io.schoolschedules.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Lesson extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String shortname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortname;
    }

    public void setShortName(String shortname) {
        this.shortname = shortname;
    }
}
