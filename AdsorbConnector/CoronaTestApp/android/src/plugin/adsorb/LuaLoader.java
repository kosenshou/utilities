//
//  LuaLoader.java
//  TemplateApp
//
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.adsorb"
package plugin.adsorb;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.NamedJavaFunction;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;

/**
 * Implements the Lua interface for a Corona plugin.
 * <p>
 * Only one instance of this class will be created by Corona for the lifetime of the application.
 * This instance will be re-used for every new Corona activity that gets created.
 */
public class LuaLoader implements JavaFunction, CoronaRuntimeListener, FGLEventListener {
   // Tag for logging
   public static final String TAG = "FGLAdsorbCorona";
   
   // Corona connector version
   private static final String CORONA_CONNECTOR_VERSION = "1.5.8";
   
   // Name of event dispatched to Lua
   private static final String EVENT_NAME = "pluginadsorbevent";
   
	// ID of event listener function from Lua
	private int m_listenerFuncId;

   // Lua state to dispatch events to
   private LuaState m_luaState;

	/**
	 * Creates a new Lua interface to this plugin.
	 * <p>
	 * Note that a new LuaLoader instance will not be created for every CoronaActivity instance.
	 * That is, only one instance of this class will be created for the lifetime of the application process.
	 * This gives a plugin the option to do operations in the background while the CoronaActivity is destroyed.
	 */
	public LuaLoader() {
		// Initialize member variables.
		m_listenerFuncId = CoronaLua.REFNIL;

		// Set up this plugin to listen for Corona runtime events to be received by methods
		// onLoaded(), onStarted(), onSuspended(), onResumed(), and onExiting().
		CoronaEnvironment.addRuntimeListener(this);
	}

	/**
	 * Called when this plugin is being loaded via the Lua require() function.
	 * <p>
	 * Note that this method will be called everytime a new CoronaActivity has been launched.
	 * This means that you'll need to re-initialize this plugin here.
	 * <p>
	 * Warning! This method is not called on the main UI thread.
	 * @param L Reference to the Lua state that the require() function was called from.
	 * @return Returns the number of values that the require() function will return.
	 *         <p>
	 *         Expected to return 1, the library that the require() function is loading.
	 */
	@Override
	public int invoke(LuaState L) {
		// Register this plugin into Lua with the following functions.
		NamedJavaFunction[] luaFunctions = new NamedJavaFunction[] {
			new InitWrapper(),
         new IsSdkInjectedWrapper (),
         new ShowInterstitialWrapper (),
         new SetSuccessAdsVarsWrapper (),
         new SetHelperAdsVarsWrapper (),
         new SetNeutralAdsVarsWrapper (),
         new LoadSuccessAdWrapper (),
         new ShowSuccessAdWrapper (),
         new LoadHelperAdWrapper (),
         new ShowHelperAdWrapper (),
         new LoadNeutralAdWrapper (),
         new ShowNeutralAdWrapper (),
         new FilterSuccessAdsWrapper (),
         new FilterHelperAdsWrapper (),
         new FilterNeutralAdsWrapper (),
         new GetSuccessAdNetworkNameWrapper (),
         new GetSuccessAdNetworkTypeWrapper (),
         new GetSuccessAdNetworkTagsWrapper (),
         new DoesSuccessAdProvideGUIWrapper (),
         new GetHelperAdNetworkNameWrapper (),
         new GetHelperAdNetworkTypeWrapper (),
         new GetHelperAdNetworkTagsWrapper (),
         new DoesHelperAdProvideGUIWrapper (),
         new GetNeutralAdNetworkNameWrapper (),
         new GetNeutralAdNetworkTypeWrapper (),
         new GetNeutralAdNetworkTagsWrapper (),
         new DoesNeutralAdProvideGUIWrapper (),
         new ReportAchievementWrapper (),
         new PromptIncentiveWrapper (),
         new IsIncentiveRewardGrantedWrapper (),
         new ReportPurchaseWrapper (),
         new ReportAttributionWrapper (),
         new ShowMoreGamesWrapper (),
         new ShowNewsletterWrapper (),
         new ShowActuallyFreeGamesWrapper (),
         new SuspendAdsForHoursWrapper (),
         new EnableLocalNotificationWrapper (),
         new EnableLocalNotificationWithDelayWrapper (),
         new DisableLocalNotificationWrapper (),
         new ArePushNotificationsAvailableWrapper (),
         new SetPushNotificationsChannelWrapper (),
         new EnablePushNotificationsWrapper (),
         new DisablePushNotificationsWrapper (),
         new ArePushNotificationsEnabledWrapper (),
         new EnterGameScreenWrapper (),
         new LeaveGameScreenWrapper (),
         new ReportGameEventWrapper (),
         new ShowMultiplayerWallWrapper (),
         new UpdateMultiplayerScoreWrapper (),
         new CompleteMultiplayerGameWrapper (),
         new CancelMultiplayerGameWrapper (),
         new SetSuccessAdsAutoModeWrapper (),
         new SetHelperAdsAutoModeWrapper (),
         new SetNeutralAdsAutoModeWrapper (),
         new RewardedAdSetupCompleteWrapper (),
         new IsAdOverlayReadyWrapper (),
         new ShowAdOverlayWrapper (),
         new AdvanceAdOverlayWrapper (),
         new hideAdOverlayWrapper (),
		};
		String libName = L.toString( 1 );
		L.register(libName, luaFunctions);

		// Returning 1 indicates that the Lua require() function will return the above Lua library.
		return 1;
	}

