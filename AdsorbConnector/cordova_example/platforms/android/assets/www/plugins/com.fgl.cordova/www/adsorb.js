cordova.define("com.fgl.cordova.Adsorb", function(require, exports, module) { var cordova = require('cordova'),
    argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

// Constructor

var Adsorb = function() {
    this.serviceName = "Adsorb";
    this._start ();
};

Adsorb.prototype._start = function() {
   // Set function to be called by Adsorb when an event is received
   exec(this._onAdsorbEvent, null, this.serviceName, 'setEventCallback', []);
}

/**
 * Check if Adsorb SDK is injected into the host app
 *
 * @param resultCallback function that receives the result: true if injected, false if not
 */
Adsorb.prototype.isSdkInjected = function (resultCallback) {
   exec(resultCallback, null, this.serviceName, 'isSdkInjected', []);
};

/**
 * Set custom extra variables for a network that provides success rewarded ads. The variables will be considered by the network the next
 * time a success rewarded ad is loaded.
 *
 * @param strNetwork network name (for instance "mediabrix")
 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
 */
Adsorb.prototype.setSuccessAdsVars = function (strNetwork, strJsonVars) {
    exec(null, null, this.serviceName, 'setSuccessAdsVars', [strNetwork, strJsonVars]);
};

/**
 * Set custom extra variables for a network that provides helper rewarded ads. The variables will be considered by the network the next
 * time a helper rewarded ad is loaded.
 *
 * @param strNetwork network name (for instance "mediabrix")
 * @param strJsonVars custom variables in json format (for instance { "icon","http://www.mysite.com/icon.png" })
 */
Adsorb.prototype.setHelperAdsVars = function (strNetwork, strJsonVars) {
    exec(null, null, this.serviceName, 'setHelperAdsVars', [strNetwork, strJsonVars]);
};

/**
 * Set custom extra variables for a network that provides neutral rewarded ads. The variables will be considered by the network the next
 * time a neutral rewarded ad is loaded.
 *
 * @param strNetwork network name
 * @param strJsonVars custom variables in json format
 */
Adsorb.prototype.setNeutralAdsVars = function (strNetwork, strJsonVars) {
    exec(null, null, this.serviceName, 'setNeutralAdsVars', [strNetwork, strJsonVars]);
};

/**
 * Show interstitial ad
 */
Adsorb.prototype.showInterstitialAd = function () {
    exec(null, null, this.serviceName, 'showInterstitialAd', []);
};

/**
 * Load success rewarded ad
 *
 * @param strAchievement achievement done by the player (such as "Level completed")
 * @param strReward reward to be granted (such as "50 gold")
 * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
 *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
 */
Adsorb.prototype.loadSuccessAd = function (strAchievement, strReward, bUseGameGUI) {
    exec(null, null, this.serviceName, 'loadSuccessAd', [strAchievement, strReward, bUseGameGUI]);
};

/**
 * Show success rewarded ad
 *
 * @param tag optional tag for tracking performance of individual ads, can be null
 * @param filterName name of filter to show success ad for (null for default)
 */
Adsorb.prototype.showSuccessAd = function (tag, filterName) {
    if (filterName == null) filterName = 'default';
    exec(null, null, this.serviceName, 'showSuccessAd', [tag, filterName]);
};

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
Adsorb.prototype.loadHelperAd = function (strCaption, strEnticementText, strReward, strButtonLabel, bUseGameGUI) {
    exec(null, null, this.serviceName, 'loadHelperAd', [strCaption, strEnticementText, strReward, strButtonLabel, bUseGameGUI]);
};

/**
 * Show helper rewarded ad
 *
 * @param tag optional tag for tracking performance of individual ads, can be null
 * @param filterName name of filter to show success ad for (null for default)
 */
Adsorb.prototype.showHelperAd = function (tag, filterName) {
    if (filterName == null) filterName = 'default';
    exec(null, null, this.serviceName, 'showHelperAd', [tag, filterName]);
};

/**
 * Load neutral rewarded ad
 *
 * @param strCaption neutral offer's caption (such as "Unlock untimed mode")
 * @param strBody neutral offer's body (such as "Watch an offer in a minute or less and unlock untimed mode!")
 * @param bUseGameGUI true to let the game use its own GUI if allowed by the ad network, false to use the network's GUI; when
 *                    receiving a ready event, adsorb will inform the game of whether it can use its GUI or not
 */
Adsorb.prototype.loadNeutralAd = function (strCaption, strBody, bUseGameGUI) {
    exec(null, null, this.serviceName, 'loadNeutralAd', [strCaption, strBody, bUseGameGUI]);
};

/**
 * Show neutral rewarded ad
 *
 * @param tag optional tag for tracking performance of individual ads, can be null
 * @param filterName name of filter to show success ad for (null for default)
 */
Adsorb.prototype.showNeutralAd = function (tag, filterName) {
    if (filterName == null) filterName = 'default';
    exec(null, null, this.serviceName, 'showNeutralAd', [tag, filterName]);
};

/**
 * Filter success rewarded ads using keywords
 *
 * @param filterName filter name (null for default)
 * @param filterKeywords filter keywords to apply to success rewarded ads (such as 'video high_value'), or null to remove the filter
 */
Adsorb.prototype.filterSuccessAds = function (filterName, filterKeywords) {
    if (filterName == null) filterName = 'default';
    exec(null, null, this.serviceName, 'filterSuccessAds', [filterName, filterKeywords]);
};

/**
 * Filter helper rewarded ads using keywords
 *
 * @param filterName filter name (null for default)
 * @param filterKeywords filter keywords to apply to helper rewarded ads (such as 'video high_value'), or null to remove the filter
 */
Adsorb.prototype.filterHelperAds = function (filterName, filterKeywords) {
    if (filterName == null) filterName = 'default';
    exec(null, null, this.serviceName, 'filterHelperAds', [filterName, filterKeywords]);
};

/**
 * Filter neutral rewarded ads using keywords
 *
 * @param filterName filter name (null for default)
 * @param filterKeywords filter keywords to apply to neutral rewarded ads (such as 'video high_value'), or null to remove the filter
 */
Adsorb.prototype.filterNeutralAds = function (filterName, filterKeywords) {
    if (filterName == null) filterName = 'default';
    exec(null, null, this.serviceName, 'filterNeutralAds', [filterName, filterKeywords]);
};

/**
 * Get the name of the network providing the currently loaded success rewarded ad
 *
 * @param filterName name of filter to get ad network name of (null for default)
 * @param resultCallback function that receives the result: network name
 */
Adsorb.prototype.getSuccessAdNetworkName = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getSuccessAdNetworkName', [filterName]);
};

/**
 * Get the type of the currently loaded success rewarded ad (video, survey, ...)
 *
 * @param filterName name of filter to get ad network type of (null for default)
 * @param resultCallback function that receives the result: network type
 */
Adsorb.prototype.getSuccessAdNetworkType = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getSuccessAdNetworkType', [filterName]);
};

