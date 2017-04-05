package in.quantumtech.qthelpcare.ui.patient.ui.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.databinding.ActivityPatientRegisterBinding;
import in.quantumtech.qthelpcare.ui.api.APIService;
import in.quantumtech.qthelpcare.ui.api.PatientAPI;
import in.quantumtech.qthelpcare.ui.database.PatientInfoTableHelper;
import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.doctor.ui.activities.LoginActivity;
import in.quantumtech.qthelpcare.ui.model.PatientRegisterBaseModel;
import in.quantumtech.qthelpcare.ui.model.RegisterResponseModel;
import in.quantumtech.qthelpcare.ui.utils.M;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PatientRegisterActivity extends AppCompatActivity {
    private static final String TAG = "PatientRegisterActivity";
    private ActivityPatientRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_patient_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setValueFromDb();
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataInvalid()){
                    final ProgressDialog progressDialog = ProgressDialog.show(PatientRegisterActivity.this, null, "Registering ...", true, false);
                    ContentValues contentValues = PatientInfoTableHelper.newPatientInfo(getText(binding.etName), getText(binding.etMobile),getText(binding.etEmail), getText(binding.etAddress));
                    getContentResolver().insert(UserContract.PatientInfoTable.CONTENT_URI, contentValues);
                    PatientRegisterBaseModel model = new PatientRegisterBaseModel(getText(binding.etName), getText(binding.etEmail), getText(binding.etMobile), getText(binding.etAddress));
                    PatientAPI patientAPI = APIService.createService(PatientAPI.class);
                    patientAPI.register(model, new Callback<RegisterResponseModel>() {
                        @Override
                        public void success(RegisterResponseModel registerResponseModel, Response response) {
                            if (progressDialog != null) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }

                            if (registerResponseModel != null) {
                                if ("success".equalsIgnoreCase(registerResponseModel.getMessage())) {
                                    final Dialog dialog = new Dialog(PatientRegisterActivity.this);
                                    dialog.setContentView(R.layout.popup_thank_you);
                                    dialog.setCancelable(false);
                                    Button btn = (Button) dialog.findViewById(R.id.btn_ok);
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            LoginActivity.start(PatientRegisterActivity.this);
                                        }
                                    });
                                    dialog.show();
                                } else {
                                    Toast.makeText(PatientRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (progressDialog != null) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                            Log.e(TAG, "failure: ", error);
                        }
                    });
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(PatientRegisterActivity.this);
            }
        });
    }

    private boolean dataInvalid() {
        boolean notValid = false;
        if (binding.etName.length() <= 0) {
            notValid = true;
            binding.etName.setError("Enter valid name.");
        }
        if (binding.etMobile.length() <= 0) {
            notValid = true;
            binding.etMobile.setError("Enter valid mobile no.");
        }
        if (binding.etEmail.length() <= 0) {
            notValid = true;
            binding.etEmail.setError("Enter valid email address.");
        } else {
            if (!M.isEmailValid(binding.etEmail.getText().toString().trim())) {
                notValid = true;
                binding.etEmail.setError("Enter valid email address.");
            }
        }
        if (binding.etAddress.length() <= 0) {
            notValid = true;
            binding.etAddress.setError("Enter valid address.");
        }
        return notValid;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, PatientRegisterActivity.class);
        context.startActivity(starter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_in_right);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void setValueFromDb() {
        Cursor cursor = getContentResolver().query(UserContract.PatientInfoTable.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToLast()) {
            binding.etName.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_NAME)));
            binding.etMobile.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_MOBILE_NO)));
            binding.etEmail.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_EMAIL)));
            binding.etAddress.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_ADDRESS)));
            cursor.close();
        }
    }
}
