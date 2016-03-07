Corona plugin for Adsorb

## What is Adsorb

Adsorb is FGL's unique monetization solution for your Android applications, that mediates interstitial ads using the highest paying networks, and also mediates incented videos for
an even higher payout. FGL constantly monitors the performance of the various networks and adjusts accordingly.

Adsorb uses a two-step process:
1) You integrate a small stub library in your application. Stub popups will appear when you request ads, and you can test granting rewards in your game as well.
2) FGL will wrap your game using Adsorb and integrate the real ad SDKs into your game. When new high-paying ad networks emerge, or when performance shifts, FGL can update the ads sdk without having you rebuild the app, and react in near-realtime to new market opportunities.

This approach has helped FGL become one of the most widely respected and highest paying solutions for free ad-based games on Android.

## Integration with your Corona app

1. Copy the android/src/android and android/src/plugin folders into the android/src folder of your Corona Entreprise app. 

2. In your lua code (for instance in Corona/main.lua), import the Adsorb plugin

local adsorb = require "plugin.adsorb"

3. In your lua code, implement the Adsorb events handler. You don't have to react to all the events. Example:

-- Adsorb events handler
local function listener( event )
   if (event.type == "onInterstitialAdsAvailable") then
      // Interstitial ads available
      native.showAlert( "Adsorb event", "Interstitial ad ready", { "OK" } )
   end
	if (event.type == "onInterstitialAdDismissed") then
      // Interstitial ad dismissed
		native.showAlert( "Adsorb event", "Interstitial ad dismissed", { "OK" } )
	end
	if (event.type == "onInterstitialAdFailed") then
      // Interstitial ad failed
		native.showAlert( "Adsorb event", "Interstitial ad failed", { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdReady") then
      // Success ad loaded and ready to show
		native.showAlert( "Adsorb event", "Success ad ready for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdUnavailable") then
      // Success ad unavailable
		native.showAlert( "Adsorb event", "Success ad unavailable for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdDismissed") then
      // Success ad dismissed
		native.showAlert( "Adsorb event", "Success ad dismissed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdFailed") then
      // Success ad failed
		native.showAlert( "Adsorb event", "Success ad failed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardGranted") then
      // Reward granted for success ad
		native.showAlert( "Adsorb event", "Success ad reward granted for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdReady") then
      // Helper ad loaded and ready to show
		native.showAlert( "Adsorb event", "Helper ad ready for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdUnavailable") then
      // Helper ad unavailable
		native.showAlert( "Adsorb event", "Helper ad unavailable for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdDismissed") then
		// Helper ad dismissed
      native.showAlert( "Adsorb event", "Helper ad dismissed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdFailed") then
      // Helper ad failed
		native.showAlert( "Adsorb event", "Helper ad failed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardGranted") then
      // Reward granted for success ad
		native.showAlert( "Adsorb event", "Helper ad reward granted for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdReady") then
      // Neutral ad loaded and ready to show
		native.showAlert( "Adsorb event", "Neutral ad ready for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdUnavailable") then
      // Neutral ad unavailable
		native.showAlert( "Adsorb event", "Neutral ad unavailable for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdDismissed") then
      // Neutral ad dismissed
		native.showAlert( "Adsorb event", "Neutral ad dismissed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdFailed") then
      // Neutral ad failed
		native.showAlert( "Adsorb event", "Neutral ad failed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardGranted") then
      // Reward granted for success ad
		native.showAlert( "Adsorb event", "Neutral ad granted for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onVirtualCurrencyGranted") then
      // Virtual currency granted (for example 50 coins)
		native.showAlert( "Adsorb event", "Virtual coins granted: " .. tonumber (event.param), { "OK" } )
	end
	if (event.type == "onMultiplayerGameStarted") then
      // Start a multiplayer game now. Take the player to the corresponding game board immediately.
      // randomSeed is a shared value across all players of the game, you can use it to initialize a board or level to be identical for all players
      // You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support
		native.showAlert( "Adsorb event", "Multiplayer game started with seed: " .. tonumber (event.param), { "OK" } )
	end
	if (event.type == "onMultiplayerGameEnded") then
      // End a multiplayer game now. You can ignore this event if your game doesn't support online play; see the chapter on multiplayer support
		native.showAlert( "Adsorb event", "Multiplayer game ended", { "OK" } )
	end
	if (event.type == "onShowMoreGamesDismissed") then
      // "Show more games" browser dismissed
		native.showAlert( "Adsorb event", "Show more games dismissed", { "OK" } )
	end
end

4. You can now show ads, reward and helper videos using the following adsorb API:

    /**
     * Show interstitial ad
     */
    function showInterstitialAd();

    /**
     * Load success rewarded ad
     * 
     * @param achievement achievement done by the player (such as "Level completed")
     * @param reward reward to be granted (such as "50 gold")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
     */
    function loadSuccessAd(achievement, reward, useGameGUI);

    /**
     * Show success rewarded ad
     * 
     * @param tag optional tag for tracking performance of individual ads, can be null
     */
    function showSuccessAd(tag);

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
    function loadHelperAd(caption, enticementText, reward, buttonLabel, useGameGUI);

    /**
     * Show helper rewarded ad
     * 
     * @param tag optional tag for tracking performance of individual ads, can be null
     */
    function showHelperAd(tag);

    /**
     * Load neutral rewarded ad
     *
     * @param caption neutral offer's caption (such as "Unlock untimed mode")
     * @param body neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
     * @param useGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
     *                   receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
     */
    function loadNeutralAd(caption, body, useGameGUI);

    /**
     * Show neutral rewarded ad
     * 
     * @param tag optional tag for tracking performance of individual ads, can be null
     */
    function showNeutralAd(tag);

    /**
     * Show newsletter
     */
    function showNewsletter();
    
    /**
     * Show more games browser
     */
    function showMoreGames();

    /**
     * Show actually free games page
     */
    function showActuallyFreeGames();

    For example:

    adsorb.showInterstitialAd();
    adsorb.loadSuccessAd("Level up!", "135 gems", false);
    adsorb.showSuccessAd(null, null);
    adsorb.loadHelperAd("Need more moves?", "Watch a short message and get an extra", "7 moves", "Tap to get moving", false);
    adsorb.showHelperAd(null, null);
    adsorb.loadNeutralAd("Unlock untimed mode", "Watch an offer in a minute or less and unlock untimed mode!", false);
    adsorb.showNeutralAd(null, null);
    adsorb.showNewsletter();
    adsorb.showMoreGames();
    adsorb.showActuallyFreeGames();

5. User re-engagement

Adsorb provides a simple mechanism for arming a local notification. The user gets a notification from your game a day later. Users install many apps and may forget about yours; local notifications have been measured to increase re-engagement.

	/**
	 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
	 * after a day
	 * 
	 * @param message message to display in the notification when it fires
	 */
   function enableLocalNotification(message);

	/**
	 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
	 * after the specified delay
	 * 
	 * @param title title to display in the notification when it fires
	 * @param message message to display in the notification when it fires
	 * @param delaySeconds delay in seconds after which to fire the local notification (for instance, 86400 for one day)
	 */
   function enableLocalNotificationWithDelay(title, message, delaySeconds);

	/**
	 * Disable showing a local notification
	 */
   function disableLocalNotification();

   You can enable a local notification at the start of your game. Adsorb will automatically arm it when your game is paused and disarm it when your game is resumed. 

   Example:
   adsorb.enableLocalNotification ("Come play with us!");

Adsorb also manages push notifications in order to announce new game releases and special offers. You can offer your users to opt in using a GUI, and call adsorb.setPushNotificationsChannel() and adsorb.EnablePushNotifications() when they do. It is strongly recommended to offer push notifications so that we can announce and cross-market your future games to existing users.

   /**
    * Check if the device supports push notifications
    * 
    * @return true if push notifications are available, false if not
    */
   function arePushNotificationsAvailable();

	/**
	 * Set channel to use for push notifications for this game. This must be called before enablePushNotifications() or disablePushNotifications()
	 * 
	 * @param channel channel to use for push notifications, such as HiddenObject
	 */
   function setPushNotificationsChannel(channel);

   /**
    * Opt into receiving push notifications for cross-marketing purposes
    */
   function enablePushNotifications();

   /**
    * Opt out of receiving push notifications for cross-marketing purposes
    */
   function disablePushNotifications();

   /**
    * Check if the user is currently opted into push notifications
    * 
    * @return true if push notifications are enabled, false if they are disabled
    */
   function arePushNotificationsEnabled();

6. Premium games

Adsorb supports disabling the ads temporarily, for instance as a reward for an incented ad, and permanently, for instance as the result of an in-app purchase to turn them off. Use the suspendAdsForHours() method as described below.

	/**
	 * Suspend loading and showing any ads for the specified number of hours. This can
	 * be used as the reward of an incented ad for instance.
	 * 
	 * @param hours number of hours to suspend all ads for (for instance 24),
	 *              -1 to suspend permanently, 
	 *              0 to resume previously suspended ads
	 */
   function suspendAdsForHours(hours);

For instance, call adsorb.SuspendAdsForHours(24); to suspend ads for a day, and suspendAdsForHours (-1) to turn them off after an in-app purchase.

7. Support for placements

We strongly recommend that you let Adsorb know what screen the user is currently on. This information will be used for anonymously measuring the effectiveness of ads on each of your various game screens. Additionally, if you allow it, it can also be used to show overlay views on top of your game, for instance over the main menu, notably to increase the monetization of your game.

	/**
	 * Tell Adsorb that the game is entering the specified screen
	 * 
	 * @param name screen name, such as: main_menu, instructions, level_select, playing, level_end, game_end
	 * @param allowOverlays true to allow Adsorb to show monetization and marketing overlays on top of some of the game screens,
	 *                      false to inform the current screen name only for event logging purposes
	 */
   function enterGameScreen(name, allowOverlays);
   
	/**
	 * Tell Adsorb that the game is leaving the current screen
	 */
   function leaveGameScreen ();

8. Advanced rewarded ads support

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
   function setSuccessAdsAutoMode(strSuccessAchievement, strSuccessReward, useGameGUI);

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
   function setHelperAdsAutoMode(strHelperCaption, strHelperEnticementText, strHelperReward, strHelperButtonLabel,
                                 useGameGUI);

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
   function setNeutralAdsAutoMode(strNeutralCaption, strNeutralBody, useGameGUI);

	/**
	 * Complete the setup of automatically load rewarded ads and start loading in the background
	 */
   function rewardedAdSetupComplete();

   Call these methods at the beginning of your game, always calling adsorb.rewardedAdSetupComplete() last. You will subsequently receive "ready" and "unavailable" events for each kind of rewarded ad. It is up to your game to track whether each kind of ad is currently available, from receiving those events, and deciding when to show each kind of ad.

Also, you may optionally want to receive information on the ad network that provides a success, helper and neutral rewarded ad, in order to show your own GUI and provide context for the rewarded ad to the user, or to react according to the type of ad being presented. The Adsorb connector provides methods to that end. These methods can be called after loading a rewarded ad and receiving the corresponding "ad ready" event for the rewarded ad type.

    /**
     * Get the name of the network providing the currently loaded success rewarded ad
	  * 
	  * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return name
     */
    function getSuccessAdNetworkName(filterName);

    /**
     * Get the type of the currently loaded success rewarded ad (video, survey, ...)
	  * 
	  * @param filterName name of filter to get ad network type of (null for default)
     * 
     * @return type
     */
    function getSuccessAdNetworkType(filterName);

    /**
     * Get the filter tags of the currently loaded success rewarded ad (long, highvalue, ...)
     * 
     * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return tags
     */
    function getSuccessAdNetworkTags(filterName);

    /**
     * Check if the currently loaded success rewarded ad provides its own GUI
     * 
     * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return true if it does, false if the game has to provide it
     */
    function  doesSuccessAdProvideGUI(filterName);

    /**
     * Get the name of the network providing the currently loaded helper rewarded ad
	  * 
	  * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return name
     */
    function getHelperAdNetworkName(filterName);

    /**
     * Get the type of the currently loaded helper rewarded ad (video, survey, ...)
	  * 
	  * @param filterName name of filter to get ad network type of (null for default)
     * 
     * @return type
     */
    function getHelperAdNetworkType(filterName);

    /**
     * Get the filter tags of the currently loaded helper rewarded ad (long, highvalue, ...)
     * 
     * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return tags
     */
    function getHelperAdNetworkTags(filterName);

    /**
     * Check if the currently loaded helper rewarded ad provides its own GUI
     * 
     * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return true if it does, false if the game has to provide it
     */
    function doesHelperAdProvideGUI(filterName);

    /**
     * Get the name of the network providing the currently loaded neutral rewarded ad
	  * 
	  * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return name
     */
    function getNeutralAdNetworkName(filterName);

    /**
     * Get the type of the currently loaded neutral rewarded ad (video, survey, ...)
	  * 
	  * @param filterName name of filter to get ad network type of (null for default)
     * 
     * @return type
     */
    function getNeutralAdNetworkType(filterName);

    /**
     * Get the filter tags of the currently loaded neutral rewarded ad (long, highvalue, ...)
     * 
     * @param filterName name of filter to get ad network tags of (null for default)
     * 
     * @return tags
     */
    function getNeutralAdNetworkTags(filterName);

    /**
     * Check if the currently loaded neutral rewarded ad provides its own GUI
     * 
     * @param filterName name of filter to get ad network name of (null for default)
     * 
     * @return true if it does, false if the game has to provide it
     */
    function doesNeutralAdProvideGUI(filterName);

Filtering rewarded ads by type

You may also request specific types of rewarded ads in various areas of your game, for instance to unlock additional features only by filling a highly-paid survey. You may request multiple filters for the same kind of ad (for instance neutral) in order to serve different rewards depending on the ads that are available. You will receive "ready", "unavailable", etc. events for each filter when you set up multiple ones. While you can change filter settings during your game, if you use multiple filters, it is better to set the filters at the beginning and keep them.

   /**
    * Filter success rewarded ads using keywords
    *
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to success rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   function filterSuccessAds(filterName, filterKeywords);

   /**
    * Filter helper rewarded ads using keywords
    * 
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to helper rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   function filterHelperAds(filterName, filterKeywords);

   /**
    * Filter neutral rewarded ads using keywords
    * 
    * @param filterName filter name (null for default)
    * @param filterKeywords filter keywords to apply to neutral rewarded ads (such as 'video highvalue'), or null to remove the filter
    */
   function filterNeutralAds(filterName, filterKeywords);

   Example:

   adsorb.filterSuccessAds(null, "video");
   adsorb.filterNeutralAds(null, "survey");

   Multiple filters:

   adsorb.filterNeutralAds ("filter1", "video long");
   adsorb.filterNeutralAds ("filter2", "video short");

Adsorb also supports passing custom key-value pairs in json format to specific ad networks, if you wish to implement features specific to a network. Please see with your FGL producer about implementing these features.

	/**
	 * Set custom extra variables for a network that provides success rewarded ads. The variables will be considered by the network the next
	 * time a success rewarded ad is loaded.
	 * 
	 * @param strNetwork network name (for instance "mediabrix")
	 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
	 */
	function setSuccessAdsVars (strNetwork, strJsonVars);
	
	/**
	 * Set custom extra variables for a network that provides helper rewarded ads. The variables will be considered by the network the next
	 * time a helper rewarded ad is loaded.
	 * 
	 * @param strNetwork network name (for instance "mediabrix")
	 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
	 */
	function setHelperAdsVars (strNetwork, strJsonVars);

	/**
	 * Set custom extra variables for a network that provides neutral rewarded ads. The variables will be considered by the network the next
	 * time a neutral rewarded ad is loaded.
	 * 
	 * @param strNetwork network name
	 * @param strJsonVars custom variables in json format
	 */
	function setNeutralAdsVars (strNetwork, strJsonVars);

   Example:
   adsorb.setSuccessAdsVars ("mediabrix", "{ \"icon\": \"http://www.mysite.com/icon.png\" } ");

9. Rewarded achievements

Adsorb also supports ad networks that boost user engagement by providing rewards when the game reports achievements, such at Lootsie. Use adsorb.ReportAchievement() to report events such as level_completed or timer (sent every few minutes, for instance every 2).

 	/**
	 * Report a completed achievement
	 * 
	 * @param achievementName name of completed achievement
	 */
	function reportAchievement(achievementName);

Networks that provide an incentive in exchange for the user doing something (such as installing a lockscreen, for example AppKey) are also supported by Adsorb. The game can check if the action has been performed and the reward granted, or prompt the user to perform the action.

	/**
	 * Prompt user to perform an action to get an incentive
	 * 
	 * @param incentiveName incentive identifier
	 * @param incentiveText what the user will get if the action is performed
	 */
   function promptIncentive(incentiveName, incentiveText);

	/**
	 * Check if an incentive's reward should currently be granted
	 * 
	 * @param incentiveName incentive identifier
	 * 
	 * @return true if the incentive's reward should currently be granted, false if not
	 */
	function isIncentiveRewardGranted(incentiveName);

10. Affiliation

Your game may be eligible for affiliation, and receiving extra installs through our partners network. To this end, it must report any completed in-app purchases to Adsorb so that your game can benefit from the partner networks and additional installs.

Call adsorb.reportPurchase() with the name of the purchased product once you have completed a purchase.

   /**
    * Report a completed in-app purchase
    * 
    * @param productName name of purchased product
	 * @param productPrice product price (such as 1.99)
    */
   function reportPurchase(productName, productPrice);

Your game may also be eligible for a different revenue split in the event that a user has been attributed to you, as negociated with your FGL producer. If such a deal is in place, you must always determine and report the user's source using adsorb.ReportAttribution(), and you must do it once per session. You must call the method even to indicate that the user isn't yours, otherwise you may not receive the correct amount of revenue.

    /**
     * Report user attribution for this session
     * 
     * @param vendor name of the vendor supplying the information
     * @param userType where the user came from
     */
    function reportAttribution(vendor, userType);

11. Multiplayer support

Adsorb facilitates score-based competition with online players, by connecting your games to social SDKs with a minimal amount of time needed for integration. If this feature is recommended by your producer at FGL, use the following methods to support online play.

	/**
	 * Show the social feed and multiplayer challenges wall for this network
	 */
	function showMultiplayerWall ();

	/**
	 * Report that the local player's current score has changed
	 * 
	 * @param score new score
	 */
	function updateMultiplayerScore(score);

	/**
	 * Report that the multiplayer game is completed for the local player
	 * 
	 * @param score final score
	 */
	function completeMultiplayerGame(score);

	/**
	 * Report that the multiplayer game has been canceled
	 */
	function cancelMultiplayerGame();

You will also need to handle the onMultiplayerGameStarted and onMultiplayerGameEnded events (see the chapter on events). You will receive onMultiplayerGameStarted when the local player engages with other players after calling adsorb.showMultiplayerWall(). You will receive onMultiplayerGameEnded as a confirmation that the multiplayer mode is finished, as a result of you signalling the completion or cancelation of a multiplayer game.

12. Native ad overlays

Adsorb also supports adding an ad overlay, in a rectangular area defined by the game. You may for instance use this feature to show an ad on your pause menu.

    /**
     * Check if an ad overlay is currently ready to show
     * 
     * @return true if an ad overlay is ready, false if not
     */
    function isAdOverlayReady();

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
    function showAdOverlay(x1, y1, x2, y2, nAnimDuration, bShowRectangle);

    /**
     * Refresh cross-promo ad overlay, if another ad is ready
     */
    function advanceAdOverlay();
    
    /**
     * Hide cross-promo ad overlay, if one was showing
     *
     * @param nAnimDuration duration of the transition anims, in milliseconds
     */
    function hideAdOverlay(nAnimDuration);

13. Event reporting

You may also report arbitrary game events through Adsorb. These events will be collected alongside other automatically generated events for tracking the performance of your ads and app installs for example.

	/**
	 * Report game event
	 * 
	 * @param eventName event name
	 */
   function reportGameEvent(eventName);

## Conclusion

You can refer to the supplied Unity example project for further information.

That's it! Adsorb will take care of the rest. Happy monetization!