/**
 * Get the filter tags of the currently loaded success rewarded ad (long, high_value, ...)
 *
 * @param filterName name of filter to get ad network tags of (null for default)
 * @param resultCallback function that receives the result: network tags
 */
Adsorb.prototype.getSuccessAdNetworkTags = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getSuccessAdNetworkTags', [filterName]);
};

/**
 * Check if the currently loaded success rewarded ad provides its own GUI
 *
 * @param filterName name of filter to get GUI mode of (null for default)
 * @param resultCallback function that receives the result: boolean, true if the network does, false if the game has to provide it
 */
Adsorb.prototype.doesSuccessAdProvideGUI = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'doesSuccessAdProvideGUI', [filterName]);
};

/**
 * Get the name of the network providing the currently loaded helper rewarded ad
 *
 * @param filterName name of filter to get ad network name of (null for default)
 * @param resultCallback function that receives the result: network name
 */
Adsorb.prototype.getHelperAdNetworkName = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getHelperAdNetworkName', [filterName]);
};

/**
 * Get the type of the currently loaded helper rewarded ad (video, survey, ...)
 *
 * @param filterName name of filter to get ad network type of (null for default)
 * @param resultCallback function that receives the result: network type
 */
Adsorb.prototype.getHelperAdNetworkType = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getHelperAdNetworkType', [filterName]);
};

