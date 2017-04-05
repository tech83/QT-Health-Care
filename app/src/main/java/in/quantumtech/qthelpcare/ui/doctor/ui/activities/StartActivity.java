package in.quantumtech.qthelpcare.ui.doctor.ui.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.ui.BaseActivity;
import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.utils.Constants;
import in.quantumtech.qthelpcare.ui.utils.PreferenceUtils;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void openLogin(View view){
        switch (view.getId()){
            case R.id.btn_patient:
                PreferenceUtils.setUserType(this, Constants.LOGIN_TYPE_PATIENT);
                showAlert(getString(R.string.patient));
                break;
            case R.id.btn_clinic:
                writeToDB(1);
                PreferenceUtils.setUserType(this,Constants.LOGIN_TYPE_DOCTOR);
                showAlert(getString(R.string.clinic));
                break;
            case R.id.btn_hospital:
                writeToDB(2);
                PreferenceUtils.setUserType(this,Constants.LOGIN_TYPE_DOCTOR);
                showAlert(getString(R.string.hospital));
                break;
            case R.id.btn_diagnostic:
                writeToDB(3);
                PreferenceUtils.setUserType(this,Constants.LOGIN_TYPE_DOCTOR);
                showAlert(getString(R.string.diagnostic_centre));
                break;
            case R.id.btn_medical:
                writeToDB(4);
                PreferenceUtils.setUserType(this,Constants.LOGIN_TYPE_DOCTOR);
                showAlert(getString(R.string.medical_store));
                break;
        }

    }
    private void writeToDB(int type){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.DoctorInfoTable.COLUMN_NAME_CENTRE_TYPE,type);
        String s = PreferenceUtils.getSharedPreferences(this).getString(PreferenceUtils.USER_INFO_URI, null);
        if (s == null) {
            Uri insert = getContentResolver().insert(UserContract.DoctorInfoTable.CONTENT_URI, contentValues);
            PreferenceUtils.getSharedPreferences(this).edit().putString(PreferenceUtils.USER_INFO_URI, String.valueOf(insert)).apply();
        } else {
            getContentResolver().update(Uri.parse(s), contentValues, null, null);
        }
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, StartActivity.class);
        context.startActivity(starter);
    }

    private void showAlert(String name){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_login_as);
        TextView title1 = (TextView) dialog.findViewById(R.id.title1);
        TextView title2 = (TextView) dialog.findViewById(R.id.title2);
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        title1.setText(getString(R.string.continue_1,name));
        title2.setText(getString(R.string.continue_2,name));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(StartActivity.this);
                dialog.dismiss();
                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.setUserType(StartActivity.this, null);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
