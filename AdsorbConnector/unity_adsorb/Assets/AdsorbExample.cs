using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;

public class AdsorbExample : MonoBehaviour
{
    string mStatusText = "Ready";

    void Start()
    {
        Debug.Log("Adsorb Example - Start");
    }

    void OnEnable()
    {
        Debug.Log("Adsorb Example - OnEnable");

        // Register Adsorb event handlers
        AdsorbEvents.adsorbOnInterstitialAdsAvailable += this.adsorbOnInterstitialAdsAvailable;
        AdsorbEvents.adsorbOnInterstitialAdDismissed += this.adsorbOnInterstitialAdDismissed;
        AdsorbEvents.adsorbOnInterstitialAdFailed += this.adsorbOnInterstitialAdFailed;
        AdsorbEvents.adsorbOnSuccessRewardedAdReady += this.adsorbOnSuccessRewardedAdReady;
        AdsorbEvents.adsorbOnSuccessRewardedAdUnavailable += this.adsorbOnSuccessRewardedAdUnavailable;
        AdsorbEvents.adsorbOnSuccessRewardedAdDismissed += this.adsorbOnSuccessRewardedAdDismissed;
        AdsorbEvents.adsorbOnSuccessRewardedAdFailed += this.adsorbOnSuccessRewardedAdFailed;
        AdsorbEvents.adsorbOnSuccessRewardGranted += this.adsorbOnSuccessRewardGranted;
        AdsorbEvents.adsorbOnHelperRewardedAdReady += this.adsorbOnHelperRewardedAdReady;
        AdsorbEvents.adsorbOnHelperRewardedAdUnavailable += this.adsorbOnHelperRewardedAdUnavailable;
        AdsorbEvents.adsorbOnHelperRewardedAdDismissed += this.adsorbOnHelperRewardedAdDismissed;
        AdsorbEvents.adsorbOnHelperRewardedAdFailed += this.adsorbOnHelperRewardedAdFailed;
        AdsorbEvents.adsorbOnHelperRewardGranted += this.adsorbOnHelperRewardGranted;
        AdsorbEvents.adsorbOnNeutralRewardedAdReady += this.adsorbOnNeutralRewardedAdReady;
        AdsorbEvents.adsorbOnNeutralRewardedAdUnavailable += this.adsorbOnNeutralRewardedAdUnavailable;
        AdsorbEvents.adsorbOnNeutralRewardedAdDismissed += this.adsorbOnNeutralRewardedAdDismissed;
        AdsorbEvents.adsorbOnNeutralRewardedAdFailed += this.adsorbOnNeutralRewardedAdFailed;
        AdsorbEvents.adsorbOnNeutralRewardGranted += this.adsorbOnNeutralRewardGranted;
        AdsorbEvents.adsorbOnVirtualCurrencyGranted += this.adsorbOnVirtualCurrencyGranted;
        AdsorbEvents.adsorbOnMultiplayerGameStarted += this.adsorbOnMultiplayerGameStarted;
        AdsorbEvents.adsorbOnMultiplayerGameEnded += this.adsorbOnMultiplayerGameEnded;
        AdsorbEvents.adsorbOnShowMoreGamesDismissed += this.adsorbOnShowMoreGamesDismissed;

        // Set custom Mediabrix icon for success ads
        Debug.Log("Adsorb Example - Set custom Mediabrix icons");
        AdsorbAds.SetSuccessAdsVars("mediabrix", "{ \"icon\": \"https://lh4.ggpht.com/L0t-3DOp_dCh9RMOxzFlVZNN5jhetkuzLzMeVmRIl9KeJkQOw-bKZJ8JHuKetMA_33Jd=w55-rw\" } ");
        AdsorbAds.SetHelperAdsVars("mediabrix", "{ \"icon\": \"https://lh4.ggpht.com/L0t-3DOp_dCh9RMOxzFlVZNN5jhetkuzLzMeVmRIl9KeJkQOw-bKZJ8JHuKetMA_33Jd=w55-rw\" } ");

        // Set placement
        AdsorbAds.EnterGameScreen("main_menu", false);

        // Enable local notifications (user gets notified from the app after a day)
        Debug.Log("Adsorb Example - Enable local notification");
        AdsorbAds.EnableLocalNotificationWithDelay("AdsorbUnityTester", "We miss you! Come play again.", 24*60*60);

        if (AdsorbAds.ArePushNotificationsAvailable())
        {
            Debug.Log("Adsorb Example - Enable push notifications");
            AdsorbAds.setPushNotificationsChannel("Mahjong");
        }

        if (AdsorbAds.ArePushNotificationsEnabled())
        {
            Debug.Log("Adsorb Example - Opted into push notifications");
        }
        else
        {
            Debug.Log("Adsorb Example - Not opted into push notifications");
        }
    }

