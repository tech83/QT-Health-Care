package in.quantumtech.qthelpcare.ui.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by quantum on 27/3/17.
 */

public class DoctorServicesTableHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + UserContract.DoctorServicesTable.TABLE_NAME + " (" +
                    UserContract.DoctorServicesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.DoctorServicesTable.COLUMN_NAME_SERVICES_TITLE + UserDbHelper.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserContract.DoctorServicesTable.TABLE_NAME;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public static ContentValues newInsertValues(String title){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.DoctorServicesTable.COLUMN_NAME_SERVICES_TITLE,title);
        return contentValues;
    }

}
