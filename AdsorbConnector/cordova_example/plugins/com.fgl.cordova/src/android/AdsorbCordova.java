package com.fgl.cordova;

import java.util.Arrays;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.fgl.connector.FGLConnector;
import com.fgl.connector.FGLEventListener;

// Cordova plugin class

public class AdsorbCordova extends CordovaPlugin implements FGLEventListener  {
   // Tag for logging
   public static final String TAG = "FGLAdsorbCordova";
   
   // Cordova connector version
   private static final String CORDOVA_CONNECTOR_VERSION = "1.5.8";
   
   // Callback context for sending Adsorb events to the Javascript code
   private CallbackContext m_eventCallbackContext;
   
   // Initialize connector
   
   @Override
   public void initialize (CordovaInterface cordova, CordovaWebView webView) {
      super.initialize (cordova, webView);
      Log.d (TAG, "initialize Cordova connector version " + CORDOVA_CONNECTOR_VERSION);
      FGLConnector.initialize (cordova.getActivity ());
      FGLConnector.setListener (this);
   }
   
   // Execute Adsorb command
   
   @Override
   public boolean execute (String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      if (action.equals ("setEventCallback")) {
         m_eventCallbackContext = callbackContext;
         
         // Set callback context used to send events to the Javascript code
         Log.d (TAG, "set event callback");
         PluginResult pluginResult = new PluginResult (PluginResult.Status.NO_RESULT);
         pluginResult.setKeepCallback (true);
         callbackContext.sendPluginResult (pluginResult);
         return true;
      }
      else if (action.equals ("isSdkInjected")) {
         try {
            // Check if the game is injected with Adsorb
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.isSdkInjected()));
         } catch (Exception e) {
            Log.e (TAG, "exception in isSdkInjected: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }

         return true;
      }
      else if (action.equals ("setSuccessAdsVars")) {
         try {
            // Set per-network custom variables for success ads
            FGLConnector.setSuccessAdsVars(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in setSuccessAdsVars: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("setHelperAdsVars")) {
         try {
            // Set per-network custom variables for helper ads
            FGLConnector.setHelperAdsVars(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in setHelperAdsVars: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("setNeutralAdsVars")) {
         try {
            // Set per-network custom variables for neutral ads
            FGLConnector.setNeutralAdsVars(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in setNeutralAdsVars: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showInterstitialAd")) {
         try {
            // Show interstitial ad
            FGLConnector.showInterstitialAd();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showInterstitialAd: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("loadSuccessAd")) {
         try {
            // Load success rewarded ad
            FGLConnector.loadSuccessAd(args.getString (0), args.getString (1), args.getBoolean (2));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in loadSuccessAd: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showSuccessAd")) {
         try {
            // Show success rewarded ad
            FGLConnector.showSuccessAd(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showSuccessAd: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("loadHelperAd")) {
         try {
            // Load helper rewarded ad
            FGLConnector.loadHelperAd(args.getString (0), args.getString (1), args.getString (2), args.getString (3), args.getBoolean (4));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in loadHelperAd: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showHelperAd")) {
         try {
            // Show helper rewarded ad
            FGLConnector.showHelperAd(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showHelperAd: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("loadNeutralAd")) {
         try {
            // Load neutral rewarded ad
            FGLConnector.loadNeutralAd(args.getString (0), args.getString (1), args.getBoolean (2));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in loadNeutralAd: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showNeutralAd")) {
         try {
            // Show neutral rewarded ad
            FGLConnector.showNeutralAd(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showNeutralAd: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("filterSuccessAds")) {
         try {
            // Filter success rewarded ads
            FGLConnector.filterSuccessAds(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in filterSuccessAds: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("filterHelperAds")) {
         try {
            // Filter helper rewarded ads
            FGLConnector.filterHelperAds(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in filterHelperAds: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("filterNeutralAds")) {
         try {
            // Filter neutral rewarded ads
            FGLConnector.filterNeutralAds(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in filterNeutralAds: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("getSuccessAdNetworkName")) {
         try {
            // Get the name of the network that provides the currently ready success ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getSuccessAdNetworkName(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getSuccessAdNetworkName: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("getSuccessAdNetworkType")) {
         try {
            // Get the type of the network that provides the currently ready success ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getSuccessAdNetworkType(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getSuccessAdNetworkType: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("getSuccessAdNetworkTags")) {
         try {
            // Get the tags of the network that provides the currently ready success ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getSuccessAdNetworkTags(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getSuccessAdNetworkTags: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("doesSuccessAdProvideGUI")) {
         try {
            // Check if the network that provides the currently ready success ad, provides its own GUI or not
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.doesSuccessAdProvideGUI(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in doesSuccessAdProvideGUI: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }

         return true;
      }
      else if (action.equals ("getHelperAdNetworkName")) {
         try {
            // Get the name of the network that provides the currently ready helper ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getHelperAdNetworkName(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getHelperAdNetworkName: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("getHelperAdNetworkType")) {
         try {
            // Get the type of the network that provides the currently ready helper ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getHelperAdNetworkType(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getHelperAdNetworkType: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("getHelperAdNetworkTags")) {
         try {
            // Get the tags of the network that provides the currently ready helper ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getHelperAdNetworkTags(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getHelperAdNetworkTags: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("doesHelperAdProvideGUI")) {
         try {
            // Check if the network that provides the currently ready helper ad, provides its own GUI or not
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.doesHelperAdProvideGUI(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in doesHelperAdProvideGUI: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }

         return true;
      }
      else if (action.equals ("getNeutralAdNetworkName")) {
         try {
            // Get the name of the network that provides the currently ready neutral ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getNeutralAdNetworkName(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getNeutralAdNetworkName: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("getNeutralAdNetworkType")) {
         try {
            // Get the type of the network that provides the currently ready neutral ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getNeutralAdNetworkType(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getNeutralAdNetworkType: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("getNeutralAdNetworkTags")) {
         try {
            // Get the tags of the network that provides the currently ready neutral ad
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.getNeutralAdNetworkTags(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in getNeutralAdNetworkTags: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, ""));
         }

         return true;
      }
      else if (action.equals ("doesNeutralAdProvideGUI")) {
         try {
            // Check if the network that provides the currently ready neutral ad, provides its own GUI or not
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.doesNeutralAdProvideGUI(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in doesNeutralAdProvideGUI: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }

         return true;
      }
      else if (action.equals ("reportAchievement")) {
         try {
            // Report achievement
            FGLConnector.reportAchievement(args.getString (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in reportAchievement: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("promptIncentive")) {
         try {
            // Prompt user to perform an action to get an incentive
            FGLConnector.promptIncentive(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in promptIncentive: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("isIncentiveRewardGranted")) {
         try {
            // Check if an incentive's reward should currently be granted
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.isIncentiveRewardGranted(args.getString (0))));
         } catch (Exception e) {
            Log.e (TAG, "exception in isIncentiveRewardGranted: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }

         return true;
      }
      else if (action.equals ("reportPurchase")) {
         try {
            // Report a completed in-app purchase
            FGLConnector.reportPurchase(args.getString (0), args.getDouble (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in reportPurchase: " + e.toString());
            e.printStackTrace ();
         }
      }
      else if (action.equals ("reportAttribution")) {
         try {
            // Report user attribution for this session
            FGLConnector.reportAttribution(args.getString (0), args.getString (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in reportAttribution: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showMoreGames")) {
         try {
            // Show more games page
            FGLConnector.showMoreGames();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showMoreGames: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showNewsletter")) {
         try {
            // Show newsletter
            FGLConnector.showNewsletter();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showNewsletter: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showActuallyFreeGames")) {
         try {
            // Show actually free games page
            FGLConnector.showActuallyFreeGames();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showActuallyFreeGames: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("suspendAdsForHours")) {
         try {
            // Suspend ads for the specified amount of hours
            FGLConnector.suspendAdsForHours(args.getInt (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in suspendAdsForHours: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("enableLocalNotification")) {
         try {
            // Enable showing a local notification
            FGLConnector.enableLocalNotification(args.getString (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in enableLocalNotification: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("enableLocalNotificationWithDelay")) {
         try {
            // Enable showing a local notification with a specific title and delay
            FGLConnector.enableLocalNotificationWithDelay(args.getString (0), args.getString (1), args.getInt (2));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in enableLocalNotificationWithDelay: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("disableLocalNotification")) {
         try {
            // Disable showing a local notification
            FGLConnector.disableLocalNotification();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in disableLocalNotification: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("arePushNotificationsAvailable")) {
         try {
            // Check if the device supports push notifications
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.arePushNotificationsAvailable()));
         } catch (Exception e) {
            Log.e (TAG, "exception in arePushNotificationsAvailable: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }

         return true;
      }
      else if (action.equals ("setPushNotificationsChannel")) {
         try {
            // Set channel to use for push notifications
            FGLConnector.enableLocalNotification(args.getString (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in enableLocalNotification: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("enablePushNotifications")) {
         try {
            // Opt into receiving push notifications
            FGLConnector.enablePushNotifications();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in enablePushNotifications: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("disablePushNotifications")) {
         try {
            // Opt out of receiving push notifications
            FGLConnector.disablePushNotifications();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in enablePushNotifications: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("arePushNotificationsEnabled")) {
         try {
            // Check if the device supports push notifications
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.arePushNotificationsEnabled()));
         } catch (Exception e) {
            Log.e (TAG, "exception in arePushNotificationsEnabled: " + e.toString());
            e.printStackTrace ();

            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }

         return true;
      }
      else if (action.equals ("enterGameScreen")) {
         try {
            // Tell Adsorb that the game is entering the specified screen
            FGLConnector.enterGameScreen(args.getString (0), args.getBoolean (1));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in enableLocalNotification: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("leaveGameScreen")) {
         try {
            // Tell Adsorb that the game is leaving the current screen
            FGLConnector.leaveGameScreen();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in leaveGameScreen: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("reportGameEvent")) {
         try {
            // Report game event
            FGLConnector.reportGameEvent(args.getString (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in reportGameEvent: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("showMultiplayerWall")) {
         try {
            // Show the social feed and multiplayer challenges wall
            FGLConnector.showMultiplayerWall();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showMultiplayerWall: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("updateMultiplayerScore")) {
         try {
            // Report that the local player's current score has changed
            FGLConnector.updateMultiplayerScore(args.getInt (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in updateMultiplayerScore: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("completeMultiplayerGame")) {
         try {
            // Report that the multiplayer game is completed for the local player
            FGLConnector.completeMultiplayerGame(args.getInt (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in completeMultiplayerGame: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("cancelMultiplayerGame")) {
         try {
            // Report that the multiplayer game has been canceled
            FGLConnector.cancelMultiplayerGame();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in cancelMultiplayerGame: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("setSuccessAdsAutoMode")) {
         try {
            // Set success rewarded ads to be automatically loaded in the background
            FGLConnector.setSuccessAdsAutoMode(args.getString (0), args.getString (1), args.getBoolean (2));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in setSuccessAdsAutoMode: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("setHelperAdsAutoMode")) {
         try {
            // Set helper rewarded ads to be automatically loaded in the background
            FGLConnector.setHelperAdsAutoMode(args.getString (0), args.getString (1), args.getString (2), args.getString (3), args.getBoolean (4));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in setHelperAdsAutoMode: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("setNeutralAdsAutoMode")) {
         try {
            // Set neutral rewarded ads to be automatically loaded in the background
            FGLConnector.setNeutralAdsAutoMode(args.getString (0), args.getString (1), args.getBoolean (2));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in setNeutralAdsAutoMode: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("rewardedAdSetupComplete")) {
         try {
            // Complete the setup of automatically load rewarded ads and start loading in the background
            FGLConnector.rewardedAdSetupComplete();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in rewardedAdSetupComplete: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("isAdOverlayReady")) {
         try {
            // Check if an ad overlay is currently ready to show
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, FGLConnector.isAdOverlayReady()));
         } catch (Exception e) {
            Log.e (TAG, "exception in isAdOverlayReady: " + e.toString());
            e.printStackTrace ();
            
            callbackContext.sendPluginResult (new PluginResult (PluginResult.Status.OK, false));
         }
         
         return true;
      }
      else if (action.equals ("showAdOverlay")) {
         try {
            // Show cross-promo ad overlay, if possible
            FGLConnector.showAdOverlay((float) args.getDouble (0), (float) args.getDouble (1),
                                       (float) args.getDouble (2), (float) args.getDouble (3),
                                       args.getInt (4), args.getBoolean (5));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in showAdOverlay: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("advanceAdOverlay")) {
         try {
            // Refresh cross-promo ad overlay, if another ad is ready
            FGLConnector.advanceAdOverlay();
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in advanceAdOverlay: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else if (action.equals ("hideAdOverlay")) {
         try {
            // Hide cross-promo ad overlay, if one was showing
            FGLConnector.hideAdOverlay(args.getInt (0));
            callbackContext.success();
            return true;
         } catch (Exception e) {
            Log.e (TAG, "exception in hideAdOverlay: " + e.toString());
            e.printStackTrace ();
            callbackContext.success();
         }
      }
      else {
         Log.e (TAG, "Unknown action requested: \"" + action + "\", args: " + args);
      }
      
      return false;
   }
   
   // Dispatch even to Javascript code
   
   private void dispatchEvent (String eventName, String param) {
      try {
         JSONObject info = new JSONObject ();
         
         info.put ("event", eventName);
         if (param != null)
            info.put ("param", param);
         
         PluginResult result = new PluginResult (PluginResult.Status.OK, info);
         result.setKeepCallback (true);
         Log.d (TAG, "dispatch event: " + info);
         if (m_eventCallbackContext != null)
            m_eventCallbackContext.sendPluginResult (result);
      } catch (Exception e) {
         Log.e (TAG, "exception in dispatchEvent: " + e.toString());
         e.printStackTrace();
      }
   }
   
   // FGLEventListener implementation
   
   @Override
   public void onInterstitialAdsAvailable() {
      Log.d (TAG, "onInterstitialAdsAvailable");
      dispatchEvent ("onInterstitialAdsAvailable", null);
   }

   @Override
   public void onInterstitialAdDismissed() {
      Log.d (TAG, "onInterstitialAdDismissed");
      dispatchEvent ("onInterstitialAdDismissed", null);
   }

   @Override
   public void onInterstitialAdFailed() {
      Log.d (TAG, "onInterstitialAdFailed");
      dispatchEvent ("onInterstitialAdFailed", null);
   }

   @Override
   public void onSuccessRewardedAdReady(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdReady for filter '" + filterName + "'");
      dispatchEvent ("onSuccessRewardedAdReady", filterName);
   }

   @Override
   public void onSuccessRewardedAdUnavailable(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdUnavailable for filter '" + filterName + "'");
      dispatchEvent ("onSuccessRewardedAdUnavailable", filterName);
   }

   @Override
   public void onSuccessRewardedAdDismissed(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdDismissed for filter '" + filterName + "'");
      dispatchEvent ("onSuccessRewardedAdDismissed", filterName);
   }

   @Override
   public void onSuccessRewardedAdFailed(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdFailed for filter '" + filterName + "'");
      dispatchEvent ("onSuccessRewardedAdFailed", filterName);
   }

   @Override
   public void onSuccessRewardGranted(String filterName) {
      Log.d (TAG, "onSuccessRewardGranted for filter '" + filterName + "'");
      dispatchEvent ("onSuccessRewardGranted", filterName);
   }

   @Override
   public void onHelperRewardGranted(String filterName) {
      Log.d (TAG, "onHelperRewardGranted for filter '" + filterName + "'");
      dispatchEvent ("onHelperRewardGranted", filterName);
   }

   @Override
   public void onHelperRewardedAdReady(String filterName) {
      Log.d (TAG, "onHelperRewardedAdReady for filter '" + filterName + "'");
      dispatchEvent ("onHelperRewardedAdReady", filterName);
   }

   @Override
   public void onHelperRewardedAdUnavailable(String filterName) {
      Log.d (TAG, "onHelperRewardedAdUnavailable for filter '" + filterName + "'");
      dispatchEvent ("onHelperRewardedAdUnavailable", filterName);
   }
   
   @Override
   public void onHelperRewardedAdDismissed(String filterName) {
      Log.d (TAG, "onHelperRewardedAdDismissed for filter '" + filterName + "'");
      dispatchEvent ("onHelperRewardedAdDismissed", filterName);
   }

   @Override
   public void onHelperRewardedAdFailed(String filterName) {
      Log.d (TAG, "onHelperRewardedAdFailed for filter '" + filterName + "'");
      dispatchEvent ("onHelperRewardedAdFailed", filterName);
   }
   
   @Override
   public void onNeutralRewardedAdReady(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdReady for filter '" + filterName + "'");
      dispatchEvent ("onNeutralRewardedAdReady", filterName);
   }

   @Override
   public void onNeutralRewardedAdUnavailable(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdUnavailable for filter '" + filterName + "'");
      dispatchEvent ("onNeutralRewardedAdUnavailable", filterName);
   }
   
   @Override
   public void onNeutralRewardedAdDismissed(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdDismissed for filter '" + filterName + "'");
      dispatchEvent ("onNeutralRewardedAdDismissed", filterName);
   }

   @Override
   public void onNeutralRewardedAdFailed(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdFailed for filter '" + filterName + "'");
      dispatchEvent ("onNeutralRewardedAdFailed", filterName);
   }
   
   @Override
   public void onNeutralRewardGranted(String filterName) {
      Log.d (TAG, "onNeutralRewardGranted for filter '" + filterName + "'");
      dispatchEvent ("onNeutralRewardGranted", filterName);
   }

   @Override
   public void onVirtualCurrencyGranted(int amount) {
      Log.d (TAG, "onVirtualCurrencyGranted, amount: " + amount);
      dispatchEvent ("onVirtualCurrencyGranted", Integer.toString (amount));
   }

   @Override
   public void onMultiplayerGameStarted(int randomSeed) {
      Log.d (TAG, "onMultiplayerGameStarted");
      dispatchEvent ("onMultiplayerGameStarted", Integer.toString (randomSeed));
   }

   @Override
   public void onMultiplayerGameEnded() {
      Log.d (TAG, "onMultiplayerGameEnded");
      dispatchEvent ("onMultiplayerGameEnded", null);
   }
   
   @Override
   public void onShowMoreGamesDismissed() {
      Log.d (TAG, "onShowMoreGamesDismissed");
      dispatchEvent ("onShowMoreGamesDismissed", null);
   }
}
