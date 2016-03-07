Native C++ library for Adsorb

## What is Adsorb

Adsorb is FGL's unique monetization solution for your Android applications, that mediates interstitial ads using the highest paying networks, and also mediates incented videos for
an even higher payout. FGL constantly monitors the performance of the various networks and adjusts accordingly.

Adsorb uses a two-step process:
1) You integrate a small stub library in your application. Stub popups will appear when you request ads, and you can test granting rewards in your game as well.
2) FGL will wrap your game using Adsorb and integrate the real ad SDKs into your game. When new high-paying ad networks emerge, or when performance shifts, FGL can update the ads sdk without having you rebuild the app, and react in near-realtime to new market opportunities.

This approach has helped FGL become one of the most widely respected and highest paying solutions for free ad-based games on Android.

## Integration with your C++ app

1. Unpack the AdsorbNativeConnector archive. It contains both a jar and a native library.

2. Put FGL-Android-Connector.jar in your game's "libs" folder

3. Put the adsorbnative folder in the jni folder of your game

4. Add the native library to your JNI build process, by adding this section to your Android.mk:

   include $(CLEAR_VARS)

   LOCAL_MODULE := adsorbnative
   LOCAL_SRC_FILES := adsorbnative/lib/$(TARGET_ARCH_ABI)/libadsorbnative.so
   include $(PREBUILT_SHARED_LIBRARY)

5. Reference the lib in your Android.mk by adding this section:

   LOCAL_STATIC_LIBRARIES += adsorbnative
 
6. Reference the adsorb library's included headers in your Android.mk, like so:

   LOCAL_CFLAGS    += --Iadsorbnative/headers

7. In the java code for your game activity, load the native library by adding this section anywhere in your activity code:

	 static {
        /* Load NDK library for Adsorb */
        System.loadLibrary("adsorbnative");
    }

    IMPORTANT NOTE: if you load other native libraries, adsorbnative must be loaded first, for instance:

	 static {
        /* Load NDK library for Adsorb */
        System.loadLibrary("adsorbnative");

        ...
        /* Load your game libraries.. */
        System.loadLibrary("game");
        ...
    }


8. Initialize the connector in the java code for the activity, as such:

   import com.fgl.connector.FGLConnector;

   ...

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        FGLConnector.initialize (this);
    }

9. Use the ads in your C++ code:

   #include "AdsorbNative.h"

   ...

   The native API for showing ads is small and listed below, as well as in the header itself:

   /**
    * Show interstitial ad
    */
   void Adsorb_showInterstitialAd (void);

   /**
    * Load success rewarded ad
    * 
    * @param str_achievement achievement done by the player (such as "Level completed")
    * @param str_reward reward to be granted (such as "50 gold")
    * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
    *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
    */
   void Adsorb_loadSuccessAd (const char *str_achievement, const char *str_reward, bool use_game_gui);

   /**
    * Show success rewarded ad
    * 
    * @param tag optional tag for tracking performance of individual ads, can be NULL
    * @param filter_name name of filter to show success ad for (NULL for default)
    */
   void Adsorb_showSuccessAd (const char *tag, const char *filter_name);

   /**
    * Load helper rewarded ad
    * 
    * @param str_caption caption (such as "Need more time?")
    * @param str_enticement_text enticement text (such as "Watch a short message and get an extra")
    * @param str_reward reward (such as "1:00")
    * @param str_button_label button label (such as "Tap to get more time")
    * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
    *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
    */
   void Adsorb_loadHelperAd (const char *str_caption, const char *str_enticement_text, const char *str_reward, const char *str_button_label, bool use_game_gui);

   /**
    * Show helper rewarded ad
    * 
    * @param tag optional tag for tracking performance of individual ads, can be NULL
    * @param filter_name name of filter to show helper ad for (NULL for default)
    */
   void Adsorb_showHelperAd (const char *tag, const char *filter_name);

   /**
    * Load neutral rewarded ad
    *
    * @param str_caption neutral offer's caption (such as "Unlock untimed mode")
    * @param str_body neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
    * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
    *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
    */
   void Adsorb_loadNeutralAd (const char *str_caption, const char *str_body, bool use_game_gui);

   /**
    * Show neutral rewarded ad
    * 
    * @param tag optional tag for tracking performance of individual ads, can be NULL
    * @param filter_name name of filter to show neutral ad for (NULL for default)
    */
   void Adsorb_showNeutralAd (const char *tag, const char *filter_name);

   /**
    * Show newsletter subscription
    */
   void Adsorb_showNewsletter ();

   /**
    * Show more games browser
    */
   void Adsorb_showMoreGames (void);

   /**
    * Show actually free games page
    */
   void Adsorb_showActuallyFreeGames ();

   Examples of use:
   
   Adsorb_showInterstitialAd ();
   Adsorb_loadSuccessAd ("Level up!", "100 gold", false);
   Adsorb_showSuccessAd (NULL, NULL);
   Adsorb_loadHelperAd ("Need more time?", "Watch a short message and get an extra", "minute", "Tap to get more time", false);
   Adsorb_showHelperAd (NULL, NULL);
   Adsorb_loadNeutralAd ("Unlock untimed mode", "Watch an offer in a minute or less and unlock untimed mode!", false);
   Adsorb_showNeutralAd (NULL, NULL);
   Adsorb_showNewsletter ();
   Adsorb_showMoreGames ();
   Adsorb_showActuallyFreeGames ();
   
