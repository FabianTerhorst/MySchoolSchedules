package fabianterhorst.github.io.schoolschedules.models;

public class ClassGroup {

    private String mName;
    private String mShortname;

    ClassGroup(String name, String shortname){
        this.mName = name;
        this.mShortname = shortname;
    }

    ClassGroup(){ }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getShortname() {
        return mShortname;
    }

    public void setShortname(String mShortname) {
        this.mShortname = mShortname;
    }
}
