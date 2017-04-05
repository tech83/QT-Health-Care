package in.quantumtech.qthelpcare.ui.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import in.quantumtech.qthelpcare.ui.api.APIService;
import in.quantumtech.qthelpcare.ui.api.CommonAPI;
import in.quantumtech.qthelpcare.ui.model.TestResponseModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import static com.google.android.gms.internal.zzt.TAG;

public class UploadFileService extends IntentService {

    private static final String ACTION_UPLOAD_PROFILE_IMAGE = "in.quantumtech.qthelpcare.ui.services.action.UPLOAD_PROFILE_IMAGE";

    private static final String EXTRA_IMAGE = "in.quantumtech.qthelpcare.ui.services.extra.IMAGE";
    private static final String EXTRA_USER_ID = "in.quantumtech.qthelpcare.ui.services.extra.USER_ID";

    public UploadFileService() {
        super("UploadFileService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUploadProfilePic(Context context, File image, String userId) {
        Intent intent = new Intent(context, UploadFileService.class);
        intent.setAction(ACTION_UPLOAD_PROFILE_IMAGE);
        intent.putExtra(EXTRA_IMAGE, image);
        intent.putExtra(EXTRA_USER_ID, userId);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action){
                case ACTION_UPLOAD_PROFILE_IMAGE:
                    File image = (File) intent.getSerializableExtra(EXTRA_IMAGE);
                    String userId = intent.getStringExtra(EXTRA_USER_ID);
                    handleActionUploadProfilePic(image,userId);
                    break;
            }
        }
    }

    /**
     * Handle action UploadProfilePic in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUploadProfilePic(File image, String userId) {
        CommonAPI commonAPI = APIService.createService(CommonAPI.class);
        MultipartTypedOutput output = new MultipartTypedOutput();
        output.addPart("Id",new TypedString(userId));
        output.addPart("Files",new TypedFile("image/*",image));
        commonAPI.sendFile(output, new Callback<TestResponseModel>() {
            @Override
            public void success(TestResponseModel testResponseModel, retrofit.client.Response response) {
                Toast.makeText(UploadFileService.this, testResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "failure: ", error);
            }
        });
    }

}