    void OnDisable()
    {
        Debug.Log("Adsorb Example - OnDisable");

        // Unregister Adsorb event handlers
        AdsorbEvents.adsorbOnInterstitialAdsAvailable -= this.adsorbOnInterstitialAdsAvailable;
        AdsorbEvents.adsorbOnInterstitialAdDismissed -= this.adsorbOnInterstitialAdDismissed;
        AdsorbEvents.adsorbOnInterstitialAdFailed -= this.adsorbOnInterstitialAdFailed;
        AdsorbEvents.adsorbOnSuccessRewardedAdReady -= this.adsorbOnSuccessRewardedAdReady;
        AdsorbEvents.adsorbOnSuccessRewardedAdUnavailable -= this.adsorbOnSuccessRewardedAdUnavailable;
        AdsorbEvents.adsorbOnSuccessRewardedAdDismissed -= this.adsorbOnSuccessRewardedAdDismissed;
        AdsorbEvents.adsorbOnSuccessRewardedAdFailed -= this.adsorbOnSuccessRewardedAdFailed;
        AdsorbEvents.adsorbOnSuccessRewardGranted -= this.adsorbOnSuccessRewardGranted;
        AdsorbEvents.adsorbOnHelperRewardedAdReady -= this.adsorbOnHelperRewardedAdReady;
        AdsorbEvents.adsorbOnHelperRewardedAdUnavailable -= this.adsorbOnHelperRewardedAdUnavailable;
        AdsorbEvents.adsorbOnHelperRewardedAdDismissed -= this.adsorbOnHelperRewardedAdDismissed;
        AdsorbEvents.adsorbOnHelperRewardedAdFailed -= this.adsorbOnHelperRewardedAdFailed;
        AdsorbEvents.adsorbOnHelperRewardGranted -= this.adsorbOnHelperRewardGranted;
        AdsorbEvents.adsorbOnNeutralRewardedAdReady -= this.adsorbOnNeutralRewardedAdReady;
        AdsorbEvents.adsorbOnNeutralRewardedAdUnavailable -= this.adsorbOnNeutralRewardedAdUnavailable;
        AdsorbEvents.adsorbOnNeutralRewardedAdDismissed -= this.adsorbOnNeutralRewardedAdDismissed;
        AdsorbEvents.adsorbOnNeutralRewardedAdFailed -= this.adsorbOnNeutralRewardedAdFailed;
        AdsorbEvents.adsorbOnNeutralRewardGranted -= this.adsorbOnNeutralRewardGranted;
        AdsorbEvents.adsorbOnVirtualCurrencyGranted -= this.adsorbOnVirtualCurrencyGranted;
        AdsorbEvents.adsorbOnMultiplayerGameStarted -= this.adsorbOnMultiplayerGameStarted;
        AdsorbEvents.adsorbOnMultiplayerGameEnded -= this.adsorbOnMultiplayerGameEnded;
        AdsorbEvents.adsorbOnShowMoreGamesDismissed -= this.adsorbOnShowMoreGamesDismissed;
    }

