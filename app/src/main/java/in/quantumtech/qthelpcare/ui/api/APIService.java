package in.quantumtech.qthelpcare.ui.api;

import in.quantumtech.qthelpcare.ui.utils.Constants;
import retrofit.RestAdapter;

/**
 * Created by quantum on 29/3/17.
 */

public class APIService {
    public static <T> T createService(Class<T> cls){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Constants.BASE_URL).build();
        return restAdapter.create(cls);
    }
}
