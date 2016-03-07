AIR Native Extension (ANE) library for Adsorb

## What is Adsorb

Adsorb is FGL's unique monetization solution for your Android applications, that mediates interstitial ads using the highest paying networks, and also mediates incented videos for
an even higher payout. FGL constantly monitors the performance of the various networks and adjusts accordingly.

Adsorb uses a two-step process:
1) You integrate a small stub library in your application. Stub popups will appear when you request ads, and you can test granting rewards in your game as well.
2) FGL will wrap your game using Adsorb and integrate the real ad SDKs into your game. When new high-paying ad networks emerge, or when performance shifts, FGL can update the ads sdk without having you rebuild the app, and react in near-realtime to new market opportunities.

This approach has helped FGL become one of the most widely respected and highest paying solutions for free ad-based games on Android.

## Integration with your Adobe AIR app for Android

1. Add the AdsorbExtension to your AIR app project. The .ane and .swc files are provided in this folder

2. Call a function in your game initialization to setup Adsorb and listen for events, for instance:

		static private var adsorb:Adsorb;
		static private var eventDispatcher:EventDispatcher;

		static public function initialize():void {
			// Get Adsorb SDK
			adsorb = Adsorb.getInstance();

        	// Listen for ad events
			eventDispatcher = new EventDispatcher ();
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_INTERSTITIAL_ADS_AVAILABLE, onInterstitialAdsAvailable);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_INTERSTITIAL_AD_DISMISSED, onInterstitialAdDismissed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_INTERSTITIAL_AD_FAILED, onInterstitialAdFailed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_SUCCESS_REWARDED_AD_READY, onSuccessRewardedAdReady);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_SUCCESS_REWARDED_AD_UNAVAILABLE, onSuccessRewardedAdUnavailable);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_SUCCESS_REWARDED_AD_DISMISSED, onSuccessRewardedAdDismissed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_SUCCESS_REWARDED_AD_FAILED, onSuccessRewardedAdFailed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_SUCCESS_REWARD_GRANTED, onSuccessRewardGranted);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_HELPER_REWARDED_AD_READY, onHelperRewardedAdReady);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_HELPER_REWARDED_AD_UNAVAILABLE, onHelperRewardedAdUnavailable);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_HELPER_REWARDED_AD_DISMISSED, onHelperRewardedAdDismissed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_HELPER_REWARDED_AD_FAILED, onHelperRewardedAdFailed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_HELPER_REWARD_GRANTED, onHelperRewardGranted);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_NEUTRAL_REWARDED_AD_READY, onNeutralRewardedAdReady);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_NEUTRAL_REWARDED_AD_UNAVAILABLE, onNeutralRewardedAdUnavailable);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_NEUTRAL_REWARDED_AD_DISMISSED, onNeutralRewardedAdDismissed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_NEUTRAL_REWARDED_AD_FAILED, onNeutralRewardedAdFailed);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_NEUTRAL_REWARD_GRANTED, onNeutralRewardGranted);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_VIRTUAL_CURRENCY_GRANTED, onVirtualCurrencyGranted);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_MULTIPLAYER_GAME_STARTED, onMultiplayerGameStarted);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_MULTIPLAYER_GAME_ENDED, onMultiplayerGameEnded);
			eventDispatcher.addEventListener(AdsorbEvent.ADSORB_SHOWMOREGAMES_DISMISSED, onShowMoreGamesDismissed);
			adsorb.setEventDispatcher(eventDispatcher);
		}

