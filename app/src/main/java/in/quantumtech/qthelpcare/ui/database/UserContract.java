package in.quantumtech.qthelpcare.ui.database;

import android.net.Uri;
import android.provider.BaseColumns;

import in.quantumtech.qthelpcare.ui.provider.MyDatabaseProvider;

/**
 * Created by quantum on 27/3/17.
 */

public final class UserContract {
    public UserContract() {
    }

    public static abstract class DoctorInfoTable implements BaseColumns{
        public static final String TABLE_NAME = "doctor_info";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PROFILE_PIC = "profile_pic";
        public static final String COLUMN_NAME_CENTRE_TYPE = "centre_type";
        public static final String COLUMN_NAME_CENTRE_NAME = "centre_name";
        public static final String COLUMN_NAME_MOBILE_NO = "mobile_no";
        public static final String COLUMN_NAME_LANDLINE_NO = "landline_no";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_EXPERIENCE = "experience";
        public static final String COLUMN_NAME_FEE = "fee";
        public static final String COLUMN_NAME_PAYMENT_METHOD = "payment_method";
        public static final String COLUMN_NAME_AMOUNT_PAID = "amount_paid";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + MyDatabaseProvider.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
    }
    public static abstract class DoctorQualificationTable implements BaseColumns{
        public static final String TABLE_NAME = "doctor_qualification";
        public static final String COLUMN_NAME_QUALIFICATION_TITLE = "title";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + MyDatabaseProvider.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
    }
    public static abstract class DoctorServicesTable implements BaseColumns{
        public static final String TABLE_NAME = "doctor_qualification";
        public static final String COLUMN_NAME_SERVICES_TITLE = "title";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + MyDatabaseProvider.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
    }
    public static abstract class DoctorSpecializationTable implements BaseColumns{
        public static final String TABLE_NAME = "doctor_specialization";
        public static final String COLUMN_NAME_SPECIALIZATION_TITLE = "title";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + MyDatabaseProvider.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
    }
    public static abstract class DoctorAvailableTable implements BaseColumns{
        public static final String TABLE_NAME = "doctor_availability";
        public static final String COLUMN_NAME_DAY_FROM = "day_s";
        public static final String COLUMN_NAME_DAY_TO = "day_e";
        public static final String COLUMN_NAME_MORNING_TIME_FROM = "morning_time_from";
        public static final String COLUMN_NAME_MORNING_TIME_TO = "morning_time_to";
        public static final String COLUMN_NAME_EVENING_TIME_FROM = "evening_time_from";
        public static final String COLUMN_NAME_EVENING_TIME_TO = "evening_to";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + MyDatabaseProvider.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
    }

    public static abstract class PatientInfoTable implements BaseColumns{
        public static final String TABLE_NAME = "patient_info";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PROFILE_PIC = "profile_pic";
        public static final String COLUMN_NAME_MOBILE_NO = "mobile_no";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_GENDER = "gender";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + MyDatabaseProvider.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
    }

    public static abstract class SearchTable implements BaseColumns{
        public static final String TABLE_NAME = "search";
        public static final String COLUMN_NAME_SEARCH_TEXT = "text";

        public static final Uri CONTENT_URI =  Uri.parse("content://" + MyDatabaseProvider.AUTHORITY + "/" + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MyDatabaseProvider.AUTHORITY + "." + TABLE_NAME;
    }
}
