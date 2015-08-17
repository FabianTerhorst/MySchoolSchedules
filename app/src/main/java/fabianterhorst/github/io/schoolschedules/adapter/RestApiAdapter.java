package fabianterhorst.github.io.schoolschedules.adapter;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

public class RestApiAdapter {

    private static RestAdapter sharedInstance, sharedPictoriusInstance = null;
    private static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();
    private static RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("timestamp", Long.toString(System.currentTimeMillis()));
            request.addQueryParam("_apikey", "126f5f4f90914edc805f85fd94e99ba4a619fb2d0f70f938b4b11adaf623fea79668b196d21070cc960058c336911d66bf501fdb2353837402f705045dd903f657d3b2810419de077508bf4705183ac2");
            request.addQueryParam("_user", "126f5f4f-9091-4edc-805f-85fd94e99ba4");
        }
    };

    public static RestAdapter getInstance() {
        if (sharedInstance == null)
            sharedInstance = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new AndroidLog("RETROFIT"))
                    .setEndpoint("https://api.import.io/store/data/")
                    .setRequestInterceptor(requestInterceptor)
                    .setConverter(new GsonConverter(gson))
                    .build();

        return sharedInstance;
    }

    public static RestAdapter getPictoriusInstance() {
        if (sharedPictoriusInstance == null)
            sharedPictoriusInstance = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new AndroidLog("RETROFIT"))
                    .setEndpoint("https://api.import.io/store/data/582badfb-22cb-4838-b0dd-a5e61f3627b1/_query?input/webpage/")
                    .setRequestInterceptor(requestInterceptor)
                    .setConverter(new GsonConverter(gson))
                    .build();

        return sharedPictoriusInstance;
    }

}