3. Implement the corresponding event handlers, for instance:

		static public function onInterstitialAdsAvailable(e:AdsorbEvent):void  { 
			// Interstitial ads available
		}		

		static public function onInterstitialAdDismissed(e:AdsorbEvent):void  { 
         // Interstitial ad dismissed
		}		

		static public function onInterstitialAdFailed(e:AdsorbEvent):void  { 
			// Interstitial ad failed to present altogether
		}		
		
		static public function onSuccessRewardedAdReady(e:AdsorbEvent):void  { 
			// Reward video loaded and ready to show
			
			adsorb.showSuccessAd (null, e.param);			// Show loaded ad, for example
		}

		static public function onSuccessRewardedAdUnavailable(e:AdsorbEvent):void  { 
			// Reward video unavailable
		}

		static public function onSuccessRewardedAdDismissed(e:AdsorbEvent):void  { 
         // Reward video dismissed
		}

		static public function onSuccessRewardedAdFailed(e:AdsorbEvent):void  { 
			// Reward video failed to present altogether
      }
		
		static public function onSuccessRewardGranted(e:AdsorbEvent):void  { 
        	// Reward granted for reward video
		}

		static public function onHelperRewardedAdReady(e:AdsorbEvent):void  { 
			// Reward video loaded and ready to show
			
			adsorb.showHelperAd (null, e.param);     // Show loaded ad, for example
		}

		static public function onHelperRewardedAdUnavailable(e:AdsorbEvent):void  { 
			// Reward video unavailable
		}

		static public function onHelperRewardedAdDismissed(e:AdsorbEvent):void  { 
         // Reward video dismissed
		}

		static public function onHelperRewardedAdFailed(e:AdsorbEvent):void  { 
			// Reward video failed to present altogether
      }

		static public function onHelperRewardGranted(e:AdsorbEvent):void  { 
        	// Reward granted for helper video
		}
    		
		static public function onNeutralRewardedAdReady(e:AdsorbEvent):void  { 
			// Reward video loaded and ready to show
			
			adsorb.showNeutralAd (null, e.param);     // Show loaded ad, for example
		}

		static public function onNeutralRewardedAdUnavailable(e:AdsorbEvent):void  { 
			// Reward video unavailable
		}

		static public function onNeutralRewardedAdDismissed(e:AdsorbEvent):void  { 
         // Reward video dismissed
		}

		static public function onNeutralRewardedAdFailed(e:AdsorbEvent):void  { 
			// Reward video failed to present altogether
      }

		static public function onNeutralRewardGranted(e:AdsorbEvent):void  { 
        	// Reward granted for neutral video
		}
    		
		static public function onVirtualCurrencyGranted(e:AdsorbEvent):void  { 
			// Virtual currency granted; the amount is in e.param
		}

		static public function onMultiplayerGameStarted(e:AdsorbEvent):void  { 
			// Start a multiplayer game now; the random seed is in e.param

         // Take the player to the corresponding game board immediately.
         // The random seed is a shared value across all players of the game, you can use it to initialize a board or level to be identical for all players
         // You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support
		}
		
		static public function onMultiplayerGameEnded(e:AdsorbEvent):void  { 
         // End a multiplayer game now. You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support
		}

		static public function onShowMoreGamesDismissed(e:AdsorbEvent):void  { 
			// Show more games browser dismissed
		}		

6. You can now show ads, reward and helper videos using the following API on the 'adsorb' object you created in the initialize function:

      /**
       * Show interstitial ad
       */
       public function showInterstitialAd() : void;

		/**
		 * Load success rewarded ad
		 * 
		 * @param strAchievement achievement done by the player (such as "Level completed")
		 * @param strReward reward to be granted (such as "50 gold")
		 * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
		 *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
		 */
		public function loadSuccessAd(strAchievement:String,strReward:String,useGameGUI:Boolean):void;
		
		/**
		 * Show success rewarded ad
	    * 
	    * @param tag optional tag for tracking performance of individual ads, can be null
       * @param filterName name of filter to show success ad for (null for default)
		 */
		public function showSuccessAd(tag:String,filterName:String):void;

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
		public function loadHelperAd(strCaption:String,strEnticementText:String,strReward:String,strButtonLabel:String,useGameGUI:Boolean):void;

		/**
		 * Show helper rewarded ad
	    * 
	    * @param tag optional tag for tracking performance of individual ads, can be null
       * @param filterName name of filter to show success ad for (null for default)
		 */
		public function showHelperAd(tag:String,filterName:String):void;

		/**
		 * Load neutral rewarded ad
       *
		 * @param strCaption neutral offer's caption (such as "Unlock untimed mode")
		 * @param strBody neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
		 * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
		 *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
		 */
		public function loadNeutralAd(strCaption:String,strBody:String,useGameGUI:Boolean):void;

		/**
		 * Show neutral rewarded ad
	    * 
	    * @param tag optional tag for tracking performance of individual ads, can be null
       * @param filterName name of filter to show success ad for (null for default)
		 */
		public function showNeutralAd(tag:String,filterName:String):void;

		/**
		 * Show newsletter subscription
		 */
		public function showNewsletter() : void;

		/**
		 * Show more games browser
		 */
		public function showMoreGames() : void;

		/**
		 * Show actually free games page
		 */
		public function showActuallyFreeGames() : void;

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
	public function enableLocalNotification (message:String) : void;

	/**
    * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
    * after the specified delay
    * 
    * @param title title to display in the notification when it fires
    * @param message message to display in the notification when it fires
    * @param delaySeconds delay in seconds after which to fire the local notification (for instance, 86400 for one day)
    */
   public function enableLocalNotificationWithDelay (title:String, message:String, delaySeconds:int);

	/**
	 * Disable showing a local notification
	 */
	public function disableLocalNotification () : void;

   You can enable a local notification at the start of your game. Adsorb will automatically arm it when your game is paused and disarm it when your game is resumed. 

   Example:
   adsorb.enableLocalNotification ("Come play with us!");