10. Handle Adsorb events

   Poll events in a loop in your game's frame rendering code using this method:

   /**
    * Get event sent by Adsorb, if any
    *
    * @param str_param pointer to where to store pointer to additional string parameter (will be set to NULL if there is none)
    *
    * @return received event, adsorb_none for none
    */
   int Adsorb_getEvent (const char **str_param);


   Example:

      const char *eventStrParam = NULL;
      while (nEventType = Adsorb_getEvent (&eventStrParam)) {
         switch (nEventType) {
         case adsorb_interstitial_ads_available:
            /* Ad interstitials available */
            break;

         case adsorb_interstitial_ad_dismissed:
            /* Ad interstitial closed */
            break;

         case adsorb_interstitial_ad_failed:
            /* Ad interstitial failed to present altogether */
            break;

         case adsorb_success_rewarded_ad_ready:
            /* Reward video ready */
            Adsorb_showRewardVideoAd(NULL, eventStrParam); /* Show loaded ad, for example */
            break;

         case adsorb_success_rewarded_ad_unavailable:
            /* No reward video is available */
            break;

         case adsorb_success_rewarded_ad_dismissed:
            /* Reward video closed */
            break;

         case adsorb_success_rewarded_ad_failed:
            /* Reward video failed to present altogether */
            break;

         case adsorb_success_reward_granted:
            /* Reward granted for reward video */
            break;

         case adsorb_helper_rewarded_ad_ready:
            /* Helper video ready */
            Adsorb_showHelperVideoAd(NULL, eventStrParam); /* Show loaded ad, for example */
            break;

         case adsorb_helper_rewarded_ad_unavailable:
            /* No helper video is available */
            break;

         case adsorb_helper_rewarded_ad_dismissed:
            /* Helper video closed */
            break;

         case adsorb_helper_rewarded_ad_failed:
            /* Helper video failed to present altogether */
            break;

         case adsorb_helper_reward_granted:
            /* Reward granted for helper video */
            break;

         case adsorb_neutral_rewarded_ad_ready:
            /* Neutral video ready */
            Adsorb_showNeutralVideoAd(NULL, eventStrParam); /* Show loaded ad, for example */
            break;

         case adsorb_neutral_rewarded_ad_unavailable:
            /* No neutral video is available */
            break;

         case adsorb_neutral_rewarded_ad_dismissed:
            /* Neutral video closed */
            break;

         case adsorb_neutral_rewarded_ad_failed:
            /* Neutral video failed to present altogether */
            break;

         case adsorb_neutral_reward_granted:
            /* Reward granted for neutral video */
            break;

         case adsorb_virtual_currency_granted:
            if (lpszEventStrParam) {
               int nAmount = atoi (lpszEventStrParam);

               /* Virtual currency granted (for instance 50 coins), when using networks that grant currency, and when your game supports it */
            }
            break;

         case adsorb_multiplayer_game_started:
            if (lpszEventStrParam) {
               int nSeed = atoi (lpszEventStrParam);

               /* Start a multiplayer game now. Take the player to the corresponding game board immediately.
                  The random seed is a shared value across all players of the game, you can use it to initialize a board or level to be identical for all players.
                  You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support */
            }
            break;

         case adsorb_multiplayer_game_ended:
            /* Multiplayer game ended. You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support */
            break;

         case adsorb_showmoregames_dismissed:
            /* "Show more games" browser dismissed */
            break;

         default:
            break;
         }
      }