    void OnGUI()
    {
        // Example GUI to trigger the Adsorb ads

        GUI.Box(new Rect(10, 10, 400, 1500), "Adsorb Example");

        if (GUI.Button(new Rect(20, 60, 380, 80), "Interstitial Ad"))
        {
            Debug.Log("Adsorb Example - Show Interstitial Ad");
            mStatusText = "";
            AdsorbAds.ShowInterstitialAd();
            AdsorbAds.ReportGameEvent("InterstitialAd");
        }

        if (GUI.Button(new Rect(20, 160, 380, 80), "Rewarded Ad"))
        {
            Debug.Log("Adsorb Example - Show Rewarded Ad");
            mStatusText = "";
            AdsorbAds.LoadSuccessAd("Level up!", "135 gems", false);
        }

        if (GUI.Button(new Rect(20, 260, 380, 80), "Helper Ad"))
        {
            Debug.Log("Adsorb Example - Show Helper Ad");
            mStatusText = "";
            AdsorbAds.LoadHelperAd("Need more moves?", "Watch a short message and get an extra", "7 moves", "Tap to get moving", false);
        }

        if (GUI.Button(new Rect(20, 360, 380, 80), "Neutral Ad"))
        {
            Debug.Log("Adsorb Example - Show Neutral Ad");
            mStatusText = "";
            AdsorbAds.LoadNeutralAd("Unlock untimed mode", "Watch an offer in a minute or less and unlock untimed mode!", false);
        }

        if (GUI.Button(new Rect(20, 460, 380, 80), "Newsletter"))
        {
            Debug.Log("Adsorb Example - Show Newsletter");
            mStatusText = "";
            AdsorbAds.ShowNewsletter();
        }

        if (GUI.Button(new Rect(20, 560, 380, 80), "More Games"))
        {
            Debug.Log("Adsorb Example - Show More Games");
            mStatusText = "";
            AdsorbAds.ShowMoreGames();
        }

        if (GUI.Button(new Rect(20, 660, 380, 80), "Actually Free"))
        {
            Debug.Log("Adsorb Example - Show Actually Free Games");
            mStatusText = "";
            AdsorbAds.ShowActuallyFreeGames();
        }

        if (GUI.Button(new Rect(20, 760, 380, 80), "Achievement"))
        {
            Debug.Log("Adsorb Example - Report Achievement");
            mStatusText = "";
            AdsorbAds.ReportAchievement("level_completed");
        }

        if (GUI.Button(new Rect(20, 860, 380, 80), "Prompt Incentive"))
        {
            Debug.Log("Adsorb Example - Prompt Incentive");
            mStatusText = "";
            AdsorbAds.PromptIncentive("double_coins", "Double your coins!");
        }


        if (GUI.Button(new Rect(20, 960, 380, 80), "Check Incentive"))
        {
            Debug.Log("Adsorb Example - Check Incentive");
            if (AdsorbAds.IsIncentiveRewardGranted("double_coins"))
                mStatusText = "Incentive ON";
            else
                mStatusText = "Incentive OFF";
        }

        if (GUI.Button(new Rect(20, 1060, 380, 80), "Purchase"))
        {
            Debug.Log("Adsorb Example - Report Purchase");
            mStatusText = "";
            AdsorbAds.ReportPurchase("gold_coins", 3.99);
        }

        if (GUI.Button(new Rect(20, 1160, 380, 80), "Show Ad Overlay"))
        {
            Debug.Log("Adsorb Example - Show Ad Overlay");
            mStatusText = "";
            if (AdsorbAds.IsAdOverlayReady())
                AdsorbAds.ShowAdOverlay(0.1f, 0.1f, 0.9f, 0.6f, 250, false);
            else
                mStatusText = "No ad overlay available";
        }

        if (GUI.Button(new Rect(20, 1260, 380, 80), "Hide Ad Overlay"))
        {
            Debug.Log("Adsorb Example - Hide Ad Overlay");
            mStatusText = "";
            AdsorbAds.HideAdOverlay(250);
        }
        
        if (GUI.Button(new Rect(20, 1360, 380, 80), "Next Ad Overlay"))
        {
            Debug.Log("Adsorb Example - Next Ad Overlay");
            mStatusText = "";
            AdsorbAds.AdvanceAdOverlay();
        }

        GUI.Label(new Rect(20, 1460, 380, 40), mStatusText);
    }

    public void adsorbOnInterstitialAdsAvailable()
    {
        // Interstitial ads available
        Debug.Log("Adsorb Example - adsorbOnInterstitialAdsAvailable");
        mStatusText = "Ads available";
    }

    public void adsorbOnInterstitialAdDismissed()
    {
        // Interstitial ad dismissed
        Debug.Log("Adsorb Example - adsorbOnInterstitialAdDismissed");
        mStatusText = "Dismissed";
    }

    public void adsorbOnInterstitialAdFailed()
    {
        // Interstitial ad failed
        Debug.Log("Adsorb Example - adsorbOnInterstitialAdFailed");
        mStatusText = "Failed";
    }

    public void adsorbOnSuccessRewardedAdReady(string filterName)
    {
        // Rewarded ad loaded and ready to show
        Debug.Log("Adsorb Example - adsorbOnSuccessRewardedAdReady for filter '" + filterName + "'"
                    + ", network: " + AdsorbAds.GetSuccessAdNetworkName(filterName)
                    + ", type: " + AdsorbAds.GetSuccessAdNetworkType(filterName)
                    + ", tags: " + AdsorbAds.GetSuccessAdNetworkTags(filterName)
                    + ", has GUI: " + AdsorbAds.DoesSuccessAdProvideGUI(filterName));

        // Show loaded video
        AdsorbAds.ShowSuccessAd(null, filterName);
    }

    public void adsorbOnSuccessRewardedAdUnavailable(string filterName)
    {
        // Rewarded ad unavailable
        Debug.Log("Adsorb Example - adsorbOnSuccessRewardedAdUnavailable for filter '" + filterName + "'");
        mStatusText = "Unavailable";
    }

    public void adsorbOnSuccessRewardedAdDismissed(string filterName)
    {
        // Rewarded ad dismissed
        Debug.Log("Adsorb Example - adsorbOnSuccessRewardedAdDismissed for filter '" + filterName + "'");
    }

    public void adsorbOnSuccessRewardedAdFailed(string filterName)
    {
        // Rewarded ad failed
        Debug.Log("Adsorb Example - adsorbOnSuccessRewardedAdFailed for filter '" + filterName + "'");
        mStatusText = "Failed";
    }

