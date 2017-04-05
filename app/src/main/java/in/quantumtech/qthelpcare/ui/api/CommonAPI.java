package in.quantumtech.qthelpcare.ui.api;

import java.util.List;

import in.quantumtech.qthelpcare.ui.model.ClinicTypeModel;
import in.quantumtech.qthelpcare.ui.model.LoginModel;
import in.quantumtech.qthelpcare.ui.model.LoginResponseModel;
import in.quantumtech.qthelpcare.ui.model.ServicesModel;
import in.quantumtech.qthelpcare.ui.model.TestResponseModel;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;

/**
 * Created by quantum on 29/3/17.
 */

public interface CommonAPI {
    @GET("/GetType")
    void getClinicType(Callback<List<ClinicTypeModel>> response);

    @POST("/Login")
    void login(@Body LoginModel data, Callback<LoginResponseModel> response);

    /**
     * userId
     * Image
     */
    @POST("/UploadImage")
    void sendFile(@Body MultipartTypedOutput model, Callback<TestResponseModel> response);

    @GET("/GetSpecialization")
    void getSpecialization(Callback<ServicesModel> response);

    @GET("/GetQualification")
    void getQualification(Callback<ServicesModel> response);

    @GET("/GetServices")
    void getServices(Callback<ServicesModel> response);

    @FormUrlEncoded
    @POST("/GetCity")
    void getCity(@Field("StateID") String stateId,Callback<ServicesModel> response);

    @GET("/GetState")
    void getState()
}
