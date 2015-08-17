package fabianterhorst.github.io.schoolschedules.models;

import java.util.List;

public class SchoolClassResult {

    private List<SchoolClass> results;

    @SuppressWarnings("unused")
    public void setResults(List<SchoolClass> results) {
        this.results = results;
    }

    public List<SchoolClass> getResults() {
        return results;
    }
}