Adsorb also manages push notifications in order to announce new game releases and special offers. You can offer your users to opt in using a GUI, and call setPushNotificationsChannel() and enablePushNotifications() when they do. It is strongly recommended to offer push notifications so that we can announce and cross-market your future games to existing users.

   /**
    * Check if the device supports push notifications
    * 
    * @return true if push notifications are available, false if not
    */
	public function arePushNotificationsAvailable() : Boolean;

	/**
	 * Set channel to use for push notifications for this game. This must be called before enablePushNotifications() or disablePushNotifications()
	 * 
	 * @param channel channel to use for push notifications, such as HiddenObject
	 */
	public function setPushNotificationsChannel (channel:String) : void;

	/**
    * Opt into receiving push notifications for cross-marketing purposes
    */
   public function enablePushNotifications () : void;

	/**
	 * Opt out of receiving push notifications for cross-marketing purposes
	 */
   public function disablePushNotifications () : void;

   /**
    * Check if the user is currently opted into push notifications
    * 
    * @return true if push notifications are enabled, false if they are disabled
    */
	public function arePushNotificationsEnabled() : Boolean;

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
   public function suspendAdsForHours (hours:int) : void;

For instance, call adsorb.suspendAdsForHours(24); to suspend ads for a day, and suspendAdsForHours (-1) to turn them off after an in-app purchase.

9. Support for placements

We strongly recommend that you let Adsorb know what screen the user is currently on. This information will be used for anonymously measuring the effectiveness of ads on each of your various game screens. Additionally, if you allow it, it can also be used to show overlay views on top of your game, for instance over the main menu, notably to increase the monetization of your game.

	/**
	 * Tell Adsorb that the game is entering the specified screen
	 * 
	 * @param name screen name, such as: main_menu, instructions, level_select, playing, level_end, game_end
	 * @param allowOverlays true to allow Adsorb to show monetization and marketing overlays on top of some of the game screens,
	 *                      false to inform the current screen name only for event logging purposes
    */
   public function enterGameScreen (name:String, allowOverlays:Boolean) : void;
   
	/**
    * Tell Adsorb that the game is leaving the current screen
	 */
   public function leaveGameScreen () : void;

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
	 * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
	 *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
	 */
	public function setSuccessAdsAutoMode (strSuccessAchievement:String, strSuccessReward:String,
											         useGameGUI:Boolean) : void;

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
	public function setHelperAdsAutoMode (strHelperCaption:String, strHelperEnticementText:String,
										           strHelperReward:String, strHelperButtonLabel:String,
										           useGameGUI:Boolean) : void;

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
	public function setNeutralAdsAutoMode (strNeutralCaption:String, strNeutralBody:String, 
                                          useGameGUI:Boolean) : void;
   
	/**
	 * Complete the setup of automatically load rewarded ads and start loading in the background
	 */
	public function rewardedAdSetupComplete () : void;

   Call these methods at the beginning of your game, always calling adsorb.rewardedAdSetupComplete() last. You will subsequently receive "ready" and "unavailable" events for each kind of rewarded ad. It is up to your game to track whether each kind of ad is currently available, from receiving those events, and deciding when to show each kind of ad.

