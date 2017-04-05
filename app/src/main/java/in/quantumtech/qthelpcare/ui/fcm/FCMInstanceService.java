package in.quantumtech.qthelpcare.ui.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import in.quantumtech.qthelpcare.ui.utils.PreferenceUtils;

/**
 * Created by quantum on 5/4/17.
 */

public class FCMInstanceService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("FCMInstanceService", "onTokenRefresh: " + token);
        //// TODO: 5/4/17 send fcm token to server.
        PreferenceUtils.setFcmToken(this,token);
    }
}
