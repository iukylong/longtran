package com.kait.longlongtran.storyvoz.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.util.Log;

/**
 * Created by LongLongTran on 2/5/2018.
 */

public class NetworkUtil {
    private final String TAG = NetworkUtil.class.getSimpleName();
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null != networkInfo) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi Đã Được Enable";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "3G/4G Đã Được Enable";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not Connected to Internet";
        }
        return status;
    }

    /**
     * check internet only
     */
    public boolean checkInternet(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getNetworkInfo(0);
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            } else {
                networkInfo = cm.getNetworkInfo(1);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (RuntimeException e) {
            Log.d(TAG, "#checkInternet + " + e);
            return false;
        }
        return status;

    }

}
