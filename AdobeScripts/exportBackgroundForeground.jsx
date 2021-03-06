﻿function main(){
if(!documents.length) return;
var doc = activeDocument;
var oldPath = activeDocument.path;

var docName = doc.name.substring(0, doc.name.indexOf('.'))

/*var outPath = oldPath + "/Export/"+ docName;
var outFolder = new Folder(outPath);
if (!outFolder.exists) {
    outFolder.create();
}*/

var bgPath = oldPath + "/Export/Background";
var bgFolder = new Folder(bgPath);
if (!bgFolder.exists) {
    bgFolder.create();
}

var fgPath = oldPath + "/Export/Foreground";
var fgFolder = new Folder(fgPath);
if (!fgFolder.exists) {
    fgFolder.create();
}

scanLayerSets(doc);

function scanLayerSets(el) {
    // find layer groups
    for(var a=0;a<el.layerSets.length;a++){
        var lname = el.layerSets[a].name;
            saveLayer(el.layers.getByName(lname), lname, oldPath, true);
    }

    // find plain layers in current group
    for(var j=0; j<el.artLayers.length; j++) {
        var name = el.artLayers[j].name;
        
		var objName = name.replace(",normal", "");
		objName = objName.replace(",decorative", "");
		objName = objName.replace(",rare", "");
		objName = objName.toLowerCase();
		
		if (objName == "background" || objName == "foreground") {
            saveLayer(el.layers.getByName(name), objName, oldPath, false);
            el.layers.getByName(name).visible = false;
         }
        
    }

}

function saveLayer(layer, lname, path, shouldMerge) {
    activeDocument.activeLayer = layer;
    dupLayers();
    if (shouldMerge === undefined || shouldMerge === true) {
        activeDocument.mergeVisibleLayers();
    }
    //activeDocument.trim(TrimType.TRANSPARENT,true,true,true,true);
    
    if (lname == "foreground") {
        var saveFile= File(fgPath+"/"+docName+".png");
        SavePNG(saveFile);
    } else if (lname == "background") {
        var saveFile= File(bgPath+"/"+docName+".jpg");
        SaveJPEG(saveFile);
    }
    
    app.activeDocument.close(SaveOptions.DONOTSAVECHANGES);
}

};

main();
activeDocument.close(SaveOptions.DONOTSAVECHANGES);

function dupLayers() { 
    var desc143 = new ActionDescriptor();
        var ref73 = new ActionReference();
        ref73.putClass( charIDToTypeID('Dcmn') );
    desc143.putReference( charIDToTypeID('null'), ref73 );
    desc143.putString( charIDToTypeID('Nm  '), activeDocument.activeLayer.name );
        var ref74 = new ActionReference();
        ref74.putEnumerated( charIDToTypeID('Lyr '), charIDToTypeID('Ordn'), charIDToTypeID('Trgt') );
    desc143.putReference( charIDToTypeID('Usng'), ref74 );
    executeAction( charIDToTypeID('Mk  '), desc143, DialogModes.NO );
};

function SavePNG(saveFile){
    var pngOpts = new ExportOptionsSaveForWeb; 
    pngOpts.format = SaveDocumentType.PNG
    pngOpts.PNG8 = false; 
    pngOpts.transparency = true; 
    pngOpts.interlaced = false; 
    pngOpts.quality = 100;
    activeDocument.exportDocument(new File(saveFile),ExportType.SAVEFORWEB,pngOpts); 
}

function SaveJPEG(saveFile) {  
     var saveOptions = new JPEGSaveOptions( );  
     saveOptions.embedColorProfile = true;  
     saveOptions.formatOptions = FormatOptions.OPTIMIZEDBASELINE;  
     saveOptions.matte = MatteType.NONE;  
     saveOptions.quality = 10;   
     activeDocument.saveAs( saveFile, saveOptions, true );  
}  