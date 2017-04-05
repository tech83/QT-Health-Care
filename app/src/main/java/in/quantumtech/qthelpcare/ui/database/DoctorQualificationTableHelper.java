package in.quantumtech.qthelpcare.ui.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import in.quantumtech.qthelpcare.ui.database.UserContract.DoctorQualificationTable;

/**
 * Created by quantum on 27/3/17.
 */

public class DoctorQualificationTableHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + DoctorQualificationTable.TABLE_NAME + " (" +
                    DoctorQualificationTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DoctorQualificationTable.COLUMN_NAME_QUALIFICATION_TITLE + UserDbHelper.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DoctorQualificationTable.TABLE_NAME;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public static ContentValues newInsertValues(String title){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorQualificationTable.COLUMN_NAME_QUALIFICATION_TITLE,title);
        return contentValues;
    }

}
