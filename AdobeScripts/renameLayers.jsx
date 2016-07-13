﻿//  
// This script exports extended layer.bounds information to [psd_file_name].xml  
// by pattesdours  
//  
  
function docCheck() {  
  // ensure that there is at least one document open  
  if (!documents.length) {  
  alert('There are no documents open.');  
  return; // quit  
  }  
}  
  
docCheck();  
  
var originalRulerUnits = preferences.rulerUnits;  
preferences.rulerUnits = Units.PIXELS;  
  
var docRef = activeDocument;  
docRef.flipCanvas(Direction.VERTICAL);
//docRef.resizeImage(UnitValue(1000, "px"), UnitValue(580, "px"));
  
var docWidth = docRef.width.value;  
var docHeight = docRef.height.value;  
var mySourceFilePath = activeDocument.fullName.path + "/";  
  
// Code to get layer index / descriptor  
//  
cTID = function(s) { return app.charIDToTypeID(s); };  
sTID = function(s) { return app.stringIDToTypeID(s); };  
function getLayerDescriptor (doc, layer) {  
  var ref = new ActionReference();  
  ref.putEnumerated(cTID("Lyr "), cTID("Ordn"), cTID("Trgt"));  
  return executeActionGet(ref)  
};  
  
function getLayerID(doc, layer) {  
  var d = getLayerDescriptor(doc, layer);  
  return d.getInteger(cTID('LyrI'));  
};  
  
var stackorder = 0;  
  
// function from Xbytor to traverse all layers  
traverseLayers = function(doc, ftn, reverse) {  
  function _traverse(doc, layers, ftn, reverse) {  
  var ok = true;  
  for (var i = 1; i <= layers.length && ok != false; i++) {  
  var index = (reverse == true) ? layers.length-i : i - 1;  
  var layer = layers[index];  
  
  if (layer.typename == "LayerSet") {  
  ok = _traverse(doc, layer.layers, ftn, reverse);  
  
  } else {  
  stackorder = stackorder + 1;  
  ok = ftn(doc, layer, stackorder);  
  }  
  }  
  return ok;  
  };  
  
  return _traverse(doc, doc.layers, ftn, reverse);  
};  
  
// create a string to hold the data  
var str ="";  
  
