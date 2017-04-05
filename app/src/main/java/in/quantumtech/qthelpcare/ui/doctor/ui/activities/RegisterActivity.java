package in.quantumtech.qthelpcare.ui.doctor.ui.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.learnpainless.core.utils.CacheManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.databinding.ActivityRegisterBinding;
import in.quantumtech.qthelpcare.ui.BaseActivity;
import in.quantumtech.qthelpcare.ui.api.APIService;
import in.quantumtech.qthelpcare.ui.api.CommonAPI;
import in.quantumtech.qthelpcare.ui.database.DoctorInfoTableHelper;
import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.model.ClinicTypeModel;
import in.quantumtech.qthelpcare.ui.doctor.ui.adapters.SpinnerCustomAdapter;
import in.quantumtech.qthelpcare.ui.utils.CacheUtils;
import in.quantumtech.qthelpcare.ui.utils.M;
import in.quantumtech.qthelpcare.ui.utils.PreferenceUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding;
    private CacheManager cacheManager;
    private List<ClinicTypeModel> clinicTypeModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarMessage.setText(getString(R.string.welcome_toolbar, "Doctor"));
        cacheManager = new CacheManager(this);

        getFromCache();
        setValueFromDb();
        saveToCache();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dataInvalid()) {
                    int checkedRadioButtonId = binding.genderGroup.getCheckedRadioButtonId();
                    String gender = null;
                    if (checkedRadioButtonId == binding.rbMale.getId()) {
                        gender = "Male";
                    } else if (checkedRadioButtonId == binding.rbFemale.getId()) {
                        gender = "female";
                    }

                    ContentValues contentValues = DoctorInfoTableHelper.newDoctorInfo1(getText(binding.etName), ((ClinicTypeModel) binding.spinnerType.getSelectedItem()).getTypeId(),
                            getText(binding.etClinic), getText(binding.etMobile), getText(binding.etLandline), getText(binding.etEmail), getText(binding.etAddress),
                            gender);
                    String s = PreferenceUtils.getSharedPreferences(RegisterActivity.this).getString(PreferenceUtils.USER_INFO_URI, null);
                    if (s == null) {
                        Uri insert = getContentResolver().insert(UserContract.DoctorInfoTable.CONTENT_URI, contentValues);
                        PreferenceUtils.getSharedPreferences(RegisterActivity.this).edit().putString(PreferenceUtils.USER_INFO_URI, String.valueOf(insert)).apply();
                    } else {
                        getContentResolver().update(Uri.parse(s), contentValues, null, null);
                    }
                    Register2Activity.start(RegisterActivity.this);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                }


            }
        });

    }
    private void getFromCache(){
        Type type = new TypeToken<List<ClinicTypeModel>>(){}.getType();
        if (cacheManager.readJson(type, CacheUtils.CATEGORY_TYPE) != null){
            clinicTypeModels = (List<ClinicTypeModel>) cacheManager.readJson(type, CacheUtils.CATEGORY_TYPE);
            SpinnerCustomAdapter spinnerCustomAdapter = new SpinnerCustomAdapter(RegisterActivity.this, R.layout.spinner_type, clinicTypeModels);
            spinnerCustomAdapter.setDropDownViewResource(R.layout.spinner_type_list);
            binding.spinnerType.setAdapter(spinnerCustomAdapter);
            binding.spinnerType.getBackground().setColorFilter(ContextCompat.getColor(RegisterActivity.this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        }

    }
    private void saveToCache(){
        CommonAPI commonAPI = APIService.createService(CommonAPI.class);
        commonAPI.getClinicType(new Callback<List<ClinicTypeModel>>() {
            @Override
            public void success(List<ClinicTypeModel> clinicTypeModels, Response response) {
                if (clinicTypeModels != null) {
                    if (RegisterActivity.this.clinicTypeModels.size() != clinicTypeModels.size()){
                        SpinnerCustomAdapter spinnerCustomAdapter = new SpinnerCustomAdapter(RegisterActivity.this, R.layout.spinner_type, clinicTypeModels);
                        spinnerCustomAdapter.setDropDownViewResource(R.layout.spinner_type_list);
                        binding.spinnerType.setAdapter(spinnerCustomAdapter);
                        binding.spinnerType.getBackground().setColorFilter(ContextCompat.getColor(RegisterActivity.this, R.color.white), PorterDuff.Mode.SRC_ATOP);

                        Type type = new TypeToken<List<ClinicTypeModel>>(){}.getType();
                        cacheManager.writeJson(clinicTypeModels,type, CacheUtils.CATEGORY_TYPE);

                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "failure: ", error);
            }
        });
    }
    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private boolean dataInvalid() {
        boolean notValid = false;
        if (binding.etName.length() <= 0) {
            notValid = true;
            binding.etName.setError("Enter valid name.");
        }
        if (binding.etClinic.length() <= 0) {
            notValid = true;
            binding.etClinic.setError("Enter valid clinic name.");
        }
        if (binding.etMobile.length() <= 0) {
            notValid = true;
            binding.etMobile.setError("Enter valid mobile no.");
        }
        if (binding.etLandline.length() <= 0) {
            notValid = true;
            binding.etLandline.setError("Enter valid landline no.");
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


        ClinicTypeModel model = (ClinicTypeModel) binding.spinnerType.getSelectedItem();
        if (model.getTypeId() == 0) {
            notValid = true;
            Toast.makeText(this, "Please select category.", Toast.LENGTH_SHORT).show();
        }

        if (binding.genderGroup.getCheckedRadioButtonId() == -1) {
            notValid = true;
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
        }
        return notValid;
    }

    private void setValueFromDb() {
        Cursor cursor = getContentResolver().query(UserContract.DoctorInfoTable.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToLast()) {
            binding.etName.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_NAME)));
            binding.etClinic.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_CENTRE_NAME)));
            binding.etMobile.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_MOBILE_NO)));
            binding.etLandline.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_LANDLINE_NO)));
            binding.etEmail.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_EMAIL)));
            binding.etAddress.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_ADDRESS)));
            int type = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_CENTRE_TYPE));
            String gender = cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_GENDER));
            binding.spinnerType.setSelection(type);
            if ("Male".equalsIgnoreCase(gender)) {
                binding.rbMale.setChecked(true);
            } else if ("female".equalsIgnoreCase(gender)) {
                binding.rbFemale.setChecked(true);
            }
            cursor.close();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
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
}