11. User re-engagement

Adsorb provides a simple mechanism for arming a local notification. The user gets a notification from your game a day later. Users install many apps and may forget about yours; local notifications have been measured to increase re-engagement.

   /**
    * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
    * after a day
    * 
    * @param message message to display in the notification when it fires
    */
   extern void Adsorb_enableLocalNotification (const char *message);

   /**
    * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
    * after the specified delay
    * 
    * @param str_title title to display in the notification when it fires
    * @param str_message message to display in the notification when it fires
    * @param delaySeconds delay in seconds after which to fire the local notification (for instance, 86400 for one day)
    */
   extern void Adsorb_enableLocalNotificationWithDelay (const char *str_title, const char *str_message, int delaySeconds);

   /**
    * Disable showing a local notification
    */
   extern void Adsorb_disableLocalNotification (void);

   You can enable a local notification at the start of your game. Adsorb will automatically arm it when your game is paused and disarm it when your game is resumed. 

   Example:
   Adsorb_enableLocalNotification ("Come play with us!");

Adsorb also manages push notifications in order to announce new game releases and special offers. You can offer your users to opt in using a GUI, and call Adsorb_setPushNotificationsChannel() and Adsorb_enablePushNotifications() when they do. It is strongly recommended to offer push notifications so that we can announce and cross-market your future games to existing users.

   /**
    * Check if the device supports push notifications
    * 
    * @return true if push notifications are available, false if not
    */
   extern bool Adsorb_arePushNotificationsAvailable (void);

   /**
    * Set channel to use for push notifications for this game. This must be called before enablePushNotifications() or disablePushNotifications()
    * 
    * @param channel channel to use for push notifications, such as HiddenObject
    */
   extern void Adsorb_setPushNotificationsChannel (const char *channel);

   /**
    * Opt into receiving push notifications for cross-marketing purposes
    */
   extern void Adsorb_enablePushNotifications ();

   /**
    * Opt out of receiving push notifications for cross-marketing purposes
    */
   extern void Adsorb_disablePushNotifications ();

   /**
    * Check if the user is currently opted into push notifications
    * 
    * @return true if push notifications are enabled, false if they are disabled
    */
   extern bool Adsorb_arePushNotificationsEnabled ();

12. Premium games

Adsorb supports disabling the ads temporarily, for instance as a reward for an incented ad, and permanently, for instance as the result of an in-app purchase to turn them off. Use the suspendAdsForHours() method as described below.

   /**
    * Suspend loading and showing any ads for the specified number of hours. This can
    * be used as the reward of an incented ad for instance.
    *
    * @param hours number of hours to suspend all ads for (for instance 24),
    *              -1 to suspend permanently,
    *              0 to resume previously suspended ads
    */
   void Adsorb_suspendAdsForHours (int hours);

For instance, call Adsorb_suspendAdsForHours(24); to suspend ads for a day, and suspendAdsForHours (-1) to turn them off after an in-app purchase.

13. Support for placements

We strongly recommend that you let Adsorb know what screen the user is currently on. This information will be used for anonymously measuring the effectiveness of ads on each of your various game screens. Additionally, if you allow it, it can also be used to show overlay views on top of your game, for instance over the main menu, notably to increase the monetization of your game.

   /**
    * Tell Adsorb that the game is entering the specified screen
    * 
    * @param name screen name, such as: main_menu, instructions, level_select, playing, level_end, game_end
    * @param allow_overlays true to allow Adsorb to show monetization and marketing overlays on top of some of the game screens,
    *                       false to inform the current screen name only for event logging purposes
    */
   extern void Adsorb_enterGameScreen (const char *name, bool allow_overlays);
    
   /**
    * Tell Adsorb that the game is leaving the current screen
    */
   extern void Adsorb_leaveGameScreen (void);

14. Advanced rewarded ads support

