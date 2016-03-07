Cordova/Phonegap connector plugin for Adsorb

## What is Adsorb

Adsorb is FGL's unique monetization solution for your Android applications, that mediates interstitial ads using the highest paying networks, and also mediates incented videos for
an even higher payout. FGL constantly monitors the performance of the various networks and adjusts accordingly.

Adsorb uses a two-step process:
1) You integrate a small stub library in your application. Stub popups will appear when you request ads, and you can test granting rewards in your game as well.
2) FGL will wrap your game using Adsorb and integrate the real ad SDKs into your game. When new high-paying ad networks emerge, or when performance shifts, FGL can update the ads sdk without having you rebuild the app, and react in near-realtime to new market opportunities.

This approach has helped FGL become one of the most widely respected and highest paying solutions for free ad-based games on Android.

## Integration with your Cordova app for Android

1. Add the Adsorb plugin to your Cordova/Phonegap app project, for instance with the following command line:

   phonegap local plugin add {PATH/TO/FOLDER...}/cordova_plugin

2. Create an instance of Adsorb and listen for events, during your game initialization. For instance in index.js:

   // Declared outside of app
   var adsorb;

   Then:

   bindEvents: function() {
      ...

      // Register for Adsorb events
      window.addEventListener('onInterstitialAdsAvailable', this.onInterstitialAdsAvailable, false);
      window.addEventListener('onInterstitialAdDismissed', this.onInterstitialAdDismissed, false);
      window.addEventListener('onInterstitialAdFailed', this.onInterstitialAdFailed, false);
      window.addEventListener('onSuccessRewardedAdReady', this.onSuccessRewardedAdReady, false);
      window.addEventListener('onSuccessRewardedAdUnavailable', this.onSuccessRewardedAdUnavailable, false);
      window.addEventListener('onSuccessRewardedAdDismissed', this.onSuccessRewardedAdDismissed, false);
      window.addEventListener('onSuccessRewardedAdFailed', this.onSuccessRewardedAdFailed, false);
      window.addEventListener('onSuccessRewardGranted', this.onSuccessRewardGranted, false);
      window.addEventListener('onHelperRewardedAdReady', this.onHelperRewardedAdReady, false);
      window.addEventListener('onHelperRewardedAdUnavailable', this.onHelperRewardedAdUnavailable, false);
      window.addEventListener('onHelperRewardedAdDismissed', this.onHelperRewardedAdDismissed, false);
      window.addEventListener('onHelperRewardedAdFailed', this.onHelperRewardedAdFailed, false);
      window.addEventListener('onHelperRewardGranted', this.onHelperRewardGranted, false);
      window.addEventListener('onNeutralRewardedAdReady', this.onNeutralRewardedAdReady, false);
      window.addEventListener('onNeutralRewardedAdUnavailable', this.onNeutralRewardedAdUnavailable, false);
      window.addEventListener('onNeutralRewardedAdDismissed', this.onNeutralRewardedAdDismissed, false);
      window.addEventListener('onNeutralRewardedAdFailed', this.onNeutralRewardedAdFailed, false);
      window.addEventListener('onNeutralRewardGranted', this.onNeutralRewardGranted, false);
      window.addEventListener('onVirtualCurrencyGranted', this.onVirtualCurrencyGranted, false);
      window.addEventListener('onMultiplayerGameStarted', this.onMultiplayerGameStarted, false);
      window.addEventListener('onMultiplayerGameEnded', this.onMultiplayerGameEnded, false);
      window.addEventListener('onShowMoreGamesDismissed', this.onShowMoreGamesDismissed, false);

      ...
   },

   and:

   onDeviceReady: function() {
      ...

      // Create instance of Adsorb
      adsorb = new Adsorb ();

      ...
   },