Also, you may optionally want to receive information on the ad network that provides a success, helper and neutral rewarded ad, in order to show your own GUI and provide context for the rewarded ad to the user, or to react according to the type of ad being presented. The Adsorb connector provides methods to that end. These methods can be called after loading a rewarded ad and receiving the corresponding "ad ready" event for the rewarded ad type.

   /**
    * Get the name of the network providing the currently loaded success rewarded ad
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    * 
    * @return name
    */
   public function getSuccessAdNetworkName(filterName:String):String;
		
   /**
    * Get the type of the currently loaded success rewarded ad (video, survey, ...)
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    *
    * @return type
    */
   public function getSuccessAdNetworkType(filterName:String):String;
		
	/**
    * Get the filter tags of the currently loaded success rewarded ad (long, highvalue, ...)
    * 
    * @param filterName name of filter to get ad network tags of (null for default)
    * 
    * @return tags
    */
	public function getSuccessAdNetworkTags(filterName:String):String;

   /**
    * Check if the currently loaded success rewarded ad provides its own GUI
    * 
    * @param filterName name of filter to get GUI mode of (null for default)
    * 
    * @return true if it does, false if the game has to provide it
    */
   public function doesSuccessAdProvideGUI(filterName:String):Boolean;

   /**
    * Get the name of the network providing the currently loaded helper rewarded ad
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    * 
    * @return name
    */
   public function getHelperAdNetworkName(filterName:String):String;
		
   /**
    * Get the type of the currently loaded helper rewarded ad (video, survey, ...)
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    *
    * @return type
    */
   public function getHelperAdNetworkType(filterName:String):String;
		
   /**
    * Get the filter tags of the currently loaded helper rewarded ad (long, highvalue, ...)
    * 
    * @param filterName name of filter to get ad network tags of (null for default)
    * 
    * @return tags
    */
   public function getHelperAdNetworkTags(filterName:String):String;

   /**
    * Check if the currently loaded helper rewarded ad provides its own GUI
    * 
    * @param filterName name of filter to get GUI mode of (null for default)
    * 
    * @return true if it does, false if the game has to provide it
    */
   public function doesHelperAdProvideGUI(filterName:String):Boolean;

   /**
    * Get the name of the network providing the currently loaded neutral rewarded ad
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    * 
    * @return name
    */
   public function getNeutralAdNetworkName(filterName:String):String;
		
   /**
    * Get the type of the currently loaded neutral rewarded ad (video, survey, ...)
    * 
    * @param filterName name of filter to get ad network type of (null for default)
    *
    * @return type
    */
   public function getNeutralAdNetworkType(filterName:String):String;
		
   /**
    * Get the filter tags of the currently loaded neutral rewarded ad (long, highvalue, ...)
    * 
    * @param filterName name of filter to get ad network tags of (null for default)
    * 
    * @return tags
    */
   public function getNeutralAdNetworkTags(filterName:String):String;

   /**
    * Check if the currently loaded neutral rewarded ad provides its own GUI
    * 
    * @param filterName name of filter to get GUI mode of (null for default)
    * 
    * @return true if it does, false if the game has to provide it
    */
   public function doesNeutralAdProvideGUI(filterName:String):Boolean;

Filtering rewarded ads by type

You may also request specific types of rewarded ads in various areas of your game, for instance to unlock additional features only by filling a highly-paid survey. You may request multiple filters for the same kind of ad (for instance neutral) in order to serve different rewards depending on the ads that are available. You will receive "ready", "unavailable", etc. events for each filter when you set up multiple ones. While you can change filter settings during your game, if you use multiple filters, it is better to set the filters at the beginning and keep them.

   /**
    * Filter success rewarded ads using keywords
    *
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to success rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   public function filterSuccessAds(filterName:String,filterKeywords:String):void;
		
   /**
    * Filter helper rewarded ads using keywords
    * 
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to helper rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   public function filterHelperAds(filterName:String,filterKeywords:String):void;
		
   /**
    * Filter neutral rewarded ads using keywords
    * 
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to neutral rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   public function filterNeutralAds(filterName:String,filterKeywords:String):void;	

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
	public function setSuccessAdsVars(strNetwork:String,strJsonVars:String):void;
	
	/**
	 * Set custom extra variables for a network that provides helper rewarded ads. The variables will be considered by the network the next
	 * time a helper rewarded ad is loaded.
	 * 
	 * @param strNetwork network name (for instance "mediabrix")
	 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
	 */
	public function setHelperAdsVars(strNetwork:String,strJsonVars:String):void;

	/**
	 * Set custom extra variables for a network that provides neutral rewarded ads. The variables will be considered by the network the next
	 * time a neutral rewarded ad is loaded.
	 * 
	 * @param strNetwork network name
	 * @param strJsonVars custom variables in json format
	 */
	public function setNeutralAdsVars(strNetwork:String,strJsonVars:String):void;

   Example:
   adsorb.setSuccessAdsVars ("mediabrix", "{ \"icon\": \"http://www.mysite.com/icon.png\" } ");

11. Rewarded achievements

