package in.quantumtech.qthelpcare.ui.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import in.quantumtech.qthelpcare.ui.database.UserContract.PatientInfoTable;
import in.quantumtech.qthelpcare.ui.model.LoginResponseModel;

/**
 * Created by quantum on 30/3/17.
 */

public class PatientInfoTableHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + PatientInfoTable.TABLE_NAME + " (" +
                    PatientInfoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PatientInfoTable.COLUMN_NAME_NAME + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    PatientInfoTable.COLUMN_NAME_MOBILE_NO + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    PatientInfoTable.COLUMN_NAME_EMAIL + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    PatientInfoTable.COLUMN_NAME_ADDRESS + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    PatientInfoTable.COLUMN_NAME_GENDER + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    PatientInfoTable.COLUMN_NAME_PROFILE_PIC + UserDbHelper.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PatientInfoTable.TABLE_NAME;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public static ContentValues newPatientInfo(String name,String mobile,String email, String address){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PatientInfoTable.COLUMN_NAME_NAME,name);
        contentValues.put(PatientInfoTable.COLUMN_NAME_MOBILE_NO,mobile);
        contentValues.put(PatientInfoTable.COLUMN_NAME_EMAIL,email);
        contentValues.put(PatientInfoTable.COLUMN_NAME_ADDRESS,address);
        return contentValues;
    }

    public static ContentValues newProfilePic(String profilePic){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PatientInfoTable.COLUMN_NAME_PROFILE_PIC,profilePic);
        return contentValues;
    }

    public static ContentValues newPatientInfo(LoginResponseModel model){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PatientInfoTable.COLUMN_NAME_NAME,model.getName());
        contentValues.put(PatientInfoTable.COLUMN_NAME_MOBILE_NO,model.getMobileNo());
        contentValues.put(PatientInfoTable.COLUMN_NAME_EMAIL,model.getEmailId());
        contentValues.put(PatientInfoTable.COLUMN_NAME_ADDRESS,model.getAddress());
        contentValues.put(PatientInfoTable.COLUMN_NAME_GENDER, model.getGender());
        return contentValues;
    }
}
