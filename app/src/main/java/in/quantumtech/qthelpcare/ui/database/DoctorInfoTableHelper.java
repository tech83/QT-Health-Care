package in.quantumtech.qthelpcare.ui.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import in.quantumtech.qthelpcare.ui.database.UserContract.DoctorInfoTable;
import in.quantumtech.qthelpcare.ui.model.LoginResponseModel;

/**
 * Created by quantum on 27/3/17.
 */

public class DoctorInfoTableHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + DoctorInfoTable.TABLE_NAME + " (" +
                    DoctorInfoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DoctorInfoTable.COLUMN_NAME_NAME + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_CENTRE_TYPE + UserDbHelper.INTEGER_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_CENTRE_NAME + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_MOBILE_NO + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_LANDLINE_NO + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_EMAIL + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_ADDRESS + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_GENDER + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_EXPERIENCE + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_FEE + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_PAYMENT_METHOD + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_AMOUNT_PAID + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorInfoTable.COLUMN_NAME_PROFILE_PIC + UserDbHelper.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DoctorInfoTable.TABLE_NAME;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public static ContentValues newDoctorInfo1(String name,int type,String centreName,String mobile,String tell,String email,String address,String gender){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorInfoTable.COLUMN_NAME_NAME,name);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_CENTRE_TYPE,type);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_CENTRE_NAME,centreName);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_MOBILE_NO,mobile);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_LANDLINE_NO,tell);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_EMAIL,email);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_ADDRESS,address);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_GENDER,gender);
        return contentValues;
    }

    public static ContentValues newDoctorInfo2(String experience,String fees){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorInfoTable.COLUMN_NAME_EXPERIENCE,experience);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_FEE,fees);
        return contentValues;
    }

    public static ContentValues newDoctorProfilePic(String profilePic){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorInfoTable.COLUMN_NAME_PROFILE_PIC,profilePic);
        return contentValues;
    }

    public static ContentValues newDoctorInfo(LoginResponseModel model){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorInfoTable.COLUMN_NAME_NAME,model.getName());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_CENTRE_TYPE,model.getType());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_CENTRE_NAME,model.getCentreName());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_MOBILE_NO,model.getMobileNo());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_LANDLINE_NO,model.getLandLineNo());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_EMAIL,model.getEmailId());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_ADDRESS,model.getAddress());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_GENDER,model.getGender());
        //contentValues.put(DoctorInfoTable.COLUMN_NAME_EXPERIENCE,model.get);
        //contentValues.put(DoctorInfoTable.COLUMN_NAME_FEE,model);
        contentValues.put(DoctorInfoTable.COLUMN_NAME_PAYMENT_METHOD,model.getPaymentMethod());
        contentValues.put(DoctorInfoTable.COLUMN_NAME_AMOUNT_PAID,model.getAmountPaid());

        return contentValues;
    }
}
