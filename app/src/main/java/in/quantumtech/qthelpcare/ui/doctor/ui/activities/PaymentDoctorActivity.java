package in.quantumtech.qthelpcare.ui.doctor.ui.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import java.util.ArrayList;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.databinding.ActivityPaymentDoctorBinding;
import in.quantumtech.qthelpcare.ui.api.APIService;
import in.quantumtech.qthelpcare.ui.api.DoctorAPI;
import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.model.RegisterResponseModel;
import in.quantumtech.qthelpcare.ui.model.DoctorRegisterBaseModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PaymentDoctorActivity extends AppCompatActivity {
    private ActivityPaymentDoctorBinding binding;
    private static final String TAG = "PaymentDoctorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_doctor);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarMessage.setText(getString(R.string.welcome_toolbar, "Doctor"));
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();

            }
        });
    }

    private void postData() {
        ArrayList<String> daysTo = new ArrayList<>();
        ArrayList<String> daysFrom = new ArrayList<>();
        ArrayList<String> m_t_f = new ArrayList<>();
        ArrayList<String> m_t_e = new ArrayList<>();
        ArrayList<String> e_t_f = new ArrayList<>();
        ArrayList<String> e_t_e = new ArrayList<>();
        ArrayList<String> specs = new ArrayList<>();
        ArrayList<String> qualis = new ArrayList<>();
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Registering ...", true, false);
        DoctorAPI doctorAPI = APIService.createService(DoctorAPI.class);
        Cursor cursor = getContentResolver().query(UserContract.DoctorInfoTable.CONTENT_URI, null, null, null, null);
        Cursor cursorTime = getContentResolver().query(UserContract.DoctorAvailableTable.CONTENT_URI, null, null, null, null);
        if (cursorTime != null && cursorTime.moveToFirst()) {
            do {
                daysTo.add(cursorTime.getString(cursorTime.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_TO)));
                daysFrom.add(cursorTime.getString(cursorTime.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_FROM)));
                m_t_f.add(cursorTime.getString(cursorTime.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_FROM)));
                m_t_e.add(cursorTime.getString(cursorTime.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_TO)));
                e_t_f.add(cursorTime.getString(cursorTime.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_FROM)));
                e_t_e.add(cursorTime.getString(cursorTime.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_TO)));
            }
            while (cursorTime.moveToNext());
            cursorTime.close();
        }

        if (cursor != null && cursor.moveToLast()) {

            DoctorRegisterBaseModel model = new DoctorRegisterBaseModel(
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_CENTRE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_CENTRE_NAME)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_MOBILE_NO)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_LANDLINE_NO)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_GENDER)),
                    specs,
                    qualis,
                    daysTo,
                    daysFrom,
                    m_t_f,
                    m_t_e,
                    e_t_f,
                    e_t_e,
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_FEE)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_PAYMENT_METHOD)),
                    cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_AMOUNT_PAID))
            );
            cursor.close();

            doctorAPI.register(model, new Callback<RegisterResponseModel>() {
                @Override
                public void success(RegisterResponseModel registerResponseModel, Response response) {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    if (registerResponseModel != null) {
                        if ("success".equalsIgnoreCase(registerResponseModel.getMessage())) {
                            final Dialog dialog = new Dialog(PaymentDoctorActivity.this);
                            dialog.setContentView(R.layout.popup_thank_you);
                            dialog.setCancelable(false);
                            Button btn = (Button) dialog.findViewById(R.id.btn_ok);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    DoctorHomeActivity.start(PaymentDoctorActivity.this);
                                }
                            });
                            dialog.show();
                        } else {
                            Toast.makeText(PaymentDoctorActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
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

    public static void start(Context context) {
        Intent starter = new Intent(context, PaymentDoctorActivity.class);
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
