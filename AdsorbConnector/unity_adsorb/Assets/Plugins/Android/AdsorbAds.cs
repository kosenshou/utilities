/**
 * Adsorb ads for Unity
 */

using UnityEngine;
using System.Collections;

public class AdsorbAds : MonoBehaviour
{
    ///// PUBLIC /////

    /**
     * Show interstitial ad
     */
    public static void ShowInterstitialAd()
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showInterstitialAd");
    }

    /**
     * Set custom extra variables for a network that provides success rewarded ads. The variables will be considered by the network the next
     * time a success rewarded ad is loaded.
     * 
     * @param strNetwork network name (for instance "mediabrix")
     * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
     */
    public static void SetSuccessAdsVars(string strNetwork, string strJsonVars)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("setSuccessAdsVars", strNetwork, strJsonVars);
    }

    /**
     * Set custom extra variables for a network that provides helper rewarded ads. The variables will be considered by the network the next
     * time a helper rewarded ad is loaded.
     * 
     * @param strNetwork network name (for instance "mediabrix")
     * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
     */
    public static void SetHelperAdsVars(string strNetwork, string strJsonVars)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("setHelperAdsVars", strNetwork, strJsonVars);
    }

    /**
     * Set custom extra variables for a network that provides neutral rewarded ads. The variables will be considered by the network the next
     * time a neutral rewarded ad is loaded.
     * 
     * @param strNetwork network name
     * @param strJsonVars custom variables in json format
     */
    public static void SetNeutralAdsVars(string strNetwork, string strJsonVars)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("setNeutralAdsVars", strNetwork, strJsonVars);
    }

    /**
     * Load success rewarded ad
     * 
     * @param achievement achievement done by the player (such as "Level completed")
     * @param reward reward to be granted (such as "50 gold")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
     */
    public static void LoadSuccessAd(string achievement, string reward, bool useGameGUI)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("loadSuccessAd", achievement, reward, useGameGUI);
    }

    /**
     * Show success rewarded ad
     * 
     * @param tag optional tag for tracking performance of individual ads, can be null
     * @param filterName name of filter to show success ad for (null for default)
     */
    public static void ShowSuccessAd(string tag, string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showSuccessAd", tag, filterName);
    }

    /**
     * Load helper rewarded ad
     * 
     * @param caption caption (such as "Need more time?")
     * @param enticementText enticement text (such as "Watch a short message and get an extra")
     * @param reward reward (such as "1:00")
     * @param buttonLabel button label (such as "Tap to get more time")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
     */
    public static void LoadHelperAd(string caption, string enticementText, string reward, string buttonLabel, bool useGameGUI)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("loadHelperAd", caption, enticementText, reward, buttonLabel, useGameGUI);
    }

    /**
     * Show helper rewarded ad
     * 
     * @param tag optional tag for tracking performance of individual ads, can be null
     * @param filterName name of filter to show helper ad for (null for default)
     */
    public static void ShowHelperAd(string tag, string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showHelperAd", tag, filterName);
    }

    /**
     * Load neutral rewarded ad
     *
     * @param caption neutral offer's caption (such as "Unlock untimed mode")
     * @param body neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
     */
    public static void LoadNeutralAd(string caption, string body, bool useGameGUI)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("loadNeutralAd", caption, body, useGameGUI);
    }

    /**
     * Show neutral rewarded ad
     * 
     * @param tag optional tag for tracking performance of individual ads, can be null
     * @param filterName name of filter to show neutral ad for (null for default)
     */
    public static void ShowNeutralAd(string tag, string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showNeutralAd", tag, filterName);
    }

    /**
     * Filter success rewarded ads using keywords
     *
     * @param filterName filter name (null for default)
     * @param filterKeywords filter keywords to apply to success rewarded ads (such as 'video high_value'), or null to remove the filter
     */
    static public void FilterSuccessAds(string filterName, string filterKeywords)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("filterSuccessAds", filterName, filterKeywords);
    }

    /**
     * Filter helper rewarded ads using keywords
     * 
     * @param filterName filter name (null for default)
     * @param filterKeywords filter keywords to apply to helper rewarded ads (such as 'video high_value'), or null to remove the filter
     */
    static public void FilterHelperAds(string filterName, string filterKeywords)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("filterHelperAds", filterName, filterKeywords);
    }

    /**
     * Filter neutral rewarded ads using keywords
     * 
     * @param filterName filter name (null for default)
     * @param filterKeywords filter keywords to apply to neutral rewarded ads (such as 'video high_value'), or null to remove the filter
     */
    static public void FilterNeutralAds(string filterName, string filterKeywords)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("filterNeutralAds", filterName, filterKeywords);
    }

    /**
     * Get the name of the network providing the currently loaded success rewarded ad
	 * 
	 * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return name
     */
    static public string GetSuccessAdNetworkName(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getSuccessAdNetworkName", filterName);
    }

    /**
     * Get the type of the currently loaded success rewarded ad (video, survey, ...)
	 * 
	 * @param filterName name of filter to get ad network type of (null for default)
     * 
     * @return type
     */
    static public string GetSuccessAdNetworkType(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getSuccessAdNetworkType", filterName);
    }

    /**
     * Get the filter tags of the currently loaded success rewarded ad (long, high_value, ...)
     * 
     * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return tags
     */
    static public string GetSuccessAdNetworkTags(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getSuccessAdNetworkType", filterName);
    }

    /**
     * Check if the currently loaded success rewarded ad provides its own GUI
	 * 
	 * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return true if it does, false if the game has to provide it
     */
    static public bool DoesSuccessAdProvideGUI(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<bool>("doesSuccessAdProvideGUI", filterName);
    }

    /**
     * Get the name of the network providing the currently loaded helper rewarded ad
	 * 
	 * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return name
     */
    static public string GetHelperAdNetworkName(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getHelperAdNetworkName", filterName);
    }

    /**
     * Get the type of the currently loaded helper rewarded ad (video, survey, ...)
	 * 
	 * @param filterName name of filter to get ad network type of (null for default)
     * 
     * @return type
     */
    static public string GetHelperAdNetworkType(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getHelperAdNetworkType", filterName);
    }

    /**
     * Get the filter tags of the currently loaded helper rewarded ad (long, high_value, ...)
     * 
     * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return tags
     */
    static public string GetHelperAdNetworkTags(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getHelperAdNetworkTags", filterName);
    }

    /**
     * Check if the currently loaded helper rewarded ad provides its own GUI
	 * 
	 * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return true if it does, false if the game has to provide it
     */
    static public bool DoesHelperAdProvideGUI(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<bool>("doesHelperAdProvideGUI", filterName);
    }

    /**
     * Get the name of the network providing the currently loaded neutral rewarded ad
	 * 
	 * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return name
     */
    static public string GetNeutralAdNetworkName(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getNeutralAdNetworkName", filterName);
    }

    /**
     * Get the type of the currently loaded neutral rewarded ad (video, survey, ...)
	 * 
	 * @param filterName name of filter to get ad network type of (null for default)
     * 
     * @return type
     */
    static public string GetNeutralAdNetworkType(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getNeutralAdNetworkType", filterName);
    }

    /**
     * Get the filter tags of the currently loaded neutral rewarded ad (long, high_value, ...)
     * 
     * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return tags
     */
    static public string GetNeutralAdNetworkTags(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<string>("getNeutralAdNetworkTags", filterName);
    }

    /**
     * Check if the currently loaded neutral rewarded ad provides its own GUI
	 * 
	 * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return true if it does, false if the game has to provide it
     */
    static public bool DoesNeutralAdProvideGUI(string filterName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<bool>("doesNeutralAdProvideGUI", filterName);
    }

 	/**
	 * Report a completed achievement
	 * 
	 * @param achievementName name of completed achievement
	 */
	static public void ReportAchievement(string achievementName)	
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("reportAchievement", achievementName);
    }

	/**
	 * Prompt user to perform an action to get an incentive
	 * 
	 * @param incentiveName incentive identifier
	 * @param incentiveText what the user will get if the action is performed
	 */
    static public void PromptIncentive(string incentiveName, string incentiveText)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("promptIncentive", incentiveName, incentiveText);
    }

	/**
	 * Check if an incentive's reward should currently be granted
	 * 
	 * @param incentiveName incentive identifier
	 * 
	 * @return true if the incentive's reward should currently be granted, false if not
	 */
    static public bool IsIncentiveRewardGranted(string incentiveName)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<bool>("isIncentiveRewardGranted", incentiveName);
    }

    /**
     * Report a completed in-app purchase
     * 
     * @param productName name of purchased product
	 * @param productPrice product price (such as 1.99)
     */
    static public void ReportPurchase(string productName, double productPrice)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("reportPurchase", productName, productPrice);
    }

    /**
     * Report user attribution for this session
     * 
     * @param vendor name of the vendor supplying the information
     * @param userType where the user came from
     */
    static public void ReportAttribution(string vendor, string userType)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("reportAttribution", vendor, userType);
    }

    /**
     * Show newsletter
     */
    public static void ShowNewsletter()
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showNewsletter");
    }

    /**
     * Show more games browser
     */
    public static void ShowMoreGames()
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showMoreGames");
    }

    /**
     * Show actually free games page
     */
    public static void ShowActuallyFreeGames()
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showActuallyFreeGames");
    }

	/**
	 * Suspend loading and showing any ads for the specified number of hours. This can
	 * be used as the reward of an incented ad for instance.
	 * 
	 * @param hours number of hours to suspend all ads for (for instance 24),
	 *              -1 to suspend permanently, 
	 *              0 to resume previously suspended ads
	 */
    public static void SuspendAdsForHours(int hours)
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("suspendAdsForHours", hours);
    }

	/**
	 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
	 * after a day
	 * 
	 * @param message message to display in the notification when it fires
	 */
    public static void EnableLocalNotification(string message)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("enableLocalNotification", message);
    }

	/**
	 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
	 * after the specified delay
	 * 
	 * @param title title to display in the notification when it fires
	 * @param message message to display in the notification when it fires
	 * @param delaySeconds delay in seconds after which to fire the local notification (for instance, 86400 for one day)
	 */
    public static void EnableLocalNotificationWithDelay(string title, string message, int delaySeconds)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("enableLocalNotificationWithDelay", title, message, delaySeconds);
    }

	/**
	 * Disable showing a local notification
	 */
    public static void DisableLocalNotification()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("disableLocalNotification");
    }

    /**
     * Check if the device supports push notifications
     * 
     * @return true if push notifications are available, false if not
     */
    public static bool ArePushNotificationsAvailable()
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<bool>("arePushNotificationsAvailable");
    }

	/**
	 * Set channel to use for push notifications for this game. This must be called before enablePushNotifications() or disablePushNotifications()
	 * 
	 * @param channel channel to use for push notifications, such as HiddenObject
	 */
    public static void setPushNotificationsChannel(string channel)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("setPushNotificationsChannel", channel);
    }

    /**
     * Opt into receiving push notifications for cross-marketing purposes
     */
    public static void EnablePushNotifications()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("enablePushNotifications");
    }

    /**
     * Opt out of receiving push notifications for cross-marketing purposes
     */
    public static void DisablePushNotifications()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("disablePushNotifications");
    }

    /**
     * Check if the user is currently opted into push notifications
     * 
     * @return true if push notifications are enabled, false if they are disabled
     */
    public static bool ArePushNotificationsEnabled()
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<bool>("arePushNotificationsEnabled");
    }

    /**
     * Tell Adsorb that the game is entering the specified screen
     * 
     * @param name screen name, such as: main_menu, instructions, level_select, playing, level_end, game_end
     * @param allowOverlays true to allow Adsorb to show monetization and marketing overlays on top of some of the game screens,
     *                      false to inform the current screen name only for event logging purposes
     */
    public static void EnterGameScreen(string name, bool allowOverlays)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("enterGameScreen", name, allowOverlays);
    }

	/**
	 * Tell Adsorb that the game is leaving the current screen
	 */
    public static void LeaveGameScreen ()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("leaveGameScreen");
    }

    /**
     * Report game event
     * 
     * @param eventName event name
     */
    static public void ReportGameEvent(string eventName)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("reportGameEvent", eventName);
    }

    /**
     * Show the social feed and multiplayer challenges wall for this network
     */
    public static void ShowMultiplayerWall()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showMultiplayerWall");
    }

	/**
	 * Report that the local player's current score has changed
	 * 
	 * @param score new score
	 */
    public static void UpdateMultiplayerScore(int score)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("updateMultiplayerScore", score);
    }

	/**
	 * Report that the multiplayer game is completed for the local player
	 * 
	 * @param score final score
	 */
    public static void CompleteMultiplayerGame(int score)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("completeMultiplayerGame", score);
    }

	/**
	 * Report that the multiplayer game has been canceled
	 */
    public static void CancelMultiplayerGame()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("cancelMultiplayerGame");
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
    public static void SetSuccessAdsAutoMode(string strSuccessAchievement, string strSuccessReward,
                                             bool useGameGUI)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("setSuccessAdsAutoMode", strSuccessAchievement, strSuccessReward, useGameGUI);
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
    public static void SetHelperAdsAutoMode(string strHelperCaption, string strHelperEnticementText,
                                            string strHelperReward, string strHelperButtonLabel,
                                            bool useGameGUI)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("setHelperAdsAutoMode", strHelperCaption, strHelperEnticementText, strHelperReward, strHelperButtonLabel, useGameGUI);
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
    public static void SetNeutralAdsAutoMode(string strNeutralCaption, string strNeutralBody, 
                                             bool useGameGUI)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("setNeutralAdsAutoMode", strNeutralCaption, strNeutralBody, useGameGUI);
    }

	/**
	 * Complete the setup of automatically load rewarded ads and start loading in the background
	 */
    public static void RewardedAdSetupComplete()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("rewardedAdSetupComplete");
    }

    /**
     * Check if an ad overlay is currently ready to show
     * 
     * @return true if an ad overlay is ready, false if not
     */
    public static bool IsAdOverlayReady()
    {
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        return adConnectorClass.CallStatic<bool>("isAdOverlayReady");
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
    public static void ShowAdOverlay(float x1, float y1, float x2, float y2, int nAnimDuration, bool bShowRectangle)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("showAdOverlay", x1, y1, x2, y2, nAnimDuration, bShowRectangle);
    }

	/**
	 * Refresh cross-promo ad overlay, if another ad is ready
	 */
    public static void AdvanceAdOverlay()
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("advanceAdOverlay");
    }

    /**
     * Hide cross-promo ad overlay, if one was showing
     *
     * @param nAnimDuration duration of the transition anims, in milliseconds
     */
    public static void HideAdOverlay(int nAnimDuration)
    {
        InitializeAdsorb();
        AndroidJavaClass adConnectorClass = new AndroidJavaClass("com.fgl.connector.FGLConnector");
        adConnectorClass.CallStatic("hideAdOverlay", nAnimDuration);
    }

    ///// PRIVATE /////

    /**
     * Initialize - you must attach this script to one of your game objects
     */
    void Awake()
    {
        InitializeAdsorb ();

        DontDestroyOnLoad(this);
    }

    static void InitializeAdsorb()
    {
        if (!mInitialized)
        {
            mInitialized = true;

            AndroidJavaClass unityConnectorClass = new AndroidJavaClass("com.fgl.unityconnector.FGLUnityConnector");
            AndroidJavaClass playerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            AndroidJavaObject activity = playerClass.GetStatic<AndroidJavaObject>("currentActivity");
            unityConnectorClass.CallStatic("initialize", activity);
        }
    }

    void OnApplicationPause(bool paused)
    {
        if (paused)
        {
            OnDisable();
        }
        else
        {
            OnEnable();
        }
    }

    void OnEnable()
    {
        AndroidJavaClass unityConnectorClass = new AndroidJavaClass("com.fgl.unityconnector.FGLUnityConnector");
        AndroidJavaClass playerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject activity = playerClass.GetStatic<AndroidJavaObject>("currentActivity");
        unityConnectorClass.CallStatic("resume", activity);
    }

    void OnDisable()
    {
        AndroidJavaClass unityConnectorClass = new AndroidJavaClass("com.fgl.unityconnector.FGLUnityConnector");
        AndroidJavaClass playerClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject activity = playerClass.GetStatic<AndroidJavaObject>("currentActivity");
        unityConnectorClass.CallStatic("pause", activity);
    }

    static bool mInitialized = false;
}
