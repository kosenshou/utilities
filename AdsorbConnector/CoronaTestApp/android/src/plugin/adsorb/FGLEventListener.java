package plugin.adsorb;

public interface FGLEventListener {	
	/**
	 * Interstitial ad networks are initialized, the app can now request ad interstitials
	 */
	public abstract void onInterstitialAdsAvailable ();
	
	/**
	 * An interstitial ad requested with FGLConnector.showInterstitialAd() has been displayed and the host app can resume
	 */
	public abstract void onInterstitialAdDismissed();
	
	/**
	 * An interstitial ad requested with FGLConnector.showInterstitialAd() failed to display altogether
	 */
	public abstract void onInterstitialAdFailed();
	
	/**
	 * A success rewarded ad is loaded and ready to show
	 * 
	 * @param filterName name of filter that success rewarded ad is ready for
	 */
	public abstract void onSuccessRewardedAdReady (String filterName);
	
	/**
	 * No success rewarded ad is available
	 * 
	 * @param filterName name of filter that success rewarded ad is unavailable for
	 */
	public abstract void onSuccessRewardedAdUnavailable (String filterName);
	
	/**
	 * A success rewarded ad was dismissed
	 * 
	 * @param filterName name of filter for which the ad was dismissed
	 */
	public abstract void onSuccessRewardedAdDismissed (String filterName);
	   
	/**
	 * A success rewarded ad failed to display altogether
	 * 
	 * @param filterName name of filter for which the ad failed to display
	 */
	public abstract void onSuccessRewardedAdFailed (String filterName);
	
	/**
	 * The reward for a success ad is granted
	 * 
	 * @param filterName name of filter for which the reward was granted
	 */
	public abstract void onSuccessRewardGranted (String filterName);
	   
	/**
	 * A helper rewarded ad is loaded and ready to show
	 * 
	 * @param filterName name of filter that helper rewarded ad is ready for
	 */
	public abstract void onHelperRewardedAdReady (String filterName);
	
	/**
	 * No helper rewarded ad is ready to show
	 * 
	 * @param filterName name of filter that helper rewarded ad is unavailable for
	 */
	public abstract void onHelperRewardedAdUnavailable (String filterName);
	
	/**
	 * A helper rewarded ad was dismissed
	 * 
	 * @param filterName name of filter for which the ad was dismissed
	 */
	public abstract void onHelperRewardedAdDismissed (String filterName);
	   	
	/**
	 * A helper rewarded ad failed to display altogether
	 * 
	 * @param filterName name of filter for which the ad failed to display
	 */
	public abstract void onHelperRewardedAdFailed (String filterName);
	
	/**
	 * The reward for a helper ad is granted
	 * 
	 * @param filterName name of filter for which the reward was granted
	 */
	public abstract void onHelperRewardGranted (String filterName);

	/**
	 * A neutral rewarded ad is loaded and ready to show
	 * 
	 * @param filterName name of filter that neutral rewarded ad is ready for
	 */
	public abstract void onNeutralRewardedAdReady (String filterName);
	
	/**
	 * No neutral rewarded ad is ready to show
	 * 
	 * @param filterName name of filter that neutral rewarded ad is unavailable for
	 */
	public abstract void onNeutralRewardedAdUnavailable (String filterName);
	
	/**
	 * A neutral rewarded ad was dismissed
	 * 
	 * @param filterName name of filter for which the ad was dismissed
	 */
	public abstract void onNeutralRewardedAdDismissed (String filterName);
	   	
	/**
	 * A neutral rewarded ad failed to display altogether
	 * 
	 * @param filterName name of filter for which the ad failed to display
	 */
	public abstract void onNeutralRewardedAdFailed (String filterName);
	
	/**
	 * The reward for a neutral ad is granted
	 * 
	 * @param filterName name of filter for which the reward was granted
	 */
	public abstract void onNeutralRewardGranted (String filterName);
	
	/**
	 * Virtual currency is granted
	 * 
	 * @param amount amount of currency granted (for instance, number of coins)
	 */
	public abstract void onVirtualCurrencyGranted (int amount);
	
	/**
	 * Start a multiplayer game now
	 * 
	 * @param randomSeed value to use for seeding random numbers, so that all players play the same game
	 */
	public abstract void onMultiplayerGameStarted (int randomSeed);
	
	/**
	 * End a multiplayer game now
	 */
	public abstract void onMultiplayerGameEnded ();
	
	/**
	 * The "show more games" browser has been dismissed
	 */
	public abstract void onShowMoreGamesDismissed ();	
}
