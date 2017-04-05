package in.quantumtech.qthelpcare.ui.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by quantum on 3/4/17.
 */

public class SearchTableHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + UserContract.SearchTable.TABLE_NAME + " (" +
                    UserContract.SearchTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.SearchTable.COLUMN_NAME_SEARCH_TEXT + UserDbHelper.TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + UserContract.SearchTable.TABLE_NAME;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    public static void onUpgrade(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public static ContentValues newInsertValues(String text){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.SearchTable.COLUMN_NAME_SEARCH_TEXT,text);
        return contentValues;
    }
}