Adsorb also provides a mode that automatically loads rewarded ads in the background. Instead of using loadSuccessAd(), loadHelperAd() and loadNeutralAd(), you may use the following methods to enable auto-fetching instead.
In this mode, you provide captions for success and helper ads for the entire game, once and for all, instead of being able to set it on each load.

   /**
    * Set success rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
    * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
    * rewarded ads that the game is interested in.
    * 
    * @param str_success_achievement achievement done by the player for success ads (such as "Level completed")
    * @param str_success_reward reward to be granted for success ads (such as "50 gold")
    * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
    *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
    */
   extern void Adsorb_setSuccessAdsAutoMode (const char *str_success_achievement, const char *str_success_reward,
    								                  bool use_game_gui);

   /**
    * Set helper rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
    * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
    * rewarded ads that the game is interested in.
    * 
    * @param str_helper_caption caption for helper ads (such as "Need more time?")
    * @param str_helper_enticement_text enticement text for helper ads (such as "Watch a short message and get an extra")
    * @param str_helper_reward reward for helper ads (such as "1:00")
    * @param str_helper_button_label button label for helper ads (such as "Tap to get more time")
    * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
    *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
    */
   extern void Adsorb_setHelperAdsAutoMode (const char *str_helper_caption, const char *str_helper_enticement_text,
    								                 const char *str_helper_reward, const char *str_helper_button_label,
    								                 bool use_game_gui);

   /**
    * Set neutral rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
    * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
    * rewarded ads that the game is interested in.
    * 
    * @param str_neutral_caption neutral offer's caption (such as "Unlock untimed mode")
    * @param str_neutral_body neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
    * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
    *                     receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
    */
   extern void Adsorb_setNeutralAdsAutoMode (const char *str_neutral_caption, const char *str_neutral_body, bool use_game_gui);

   /**
    * Complete the setup of automatically load rewarded ads and start loading in the background
    */
   extern void Adsorb_rewardedAdSetupComplete (void);

   Call these methods at the beginning of your game, always calling Adsorb_rewardedAdSetupComplete() last. You will subsequently receive "ready" and "unavailable" events for each kind of rewarded ad. It is up to your game to track whether each kind of ad is currently available, from receiving those events, and deciding when to show each kind of ad.

You may optionally want to receive information on the ad network that provides a success, helper and neutral rewarded ad, in order to show your own GUI and provide context for the rewarded ad to the user, or to react according to the type of ad being presented. The Adsorb connector provides methods to that end. These methods can be called after loading a rewarded ad and receiving the corresponding "ad ready" event for the rewarded ad type.

   /**
    * Get the name of the network providing the currently loaded success rewarded ad
    *
    * @param filter_name name of filter to get ad network name of (null for default)
    *
    * @return name
    */
   extern const char *Adsorb_getSuccessAdNetworkName (const char *filter_name);

   /**
    * Get the type of the currently loaded success rewarded ad (video, survey, ...)
    *
    * @param filter_name name of filter to get ad network type of (null for default)
    *
    * @return type
    */
   extern const char *Adsorb_getSuccessAdNetworkType (const char *filter_name);

   /**
    * Get the filter tags of the currently loaded success rewarded ad (long, highvalue, ...)
    * 
    * @param filter_name name of filter to get ad network tags of (null for default)
    * 
    * @return tags
    */
   extern const char *Adsorb_getSuccessAdNetworkTags (const char *filter_name);

   /**
    * Check if the currently loaded success rewarded ad provides its own GUI
    *
    * @param filter_name name of filter to get GUI mode of (null for default)
    * 
    * @return true if it does, false if the game has to provide it
    */
   extern bool Adsorb_doesSuccessAdProvideGUI (const char *filter_name);

   /**
    * Get the name of the network providing the currently loaded helper rewarded ad
    *
    * @param filter_name name of filter to get ad network name of (null for default)
    *
    * @return name
    */
   extern const char *Adsorb_getHelperAdNetworkName (const char *filter_name);

   /**
    * Get the type of the currently loaded helper rewarded ad (video, survey, ...)
    *
    * @param filter_name name of filter to get ad network type of (null for default)
    *
    * @return type
    */
   extern const char *Adsorb_getHelperAdNetworkType (const char *filter_name);

   /**
    * Get the filter tags of the currently loaded helper rewarded ad (long, highvalue, ...)
    * 
    * @param filter_name name of filter to get ad network tags of (null for default)
    * 
    * @return tags
    */
   extern const char *Adsorb_getHelperAdNetworkTags (const char *filter_name);

   /**
    * Check if the currently loaded helper rewarded ad provides its own GUI
    *
    * @param filter_name name of filter to get GUI mode of (null for default)
    * 
    * @return true if it does, false if the game has to provide it
    */
   extern bool Adsorb_doesHelperAdProvideGUI (const char *filter_name);

   /**
    * Get the name of the network providing the currently loaded neutral rewarded ad
    *
    * @param filter_name name of filter to get ad network name of (null for default)
    *
    * @return name
    */
   extern const char *Adsorb_getNeutralAdNetworkName (const char *filter_name);

   /**
    * Get the type of the currently loaded neutral rewarded ad (video, survey, ...)
    *
    * @param filter_name name of filter to get ad network type of (null for default)
    *
    * @return type
    */
   extern const char *Adsorb_getNeutralAdNetworkType (const char *filter_name);

   /**
    * Get the filter tags of the currently loaded neutral rewarded ad (long, highvalue, ...)
    * 
    * @param filter_name name of filter to get ad network tags of (null for default)
    * 
    * @return tags
    */
   extern const char *Adsorb_getNeutralAdNetworkTags (const char *filter_name);

   /**
    * Check if the currently loaded neutral rewarded ad provides its own GUI
    *
    * @param filter_name name of filter to get GUI mode of (null for default)
    * 
    * @return true if it does, false if the game has to provide it
    */
   extern bool Adsorb_doesNeutralAdProvideGUI (const char *filter_name);

