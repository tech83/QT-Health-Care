package in.quantumtech.qthelpcare.ui.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import in.quantumtech.qthelpcare.ui.database.UserContract.DoctorAvailableTable;
import in.quantumtech.qthelpcare.ui.model.Timing;

/**
 * Created by quantum on 27/3/17.
 */

public class DoctorAvailableTableHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + DoctorAvailableTable.TABLE_NAME + " (" +
                    DoctorAvailableTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DoctorAvailableTable.COLUMN_NAME_DAY_FROM + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorAvailableTable.COLUMN_NAME_DAY_TO + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_FROM + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_TO + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_FROM + UserDbHelper.TEXT_TYPE + UserDbHelper.COMMA_SEP +
                    DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_TO + UserDbHelper.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DoctorAvailableTable.TABLE_NAME;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public static ContentValues newInsertValues(String dayFrom,String dayTo,String mFromTime,String mToTime,String eFromTime,String eToTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_DAY_FROM,dayFrom);
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_DAY_TO,dayTo);
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_FROM,mFromTime);
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_TO,mToTime);
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_FROM,eFromTime);
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_TO,eToTime);
        return contentValues;
    }

    public static ContentValues newInsertValues(Timing model){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_DAY_FROM,model.getDayFrom());
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_DAY_TO,model.getDayTo());
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_FROM,model.getTimefromMorning());
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_TO,model.getTimeToMorning());
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_FROM,model.getTimeFromEvening());
        contentValues.put(DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_TO,model.getTimeToEvening());
        return contentValues;
    }
}
