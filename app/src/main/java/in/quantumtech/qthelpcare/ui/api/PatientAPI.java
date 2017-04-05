package in.quantumtech.qthelpcare.ui.api;

import in.quantumtech.qthelpcare.ui.model.PatientRegisterBaseModel;
import in.quantumtech.qthelpcare.ui.model.RegisterResponseModel;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by quantum on 29/3/17.
 */

public interface PatientAPI {
    @POST("/RegisterPatient")
    void register(@Body PatientRegisterBaseModel model, Callback<RegisterResponseModel> response);
}
