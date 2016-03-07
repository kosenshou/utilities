/*
 * JNI interface for the Adsorb connector - definitions
 */

#ifndef _ADSORBNATIVE_H

#ifdef __cplusplus
extern "C" {
#endif

/** Adsorb events */
typedef enum {
   /** No event */
   adsorb_none = 0,

	/**
	 * An interstitial ad requested with showInterstitialAd() has been displayed and the host app can resume
	 */
	adsorb_interstitial_ad_dismissed = 1,
	
	/**
	 * A success rewarded ad was dismissed
	 */
	adsorb_success_rewarded_ad_dismissed,
	   
	/**
	 * The reward for a success ad is granted
	 */
	adsorb_success_reward_granted,
	   
	/**
	 * The reward for a helper ad is granted
	 */
	adsorb_helper_reward_granted,

   /**
    * An interstitial ad failed to present altogether
    */
   adsorb_interstitial_ad_failed,

	/**
	 * A success rewarded ad failed to present altogether
	 */
	adsorb_success_rewarded_ad_failed,

	/**
	 * The "show more games" browser has been dismissed
	 */
   adsorb_interstitial_ads_available,

	/**
	 * The "show more games" browser has been dismissed
	 */
   adsorb_showmoregames_dismissed,

	/**
	 * A helper rewarded ad was dismissed
	 */
	adsorb_helper_rewarded_ad_dismissed,

	/**
	 * A helper rewarded ad failed to present altogether
	 */
	adsorb_helper_rewarded_ad_failed,

	/**
	 * A success rewarded ad is loaded and ready to show
	 */
	adsorb_success_rewarded_ad_ready,

	/**
	 * No success rewarded ad is available
	 */
	adsorb_success_rewarded_ad_unavailable,

   /**
	 * A helper rewarded ad is loaded and ready to show
	 */
	adsorb_helper_rewarded_ad_ready,	   

	/**
	 * No helper rewarded ad is ready to show
	 */
	adsorb_helper_rewarded_ad_unavailable,

   /**
	 * A neutral rewarded ad is loaded and ready to show
	 */
	adsorb_neutral_rewarded_ad_ready,

	/**
	 * No neutral rewarded ad is ready to show
	 */
	adsorb_neutral_rewarded_ad_unavailable,

	/**
	 * A neutral rewarded ad was dismissed
	 */
	adsorb_neutral_rewarded_ad_dismissed,

	/**
	 * A neutral rewarded ad failed to present altogether
	 */
	adsorb_neutral_rewarded_ad_failed,

	/**
	 * The reward for a neutral ad is granted
	 */
	adsorb_neutral_reward_granted,

   /**
    * Virtual currency granted
    */
   adsorb_virtual_currency_granted,

	/**
	 * Start a multiplayer game now
	 */
   adsorb_multiplayer_game_started,

	/**
	 * End a multiplayer game now
	 */
   adsorb_multiplayer_game_ended,
} adsorb_event_t;

/**
 * Get event sent by Adsorb, if any
 *
 * @param str_param pointer to where to store pointer to additional string parameter (will be set to NULL if there is none)
 *
 * @return received event, adsorb_none for none
 */
extern int Adsorb_getEvent (const char **str_param);

/**
 * Show interstitial ad
 */
extern void Adsorb_showInterstitialAd (void);

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

/**
 * Load success rewarded ad
 * 
 * @param str_achievement achievement done by the player (such as "Level completed")
 * @param str_reward reward to be granted (such as "50 gold")
 * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
 *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
 */
extern void Adsorb_loadSuccessAd (const char *str_achievement, const char *str_reward, bool use_game_gui);

/**
 * Show success rewarded ad
 * 
 * @param tag optional tag for tracking performance of individual ads, can be NULL
 * @param filter_name name of filter to show success ad for (NULL for default)
 */
extern void Adsorb_showSuccessAd (const char *tag, const char *filter_name);

/**
 * Load helper rewarded ad
 * 
 * @param str_caption caption (such as "Need more time?")
 * @param str_enticement_text enticement text (such as "Watch a short message and get an extra")
 * @param str_reward reward (such as "1:00")
 * @param str_button_label button label (such as "Tap to get more time")
 * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
 *                     receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
 */
extern void Adsorb_loadHelperAd (const char *str_caption, const char *str_enticement_text, const char *str_reward, const char *str_button_label,
                                 bool use_game_gui);

/**
 * Show helper rewarded ad
 * 
 * @param tag optional tag for tracking performance of individual ads, can be NULL
 * @param filter_name name of filter to show helper ad for (NULL for default)
 */
extern void Adsorb_showHelperAd (const char *tag, const char *filter_name);

/**
 * Load neutral rewarded ad
 *
 * @param str_caption neutral offer's caption (such as "Unlock untimed mode")
 * @param str_body neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
 * @param use_game_gui true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
 *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
 */
extern void Adsorb_loadNeutralAd (const char *str_caption, const char *str_body, bool use_game_gui);

/**
 * Show neutral rewarded ad
 * 
 * @param tag optional tag for tracking performance of individual ads, can be NULL
 * @param filter_name name of filter to show neutral ad for (NULL for default)
 */
extern void Adsorb_showNeutralAd (const char *tag, const char *filter_name);

/**
 * Only load success rewarded ads corresponding to the specified type
 * 
 * @param filter_name filter name (NULL for default)
 * @param filter_keywords filter keywords to apply to success rewarded ads (such as 'video high_value'), or null to remove the filter
 */
extern void Adsorb_filterSuccessAds (const char *filter_name, const char *filter_keywords);

/**
 * Only load helper rewarded ads corresponding to the specified type
 * 
 * @param filter_name filter name (NULL for default)
 * @param filter_keywords filter keywords to apply to helper rewarded ads (such as 'video high_value'), or null to remove the filter
 */
extern void Adsorb_filterHelperAds (const char *filter_name, const char *filter_keywords);

/**
 * Only load neutral rewarded ads corresponding to the specified type
 * 
 * @param filter_name filter name (NULL for default)
 * @param filter_keywords filter keywords to apply to neutral rewarded ads (such as 'video high_value'), or null to remove the filter
 */
extern void Adsorb_filterNeutralAds (const char *filter_name, const char *filter_keywords);

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
 * Get the filter tags of the currently loaded success rewarded ad (long, high_value, ...)
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
 * Get the filter tags of the currently loaded helper rewarded ad (long, high_value, ...)
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
 * Get the filter tags of the currently loaded neutral rewarded ad (long, high_value, ...)
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
   
/**
 * Report a completed achievement
 * 
 * @param achievement_name name of completed achievement
 */
extern void Adsorb_reportAchievement (const char *achievement_name);

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

/**
 * Report a completed in-app purchase
 * 
 * @param product_name name of purchased product
 * @param product_price product price (such as 1.99)
 */
extern void Adsorb_reportPurchase(const char *product_name, double product_price);	

/**
 * Report user attribution for this session
 * 
 * @param str_vendor name of the vendor supplying the information
 * @param str_userType where the user came from
 */
extern void Adsorb_reportAttribution(const char *str_vendor, const char *str_userType);

/**
 * Show more games browser
 */
extern void Adsorb_showMoreGames (void);

/**
 * Show newsletter subscription
 */
extern void Adsorb_showNewsletter ();

/**
 * Show actually free games page
 */
extern void Adsorb_showActuallyFreeGames ();

/**
 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
 * after a day
 * 
 * @param str_message message to display in the notification when it fires
 */
extern void Adsorb_enableLocalNotification (const char *str_message);

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

/**
 * Report event as a screen view
 * 
 * @param eventName event name
 */
extern void Adsorb_reportGameEvent (const char *eventName);

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
 *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
 */
extern void Adsorb_setNeutralAdsAutoMode (const char *str_neutral_caption, const char *str_neutral_body, bool use_game_gui);

/**
 * Complete the setup of automatically load rewarded ads and start loading in the background
 */
extern void Adsorb_rewardedAdSetupComplete (void);
    
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

#ifdef __cplusplus
}
#endif

#endif /* _ADSORBNATIVE_H */
