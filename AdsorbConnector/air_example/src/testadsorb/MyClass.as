package testadsorb
{
	import com.fgl.airconnector.Adsorb;
	import com.fgl.airconnector.AdsorbEvent;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.display.Stage;
	import flash.display.MovieClip;
	import flash.desktop.NativeApplication;

	import ie.jampot.nativeExtensions.Alert;
	import ie.jampot.nativeExtensions.AlertEvent;
	
	public class MyClass
	{
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
			
			// Set custom icon for Mediabrix success ads
			adsorb.setSuccessAdsVars ("mediabrix", "{ \"icon\": \"https://lh4.ggpht.com/L0t-3DOp_dCh9RMOxzFlVZNN5jhetkuzLzMeVmRIl9KeJkQOw-bKZJ8JHuKetMA_33Jd=w55-rw\" } ");
			
			// Set placement
			adsorb.enterGameScreen("main_menu", false);
			
			// Enable local notifications (user gets notified from the app after a day)
			adsorb.enableLocalNotificationWithDelay("AdsorbAIRTester", "We miss you! Come play again.", 24*60*60);

			// Opt into push notifications
			if (adsorb.arePushNotificationsAvailable ()) {
				adsorb.setPushNotificationsChannel ("PuzzleSpin");
				adsorb.enablePushNotifications ();
			}
			
			if (adsorb.arePushNotificationsEnabled ()) {
				var aErr : Alert = new Alert();
				aErr.showAlert("Opted-in");				
			}
		}
		
		static public function showInterstitialEventHandler(e:Event):void {
         	// Demo: show ad interstitial

			try {				
				adsorb.showInterstitialAd ();
            adsorb.reportGameEvent ("InterstitialAd");
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function showRewardVideoEventHandler(e:Event):void {
         // Demo: load success rewarded ad

			try {				
				adsorb.loadSuccessAd ("Level up!", "135 gems", false);
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}

		static public function showHelperVideoEventHandler(e:Event):void {
         	// Demo: load helper rewarded ad

			try {				
				adsorb.loadHelperAd ("Need more moves?", "Watch a short message and get an extra", "7 moves", "Tap to get moving", false);
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}

		static public function showNeutralVideoEventHandler(e:Event):void {
			// Demo: load neutral rewarded ad
			
			try {				
				adsorb.loadNeutralAd ("Unlock untimed mode", "Watch an offer in a minute or less and unlock untimed mode!", false);
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function showNewsletter(e:Event):void {
			// Demo: show newsletter
			
			try {				
				adsorb.showNewsletter ();
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function showMoreGames(e:Event):void {
			// Demo: show more games browser
			
			try {				
				adsorb.showMoreGames ();
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function showActuallyFreeGames(e:Event):void {
			// Demo: show actually free games page
			
			try {				
				adsorb.showActuallyFreeGames ();
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function reportAchievement(e:Event):void {
			// Demo: report achievement
			
			try {				
				adsorb.reportAchievement ("level_completed");
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
				
		static public function promptIncentive(e:Event):void {
			// Demo: prompt user to perform action for an incentive
			
			try {				
				adsorb.promptIncentive ("double_coins", "Double your coins!");
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function checkIncentive(e:Event):void {
			// Demo: check if incentive's reward is currently granted
			
			try {				
				var aResult : Alert = new Alert();
				if (adsorb.isIncentiveRewardGranted("double_coins"))
					aResult.showAlert("Incentive is granted");
				else
					aResult.showAlert("Incentive is NOT granted");
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function reportPurchase(e:Event):void {
			// Demo: report purchase
			
			try {				
				adsorb.reportPurchase ("gold_coins", 3.99);
			} catch (e:Error) {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("Error: " + e.toString());
			}
		}
		
		static public function showAdOverlay(e:Event):void {
			if (adsorb.isAdOverlayReady ())
				adsorb.showAdOverlay (0.1, 0.1, 0.9, 0.5, 300, false);
			else {
				// Exception
				var aErr : Alert = new Alert();
				aErr.showAlert("No ad overlay currently ready");
			}
		}

		static public function advanceAdOverlay(e:Event):void {
			adsorb.advanceAdOverlay ();
		}
		
		static public function hideAdOverlay(e:Event):void {
			adsorb.hideAdOverlay (300);
		}

		static public function onInterstitialAdsAvailable(e:AdsorbEvent):void  { 
			// Interstitial ads available
		}		
		
		static public function onInterstitialAdDismissed(e:AdsorbEvent):void  { 
			// Interstitial ad dismissed
		}		

		static public function onInterstitialAdFailed(e:AdsorbEvent):void  { 
			// Interstitial ad failed to present altogether
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Interstitial failed");
		}		
		
		static public function onSuccessRewardedAdReady(e:AdsorbEvent):void  { 
			// Reward video loaded and ready to show
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Rewarded video ready (" + adsorb.getSuccessAdNetworkName(e.param) + ") for " + e.param);
			
			// Show loaded ad
			adsorb.showSuccessAd (null, e.param);
		}

		static public function onSuccessRewardedAdUnavailable(e:AdsorbEvent):void  { 
			// Reward video unavailable
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Rewarded video unavailable");
		}

		static public function onSuccessRewardedAdDismissed(e:AdsorbEvent):void  { 
			// Reward video dismissed
		}

		static public function onSuccessRewardedAdFailed(e:AdsorbEvent):void  { 
			// Reward video failed to present altogether
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Rewarded video failed");
		}
		
		static public function onSuccessRewardGranted(e:AdsorbEvent):void  { 
			// Reward granted for reward video
			var aAlert : Alert = new Alert();
			aAlert.showAlert("The reward has been granted.");				
		}

		static public function onHelperRewardedAdReady(e:AdsorbEvent):void  { 
			// Reward video loaded and ready to show
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Helper video ready (" + adsorb.getHelperAdNetworkName(e.param) + ") for " + e.param);
			
			// Show loaded ad
			adsorb.showHelperAd (null, e.param);
		}

		static public function onHelperRewardedAdUnavailable(e:AdsorbEvent):void  { 
			// Reward video unavailable
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Helper video unavailable");
		}

		static public function onHelperRewardedAdDismissed(e:AdsorbEvent):void  { 
			// Reward video dismissed
		}

		static public function onHelperRewardedAdFailed(e:AdsorbEvent):void  { 
			// Reward video failed to present altogether
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Helper video failed");
		}
		
		static public function onHelperRewardGranted(e:AdsorbEvent):void  { 
			// Reward granted for helper video
			var aAlert : Alert = new Alert();
			aAlert.showAlert("The reward has been granted.");				
		}
		
		static public function onNeutralRewardedAdReady(e:AdsorbEvent):void  { 
			// Reward video loaded and ready to show
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Neutral video ready (" + adsorb.getNeutralAdNetworkName(e.param) + ") for " + e.param);
			
			// Show loaded ad
			adsorb.showNeutralAd (null, e.param);
		}
		
		static public function onNeutralRewardedAdUnavailable(e:AdsorbEvent):void  { 
			// Reward video unavailable
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Neutral video unavailable");
		}
		
		static public function onNeutralRewardedAdDismissed(e:AdsorbEvent):void  { 
			// Reward video dismissed
		}
		
		static public function onNeutralRewardedAdFailed(e:AdsorbEvent):void  { 
			// Reward video failed to present altogether
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Neutral video failed");
		}
		
		static public function onNeutralRewardGranted(e:AdsorbEvent):void  { 
			// Reward granted for helper video
			var aAlert : Alert = new Alert();
			aAlert.showAlert("The reward has been granted.");				
		}
		
		static public function onVirtualCurrencyGranted(e:AdsorbEvent):void  { 
			// Virtual currency granted; the amount is in e.param
			var aAlert : Alert = new Alert();
			aAlert.showAlert(e.param + " coins granted");				
		}
		
		static public function onMultiplayerGameStarted(e:AdsorbEvent):void  { 
			// Multiplayer game started; the random seed is in e.param
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Multiplayer game started, seed: " + e.param);				
		}
		
		static public function onMultiplayerGameEnded(e:AdsorbEvent):void  { 
			// Multiplayer game ended
			var aAlert : Alert = new Alert();
			aAlert.showAlert("Multiplayer game ended.");				
		}
		
		static public function onShowMoreGamesDismissed(e:Event):void  { 
			// Show more games browser dismissed
		}
	}
}
