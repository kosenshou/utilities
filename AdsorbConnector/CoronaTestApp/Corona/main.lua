local adsorb = require "plugin.adsorb"
local widget = require "widget"

local function delegateListener( event )
end
Runtime:addEventListener( "delegate", delegateListener )

-- Adsorb events handler
local function listener( event )
  if (event.type == "onInterstitialAdsAvailable") then
    native.showAlert( "Adsorb event", "Interstitial ad ready", { "OK" } )
  end
	if (event.type == "onInterstitialAdDismissed") then
		native.showAlert( "Adsorb event", "Interstitial ad dismissed", { "OK" } )
	end
	if (event.type == "onInterstitialAdFailed") then
		native.showAlert( "Adsorb event", "Interstitial ad failed", { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdReady") then
		native.showAlert( "Adsorb event", "Success ad ready for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdUnavailable") then
		native.showAlert( "Adsorb event", "Success ad unavailable for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdDismissed") then
		native.showAlert( "Adsorb event", "Success ad dismissed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardedAdFailed") then
		native.showAlert( "Adsorb event", "Success ad failed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onSuccessRewardGranted") then
		native.showAlert( "Adsorb event", "Success ad reward granted for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdReady") then
		native.showAlert( "Adsorb event", "Helper ad ready for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdUnavailable") then
		native.showAlert( "Adsorb event", "Helper ad unavailable for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdDismissed") then
		native.showAlert( "Adsorb event", "Helper ad dismissed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardedAdFailed") then
		native.showAlert( "Adsorb event", "Helper ad failed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onHelperRewardGranted") then
		native.showAlert( "Adsorb event", "Helper ad reward granted for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdReady") then
		native.showAlert( "Adsorb event", "Neutral ad ready for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdUnavailable") then
		native.showAlert( "Adsorb event", "Neutral ad unavailable for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdDismissed") then
		native.showAlert( "Adsorb event", "Neutral ad dismissed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardedAdFailed") then
		native.showAlert( "Adsorb event", "Neutral ad failed for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onNeutralRewardGranted") then
		native.showAlert( "Adsorb event", "Neutral ad granted for filter " .. event.param, { "OK" } )
	end
	if (event.type == "onVirtualCurrencyGranted") then
		native.showAlert( "Adsorb event", "Virtual coins granted: " .. tonumber (event.param), { "OK" } )
	end
	if (event.type == "onMultiplayerGameStarted") then
		native.showAlert( "Adsorb event", "Multiplayer game started with seed: " .. tonumber (event.param), { "OK" } )
	end
	if (event.type == "onMultiplayerGameEnded") then
		native.showAlert( "Adsorb event", "Multiplayer game ended", { "OK" } )
	end
	if (event.type == "onShowMoreGamesDismissed") then
		native.showAlert( "Adsorb event", "Show more games dismissed", { "OK" } )
	end
end

-- Initialise Adsorb
adsorb.init( listener )
adsorb.enterGameScreen ( "main_menu", true )
adsorb.enableLocalNotificationWithDelay ( "AdsorbCoronaTester", "We miss you! Come play again.", 24*60*60 )
adsorb.setSuccessAdsAutoMode ( "Level up!", "100 gold", false )
adsorb.setHelperAdsAutoMode ( "Need more time?", "Watch a short message and get an extra", "minute", "Tap to get more time", false )
adsorb.setNeutralAdsAutoMode ( "Unlock untimed mode", "Watch an offer in a minute or less and unlock untimed mode!" ,false )
adsorb.rewardedAdSetupComplete ( )

-- Button event handlers

local onShowInterstitialAd = function( event)
   adsorb.showInterstitialAd ( )
	 adsorb.reportGameEvent ("CoronaInterstitialAd")
end

local onShowSuccessAd = function( event)
	adsorb.showSuccessAd ( nil, nil )
end

local onShowHelperAd = function( event)
	adsorb.showHelperAd ( nil, nil )
end

local onShowNeutralAd = function( event)
	adsorb.showNeutralAd ( nil, nil )
end

local onShowMoreGames = function( event)
	adsorb.showMoreGames ( )
end

-- Define buttons to trigger Adsorb functions

local showInterstitialAdButton = widget.newButton
{
    x = display.contentWidth * 0.5,
		y = 140,
    width = 200,
		height = 50,
		label = "Show interstitial",
		onRelease = onShowInterstitialAd
}

local showSuccessAdButton = widget.newButton
{
		x = display.contentWidth * 0.5,
		y = 200,
		width = 200,
		height = 50,
		label = "Show success ad",
		onRelease = onShowSuccessAd
}

local showHelperAdButton = widget.newButton
{
		x = display.contentWidth * 0.5,
		y = 260,
		width = 200,
		height = 50,
		label = "Show helper ad",
		onRelease = onShowHelperAd
}

local showNeutralAdButton = widget.newButton
{
		x = display.contentWidth * 0.5,
		y = 320,
		width = 200,
		height = 50,
		label = "Show neutral ad",
		onRelease = onShowNeutralAd
}

local showMoreGamesButton = widget.newButton
{
		x = display.contentWidth * 0.5,
		y = 380,
		width = 200,
		height = 50,
		label = "Show more games",
		onRelease = onShowMoreGames
}
