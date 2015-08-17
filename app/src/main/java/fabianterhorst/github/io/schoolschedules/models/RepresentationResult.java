package fabianterhorst.github.io.schoolschedules.models;

import java.util.List;

public class RepresentationResult {

    private String mLastRepresentationName;
    private String mLastRepresentationDate;
    private String mLastRepresentationDay;

    private List<Representation> results;

    @SuppressWarnings("unused")
    public void setResults(List<Representation> results) {
        this.results = results;
    }

    public List<Representation> getResults() {
        for(Representation representation : results) {
            //Todo : check all nulls
            //when class name is not null the other things are not null, too
            if (representation.getClass_name() != null) {
                mLastRepresentationName = representation.getClass_name();
                mLastRepresentationDate = representation.getDate();
                mLastRepresentationDay = representation.getDay();
            }else{
                if(mLastRepresentationName != null){
                    representation.setClass_name(mLastRepresentationName);
                    representation.setDate(mLastRepresentationDate);
                    representation.setDay(mLastRepresentationDay);
                }
            }

        }
        return results;
    }
}
