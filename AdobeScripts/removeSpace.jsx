//  
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

function cLayer(doc, layer) {  
//this.layerID = Stdlib.getLayerID(doc, layer);  
this.layerID = getLayerID(doc, layer);  
  return this;  
}  

// now a function to collect the data  
function exportBounds(doc, layer, i) {  
  var isVisible = layer.visible;  
  var layerData = cLayer(doc, layer);  
  
  if(layer.name == "background,background") {  
  
  } else if (layer.name == "foreground,background") {

  } else {
      //layer.name = capitalizeFirstLetter (layer.name);
      layer.name = removeSpace(layer.name);
  }
  
};

// call X's function using the one above  
traverseLayers(app.activeDocument, exportBounds, true);  

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function removeSpace(string) {
   return string.replace(/^\s+|\s+$/gm,'');
}