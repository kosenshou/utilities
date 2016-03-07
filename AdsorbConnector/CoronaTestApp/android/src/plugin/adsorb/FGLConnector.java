package plugin.adsorb;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * NDK interface
 */
class adsorbnative {
	/**
	 * Forward Adsorb event to C++ code
	 * 
	 * @param nEventType event type
	 */
	public static native void onAdsorbEvent (int nEventType);
	
	/**
	 * Forward Adsorb event to C++ code, with string parameter
	 * 
	 * @param nEventType event type
	 * @param strParam additional string parameter
	 */
	public static native void onAdsorbEventWithString (int nEventType, String strParam);
}

/**
 * Java connector for Adsorb; relays intents back and forth with a simple java interface
 */

public class FGLConnector {
	// Tag for logging
	private static final String CLASS_TAG = "FGLConnector";
	
	// Version
	private static final String CONNECTOR_VERSION = "1.5.12";

	// Preferences file used by the adsorb SDK
	private static final String FGL_PREFERENCES_FILE = "FGL_adsorb_preferences";
	
	// Host activity
	private static Activity mActivity;
	
	// Event listener set by the host app
	private static FGLEventListener mListener;
	
	// Instance of Adsorb events receiver
	private static AdsorbEventIntentReceiver mReceiver;
	
	// true to forward events through the NDK interface as well
	private static boolean mSendNDKEvents;
	
	// true if the game is ready to receive events yet
	private static boolean mReady;
	
	// Success ad network info
	private static HashMap<String,String> mSuccessAdNetworkName = new HashMap<String,String>();
	private static HashMap<String,String> mSuccessAdNetworkType = new HashMap<String,String>();	
	private static HashMap<String,String> mSuccessAdNetworkTags = new HashMap<String,String>();	
	private static HashMap<String,Boolean> mSuccessAdProvidesGUI = new HashMap<String,Boolean>();
	
	// Helper ad network info
	private static HashMap<String,String> mHelperAdNetworkName = new HashMap<String,String>();
	private static HashMap<String,String> mHelperAdNetworkType = new HashMap<String,String>();
	private static HashMap<String,String> mHelperAdNetworkTags = new HashMap<String,String>();
	private static HashMap<String,Boolean> mHelperAdProvidesGUI = new HashMap<String,Boolean>();
	
	// Neutral ad network info
	private static HashMap<String,String> mNeutralAdNetworkName = new HashMap<String,String>();
	private static HashMap<String,String> mNeutralAdNetworkType = new HashMap<String,String>();	
	private static HashMap<String,String> mNeutralAdNetworkTags = new HashMap<String,String>();	
	private static HashMap<String,Boolean> mNeutralAdProvidesGUI = new HashMap<String,Boolean>();

	// true if we are auto-loading success rewarded ads
	private static boolean mAutoLoadSuccessRewardedAds;
	
	// true if we are auto-loading success rewarded ads
	private static boolean mAutoLoadHelperRewardedAds;
	
	// true if we are auto-loading success rewarded ads
	private static boolean mAutoLoadNeutralRewardedAds;
	
	// true if an ad overlay is currently ready
	private static boolean mAdOverlayReady;
	
	// Map of incentive states
	private static HashMap<String,Boolean> mIncentiveState = new HashMap<String,Boolean>();
	
