package com.frostytornado.cordova.plugin.ad.admob;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import android.app.Activity;
import android.util.Log;
//
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.os.Build;
import android.provider.Settings;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.os.Handler;
//
import java.util.*;//Random

interface Plugin {

    public CordovaWebView getWebView();

    public CordovaInterface getCordova();

    public CallbackContext getCallbackContextKeepCallback();
    
    public void sendPluginResult(String strEventName);
}

interface PluginDelegate {

    public void _setLicenseKey(String email, String licenseKey);

    public void _setUp(String bannerAdUnit, String interstitialAdUnit, boolean isOverlap, boolean isTest);

    public void _preloadBannerAd();

    public void _showBannerAd(String position, String size);

    public void _reloadBannerAd();

    public void _hideBannerAd();

    public void _preloadInterstitialAd();

    public void _showInterstitialAd();

    public void onPause(boolean multitasking);

    public void onResume(boolean multitasking);

    public void onDestroy();
}

public class Admob extends CordovaPlugin implements PluginDelegate, Plugin {

    protected static final String LOG_TAG = "Admob";
    protected CallbackContext callbackContextKeepCallback;
    //
    protected PluginDelegate pluginDelegate;
    //
    public String email;
    public String licenseKey;
    public boolean validLicenseKey;
    protected String TEST_BANNER_AD_UNIT = "ca-app-pub-4906074177432504/6997786077";
    protected String TEST_FULL_SCREEN_AD_UNIT = "ca-app-pub-4906074177432504/8474519270";

    @Override
    public void pluginInitialize() {
        super.pluginInitialize();
        //
    }

