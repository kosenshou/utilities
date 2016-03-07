/**
 * Adsorb events for Unity
 */

using UnityEngine;
using System.Collections;

public class AdsorbEvents : MonoBehaviour
{
    // Interstitial ads available
    public delegate void AdsorbOnInterstitialAdsAvailableHandler();
    public static event AdsorbOnInterstitialAdsAvailableHandler adsorbOnInterstitialAdsAvailable;

    // Interstitial ad dismissed
    public delegate void AdsorbOnInterstitialAdDismissedHandler();
    public static event AdsorbOnInterstitialAdDismissedHandler adsorbOnInterstitialAdDismissed;

    // Interstitial ad failed
    public delegate void AdsorbOnInterstitialAdFailedHandler();
    public static event AdsorbOnInterstitialAdFailedHandler adsorbOnInterstitialAdFailed;

    // Rewarded ad ready
    public delegate void AdsorbOnSuccessRewardedAdReadyHandler(string filterName);
    public static event AdsorbOnSuccessRewardedAdReadyHandler adsorbOnSuccessRewardedAdReady;

    // Rewarded ad unavailable
    public delegate void AdsorbOnSuccessRewardedAdUnavailableHandler(string filterName);
    public static event AdsorbOnSuccessRewardedAdUnavailableHandler adsorbOnSuccessRewardedAdUnavailable;

    // Rewarded ad dismissed
    public delegate void AdsorbOnSuccessRewardedAdDismissedHandler(string filterName);
    public static event AdsorbOnSuccessRewardedAdDismissedHandler adsorbOnSuccessRewardedAdDismissed;

    // Rewarded ad failed
    public delegate void AdsorbOnSuccessRewardedAdFailedHandler(string filterName);
    public static event AdsorbOnSuccessRewardedAdFailedHandler adsorbOnSuccessRewardedAdFailed;

    // Reward granted for success ad
    public delegate void AdsorbOnSuccessRewardGrantedHandler(string filterName);
    public static event AdsorbOnSuccessRewardGrantedHandler adsorbOnSuccessRewardGranted;

    // Helper ad ready
    public delegate void AdsorbOnHelperRewardedAdReadyHandler(string filterName);
    public static event AdsorbOnHelperRewardedAdReadyHandler adsorbOnHelperRewardedAdReady;

    // Helper ad unavailable
    public delegate void AdsorbOnHelperRewardedAdUnavailableHandler(string filterName);
    public static event AdsorbOnHelperRewardedAdUnavailableHandler adsorbOnHelperRewardedAdUnavailable;

    // Helper ad dismissed
    public delegate void AdsorbOnHelperRewardedAdDismissedHandler(string filterName);
    public static event AdsorbOnHelperRewardedAdDismissedHandler adsorbOnHelperRewardedAdDismissed;

    // Helper ad failed
    public delegate void AdsorbOnHelperRewardedAdFailedHandler(string filterName);
    public static event AdsorbOnHelperRewardedAdFailedHandler adsorbOnHelperRewardedAdFailed;

    // Reward granted for helper ad
    public delegate void AdsorbOnHelperRewardGrantedHandler(string filterName);
    public static event AdsorbOnHelperRewardGrantedHandler adsorbOnHelperRewardGranted;

    // Neutral ad ready
    public delegate void AdsorbOnNeutralRewardedAdReadyHandler(string filterName);
    public static event AdsorbOnNeutralRewardedAdReadyHandler adsorbOnNeutralRewardedAdReady;

    // Neutral ad unavailable
    public delegate void AdsorbOnNeutralRewardedAdUnavailableHandler(string filterName);
    public static event AdsorbOnNeutralRewardedAdUnavailableHandler adsorbOnNeutralRewardedAdUnavailable;

    // Neutral ad dismissed
    public delegate void AdsorbOnNeutralRewardedAdDismissedHandler(string filterName);
    public static event AdsorbOnNeutralRewardedAdDismissedHandler adsorbOnNeutralRewardedAdDismissed;

    // Neutral ad failed
    public delegate void AdsorbOnNeutralRewardedAdFailedHandler(string filterName);
    public static event AdsorbOnNeutralRewardedAdFailedHandler adsorbOnNeutralRewardedAdFailed;

    // Reward granted for neutral ad
    public delegate void AdsorbOnNeutralRewardGrantedHandler(string filterName);
    public static event AdsorbOnNeutralRewardGrantedHandler adsorbOnNeutralRewardGranted;

    // Virtual currency granted
    public delegate void AdsorbOnVirtualCurrencyGrantedHandler(int amount);
    public static event AdsorbOnVirtualCurrencyGrantedHandler adsorbOnVirtualCurrencyGranted;

    // Multiplayer game started
    public delegate void AdsorbOnMultiplayerGameStartedHandler(int randomSeed);
    public static event AdsorbOnMultiplayerGameStartedHandler adsorbOnMultiplayerGameStarted;

    // Multiplayer game ended
    public delegate void AdsorbOnMultiplayerGameEndedHandler();
    public static event AdsorbOnMultiplayerGameEndedHandler adsorbOnMultiplayerGameEnded;

    // More games browser dismissed
    public delegate void AdsorbOnShowMoreGamesDismissedHandler();
    public static event AdsorbOnShowMoreGamesDismissedHandler adsorbOnShowMoreGamesDismissed;