	/**
	 * Called after the Corona runtime has been created and just before executing the "main.lua" file.
	 * <p>
	 * Warning! This method is not called on the main thread.
	 * @param runtime Reference to the CoronaRuntime object that has just been loaded/initialized.
	 *                Provides a LuaState object that allows the application to extend the Lua API.
	 */
	@Override
	public void onLoaded(CoronaRuntime runtime) {
		// Note that this method will not be called the first time a Corona activity has been launched.
		// This is because this listener cannot be added to the CoronaEnvironment until after
		// this plugin has been required-in by Lua, which occurs after the onLoaded() event.
		// However, this method will be called when a 2nd Corona activity has been created.
	}

	/**
	 * Called just after the Corona runtime has executed the "main.lua" file.
	 * <p>
	 * Warning! This method is not called on the main thread.
	 * @param runtime Reference to the CoronaRuntime object that has just been started.
	 */
	@Override
	public void onStarted(CoronaRuntime runtime) {
	}

	/**
	 * Called just after the Corona runtime has been suspended which pauses all rendering, audio, timers,
	 * and other Corona related operations. This can happen when another Android activity (ie: window) has
	 * been displayed, when the screen has been powered off, or when the screen lock is shown.
	 * <p>
	 * Warning! This method is not called on the main thread.
	 * @param runtime Reference to the CoronaRuntime object that has just been suspended.
	 */
	@Override
	public void onSuspended(CoronaRuntime runtime) {
	}

	/**
	 * Called just after the Corona runtime has been resumed after a suspend.
	 * <p>
	 * Warning! This method is not called on the main thread.
	 * @param runtime Reference to the CoronaRuntime object that has just been resumed.
	 */
	@Override
	public void onResumed(CoronaRuntime runtime) {
	}

	/**
	 * Called just before the Corona runtime terminates.
	 * <p>
	 * This happens when the Corona activity is being destroyed which happens when the user presses the Back button
	 * on the activity, when the native.requestExit() method is called in Lua, or when the activity's finish()
	 * method is called. This does not mean that the application is exiting.
	 * <p>
	 * Warning! This method is not called on the main thread.
	 * @param runtime Reference to the CoronaRuntime object that is being terminated.
	 */
	@Override
	public void onExiting(CoronaRuntime runtime) {
		// Remove the Lua listener reference.
		CoronaLua.deleteRef( runtime.getLuaState(), m_listenerFuncId );
		m_listenerFuncId = CoronaLua.REFNIL;
	}

	// Initialize connector
   
	private int init(LuaState L) {
		int listenerIndex = 1;

      m_luaState = L;
		if ( CoronaLua.isListener( L, listenerIndex, EVENT_NAME ) ) {
			m_listenerFuncId = CoronaLua.newRef( L, listenerIndex );
		}

      CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
      if (activity != null) {
         Log.d (TAG, "initialize Corona connector version " + CORONA_CONNECTOR_VERSION);
         FGLConnector.initialize (activity);
         FGLConnector.setListener (this);
      }
      
		return 0;
	}
   
	// Wrapper for init
   
	private class InitWrapper implements NamedJavaFunction {
		@Override
		public String getName() {
			return "init";
		}
		
