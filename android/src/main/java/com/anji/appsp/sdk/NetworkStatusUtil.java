package com.anji.appsp.sdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkStatusUtil {
    public static NetworkStatus getNetworkStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return NetworkStatus.none;
        }

        switch (info.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                return NetworkStatus.wifi;
            case ConnectivityManager.TYPE_MOBILE:
                return NetworkStatus.mobile;
            default:
                return NetworkStatus.other;
        }
    }

    public static boolean isAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static boolean isWifi(Context context) {
        return getNetworkStatus(context) == NetworkStatus.wifi;
    }

    public enum NetworkStatus {
        none,
        wifi,
        mobile,
        other
    }
}