Filtering rewarded ads by type

You may also request specific types of rewarded ads in various areas of your game, for instance to unlock additional features only by filling a highly-paid survey. You may request multiple filters for the same kind of ad (for instance neutral) in order to serve different rewards depending on the ads that are available. You will receive "ready", "unavailable", etc. events for each filter when you set up multiple ones. While you can change filter settings during your game, if you use multiple filters, it is better to set the filters at the beginning and keep them.

   /**
    * Only load success rewarded ads corresponding to the specified type
    * 
    * @param filter_name filter name (NULL for default)
    * @param filter_keywords filter keywords to apply to success rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   extern void Adsorb_filterSuccessAds (const char *filter_name, const char *filter_keywords);

   /**
    * Only load helper rewarded ads corresponding to the specified type
    * 
    * @param filter_name filter name (NULL for default)
    * @param filter_keywords filter keywords to apply to helper rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   extern void Adsorb_filterHelperAds (const char *filter_name, const char *filter_keywords);

   /**
    * Only load neutral rewarded ads corresponding to the specified type
    * 
    * @param filter_name filter name (NULL for default)
    * @param filter_keywords filter keywords to apply to neutral rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   extern void Adsorb_filterNeutralAds (const char *filter_name, const char *filter_keywords);

   Examples:

   Adsorb_filterSuccessAds (NULL, "video");
   Adsorb_filterNeutralAds (NULL, "survey");

   Multiple filters:

   Adsorb_filterNeutralAds ("filter1", "video long");
   Adsorb_filterNeutralAds ("filter2", "video short");

Adsorb also supports passing custom key-value pairs in json format to specific ad networks, if you wish to implement features specific to a network. Please see with your FGL producer about implementing these features.

   /**
    * Set custom extra variables for a network that provides success rewarded ads. The variables will be considered by the network the next
    * time a success rewarded ad is loaded.
    * 
    * @param str_network network name (for instance "mediabrix")
    * @param str_jsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
    */
   extern void Adsorb_setSuccessAdsVars (const char *str_network, const char *str_jsonVars);
	
   /**
    * Set custom extra variables for a network that provides helper rewarded ads. The variables will be considered by the network the next
    * time a helper rewarded ad is loaded.
    * 
    * @param str_network network name (for instance "mediabrix")
    * @param str_jsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
    */
   extern void Adsorb_setHelperAdsVars (const char *str_network, const char *str_jsonVars);

   /**
    * Set custom extra variables for a network that provides neutral rewarded ads. The variables will be considered by the network the next
    * time a neutral rewarded ad is loaded.
    * 
    * @param str_network network name
    * @param str_jsonVars custom variables in json format
    */
   extern void Adsorb_setNeutralAdsVars (const char *str_network, const char *str_jsonVars);

   Example:
   Adsorb_setSuccessAdsVars ("mediabrix", "{ \"icon\": \"http://www.mysite.com/icon.png\" } ");

15. Rewarded achievements

Adsorb also supports ad networks that boost user engagement by providing rewards when the game reports achievements, such at Lootsie. Use Adsorb_reportAchievement() to report events such as level_completed or timer (sent every few minutes, for instance every 2).

   /**
    * Report a completed achievement
    * 
    * @param achievement_name name of completed achievement
    */
   extern void Adsorb_reportAchievement (const char *achievement_name);