    //@Override
    //public void onCreate(Bundle savedInstanceState) {//build error
    //	super.onCreate(savedInstanceState);
    //	//
    //}
    //@Override
    //public void onStart() {//build error
    //	super.onStart();
    //	//
    //}
    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        pluginDelegate.onPause(multitasking);
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        pluginDelegate.onResume(multitasking);
    }

    //@Override
    //public void onStop() {//build error
    //	super.onStop();
    //	//
    //}
    @Override
    public void onDestroy() {
        super.onDestroy();
        pluginDelegate.onDestroy();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("setLicenseKey")) {
            setLicenseKey(action, args, callbackContext);

            return true;
        } else if (action.equals("setUp")) {
            setUp(action, args, callbackContext);

            return true;
        } else if (action.equals("preloadBannerAd")) {
            preloadBannerAd(action, args, callbackContext);

            return true;
        } else if (action.equals("showBannerAd")) {
            showBannerAd(action, args, callbackContext);

            return true;
        } else if (action.equals("reloadBannerAd")) {
            reloadBannerAd(action, args, callbackContext);

            return true;
        } else if (action.equals("hideBannerAd")) {
            hideBannerAd(action, args, callbackContext);

            return true;
        } else if (action.equals("preloadInterstitialAd")) {
            Log.d(LOG_TAG, "_preloadInterstitialAd");
            preloadInterstitialAd(action, args, callbackContext);

            return true;
        } else if (action.equals("showInterstitialAd")) {
            showInterstitialAd(action, args, callbackContext);

            return true;
        }

        return false; // Returning false results in a "MethodNotFound" error.
    }

    private void setLicenseKey(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        final String email = args.getString(0);
        final String licenseKey = args.getString(1);
        Log.d(LOG_TAG, String.format("%s", email));
        Log.d(LOG_TAG, String.format("%s", licenseKey));

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _setLicenseKey(email, licenseKey);
            }
        });
    }

    private void setUp(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //Activity activity=cordova.getActivity();
        //webView
        //args.length()
        //args.getString(0)
        //args.getString(1)
        //args.getInt(0)
        //args.getInt(1)
        //args.getBoolean(0)
        //args.getBoolean(1)
        //JSONObject json = args.optJSONObject(0);
        //json.optString("bannerAdUnit")
        //json.optString("interstitialAdUnit")
        //JSONObject inJson = json.optJSONObject("inJson");
        //final String bannerAdUnit = args.getString(0);
        //final String interstitialAdUnit = args.getString(1);				
        //final boolean isOverlap = args.getBoolean(2);				
        //final boolean isTest = args.getBoolean(3);
        //final String[] zoneIds = new String[args.getJSONArray(4).length()];
        //for (int i = 0; i < args.getJSONArray(4).length(); i++) {
        //	zoneIds[i] = args.getJSONArray(4).getString(i);
        //}			
        //Log.d(LOG_TAG, String.format("%s", bannerAdUnit));			
        //Log.d(LOG_TAG, String.format("%s", interstitialAdUnit));
        //Log.d(LOG_TAG, String.format("%b", isOverlap));
        //Log.d(LOG_TAG, String.format("%b", isTest));		
        final String bannerAdUnit = args.getString(0);
        final String interstitialAdUnit = args.getString(1);
        final boolean isOverlap = args.getBoolean(2);
        final boolean isTest = args.getBoolean(3);
        Log.d(LOG_TAG, String.format("%s", bannerAdUnit));
        Log.d(LOG_TAG, String.format("%s", interstitialAdUnit));
        Log.d(LOG_TAG, String.format("%b", isOverlap));
        Log.d(LOG_TAG, String.format("%b", isTest));

        callbackContextKeepCallback = callbackContext;

        if (isOverlap) {
            pluginDelegate = new AdmobOverlap(this);
        } else {
            pluginDelegate = new AdmobSplit(this);
        }

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _setUp(bannerAdUnit, interstitialAdUnit, isOverlap, isTest);
            }
        });
    }
    
    @Override
    public void sendPluginResult(String strEventName){
        PluginResult pr = new PluginResult(PluginResult.Status.OK, strEventName);
        pr.setKeepCallback(true);
        this.getCallbackContextKeepCallback().sendPluginResult(pr);
    }

    private void preloadBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _preloadBannerAd();
            }
        });
    }

    private void showBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        final String position = args.getString(0);
        final String size = args.getString(1);
        Log.d(LOG_TAG, String.format("%s", position));
        Log.d(LOG_TAG, String.format("%s", size));

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _showBannerAd(position, size);
            }
        });
    }

    private void reloadBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _reloadBannerAd();
            }
        });
    }

    private void hideBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _hideBannerAd();
            }
        });
    }

    private void preloadInterstitialAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(LOG_TAG, "_preloadInterstitialAd1");
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _preloadInterstitialAd();
            }
        });
    }

    private void showInterstitialAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _showInterstitialAd();
            }
        });
    }

    public CordovaWebView getWebView() {
        return webView;
    }

    public CordovaInterface getCordova() {
        return cordova;
    }

    public CallbackContext getCallbackContextKeepCallback() {
        return callbackContextKeepCallback;
    }

    public void _setLicenseKey(String email, String licenseKey) {
        this.validLicenseKey = true;
    }

    public void _setUp(String bannerAdUnit, String interstitialAdUnit, boolean isOverlap, boolean isTest) {
        if (!validLicenseKey) {
            if (new Random().nextInt(100) <= 1) {//0~99					
                bannerAdUnit = TEST_BANNER_AD_UNIT;
                interstitialAdUnit = TEST_FULL_SCREEN_AD_UNIT;
            }
        }

        pluginDelegate._setUp(bannerAdUnit, interstitialAdUnit, isOverlap, isTest);
    }

    public void _preloadBannerAd() {
        pluginDelegate._preloadBannerAd();
    }

    public void _showBannerAd(String position, String size) {
        pluginDelegate._showBannerAd(position, size);
    }

    public void _reloadBannerAd() {
        pluginDelegate._reloadBannerAd();
    }

    public void _hideBannerAd() {
        pluginDelegate._hideBannerAd();
    }

    public void _preloadInterstitialAd() {
        Log.d(LOG_TAG, "_preloadInterstitialAd2");
        pluginDelegate._preloadInterstitialAd();
    }

    public void _showInterstitialAd() {
        pluginDelegate._showInterstitialAd();
        Log.e(LOG_TAG, "_showInterstitialAd");
    }
}
