package in.quantumtech.qthelpcare.ui.doctor.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learnpainless.core.tasks.CompressImageTask;
import com.learnpainless.core.tasks.Response;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.databinding.ActivityLoginBinding;
import in.quantumtech.qthelpcare.ui.api.APIService;
import in.quantumtech.qthelpcare.ui.api.CommonAPI;
import in.quantumtech.qthelpcare.ui.model.LoginModel;
import in.quantumtech.qthelpcare.ui.model.LoginResponseModel;
import in.quantumtech.qthelpcare.ui.patient.ui.activities.PatientRegisterActivity;
import in.quantumtech.qthelpcare.ui.services.UploadFileService;
import in.quantumtech.qthelpcare.ui.utils.Constants;
import in.quantumtech.qthelpcare.ui.utils.M;
import in.quantumtech.qthelpcare.ui.utils.PreferenceUtils;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        // Set up the login form.
        String userType = PreferenceUtils.getUserType(LoginActivity.this);
        if (userType != null){
            switch (userType){
                case Constants.LOGIN_TYPE_PATIENT:
                    binding.rootLayout.setBackgroundResource(R.drawable.user_bg);
                    break;
                case Constants.LOGIN_TYPE_DOCTOR:
                    binding.rootLayout.setBackgroundResource(R.drawable.start_bg);
                    break;
            }
        }
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //openGallery();
                //PatientHomeActivity.start(LoginActivity.this);
                if (!dataInvalid()){
                    LoginModel model = new LoginModel(getText(binding.email),getText(binding.password),PreferenceUtils.getUserType(LoginActivity.this));
                    CommonAPI commonAPI = APIService.createService(CommonAPI.class);
                    commonAPI.login(model, new Callback<LoginResponseModel>() {
                        @Override
                        public void success(LoginResponseModel loginResponseModel, retrofit.client.Response response) {
                            if (loginResponseModel != null){
                                if (loginResponseModel.getID() != 0){
                                    //// TODO: 31/3/17 doctor login info.

                                }
                                else if (loginResponseModel.getPatientId() != 0){
                                    //// TODO: 31/3/17 patient login info.
                                    /*CacheManager cacheManager = new CacheManager(LoginActivity.this);
                                    Type type = new TypeToken<LoginResponseModel>(){}.getType();
                                    cacheManager.writeJson(loginResponseModel,type, CacheUtils.PATIENT_LOGIN_INFO);
                                    DoctorHomeActivity.start(LoginActivity.this);*/

                                    overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(TAG, "failure: ", error);
                        }
                    });
                }


            }
        });

        binding.txtForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_edittext);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                //This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
                dialog.show();
            }
        });

        binding.txtRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String userType = PreferenceUtils.getUserType(LoginActivity.this);
                if (userType != null){
                    switch (userType){
                        case Constants.LOGIN_TYPE_PATIENT:
                            PatientRegisterActivity.start(LoginActivity.this);
                            overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
                            break;
                        case Constants.LOGIN_TYPE_DOCTOR:
                            RegisterActivity.start(LoginActivity.this);
                            overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
                            break;
                    }
                }



            }
        });

    }
    private static final String TAG = "LoginActivity";
    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_out_right,R.anim.anim_slide_in_right);
    }

    private void openGallery(){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, REQUEST_IMAGE_PICKER);
    }
    public static final int REQUEST_IMAGE_PICKER = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_PICKER:
                    new CompressImageTask(listener,LoginActivity.this,String.valueOf(data.getData())).execute();
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private Response.Listener<CompressImageTask.CompressedModel> listener = new Response.Listener<CompressImageTask.CompressedModel>() {
        @Override
        public void onResponse(CompressImageTask.CompressedModel compressedModel) {
            Log.d(TAG, "onResponse: " + compressedModel.getPath());
            UploadFileService.startActionUploadProfilePic(LoginActivity.this,compressedModel.getFile(),"0");
        }

        @Override
        public void onErrorResponse(Exception e) {
            Log.e(TAG, "onErrorResponse: ", e);
        }
    };

    private boolean dataInvalid() {
        boolean notValid = false;

        if (binding.email.length() <= 0) {
            notValid = true;
            binding.email.setError("Enter valid email address.");
        }
        else {
            if (!M.isEmailValid(binding.email.getText().toString().trim())) {
                notValid = true;
                binding.email.setError("Enter valid email address.");
            }
        }
        if (binding.password.length() <= 0) {
            notValid = true;
            binding.password.setError("Enter valid password.");
        }
        return notValid;
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }
}

