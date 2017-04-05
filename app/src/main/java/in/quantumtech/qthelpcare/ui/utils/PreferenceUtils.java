package in.quantumtech.qthelpcare.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.learnpainless.core.security.AESEncryption;

/**
 * Created by quantum on 28/3/17.
 */

public class PreferenceUtils {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "password";
    public static final String USER_INFO_URI = "user_info_uri";
    public static final String FCM_TOKEN = "fcm_token";

    public static void setLoginUser(Context context, String user, String password) {
        String encryptedUser = AESEncryption.encrypt(context, user);
        String encryptedPassword = AESEncryption.encrypt(context, password);
        getSharedPreferences(context).edit().putString(USERNAME, encryptedUser).putString(PASSWORD, encryptedPassword).apply();
    }

    public static String getUser(Context context) {
        String encryptedUser = getSharedPreferences(context).getString(USERNAME, null);
        return encryptedUser != null ? AESEncryption.decrypt(context, encryptedUser) : null;
    }

    public static String getPassword(Context context) {
        String encryptedPassword = getSharedPreferences(context).getString(PASSWORD, null);
        return encryptedPassword != null ? AESEncryption.decrypt(context, encryptedPassword) : null;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isUserLogged(Context context){
        return getSharedPreferences(context).getString(USERNAME, null) != null && getSharedPreferences(context).getString(PASSWORD, null) != null;
    }

    public static void setUserType(Context context, String name){
        getSharedPreferences(context).edit().putString(Constants.LOGIN_TYPE_KEY,name).apply();
    }

    public static String getUserType(Context context){
        return getSharedPreferences(context).getString(Constants.LOGIN_TYPE_KEY,null);
    }

    public static void setFcmToken(Context context,String token){
        getSharedPreferences(context).edit().putString(FCM_TOKEN,token).apply();
    }

    public static String getFcmToken(Context context){
        return getSharedPreferences(context).getString(FCM_TOKEN,null);
    }
}