		@Override
		public int invoke(LuaState L) {
			return init(L);
		}
	}

   // Wrapper for isSdkInjected
   
   private class IsSdkInjectedWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "isSdkInjected";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.isSdkInjected ();
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for showInterstitialAd
   
   private class ShowInterstitialWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showInterstitialAd";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showInterstitialAd();
         return 0;
      }
   }
   
   // Wrapper for setSuccessAdsVars
   
   private class SetSuccessAdsVarsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "setSuccessAdsVars";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.setSuccessAdsVars (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for setHelperAdsVars
   
   private class SetHelperAdsVarsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "setHelperAdsVars";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.setHelperAdsVars (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for setNeutralAdsVars
   
   private class SetNeutralAdsVarsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "setNeutralAdsVars";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.setNeutralAdsVars (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for loadSuccessAd
   
   private class LoadSuccessAdWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "loadSuccessAd";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.loadSuccessAd (L.checkString (1, null), L.checkString (2, null), L.checkBoolean (3, false));
         return 0;
      }
   }
   
   // Wrapper for showSuccessAd
   
   private class ShowSuccessAdWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showSuccessAd";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showSuccessAd (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for loadHelperAd
   
   private class LoadHelperAdWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "loadHelperAd";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.loadHelperAd (L.checkString (1, null), L.checkString (2, null),
                                    L.checkString (3, null), L.checkString (4, null), L.checkBoolean (5, false));
         return 0;
      }
   }
   
   // Wrapper for showHelperAd
   
   private class ShowHelperAdWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showHelperAd";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showHelperAd (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for loadNeutralAd
   
   private class LoadNeutralAdWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "loadNeutralAd";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.loadNeutralAd (L.checkString (1, null), L.checkString (2, null), L.checkBoolean (3, false));
         return 0;
      }
   }
   
   // Wrapper for showNeutralAd
   
   private class ShowNeutralAdWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showNeutralAd";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showNeutralAd (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for filterSuccessAds
   
   private class FilterSuccessAdsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "filterSuccessAds";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.filterSuccessAds (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for filterHelperAds
   
   private class FilterHelperAdsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "filterHelperAds";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.filterHelperAds (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for filterNeutralAds
   
   private class FilterNeutralAdsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "filterNeutralAds";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.filterNeutralAds (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for getSuccessAdNetworkName
   
   private class GetSuccessAdNetworkNameWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getSuccessAdNetworkName";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getSuccessAdNetworkName (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for getSuccessAdNetworkType
   
   private class GetSuccessAdNetworkTypeWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getSuccessAdNetworkType";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getSuccessAdNetworkType (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for getSuccessAdNetworkTags
   
   private class GetSuccessAdNetworkTagsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getSuccessAdNetworkTags";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getSuccessAdNetworkTags (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for doesSuccessAdProvideGUI
   
   private class DoesSuccessAdProvideGUIWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "doesSuccessAdProvideGUI";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.doesSuccessAdProvideGUI (L.checkString (1, null));
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for getHelperAdNetworkName
   
   private class GetHelperAdNetworkNameWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getHelperAdNetworkName";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getHelperAdNetworkName (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for getHelperAdNetworkType
   
   private class GetHelperAdNetworkTypeWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getHelperAdNetworkType";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getHelperAdNetworkType (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for getHelperAdNetworkTags
   
   private class GetHelperAdNetworkTagsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getHelperAdNetworkTags";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getHelperAdNetworkTags (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for doesHelperAdProvideGUI
   
   private class DoesHelperAdProvideGUIWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "doesHelperAdProvideGUI";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.doesHelperAdProvideGUI (L.checkString (1, null));
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for getNeutralAdNetworkName
   
   private class GetNeutralAdNetworkNameWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getNeutralAdNetworkName";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getNeutralAdNetworkName (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for getNeutralAdNetworkType
   
   private class GetNeutralAdNetworkTypeWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getNeutralAdNetworkType";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getNeutralAdNetworkType (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for getNeutralAdNetworkTags
   
   private class GetNeutralAdNetworkTagsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "getNeutralAdNetworkTags";
      }
      
      @Override
      public int invoke(LuaState L) {
         String result = FGLConnector.getNeutralAdNetworkTags (L.checkString (1, null));
         L.pushString (result);
         return 1;
      }
   }
   
   // Wrapper for doesNeutralAdProvideGUI
   
   private class DoesNeutralAdProvideGUIWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "doesNeutralAdProvideGUI";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.doesNeutralAdProvideGUI (L.checkString (1, null));
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for reportAchievement
   
   private class ReportAchievementWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "reportAchievement";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.reportAchievement (L.checkString (1, null));
         return 0;
      }
   }
   
   // Wrapper for promptIncentive
   
   private class PromptIncentiveWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "promptIncentive";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.promptIncentive (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for isIncentiveRewardGranted
   
   private class IsIncentiveRewardGrantedWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "isIncentiveRewardGranted";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.isIncentiveRewardGranted (L.checkString (1, null));
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for reportPurchase
   
   private class ReportPurchaseWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "reportPurchase";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.reportPurchase (L.checkString (1, null), L.checkNumber (2, 0.0));
         return 0;
      }
   }
   
   // Wrapper for reportAttribution
   
   private class ReportAttributionWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "reportAttribution";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.reportAttribution (L.checkString (1, null), L.checkString (2, null));
         return 0;
      }
   }
   
   // Wrapper for showMoreGames
   
   private class ShowMoreGamesWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showMoreGames";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showMoreGames ();
         return 0;
      }
   }
   
   // Wrapper for showNewsletter
   
   private class ShowNewsletterWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showNewsletter";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showNewsletter ();
         return 0;
      }
   }
   
   // Wrapper for showActuallyFreeGames
   
   private class ShowActuallyFreeGamesWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showActuallyFreeGames";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showActuallyFreeGames ();
         return 0;
      }
   }
   
   // Wrapper for suspendAdsForHours
   
   private class SuspendAdsForHoursWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "suspendAdsForHours";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.suspendAdsForHours (L.checkInteger (1, 0));
         return 0;
      }
   }
   
   // Wrapper for enableLocalNotification
   
   private class EnableLocalNotificationWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "enableLocalNotification";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.enableLocalNotification (L.checkString (1, null));
         return 0;
      }
   }
   
   // Wrapper for enableLocalNotificationWithDelay
   
   private class EnableLocalNotificationWithDelayWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "enableLocalNotificationWithDelay";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.enableLocalNotificationWithDelay (L.checkString (1, null), L.checkString (2, null), L.checkInteger (3, 86400));
         return 0;
      }
   }
   
   // Wrapper for disableLocalNotification
   
   private class DisableLocalNotificationWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "disableLocalNotification";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.disableLocalNotification ();
         return 0;
      }
   }
   
   // Wrapper for arePushNotificationsAvailable
   
   private class ArePushNotificationsAvailableWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "arePushNotificationsAvailable";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.arePushNotificationsAvailable ();
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for setPushNotificationsChannel
   
   private class SetPushNotificationsChannelWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "setPushNotificationsChannel";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.setPushNotificationsChannel (L.checkString (1, null));
         return 0;
      }
   }
   
   // Wrapper for enablePushNotifications
   
   private class EnablePushNotificationsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "enablePushNotifications";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.enablePushNotifications ();
         return 0;
      }
   }
   
   // Wrapper for disablePushNotifications
   
   private class DisablePushNotificationsWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "disablePushNotifications";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.disablePushNotifications ();
         return 0;
      }
   }
   
   // Wrapper for arePushNotificationsEnabled
   
   private class ArePushNotificationsEnabledWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "arePushNotificationsEnabled";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.arePushNotificationsEnabled ();
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for enterGameScreen
   
   private class EnterGameScreenWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "enterGameScreen";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.enterGameScreen (L.checkString (1, null), L.checkBoolean (2, false));
         return 0;
      }
   }
   
   // Wrapper for leaveGameScreen
   
   private class LeaveGameScreenWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "leaveGameScreen";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.leaveGameScreen ();
         return 0;
      }
   }
   
   // Wrapper for reportGameEvent
   
   private class ReportGameEventWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "reportGameEvent";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.reportGameEvent (L.checkString (1, null));
         return 0;
      }
   }
   
   // Wrapper for showMultiplayerWall
   
   private class ShowMultiplayerWallWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showMultiplayerWall";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showMultiplayerWall ();
         return 0;
      }
   }
   
   // Wrapper for updateMultiplayerScore
   
   private class UpdateMultiplayerScoreWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "updateMultiplayerScore";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.updateMultiplayerScore (L.checkInteger (1, 0));
         return 0;
      }
   }
   
   // Wrapper for completeMultiplayerGame
   
   private class CompleteMultiplayerGameWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "completeMultiplayerGame";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.completeMultiplayerGame (L.checkInteger (1, 0));
         return 0;
      }
   }
   
   // Wrapper for cancelMultiplayerGame
   
   private class CancelMultiplayerGameWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "cancelMultiplayerGame";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.cancelMultiplayerGame ();
         return 0;
      }
   }
   
   // Wrapper for setSuccessAdsAutoMode
   
   private class SetSuccessAdsAutoModeWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "setSuccessAdsAutoMode";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.setSuccessAdsAutoMode (L.checkString (1, null), L.checkString (2, null), L.checkBoolean (3, false));
         return 0;
      }
   }
   
   // Wrapper for setHelperAdsAutoMode
   
   private class SetHelperAdsAutoModeWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "setHelperAdsAutoMode";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.setHelperAdsAutoMode (L.checkString (1, null), L.checkString (2, null),
                                            L.checkString (3, null), L.checkString (4, null), L.checkBoolean (5, false));
         return 0;
      }
   }
   
   // Wrapper for setNeutralAdsAutoMode
   
   private class SetNeutralAdsAutoModeWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "setNeutralAdsAutoMode";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.setNeutralAdsAutoMode (L.checkString (1, null), L.checkString (2, null), L.checkBoolean (3, false));
         return 0;
      }
   }
   
   // Wrapper for rewardedAdSetupComplete
   
   private class RewardedAdSetupCompleteWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "rewardedAdSetupComplete";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.rewardedAdSetupComplete ();
         return 0;
      }
   }
   
   // Wrapper for isAdOverlayReady
   
   private class IsAdOverlayReadyWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "isAdOverlayReady";
      }
      
      @Override
      public int invoke(LuaState L) {
         boolean bResult = FGLConnector.isAdOverlayReady ();
         L.pushBoolean (bResult);
         return 1;
      }
   }
   
   // Wrapper for showAdOverlay
   
   private class ShowAdOverlayWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "showAdOverlay";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.showAdOverlay ((float) L.checkNumber (1, 0.0), (float) L.checkNumber (2, 0.0),
                                     (float) L.checkNumber (3, 0.0), (float) L.checkNumber (4, 0.0),
                                     L.checkInteger (5, 0), L.checkBoolean (6, false));
         return 0;
      }
   }
   
   // Wrapper for advanceAdOverlay
   
   private class AdvanceAdOverlayWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "advanceAdOverlay";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.advanceAdOverlay ();
         return 0;
      }
   }
   
   // Wrapper for hideAdOverlay
   
   private class hideAdOverlayWrapper implements NamedJavaFunction {
      @Override
      public String getName() {
         return "hideAdOverlay";
      }
      
      @Override
      public int invoke(LuaState L) {
         FGLConnector.hideAdOverlay (L.checkInteger (1, 0));
         return 0;
      }
   }
   
   // Dispatch event to Lua
   
   private void sendEvent (String eventName, String param) {
      if (m_luaState != null) {
         Log.d (TAG, "sendMessage: " + eventName + ", " + param);
         CoronaLua.newEvent (m_luaState, EVENT_NAME);
         
         if (param != null)
            m_luaState.pushString(param);
         else
            m_luaState.pushString("");
         m_luaState.setField(-2, "param");
         
         m_luaState.pushString(eventName);
         m_luaState.setField(-2, "type");
         
         try {
            CoronaLua.dispatchEvent (m_luaState, m_listenerFuncId, 0);
         } catch (Exception e) {
            Log.d (TAG, "exception in sendEvent: " + e.toString());
            e.printStackTrace ();
         }
      }
   }
   
   // FGLEventListener implementation
   
   @Override
   public void onInterstitialAdsAvailable() {
      Log.d (TAG, "onInterstitialAdsAvailable");
      sendEvent ("onInterstitialAdsAvailable", null);
   }
   
   @Override
   public void onInterstitialAdDismissed() {
      Log.d (TAG, "onInterstitialAdDismissed");
      sendEvent ("onInterstitialAdDismissed", null);
   }
   
   @Override
   public void onInterstitialAdFailed() {
      Log.d (TAG, "onInterstitialAdFailed");
      sendEvent ("onInterstitialAdFailed", null);
   }
   
   @Override
   public void onSuccessRewardedAdReady(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdReady for filter '" + filterName + "'");
      sendEvent ("onSuccessRewardedAdReady", filterName);
   }
   
   @Override
   public void onSuccessRewardedAdUnavailable(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdUnavailable for filter '" + filterName + "'");
      sendEvent ("onSuccessRewardedAdUnavailable", filterName);
   }
   
   @Override
   public void onSuccessRewardedAdDismissed(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdDismissed for filter '" + filterName + "'");
      sendEvent ("onSuccessRewardedAdDismissed", filterName);
   }
   
   @Override
   public void onSuccessRewardedAdFailed(String filterName) {
      Log.d (TAG, "onSuccessRewardedAdFailed for filter '" + filterName + "'");
      sendEvent ("onSuccessRewardedAdFailed", filterName);
   }
   
   @Override
   public void onSuccessRewardGranted(String filterName) {
      Log.d (TAG, "onSuccessRewardGranted for filter '" + filterName + "'");
      sendEvent ("onSuccessRewardGranted", filterName);
   }
   
   @Override
   public void onHelperRewardGranted(String filterName) {
      Log.d (TAG, "onHelperRewardGranted for filter '" + filterName + "'");
      sendEvent ("onHelperRewardGranted", filterName);
   }
   
   @Override
   public void onHelperRewardedAdReady(String filterName) {
      Log.d (TAG, "onHelperRewardedAdReady for filter '" + filterName + "'");
      sendEvent ("onHelperRewardedAdReady", filterName);
   }
   
   @Override
   public void onHelperRewardedAdUnavailable(String filterName) {
      Log.d (TAG, "onHelperRewardedAdUnavailable for filter '" + filterName + "'");
      sendEvent ("onHelperRewardedAdUnavailable", filterName);
   }
   
   @Override
   public void onHelperRewardedAdDismissed(String filterName) {
      Log.d (TAG, "onHelperRewardedAdDismissed for filter '" + filterName + "'");
      sendEvent ("onHelperRewardedAdDismissed", filterName);
   }
   
   @Override
   public void onHelperRewardedAdFailed(String filterName) {
      Log.d (TAG, "onHelperRewardedAdFailed for filter '" + filterName + "'");
      sendEvent ("onHelperRewardedAdFailed", filterName);
   }
   
   @Override
   public void onNeutralRewardedAdReady(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdReady for filter '" + filterName + "'");
      sendEvent ("onNeutralRewardedAdReady", filterName);
   }
   
   @Override
   public void onNeutralRewardedAdUnavailable(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdUnavailable for filter '" + filterName + "'");
      sendEvent ("onNeutralRewardedAdUnavailable", filterName);
   }
   
   @Override
   public void onNeutralRewardedAdDismissed(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdDismissed for filter '" + filterName + "'");
      sendEvent ("onNeutralRewardedAdDismissed", filterName);
   }
   
   @Override
   public void onNeutralRewardedAdFailed(String filterName) {
      Log.d (TAG, "onNeutralRewardedAdFailed for filter '" + filterName + "'");
      sendEvent ("onNeutralRewardedAdFailed", filterName);
   }
   
   @Override
   public void onNeutralRewardGranted(String filterName) {
      Log.d (TAG, "onNeutralRewardGranted for filter '" + filterName + "'");
      sendEvent ("onNeutralRewardGranted", filterName);
   }
   
   @Override
   public void onVirtualCurrencyGranted(int amount) {
      Log.d (TAG, "onVirtualCurrencyGranted, amount: " + amount);
      sendEvent ("onVirtualCurrencyGranted", Integer.toString (amount));
   }
   
   @Override
   public void onMultiplayerGameStarted(int randomSeed) {
      Log.d (TAG, "onMultiplayerGameStarted");
      sendEvent ("onMultiplayerGameStarted", Integer.toString (randomSeed));
   }
   
   @Override
   public void onMultiplayerGameEnded() {
      Log.d (TAG, "onMultiplayerGameEnded");
      sendEvent ("onMultiplayerGameEnded", null);
   }
   
   @Override
   public void onShowMoreGamesDismissed() {
      Log.d (TAG, "onShowMoreGamesDismissed");
      sendEvent ("onShowMoreGamesDismissed", null);
   }
}