Adsorb also supports ad networks that boost user engagement by providing rewards when the game reports achievements, such at Lootsie. Use adsorb.reportAchievement() to report events such as level_completed or timer (sent every few minutes, for instance every 2).

	/**
    * Report a completed achievement
    * 
    * @param achievementName name of completed achievement
    */
	public function reportAchievement (achievementName:String) : void;

Networks that provide an incentive in exchange for the user doing something (such as installing a lockscreen, for example AppKey) are also supported by Adsorb. The game can check if the action has been performed and the reward granted, or prompt the user to perform the action.

	/**
	 * Prompt user to perform an action to get an incentive
	 * 
	 * @param incentiveName incentive identifier
	 * @param incentiveText what the user will get if the action is performed
	 */
	public function promptIncentive (incentiveName:String, incentiveText:String) : void;

	/**
	 * Check if an incentive's reward should currently be granted
	 * 
	 * @param incentiveName incentive identifier
	 * 
	 * @return true if the incentive's reward should currently be granted, false if not
	 */
	public function isIncentiveRewardGranted (incentiveName:String):Boolean;

12. Affiliation

Your game may be eligible for affiliation, and receiving extra installs through our partners network. To this end, it must report any completed in-app purchases to Adsorb so that your game can benefit from the partner networks and additional installs.

Call adsorb.reportPurchase() with the name of the purchased product once you have completed a purchase.

   /**
    * Report a completed in-app purchase
    * 
    * @param productName name of purchased product
    * @param productPrice product price (such as 1.99)
    */
	public function reportPurchase (productName:String,productPrice:Number) : void;

Your game may also be eligible for a different revenue split in the event that a user has been attributed to you, as negociated with your FGL producer. If such a deal is in place, you must always determine and report the user's source using adsorb.reportAttribution(), and you must do it once per session. You must call the method even to indicate that the user isn't yours, otherwise you may not receive the correct amount of revenue.

	/**
	 * Report user attribution for this session
	 * 
	 * @param vendor name of the vendor supplying the information
	 * @param userType where the user came from
	 */
	public function reportAttribution (vendor:String, userType:String) : void;

13. Multiplayer support

Adsorb facilitates score-based competition with online players, by connecting your games to social SDKs with a minimal amount of time needed for integration. If this feature is recommended by your producer at FGL, use the following methods to support online play.

	/**
	 * Show the social feed and multiplayer challenges wall for this network
	 */
	public function showMultiplayerWall () : void;

	/**
	 * Report that the local player's current score has changed
	 * 
	 * @param score new score
	 */
	public function updateMultiplayerScore (score:int) : void;

	/**
	 * Report that the multiplayer game is completed for the local player
	 * 
	 * @param score final score
	 */
	public function completeMultiplayerGame (score:int) : void;

	/**
	 * Report that the multiplayer game has been canceled
	 */
	public function cancelMultiplayerGame () : void;

You will also need to handle the onMultiplayerGameStarted and onMultiplayerGameEnded events (see the chapter on events). You will receive onMultiplayerGameStarted when the local player engages with other players after calling adsorb.showMultiplayerWall(). You will receive onMultiplayerGameEnded() as a confirmation that the multiplayer mode is finished, as a result of you signalling the completion or cancelation of a multiplayer game.

14. Native ad overlays

Adsorb also supports adding an ad overlay, in a rectangular area defined by the game. You may for instance use this feature to show an ad on your pause menu.

    /**
     * Check if an ad overlay is currently ready to show
     * 
     * @return true if an ad overlay is ready, false if not
     */
    public function isAdOverlayReady() : Boolean;

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
    public function showAdOverlay (x1:Number, y1:Number, x2:Number, y2:Number, nAnimDuration:int, bShowRectangle:Boolean) : void;

    /**
     * Refresh cross-promo ad overlay, if another ad is ready
     */
    public function advanceAdOverlay () : void;

    /**
     * Hide cross-promo ad overlay, if one was showing
     *
     * @param nAnimDuration duration of the transition anims, in milliseconds
     */
    public function hideAdOverlay (nAnimDuration:int) : void;

15. Event reporting

You may also report arbitrary game events through Adsorb. These events will be collected alongside other automatically generated events for tracking the performance of your ads and app installs for example.

	/**
	 * Report game event
	 * 
	 * @param eventName event name
	 */
   public function reportGameEvent (eventName:String) : void;

## Conclusion

You can refer to the supplied AIR example project for further information.

That's it! Adsorb will take care of the rest. Happy monetization!