Networks that provide an incentive in exchange for the user doing something (such as installing a lockscreen, for example AppKey) are also supported by Adsorb. The game can check if the action has been performed and the reward granted, or prompt the user to perform the action.

   /**
    * Prompt user to perform an action to get an incentive
    * 
    * @param str_incentive_name incentive identifier
    * @param str_incentive_text what the user will get if the action is performed
    */
   extern void Adsorb_promptIncentive (const char *str_incentive_name, const char *str_incentive_text);

   /**
    * Check if an incentive's reward should currently be granted
    * 
    * @param str_incentive_name incentive identifier
    * 
    * @return true if the incentive's reward should currently be granted, false if not
    */
   extern bool Adsorb_isIncentiveRewardGranted (const char *str_incentive_name);

16. Affiliation

Your game may be eligible for affiliation, and receiving extra installs through our partners network. To this end, it must report any completed in-app purchases to Adsorb so that your game can benefit from the partner networks and additional installs.

Call Adsorb_reportPurchase() with the name of the purchased product once you have completed a purchase.

   /**
    * Report a completed in-app purchase
    * 
    * @param product_name name of purchased product
	 * @param product_price product price (such as 1.99)
    */
   extern void Adsorb_reportPurchase(const char *product_name, double product_price);

Your game may also be eligible for a different revenue split in the event that a user has been attributed to you, as negociated with your FGL producer. If such a deal is in place, you must always determine and report the user's source using Adsorb_reportAttribution(), and you must do it once per session. You must call the method even to indicate that the user isn't yours, otherwise you may not receive the correct amount of revenue.

   /**
    * Report user attribution for this session
    * 
    * @param str_vendor name of the vendor supplying the information
    * @param str_userType where the user came from
    */
   extern void Adsorb_reportAttribution(const char *str_vendor, const char *str_userType);

17. Multiplayer support

Adsorb facilitates score-based competition with online players, by connecting your games to social SDKs with a minimal amount of time needed for integration. If this feature is recommended by your producer at FGL, use the following methods to support online play.

   /**
    * Show the social feed and multiplayer challenges wall for this network
    */
   extern void Adsorb_showMultiplayerWall (void);

   /**
    * Report that the local player's current score has changed
    * 
    * @param score new score
    */
   extern void Adsorb_updateMultiplayerScore (int score);
	
   /**
    * Report that the multiplayer game is completed for the local player
    * 
    * @param score final score
    */
   extern void Adsorb_completeMultiplayerGame (int score);
	
   /**
    * Report that the multiplayer game has been canceled
    */
   extern void Adsorb_cancelMultiplayerGame (void);

You will also need to handle the adsorb_multiplayer_game_started and adsorb_multiplayer_game_ended events (see the chapter on events). You will receive adsorb_multiplayer_game_started when the local player engages with other players after calling Adsorb_showMultiplayerWall(). You will receive adsorb_multiplayer_game_ended as a confirmation that the multiplayer mode is finished, as a result of you signalling the completion or cancelation of a multiplayer game.

18. Native ad overlays

Adsorb also supports adding an ad overlay, in a rectangular area defined by the game. You may for instance use this feature to show an ad on your pause menu.

   /**
    * Check if an ad overlay is currently ready to show
    * 
    * @return true if an ad overlay is ready, false if not
    */
   extern bool Adsorb_isAdOverlayReady (void);
    
   /**
    * Show cross-promo ad overlay, if possible
    * 
    * @param x1 X coordinate of top, left of view, as a fraction of the display width (0..1)
    * @param y1 Y coordinate of top, left of view, as a fraction of the display height (0..1)
    * @param x2 X coordinate of bottom, right of view + 1, as a fraction of the display width (0..1)
    * @param y2 Y coordinate of bottom, right of view + 1, as a fraction of the display height (0..1)
    * @param anim_duration duration of the transition anims, in milliseconds
    * @param show_rectangle true to show the area requested by the game (paint it red), for debugging purposes
    */
   extern void Adsorb_showAdOverlay (float x1, float y1, float x2, float y2, int anim_duration, bool show_rectangle);

   /**
    * Refresh cross-promo ad overlay, if another ad is ready
    */
   extern void Adsorb_advanceAdOverlay (void);

   /**
    * Hide cross-promo ad overlay, if one was showing
    *
    * @param anim_duration duration of the transition anims, in milliseconds
    */
   extern void Adsorb_hideAdOverlay (int anim_duration);

## Conclusion

That's it! Adsorb will take care of the rest. Happy monetization!