    public void adsorbOnSuccessRewardGranted(string filterName)
    {
        // Reward granted for success ad
        Debug.Log("Adsorb Example - adsorbOnSuccessRewardGranted for filter '" + filterName + "'");
        mStatusText = "Reward granted";
    }

    public void adsorbOnHelperRewardedAdReady(string filterName)
    {
        // Helper ad loaded and ready to show
        Debug.Log("Adsorb Example - adsorbOnHelperRewardedAdReady for filter '" + filterName + "'"
                    + ", network: " + AdsorbAds.GetHelperAdNetworkName(filterName)
                    + ", type: " + AdsorbAds.GetHelperAdNetworkType(filterName)
                    + ", tags: " + AdsorbAds.GetHelperAdNetworkTags(filterName)
                    + ", has GUI: " + AdsorbAds.DoesHelperAdProvideGUI(filterName));

        // Show loaded video
        AdsorbAds.ShowHelperAd(null, filterName);
    }

    public void adsorbOnHelperRewardedAdUnavailable(string filterName)
    {
        // Helper ad unavailable
        Debug.Log("Adsorb Example - adsorbOnHelperRewardedAdUnavailable for filter '" + filterName + "'");
        mStatusText = "Unavailable";
    }

    public void adsorbOnHelperRewardedAdDismissed(string filterName)
    {
        // Helper ad dismissed
        Debug.Log("Adsorb Example - adsorbOnHelperRewardedAdDismissed for filter '" + filterName + "'");
    }

    public void adsorbOnHelperRewardedAdFailed(string filterName)
    {
        // Helper ad failed
        Debug.Log("Adsorb Example - adsorbOnHelperRewardedAdFailed for filter '" + filterName + "'");
        mStatusText = "Failed";
    }

    public void adsorbOnHelperRewardGranted(string filterName)
    {
        // Reward granted for success ad
        Debug.Log("Adsorb Example - adsorbOnHelperRewardGranted for filter '" + filterName + "'");
        mStatusText = "Reward granted";
    }

    public void adsorbOnNeutralRewardedAdReady(string filterName)
    {
        // Neutral ad loaded and ready to show
        Debug.Log("Adsorb Example - adsorbOnNeutralRewardedAdReady for filter '" + filterName + "'"
                    + ", network: " + AdsorbAds.GetNeutralAdNetworkName(filterName)
                    + ", type: " + AdsorbAds.GetNeutralAdNetworkType(filterName)
                    + ", tags: " + AdsorbAds.GetNeutralAdNetworkTags(filterName)
                    + ", has GUI: " + AdsorbAds.DoesNeutralAdProvideGUI(filterName));

        // Show loaded video
        AdsorbAds.ShowNeutralAd(null, filterName);
    }

    public void adsorbOnNeutralRewardedAdUnavailable(string filterName)
    {
        // Neutral ad unavailable
        Debug.Log("Adsorb Example - adsorbOnNeutralRewardedAdUnavailable for filter '" + filterName + "'");
        mStatusText = "Unavailable";
    }

    public void adsorbOnNeutralRewardedAdDismissed(string filterName)
    {
        // Neutral ad dismissed
        Debug.Log("Adsorb Example - adsorbOnNeutralRewardedAdDismissed for filter '" + filterName + "'");
    }

    public void adsorbOnNeutralRewardedAdFailed(string filterName)
    {
        // Neutral ad failed
        Debug.Log("Adsorb Example - adsorbOnNeutralRewardedAdFailed for filter '" + filterName + "'"); ;
        mStatusText = "Failed";
    }

    public void adsorbOnNeutralRewardGranted(string filterName)
    {
        // Reward granted for success ad
        Debug.Log("Adsorb Example - adsorbOnNeutralRewardGranted for filter '" + filterName + "'");
        mStatusText = "Reward granted";
    }

    public void adsorbOnVirtualCurrencyGranted(int amount)
    {
        // Virtual currency granted
        Debug.Log("Adsorb Example - adsorbOnVirtualCurrencyGranted, amount: " + amount);
        mStatusText = amount + " coins granted";
    }

    public void adsorbOnMultiplayerGameStarted(int randomSeed)
    {
        // Multiplayer game started
        Debug.Log("Adsorb Example - adsorbOnMultiplayerGameStarted, random seed: " + randomSeed);
        mStatusText = "Multiplayer start";
    }

    public void adsorbOnMultiplayerGameEnded()
    {
        // Multiplayer game ended
        Debug.Log("Adsorb Example - adsorbOnMultiplayerGameEnded");
        mStatusText = "Multiplayer end";
    }

    public void adsorbOnShowMoreGamesDismissed()
    {
        // "Show more games" browser dismissed
        Debug.Log("Adsorb Example - adsorbOnShowMoreGamesDismissed");
        mStatusText = "Dismissed";
    }
}
