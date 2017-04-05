package in.quantumtech.qthelpcare.ui.doctor.ui.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.databinding.ActivityRegister2Binding;
import in.quantumtech.qthelpcare.ui.database.DoctorAvailableTableHelper;
import in.quantumtech.qthelpcare.ui.database.DoctorInfoTableHelper;
import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.doctor.ui.adapters.ServicesAdapter;
import in.quantumtech.qthelpcare.ui.model.ListModel;
import in.quantumtech.qthelpcare.ui.doctor.ui.adapters.AvailabilityAdapter;
import in.quantumtech.qthelpcare.ui.doctor.ui.adapters.QualificationAdapter;
import in.quantumtech.qthelpcare.ui.doctor.ui.adapters.SpecializationAdapter;
import in.quantumtech.qthelpcare.ui.utils.Constants;
import in.quantumtech.qthelpcare.ui.utils.M;
import in.quantumtech.qthelpcare.ui.utils.PreferenceUtils;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private ActivityRegister2Binding binding;
    private AvailabilityAdapter availabilityAdapter;
    private QualificationAdapter qualificationAdapter;
    private SpecializationAdapter specializationAdapter;
    private ServicesAdapter servicesAdapter;

    public static List<CharSequence> daysArray = new ArrayList<>(7);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register2);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarMessage.setText(getString(R.string.welcome_toolbar, "Doctor"));

        try {
            setupQualifications();
            setupSpecialization();
        } catch (IOException e) {
            e.printStackTrace();
        }
        daysArray.clear();
        daysArray.add("MON");
        daysArray.add("TUE");
        daysArray.add("WED");
        daysArray.add("THU");
        daysArray.add("FRI");
        daysArray.add("SAT");
        daysArray.add("SUN");
        availabilityAdapter = new AvailabilityAdapter(this,R.layout.item_available_time,null);
        qualificationAdapter = new QualificationAdapter(this,R.layout.item_qualification,null);
        specializationAdapter = new SpecializationAdapter(this,R.layout.item_qualification,null);
        servicesAdapter = new ServicesAdapter(this,R.layout.item_qualification,null);

        binding.listView.setAdapter(availabilityAdapter);
        binding.addedQualifications.setAdapter(qualificationAdapter);

        binding.addedSpecialization.setAdapter(specializationAdapter);
        binding.btnNext.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.btnAdd.setOnClickListener(this);


        setValueFromDb();
        Cursor cursor = getContentResolver().query(UserContract.DoctorAvailableTable.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToNext()){
            cursor.close();
        }
        else {
            ContentValues contentValues = DoctorAvailableTableHelper.newInsertValues("MON","FRI","10:00","13:00","16:00","19:00");
            getContentResolver().insert(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues);
        }

        getSupportLoaderManager().initLoader(Constants.CURSOR_ID_AVAILABILITY,null,this);
        getSupportLoaderManager().initLoader(Constants.CURSOR_ID_QUALIFICATION,null,this);
        getSupportLoaderManager().initLoader(Constants.CURSOR_ID_SPECILIZATION,null,this);
        getSupportLoaderManager().initLoader(Constants.CURSOR_ID_SERVICES,null,this);
    }


    private void setupSpecialization() throws IOException {
        InputStream open = getAssets().open("json/specialization.json");
        int size = open.available();
        byte[] buffer = new byte[size];
        open.read(buffer);
        open.close();
        String qualificationJson = new String(buffer,"UTF-8");

        Type type = new TypeToken<List<ListModel>>(){}.getType();
        Gson gson = new GsonBuilder().create();
        List<ListModel> items = gson.fromJson(qualificationJson, type);
        List<String> qualificationArray = new ArrayList<>();
        for (ListModel model : items) {
            qualificationArray.add(model.getTitle());
        }

        ArrayAdapter<String> qualifications = new ArrayAdapter<>(this,R.layout.spinner_type_list,qualificationArray);
        binding.etSpecilization.setAdapter(qualifications);
        binding.etSpecilization.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = (String) parent.getItemAtPosition(position);
                ContentValues contentValues = new ContentValues();
                contentValues.put(UserContract.DoctorSpecializationTable.COLUMN_NAME_SPECIALIZATION_TITLE,title);
                getContentResolver().insert(UserContract.DoctorSpecializationTable.CONTENT_URI,contentValues);
            }
        });
    }

    private void setupQualifications() throws IOException {
        InputStream open = getAssets().open("json/qualification.json");
        int size = open.available();
        byte[] buffer = new byte[size];
        open.read(buffer);
        open.close();
        String qualificationJson = new String(buffer,"UTF-8");

        Type type = new TypeToken<List<ListModel>>(){}.getType();
        Gson gson = new GsonBuilder().create();
        List<ListModel> items = gson.fromJson(qualificationJson, type);
        List<String> qualificationArray = new ArrayList<>();
        for (ListModel model : items) {
            qualificationArray.add(model.getTitle());
        }

        ArrayAdapter<String> qualifications = new ArrayAdapter<>(this, R.layout.spinner_type_list, qualificationArray);
        binding.etQualification.setAdapter(qualifications);

        binding.etQualification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = (String) parent.getItemAtPosition(position);
                ContentValues contentValues = new ContentValues();
                contentValues.put(UserContract.DoctorQualificationTable.COLUMN_NAME_QUALIFICATION_TITLE,title);
                getContentResolver().insert(UserContract.DoctorQualificationTable.CONTENT_URI,contentValues);
            }
        });
    }



    public static void start(Context context) {
        Intent starter = new Intent(context, Register2Activity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_out_right,R.anim.anim_slide_in_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                String string = PreferenceUtils.getSharedPreferences(Register2Activity.this).getString(PreferenceUtils.USER_INFO_URI, null);
                if (string != null){
                    Uri uri = Uri.parse(string);
                    ContentValues contentValues = DoctorInfoTableHelper.newDoctorInfo2(binding.etExperience.getText().toString(),binding.etFees.getText().toString());
                    getContentResolver().update(uri,contentValues,null,null);
                }

                PaymentDoctorActivity.start(this);
                overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_left);
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_add:
                Cursor cursor = getContentResolver().query(UserContract.DoctorAvailableTable.CONTENT_URI, null, null, null, null);
                if (cursor != null && cursor.moveToLast()){
                    String day_s = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_FROM));
                    String day_e = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_TO));
                    int stringIndex = M.getDayIndex(day_s, daysArray);
                    int endIndex = M.getDayIndex(day_e, daysArray);

                    /*for (int i = endIndex - 1; i >= stringIndex ; i--) {
                         daysArray.remove(i);
                    }*/
                    cursor.close();

                    ContentValues contentValues = DoctorAvailableTableHelper.newInsertValues((String) daysArray.get(endIndex > daysArray.size() - 1 ? endIndex - 1 : daysArray.size() - 1), (String) daysArray.get(daysArray.size() - 1),"10:00","13:00","16:00","19:00");
                    getContentResolver().insert(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues);
                }
                else {
                    ContentValues contentValues = DoctorAvailableTableHelper.newInsertValues("MON","FRI","10:00","13:00","16:00","19:00");
                    getContentResolver().insert(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setValueFromDb(){
        Cursor cursor = getContentResolver().query(UserContract.DoctorInfoTable.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToLast()){
            binding.etExperience.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_EXPERIENCE)));
            binding.etFees.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorInfoTable.COLUMN_NAME_FEE)));
            cursor.close();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case Constants.CURSOR_ID_AVAILABILITY:
                return new CursorLoader(this, UserContract.DoctorAvailableTable.CONTENT_URI, null, null, null, null);
            case Constants.CURSOR_ID_QUALIFICATION:
                return new CursorLoader(this, UserContract.DoctorQualificationTable.CONTENT_URI, null, null, null, null);
            case Constants.CURSOR_ID_SPECILIZATION:
                return new CursorLoader(this, UserContract.DoctorSpecializationTable.CONTENT_URI, null, null, null, null);
            case Constants.CURSOR_ID_SERVICES:
                return new CursorLoader(this, UserContract.DoctorServicesTable.CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case Constants.CURSOR_ID_AVAILABILITY:
                availabilityAdapter.swapCursor(data);
                M.setListViewHeightBasedOnChildren(binding.listView);
                break;
            case Constants.CURSOR_ID_QUALIFICATION:
                qualificationAdapter.swapCursor(data);
                M.setListViewHeightBasedOnChildren(binding.addedQualifications);
                break;
            case Constants.CURSOR_ID_SPECILIZATION:
                specializationAdapter.swapCursor(data);
                M.setListViewHeightBasedOnChildren(binding.addedSpecialization);
                break;
            case Constants.CURSOR_ID_SERVICES:
                servicesAdapter.swapCursor(data);
                M.setListViewHeightBasedOnChildren(binding.addedServices);
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()){
            case Constants.CURSOR_ID_AVAILABILITY:
                availabilityAdapter.swapCursor(null);
                break;
            case Constants.CURSOR_ID_QUALIFICATION:
                qualificationAdapter.swapCursor(null);
                break;
            case Constants.CURSOR_ID_SPECILIZATION:
                specializationAdapter.swapCursor(null);
                break;
            case Constants.CURSOR_ID_SERVICES:
                servicesAdapter.swapCursor(null);
                break;
        }
    }
}
