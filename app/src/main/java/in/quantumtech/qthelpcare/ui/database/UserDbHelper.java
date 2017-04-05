package in.quantumtech.qthelpcare.ui.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by quantum on 27/3/17.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "user.db";

    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";

    private static UserDbHelper instance;

    public static synchronized UserDbHelper getInstance(Context context){
        if (instance == null) {
            instance = new UserDbHelper(context);
        }
        return instance;
    }

    private UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DoctorInfoTableHelper.onCreate(db);
        DoctorAvailableTableHelper.onCreate(db);
        DoctorQualificationTableHelper.onCreate(db);
        DoctorSpecializationTableHelper.onCreate(db);
        DoctorServicesTableHelper.onCreate(db);
        PatientInfoTableHelper.onCreate(db);
        SearchTableHelper.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DoctorInfoTableHelper.onUpgrade(db);
        //DoctorAvailableTableHelper.onUpgrade(db);
        //DoctorQualificationTableHelper.onUpgrade(db);
        //DoctorSpecializationTableHelper.onUpgrade(db);
        //PatientInfoTableHelper.onUpgrade(db);
        //SearchTableHelper.onUpgrade(db);
    }
}
