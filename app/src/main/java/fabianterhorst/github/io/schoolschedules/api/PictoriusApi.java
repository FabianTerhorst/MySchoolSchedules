package fabianterhorst.github.io.schoolschedules.api;

import fabianterhorst.github.io.schoolschedules.models.RepresentationResult;
import fabianterhorst.github.io.schoolschedules.models.SchoolClassResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public interface PictoriusApi {
    @Headers({
            "Content-type: application/json"
    })

    @GET("/f94841d1-0c25-4805-8a07-3115d5e8341a/_query?input/webpage/url=https%3A%2F%2Fdl.dropboxusercontent.com%2Fu%2F541390%2FKlassenvertretungen%2Findex.html")
    void getClasses(Callback<SchoolClassResult> callback);

    @GET("/url={url}")
    void getRepresentations(@Path("url") String url, Callback<RepresentationResult> callback);

}