3. Implement the corresponding event handlers, for instance:

    // React to Adsorb events
   
    onInterstitialAdsAvailable: function() {
         // Interstitial ads available
    },
   
    onInterstitialAdDismissed: function() {
         // Interstitial ad dismissed
    },
   
    onInterstitialAdFailed: function() {
			// Interstitial ad failed to present altogether
    },
   
    onSuccessRewardedAdReady: function(filter) {
			// Success rewarded ad loaded and ready to show
    },
      
    onSuccessRewardedAdUnavailable: function(filter) {
			// Success rewarded ad unavailable
    },
      
    onSuccessRewardedAdDismissed: function(filter) {
			// Success rewarded ad dismissed
    },
      
    onSuccessRewardedAdFailed: function(filter) {
			// Success rewarded ad failed to show altogether
    },
      
    onSuccessRewardGranted: function(filter) {
         // Grant reward to user for watching success ad
    },
   
    onHelperRewardedAdReady: function(filter) {
			// Helper rewarded ad loaded and ready to show
    },
      
    onHelperRewardedAdUnavailable: function(filter) {
			// Helper rewarded ad unavailable
    },
      
    onHelperRewardedAdDismissed: function(filter) {
			// Helper rewarded ad dismissed
    },
      
    onHelperRewardedAdFailed: function(filter) {
			// Helper rewarded ad failed to show altogether
    },
      
    onHelperRewardGranted: function(filter) {
         // Grant reward to user for watching helper ad
    },
   
    onNeutralRewardedAdReady: function(filter) {
			// Neutral rewarded ad loaded and ready to show
    },
      
    onNeutralRewardedAdUnavailable: function(filter) {
			// Neutral rewarded ad unavailable
    },
      
    onNeutralRewardedAdDismissed: function(filter) {
			// Neutral rewarded ad dismissed
    },
      
    onNeutralRewardedAdFailed: function(filter) {
			// Neutral rewarded ad failed to show altogether
    },
      
    onNeutralRewardGranted: function(filter) {
         // Grant reward to user for watching neutral ad
    },
   
    onVirtualCurrencyGranted: function(amount) {
			// Virtual currency granted; the amount is in 'amount'
    },
   
    onMultiplayerGameStarted: function(randomSeed) {
			// Start a multiplayer game now; the random seed is in 'randomSeed'

         // Take the player to the corresponding game board immediately.
         // The random seed is a shared value across all players of the game, you can use it to initialize a board or level to be identical for all players
         // You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support
    },
   
    onMultiplayerGameEnded: function() {
         // End a multiplayer game now. You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support
    },
      
    onShowMoreGamesDismissed: function() {
			// Show more games browser dismissed
    }

6. You can now show ads, reward and helper videos using the following API on the 'adsorb' object you created in the onDeviceReady function:

      /**
       * Show interstitial ad
       */
      adsorb.showInterstitialAd ();

      /**
       * Load success rewarded ad
       * 
       * @param strAchievement achievement done by the player (such as "Level completed")
       * @param strReward reward to be granted (such as "50 gold")
       * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
       *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
       */
      adsorb.loadSuccessAd (strAchievement, strReward, bUseGameGUI);

      /**
       * Show success rewarded ad
       * 
       * @param tag optional tag for tracking performance of individual ads, can be null
       * @param filterName name of filter to show success ad for (null for default)
       */
      adsorb.showSuccessAd (tag, filterName);

      /**
       * Load helper rewarded ad
       * 
       * @param strCaption caption (such as "Need more time?")
       * @param strEnticementText enticement text (such as "Watch a short message and get an extra")
       * @param strReward reward (such as "1:00")
       * @param strButtonLabel button label (such as "Tap to get more time")
       * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
       *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
       */
      adsorb.loadHelperAd (strCaption, strEnticementText, strReward, strButtonLabel, bUseGameGUI);

      /**
       * Show helper rewarded ad
       * 
       * @param tag optional tag for tracking performance of individual ads, can be null
       * @param filterName name of filter to show success ad for (null for default)
       */
      adsorb.showHelperAd (tag, filterName);

      /**
       * Load neutral rewarded ad
       *
       * @param strCaption neutral offer's caption (such as "Unlock untimed mode")
       * @param strBody neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
       * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
       *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
       */
      adsorb.loadNeutralAd (strCaption, strBody, bUseGameGUI);

      /**
       * Show neutral rewarded ad
       * 
       * @param tag optional tag for tracking performance of individual ads, can be null
       * @param filterName name of filter to show success ad for (null for default)
       */
      adsorb.showNeutralAd (tag, filterName);

		/**
		 * Show newsletter subscription
		 */
		adsorb.showNewsletter();

		/**
		 * Show more games browser
		 */
		adsorb.showMoreGames();

		/**
		 * Show actually free games page
		 */
		adsorb.showActuallyFreeGames();

      For example:

      adsorb.showInterstitialAd ();
      adsorb.loadSuccessAd ("Level up!", "135 gems", false);
      adsorb.showSuccessAd (null, null);
      adsorb.loadHelperAd ("Need more moves?", "Watch a short message and get an extra", "7 moves", "Tap to get moving", false);
      adsorb.showHelperAd (null, null);
      adsorb.loadNeutralAd ("Unlock untimed mode", "Watch an offer in a minute or less and unlock untimed mode!", false);
      adsorb.showNeutralAd (null, null);
      adsorb.showNewsletter ();
      adsorb.showMoreGames ();
      adsorb.showActuallyFreeGames ();

