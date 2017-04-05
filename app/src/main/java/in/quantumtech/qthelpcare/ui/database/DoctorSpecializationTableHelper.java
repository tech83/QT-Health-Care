package in.quantumtech.qthelpcare.ui.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import in.quantumtech.qthelpcare.ui.database.UserContract.DoctorSpecializationTable;

/**
 * Created by quantum on 27/3/17.
 */

public class DoctorSpecializationTableHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + DoctorSpecializationTable.TABLE_NAME + " (" +
                    DoctorSpecializationTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DoctorSpecializationTable.COLUMN_NAME_SPECIALIZATION_TITLE + UserDbHelper.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DoctorSpecializationTable.TABLE_NAME;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public static ContentValues newInsertValues(String title){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorSpecializationTable.COLUMN_NAME_SPECIALIZATION_TITLE,title);
        return contentValues;
    }
}