    /**
     * Initialize - you must attach this script to one of your game objects
     */
    void Awake()
    {
        AndroidJavaClass unityConnectorClass = new AndroidJavaClass("com.fgl.unityconnector.FGLUnityConnector");
        unityConnectorClass.CallStatic("setMessageTarget", gameObject.name);

        DontDestroyOnLoad(this);
    }

    /* Event trampolines */

    public void AdsorbOnInterstitialAdsAvailableCallback(string empty)
    {
        if (adsorbOnInterstitialAdsAvailable != null)
            adsorbOnInterstitialAdsAvailable();
    }

    public void AdsorbOnInterstitialAdDismissedCallback(string empty)
    {
        if (adsorbOnInterstitialAdDismissed != null)
            adsorbOnInterstitialAdDismissed();
    }

    public void AdsorbOnInterstitialAdFailedCallback(string empty)
    {
        if (adsorbOnInterstitialAdFailed != null)
            adsorbOnInterstitialAdFailed();
    }

    public void AdsorbOnSuccessRewardedAdReadyCallback(string filterName)
    {
        if (adsorbOnSuccessRewardedAdReady != null)
            adsorbOnSuccessRewardedAdReady(filterName);
    }

    public void AdsorbOnSuccessRewardedAdUnavailableCallback(string filterName)
    {
        if (adsorbOnSuccessRewardedAdUnavailable != null)
            adsorbOnSuccessRewardedAdUnavailable(filterName);
    }

    public void AdsorbOnSuccessRewardedAdDismissedCallback(string filterName)
    {
        if (adsorbOnSuccessRewardedAdDismissed != null)
            adsorbOnSuccessRewardedAdDismissed(filterName);
    }

    public void AdsorbOnSuccessRewardedAdFailedCallback(string filterName)
    {
        if (adsorbOnSuccessRewardedAdFailed != null)
            adsorbOnSuccessRewardedAdFailed(filterName);
    }

    public void AdsorbOnSuccessRewardGrantedCallback(string filterName)
    {
        if (adsorbOnSuccessRewardGranted != null)
            adsorbOnSuccessRewardGranted(filterName);
    }

    public void AdsorbOnHelperRewardedAdReadyCallback(string filterName)
    {
        if (adsorbOnHelperRewardedAdReady != null)
            adsorbOnHelperRewardedAdReady(filterName);
    }

    public void AdsorbOnHelperRewardedAdUnavailableCallback(string filterName)
    {
        if (adsorbOnHelperRewardedAdUnavailable != null)
            adsorbOnHelperRewardedAdUnavailable(filterName);
    }

    public void AdsorbOnHelperRewardedAdDismissedCallback(string filterName)
    {
        if (adsorbOnHelperRewardedAdDismissed != null)
            adsorbOnHelperRewardedAdDismissed(filterName);
    }

    public void AdsorbOnHelperRewardedAdFailedCallback(string filterName)
    {
        if (adsorbOnHelperRewardedAdFailed != null)
            adsorbOnHelperRewardedAdFailed(filterName);
    }

    public void AdsorbOnHelperRewardGrantedCallback(string filterName)
    {
        if (adsorbOnHelperRewardGranted != null)
            adsorbOnHelperRewardGranted(filterName);
    }

    public void AdsorbOnNeutralRewardedAdReadyCallback(string filterName)
    {
        if (adsorbOnNeutralRewardedAdReady != null)
            adsorbOnNeutralRewardedAdReady(filterName);
    }

    public void AdsorbOnNeutralRewardedAdUnavailableCallback(string filterName)
    {
        if (adsorbOnNeutralRewardedAdUnavailable != null)
            adsorbOnNeutralRewardedAdUnavailable(filterName);
    }

    public void AdsorbOnNeutralRewardedAdDismissedCallback(string filterName)
    {
        if (adsorbOnNeutralRewardedAdDismissed != null)
            adsorbOnNeutralRewardedAdDismissed(filterName);
    }

    public void AdsorbOnNeutralRewardedAdFailedCallback(string filterName)
    {
        if (adsorbOnNeutralRewardedAdFailed != null)
            adsorbOnNeutralRewardedAdFailed(filterName);
    }

    public void AdsorbOnNeutralRewardGrantedCallback(string filterName)
    {
        if (adsorbOnNeutralRewardGranted != null)
            adsorbOnNeutralRewardGranted(filterName);
    }

    public void AdsorbOnVirtualCurrencyGrantedCallback(string amount)
    {
        if (adsorbOnVirtualCurrencyGranted != null)
            adsorbOnVirtualCurrencyGranted(System.Int32.Parse(amount));
    }

    public void AdsorbOnMultiplayerGameStartedCallback(string randomSeed)
    {
        if (adsorbOnMultiplayerGameStarted != null)
            adsorbOnMultiplayerGameStarted(System.Int32.Parse(randomSeed));
    }

    public void AdsorbOnMultiplayerGameEndedCallback(string dummy)
    {
        if (adsorbOnMultiplayerGameEnded != null)
            adsorbOnMultiplayerGameEnded();
    }

    public void AdsorbOnShowMoreGamesDismissedCallback(string dummy)
    {
        if (adsorbOnShowMoreGamesDismissed != null)
            adsorbOnShowMoreGamesDismissed();
    }
}
