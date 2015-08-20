package fabianterhorst.github.io.schoolschedules.models;

import java.util.ArrayList;
import java.util.List;

public class ClassGroup {

    private String mName;
    private String mShortname;
    private String mAdmin;
    private List<String> mMember;

    public ClassGroup(String name, String shortname, String admin){
        this.mName = name;
        this.mShortname = shortname;
        this.mAdmin = admin;
        this.mMember = new ArrayList<>();
        this.mMember.add(admin);
    }

    public ClassGroup(){ }

    public List<String> getMember() {
        return mMember;
    }

    public void setMember(List<String> mMember) {
        this.mMember = mMember;
    }

    public void addMember(String email){
        this.mMember.add(email);
    }

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

    public String getAdmin() {
        return mAdmin;
    }

    public void setAdmin(String mAdmin) {
        this.mAdmin = mAdmin;
    }
}