7. User re-engagement

Adsorb provides a simple mechanism for arming a local notification. The user gets a notification from your game a day later. Users install many apps and may forget about yours; local notifications have been measured to increase re-engagement.

   /**
    * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
    * after a day
    * 
    * @param message message to display in the notification when it fires
    */
   adsorb.enableLocalNotification (message);
    
   /**
    * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
    * after the specified delay
    *
    * @param title title to display in the notification when it fires
    * @param message message to display in the notification when it fires
    * @param delaySeconds delay in seconds after which to fire the local notification (for instance, 86400 for one day)
    */
   adsorb.enableLocalNotificationWithDelay (title, message, delaySeconds);

   /**
    * Disable showing a local notification
    */
   adsorb.disableLocalNotification ();

   You can enable a local notification at the start of your game. Adsorb will automatically arm it when your game is paused and disarm it when your game is resumed. 

   Example:
   adsorb.enableLocalNotification ("Come play with us!");

Adsorb also manages push notifications in order to announce new game releases and special offers. You can offer your users to opt in using a GUI, and call setPushNotificationsChannel() and enablePushNotifications() when they do. It is strongly recommended to offer push notifications so that we can announce and cross-market your future games to existing users.

   /**
    * Check if the device supports push notifications
    * 
    * @param resultCallback function that receives the result: boolean, true if push notifications are available, false if not
    */
   adsorb.arePushNotificationsAvailable (resultCallback);

   /**
    * Set channel to use for push notifications for this game. This must be called before enablePushNotifications() or disablePushNotifications()
    * 
    * @param channel channel to use for push notifications, such as HiddenObject
    */
   adsorb.setPushNotificationsChannel (channel);
    
   /**
    * Opt into receiving push notifications for cross-marketing purposes
    */
   adsorb.enablePushNotifications ();
    
   /**
    * Opt out of receiving push notifications for cross-marketing purposes
    */
   adsorb.disablePushNotifications ();

   /**
    * Check if the user is currently opted into push notifications
    * 
    * @param resultCallback function that receives the result: boolean, true if push notifications are enabled, false if they are disabled
    */
   adsorb.arePushNotificationsEnabled (resultCallback);

8. Premium games

Adsorb supports disabling the ads temporarily, for instance as a reward for an incented ad, and permanently, for instance as the result of an in-app purchase to turn them off. Use the suspendAdsForHours() method as described below.

   /**
    * Suspend loading and showing any ads for the specified number of hours. This can
    * be used as the reward of an incented ad for instance.
    * 
    * @param hours number of hours to suspend all ads for (for instance 24),
    *              -1 to suspend permanently, 
    *              0 to resume previously suspended ads
    */
   adsorb.suspendAdsForHours (hours);

For instance, call adsorb.suspendAdsForHours(24); to suspend ads for a day, and suspendAdsForHours (-1) to turn them off after an in-app purchase.

9. Support for placements

We strongly recommend that you let Adsorb know what screen the user is currently on. This information will be used for anonymously measuring the effectiveness of ads on each of your various game screens. Additionally, if you allow it, it can also be used to show overlay views on top of your game, for instance over the main menu, notably to increase the monetization of your game.

	/**
	 * Tell Adsorb that the game is entering the specified screen
	 * 
	 * @param name screen name, such as: main_menu, instructions, level_select, playing, level_end, game_end
	 * @param bAllowOverlays true to allow Adsorb to show monetization and marketing overlays on top of some of the game screens,
	 *                       false to inform the current screen name only for event logging purposes
    */
   adsorb.enterGameScreen (name, bAllowOverlays);
   
	/**
    * Tell Adsorb that the game is leaving the current screen
	 */
   adsorb.leaveGameScreen ();

10. Advanced rewarded ads support

