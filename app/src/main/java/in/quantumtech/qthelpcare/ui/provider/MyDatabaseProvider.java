package in.quantumtech.qthelpcare.ui.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.database.UserDbHelper;

public class MyDatabaseProvider extends ContentProvider {
    public static final String AUTHORITY = "in.quantumtech.qthelpcare.dataprovider";
    private static final int DOCTOR_INFO = 1;
    private static final int DOCTOR_QUALIFICATION = 2;
    private static final int DOCTOR_SPECIALIZATION = 3;
    private static final int DOCTOR_AVAILABLE = 4;
    private static final int DOCTOR_ID = 5;
    private static final int PATIENT_INFO = 6;
    private static final int PATIENT_ID = 7;
    private static final int SEARCH = 8;
    private static final int SEARCH_ID = 9;

    private final UriMatcher uriMatcher;
    private UserDbHelper dbHelper;

    public MyDatabaseProvider() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, UserContract.DoctorInfoTable.TABLE_NAME, DOCTOR_INFO);
        uriMatcher.addURI(AUTHORITY, UserContract.DoctorInfoTable.TABLE_NAME + "/#", DOCTOR_ID);
        uriMatcher.addURI(AUTHORITY, UserContract.DoctorQualificationTable.TABLE_NAME, DOCTOR_QUALIFICATION);
        uriMatcher.addURI(AUTHORITY, UserContract.DoctorSpecializationTable.TABLE_NAME, DOCTOR_SPECIALIZATION);
        uriMatcher.addURI(AUTHORITY, UserContract.DoctorAvailableTable.TABLE_NAME, DOCTOR_AVAILABLE);

        uriMatcher.addURI(AUTHORITY, UserContract.PatientInfoTable.TABLE_NAME, PATIENT_INFO);
        uriMatcher.addURI(AUTHORITY, UserContract.PatientInfoTable.TABLE_NAME + "/#", PATIENT_ID);

        uriMatcher.addURI(AUTHORITY, UserContract.SearchTable.TABLE_NAME, SEARCH);
        uriMatcher.addURI(AUTHORITY, UserContract.SearchTable.TABLE_NAME + "/#", SEARCH_ID);

    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String table;
        switch (uriMatcher.match(uri)) {
            case DOCTOR_INFO:
                table = UserContract.DoctorInfoTable.TABLE_NAME;
                break;

            case DOCTOR_QUALIFICATION:
                table = UserContract.DoctorQualificationTable.TABLE_NAME;
                break;
            case DOCTOR_SPECIALIZATION:
                table = UserContract.DoctorSpecializationTable.TABLE_NAME;
                break;
            case DOCTOR_AVAILABLE:
                table = UserContract.DoctorAvailableTable.TABLE_NAME;
                break;
            case PATIENT_INFO:
                table = UserContract.PatientInfoTable.TABLE_NAME;
                break;
            case SEARCH:
                table = UserContract.SearchTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(table, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case DOCTOR_INFO:
                return UserContract.DoctorInfoTable.CONTENT_TYPE;

            case DOCTOR_QUALIFICATION:
                return UserContract.DoctorQualificationTable.CONTENT_TYPE;
            case DOCTOR_SPECIALIZATION:
                return UserContract.DoctorSpecializationTable.CONTENT_TYPE;
            case DOCTOR_AVAILABLE:
                return UserContract.DoctorAvailableTable.CONTENT_TYPE;
            case DOCTOR_ID:
                return UserContract.DoctorInfoTable.CONTENT_ITEM_TYPE;
            case PATIENT_INFO:
                return UserContract.PatientInfoTable.CONTENT_TYPE;
            case PATIENT_ID:
                return UserContract.PatientInfoTable.CONTENT_ITEM_TYPE;
            case SEARCH:
                return UserContract.SearchTable.CONTENT_TYPE;
            case SEARCH_ID:
                return UserContract.SearchTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        String table;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case DOCTOR_INFO:
                table = UserContract.DoctorInfoTable.TABLE_NAME;
                break;

            case DOCTOR_QUALIFICATION:
                table = UserContract.DoctorQualificationTable.TABLE_NAME;
                break;

            case DOCTOR_SPECIALIZATION:
                table = UserContract.DoctorSpecializationTable.TABLE_NAME;
                break;

            case DOCTOR_AVAILABLE:
                table = UserContract.DoctorAvailableTable.TABLE_NAME;
                break;
            case PATIENT_INFO:
                table = UserContract.PatientInfoTable.TABLE_NAME;
                break;
            case SEARCH:
                table = UserContract.SearchTable.TABLE_NAME;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        long rowId = db.insert(table, null, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        dbHelper = UserDbHelper.getInstance(getContext());
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        String table;
        final int code = uriMatcher.match(uri);
        switch (code) {
            case DOCTOR_INFO:
                table = UserContract.DoctorInfoTable.TABLE_NAME;
                break;
            case DOCTOR_QUALIFICATION:
                table = UserContract.DoctorQualificationTable.TABLE_NAME;
                break;
            case DOCTOR_SPECIALIZATION:
                table = UserContract.DoctorSpecializationTable.TABLE_NAME;
                break;
            case DOCTOR_AVAILABLE:
                table = UserContract.DoctorAvailableTable.TABLE_NAME;
                break;
            case PATIENT_INFO:
                table = UserContract.PatientInfoTable.TABLE_NAME;
                break;
            case SEARCH:
                table = UserContract.SearchTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(table, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String table;

        switch (uriMatcher.match(uri)) {
            case DOCTOR_INFO:
                table = UserContract.DoctorInfoTable.TABLE_NAME;
                break;

            case DOCTOR_QUALIFICATION:
                table = UserContract.DoctorQualificationTable.TABLE_NAME;
                break;

            case DOCTOR_SPECIALIZATION:
                table = UserContract.DoctorSpecializationTable.TABLE_NAME;
                break;

            case DOCTOR_AVAILABLE:
                table = UserContract.DoctorAvailableTable.TABLE_NAME;
                break;

            case DOCTOR_ID:
                table = UserContract.DoctorInfoTable.TABLE_NAME;
                selection = DatabaseUtils.concatenateWhere(UserContract.DoctorInfoTable._ID + " = " + ContentUris.parseId(uri), selection);
                break;
            case PATIENT_INFO:
                table = UserContract.PatientInfoTable.TABLE_NAME;
                break;
            case PATIENT_ID:
                table = UserContract.PatientInfoTable.TABLE_NAME;
                selection = DatabaseUtils.concatenateWhere(UserContract.PatientInfoTable._ID + " = " + ContentUris.parseId(uri), selection);
                break;
            case SEARCH:
                table = UserContract.SearchTable.TABLE_NAME;
                break;
            case SEARCH_ID:
                table = UserContract.SearchTable.TABLE_NAME;
                selection = DatabaseUtils.concatenateWhere(UserContract.SearchTable._ID + " = " + ContentUris.parseId(uri), selection);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.update(table, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();

            return results;
        } catch (OperationApplicationException e) {
            e.printStackTrace();
            return null;
        } finally {
            db.endTransaction();
        }
    }
}
