package in.quantumtech.qthelpcare.ui.patient.ui.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.learnpainless.core.utils.PermissionsUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.databinding.FragmentPatientHomeBinding;
import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.doctor.ui.adapters.CountrySpinnerAdapter;
import in.quantumtech.qthelpcare.ui.model.SpinnerModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PermissionsUtil.OnPermissionsResult{
    private static final int REQUEST_CHECK_SETTINGS = 1221;
    private FragmentPatientHomeBinding binding;
    private ResourceCursorAdapter searchAdapter;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private PermissionsUtil permissionsUtil;

    public PatientHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        permissionsUtil = PermissionsUtil.getInstance(getActivity());
        setupGoogleApi();
        setupSearch();
        setupToggles();
        try {
            setupSpinners();
        } catch (IOException e) {
            e.printStackTrace();
        }
        binding.imgPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (permissionsUtil.hasPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})){
                        if (googleApiClient != null) {
                            googleApiClient.connect();
                        }
                    }
                    else {
                        permissionsUtil.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                    }
                }
                else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }
            }
        });
        getLoaderManager().initLoader(0, null, this);
    }

    private void setupGoogleApi() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext(), this, this).addApi(LocationServices.API).build();
        }
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onStop() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    private void setupSpinners() throws IOException {
        InputStream open = getContext().getAssets().open("json/states.json");
        int size = open.available();
        byte[] buffer = new byte[size];
        open.read(buffer);
        open.close();
        String qualificationJson = new String(buffer, "UTF-8");

        Type type = new TypeToken<List<SpinnerModel>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        List<SpinnerModel> items = gson.fromJson(qualificationJson, type);
        CountrySpinnerAdapter spinnerAdapter = new CountrySpinnerAdapter(getContext(), R.layout.spinner_type_list, items);
        binding.state.setAdapter(spinnerAdapter);
        binding.city.setAdapter(spinnerAdapter);
    }

    private void checkSettings(GoogleApiClient googleApiClient){
        LocationRequest highAccuracy = LocationRequest.create();
        highAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        highAccuracy.setInterval(30 * 1000);
        highAccuracy.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(highAccuracy)
                .setAlwaysShow(true);

        PendingResult<LocationSettingsResult> pendingResult = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();

                switch (status.getStatusCode()){
                    case LocationSettingsStatusCodes.SUCCESS:

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(getActivity(),REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });

    }

    private void setupToggles() {
        binding.tbClinic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.tbClinic.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tb_clinic, 0, 0, 0);
                } else {
                    binding.tbClinic.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tb_clinic_white, 0, 0, 0);
                }
            }
        });
        binding.tbDiagnostic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.tbDiagnostic.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tb_daignostic, 0, 0, 0);
                } else {
                    binding.tbDiagnostic.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tb_daignostic_white, 0, 0, 0);
                }
            }
        });
        binding.tbDoctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.tbDoctor.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable._tb_hospital, 0, 0, 0);
                } else {
                    binding.tbDoctor.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tb_hospital_white, 0, 0, 0);
                }
            }
        });
    }

    private void setupSearch() {
        searchAdapter = new ResourceCursorAdapter(getContext(), R.layout.spinner_state_list, null, 0) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                viewHolder.textView.setText(cursor.getString(cursor.getColumnIndex(UserContract.SearchTable.COLUMN_NAME_SEARCH_TEXT)));
            }

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = super.newView(context, cursor, parent);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.text);
                view.setTag(viewHolder);
                return view;
            }
        };
        binding.search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String text = cursor.getString(cursor.getColumnIndex(UserContract.SearchTable.COLUMN_NAME_SEARCH_TEXT));
                binding.search.setText(text);
            }
        });

        binding.search.setAdapter(searchAdapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), UserContract.SearchTable.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        searchAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        searchAdapter.swapCursor(null);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkSettings(googleApiClient);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null){
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> fromLocation = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
                if (fromLocation.size() > 0){
                    String addressLine = fromLocation.get(0).getAddressLine(0);
                    binding.location.setText(addressLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void permissionGranted() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void permissionDenied() {
        Toast.makeText(getContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
    }

    public static class ViewHolder{
        TextView textView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(getContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