Adsorb also provides a mode that automatically loads rewarded ads in the background. Instead of using loadSuccessAd(), loadHelperAd() and loadNeutralAd(), you may use the following methods to enable auto-fetching instead.
In this mode, you provide captions for success and helper ads for the entire game, once and for all, instead of being able to set it on each load.

	/**
	 * Set success rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
	 * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
	 * rewarded ads that the game is interested in.
	 * 
	 * @param strSuccessAchievement achievement done by the player for success ads (such as "Level completed")
	 * @param strSuccessReward reward to be granted for success ads (such as "50 gold")
	 * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
	 *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
	adsorb.setSuccessAdsAutoMode (strSuccessAchievement, strSuccessReward, bUseGameGUI);

	/**
	 * Set helper rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
	 * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
	 * rewarded ads that the game is interested in.
	 * 
	 * @param strHelperCaption caption for helper ads (such as "Need more time?")
	 * @param strHelperEnticementText enticement text for helper ads (such as "Watch a short message and get an extra")
	 * @param strHelperReward reward for helper ads (such as "1:00")
	 * @param strHelperButtonLabel button label for helper ads (such as "Tap to get more time")
	 * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
	 *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
	adsorb.setHelperAdsAutoMode (strHelperCaption, strHelperEnticementText, strHelperReward, strHelperButtonLabel, bUseGameGUI);

	/**
	 * Set neutral rewarded ads to be automatically loaded in the background. The game will receive ready and unavailable ads as if using
	 * the manual load methods. The game must call rewardedAdSetupComplete() once automatic loading is set up for every kind of
	 * rewarded ads that the game is interested in.
	 * 
    * @param strNeutralCaption neutral offer's caption (such as "Unlock untimed mode")
    * @param strNeutralBody neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
	 * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
	 *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
	adsorb.setNeutralAdsAutoMode (strNeutralCaption, strNeutralBody, bUseGameGUI);
   
	/**
	 * Complete the setup of automatically load rewarded ads and start loading in the background
	 */
	adsorb.rewardedAdSetupComplete ();

   Call these methods at the beginning of your game, always calling adsorb.rewardedAdSetupComplete() last. You will subsequently receive "ready" and "unavailable" events for each kind of rewarded ad. It is up to your game to track whether each kind of ad is currently available, from receiving those events, and deciding when to show each kind of ad.

Also, you may optionally want to receive information on the ad network that provides a success, helper and neutral rewarded ad, in order to show your own GUI and provide context for the rewarded ad to the user, or to react according to the type of ad being presented. The Adsorb connector provides methods to that end. These methods can be called after loading a rewarded ad and receiving the corresponding "ad ready" event for the rewarded ad type.

   /**
    * Get the name of the network providing the currently loaded success rewarded ad
    * 
    * @param filterName name of filter to get ad network name of (null for default)
    * @param resultCallback function that receives the result: network name
    */
   adsorb.getSuccessAdNetworkName (filterName, resultCallback);
		
   /**
    * Get the type of the currently loaded success rewarded ad (video, survey, ...)
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    * @param resultCallback function that receives the result: network type
    */
   adsorb.getSuccessAdNetworkType (filterName, resultCallback);
		
   /**
    * Get the filter tags of the currently loaded success rewarded ad (long, high_value, ...)
    * 
    * @param filterName name of filter to get ad network tags of (null for default)
    * @param resultCallback function that receives the result: network tags
    */
   adsorb.getSuccessAdNetworkTags (filterName, resultCallback);

   /**
    * Check if the currently loaded success rewarded ad provides its own GUI
    * 
    * @param filterName name of filter to get GUI mode of (null for default)
    * @param resultCallback function that receives the result: boolean, true if the network does, false if the game has to provide it
    */
   adsorb.doesSuccessAdProvideGUI (filterName, resultCallback);

   /**
    * Get the name of the network providing the currently loaded helper rewarded ad
    * 
    * @param filterName name of filter to get ad network name of (null for default)
    * @param resultCallback function that receives the result: network name
    */
   adsorb.getHelperAdNetworkName (filterName, resultCallback);
		
   /**
    * Get the type of the currently loaded helper rewarded ad (video, survey, ...)
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    * @param resultCallback function that receives the result: network type
    */
   adsorb.getHelperAdNetworkType (filterName, resultCallback);
		
   /**
    * Get the filter tags of the currently loaded helper rewarded ad (long, high_value, ...)
    * 
    * @param filterName name of filter to get ad network tags of (null for default)
    * @param resultCallback function that receives the result: network tags
    */
   adsorb.getHelperAdNetworkTags (filterName, resultCallback);

   /**
    * Check if the currently loaded helper rewarded ad provides its own GUI
    * 
    * @param filterName name of filter to get GUI mode of (null for default)
    * @param resultCallback function that receives the result: boolean, true if the network does, false if the game has to provide it
    */
   adsorb.doesHelperAdProvideGUI (filterName, resultCallback);

   /**
    * Get the name of the network providing the currently loaded neutral rewarded ad
    * 
    * @param filterName name of filter to get ad network name of (null for default)
    * @param resultCallback function that receives the result: network name
    */
   adsorb.getNeutralAdNetworkName (filterName, resultCallback);
		
   /**
    * Get the type of the currently loaded neutral rewarded ad (video, survey, ...)
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    * @param resultCallback function that receives the result: network type
    */
   adsorb.getNeutralAdNetworkType (filterName, resultCallback);
		
   /**
    * Get the filter tags of the currently loaded neutral rewarded ad (long, high_value, ...)
    * 
    * @param filterName name of filter to get ad network tags of (null for default)
    * @param resultCallback function that receives the result: network tags
    */
   adsorb.getNeutralAdNetworkTags (filterName, resultCallback);

   /**
    * Check if the currently loaded neutral rewarded ad provides its own GUI
    * 
    * @param filterName name of filter to get GUI mode of (null for default)
    * @param resultCallback function that receives the result: boolean, true if the network does, false if the game has to provide it
    */
   adsorb.doesNeutralAdProvideGUI (filterName, resultCallback);