/**
 * Get the filter tags of the currently loaded helper rewarded ad (long, high_value, ...)
 *
 * @param filterName name of filter to get ad network tags of (null for default)
 * @param resultCallback function that receives the result: network tags
 */
Adsorb.prototype.getHelperAdNetworkTags = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getHelperAdNetworkTags', [filterName]);
};

/**
 * Check if the currently loaded helper rewarded ad provides its own GUI
 *
 * @param filterName name of filter to get GUI mode of (null for default)
 * @param resultCallback function that receives the result: boolean, true if the network does, false if the game has to provide it
 */
Adsorb.prototype.doesHelperAdProvideGUI = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'doesHelperAdProvideGUI', [filterName]);
};

/**
 * Get the name of the network providing the currently loaded neutral rewarded ad
 *
 * @param filterName name of filter to get ad network name of (null for default)
 * @param resultCallback function that receives the result: network name
 */
Adsorb.prototype.getNeutralAdNetworkName = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getNeutralAdNetworkName', [filterName]);
};

/**
 * Get the type of the currently loaded neutral rewarded ad (video, survey, ...)
 *
 * @param filterName name of filter to get ad network type of (null for default)
 * @param resultCallback function that receives the result: network type
 */
Adsorb.prototype.getNeutralAdNetworkType = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getNeutralAdNetworkType', [filterName]);
};

/**
 * Get the filter tags of the currently loaded neutral rewarded ad (long, high_value, ...)
 *
 * @param filterName name of filter to get ad network tags of (null for default)
 * @param resultCallback function that receives the result: network tags
 */
Adsorb.prototype.getNeutralAdNetworkTags = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'getNeutralAdNetworkTags', [filterName]);
};

/**
 * Check if the currently loaded neutral rewarded ad provides its own GUI
 *
 * @param filterName name of filter to get GUI mode of (null for default)
 * @param resultCallback function that receives the result: boolean, true if the network does, false if the game has to provide it
 */
Adsorb.prototype.doesNeutralAdProvideGUI = function (filterName, resultCallback) {
   if (filterName == null) filterName = 'default';
   exec(resultCallback, null, this.serviceName, 'doesNeutralAdProvideGUI', [filterName]);
};

/**
 * Report a completed achievement
 *
 * @param achievementName name of completed achievement
 */
Adsorb.prototype.reportAchievement = function (achievementName) {
    exec(null, null, this.serviceName, 'reportAchievement', [achievementName]);
};

/**
 * Prompt user to perform an action to get an incentive
 *
 * @param incentiveName incentive identifier
 * @param incentiveText what the user will get if the action is performed
 */
Adsorb.prototype.promptIncentive = function (incentiveName, incentiveText) {
    exec(null, null, this.serviceName, 'promptIncentive', [incentiveName, incentiveText]);
};

/**
 * Check if an incentive's reward should currently be granted
 *
 * @param incentiveName incentive identifier
 *
 * @return true if the incentive's reward should currently be granted, false if not
 */
Adsorb.prototype.isIncentiveRewardGranted = function (incentiveName, resultCallback) {
   exec(resultCallback, null, this.serviceName, 'isIncentiveRewardGranted', [filterName]);
};

/**
 * Report a completed in-app purchase
 *
 * @param productName name of purchased product
 * @param productPrice product price (such as 1.99)
 */
Adsorb.prototype.reportPurchase = function (productName, productPrice) {
    exec(null, null, this.serviceName, 'reportPurchase', [productName, productPrice]);
};

/**
 * Report user attribution for this session
 *
 * @param vendor name of the vendor supplying the information
 * @param userType where the user came from
 */