// class using a contructor  
function cLayer(doc, layer) {  
  
//this.layerID = Stdlib.getLayerID(doc, layer);  
this.layerID = getLayerID(doc, layer);  
  //alert("layer ID: " + this.layerID);  
this.layerWidth = layer.bounds[2].value - layer.bounds[0].value;  
  this.layerHeight = layer.bounds[3].value - layer.bounds[1].value;  
    
// these return object coordinates relative to canvas  
  this.upperLeftX = layer.bounds[0].value;  
  this.upperLeftY = layer.bounds[1].value;  
  this.upperCenterX = this.layerWidth / 2 + layer.bounds[0].value;  
  this.upperCenterY = layer.bounds[1].value;  
  this.upperRightX = layer.bounds[2].value;  
  this.upperRightY = layer.bounds[1].value;  
  this.middleLeftX = layer.bounds[0].value;  
  this.middleLeftY = this.layerHeight / 2 + layer.bounds[1].value;  
  this.middleCenterX = this.layerWidth / 2 + layer.bounds[0].value;  
  this.middleCenterY = this.layerHeight / 2 + layer.bounds[1].value;  
  this.middleRightX = layer.bounds[2].value;  
  this.middleRightY = this.layerHeight / 2 + layer.bounds[1].value;  
  this.lowerLeftX = layer.bounds[0].value;  
  this.lowerLeftY = layer.bounds[3].value;  
  this.lowerCenterX = this.layerWidth / 2 + layer.bounds[0].value;  
  this.lowerCenterY = layer.bounds[3].value;  
  this.lowerRightX = layer.bounds[2].value;  
  this.lowerRightY = layer.bounds[3].value;  
  
// I'm adding these for easier editing of flash symbol transformation point (outputs a 'x, y' format)  
// because I like to assign shortcut keys that use the numeric pad keyboard, like such:  
// 7 8 9  
// 4 5 6  
// 1 2 3  
//  
this.leftBottom = this.lowerLeftX + ", " + this.lowerLeftY;  
this.bottomCenter = this.lowerCenterX + ", " + this.lowerCenterY;  
this.rightBottom = this.lowerRightX + ", " + this.lowerRightY;  
  
this.leftCenter = this.middleLeftX + ", " + this.middleLeftY;  
this.center = this.middleCenterX + ", " + this.middleCenterY;  
this.rightCenter = this.middleRightX + ", " + this.middleRightY;  
  
this.leftTop = this.upperLeftX + ", " + this.upperLeftY;  
this.topCenter = this.upperCenterX + ", " + this.upperCenterY;  
this.rightTop = this.upperRightX + ", " + this.upperRightY;  
  
// these return object coordinates relative to layer bounds  
  this.relUpperLeftX = layer.bounds[1].value - layer.bounds[1].value;  
  this.relUpperLeftY = layer.bounds[0].value - layer.bounds[0].value;  
  this.relUpperCenterX = this.layerWidth / 2;  
  this.relUpperCenterY = layer.bounds[0].value - layer.bounds[0].value;  
  this.relUpperRightX = this.layerWidth;  
  this.relUpperRightY = layer.bounds[0].value - layer.bounds[0].value;  
  this.relMiddleLeftX = layer.bounds[1].value - layer.bounds[1].value;  
  this.relMiddleLeftY = this.layerHeight / 2;  
  this.relMiddleCenterX = this.layerWidth / 2;  
  this.relMiddleCenterY = this.layerHeight / 2;  
  this.relMiddleRightX = this.layerWidth;  
this.relMiddleRightY = this.layerHeight / 2;  
  this.relLowerLeftX = layer.bounds[1].value - layer.bounds[1].value;  
  this.relLowerLeftY = this.layerHeight;  
  this.relLowerCenterX = this.layerWidth / 2;  
this.relLowerCenterY = this.layerHeight / 2;  
  this.relLowerRightY = this.layerHeight;  
  this.relLowerRightX = this.layerWidth;  
  this.relLowerRightY = this.layerHeight;  
    
  return this;  
}  
  
// add header line  
//str = "<psd filename=\"" + docRef.name + "\" path=\"" + mySourceFilePath + "\" width=\"" + docWidth + "\" height=\"" + docHeight + "\">\n";  
//'<?xml version="1.0" encoding="UTF-8"?>\n' // file header
str = '<scene name="'+ app.activeDocument.name.match(/([^\.]+)/)[1] +'">\n';  

// now a function to collect the data  
function exportBounds(doc, layer, i) {  
  var isVisible = layer.visible;  
  var layerData = cLayer(doc, layer);  
  
  if(layer.name == "background,background") {  
  
  } else if (layer.name == "foreground,background") {

  } else {
      layer.name = capitalizeFirstLetter (layer.name);
  }
  
  
  if(isVisible){
//DEFAULT  
/*// Layer object main coordinates relative to its active pixels  
  var str2 = "\t<layer name=\"" + layer.name   
+ "\" stack=\"" + (i - 1) // order in which layers are stacked, starting with zero for the bottom-most layer  
+ "\" position=\"" + leftTop // this is the   
+ "\" layerwidth=\"" + layerData.layerWidth   
+ "\" layerheight=\"" + layerData.layerHeight   
+ "\" transformpoint=\"" + "center" + "\">" // hard-coding 'center' as the default transformation point  
+ layer.name + ".png" + "</layer>\n" // I have to put some content here otherwise sometimes tags are ignored  */

lowerLeftX +=13;
upperRightY +=10;

//XML
var str2 = '\t<object name=\"' + layer.name // object name
+ '" posX="' + lowerLeftX // object's position X axis
+ '" posY="' + upperRightY // object's position Y axis
+ '" width="' + layerWidth // object's width
+ '" height="' + layerHeight // object's height
+ '" id="' + (i - 1) // objects id
+ '"/>\n' // structure end

//JSON
/*var str2 = '{\n\tobjects: [\n'
+ '{ name: ' */

str += str2.toString();  


  };  
};  
  
  
// call X's function using the one above  
traverseLayers(app.activeDocument, exportBounds, true);  

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}