	// Receiver implementation for Adsorb "return channel" events
	private static class AdsorbEventIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive (Context context, Intent intent) {
           if (intent != null && intent.getAction().equals ("com.fgl.ADSORB_EVENT")) {
              // Event received from Adsorb, extract type

              try {
                String eventType = intent.getStringExtra("type");

                if (eventType != null) {
                   if (eventType.equals ("onInterstitialAdsAvailable")) {
                        // Interstitial ads available
                        Log.d (CLASS_TAG, "received Adsorb event: interstitial ads available");
                        if (mListener != null)
                      	  mListener.onInterstitialAdsAvailable();
                        if (mSendNDKEvents)
                      	  adsorbnative.onAdsorbEvent (7);
                   }
                   else if (eventType.equals ("onInterstitialAdDismissed")) {
                      // Interstitial ad dismissed
                      Log.d (CLASS_TAG, "received Adsorb event: interstitial ad dismissed");
                      if (mListener != null)
                    	  mListener.onInterstitialAdDismissed();
                      if (mSendNDKEvents)
                    	  adsorbnative.onAdsorbEvent (1);
                   }
                   else if (eventType.equals ("onSuccessRewardedAdReady")) {
                	   String successAdFilterName, successAdNetworkName, successAdNetworkType, successAdNetworkTags;
                	   boolean successAdProvidesGUI;
                	   
                       // Success ad ready
                	   successAdFilterName = null;
                	   try {
                		   successAdFilterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (successAdFilterName == null) successAdFilterName = "default";
                	   successAdNetworkName = null;
                	   try {
	                	   successAdNetworkName = intent.getStringExtra("network_name");
                	   } catch (Exception e) {                		   
                	   }
                	   if (successAdNetworkName == null) successAdNetworkName = "";
                	   successAdNetworkType = null;
                	   try {
                		   successAdNetworkType = intent.getStringExtra("network_type");
                	   } catch (Exception e) {                		   
                	   }
                	   if (successAdNetworkType == null) successAdNetworkType = "";
                	   successAdNetworkTags = null;
                	   try {
                		   successAdNetworkTags = intent.getStringExtra("network_tags");
                	   } catch (Exception e) {                		   
                	   }
                	   if (successAdNetworkTags == null) successAdNetworkTags = "";
                	   successAdProvidesGUI = false;
                	   try {
	                	   successAdProvidesGUI = intent.getBooleanExtra("network_provides_gui", false);
                	   } catch (Exception e) {                		   
                	   }
                	   
                	   mSuccessAdNetworkName.put(successAdFilterName, successAdNetworkName);
                	   mSuccessAdNetworkType.put(successAdFilterName, successAdNetworkType);
                	   mSuccessAdNetworkTags.put(successAdFilterName, successAdNetworkTags);
                	   mSuccessAdProvidesGUI.put(successAdFilterName, Boolean.valueOf(successAdProvidesGUI));
                	   
                       Log.d (CLASS_TAG, "received Adsorb event: success ad ready for filter '" + successAdFilterName + "', network: " + successAdNetworkName + ", type: " + successAdNetworkType + ", tags: " + successAdNetworkTags + ", has GUI:" + successAdProvidesGUI);                      
                       if (mListener != null)
                     	  mListener.onSuccessRewardedAdReady(successAdFilterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (11, successAdFilterName);
                   }
                   else if (eventType.equals ("onSuccessRewardedAdUnavailable")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Success ad unavailable
                       Log.d (CLASS_TAG, "received Adsorb event: success ad unavailable for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onSuccessRewardedAdUnavailable(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (12, filterName);
                   }
                   else if (eventType.equals ("onSuccessRewardedAdDismissed")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Success ad dismissed
                       Log.d (CLASS_TAG, "received Adsorb event: success ad dismissed for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onSuccessRewardedAdDismissed(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (2, filterName);
                   }
                   else if (eventType.equals ("onSuccessRewardedAdFailed")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Success ad failed
                       Log.d (CLASS_TAG, "received Adsorb event: success ad failed for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onSuccessRewardedAdFailed(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (6, filterName);
                   }
                   else if (eventType.equals ("onSuccessRewardGranted")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Success ad reward granted
                       Log.d (CLASS_TAG, "received Adsorb event: success ad reward granted for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onSuccessRewardGranted(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (3, filterName);
                   }
                   else if (eventType.equals ("onHelperRewardedAdReady")) {
                	   String helperAdFilterName, helperAdNetworkName, helperAdNetworkType, helperAdNetworkTags;
                	   boolean helperAdProvidesGUI;
                	   
                       // Helper ad ready
                	   helperAdFilterName = null;
                	   try {
                		   helperAdFilterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (helperAdFilterName == null) helperAdFilterName = "default";
                	   helperAdNetworkName = null;
                	   try {
                		   helperAdNetworkName = intent.getStringExtra("network_name");
                	   } catch (Exception e) {                		   
                	   }
                	   if (helperAdNetworkName == null) helperAdNetworkName = "";
                	   helperAdNetworkType = null;
                	   try {
                		   helperAdNetworkType = intent.getStringExtra("network_type");
                	   } catch (Exception e) {                		   
                	   }
                	   if (helperAdNetworkType == null) helperAdNetworkType = "";
                	   helperAdNetworkTags = null;
                	   try {
                		   helperAdNetworkTags = intent.getStringExtra("network_tags");
                	   } catch (Exception e) {                		   
                	   }
                	   if (helperAdNetworkTags == null) helperAdNetworkTags = "";
                	   helperAdProvidesGUI = false;
                	   try {
                		   helperAdProvidesGUI = intent.getBooleanExtra("network_provides_gui", false);
                	   } catch (Exception e) {                		   
                	   }
                	   
                	   mHelperAdNetworkName.put(helperAdFilterName, helperAdNetworkName);
                	   mHelperAdNetworkType.put(helperAdFilterName, helperAdNetworkType);
                	   mHelperAdNetworkTags.put(helperAdFilterName, helperAdNetworkTags);
                	   mHelperAdProvidesGUI.put(helperAdFilterName, Boolean.valueOf(helperAdProvidesGUI));
                	   
                       Log.d (CLASS_TAG, "received Adsorb event: helper ad ready for filter '" + helperAdFilterName + "', network: " + helperAdNetworkName + ", type: " + helperAdNetworkType + ", tags: " + helperAdNetworkTags + ", has GUI:" + helperAdProvidesGUI);
                       if (mListener != null)
                     	  mListener.onHelperRewardedAdReady(helperAdFilterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (13, helperAdFilterName);
                   }
                   else if (eventType.equals ("onHelperRewardedAdUnavailable")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Helper ad unavailable
                       Log.d (CLASS_TAG, "received Adsorb event: helper ad unavailable for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onHelperRewardedAdUnavailable(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (14, filterName);
                   }
                   else if (eventType.equals ("onHelperRewardedAdDismissed")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Helper ad dismissed
                       Log.d (CLASS_TAG, "received Adsorb event: helper ad dismissed for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onHelperRewardedAdDismissed(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (9, filterName);
                   }
                   else if (eventType.equals ("onHelperRewardedAdFailed")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Helper ad failed
                       Log.d (CLASS_TAG, "received Adsorb event: helper ad failed for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onHelperRewardedAdFailed(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (10, filterName);
                   }
                   else if (eventType.equals ("onHelperRewardGranted")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Helper ad reward granted
                       Log.d (CLASS_TAG, "received Adsorb event: helper ad reward granted for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onHelperRewardGranted(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (4, filterName);
                   }
                   else if (eventType.equals ("onNeutralRewardedAdReady")) {
                	   String neutralAdFilterName, neutralAdNetworkName, neutralAdNetworkType, neutralAdNetworkTags;
                	   boolean neutralAdProvidesGUI;
                	   
                       // Neutral ad ready
                	   neutralAdFilterName = null;
                	   try {
                		   neutralAdFilterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (neutralAdFilterName == null) neutralAdFilterName = "default";
                	   neutralAdNetworkName = null;
                	   try {
                		   neutralAdNetworkName = intent.getStringExtra("network_name");
                	   } catch (Exception e) {                		   
                	   }
                	   if (neutralAdNetworkName == null) neutralAdNetworkName = "";
                	   neutralAdNetworkType = null;
                	   try {
                		   neutralAdNetworkType = intent.getStringExtra("network_type");
                	   } catch (Exception e) {                		   
                	   }
                	   if (neutralAdNetworkType == null) neutralAdNetworkType = "";
                	   neutralAdNetworkTags = null;
                	   try {
                		   neutralAdNetworkTags = intent.getStringExtra("network_tags");
                	   } catch (Exception e) {                		   
                	   }
                	   if (neutralAdNetworkTags == null) neutralAdNetworkTags = "";
                	   neutralAdProvidesGUI = false;
                	   try {
                		   neutralAdProvidesGUI = intent.getBooleanExtra("network_provides_gui", false);
                	   } catch (Exception e) {                		   
                	   }
                	   
                	   mNeutralAdNetworkName.put(neutralAdFilterName, neutralAdNetworkName);
                	   mNeutralAdNetworkType.put(neutralAdFilterName, neutralAdNetworkType);
                	   mNeutralAdNetworkTags.put(neutralAdFilterName, neutralAdNetworkTags);
                	   mNeutralAdProvidesGUI.put(neutralAdFilterName, Boolean.valueOf(neutralAdProvidesGUI));
                	   
                       Log.d (CLASS_TAG, "received Adsorb event: neutral ad ready for filter '" + neutralAdFilterName + "', network: " + neutralAdNetworkName + ", type: " + neutralAdNetworkType + ", tags: " + neutralAdNetworkTags + ", has GUI:" + neutralAdProvidesGUI);
                       if (mListener != null)
                     	  mListener.onNeutralRewardedAdReady(neutralAdFilterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (15, neutralAdFilterName);
                   }
                   else if (eventType.equals ("onNeutralRewardedAdUnavailable")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Neutral ad unavailable
                       Log.d (CLASS_TAG, "received Adsorb event: neutral ad unavailable for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onNeutralRewardedAdUnavailable(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (16, filterName);
                   }
                   else if (eventType.equals ("onNeutralRewardedAdDismissed")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Neutral ad dismissed
                       Log.d (CLASS_TAG, "received Adsorb event: neutral ad dismissed for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onNeutralRewardedAdDismissed(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (17, filterName);
                   }
                   else if (eventType.equals ("onNeutralRewardedAdFailed")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Neutral ad failed
                       Log.d (CLASS_TAG, "received Adsorb event: neutral ad failed for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onNeutralRewardedAdFailed(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (18, filterName);
                   }
                   else if (eventType.equals ("onNeutralRewardGranted")) {
                	   String filterName = null;
                	   try {
                		   filterName = intent.getStringExtra("filter");
                	   } catch (Exception e) {                		   
                	   }
                	   if (filterName == null) filterName = "default";
                	   
                       // Neutral ad reward granted
                       Log.d (CLASS_TAG, "received Adsorb event: neutral ad reward granted for filter '" + filterName + "'");
                       if (mListener != null)
                     	  mListener.onNeutralRewardGranted(filterName);
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEventWithString (19, filterName);
                   }
                   else if (eventType.equals ("onInterstitialAdFailed")) {
                       // Interstitial ad failed
                       Log.d (CLASS_TAG, "received Adsorb event: interstitial ad failed");
                       if (mListener != null)
                     	  mListener.onInterstitialAdFailed();
                       if (mSendNDKEvents)
                     	  adsorbnative.onAdsorbEvent (5);
                   }
                   else if (eventType.equals ("onShowMoreGamesDismissed")) {
                        // Show more games browser dismissed
                        Log.d (CLASS_TAG, "received Adsorb event: show more games browser dismissed");
                        if (mListener != null)
                      	  mListener.onShowMoreGamesDismissed();
                        if (mSendNDKEvents)
                      	  adsorbnative.onAdsorbEvent (8);
                   }
                   else if (eventType.equals ("onVirtualCurrencyGranted")) {
                	   int amount = 0;
                	   
                	   try {
                		   amount = intent.getIntExtra("amount", 0);
                	   } catch (Exception e) {                		   
                	   }
                	   
                       Log.d (CLASS_TAG, "received Adsorb event: virtual currency granted, amount: " + amount);
                	   if (amount > 0) {
	                       // Virtual currency granted
	                       if (mListener != null)
	                     	  mListener.onVirtualCurrencyGranted(amount);
	                       if (mSendNDKEvents)
	                     	  adsorbnative.onAdsorbEventWithString (20, Integer.toString(amount));
                	   }
                   }
                   else if (eventType.equals ("onIncentiveEnabled")) {
                	   try {
                		   String name = intent.getStringExtra("name");
                		   String network = intent.getStringExtra("network");
                		   
                		   if (name != null && network != null) {
                			   Log.d (CLASS_TAG, "received Adsorb event: incentive enabled, name: " + name + ", network: " + network);
                			   mIncentiveState.put(name, Boolean.valueOf(true));
                		   }
                	   } catch (Exception e) {
                	   }
                   }
                   else if (eventType.equals ("onIncentiveDisabled")) {
                	   try {
                		   String name = intent.getStringExtra("name");
                		   String network = intent.getStringExtra("network");
                		   
                		   if (name != null && network != null) {
                			   Log.d (CLASS_TAG, "received Adsorb event: incentive disabled, name: " + name + ", network: " + network);
                			   mIncentiveState.put(name, Boolean.valueOf(false));
                		   }
                	   } catch (Exception e) {
                	   }
                   }
                   else if (eventType.equals ("onMultiplayerGameStarted")) {
                	   try {
                		   String network = intent.getStringExtra("network");
                		   int randomSeed = intent.getIntExtra("random_seed", 0);

                		   if (network != null) {
                			   Log.d (CLASS_TAG, "received Adsorb event: multiplayer game started, network: " + network);
                			   if (mListener != null)
                				   mListener.onMultiplayerGameStarted(randomSeed);
                			   if (mSendNDKEvents)
                				   adsorbnative.onAdsorbEventWithString (21, Integer.toString(randomSeed));
                		   }
                	   } catch (Exception e) {
                	   }
                   }
                   else if (eventType.equals ("onMultiplayerGameEnded")) {
                	   try {
                		   String network = intent.getStringExtra("network");

                		   if (network != null) {
                			   Log.d (CLASS_TAG, "received Adsorb event: multiplayer game ended, network: " + network);
                			   if (mListener != null)
                				   mListener.onMultiplayerGameEnded();
                			   if (mSendNDKEvents)
                				   adsorbnative.onAdsorbEvent (22);
                		   }
                	   } catch (Exception e) {
                	   }
                   }
                   else if (eventType.equals ("onAdOverlayReady")) {
                	   try {
                		   Log.d (CLASS_TAG, "ad overlay ready");
                		   mAdOverlayReady = true;
                	   } catch (Exception e) {
                	   }
                   }
                   else if (eventType.equals ("onAdOverlayUnavailable")) {
                	   try {
                		   Log.d (CLASS_TAG, "ad overlay unavailable");
                		   mAdOverlayReady = false;
                	   } catch (Exception e) {
                	   }
                   }
                }
              }
              catch (Exception e) {
            	  Log.d (CLASS_TAG, "exception occured while processing Adsorb event: " + e.toString());
              }
           }
        }
	}
	
	/**
	 * Start sending events to the C++ code
	 * 
	 * @private
	 */
	static public void startNDKInterface () {
		mSendNDKEvents = true;
		
		if (mReceiver != null && !mReady) {
			mReady = true;
		
			if (!isSdkInjected ()) {
            	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onInterstitialAdsAvailable"));				
			}
		}
	}
	
	/**
	 * Initialize Adsorb
	 * 
	 * @param activity activity to initialize adsorb with
	 */
	static public void initialize (Activity activity) {
		destroy ();
		
		if (mReceiver == null) {
			// Register to receive Adsorb "return channel" intents
			Log.d (CLASS_TAG, "initialize connector version " + CONNECTOR_VERSION);
			mActivity = activity;
	        IntentFilter filter = new IntentFilter();
	        filter.addAction ("com.fgl.ADSORB_EVENT");
	        mReceiver = new AdsorbEventIntentReceiver ();
	        LocalBroadcastManager.getInstance(mActivity).registerReceiver (mReceiver, filter);		

	        // Receive state changes that may have occured before we registered to receive local broadcasts
			try {
				mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "refreshState"));
			} catch (Exception e) {					
			}
		}
		
		if (mSendNDKEvents && !mReady) {
			mReady = true;
		
			if (!isSdkInjected ()) {
            	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onInterstitialAdsAvailable"));				
			}
		}
	}
	
	/**
	 * Set event listener
	 * 
	 * @param listener new listener
	 */
	static public void setListener (FGLEventListener listener) {
		mListener = listener;
		
		if (!mReady && mListener != null && mReceiver != null) {
			mReady = true;
		
			if (!isSdkInjected ()) {
            	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onInterstitialAdsAvailable"));				
			}
		}
	}
	
	/**
	 * Clean up Adsorb 
	 * 
	 * @param context application context
	 */
	static public void destroy () {
		if (mReceiver != null && mActivity != null) {
			// Unregister
			Log.d (CLASS_TAG, "destroy");
			LocalBroadcastManager.getInstance(mActivity).unregisterReceiver (mReceiver);
			mReceiver = null;
			mActivity = null;
		}
	}
	
	/**
	 * Check if Adsorb SDK is injected into the host app
	 * 
	 * @param context application context
	 * 
	 * @return true if injected, false if not
	 */
	static public boolean isSdkInjected () {
		boolean value = false;
		
		if (mActivity != null) {
			try {
				ApplicationInfo app = mActivity.getPackageManager().getApplicationInfo (mActivity.getPackageName(), PackageManager.GET_ACTIVITIES|PackageManager.GET_META_DATA);			
	
				if (app.metaData != null)
					value = app.metaData.getBoolean("fgl.is_injected");
			} catch (NameNotFoundException e) {			
			}
		}
		
		return value;		
	}	
	
	/**
	 * Show interstitial ad
	 */
	static public void showInterstitialAd () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "showAd"));
				} catch (Exception e) {					
				}
				
				if (!isSdkInjected ()) {
					Log.d (CLASS_TAG, "SDK not injected, simulate interstitial ad");
					
					// Ads not injected yet - simulate an "ad"
					
					mActivity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
			                AlertDialog.Builder alertbox = new AlertDialog.Builder(mActivity);

			                alertbox.setTitle ("Adsorb - Interstitial");
			                alertbox.setMessage ("Well done! You have successfully integrated Adsorb and this is a dummy interstitial. A real ad will appear when the app is wrapped.");
			                alertbox.setNeutralButton ("Succeed", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onInterstitialAdDismissed"));
			                    }
			                });
			               
			                alertbox.setNegativeButton("Fail", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onInterstitialAdFailed"));
			                    }
			                });
			                
			                alertbox.setOnCancelListener (new DialogInterface.OnCancelListener() {
			                   public void onCancel (DialogInterface arg0) {
			                	   mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onInterstitialAdDismissed"));
			                   }
			                });
			               
			                AlertDialog alertDialog = alertbox.create ();
			                alertDialog.setCanceledOnTouchOutside (false);
			                alertDialog.show ();						
						}					
					});
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showInterstitialAd: " + e.toString ());
			e.printStackTrace();
		}
	}	

	/**
	 * Set custom extra variables for a network that provides success rewarded ads. The variables will be considered by the network the next
	 * time a success rewarded ad is loaded.
	 * 
	 * @param strNetwork network name (for instance "mediabrix")
	 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
	 */
	static public void setSuccessAdsVars (String strNetwork, String strJsonVars) {
		try {
			if (mActivity != null) {
				mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "setRewardedSuccessAdVars").
																	   putExtra ("network", strNetwork).
																	   putExtra ("vars", strJsonVars));
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setSuccessAdsAutoMode: " + e.toString ());
			e.printStackTrace();
		}
	}    
	
	/**
	 * Set custom extra variables for a network that provides helper rewarded ads. The variables will be considered by the network the next
	 * time a helper rewarded ad is loaded.
	 * 
	 * @param strNetwork network name (for instance "mediabrix")
	 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
	 */
	static public void setHelperAdsVars (String strNetwork, String strJsonVars) {
		try {
			if (mActivity != null) {
				mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "setRewardedHelperAdVars").
																	   putExtra ("network", strNetwork).
																	   putExtra ("vars", strJsonVars));
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setSuccessAdsAutoMode: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Set custom extra variables for a network that provides neutral rewarded ads. The variables will be considered by the network the next
	 * time a neutral rewarded ad is loaded.
	 * 
	 * @param strNetwork network name
	 * @param strJsonVars custom variables in json format
	 */
	static public void setNeutralAdsVars (String strNetwork, String strJsonVars) {
		try {
			if (mActivity != null) {
				mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "setRewardedNeutralAdVars").
																	   putExtra ("network", strNetwork).
																	   putExtra ("vars", strJsonVars));
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setSuccessAdsAutoMode: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Load success rewarded ad
	 * 
     * @param strAchievement achievement done by the player (such as "Level completed")
     * @param strReward reward to be granted (such as "50 gold")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
	static public void loadSuccessAd (String strAchievement, String strReward,
									  boolean useGameGUI) {
		try {
			if (mActivity != null) {
				mSuccessAdNetworkName.clear ();
				mSuccessAdNetworkType.clear ();
				mSuccessAdNetworkTags.clear ();
				mSuccessAdProvidesGUI.clear ();
				mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "loadRewardedSuccessAd").
																	   putExtra ("achievement", strAchievement).
																	   putExtra ("reward", strReward).
										 							   putExtra ("use_game_gui", useGameGUI));				
				if (!isSdkInjected ()) {
					// Ads not injected yet - simulate a loaded ad
					Log.d (CLASS_TAG, "SDK not injected, simulate success ad load");
					mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdReady").
																					   putExtra("network_name","test").
																					   putExtra("network_type","video").
																					   putExtra("network_provides_gui",true));
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in loadSuccessAd: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Show success rewarded ad
	 * 
	 * @param tag optional tag for tracking performance of individual ads, can be null
	 * @param filterName name of filter to show success ad for (null for default)
	 */
	static public void showSuccessAd (String tag, String filterName) {		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "showRewardedSuccessAd");
				if (tag != null) intent.putExtra ("tag", tag);
				if (filterName != null) intent.putExtra ("filter", filterName);
				mActivity.sendBroadcast (intent);
				
				if (!isSdkInjected ()) {
					// Ads not injected yet - simulate an "ad"
					Log.d (CLASS_TAG, "SDK not injected, simulate rewarded ad");
					
					mActivity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
			                AlertDialog.Builder alertbox = new AlertDialog.Builder(mActivity);

			                alertbox.setTitle ("Adsorb - Rewarded");
			                alertbox.setMessage ("Well done! You have successfully integrated Adsorb and this is a dummy rewarded ad. A real ad will appear when the app is wrapped. Grant the reward?");
			                alertbox.setPositiveButton("Reward", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardGranted"));
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadSuccessRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
				                	}
			                    }
			                });

			                alertbox.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadSuccessRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
				                	}
			                    }
			                });
			                
			                alertbox.setNegativeButton("Fail", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdFailed"));
			                    	
			                	    if (mAutoLoadSuccessRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
 			                	    }
			                    }
			                });
			                
			                alertbox.setOnCancelListener (new DialogInterface.OnCancelListener() {
			                   public void onCancel (DialogInterface arg0) {
			                	   mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdDismissed"));

			                	   if (mAutoLoadSuccessRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onSuccessRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
			                	   }
			                   }
			                });
			               
			                AlertDialog alertDialog = alertbox.create ();
			                alertDialog.setCanceledOnTouchOutside (false);
			                alertDialog.show ();						
						}					
					});
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showSuccessAd: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Load helper rewarded ad
	 * 
     * @param strCaption caption (such as "Need more time?")
     * @param strEnticementText enticement text (such as "Watch a short message and get an extra")
     * @param strReward reward (such as "1:00")
     * @param strButtonLabel button label (such as "Tap to get more time")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
     */
	static public void loadHelperAd (String strCaption, String strEnticementText, String strReward, String strButtonLabel,
									 boolean useGameGUI) {
		try {
			if (mActivity != null) {
				mHelperAdNetworkName.clear ();
				mHelperAdNetworkType.clear ();
				mHelperAdNetworkTags.clear ();
				mHelperAdProvidesGUI.clear ();
				mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "loadRewardedHelperAd").
																	   putExtra ("caption", strCaption).
																	   putExtra ("enticement", strEnticementText).
																	   putExtra ("reward", strReward).
																	   putExtra ("button", strButtonLabel).
										 							   putExtra ("use_game_gui", useGameGUI));
				
				if (!isSdkInjected ()) {
					// Ads not injected yet - simulate a loaded ad
					Log.d (CLASS_TAG, "SDK not injected, simulate helper ad load");
					mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdReady").
																					   putExtra("network_name","test").
																					   putExtra("network_type","video").
																					   putExtra("network_provides_gui",true));
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in loadHelperAd: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Show helper rewarded ad
	 * 
	 * @param tag optional tag for tracking performance of individual ads, can be null
	 * @param filterName name of filter to show success ad for (null for default)
     */
	static public void showHelperAd (String tag, String filterName) {
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "showRewardedHelperAd");
				if (tag != null) intent.putExtra ("tag", tag);
				if (filterName != null) intent.putExtra ("filter", filterName);
				mActivity.sendBroadcast (intent);
				
				if (!isSdkInjected ()) {
					// Ads not injected yet - simulate an "ad"
					Log.d (CLASS_TAG, "SDK not injected, simulate rewarded ad");
					
					mActivity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
			                AlertDialog.Builder alertbox = new AlertDialog.Builder(mActivity);

			                alertbox.setTitle ("Adsorb - Helper");
			                alertbox.setMessage ("Well done! You have successfully integrated Adsorb and this is a dummy helper ad. A real ad will appear when the app is wrapped. Grant the reward?");
			                alertbox.setPositiveButton ("Reward", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardGranted"));
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadHelperRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
				                	}
			                    }
			                });

			                alertbox.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadHelperRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
				                	}
			                    }
			                });
			                
			                alertbox.setNegativeButton("Fail", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdFailed"));
			                    	
				                	if (mAutoLoadHelperRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
				                	}
			                    }
			                });
			                
			                alertbox.setOnCancelListener (new DialogInterface.OnCancelListener() {
			                   public void onCancel (DialogInterface arg0) {
			                	   mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadHelperRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onHelperRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",true));
				                	}
			                   }
			                });
			               
			                AlertDialog alertDialog = alertbox.create ();
			                alertDialog.setCanceledOnTouchOutside (false);
			                alertDialog.show ();						
						}					
					});
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showHelperAd: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Load neutral rewarded ad
     *
     * @param strCaption neutral offer's caption (such as "Unlock untimed mode")
     * @param strBody neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
     */
	static public void loadNeutralAd (String strCaption, String strBody, boolean useGameGUI) {
		try {
			if (mActivity != null) {
				mNeutralAdNetworkName.clear();
				mNeutralAdNetworkType.clear();
				mNeutralAdNetworkTags.clear();
				mNeutralAdProvidesGUI.clear();
				mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "loadRewardedNeutralAd").
																	   putExtra ("caption", strCaption).
																	   putExtra ("body", strBody).
						   											   putExtra ("use_game_gui", useGameGUI));
				
				if (!isSdkInjected ()) {
					// Ads not injected yet - simulate a loaded ad
					Log.d (CLASS_TAG, "SDK not injected, simulate neutral ad load");
					mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdReady").
																					   putExtra("network_name","test").
																					   putExtra("network_type","video").
																					   putExtra("network_provides_gui",false));
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in loadNeutralAd: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Show neutral rewarded ad
	 * 
	 * @param tag optional tag for tracking performance of individual ads, can be null
	 * @param filterName name of filter to show success ad for (null for default)
     */
	static public void showNeutralAd (String tag, String filterName) {
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "showRewardedNeutralAd");
				if (tag != null) intent.putExtra ("tag", tag);
				if (filterName != null) intent.putExtra ("filter", filterName);
				mActivity.sendBroadcast (intent);
				
				if (!isSdkInjected ()) {
					// Ads not injected yet - simulate an "ad"
					Log.d (CLASS_TAG, "SDK not injected, simulate rewarded ad");
					
					mActivity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
			                AlertDialog.Builder alertbox = new AlertDialog.Builder(mActivity);

			                alertbox.setTitle ("Adsorb - Neutral");
			                alertbox.setMessage ("Well done! You have successfully integrated Adsorb and this is a dummy neutral ad. A real ad will appear when the app is wrapped. Grant the reward?");
			                alertbox.setPositiveButton ("Reward", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardGranted"));
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadNeutralRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",false));
				                	}
			                    }
			                });

			                alertbox.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadNeutralRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",false));
				                	}
			                    }
			                });
			                
			                alertbox.setNegativeButton("Fail", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdFailed"));
			                    	
				                	if (mAutoLoadNeutralRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",false));
				                	}
			                    }
			                });
			                
			                alertbox.setOnCancelListener (new DialogInterface.OnCancelListener() {
			                   public void onCancel (DialogInterface arg0) {
			                	   mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdDismissed"));
			                    	
				                	if (mAutoLoadNeutralRewardedAds) {
										mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onNeutralRewardedAdReady").
																										   putExtra("network_name","test").
																										   putExtra("network_type","video").
																										   putExtra("network_provides_gui",false));
				                	}
			                   }
			                });
			               
			                AlertDialog alertDialog = alertbox.create ();
			                alertDialog.setCanceledOnTouchOutside (false);
			                alertDialog.show ();						
						}					
					});
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showNeutralAd: " + e.toString ());
			e.printStackTrace();
		}
	}

	/**
	 * Filter success rewarded ads using keywords
	 *
	 * @param filterName filter name (null for default)
	 * @param filterKeywords filter keywords to apply to success rewarded ads (such as 'video high_value'), or null to remove the filter
	 */
	static public void filterSuccessAds (String filterName, String filterKeywords) {
		if (filterName == null) filterName = "default";
		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "filterSuccessAdByType");
				if (filterName != null) intent.putExtra ("name", filterName);
				if (filterKeywords != null) intent.putExtra ("type", filterKeywords);
				mActivity.sendBroadcast (intent);				
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in filterSuccessAdByType: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Filter helper rewarded ads using keywords
	 * 
	 * @param filterName filter name (null for default)
	 * @param filterKeywords filter keywords to apply to helper rewarded ads (such as 'video high_value'), or null to remove the filter
	 */
	static public void filterHelperAds (String filterName, String filterKeywords) {		
		if (filterName == null) filterName = "default";
		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "filterHelperAdByType");				
				if (filterName != null) intent.putExtra ("name", filterName);
				if (filterKeywords != null) intent.putExtra ("type", filterKeywords);
				mActivity.sendBroadcast (intent);
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in filterHelperAdByType: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Filter neutral rewarded ads using keywords
	 * 
	 * @param filterName filter name (null for default)
	 * @param filterKeywords filter keywords to apply to neutral rewarded ads (such as 'video high_value'), or null to remove the filter
	 */
	static public void filterNeutralAds (String filterName, String filterKeywords) {		
		if (filterName == null) filterName = "default";
		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "filterNeutralAdByType");				
				if (filterName != null) intent.putExtra ("name", filterName);
				if (filterKeywords != null) intent.putExtra ("type", filterKeywords);
				mActivity.sendBroadcast (intent);
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in filterNeutralAdByType: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the name of the network providing the currently loaded success rewarded ad
	 * 
	 * @param filterName name of filter to get ad network name of (null for default)
	 * 
	 * @return name
	 */
	static public String getSuccessAdNetworkName(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mSuccessAdNetworkName.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Get the type of the currently loaded success rewarded ad (video, survey, ...)
	 * 
	 * @param filterName name of filter to get ad network type of (null for default)
	 * 
	 * @return type
	 */
	static public String getSuccessAdNetworkType(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mSuccessAdNetworkType.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Get the filter tags of the currently loaded success rewarded ad (long, high_value, ...)
	 * 
	 * @param filterName name of filter to get ad network tags of (null for default)
	 * 
	 * @return tags
	 */
	static public String getSuccessAdNetworkTags(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mSuccessAdNetworkTags.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Check if the currently loaded success rewarded ad provides its own GUI
	 * 
	 * @param filterName name of filter to get GUI mode of (null for default)
	 * 
	 * @return true if it does, false if the game has to provide it
	 */
	static public boolean doesSuccessAdProvideGUI(String filterName) {
		if (filterName == null) filterName = "default";
		Boolean boolData = mSuccessAdProvidesGUI.get(filterName);
		return (boolData != null) ? boolData.booleanValue() : false;
	}
	
	/**
	 * Get the name of the network providing the currently loaded helper rewarded ad
	 * 
	 * @param filterName name of filter to get ad network name of (null for default)
	 * 
	 * @return name
	 */
	static public String getHelperAdNetworkName(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mHelperAdNetworkName.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Get the type of the currently loaded helper rewarded ad (video, survey, ...)
	 * 
	 * @return type
	 */
	static public String getHelperAdNetworkType(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mHelperAdNetworkType.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Get the filter tags of the currently loaded helper rewarded ad (long, high_value, ...)
	 * 
	 * @param filterName name of filter to get ad network tags of (null for default)
	 * 
	 * @return tags
	 */
	static public String getHelperAdNetworkTags(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mHelperAdNetworkTags.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Check if the currently loaded helper rewarded ad provides its own GUI
	 * 
	 * @param filterName name of filter to get GUI mode of (null for default)
	 * 
	 * @return true if it does, false if the game has to provide it
	 */
	static public boolean doesHelperAdProvideGUI(String filterName) {
		if (filterName == null) filterName = "default";
		Boolean boolData = mHelperAdProvidesGUI.get(filterName);
		return (boolData != null) ? boolData.booleanValue() : false;
	}
	
	/**
	 * Get the name of the network providing the currently loaded neutral rewarded ad
	 * 
	 * @param filterName name of filter to get ad network name of (null for default)
	 * 
	 * @return name
	 */
	static public String getNeutralAdNetworkName(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mNeutralAdNetworkName.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Get the type of the currently loaded neutral rewarded ad (video, survey, ...)
	 * 
	 * @return type
	 */
	static public String getNeutralAdNetworkType(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mNeutralAdNetworkType.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Get the filter tags of the currently loaded neutral rewarded ad (long, high_value, ...)
	 * 
	 * @param filterName name of filter to get ad network tags of (null for default)
	 * 
	 * @return tags
	 */
	static public String getNeutralAdNetworkTags(String filterName) {
		if (filterName == null) filterName = "default";
		String strData = mNeutralAdNetworkTags.get(filterName);
		if (strData == null) strData = "";
		return strData;
	}
	
	/**
	 * Check if the currently loaded neutral rewarded ad provides its own GUI
	 * 
	 * @param filterName name of filter to get GUI mode of (null for default)
	 * 
	 * @return true if it does, false if the game has to provide it
	 */
	static public boolean doesNeutralAdProvideGUI(String filterName) {
		if (filterName == null) filterName = "default";
		Boolean boolData = mNeutralAdProvidesGUI.get(filterName);
		return (boolData != null) ? boolData.booleanValue() : false;
	}
	
	/**
	 * Report a completed achievement
	 * 
	 * @param achievementName name of completed achievement
	 */
	static public void reportAchievement(String achievementName) {		
		if (achievementName == null) achievementName = "";
		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "reportAchievement");
				intent.putExtra ("name", achievementName);
				mActivity.sendBroadcast (intent);				
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in reportAchievement: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Prompt user to perform an action to get an incentive
	 * 
	 * @param incentiveName incentive identifier
	 * @param incentiveText what the user will get if the action is performed
	 */
	static public void promptIncentive (String incentiveName, String incentiveText) {
		if (incentiveName == null) incentiveName = "";
		if (incentiveText == null) incentiveText = "";
		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "promptIncentive");
				intent.putExtra ("name", incentiveName);
				intent.putExtra ("text", incentiveText);
				mActivity.sendBroadcast (intent);				
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in promptIncentive: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if an incentive's reward should currently be granted
	 * 
	 * @param incentiveName incentive identifier
	 * 
	 * @return true if the incentive's reward should currently be granted, false if not
	 */
	static public boolean isIncentiveRewardGranted(String incentiveName) {
		boolean bEnabled = false;

		try {
			if (incentiveName != null && mIncentiveState.containsKey(incentiveName)) {			
				Boolean boolData = mIncentiveState.get(incentiveName);
				
				if (boolData != null)
					bEnabled = boolData.booleanValue();
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in isIncentiveRewardGranted: " + e.toString ());
			e.printStackTrace();
		}
		
		return bEnabled;
	}
	
	/**
	 * Report a completed in-app purchase
	 * 
	 * @param productName name of purchased product
	 * @param productPrice product price (such as 1.99)
	 */
	static public void reportPurchase(String productName, double productPrice) {		
		if (productName == null) productName = "default";
		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "reportPurchase");
				intent.putExtra ("product", productName);
				intent.putExtra ("price", productPrice);
				mActivity.sendBroadcast (intent);				
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in reportPurchase: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Report user attribution for this session
	 * 
	 * @param vendor name of the vendor supplying the information
	 * @param userType where the user came from
	 */
	static public void reportAttribution(String vendor, String userType) {		
		if (vendor == null) vendor = "";
		if (userType == null) userType = "";
		
		try {
			if (mActivity != null) {
				Intent intent = new Intent ("com.fgl.INVOKE").putExtra ("command", "reportAttribution");
				intent.putExtra ("key", vendor);
				intent.putExtra ("value", userType);
				mActivity.sendBroadcast (intent);				
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in reportAttribution: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Show "more games" browser
	 */
	static public void showMoreGames () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "moreGames"));
				} catch (Exception e) {					
				}
				
				if (!isSdkInjected ()) {
					Log.d (CLASS_TAG, "SDK not injected, simulate more games browser");
					
					// Ads not injected yet - simulate "more games"
					
					mActivity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
			                AlertDialog.Builder alertbox = new AlertDialog.Builder(mActivity);

			                alertbox.setTitle ("Adsorb - More Games");
			                alertbox.setMessage ("Well done! You have successfully integrated Adsorb and this is a dummy More Games browser. The real one will appear when the app is wrapped.");
			                alertbox.setNeutralButton ("Ok", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                	   mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onShowMoreGamesDismissed"));
			                    }
			                });
			               
			                alertbox.setOnCancelListener (new DialogInterface.OnCancelListener() {
			                   public void onCancel (DialogInterface arg0) {
			                	   mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra ("type", "onShowMoreGamesDismissed"));
			                   }
			                });
			               
			                AlertDialog alertDialog = alertbox.create ();
			                alertDialog.setCanceledOnTouchOutside (false);
			                alertDialog.show ();						
						}					
					});
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showMoreGames: " + e.toString ());
			e.printStackTrace();
		}
	}	
	
	/**
	 * Show newsletter subscription
	 */
	static public void showNewsletter () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "newsletter"));
				} catch (Exception e) {					
				}
				
				if (!isSdkInjected ()) {
					Log.d (CLASS_TAG, "SDK not injected, simulate newsletter");
					
					// Ads not injected yet - simulate "more games"
					
					mActivity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
			                AlertDialog.Builder alertbox = new AlertDialog.Builder(mActivity);

			                alertbox.setTitle ("Adsorb - Newsletter");
			                alertbox.setMessage ("Well done! You have successfully integrated Adsorb and this is a dummy newsletter. The real one will appear when the app is wrapped.");
			                alertbox.setNeutralButton ("Ok", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    }
			                });
			               
			                alertbox.setOnCancelListener (new DialogInterface.OnCancelListener() {
			                   public void onCancel (DialogInterface arg0) {
			                   }
			                });
			               
			                AlertDialog alertDialog = alertbox.create ();
			                alertDialog.setCanceledOnTouchOutside (false);
			                alertDialog.show ();						
						}					
					});
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showNewsletter: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Show actually free games page
	 */
	static public void showActuallyFreeGames () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "actuallyFree"));
				} catch (Exception e) {					
				}
				
				if (!isSdkInjected ()) {
					Log.d (CLASS_TAG, "SDK not injected, simulate actually free games page");
					
					// Ads not injected yet - simulate "more games"
					
					mActivity.runOnUiThread(new Runnable () {
						@Override
						public void run() {
			                AlertDialog.Builder alertbox = new AlertDialog.Builder(mActivity);

			                alertbox.setTitle ("Adsorb - Actually free games");
			                alertbox.setMessage ("Well done! You have successfully integrated Adsorb and this is a dummy actually free games page. The real one will appear when the app is wrapped.");
			                alertbox.setNeutralButton ("Ok", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    }
			                });
			               
			                alertbox.setOnCancelListener (new DialogInterface.OnCancelListener() {
			                   public void onCancel (DialogInterface arg0) {
			                   }
			                });
			               
			                AlertDialog alertDialog = alertbox.create ();
			                alertDialog.setCanceledOnTouchOutside (false);
			                alertDialog.show ();						
						}					
					});
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showActuallyFreeGames: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Suspend loading and showing any ads for the specified number of hours. This can
	 * be used as the reward of an incented ad for instance.
	 * 
	 * @param hours number of hours to suspend all ads for (for instance 24),
	 *              -1 to suspend permanently, 
	 *              0 to resume previously suspended ads
	 */
    public static void suspendAdsForHours (int hours) {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "suspendAdsForHours").
							   				 							   putExtra ("hours", hours));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in suspendAdsForHours: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
	 * after a day
	 * 
	 * @param message message to display in the notification when it fires
	 */
    public static void enableLocalNotification (String message) {
		try {
			Log.d (CLASS_TAG, "enableLocalNotification");
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "enableLocalNotification").
							   				 							   putExtra ("message", message));
				} catch (Exception e) {
				}
			}
			else {
				Log.d (CLASS_TAG, "not armed - activity is null");				
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in enableLocalNotification: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
	 * after the specified delay
	 * 
	 * @param title title to display in the notification when it fires
	 * @param message message to display in the notification when it fires
	 * @param delaySeconds delay in seconds after which to fire the local notification (for instance, 86400 for one day)
	 */
    public static void enableLocalNotificationWithDelay (String title, String message, int delaySeconds) {
		try {
			Log.d (CLASS_TAG, "enableLocalNotification");
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "enableLocalNotification").
																		   putExtra ("title", title).
							   				 							   putExtra ("message", message).
							   				 							   putExtra ("delay", delaySeconds));
				} catch (Exception e) {
				}
			}
			else {
				Log.d (CLASS_TAG, "not armed - activity is null");				
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in enableLocalNotification: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Disable showing a local notification
	 */
    public static void disableLocalNotification () {
		try {
			Log.d (CLASS_TAG, "disableLocalNotification");
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "disableLocalNotification"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in disableLocalNotification: " + e.toString ());
			e.printStackTrace();
		}
    }
    
    /**
     * Check if the device supports push notifications
     * 
     * @return true if push notifications are available, false if not
     */
    public static boolean arePushNotificationsAvailable() {
    	String strMarket = null;
    
    	if (mActivity != null) {
			try {
				ApplicationInfo app = mActivity.getPackageManager().getApplicationInfo (mActivity.getPackageName(), PackageManager.GET_ACTIVITIES|PackageManager.GET_META_DATA);			
	
				if (app.metaData != null)
					strMarket = app.metaData.getString("fgl.market");
			} catch (NameNotFoundException e) {			
			}
			
			return strMarket == null || strMarket.equalsIgnoreCase("googleplay");
    	}
    	else {
    		return false;
    	}
    }
    
	/**
	 * Set channel to use for push notifications for this game. This must be called before enablePushNotifications() or disablePushNotifications()
	 * 
	 * @param channel channel to use for push notifications, such as HiddenObject
	 */
    public static void setPushNotificationsChannel (String channel) {
		try {
			Log.d (CLASS_TAG, "setPushNotificationsChannel");
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "setPushNotificationsChannel").
							   				 							   putExtra ("channel", channel));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setPushNotificationsChannel: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Opt into receiving push notifications for cross-marketing purposes
	 */
    public static void enablePushNotifications () {
		try {
			Log.d (CLASS_TAG, "enablePushNotifications");
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "enablePushNotifications"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in enablePushNotifications: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Opt out of receiving push notifications for cross-marketing purposes
	 */
    public static void disablePushNotifications () {
		try {
			Log.d (CLASS_TAG, "disablePushNotifications");
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "disablePushNotifications"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in disablePushNotifications: " + e.toString ());
			e.printStackTrace();
		}
    }

    /**
     * Check if the user is currently opted into push notifications
     * 
     * @return true if push notifications are enabled, false if they are disabled
     */
    public static boolean arePushNotificationsEnabled () {
    	boolean bOptedIn = false;
    	
    	if (mActivity != null) {
			final SharedPreferences prefs = mActivity.getSharedPreferences (FGL_PREFERENCES_FILE, 0);
			if (prefs.getBoolean("adsorb_optin_pushnotif", false) == true) {
				bOptedIn = true;
			}
    	}
    	
    	return bOptedIn;
    }
    
	/**
	 * Tell Adsorb that the game is entering the specified screen
	 * 
	 * @param name screen name, such as: main_menu, instructions, level_select, playing, level_end, game_end
	 * @param allowOverlays true to allow Adsorb to show monetization and marketing overlays on top of some of the game screens,
	 *                      false to inform the current screen name only for event logging purposes
	 */
    public static void enterGameScreen (String name, boolean allowOverlays) {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "enterGameScreen").
							   				 							   putExtra ("name", name).
							   				 							   putExtra ("allow_overlays", allowOverlays));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in enterGameScreen: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Tell Adsorb that the game is leaving the current screen
	 */
    public static void leaveGameScreen () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "leaveGameScreen"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in leaveGameScreen: " + e.toString ());
			e.printStackTrace();
		}
    }    
    
	/**
	 * Report game event
	 * 
	 * @param eventName event name
	 */
    public static void reportGameEvent (String eventName) {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "reportGameEvent").
																		   putExtra ("name", eventName));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in reportGameEvent: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Show the social feed and multiplayer challenges wall for this network
	 */
	public static void showMultiplayerWall () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "showMultiplayerWall"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showMultiplayerWall: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Report that the local player's current score has changed
	 * 
	 * @param score new score
	 */
	public static void updateMultiplayerScore (int score) {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "updateMultiplayerScore").
 							   											   putExtra ("score", score));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in updateMultiplayerScore: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Report that the multiplayer game is completed for the local player
	 * 
	 * @param score final score
	 */
	public static void completeMultiplayerGame (int score) {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "completeMultiplayerGame").
 							   											   putExtra ("score", score));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in completeMultiplayerGame: " + e.toString ());
			e.printStackTrace();
		}
	}
	
	/**
	 * Report that the multiplayer game has been canceled
	 */
	public static void cancelMultiplayerGame () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "cancelMultiplayerGame"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in cancelMultiplayerGame: " + e.toString ());
			e.printStackTrace();
		}		
	}    
    
	/**
	 * Set success rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
	 * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
	 * rewarded ads that the game is interested in.
	 * 
     * @param strSuccessAchievement achievement done by the player for success ads (such as "Level completed")
     * @param strSuccessReward reward to be granted for success ads (such as "50 gold")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
    public static void setSuccessAdsAutoMode (String strSuccessAchievement, String strSuccessReward,
    										  boolean useGameGUI) {
		try {
			if (mActivity != null) {
				try {
					mAutoLoadSuccessRewardedAds = true;
					
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "setSuccessAdsAutoMode").
											 							   putExtra ("success_achievement", strSuccessAchievement).
											 							   putExtra ("success_reward", strSuccessReward).
											 							   putExtra ("use_game_gui", useGameGUI));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setSuccessAdsAutoMode: " + e.toString ());
			e.printStackTrace();
		}
    }    
    
	/**
	 * Set helper rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
	 * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
	 * rewarded ads that the game is interested in.
	 * 
     * @param strHelperCaption caption for helper ads (such as "Need more time?")
     * @param strHelperEnticementText enticement text for helper ads (such as "Watch a short message and get an extra")
     * @param strHelperReward reward for helper ads (such as "1:00")
     * @param strHelperButtonLabel button label for helper ads (such as "Tap to get more time")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
    public static void setHelperAdsAutoMode (String strHelperCaption, String strHelperEnticementText,
    										 String strHelperReward, String strHelperButtonLabel,
    										 boolean useGameGUI) {
		try {
			if (mActivity != null) {
				try {
					mAutoLoadHelperRewardedAds = true;
					
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "setHelperAdsAutoMode").
							   				 							   putExtra ("helper_caption", strHelperCaption).
							   				 							   putExtra ("helper_enticement", strHelperEnticementText).
							   				 							   putExtra ("helper_reward", strHelperReward).
							   				 							   putExtra ("helper_button", strHelperButtonLabel).
											 							   putExtra ("use_game_gui", useGameGUI));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setHelperAdsAutoMode: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Set neutral rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
	 * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
	 * rewarded ads that the game is interested in.
	 * 
     * @param strNeutralCaption neutral offer's caption (such as "Unlock untimed mode")
     * @param strNeutralBody neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
    public static void setNeutralAdsAutoMode (String strNeutralCaption, String strNeutralBody, boolean useGameGUI) {
		try {
			if (mActivity != null) {
				try {
					mAutoLoadNeutralRewardedAds = true;
					
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "setNeutralAdsAutoMode").
																		   putExtra ("neutral_caption", strNeutralCaption).
																		   putExtra ("neutral_body", strNeutralBody).
							   											   putExtra ("use_game_gui", useGameGUI));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setNeutralAdsAutoMode: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Complete the setup of automatically load rewarded ads and start loading in the background
	 */
    public static void rewardedAdSetupComplete () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "rewardedAdSetupComplete"));
					
					if (!isSdkInjected ()) {
						// SDK not injected - simulate ready events
						
						Log.d (CLASS_TAG, "SDK not injected, simulate loaded ads");
						
						if (mAutoLoadSuccessRewardedAds) {
							mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra("type", "onSuccessRewardedAdReady").
																							   putExtra("network_name","test").
																							   putExtra("network_type","video").
																							   putExtra("network_provides_gui",true));	
						}
						
						if (mAutoLoadHelperRewardedAds) {
							mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra("type", "onHelperRewardedAdReady").
																							   putExtra("network_name","test").
																							   putExtra("network_type","video").
																							   putExtra("network_provides_gui",true));
						}
						
						if (mAutoLoadNeutralRewardedAds) {
							mReceiver.onReceive(mActivity, new Intent ("com.fgl.ADSORB_EVENT").putExtra("type", "onNeutralRewardedAdReady").
																							   putExtra("network_name","test").
																							   putExtra("network_type","video").
																							   putExtra("network_provides_gui",false));
						}
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in setRewardedAdsAutoMode: " + e.toString ());
			e.printStackTrace();
		}
    }    
    
    /**
     * Check if an ad overlay is currently ready to show
     * 
     * @return true if an ad overlay is ready, false if not
     */
    public static boolean isAdOverlayReady () {
    	return mAdOverlayReady;
    }
    
	/**
	 * Show cross-promo ad overlay, if possible
	 * 
	 * @param x1 X coordinate of top, left of view, as a fraction of the display width (0..1)
	 * @param y1 Y coordinate of top, left of view, as a fraction of the display height (0..1)
	 * @param x2 X coordinate of bottom, right of view + 1, as a fraction of the display width (0..1)
	 * @param y2 Y coordinate of bottom, right of view + 1, as a fraction of the display height (0..1)
	 * @param nAnimDuration duration of the transition anims, in milliseconds
	 * @param bShowRectangle true to show the area requested by the game (paint it red), for debugging purposes
	 */
    public static void showAdOverlay (float x1, float y1, float x2, float y2, int nAnimDuration, boolean bShowRectangle) {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "showAdOverlay").
																		   putExtra ("x1", x1).
																		   putExtra ("y1", y1).
							   											   putExtra ("x2", x2).
							   											   putExtra ("y2", y2).
							   											   putExtra ("duration", nAnimDuration).
							   											   putExtra ("debug", bShowRectangle));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in showAdOverlay: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Refresh cross-promo ad overlay, if another ad is ready
	 */
    public static void advanceAdOverlay () {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "advanceAdOverlay"));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in advanceAdOverlay: " + e.toString ());
			e.printStackTrace();
		}
    }
    
	/**
	 * Hide cross-promo ad overlay, if one was showing
	 *
	 * @param nAnimDuration duration of the transition anims, in milliseconds
	 */
    public static void hideAdOverlay (int nAnimDuration) {
		try {
			if (mActivity != null) {
				try {
					mActivity.sendBroadcast (new Intent ("com.fgl.INVOKE").putExtra ("command", "hideAdOverlay").
																		   putExtra ("duration", nAnimDuration));
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Log.e (CLASS_TAG, "exception in hideAdOverlay: " + e.toString ());
			e.printStackTrace();
		}
    }
}