Filtering rewarded ads by type

You may also request specific types of rewarded ads in various areas of your game, for instance to unlock additional features only by filling a highly-paid survey. You may request multiple filters for the same kind of ad (for instance neutral) in order to serve different rewards depending on the ads that are available. You will receive "ready", "unavailable", etc. events for each filter when you set up multiple ones. While you can change filter settings during your game, if you use multiple filters, it is better to set the filters at the beginning and keep them.

   /**
    * Filter success rewarded ads using keywords
    *
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to success rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   adsorb.filterSuccessAds(filterName, filterKeywords);
		
   /**
    * Filter helper rewarded ads using keywords
    * 
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to helper rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   adsorb.filterHelperAds(filterName, filterKeywords);
		
   /**
    * Filter neutral rewarded ads using keywords
    * 
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to neutral rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   adsorb.filterNeutralAds(filterName, filterKeywords);	

   Examples:

   adsorb.filterSuccessAds (null, "video");
   adsorb.filterNeutralAds (null, "survey");

   Multiple filters:

   adsorb.filterNeutralAds ("filter1", "video long");
   adsorb.filterNeutralAds ("filter2", "video short");

   The filter that triggered a ready, unavailable, dismissed, failed or granted event for a rewarded ad, is set in the e.param variable of the received events. This filter name can be used to differenciate between the multiple ad opportunities that you set your game up to filter for, and to show the relevant ad, as the show methods also take the filter name.

Adsorb also supports passing custom key-value pairs in json format to specific ad networks, if you wish to implement features specific to a network. Please see with your FGL producer about implementing these features.

	/**
	 * Set custom extra variables for a network that provides success rewarded ads. The variables will be considered by the network the next
	 * time a success rewarded ad is loaded.
	 * 
	 * @param strNetwork network name (for instance "mediabrix")
	 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
	 */
	adsorbsetSuccessAdsVars (strNetwork, strJsonVars);
	
	/**
	 * Set custom extra variables for a network that provides helper rewarded ads. The variables will be considered by the network the next
	 * time a helper rewarded ad is loaded.
	 * 
	 * @param strNetwork network name (for instance "mediabrix")
	 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
	 */
	adsorbvoid setHelperAdsVars (strNetwork, strJsonVars);

	/**
	 * Set custom extra variables for a network that provides neutral rewarded ads. The variables will be considered by the network the next
	 * time a neutral rewarded ad is loaded.
	 * 
	 * @param strNetwork network name
	 * @param strJsonVars custom variables in json format
	 */
	adsorbsetNeutralAdsVars (strNetwork, strJsonVars);

   Example:
   adsorb.setSuccessAdsVars ("mediabrix", "{ \"icon\": \"http://www.mysite.com/icon.png\" } ");

11. Rewarded achievements