Adsorb.prototype.reportAttribution = function (vendor, userType) {
    exec(null, null, this.serviceName, 'reportAttribution', [vendor, userType]);
};

/**
 * Show "more games" browser
 */
Adsorb.prototype.showMoreGames = function () {
   exec(null, null, this.serviceName, 'showMoreGames', []);
};

/**
 * Show newsletter subscription
 */
Adsorb.prototype.showNewsletter = function () {
   exec(null, null, this.serviceName, 'showNewsletter', []);
};

/**
 * Show actually free games page
 */
Adsorb.prototype.showActuallyFreeGames = function () {
   exec(null, null, this.serviceName, 'showActuallyFreeGames', []);
};

/**
 * Suspend loading and showing any ads for the specified number of hours. This can
 * be used as the reward of an incented ad for instance.
 *
 * @param hours number of hours to suspend all ads for (for instance 24),
 *              -1 to suspend permanently,
 *              0 to resume previously suspended ads
 */
Adsorb.prototype.suspendAdsForHours = function (hours) {
   exec(null, null, this.serviceName, 'suspendAdsForHours', [hours]);
};

/**
 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
 * after a day
 *
 * @param message message to display in the notification when it fires
 */
Adsorb.prototype.enableLocalNotification = function (message) {
   exec(null, null, this.serviceName, 'enableLocalNotification', [message]);
};

/**
 * Enable showing a local notification. The notification will automatically be armed when the app is paused; it will fire
 * after the specified delay
 *
 * @param title title to display in the notification when it fires
 * @param message message to display in the notification when it fires
 * @param delaySeconds delay in seconds after which to fire the local notification (for instance, 86400 for one day)
 */
Adsorb.prototype.enableLocalNotificationWithDelay = function (title, message, delaySeconds) {
   exec(null, null, this.serviceName, 'enableLocalNotificationWithDelay', [title, message, delaySeconds]);
};

/**
 * Disable showing a local notification
 */
Adsorb.prototype.disableLocalNotification = function () {
   exec(null, null, this.serviceName, 'disableLocalNotification', []);
};

/**
 * Check if the device supports push notifications
 *
 * @param resultCallback function that receives the result: boolean, true if push notifications are available, false if not
 */
Adsorb.prototype.arePushNotificationsAvailable = function(resultCallback) {
   exec(resultCallback, null, this.serviceName, 'arePushNotificationsAvailable', []);
};

/**
 * Set channel to use for push notifications for this game. This must be called before enablePushNotifications() or disablePushNotifications()
 *
 * @param channel channel to use for push notifications, such as HiddenObject
 */
Adsorb.prototype.setPushNotificationsChannel = function (channel) {
   exec(null, null, this.serviceName, 'setPushNotificationsChannel', [channel]);
};

/**
 * Opt into receiving push notifications for cross-marketing purposes
 */
Adsorb.prototype.enablePushNotifications = function () {
   exec(null, null, this.serviceName, 'enablePushNotifications', []);
};

/**
 * Opt out of receiving push notifications for cross-marketing purposes
 */
Adsorb.prototype.disablePushNotifications = function () {
   exec(null, null, this.serviceName, 'disablePushNotifications', []);
};

/**
 * Check if the user is currently opted into push notifications
 *
 * @param resultCallback function that receives the result: boolean, true if push notifications are enabled, false if they are disabled
 */
Adsorb.prototype.arePushNotificationsEnabled = function (resultCallback) {
   exec(resultCallback, null, this.serviceName, 'arePushNotificationsEnabled', []);
};

/**
 * Tell Adsorb that the game is entering the specified screen
 *
 * @param name screen name, such as: main_menu, instructions, level_select, playing, level_end, game_end
 * @param bAllowOverlays true to allow Adsorb to show monetization and marketing overlays on top of some of the game screens,
 *                       false to inform the current screen name only for event logging purposes
 */
Adsorb.prototype.enterGameScreen = function (name, bAllowOverlays) {
   exec(null, null, this.serviceName, 'enterGameScreen', [name, bAllowOverlays]);
};

