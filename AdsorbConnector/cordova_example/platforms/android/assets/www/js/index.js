/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var adsorb;

var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        console.log('bindEvents');
        document.addEventListener('deviceready', this.onDeviceReady, false);

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

        // Register for demo buttons
        demoButton1.addEventListener('touchstart', this.onShowInterstitialAd, false);
        demoButton2.addEventListener('touchstart', this.onShowMoreGames, false);
        demoButton3.addEventListener('touchstart', this.onShowNewsletter, false);
        demoButton4.addEventListener('touchstart', this.onShowActuallyFreeGames, false);
        demoButton5.addEventListener('touchstart', this.onShowSuccessAd, false);
        demoButton6.addEventListener('touchstart', this.onShowHelperAd, false);
        demoButton7.addEventListener('touchstart', this.onShowNeutralAd, false);
        demoButton8.addEventListener('touchstart', this.onShowAdOverlay, false);
        demoButton9.addEventListener('touchstart', this.onHideAdOverlay, false);
        demoButton10.addEventListener('touchstart', this.onNextAdOverlay, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');

        // Create instance of Adsorb
        adsorb = new Adsorb ();

        // Log if game is injected, as an example of getting a result from the Adsorb plugin
        adsorb.isSdkInjected (function(result) {
           console.log ('Adsorb is injected:' + result);
        });

        // Set custom mediabrix icon
        adsorb.setSuccessAdsVars ("mediabrix", "{ \"icon\": \"https://lh4.ggpht.com/L0t-3DOp_dCh9RMOxzFlVZNN5jhetkuzLzMeVmRIl9KeJkQOw-bKZJ8JHuKetMA_33Jd=w55-rw\" } ");
        adsorb.setHelperAdsVars ("mediabrix", "{ \"icon\": \"https://lh4.ggpht.com/L0t-3DOp_dCh9RMOxzFlVZNN5jhetkuzLzMeVmRIl9KeJkQOw-bKZJ8JHuKetMA_33Jd=w55-rw\" } ");

        // Set push notifications channel (optional - this can also be set from the CMS)
        adsorb.setPushNotificationsChannel ("HiddenObject");

        // Set placement (and allow push notifications overlay)
        adsorb.enterGameScreen("main_menu", true);

        // Enable local notifications (user gets notified from the app after a day)
        adsorb.enableLocalNotificationWithDelay("AdsorbCordovaTester", "Get your free gift!", 24*60*60);

        // Enable auto-loading rewarded ads
        adsorb.setSuccessAdsAutoMode ("Level up!", "100 gold", false);
        adsorb.setHelperAdsAutoMode ("Need more time?", "Watch a short message and get an extra", "minute", "Tap to get more time", false);
        adsorb.setNeutralAdsAutoMode ("Unlock untimed mode", "Watch an offer in a minute or less and unlock untimed mode!", false);
        adsorb.rewardedAdSetupComplete ();
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    },

    // React to demo buttons

    onShowInterstitialAd: function() {
        console.log('onShowInterstitialAd');
        adsorb.showInterstitialAd ();
        adsorb.reportGameEvent ("CordovaInterstitialAd");
    },

    onShowSuccessAd: function() {
        console.log('onShowSuccessAd');
        adsorb.showSuccessAd (null, null);
    },

    onShowHelperAd: function() {
        console.log('onShowHelperAd');
        adsorb.showHelperAd (null, null);
    },

    onShowNeutralAd: function() {
        console.log('onShowNeutralAd');
        adsorb.showNeutralAd (null, null);
    },

    onShowAdOverlay: function() {
        console.log('onShowAdOverlay');
        adsorb.isAdOverlayReady (function(result) {
          if (result)
            adsorb.showAdOverlay (0.1, 0.1, 0.9, 0.35, 300, false);
          else
            console.log('not ad overlay currently ready');
        });
    },

    onHideAdOverlay: function() {
        console.log('onHideAdOverlay');
        adsorb.hideAdOverlay (300);
    },

    onNextAdOverlay: function() {
        console.log('onNextAdOverlay');
        adsorb.advanceAdOverlay ();
    },

    onShowMoreGames: function() {
        console.log('onShowMoreGames');
        adsorb.showMoreGames ();
    },

    onShowNewsletter: function() {
        console.log('onShowNewsletter');
        adsorb.showNewsletter ();
    },

    onShowActuallyFreeGames: function() {
        console.log('onShowActuallyFreeGames');
        adsorb.showActuallyFreeGames ();
    },

    // React to Adsorb events

    onInterstitialAdsAvailable: function() {
        console.log('onInterstitialAdsAvailable');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Interstitial ready";
    },

    onInterstitialAdDismissed: function() {
        console.log('onInterstitialAdDismissed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Interstitial dismissed";
    },

    onInterstitialAdFailed: function() {
        console.log('onInterstitialAdFailed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Interstitial failed";
    },

    onSuccessRewardedAdReady: function() {
        console.log('onSuccessRewardedAdReady');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Success ad ready";
    },

    onSuccessRewardedAdUnavailable: function() {
        console.log('onSuccessRewardedAdUnavailable');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Success ad unavailable";
    },

    onSuccessRewardedAdDismissed: function() {
        console.log('onSuccessRewardedAdDismissed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Success ad dismissed";
    },

    onSuccessRewardedAdFailed: function() {
        console.log('onSuccessRewardedAdFailed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Success ad failed";
    },

    onSuccessRewardGranted: function() {
        console.log('onSuccessRewardGranted');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Success ad reward granted!";
    },

    onHelperRewardedAdReady: function() {
        console.log('onHelperRewardedAdReady');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Helper ad ready";
    },

    onHelperRewardedAdUnavailable: function() {
        console.log('onHelperRewardedAdUnavailable');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Helper ad unavailable";
    },

    onHelperRewardedAdDismissed: function() {
        console.log('onHelperRewardedAdDismissed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Helper ad dismissed";
    },

    onHelperRewardedAdFailed: function() {
        console.log('onHelperRewardedAdFailed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Helper ad failed";
    },

    onHelperRewardGranted: function() {
        console.log('onHelperRewardGranted');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Helper ad reward granted!";
    },

    onNeutralRewardedAdReady: function() {
        console.log('onNeutralRewardedAdReady');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Neutral ad ready";
    },

    onNeutralRewardedAdUnavailable: function() {
        console.log('onNeutralRewardedAdUnavailable');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Neutral ad unavailable";
    },

    onNeutralRewardedAdDismissed: function() {
        console.log('onNeutralRewardedAdDismissed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Neutral ad dismissed";
    },

    onNeutralRewardedAdFailed: function() {
        console.log('onNeutralRewardedAdFailed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Neutral ad failed";
    },

    onNeutralRewardGranted: function() {
        console.log('onNeutralRewardGranted');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "Neutral ad reward granted!";
    },

    onVirtualCurrencyGranted: function(amount) {
        console.log('onVirtualCurrencyGranted: ' + amount);
        document.getElementById('deviceready').querySelector('.received').innerHTML = "VC granted: " + amount;
    },

    onMultiplayerGameStarted: function(randomSeed) {
        console.log('onMultiplayerGameStarted: ' + randomSeed);
        document.getElementById('deviceready').querySelector('.received').innerHTML = "MP game started: " + randomSeed;
    },

    onMultiplayerGameEnded: function() {
        console.log('onMultiplayerGameEnded');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "MP game ended";
    },

    onShowMoreGamesDismissed: function() {
        console.log('onShowMoreGamesDismissed');
        document.getElementById('deviceready').querySelector('.received').innerHTML = "More games dismissed";
    }
};
