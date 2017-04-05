package in.quantumtech.qthelpcare.ui.api;

import in.quantumtech.qthelpcare.ui.model.RegisterResponseModel;
import in.quantumtech.qthelpcare.ui.model.DoctorRegisterBaseModel;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by quantum on 29/3/17.
 */

public interface DoctorAPI {
    @POST("/Register")
    void register(@Body DoctorRegisterBaseModel model, Callback<RegisterResponseModel> response);
}