/**
 * Tell Adsorb that the game is leaving the current screen
 */
Adsorb.prototype.leaveGameScreen = function () {
   exec(null, null, this.serviceName, 'leaveGameScreen', []);
};

/**
 * Report game event
 *
 * @param eventName event name
 */
Adsorb.prototype.reportGameEvent = function (eventName) {
    exec(null, null, this.serviceName, 'reportGameEvent', [eventName]);
};

/**
 * Show the social feed and multiplayer challenges wall for this network
 */
Adsorb.prototype.showMultiplayerWall = function () {
   exec(null, null, this.serviceName, 'showMultiplayerWall', []);
};

/**
 * Report that the local player's current score has changed
 *
 * @param score new score
 */
Adsorb.prototype.updateMultiplayerScore = function (score) {
   exec(null, null, this.serviceName, 'updateMultiplayerScore', [score]);
};

/**
 * Report that the multiplayer game is completed for the local player
 *
 * @param score final score
 */
Adsorb.prototype.completeMultiplayerGame = function (score) {
   exec(null, null, this.serviceName, 'completeMultiplayerGame', [score]);
};

/**
 * Report that the multiplayer game has been canceled
 */
Adsorb.prototype.cancelMultiplayerGame = function () {
   exec(null, null, this.serviceName, 'cancelMultiplayerGame', []);
};

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
Adsorb.prototype.setSuccessAdsAutoMode = function (strSuccessAchievement, strSuccessReward, bUseGameGUI) {
   exec(null, null, this.serviceName, 'setSuccessAdsAutoMode', [strSuccessAchievement, strSuccessReward, bUseGameGUI]);
};

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
Adsorb.prototype.setHelperAdsAutoMode = function (strHelperCaption, strHelperEnticementText, strHelperReward, strHelperButtonLabel, bUseGameGUI) {
   exec(null, null, this.serviceName, 'setHelperAdsAutoMode', [strHelperCaption, strHelperEnticementText, strHelperReward, strHelperButtonLabel, bUseGameGUI]);
};

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
Adsorb.prototype.setNeutralAdsAutoMode = function (strNeutralCaption, strNeutralBody, bUseGameGUI) {
   exec(null, null, this.serviceName, 'setNeutralAdsAutoMode', [strNeutralCaption, strNeutralBody, bUseGameGUI]);
};

/**
 * Complete the setup of automatically load rewarded ads and start loading in the background
 */
Adsorb.prototype.rewardedAdSetupComplete = function() {
   exec(null, null, this.serviceName, 'rewardedAdSetupComplete', []);
};

/**
 * Check if an ad overlay is currently ready to show
 *
 * @param resultCallback function that receives the result: true if an ad overlay is ready, false if not
 */
Adsorb.prototype.isAdOverlayReady = function (resultCallback) {
   exec(resultCallback, null, this.serviceName, 'isAdOverlayReady', []);
};

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
Adsorb.prototype.showAdOverlay = function (x1, y1, x2, y2, nAnimDuration, bShowRectangle) {
   exec(null, null, this.serviceName, 'showAdOverlay', [x1, y1, x2, y2, nAnimDuration, bShowRectangle]);
};

/**
 * Refresh cross-promo ad overlay, if another ad is ready
 */
Adsorb.prototype.advanceAdOverlay = function () {
   exec(null, null, this.serviceName, 'advanceAdOverlay', []);
};

/**
 * Hide cross-promo ad overlay, if one was showing
 *
 * @param nAnimDuration duration of the transition anims, in milliseconds
 */
Adsorb.prototype.hideAdOverlay = function (nAnimDuration) {
   exec(null, null, this.serviceName, 'hideAdOverlay', [nAnimDuration]);
};

// Trampoline for Adsorb events

Adsorb.prototype._onAdsorbEvent = function (info) {
    if (info) {
       // Adsorb sent an event, fire it to the window
       cordova.fireWindowEvent (info.event, info.param);
    }
};

module.exports = Adsorb;

});