Adsorb also supports ad networks that boost user engagement by providing rewards when the game reports achievements, such at Lootsie. Use adsorb.reportAchievement() to report events such as level_completed or timer (sent every few minutes, for instance every 2).

	/**
    * Report a completed achievement
    * 
    * @param achievementName name of completed achievement
    */
	adsorb.reportAchievement (achievementName) : void;

Networks that provide an incentive in exchange for the user doing something (such as installing a lockscreen, for example AppKey) are also supported by Adsorb. The game can check if the action has been performed and the reward granted, or prompt the user to perform the action.

	/**
	 * Prompt user to perform an action to get an incentive
	 * 
	 * @param incentiveName incentive identifier
	 * @param incentiveText what the user will get if the action is performed
	 */
	adsorb.promptIncentive (incentiveName, incentiveText);

   /**
    * Check if an incentive's reward should currently be granted
    * 
    * @param incentiveName incentive identifier
    * @param resultCallback function that receives the result: boolean, true if the incentive's reward should currently be granted, false if not
    */
   adsorb.isIncentiveRewardGranted (incentiveName, resultCallback);

12. Affiliation

Your game may be eligible for affiliation, and receiving extra installs through our partners network. To this end, it must report any completed in-app purchases to Adsorb so that your game can benefit from the partner networks and additional installs.

Call adsorb.reportPurchase() with the name of the purchased product once you have completed a purchase.

   /**
    * Report a completed in-app purchase
    * 
    * @param productName name of purchased product
    * @param productPrice product price (such as 1.99)
    */
	adsorb.reportPurchase (productName, productPrice);

Your game may also be eligible for a different revenue split in the event that a user has been attributed to you, as negociated with your FGL producer. If such a deal is in place, you must always determine and report the user's source using adsorb.reportAttribution(), and you must do it once per session. You must call the method even to indicate that the user isn't yours, otherwise you may not receive the correct amount of revenue.

	/**
	 * Report user attribution for this session
	 * 
	 * @param vendor name of the vendor supplying the information
	 * @param userType where the user came from
	 */
	adsorb.reportAttribution (vendor, userType);

13. Multiplayer support

Adsorb facilitates score-based competition with online players, by connecting your games to social SDKs with a minimal amount of time needed for integration. If this feature is recommended by your producer at FGL, use the following methods to support online play.

	/**
	 * Show the social feed and multiplayer challenges wall for this network
	 */
	adsorb.showMultiplayerWall ();

	/**
	 * Report that the local player's current score has changed
	 * 
	 * @param score new score
	 */
	adsorb.updateMultiplayerScore (score);

	/**
	 * Report that the multiplayer game is completed for the local player
	 * 
	 * @param score final score
	 */
	adsorb.completeMultiplayerGame (score);

	/**
	 * Report that the multiplayer game has been canceled
	 */
	adsorb.cancelMultiplayerGame ();

You will also need to handle the onMultiplayerGameStarted and onMultiplayerGameEnded events (see the chapter on events). You will receive onMultiplayerGameStarted when the local player engages with other players after calling adsorb.showMultiplayerWall(). You will receive onMultiplayerGameEnded() as a confirmation that the multiplayer mode is finished, as a result of you signalling the completion or cancelation of a multiplayer game.

14. Native ad overlays

   /**
    * Check if an ad overlay is currently ready to show
    *
    * @param resultCallback function that receives the result: true if an ad overlay is ready, false if not
    */
   adsorb.isAdOverlayReady (resultCallback);

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
   adsorb.showAdOverlay (x1, y1, x2, y2, nAnimDuration, bShowRectangle);

   /**
    * Refresh cross-promo ad overlay, if another ad is ready
    */
   adsorb.advanceAdOverlay ();

   /**
    * Hide cross-promo ad overlay, if one was showing
    *
    * @param nAnimDuration duration of the transition anims, in milliseconds
    */
   adsorb.hideAdOverlay (nAnimDuration);

Adsorb also supports adding an ad overlay, in a rectangular area defined by the game. You may for instance use this feature to show an ad on your pause menu.

15. Event reporting

You may also report arbitrary game events through Adsorb. These events will be collected alongside other automatically generated events for tracking the performance of your ads and app installs for example.

	/**
	 * Report game event
	 * 
	 * @param eventName event name
	 */
	adsorb.reportGameEvent (eventName) : void;

## Conclusion

You can refer to the supplied Cordova/Phonegap demo project for further information and for a full sample implementation of the plugin.

That's it! Adsorb will take care of the rest. Happy monetization!
