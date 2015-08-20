package fabianterhorst.github.io.schoolschedules.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject{

    @PrimaryKey
    private int id = 1;
    private String className;

    @SuppressWarnings("unused")
    public User(){

    }

    public User(String className){
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    @SuppressWarnings("unused")
    public void setClassName(String className) {
        this.className = className;
    }

}
