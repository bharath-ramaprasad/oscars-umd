/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

if(!dojo._hasResource["dojo.colors"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.colors"] = true;
dojo.provide("dojo.colors");

//TODO: this module appears to break naming conventions

/*=====
dojo.colors = {
	// summary: Color utilities
}
=====*/

(function(){
	// this is a standard conversion prescribed by the CSS3 Color Module
	var hue2rgb = function(m1, m2, h){
		if(h < 0){ ++h; }
		if(h > 1){ --h; }
		var h6 = 6 * h;
		if(h6 < 1){ return m1 + (m2 - m1) * h6; }
		if(2 * h < 1){ return m2; }
		if(3 * h < 2){ return m1 + (m2 - m1) * (2 / 3 - h) * 6; }
		return m1;
	};
	
	dojo.colorFromRgb = function(/*String*/ color, /*dojo.Color?*/ obj){
		// summary:
		//		get rgb(a) array from css-style color declarations
		// description:
		//		this function can handle all 4 CSS3 Color Module formats: rgb,
		//		rgba, hsl, hsla, including rgb(a) with percentage values.
		var m = color.toLowerCase().match(/^(rgba?|hsla?)\(([\s\.\-,%0-9]+)\)/);
		if(m){
			var c = m[2].split(/\s*,\s*/), l = c.length, t = m[1];
			if((t == "rgb" && l == 3) || (t == "rgba" && l == 4)){
				var r = c[0];
				if(r.charAt(r.length - 1) == "%"){
					// 3 rgb percentage values
					var a = dojo.map(c, function(x){
						return parseFloat(x) * 2.56;
					});
					if(l == 4){ a[3] = c[3]; }
					return dojo.colorFromArray(a, obj);	// dojo.Color
				}
				return dojo.colorFromArray(c, obj);	// dojo.Color
			}
			if((t == "hsl" && l == 3) || (t == "hsla" && l == 4)){
				// normalize hsl values
				var H = ((parseFloat(c[0]) % 360) + 360) % 360 / 360,
					S = parseFloat(c[1]) / 100,
					L = parseFloat(c[2]) / 100,
					// calculate rgb according to the algorithm 
					// recommended by the CSS3 Color Module 
					m2 = L <= 0.5 ? L * (S + 1) : L + S - L * S, 
					m1 = 2 * L - m2,
					a = [hue2rgb(m1, m2, H + 1 / 3) * 256,
						hue2rgb(m1, m2, H) * 256, hue2rgb(m1, m2, H - 1 / 3) * 256, 1];
				if(l == 4){ a[3] = c[3]; }
				return dojo.colorFromArray(a, obj);	// dojo.Color
			}
		}
		return null;	// dojo.Color
	};
	
	var confine = function(c, low, high){
		// summary:
		//		sanitize a color component by making sure it is a number,
		//		and clamping it to valid values
		c = Number(c);
		return isNaN(c) ? high : c < low ? low : c > high ? high : c;	// Number
	};
	
	dojo.Color.prototype.sanitize = function(){
		// summary: makes sure that the object has correct attributes
		var t = this;
		t.r = Math.round(confine(t.r, 0, 255));
		t.g = Math.round(confine(t.g, 0, 255));
		t.b = Math.round(confine(t.b, 0, 255));
		t.a = confine(t.a, 0, 1);
		return this;	// dojo.Color
	};
})();


dojo.colors.makeGrey = function(/*Number*/ g, /*Number?*/ a){
	// summary: creates a greyscale color with an optional alpha
	return dojo.colorFromArray([g, g, g, a]);
};

// mixin all CSS3 named colors not already in _base, along with SVG 1.0 variant spellings
dojo.Color.named = dojo.mixin({
	aliceblue:	[240,248,255],
	antiquewhite:	[250,235,215],
	aquamarine:	[127,255,212],
	azure:	[240,255,255],
	beige:	[245,245,220],
	bisque:	[255,228,196],
	blanchedalmond:	[255,235,205],
	blueviolet:	[138,43,226],
	brown:	[165,42,42],
	burlywood:	[222,184,135],
	cadetblue:	[95,158,160],
	chartreuse:	[127,255,0],
	chocolate:	[210,105,30],
	coral:	[255,127,80],
	cornflowerblue:	[100,149,237],
	cornsilk:	[255,248,220],
	crimson:	[220,20,60],
	cyan:	[0,255,255],
	darkblue:	[0,0,139],
	darkcyan:	[0,139,139],
	darkgoldenrod:	[184,134,11],
	darkgray:	[169,169,169],
	darkgreen:	[0,100,0],
	darkgrey:	[169,169,169],
	darkkhaki:	[189,183,107],
	darkmagenta:	[139,0,139],
	darkolivegreen:	[85,107,47],
	darkorange:	[255,140,0],
	darkorchid:	[153,50,204],
	darkred:	[139,0,0],
	darksalmon:	[233,150,122],
	darkseagreen:	[143,188,143],
	darkslateblue:	[72,61,139],
	darkslategray:	[47,79,79],
	darkslategrey:	[47,79,79],
	darkturquoise:	[0,206,209],
	darkviolet:	[148,0,211],
	deeppink:	[255,20,147],
	deepskyblue:	[0,191,255],
	dimgray:	[105,105,105],
	dimgrey:	[105,105,105],
	dodgerblue:	[30,144,255],
	firebrick:	[178,34,34],
	floralwhite:	[255,250,240],
	forestgreen:	[34,139,34],
	gainsboro:	[220,220,220],
	ghostwhite:	[248,248,255],
	gold:	[255,215,0],
	goldenrod:	[218,165,32],
	greenyellow:	[173,255,47],
	grey:	[128,128,128],
	honeydew:	[240,255,240],
	hotpink:	[255,105,180],
	indianred:	[205,92,92],
	indigo:	[75,0,130],
	ivory:	[255,255,240],
	khaki:	[240,230,140],
	lavender:	[230,230,250],
	lavenderblush:	[255,240,245],
	lawngreen:	[124,252,0],
	lemonchiffon:	[255,250,205],
	lightblue:	[173,216,230],
	lightcoral:	[240,128,128],
	lightcyan:	[224,255,255],
	lightgoldenrodyellow:	[250,250,210],
	lightgray:	[211,211,211],
	lightgreen:	[144,238,144],
	lightgrey:	[211,211,211],
	lightpink:	[255,182,193],
	lightsalmon:	[255,160,122],
	lightseagreen:	[32,178,170],
	lightskyblue:	[135,206,250],
	lightslategray:	[119,136,153],
	lightslategrey:	[119,136,153],
	lightsteelblue:	[176,196,222],
	lightyellow:	[255,255,224],
	limegreen:	[50,205,50],
	linen:	[250,240,230],
	magenta:	[255,0,255],
	mediumaquamarine:	[102,205,170],
	mediumblue:	[0,0,205],
	mediumorchid:	[186,85,211],
	mediumpurple:	[147,112,219],
	mediumseagreen:	[60,179,113],
	mediumslateblue:	[123,104,238],
	mediumspringgreen:	[0,250,154],
	mediumturquoise:	[72,209,204],
	mediumvioletred:	[199,21,133],
	midnightblue:	[25,25,112],
	mintcream:	[245,255,250],
	mistyrose:	[255,228,225],
	moccasin:	[255,228,181],
	navajowhite:	[255,222,173],
	oldlace:	[253,245,230],
	olivedrab:	[107,142,35],
	orange:	[255,165,0],
	orangered:	[255,69,0],
	orchid:	[218,112,214],
	palegoldenrod:	[238,232,170],
	palegreen:	[152,251,152],
	paleturquoise:	[175,238,238],
	palevioletred:	[219,112,147],
	papayawhip:	[255,239,213],
	peachpuff:	[255,218,185],
	peru:	[205,133,63],
	pink:	[255,192,203],
	plum:	[221,160,221],
	powderblue:	[176,224,230],
	rosybrown:	[188,143,143],
	royalblue:	[65,105,225],
	saddlebrown:	[139,69,19],
	salmon:	[250,128,114],
	sandybrown:	[244,164,96],
	seagreen:	[46,139,87],
	seashell:	[255,245,238],
	sienna:	[160,82,45],
	skyblue:	[135,206,235],
	slateblue:	[106,90,205],
	slategray:	[112,128,144],
	slategrey:	[112,128,144],
	snow:	[255,250,250],
	springgreen:	[0,255,127],
	steelblue:	[70,130,180],
	tan:	[210,180,140],
	thistle:	[216,191,216],
	tomato:	[255,99,71],
	transparent: [0, 0, 0, 0],
	turquoise:	[64,224,208],
	violet:	[238,130,238],
	wheat:	[245,222,179],
	whitesmoke:	[245,245,245],
	yellowgreen:	[154,205,50]
}, dojo.Color.named);

}

if(!dojo._hasResource["dojo.i18n"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.i18n"] = true;
dojo.provide("dojo.i18n");

/*=====
dojo.i18n = {
	// summary: Utility classes to enable loading of resources for internationalization (i18n)
};
=====*/

dojo.i18n.getLocalization = function(/*String*/packageName, /*String*/bundleName, /*String?*/locale){
	//	summary:
	//		Returns an Object containing the localization for a given resource
	//		bundle in a package, matching the specified locale.
	//	description:
	//		Returns a hash containing name/value pairs in its prototypesuch
	//		that values can be easily overridden.  Throws an exception if the
	//		bundle is not found.  Bundle must have already been loaded by
	//		`dojo.requireLocalization()` or by a build optimization step.  NOTE:
	//		try not to call this method as part of an object property
	//		definition (`var foo = { bar: dojo.i18n.getLocalization() }`).  In
	//		some loading situations, the bundle may not be available in time
	//		for the object definition.  Instead, call this method inside a
	//		function that is run after all modules load or the page loads (like
	//		in `dojo.addOnLoad()`), or in a widget lifecycle method.
	//	packageName:
	//		package which is associated with this resource
	//	bundleName:
	//		the base filename of the resource bundle (without the ".js" suffix)
	//	locale:
	//		the variant to load (optional).  By default, the locale defined by
	//		the host environment: dojo.locale

	locale = dojo.i18n.normalizeLocale(locale);

	// look for nearest locale match
	var elements = locale.split('-');
	var module = [packageName,"nls",bundleName].join('.');
	var bundle = dojo._loadedModules[module];
	if(bundle){
		var localization;
		for(var i = elements.length; i > 0; i--){
			var loc = elements.slice(0, i).join('_');
			if(bundle[loc]){
				localization = bundle[loc];
				break;
			}
		}
		if(!localization){
			localization = bundle.ROOT;
		}

		// make a singleton prototype so that the caller won't accidentally change the values globally
		if(localization){
			var clazz = function(){};
			clazz.prototype = localization;
			return new clazz(); // Object
		}
	}

	throw new Error("Bundle not found: " + bundleName + " in " + packageName+" , locale=" + locale);
};

dojo.i18n.normalizeLocale = function(/*String?*/locale){
	//	summary:
	//		Returns canonical form of locale, as used by Dojo.
	//
	//  description:
	//		All variants are case-insensitive and are separated by '-' as specified in [RFC 3066](http://www.ietf.org/rfc/rfc3066.txt).
	//		If no locale is specified, the dojo.locale is returned.  dojo.locale is defined by
	//		the user agent's locale unless overridden by djConfig.

	var result = locale ? locale.toLowerCase() : dojo.locale;
	if(result == "root"){
		result = "ROOT";
	}
	return result; // String
};

dojo.i18n._requireLocalization = function(/*String*/moduleName, /*String*/bundleName, /*String?*/locale, /*String?*/availableFlatLocales){
	//	summary:
	//		See dojo.requireLocalization()
	//	description:
	// 		Called by the bootstrap, but factored out so that it is only
	// 		included in the build when needed.

	var targetLocale = dojo.i18n.normalizeLocale(locale);
 	var bundlePackage = [moduleName, "nls", bundleName].join(".");
	// NOTE: 
	//		When loading these resources, the packaging does not match what is
	//		on disk.  This is an implementation detail, as this is just a
	//		private data structure to hold the loaded resources.  e.g.
	//		`tests/hello/nls/en-us/salutations.js` is loaded as the object
	//		`tests.hello.nls.salutations.en_us={...}` The structure on disk is
	//		intended to be most convenient for developers and translators, but
	//		in memory it is more logical and efficient to store in a different
	//		order.  Locales cannot use dashes, since the resulting path will
	//		not evaluate as valid JS, so we translate them to underscores.
	
	//Find the best-match locale to load if we have available flat locales.
	var bestLocale = "";
	if(availableFlatLocales){
		var flatLocales = availableFlatLocales.split(",");
		for(var i = 0; i < flatLocales.length; i++){
			//Locale must match from start of string.
			//Using ["indexOf"] so customBase builds do not see
			//this as a dojo._base.array dependency.
			if(targetLocale["indexOf"](flatLocales[i]) == 0){
				if(flatLocales[i].length > bestLocale.length){
					bestLocale = flatLocales[i];
				}
			}
		}
		if(!bestLocale){
			bestLocale = "ROOT";
		}		
	}

	//See if the desired locale is already loaded.
	var tempLocale = availableFlatLocales ? bestLocale : targetLocale;
	var bundle = dojo._loadedModules[bundlePackage];
	var localizedBundle = null;
	if(bundle){
		if(dojo.config.localizationComplete && bundle._built){return;}
		var jsLoc = tempLocale.replace(/-/g, '_');
		var translationPackage = bundlePackage+"."+jsLoc;
		localizedBundle = dojo._loadedModules[translationPackage];
	}

	if(!localizedBundle){
		bundle = dojo["provide"](bundlePackage);
		var syms = dojo._getModuleSymbols(moduleName);
		var modpath = syms.concat("nls").join("/");
		var parent;

		dojo.i18n._searchLocalePath(tempLocale, availableFlatLocales, function(loc){
			var jsLoc = loc.replace(/-/g, '_');
			var translationPackage = bundlePackage + "." + jsLoc;
			var loaded = false;
			if(!dojo._loadedModules[translationPackage]){
				// Mark loaded whether it's found or not, so that further load attempts will not be made
				dojo["provide"](translationPackage);
				var module = [modpath];
				if(loc != "ROOT"){module.push(loc);}
				module.push(bundleName);
				var filespec = module.join("/") + '.js';
				loaded = dojo._loadPath(filespec, null, function(hash){
					// Use singleton with prototype to point to parent bundle, then mix-in result from loadPath
					var clazz = function(){};
					clazz.prototype = parent;
					bundle[jsLoc] = new clazz();
					for(var j in hash){ bundle[jsLoc][j] = hash[j]; }
				});
			}else{
				loaded = true;
			}
			if(loaded && bundle[jsLoc]){
				parent = bundle[jsLoc];
			}else{
				bundle[jsLoc] = parent;
			}
			
			if(availableFlatLocales){
				//Stop the locale path searching if we know the availableFlatLocales, since
				//the first call to this function will load the only bundle that is needed.
				return true;
			}
		});
	}

	//Save the best locale bundle as the target locale bundle when we know the
	//the available bundles.
	if(availableFlatLocales && targetLocale != bestLocale){
		bundle[targetLocale.replace(/-/g, '_')] = bundle[bestLocale.replace(/-/g, '_')];
	}
};

(function(){
	// If other locales are used, dojo.requireLocalization should load them as
	// well, by default. 
	// 
	// Override dojo.requireLocalization to do load the default bundle, then
	// iterate through the extraLocale list and load those translations as
	// well, unless a particular locale was requested.

	var extra = dojo.config.extraLocale;
	if(extra){
		if(!extra instanceof Array){
			extra = [extra];
		}

		var req = dojo.i18n._requireLocalization;
		dojo.i18n._requireLocalization = function(m, b, locale, availableFlatLocales){
			req(m,b,locale, availableFlatLocales);
			if(locale){return;}
			for(var i=0; i<extra.length; i++){
				req(m,b,extra[i], availableFlatLocales);
			}
		};
	}
})();

dojo.i18n._searchLocalePath = function(/*String*/locale, /*Boolean*/down, /*Function*/searchFunc){
	//	summary:
	//		A helper method to assist in searching for locale-based resources.
	//		Will iterate through the variants of a particular locale, either up
	//		or down, executing a callback function.  For example, "en-us" and
	//		true will try "en-us" followed by "en" and finally "ROOT".

	locale = dojo.i18n.normalizeLocale(locale);

	var elements = locale.split('-');
	var searchlist = [];
	for(var i = elements.length; i > 0; i--){
		searchlist.push(elements.slice(0, i).join('-'));
	}
	searchlist.push(false);
	if(down){searchlist.reverse();}

	for(var j = searchlist.length - 1; j >= 0; j--){
		var loc = searchlist[j] || "ROOT";
		var stop = searchFunc(loc);
		if(stop){ break; }
	}
};

dojo.i18n._preloadLocalizations = function(/*String*/bundlePrefix, /*Array*/localesGenerated){
	//	summary:
	//		Load built, flattened resource bundles, if available for all
	//		locales used in the page. Only called by built layer files.

	function preload(locale){
		locale = dojo.i18n.normalizeLocale(locale);
		dojo.i18n._searchLocalePath(locale, true, function(loc){
			for(var i=0; i<localesGenerated.length;i++){
				if(localesGenerated[i] == loc){
					dojo["require"](bundlePrefix+"_"+loc);
					return true; // Boolean
				}
			}
			return false; // Boolean
		});
	}
	preload();
	var extra = dojo.config.extraLocale||[];
	for(var i=0; i<extra.length; i++){
		preload(extra[i]);
	}
};

}

if(!dojo._hasResource["dijit.ColorPalette"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.ColorPalette"] = true;
dojo.provide("dijit.ColorPalette");







dojo.declare("dijit.ColorPalette",
	[dijit._Widget, dijit._Templated],
	{
	// summary: A keyboard accessible color-picking widget
	// description:
	//	Grid showing various colors, so the user can pick a certain color
	//	Can be used standalone, or as a popup.
	//
	// example:
	// |	<div dojoType="dijit.ColorPalette"></div>
	//
	// example:
	// |    var picker = new dijit.ColorPalette({ },srcNode);
	// |	picker.startup();
	//
	// defaultTimeout: Number
	//		number of milliseconds before a held key or button becomes typematic
	defaultTimeout: 500,

	// timeoutChangeRate: Number
	//		fraction of time used to change the typematic timer between events
	//		1.0 means that each typematic event fires at defaultTimeout intervals
	//		< 1.0 means that each typematic event fires at an increasing faster rate
	timeoutChangeRate: 0.90,

	// palette: String
	//		Size of grid, either "7x10" or "3x4".
	palette: "7x10",

	//_value: String
	//		The value of the selected color.
	value: null,

	//_currentFocus: Integer
	//		Index of the currently focused color.
	_currentFocus: 0,

	// _xDim: Integer
	//		This is the number of colors horizontally across.
	_xDim: null,

	// _yDim: Integer
	///		This is the number of colors vertically down.
	_yDim: null,

	// _palettes: Map
	// 		This represents the value of the colors.
	//		The first level is a hashmap of the different arrays available
	//		The next two dimensions represent the columns and rows of colors.
	_palettes: {

		"7x10":	[["white", "seashell", "cornsilk", "lemonchiffon","lightyellow", "palegreen", "paleturquoise", "lightcyan",	"lavender", "plum"],
				["lightgray", "pink", "bisque", "moccasin", "khaki", "lightgreen", "lightseagreen", "lightskyblue", "cornflowerblue", "violet"],
				["silver", "lightcoral", "sandybrown", "orange", "palegoldenrod", "chartreuse", "mediumturquoise", 	"skyblue", "mediumslateblue","orchid"],
				["gray", "red", "orangered", "darkorange", "yellow", "limegreen", 	"darkseagreen", "royalblue", "slateblue", "mediumorchid"],
				["dimgray", "crimson", 	"chocolate", "coral", "gold", "forestgreen", "seagreen", "blue", "blueviolet", "darkorchid"],
				["darkslategray","firebrick","saddlebrown", "sienna", "olive", "green", "darkcyan", "mediumblue","darkslateblue", "darkmagenta" ],
				["black", "darkred", "maroon", "brown", "darkolivegreen", "darkgreen", "midnightblue", "navy", "indigo", 	"purple"]],

		"3x4": [["white", "lime", "green", "blue"],
			["silver", "yellow", "fuchsia", "navy"],
			["gray", "red", "purple", "black"]]	

	},

	// _imagePaths: Map
	//		This is stores the path to the palette images
	_imagePaths: {
		"7x10": dojo.moduleUrl("dijit.themes", "a11y/colors7x10.png"),
		"3x4": dojo.moduleUrl("dijit.themes", "a11y/colors3x4.png")
	},

	// _paletteCoords: Map
	//		This is a map that is used to calculate the coordinates of the
	//		images that make up the palette.
	_paletteCoords: {
		"leftOffset": 3, "topOffset": 3,
		"cWidth": 20, "cHeight": 20
		
	},

	// templatePath: String
	//		Path to the template of this widget.
	templateString:"<div class=\"dijitInline dijitColorPalette\">\n\t<div class=\"dijitColorPaletteInner\" dojoAttachPoint=\"divNode\" waiRole=\"grid\" tabIndex=\"${tabIndex}\">\n\t\t<img class=\"dijitColorPaletteUnder\" dojoAttachPoint=\"imageNode\" waiRole=\"presentation\">\n\t</div>\t\n</div>\n",

	// _paletteDims: Object
	//		Size of the supported palettes for alignment purposes.
	_paletteDims: {
		"7x10": {"width": "206px", "height": "145px"},
		"3x4": {"width": "86px", "height": "64px"}
	},

	// tabIndex: String
	//		Widget tabindex.
	tabIndex: "0",

	postCreate: function(){
		// A name has to be given to the colorMap, this needs to be unique per Palette.
		dojo.mixin(this.divNode.style, this._paletteDims[this.palette]);
		this.imageNode.setAttribute("src", this._imagePaths[this.palette]);
		var choices = this._palettes[this.palette];	
		this.domNode.style.position = "relative";
		this._cellNodes = [];	
		this.colorNames = dojo.i18n.getLocalization("dojo", "colors", this.lang);
		var url = this._blankGif,
            colorObject = new dojo.Color(),
		    coords = this._paletteCoords;
		for(var row=0; row < choices.length; row++){
			for(var col=0; col < choices[row].length; col++) {
                var imgNode = dojo.doc.createElement("img");
                imgNode.src = url;
                dojo.addClass(imgNode, "dijitPaletteImg");
                var color = choices[row][col],
                        colorValue = colorObject.setColor(dojo.Color.named[color]);
                imgNode.alt = this.colorNames[color];
                imgNode.color = colorValue.toHex();
                var imgStyle = imgNode.style;
                imgStyle.color = imgStyle.backgroundColor = imgNode.color;
                var cellNode = dojo.doc.createElement("span");
                cellNode.appendChild(imgNode);
                dojo.forEach(["Dijitclick", "MouseEnter", "Focus", "Blur"], function(handler) {
                    this.connect(cellNode, "on" + handler.toLowerCase(), "_onCell" + handler);
                }, this);
                this.divNode.appendChild(cellNode);
                var cellStyle = cellNode.style;
                cellStyle.top = coords.topOffset + (row * coords.cHeight) + "px";
                cellStyle.left = coords.leftOffset + (col * coords.cWidth) + "px";
                dojo.attr(cellNode, "tabindex", "-1");
                cellNode.title = this.colorNames[color];
                dojo.addClass(cellNode, "dijitPaletteCell");
                dijit.setWaiRole(cellNode, "gridcell");
                cellNode.index = this._cellNodes.length;
                this._cellNodes.push(cellNode);
            }
		}
		this._xDim = choices[0].length;
		this._yDim = choices.length;
		this.connect(this.divNode, "onfocus", "_onDivNodeFocus");

		// Now set all events
		// The palette itself is navigated to with the tab key on the keyboard
		// Keyboard navigation within the Palette is with the arrow keys
		// Spacebar selects the color.
		// For the up key the index is changed by negative the x dimension.		

		var keyIncrementMap = {
			UP_ARROW: -this._xDim,
			// The down key the index is increase by the x dimension.	
			DOWN_ARROW: this._xDim,
			// Right and left move the index by 1.
			RIGHT_ARROW: 1,
			LEFT_ARROW: -1
		};
		for(var key in keyIncrementMap){
			this._connects.push(dijit.typematic.addKeyListener(this.domNode,
				{charOrCode:dojo.keys[key], ctrlKey:false, altKey:false, shiftKey:false},
				this,
				function(){
					var increment = keyIncrementMap[key];
					return function(count){ this._navigateByKey(increment, count); };
				}(),
				this.timeoutChangeRate, this.defaultTimeout));
		}
	},

	focus: function(){
		// summary:
		//		Focus this ColorPalette.  Puts focus on the first swatch.
		this._focusFirst();
	},

	onChange: function(color){
		// summary:
		//		Callback when a color is selected.
		// color: String
		//		Hex value corresponding to color.
//		
	},

	_focusFirst: function(){
		this._currentFocus = 0;
		var cellNode = this._cellNodes[this._currentFocus];
		window.setTimeout(function(){dijit.focus(cellNode)}, 0);
	},

	_onDivNodeFocus: function(evt){
		// focus bubbles on Firefox 2, so just make sure that focus has really
		// gone to the container
		if(evt.target === this.divNode){
			this._focusFirst();
		}
	},

	_onFocus: function(){
		// while focus is on the palette, set its tabindex to -1 so that on a
		// shift-tab from a cell, the container is not in the tab order
		dojo.attr(this.divNode, "tabindex", "-1");
	},

	_onBlur: function(){
		this._removeCellHighlight(this._currentFocus);
		// when focus leaves the palette, restore its tabindex, since it was
		// modified by _onFocus().
		dojo.attr(this.divNode, "tabindex", this.tabIndex);
	},

	_onCellDijitclick: function(/*Event*/ evt){
		// summary:
		//		Handler for click, enter key & space key. Selects the color.
		// evt:
		//		The event.
		var target = evt.currentTarget;
		if (this._currentFocus != target.index){
			this._currentFocus = target.index;
			window.setTimeout(function(){dijit.focus(target)}, 0);
		}
		this._selectColor(target);
		dojo.stopEvent(evt);
	},

	_onCellMouseEnter: function(/*Event*/ evt){
		// summary:
		//		Handler for onMouseOver. Put focus on the color under the mouse.
		// evt:
		//		The mouse event.
		var target = evt.currentTarget;
		this._setCurrent(target);	// redundant, but needed per safari bug where onCellFocus never called
		window.setTimeout(function(){dijit.focus(target)}, 0);
	},

	_onCellFocus: function(/*Event*/ evt){
		// summary:
		//		Handler for onFocus. Removes highlight of
		//		the color that just lost focus, and highlights
		//		the new color.
		// evt:
		//		The focus event.
		this._setCurrent(evt.currentTarget);
	},

	_setCurrent: function(/*Node*/ node){
		// summary:
		//		Called when color is hovered or focused.
		// description:
		//		Removes highlight of the old color, and highlights
		//		the new color.
		this._removeCellHighlight(this._currentFocus);
		this._currentFocus = node.index;
		dojo.addClass(node, "dijitPaletteCellHighlight");		
	},

	_onCellBlur: function(/*Event*/ evt){
		// summary:
		//		needed for Firefox 2 on Mac OS X
		this._removeCellHighlight(this._currentFocus);
	},

	_removeCellHighlight: function(index){
		dojo.removeClass(this._cellNodes[index], "dijitPaletteCellHighlight");
	},

	_selectColor: function(selectNode){	
		// summary:
		// 		This selects a color. It triggers the onChange event
		// area:
		//		The area node that covers the color being selected.
		var img = selectNode.getElementsByTagName("img")[0];
		this.onChange(this.value = img.color);
	},

	_navigateByKey: function(increment, typeCount){
		// summary:
		// 	  	This is the callback for typematic.
		// 		It changes the focus and the highlighed color.
		// increment:
		// 		How much the key is navigated.
		// typeCount:
		//		How many times typematic has fired.

		// typecount == -1 means the key is released.
		if(typeCount == -1){ return; }

		var newFocusIndex = this._currentFocus + increment;
		if(newFocusIndex < this._cellNodes.length && newFocusIndex > -1)
		{
			var focusNode = this._cellNodes[newFocusIndex];
			focusNode.focus();
		}
	}
});

}

if(!dojo._hasResource["dijit.Declaration"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Declaration"] = true;
dojo.provide("dijit.Declaration");



dojo.declare(
	"dijit.Declaration",
	dijit._Widget,
	{
		// summary:
		//		The Declaration widget allows a user to declare new widget
		//		classes directly from a snippet of markup.

		_noScript: true,
		widgetClass: "",
		replaceVars: true,
		defaults: null,
		mixins: [],
		buildRendering: function(){
			var src = this.srcNodeRef.parentNode.removeChild(this.srcNodeRef),
				preambles = dojo.query("> script[type='dojo/method'][event='preamble']", src).orphan(),
				methods = dojo.query("> script[type^='dojo/method'][event]", src).orphan(),
				postscriptConnects = dojo.query("> script[type^='dojo/method']", src).orphan(),
				regularConnects = dojo.query("> script[type^='dojo/connect']", src).orphan(),
				srcType = src.nodeName;

			var propList = this.defaults||{};

			// For all methods defined like <script type="dojo/method" event="foo">,
			// add that method to prototype
			dojo.forEach(methods, function(s){
				var evt = s.getAttribute("event"),
					func = dojo.parser._functionFromScript(s);
				propList[evt] = func;
			});

			// map array of strings like [ "dijit.form.Button" ] to array of mixin objects
			// (note that dojo.map(this.mixins, dojo.getObject) doesn't work because it passes
			// a bogus third argument to getObject(), confusing it)
			this.mixins = this.mixins.length ?
				dojo.map(this.mixins, function(name){ return dojo.getObject(name); } ) :
				[ dijit._Widget, dijit._Templated ];

			propList.widgetsInTemplate = true;
			propList._skipNodeCache = true;
			propList.templateString = "<"+srcType+" class='"+src.className+"' dojoAttachPoint='"+(src.getAttribute("dojoAttachPoint")||'')+"' dojoAttachEvent='"+(src.getAttribute("dojoAttachEvent")||'')+"' >"+src.innerHTML.replace(/\%7B/g,"{").replace(/\%7D/g,"}")+"</"+srcType+">";

			// strip things so we don't create stuff under us in the initial setup phase
			dojo.query("[dojoType]", src).forEach(function(node){
				node.removeAttribute("dojoType");
			});

			// create the new widget class
			var wc = dojo.declare(
				this.widgetClass,
				this.mixins,
				propList
			);

			// Handle <script> blocks of form:
			//		<script type="dojo/connect" event="foo">
			// and
			//		<script type="dojo/method">
			// (Note that the second one is just shorthand for a dojo/connect to postscript)
			// Since this is a connect in the declaration, we are actually connection to the method
			// in the _prototype_.
			var connects = regularConnects.concat(postscriptConnects);
			dojo.forEach(connects, function(s){
				var evt = s.getAttribute("event")||"postscript",
					func = dojo.parser._functionFromScript(s);
				dojo.connect(wc.prototype, evt, func);
			});
		}
	}
);

}

if(!dojo._hasResource["dojo.dnd.common"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.common"] = true;
dojo.provide("dojo.dnd.common");

dojo.dnd._isMac = navigator.appVersion.indexOf("Macintosh") >= 0;
dojo.dnd._copyKey = dojo.dnd._isMac ? "metaKey" : "ctrlKey";

dojo.dnd.getCopyKeyState = function(e) {
	// summary: abstracts away the difference between selection on Mac and PC,
	//	and returns the state of the "copy" key to be pressed.
	// e: Event: mouse event
	return e[dojo.dnd._copyKey];	// Boolean
};

dojo.dnd._uniqueId = 0;
dojo.dnd.getUniqueId = function(){
	// summary: returns a unique string for use with any DOM element
	var id;
	do{
		id = dojo._scopeName + "Unique" + (++dojo.dnd._uniqueId);
	}while(dojo.byId(id));
	return id;
};

dojo.dnd._empty = {};

dojo.dnd.isFormElement = function(/*Event*/ e){
	// summary: returns true, if user clicked on a form element
	var t = e.target;
	if(t.nodeType == 3 /*TEXT_NODE*/){
		t = t.parentNode;
	}
	return " button textarea input select option ".indexOf(" " + t.tagName.toLowerCase() + " ") >= 0;	// Boolean
};

}

if(!dojo._hasResource["dojo.dnd.autoscroll"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.autoscroll"] = true;
dojo.provide("dojo.dnd.autoscroll");

dojo.dnd.getViewport = function(){
	// summary: returns a viewport size (visible part of the window)

	// FIXME: need more docs!!
	var d = dojo.doc, dd = d.documentElement, w = window, b = dojo.body();
	if(dojo.isMozilla){
		return {w: dd.clientWidth, h: w.innerHeight};	// Object
	}else if(!dojo.isOpera && w.innerWidth){
		return {w: w.innerWidth, h: w.innerHeight};		// Object
	}else if (!dojo.isOpera && dd && dd.clientWidth){
		return {w: dd.clientWidth, h: dd.clientHeight};	// Object
	}else if (b.clientWidth){
		return {w: b.clientWidth, h: b.clientHeight};	// Object
	}
	return null;	// Object
};

dojo.dnd.V_TRIGGER_AUTOSCROLL = 32;
dojo.dnd.H_TRIGGER_AUTOSCROLL = 32;

dojo.dnd.V_AUTOSCROLL_VALUE = 16;
dojo.dnd.H_AUTOSCROLL_VALUE = 16;

dojo.dnd.autoScroll = function(e){
	// summary:
	//		a handler for onmousemove event, which scrolls the window, if
	//		necesary
	// e: Event:
	//		onmousemove event

	// FIXME: needs more docs!
	var v = dojo.dnd.getViewport(), dx = 0, dy = 0;
	if(e.clientX < dojo.dnd.H_TRIGGER_AUTOSCROLL){
		dx = -dojo.dnd.H_AUTOSCROLL_VALUE;
	}else if(e.clientX > v.w - dojo.dnd.H_TRIGGER_AUTOSCROLL){
		dx = dojo.dnd.H_AUTOSCROLL_VALUE;
	}
	if(e.clientY < dojo.dnd.V_TRIGGER_AUTOSCROLL){
		dy = -dojo.dnd.V_AUTOSCROLL_VALUE;
	}else if(e.clientY > v.h - dojo.dnd.V_TRIGGER_AUTOSCROLL){
		dy = dojo.dnd.V_AUTOSCROLL_VALUE;
	}
	window.scrollBy(dx, dy);
};

dojo.dnd._validNodes = {"div": 1, "p": 1, "td": 1};
dojo.dnd._validOverflow = {"auto": 1, "scroll": 1};

dojo.dnd.autoScrollNodes = function(e){
	// summary:
	//		a handler for onmousemove event, which scrolls the first avaialble
	//		Dom element, it falls back to dojo.dnd.autoScroll()
	// e: Event:
	//		onmousemove event

	// FIXME: needs more docs!
	for(var n = e.target; n;){
		if(n.nodeType == 1 && (n.tagName.toLowerCase() in dojo.dnd._validNodes)){
			var s = dojo.getComputedStyle(n);
			if(s.overflow.toLowerCase() in dojo.dnd._validOverflow){
				var b = dojo._getContentBox(n, s), t = dojo._abs(n, true);
				//
				var w = Math.min(dojo.dnd.H_TRIGGER_AUTOSCROLL, b.w / 2), 
					h = Math.min(dojo.dnd.V_TRIGGER_AUTOSCROLL, b.h / 2),
					rx = e.pageX - t.x, ry = e.pageY - t.y, dx = 0, dy = 0;
				if(dojo.isSafari || dojo.isOpera){
					// FIXME: this code should not be here, it should be taken into account 
					// either by the event fixing code, or the dojo._abs()
					// FIXME: this code doesn't work on Opera 9.5 Beta
					rx += dojo.body().scrollLeft, ry += dojo.body().scrollTop;
				}
				if(rx > 0 && rx < b.w){
					if(rx < w){
						dx = -w;
					}else if(rx > b.w - w){
						dx = w;
					}
				}
				//
				if(ry > 0 && ry < b.h){
					if(ry < h){
						dy = -h;
					}else if(ry > b.h - h){
						dy = h;
					}
				}
				var oldLeft = n.scrollLeft, oldTop = n.scrollTop;
				n.scrollLeft = n.scrollLeft + dx;
				n.scrollTop  = n.scrollTop  + dy;
				if(oldLeft != n.scrollLeft || oldTop != n.scrollTop){ return; }
			}
		}
		try{
			n = n.parentNode;
		}catch(x){
			n = null;
		}
	}
	dojo.dnd.autoScroll(e);
};

}

if(!dojo._hasResource["dojo.dnd.Mover"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.Mover"] = true;
dojo.provide("dojo.dnd.Mover");




dojo.declare("dojo.dnd.Mover", null, {
	constructor: function(node, e, host){
		// summary: an object, which makes a node follow the mouse, 
		//	used as a default mover, and as a base class for custom movers
		// node: Node: a node (or node's id) to be moved
		// e: Event: a mouse event, which started the move;
		//	only pageX and pageY properties are used
		// host: Object?: object which implements the functionality of the move,
		//	 and defines proper events (onMoveStart and onMoveStop)
		this.node = dojo.byId(node);
		this.marginBox = {l: e.pageX, t: e.pageY};
		this.mouseButton = e.button;
		var h = this.host = host, d = node.ownerDocument, 
			firstEvent = dojo.connect(d, "onmousemove", this, "onFirstMove");
		this.events = [
			dojo.connect(d, "onmousemove", this, "onMouseMove"),
			dojo.connect(d, "onmouseup",   this, "onMouseUp"),
			// cancel text selection and text dragging
			dojo.connect(d, "ondragstart",   dojo.stopEvent),
			dojo.connect(d.body, "onselectstart", dojo.stopEvent),
			firstEvent
		];
		// notify that the move has started
		if(h && h.onMoveStart){
			h.onMoveStart(this);
		}
	},
	// mouse event processors
	onMouseMove: function(e){
		// summary: event processor for onmousemove
		// e: Event: mouse event
		dojo.dnd.autoScroll(e);
		var m = this.marginBox;
		this.host.onMove(this, {l: m.l + e.pageX, t: m.t + e.pageY});
		dojo.stopEvent(e);
	},
	onMouseUp: function(e){
		if(dojo.isSafari && dojo.dnd._isMac && this.mouseButton == 2 ? 
				e.button == 0 : this.mouseButton == e.button){
			this.destroy();
		}
		dojo.stopEvent(e);
	},
	// utilities
	onFirstMove: function(){
		// summary: makes the node absolute; it is meant to be called only once
		var s = this.node.style, l, t, h = this.host;
		switch(s.position){
			case "relative":
			case "absolute":
				// assume that left and top values are in pixels already
				l = Math.round(parseFloat(s.left));
				t = Math.round(parseFloat(s.top));
				break;
			default:
				s.position = "absolute";	// enforcing the absolute mode
				var m = dojo.marginBox(this.node);
				// event.pageX/pageY (which we used to generate the initial
				// margin box) includes padding and margin set on the body.
				// However, setting the node's position to absolute and then
				// doing dojo.marginBox on it *doesn't* take that additional
				// space into account - so we need to subtract the combined
				// padding and margin.  We use getComputedStyle and
				// _getMarginBox/_getContentBox to avoid the extra lookup of
				// the computed style. 
				var b = dojo.doc.body;
				var bs = dojo.getComputedStyle(b);
				var bm = dojo._getMarginBox(b, bs);
				var bc = dojo._getContentBox(b, bs);
				l = m.l - (bc.l - bm.l);
				t = m.t - (bc.t - bm.t);
				break;
		}
		this.marginBox.l = l - this.marginBox.l;
		this.marginBox.t = t - this.marginBox.t;
		if(h && h.onFirstMove){
			h.onFirstMove(this);
		}
		dojo.disconnect(this.events.pop());
	},
	destroy: function(){
		// summary: stops the move, deletes all references, so the object can be garbage-collected
		dojo.forEach(this.events, dojo.disconnect);
		// undo global settings
		var h = this.host;
		if(h && h.onMoveStop){
			h.onMoveStop(this);
		}
		// destroy objects
		this.events = this.node = this.host = null;
	}
});

}

if(!dojo._hasResource["dojo.dnd.Moveable"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.Moveable"] = true;
dojo.provide("dojo.dnd.Moveable");



dojo.declare("dojo.dnd.Moveable", null, {
	// object attributes (for markup)
	handle: "",
	delay: 0,
	skip: false,
	
	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		handle: Node: a node (or node's id), which is used as a mouse handle
		//			if omitted, the node itself is used as a handle
		//		delay: Number: delay move by this number of pixels
		//		skip: Boolean: skip move of form elements
		//		mover: Object: a constructor of custom Mover
		this.node = dojo.byId(node);
		if(!params){ params = {}; }
		this.handle = params.handle ? dojo.byId(params.handle) : null;
		if(!this.handle){ this.handle = this.node; }
		this.delay = params.delay > 0 ? params.delay : 0;
		this.skip  = params.skip;
		this.mover = params.mover ? params.mover : dojo.dnd.Mover;
		this.events = [
			dojo.connect(this.handle, "onmousedown", this, "onMouseDown"),
			// cancel text selection and text dragging
			dojo.connect(this.handle, "ondragstart",   this, "onSelectStart"),
			dojo.connect(this.handle, "onselectstart", this, "onSelectStart")
		];
	},

	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.Moveable(node, params);
	},

	// methods
	destroy: function(){
		// summary: stops watching for possible move, deletes all references, so the object can be garbage-collected
		dojo.forEach(this.events, dojo.disconnect);
		this.events = this.node = this.handle = null;
	},
	
	// mouse event processors
	onMouseDown: function(e){
		// summary: event processor for onmousedown, creates a Mover for the node
		// e: Event: mouse event
		if(this.skip && dojo.dnd.isFormElement(e)){ return; }
		if(this.delay){
			this.events.push(
				dojo.connect(this.handle, "onmousemove", this, "onMouseMove"),
				dojo.connect(this.handle, "onmouseup", this, "onMouseUp")
			);
			this._lastX = e.pageX;
			this._lastY = e.pageY;
		}else{
			this.onDragDetected(e);
		}
		dojo.stopEvent(e);
	},
	onMouseMove: function(e){
		// summary: event processor for onmousemove, used only for delayed drags
		// e: Event: mouse event
		if(Math.abs(e.pageX - this._lastX) > this.delay || Math.abs(e.pageY - this._lastY) > this.delay){
			this.onMouseUp(e);
			this.onDragDetected(e);
		}
		dojo.stopEvent(e);
	},
	onMouseUp: function(e){
		// summary: event processor for onmouseup, used only for delayed drags
		// e: Event: mouse event
		for(var i = 0; i < 2; ++i){
			dojo.disconnect(this.events.pop());
		}
		dojo.stopEvent(e);
	},
	onSelectStart: function(e){
		// summary: event processor for onselectevent and ondragevent
		// e: Event: mouse event
		if(!this.skip || !dojo.dnd.isFormElement(e)){
			dojo.stopEvent(e);
		}
	},
	
	// local events
	onDragDetected: function(/* Event */ e){
		// summary: called when the drag is detected,
		// responsible for creation of the mover
		new this.mover(this.node, e, this);
	},
	onMoveStart: function(/* dojo.dnd.Mover */ mover){
		// summary: called before every move operation
		dojo.publish("/dnd/move/start", [mover]);
		dojo.addClass(dojo.body(), "dojoMove"); 
		dojo.addClass(this.node, "dojoMoveItem"); 
	},
	onMoveStop: function(/* dojo.dnd.Mover */ mover){
		// summary: called after every move operation
		dojo.publish("/dnd/move/stop", [mover]);
		dojo.removeClass(dojo.body(), "dojoMove");
		dojo.removeClass(this.node, "dojoMoveItem");
	},
	onFirstMove: function(/* dojo.dnd.Mover */ mover){
		// summary: called during the very first move notification,
		//	can be used to initialize coordinates, can be overwritten.
		
		// default implementation does nothing
	},
	onMove: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called during every move notification,
		//	should actually move the node, can be overwritten.
		this.onMoving(mover, leftTop);
		var s = mover.node.style;
		s.left = leftTop.l + "px";
		s.top  = leftTop.t + "px";
		this.onMoved(mover, leftTop);
	},
	onMoving: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called before every incremental move,
		//	can be overwritten.
		
		// default implementation does nothing
	},
	onMoved: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called after every incremental move,
		//	can be overwritten.
		
		// default implementation does nothing
	}
});

}

if(!dojo._hasResource["dojo.dnd.move"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.move"] = true;
dojo.provide("dojo.dnd.move");




dojo.declare("dojo.dnd.move.constrainedMoveable", dojo.dnd.Moveable, {
	// object attributes (for markup)
	constraints: function(){},
	within: false,
	
	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.move.constrainedMoveable(node, params);
	},

	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		constraints: Function: a function, which calculates a constraint box,
		//			it is called in a context of the moveable object.
		//		within: Boolean: restrict move within boundaries.
		//	the rest is passed to the base class
		if(!params){ params = {}; }
		this.constraints = params.constraints;
		this.within = params.within;
	},
	onFirstMove: function(/* dojo.dnd.Mover */ mover){
		// summary: called during the very first move notification,
		//	can be used to initialize coordinates, can be overwritten.
		var c = this.constraintBox = this.constraints.call(this, mover);
		c.r = c.l + c.w;
		c.b = c.t + c.h;
		if(this.within){
			var mb = dojo.marginBox(mover.node);
			c.r -= mb.w;
			c.b -= mb.h;
		}
	},
	onMove: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
		// summary: called during every move notification,
		//	should actually move the node, can be overwritten.
		var c = this.constraintBox, s = mover.node.style;
		s.left = (leftTop.l < c.l ? c.l : c.r < leftTop.l ? c.r : leftTop.l) + "px";
		s.top  = (leftTop.t < c.t ? c.t : c.b < leftTop.t ? c.b : leftTop.t) + "px";
	}
});

dojo.declare("dojo.dnd.move.boxConstrainedMoveable", dojo.dnd.move.constrainedMoveable, {
	// object attributes (for markup)
	box: {},
	
	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.move.boxConstrainedMoveable(node, params);
	},

	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		box: Object: a constraint box
		//	the rest is passed to the base class
		var box = params && params.box;
		this.constraints = function(){ return box; };
	}
});

dojo.declare("dojo.dnd.move.parentConstrainedMoveable", dojo.dnd.move.constrainedMoveable, {
	// object attributes (for markup)
	area: "content",

	// markup methods
	markupFactory: function(params, node){
		return new dojo.dnd.move.parentConstrainedMoveable(node, params);
	},

	constructor: function(node, params){
		// summary: an object, which makes a node moveable
		// node: Node: a node (or node's id) to be moved
		// params: Object: an optional object with additional parameters;
		//	following parameters are recognized:
		//		area: String: a parent's area to restrict the move,
		//			can be "margin", "border", "padding", or "content".
		//	the rest is passed to the base class
		var area = params && params.area;
		this.constraints = function(){
			var n = this.node.parentNode, 
				s = dojo.getComputedStyle(n), 
				mb = dojo._getMarginBox(n, s);
			if(area == "margin"){
				return mb;	// Object
			}
			var t = dojo._getMarginExtents(n, s);
			mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
			if(area == "border"){
				return mb;	// Object
			}
			t = dojo._getBorderExtents(n, s);
			mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
			if(area == "padding"){
				return mb;	// Object
			}
			t = dojo._getPadExtents(n, s);
			mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
			return mb;	// Object
		};
	}
});

// WARNING: below are obsolete objects, instead of custom movers use custom moveables (above)

dojo.dnd.move.constrainedMover = function(fun, within){
	// summary: returns a constrained version of dojo.dnd.Mover
	// description: this function produces n object, which will put a constraint on 
	//	the margin box of dragged object in absolute coordinates
	// fun: Function: called on drag, and returns a constraint box
	// within: Boolean: if true, constraints the whole dragged object withtin the rectangle, 
	//	otherwise the constraint is applied to the left-top corner
	dojo.deprecated("dojo.dnd.move.constrainedMover, use dojo.dnd.move.constrainedMoveable instead");
	var mover = function(node, e, notifier){
		dojo.dnd.Mover.call(this, node, e, notifier);
	};
	dojo.extend(mover, dojo.dnd.Mover.prototype);
	dojo.extend(mover, {
		onMouseMove: function(e){
			// summary: event processor for onmousemove
			// e: Event: mouse event
			dojo.dnd.autoScroll(e);
			var m = this.marginBox, c = this.constraintBox,
				l = m.l + e.pageX, t = m.t + e.pageY;
			l = l < c.l ? c.l : c.r < l ? c.r : l;
			t = t < c.t ? c.t : c.b < t ? c.b : t;
			this.host.onMove(this, {l: l, t: t});
		},
		onFirstMove: function(){
			// summary: called once to initialize things; it is meant to be called only once
			dojo.dnd.Mover.prototype.onFirstMove.call(this);
			var c = this.constraintBox = fun.call(this);
			c.r = c.l + c.w;
			c.b = c.t + c.h;
			if(within){
				var mb = dojo.marginBox(this.node);
				c.r -= mb.w;
				c.b -= mb.h;
			}
		}
	});
	return mover;	// Object
};

dojo.dnd.move.boxConstrainedMover = function(box, within){
	// summary: a specialization of dojo.dnd.constrainedMover, which constrains to the specified box
	// box: Object: a constraint box (l, t, w, h)
	// within: Boolean: if true, constraints the whole dragged object withtin the rectangle, 
	//	otherwise the constraint is applied to the left-top corner
	dojo.deprecated("dojo.dnd.move.boxConstrainedMover, use dojo.dnd.move.boxConstrainedMoveable instead");
	return dojo.dnd.move.constrainedMover(function(){ return box; }, within);	// Object
};

dojo.dnd.move.parentConstrainedMover = function(area, within){
	// summary: a specialization of dojo.dnd.constrainedMover, which constrains to the parent node
	// area: String: "margin" to constrain within the parent's margin box, "border" for the border box,
	//	"padding" for the padding box, and "content" for the content box; "content" is the default value.
	// within: Boolean: if true, constraints the whole dragged object withtin the rectangle, 
	//	otherwise the constraint is applied to the left-top corner
	dojo.deprecated("dojo.dnd.move.parentConstrainedMover, use dojo.dnd.move.parentConstrainedMoveable instead");
	var fun = function(){
		var n = this.node.parentNode, 
			s = dojo.getComputedStyle(n), 
			mb = dojo._getMarginBox(n, s);
		if(area == "margin"){
			return mb;	// Object
		}
		var t = dojo._getMarginExtents(n, s);
		mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
		if(area == "border"){
			return mb;	// Object
		}
		t = dojo._getBorderExtents(n, s);
		mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
		if(area == "padding"){
			return mb;	// Object
		}
		t = dojo._getPadExtents(n, s);
		mb.l += t.l, mb.t += t.t, mb.w -= t.w, mb.h -= t.h;
		return mb;	// Object
	};
	return dojo.dnd.move.constrainedMover(fun, within);	// Object
};

// patching functions one level up for compatibility

dojo.dnd.constrainedMover = dojo.dnd.move.constrainedMover;
dojo.dnd.boxConstrainedMover = dojo.dnd.move.boxConstrainedMover;
dojo.dnd.parentConstrainedMover = dojo.dnd.move.parentConstrainedMover;

}

if(!dojo._hasResource["dojo.dnd.TimedMoveable"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.dnd.TimedMoveable"] = true;
dojo.provide("dojo.dnd.TimedMoveable");



(function(){
	// precalculate long expressions
	var oldOnMove = dojo.dnd.Moveable.prototype.onMove;
		
	dojo.declare("dojo.dnd.TimedMoveable", dojo.dnd.Moveable, {
		// summary:
		//	A specialized version of Moveable to support an FPS throttling.
		//	This class puts an upper restriction on FPS, which may reduce 
		//	the CPU load. The additional parameter "timeout" regulates
		//	the delay before actually moving the moveable object.
		
		// object attributes (for markup)
		timeout: 40,	// in ms, 40ms corresponds to 25 fps
	
		constructor: function(node, params){
			// summary: an object, which makes a node moveable with a timer
			// node: Node: a node (or node's id) to be moved
			// params: Object: an optional object with additional parameters.
			//	See dojo.dnd.Moveable for details on general parameters.
			//	Following parameters are specific for this class:
			//		timeout: Number: delay move by this number of ms
			//			accumulating position changes during the timeout
			
			// sanitize parameters
			if(!params){ params = {}; }
			if(params.timeout && typeof params.timeout == "number" && params.timeout >= 0){
				this.timeout = params.timeout;
			}
		},
	
		// markup methods
		markupFactory: function(params, node){
			return new dojo.dnd.TimedMoveable(node, params);
		},
	
		onMoveStop: function(/* dojo.dnd.Mover */ mover){
			if(mover._timer){
				// stop timer
				clearTimeout(mover._timer)
				// reflect the last received position
				oldOnMove.call(this, mover, mover._leftTop)
			}
			dojo.dnd.Moveable.prototype.onMoveStop.apply(this, arguments);
		},
		onMove: function(/* dojo.dnd.Mover */ mover, /* Object */ leftTop){
			mover._leftTop = leftTop;
			if(!mover._timer){
				var _t = this;	// to avoid using dojo.hitch()
				mover._timer = setTimeout(function(){
					// we don't have any pending requests
					mover._timer = null;
					// reflect the last received position
					oldOnMove.call(_t, mover, mover._leftTop);
				}, this.timeout);
			}
		}
	});
})();

}

if(!dojo._hasResource["dojo.fx"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.fx"] = true;
dojo.provide("dojo.fx");
dojo.provide("dojo.fx.Toggler");

/*=====
dojo.fx = {
	// summary: Effects library on top of Base animations
};
=====*/

(function(){
	var _baseObj = {
			_fire: function(evt, args){
				if(this[evt]){
					this[evt].apply(this, args||[]);
				}
				return this;
			}
		};

	var _chain = function(animations){
		this._index = -1;
		this._animations = animations||[];
		this._current = this._onAnimateCtx = this._onEndCtx = null;

		this.duration = 0;
		dojo.forEach(this._animations, function(a){
			this.duration += a.duration;
			if(a.delay){ this.duration += a.delay; }
		}, this);
	};
	dojo.extend(_chain, {
		_onAnimate: function(){
			this._fire("onAnimate", arguments);
		},
		_onEnd: function(){
			dojo.disconnect(this._onAnimateCtx);
			dojo.disconnect(this._onEndCtx);
			this._onAnimateCtx = this._onEndCtx = null;
			if(this._index + 1 == this._animations.length){
				this._fire("onEnd");
			}else{
				// switch animations
				this._current = this._animations[++this._index];
				this._onAnimateCtx = dojo.connect(this._current, "onAnimate", this, "_onAnimate");
				this._onEndCtx = dojo.connect(this._current, "onEnd", this, "_onEnd");
				this._current.play(0, true);
			}
		},
		play: function(/*int?*/ delay, /*Boolean?*/ gotoStart){
			if(!this._current){ this._current = this._animations[this._index = 0]; }
			if(!gotoStart && this._current.status() == "playing"){ return this; }
			var beforeBegin = dojo.connect(this._current, "beforeBegin", this, function(){
					this._fire("beforeBegin");
				}),
				onBegin = dojo.connect(this._current, "onBegin", this, function(arg){
					this._fire("onBegin", arguments);
				}),
				onPlay = dojo.connect(this._current, "onPlay", this, function(arg){
					this._fire("onPlay", arguments);
					dojo.disconnect(beforeBegin);
					dojo.disconnect(onBegin);
					dojo.disconnect(onPlay);
				});
			if(this._onAnimateCtx){
				dojo.disconnect(this._onAnimateCtx);
			}
			this._onAnimateCtx = dojo.connect(this._current, "onAnimate", this, "_onAnimate");
			if(this._onEndCtx){
				dojo.disconnect(this._onEndCtx);
			}
			this._onEndCtx = dojo.connect(this._current, "onEnd", this, "_onEnd");
			this._current.play.apply(this._current, arguments);
			return this;
		},
		pause: function(){
			if(this._current){
				var e = dojo.connect(this._current, "onPause", this, function(arg){
						this._fire("onPause", arguments);
						dojo.disconnect(e);
					});
				this._current.pause();
			}
			return this;
		},
		gotoPercent: function(/*Decimal*/percent, /*Boolean?*/ andPlay){
			this.pause();
			var offset = this.duration * percent;
			this._current = null;
			dojo.some(this._animations, function(a){
				if(a.duration <= offset){
					this._current = a;
					return true;
				}
				offset -= a.duration;
				return false;
			});
			if(this._current){
				this._current.gotoPercent(offset / this._current.duration, andPlay);
			}
			return this;
		},
		stop: function(/*boolean?*/ gotoEnd){
			if(this._current){
				if(gotoEnd){
					for(; this._index + 1 < this._animations.length; ++this._index){
						this._animations[this._index].stop(true);
					}
					this._current = this._animations[this._index];
				}
				var e = dojo.connect(this._current, "onStop", this, function(arg){
						this._fire("onStop", arguments);
						dojo.disconnect(e);
					});
				this._current.stop();
			}
			return this;
		},
		status: function(){
			return this._current ? this._current.status() : "stopped";
		},
		destroy: function(){
			if(this._onAnimateCtx){ dojo.disconnect(this._onAnimateCtx); }
			if(this._onEndCtx){ dojo.disconnect(this._onEndCtx); }
		}
	});
	dojo.extend(_chain, _baseObj);

	dojo.fx.chain = function(/*dojo._Animation[]*/ animations){
		// summary: Chain a list of dojo._Animation s to run in sequence
		// example:
		//	|	dojo.fx.chain([
		//	|		dojo.fadeIn({ node:node }),
		//	|		dojo.fadeOut({ node:otherNode })
		//	|	]).play();
		//
		return new _chain(animations) // dojo._Animation
	};

	var _combine = function(animations){
		this._animations = animations||[];
		this._connects = [];
		this._finished = 0;

		this.duration = 0;
		dojo.forEach(animations, function(a){
			var duration = a.duration;
			if(a.delay){ duration += a.delay; }
			if(this.duration < duration){ this.duration = duration; }
			this._connects.push(dojo.connect(a, "onEnd", this, "_onEnd"));
		}, this);
		
		this._pseudoAnimation = new dojo._Animation({curve: [0, 1], duration: this.duration});
		dojo.forEach(["beforeBegin", "onBegin", "onPlay", "onAnimate", "onPause", "onStop"], 
			function(evt){
				this._connects.push(dojo.connect(this._pseudoAnimation, evt, dojo.hitch(this, "_fire", evt)));
			},
			this
		);
	};
	dojo.extend(_combine, {
		_doAction: function(action, args){
			dojo.forEach(this._animations, function(a){
				a[action].apply(a, args);
			});
			return this;
		},
		_onEnd: function(){
			if(++this._finished == this._animations.length){
				this._fire("onEnd");
			}
		},
		_call: function(action, args){
			var t = this._pseudoAnimation;
			t[action].apply(t, args);
		},
		play: function(/*int?*/ delay, /*Boolean?*/ gotoStart){
			this._finished = 0;
			this._doAction("play", arguments);
			this._call("play", arguments);
			return this;
		},
		pause: function(){
			this._doAction("pause", arguments);
			this._call("pause", arguments);
			return this;
		},
		gotoPercent: function(/*Decimal*/percent, /*Boolean?*/ andPlay){
			var ms = this.duration * percent;
			dojo.forEach(this._animations, function(a){
				a.gotoPercent(a.duration < ms ? 1 : (ms / a.duration), andPlay);
			});
			this._call("gotoPercent", arguments);
			return this;
		},
		stop: function(/*boolean?*/ gotoEnd){
			this._doAction("stop", arguments);
			this._call("stop", arguments);
			return this;
		},
		status: function(){
			return this._pseudoAnimation.status();
		},
		destroy: function(){
			dojo.forEach(this._connects, dojo.disconnect);
		}
	});
	dojo.extend(_combine, _baseObj);

	dojo.fx.combine = function(/*dojo._Animation[]*/ animations){
		// summary: Combine a list of dojo._Animation s to run in parallel
		// example:
		//	|	dojo.fx.combine([
		//	|		dojo.fadeIn({ node:node }),
		//	|		dojo.fadeOut({ node:otherNode })
		//	|	]).play();
		return new _combine(animations); // dojo._Animation
	};
})();

dojo.declare("dojo.fx.Toggler", null, {
	// summary:
	//		class constructor for an animation toggler. It accepts a packed
	//		set of arguments about what type of animation to use in each
	//		direction, duration, etc.
	//
	// example:
	//	|	var t = new dojo.fx.Toggler({
	//	|		node: "nodeId",
	//	|		showDuration: 500,
	//	|		// hideDuration will default to "200"
	//	|		showFunc: dojo.wipeIn, 
	//	|		// hideFunc will default to "fadeOut"
	//	|	});
	//	|	t.show(100); // delay showing for 100ms
	//	|	// ...time passes...
	//	|	t.hide();

	// FIXME: need a policy for where the toggler should "be" the next
	// time show/hide are called if we're stopped somewhere in the
	// middle.

	constructor: function(args){
		var _t = this;

		dojo.mixin(_t, args);
		_t.node = args.node;
		_t._showArgs = dojo.mixin({}, args);
		_t._showArgs.node = _t.node;
		_t._showArgs.duration = _t.showDuration;
		_t.showAnim = _t.showFunc(_t._showArgs);

		_t._hideArgs = dojo.mixin({}, args);
		_t._hideArgs.node = _t.node;
		_t._hideArgs.duration = _t.hideDuration;
		_t.hideAnim = _t.hideFunc(_t._hideArgs);

		dojo.connect(_t.showAnim, "beforeBegin", dojo.hitch(_t.hideAnim, "stop", true));
		dojo.connect(_t.hideAnim, "beforeBegin", dojo.hitch(_t.showAnim, "stop", true));
	},

	// node: DomNode
	//	the node to toggle
	node: null,

	// showFunc: Function
	//	The function that returns the dojo._Animation to show the node
	showFunc: dojo.fadeIn,

	// hideFunc: Function	
	//	The function that returns the dojo._Animation to hide the node
	hideFunc: dojo.fadeOut,

	// showDuration:
	//	Time in milliseconds to run the show Animation
	showDuration: 200,

	// hideDuration:
	//	Time in milliseconds to run the hide Animation
	hideDuration: 200,

	/*=====
	_showArgs: null,
	_showAnim: null,

	_hideArgs: null,
	_hideAnim: null,

	_isShowing: false,
	_isHiding: false,
	=====*/

	show: function(delay){
		// summary: Toggle the node to showing
		return this.showAnim.play(delay || 0);
	},

	hide: function(delay){
		// summary: Toggle the node to hidden
		return this.hideAnim.play(delay || 0);
	}
});

dojo.fx.wipeIn = function(/*Object*/ args){
	// summary
	//		Returns an animation that will expand the
	//		node defined in 'args' object from it's current height to
	//		it's natural height (with no scrollbar).
	//		Node must have no margin/border/padding.
	args.node = dojo.byId(args.node);
	var node = args.node, s = node.style, o;

	var anim = dojo.animateProperty(dojo.mixin({
		properties: {
			height: {
				// wrapped in functions so we wait till the last second to query (in case value has changed)
				start: function(){
					// start at current [computed] height, but use 1px rather than 0
					// because 0 causes IE to display the whole panel
					o = s.overflow;
					s.overflow="hidden";
					if(s.visibility=="hidden"||s.display=="none"){
						s.height="1px";
						s.display="";
						s.visibility="";
						return 1;
					}else{
						var height = dojo.style(node, "height");
						return Math.max(height, 1);
					}
				},
				end: function(){
					return node.scrollHeight;
				}
			}
		}
	}, args));

	dojo.connect(anim, "onEnd", function(){ 
		s.height = "auto";
		s.overflow = o;
	});

	return anim; // dojo._Animation
}

dojo.fx.wipeOut = function(/*Object*/ args){
	// summary
	//		Returns an animation that will shrink node defined in "args"
	//		from it's current height to 1px, and then hide it.
	var node = args.node = dojo.byId(args.node);
	var s = node.style;
	var o;

	var anim = dojo.animateProperty(dojo.mixin({
		properties: {
			height: {
				end: 1 // 0 causes IE to display the whole panel
			}
		}
	}, args));

	dojo.connect(anim, "beforeBegin", function(){
		o = s.overflow;
		s.overflow = "hidden";
		s.display = "";
	});
	dojo.connect(anim, "onEnd", function(){
		s.overflow = o;
		s.height = "auto";
		s.display = "none";
	});

	return anim; // dojo._Animation
}

dojo.fx.slideTo = function(/*Object?*/ args){
	// summary
	//		Returns an animation that will slide "node" 
	//		defined in args Object from its current position to
	//		the position defined by (args.left, args.top).
	// example:
	//	|	dojo.fx.slideTo({ node: node, left:"40", top:"50", unit:"px" }).play()

	var node = (args.node = dojo.byId(args.node));
	
	var top = null;
	var left = null;
	
	var init = (function(n){
		return function(){
			var cs = dojo.getComputedStyle(n);
			var pos = cs.position;
			top = (pos == 'absolute' ? n.offsetTop : parseInt(cs.top) || 0);
			left = (pos == 'absolute' ? n.offsetLeft : parseInt(cs.left) || 0);
			if(pos != 'absolute' && pos != 'relative'){
				var ret = dojo.coords(n, true);
				top = ret.y;
				left = ret.x;
				n.style.position="absolute";
				n.style.top=top+"px";
				n.style.left=left+"px";
			}
		};
	})(node);
	init();

	var anim = dojo.animateProperty(dojo.mixin({
		properties: {
			top: { end: args.top||0 },
			left: { end: args.left||0 }
		}
	}, args));
	dojo.connect(anim, "beforeBegin", anim, init);

	return anim; // dojo._Animation
}

}

if(!dojo._hasResource["dojo.html"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.html"] = true;
dojo.provide("dojo.html");

// the parser might be needed..
 

(function(){ // private scope, sort of a namespace

	// idCounter is incremented with each instantiation to allow asignment of a unique id for tracking, logging purposes
	var idCounter = 0; 

	dojo.html._secureForInnerHtml = function(/*String*/ cont){
		// summary:
		//		removes !DOCTYPE and title elements from the html string.
		// 
		//		khtml is picky about dom faults, you can't attach a style or <title> node as child of body
		//		must go into head, so we need to cut out those tags
		//	cont:
		//		An html string for insertion into the dom
		//	
		return cont.replace(/(?:\s*<!DOCTYPE\s[^>]+>|<title[^>]*>[\s\S]*?<\/title>)/ig, ""); // String
	};

	dojo.html._emptyNode = function(/* DomNode */ node){
		// summary:
		//		removes all child nodes from the given node
		//	node:
		//		the parent element

		while(node.firstChild){
			dojo._destroyElement(node.firstChild);
		}
	};

	dojo.html._setNodeContent = function(/* DomNode */ node, /* String|DomNode|NodeList */ cont, /* Boolean? */ shouldEmptyFirst){
		// summary:
		//		inserts the given content into the given node
		//		overlaps similiar functionality in dijit.layout.ContentPane._setContent
		//	node:
		//		the parent element
		//	content:
		//		the content to be set on the parent element. 
		//		This can be an html string, a node reference or a NodeList, dojo.NodeList, Array or other enumerable list of nodes
		// shouldEmptyFirst
		//		if shouldEmptyFirst is true, the node will first be emptied of all content before the new content is inserted
		//		defaults to false
		if(shouldEmptyFirst){
			dojo.html._emptyNode(node); 
		}

		if(typeof cont == "string"){
			// there's some hoops to jump through before we can set innerHTML on the would-be parent element. 
	
			// rationale for this block:
			// if node is a table derivate tag, some browsers dont allow innerHTML on those
			// TODO: <select>, <dl>? what other elements will give surprises if you naively set innerHTML?
			
			var pre = '', post = '', walk = 0, name = node.nodeName.toLowerCase();
			switch(name){
				case 'tr':
					pre = '<tr>'; post = '</tr>';
					walk += 1;//fallthrough
				case 'tbody': case 'thead':// children of THEAD is of same type as TBODY
					pre = '<tbody>' + pre; post += '</tbody>';
					walk += 1;// falltrough
				case 'table':
					pre = '<table>' + pre; post += '</table>';
					walk += 1;
					break;
			}
			if(walk){
				var n = node.ownerDocument.createElement('div');
				n.innerHTML = pre + cont + post;
				do{
					n = n.firstChild;
				}while(--walk);
				// now we can safely add the child nodes...
				dojo.forEach(n.childNodes, function(n){
					node.appendChild(n.cloneNode(true));
				});
			}else{
				// innerHTML the content as-is into the node (element)
				// should we ever support setting content on non-element node types? 
				// e.g. text nodes, comments, etc.?
				node.innerHTML = cont;
			}

		}else{
			// DomNode or NodeList
			if(cont.nodeType){ // domNode (htmlNode 1 or textNode 3)
				node.appendChild(cont);
			}else{// nodelist or array such as dojo.Nodelist
				dojo.forEach(cont, function(n){
					node.appendChild(n.cloneNode(true));
				});
			}
		}
		// return DomNode
		return node;
	};

	// we wrap up the content-setting operation in a object
	dojo.declare("dojo.html._ContentSetter", null, 
		{
			// node: DomNode|String
			//		An node which will be the parent element that we set content into
			node: "",

			// content: String|DomNode|DomNode[]
			//		The content to be placed in the node. Can be an HTML string, a node reference, or a enumerable list of nodes
			content: "",
			
			// id: String?
			//		Usually only used internally, and auto-generated with each instance 
			id: "",

			// cleanContent: Boolean
			//		Should the content be treated as a full html document, 
			//		and the real content stripped of <html>, <body> wrapper before injection
			cleanContent: false,
			
			// extractContent: Boolean
			//		Should the content be treated as a full html document, and the real content stripped of <html>, <body> wrapper before injection
			extractContent: false,

			// parseContent: Boolean
			//		Should the node by passed to the parser after the new content is set
			parseContent: false,
			
			// lifecyle methods
			constructor: function(/* Object */params, /* String|DomNode */node){
				//	summary:
				//		Provides a configurable, extensible object to wrap the setting on content on a node
				//		call the set() method to actually set the content..
 
				// the original params are mixed directly into the instance "this"
				dojo.mixin(this, params || {});

				// give precedence to params.node vs. the node argument
				// and ensure its a node, not an id string
				node = this.node = dojo.byId( this.node || node );
	
				if(!this.id){
					this.id = [
						"Setter",
						(node) ? node.id || node.tagName : "", 
						idCounter++
					].join("_");
				}

				if(! (this.node || node)){
					new Error(this.declaredClass + ": no node provided to " + this.id);
				}
			},
			set: function(/* String|DomNode|NodeList? */ cont, /* Object? */ params){
				// summary:
				//		front-end to the set-content sequence 
				//	cont:
				//		An html string, node or enumerable list of nodes for insertion into the dom
				//		If not provided, the object's content property will be used
				if(undefined !== cont){
					this.content = cont;
				}
				// in the re-use scenario, set needs to be able to mixin new configuration
				if(params){
					this._mixin(params);
				}

				this.onBegin();
				this.setContent();
				this.onEnd();

				return this.node;
			},
			setContent: function(){
				// summary:
				//		sets the content on the node 

				var node = this.node; 
				if(!node) {
					console.error("setContent given no node");
				}
				try{
					node = dojo.html._setNodeContent(node, this.content);
				}catch(e){
					// check if a domfault occurs when we are appending this.errorMessage
					// like for instance if domNode is a UL and we try append a DIV
	
					// FIXME: need to allow the user to provide a content error message string
					var errMess = this.onContentError(e); 
					try{
						node.innerHTML = errMess;
					}catch(e){
						console.error('Fatal ' + this.declaredClass + '.setContent could not change content due to '+e.message, e);
					}
				}
				// always put back the node for the next method
				this.node = node; // DomNode
			},
			
			empty: function() {
				// summary
				//	cleanly empty out existing content

				// destroy any widgets from a previous run
				// NOTE: if you dont want this you'll need to empty 
				// the parseResults array property yourself to avoid bad things happenning
				if(this.parseResults && this.parseResults.length) {
					dojo.forEach(this.parseResults, function(w) {
						if(w.destroy){
							w.destroy();
						}
					});
					delete this.parseResults;
				}
				// this is fast, but if you know its already empty or safe, you could 
				// override empty to skip this step
				dojo.html._emptyNode(this.node);
			},
	
			onBegin: function(){
				// summary
				//		Called after instantiation, but before set(); 
				//		It allows modification of any of the object properties 
				//		- including the node and content provided - before the set operation actually takes place
				//		This default implementation checks for cleanContent and extractContent flags to 
				//		optionally pre-process html string content
				var cont = this.content;
	
				if(dojo.isString(cont)){
					if(this.cleanContent){
						cont = dojo.html._secureForInnerHtml(cont);
					}
  
					if(this.extractContent){
						var match = cont.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
						if(match){ cont = match[1]; }
					}
				}

				// clean out the node and any cruft associated with it - like widgets
				this.empty();
				
				this.content = cont;
				return this.node; /* DomNode */
			},
	
			onEnd: function(){
				// summary
				//		Called after set(), when the new content has been pushed into the node
				//		It provides an opportunity for post-processing before handing back the node to the caller
				//		This default implementation checks a parseContent flag to optionally run the dojo parser over the new content
				if(this.parseContent){
					// populates this.parseResults if you need those..
					this._parse();
				}
				return this.node; /* DomNode */
			},
	
			tearDown: function(){
				// summary
				//		manually reset the Setter instance if its being re-used for example for another set()
				// description
				//		tearDown() is not called automatically. 
				//		In normal use, the Setter instance properties are simply allowed to fall out of scope
				//		but the tearDown method can be called to explicitly reset this instance.
				delete this.parseResults; 
				delete this.node; 
				delete this.content; 
			},
  
			onContentError: function(err){
				return "Error occured setting content: " + err; 
			},
			
			_mixin: function(params){
				// mix properties/methods into the instance
				// TODO: the intention with tearDown is to put the Setter's state 
				// back to that of the original constructor (vs. deleting/resetting everything regardless of ctor params)
				// so we could do something here to move the original properties aside for later restoration
				var empty = {}, key;
				for(key in params){
					if(key in empty){ continue; }
					// TODO: here's our opportunity to mask the properties we dont consider configurable/overridable
					// .. but history shows we'll almost always guess wrong
					this[key] = params[key]; 
				}
			},
			_parse: function(){
				// summary: 
				//		runs the dojo parser over the node contents, storing any results in this.parseResults
				//		Any errors resulting from parsing are passed to _onError for handling

				var rootNode = this.node;
				try{
					// store the results (widgets, whatever) for potential retrieval
					this.parseResults = dojo.parser.parse(rootNode, true);
				}catch(e){
					this._onError('Content', e, "Error parsing in _ContentSetter#"+this.id);
				}
			},
  
			_onError: function(type, err, consoleText){
				// summary:
				//		shows user the string that is returned by on[type]Error
				//		overide/implement on[type]Error and return your own string to customize
				var errText = this['on' + type + 'Error'].call(this, err);
				if(consoleText){
					console.error(consoleText, err);
				}else if(errText){ // a empty string won't change current content
					dojo.html._setNodeContent(this.node, errText, true);
				}
			}
	}); // end dojo.declare()

	dojo.html.set = function(/* DomNode */ node, /* String|DomNode|NodeList */ cont, /* Object? */ params){
			// summary:
			//		inserts (replaces) the given content into the given node
			//	node:
			//		the parent element that will receive the content
			//	cont:
			//		the content to be set on the parent element. 
			//		This can be an html string, a node reference or a NodeList, dojo.NodeList, Array or other enumerable list of nodes
			//	params: 
			//		Optional flags/properties to configure the content-setting. See dojo.html._ContentSetter
			//	example:
			//		A safe string/node/nodelist content replacement/injection with hooks for extension
			//		Example Usage: 
			//		dojo.html.set(node, "some string"); 
			//		dojo.html.set(node, contentNode, {options}); 
			//		dojo.html.set(node, myNode.childNodes, {options}); 
		if(undefined == cont){
			console.warn("dojo.html.set: no cont argument provided, using empty string");
			cont = "";
		}	
		if(!params){
			// simple and fast
			return dojo.html._setNodeContent(node, cont, true);
		}else{ 
			// more options but slower
			// note the arguments are reversed in order, to match the convention for instantiation via the parser
			var op = new dojo.html._ContentSetter(dojo.mixin( 
					params, 
					{ content: cont, node: node } 
			));
			return op.set();
		}
	};
})();

}

if(!dojo._hasResource["dijit.layout.ContentPane"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.ContentPane"] = true;
dojo.provide("dijit.layout.ContentPane");









dojo.declare(
	"dijit.layout.ContentPane",
	dijit._Widget,
{
	// summary:
	//		A widget that acts as a Container for other widgets, and includes a ajax interface
	// description:
	//		A widget that can be used as a standalone widget
	//		or as a baseclass for other widgets
	//		Handles replacement of document fragment using either external uri or javascript
	//		generated markup or DOM content, instantiating widgets within that content.
	//		Don't confuse it with an iframe, it only needs/wants document fragments.
	//		It's useful as a child of LayoutContainer, SplitContainer, or TabContainer.
	//		But note that those classes can contain any widget as a child.
	// example:
	//		Some quick samples:
	//		To change the innerHTML use .attr('content', '<b>new content</b>')
	//
	//		Or you can send it a NodeList, .attr('content', dojo.query('div [class=selected]', userSelection))
	//		please note that the nodes in NodeList will copied, not moved
	//
	//		To do a ajax update use .attr('href', url)

	// href: String
	//		The href of the content that displays now.
	//		Set this at construction if you want to load data externally when the
	//		pane is shown.	(Set preload=true to load it immediately.)
	//		Changing href after creation doesn't have any effect; use attr('href', ...);
	href: "",

/*=====
	// content: String
	//		The innerHTML of the ContentPane.
	//		Note that the initialization parameter / argument to attr("content", ...)
	//		can be a String, DomNode, Nodelist, or widget.
	content: "",
=====*/

	// extractContent: Boolean
	//	Extract visible content from inside of <body> .... </body>
	extractContent: false,

	// parseOnLoad: Boolean
	//	parse content and create the widgets, if any
	parseOnLoad:	true,

	// preventCache: Boolean
	//		Cache content retreived externally
	preventCache:	false,

	// preload: Boolean
	//	Force load of data even if pane is hidden.
	preload: false,

	// refreshOnShow: Boolean
	//		Refresh (re-download) content when pane goes from hidden to shown
	refreshOnShow: false,

	// loadingMessage: String
	//	Message that shows while downloading
	loadingMessage: "<span class='dijitContentPaneLoading'>${loadingState}</span>", 

	// errorMessage: String
	//	Message that shows if an error occurs
	errorMessage: "<span class='dijitContentPaneError'>${errorState}</span>", 

	// isLoaded: Boolean
	//	Tells loading status see onLoad|onUnload for event hooks
	isLoaded: false,

	baseClass: "dijitContentPane",

	// doLayout: Boolean
	//		- false - don't adjust size of children
	//		- true - if there is a single visible child widget, set it's size to
	//				however big the ContentPane is
	doLayout: true,

	// whether current content is something the user specified, or just a "Loading..." message
	_isRealContent: true,

	postMixInProperties: function(){
		this.inherited(arguments);
		var messages = dojo.i18n.getLocalization("dijit", "loading", this.lang);
		this.loadingMessage = dojo.string.substitute(this.loadingMessage, messages);
		this.errorMessage = dojo.string.substitute(this.errorMessage, messages);
	},

	buildRendering: function(){
		this.inherited(arguments);
		if(!this.containerNode){
			// make getDescendants() work
			this.containerNode = this.domNode;
		}
	},

	postCreate: function(){
		// remove the title attribute so it doesn't show up when i hover
		// over a node
		this.domNode.title = "";

		if (!dijit.hasWaiRole(this.domNode)){
			dijit.setWaiRole(this.domNode, "group");
		}

		dojo.addClass(this.domNode, this.baseClass);
	},

	startup: function(){
		if(this._started){ return; }
		if(this.doLayout != "false" && this.doLayout !== false){
			this._checkIfSingleChild();
			if(this._singleChild){
				this._singleChild.startup();
			}
		}
		this._loadCheck();
		this.inherited(arguments);
	},

	_checkIfSingleChild: function(){
		// summary:
		//		Test if we have exactly one visible widget as a child,
		//		and if so assume that we are a container for that widget,
		//		and should propogate startup() and resize() calls to it.
		//		Skips over things like data stores since they aren't visible.

		var childNodes = dojo.query(">", this.containerNode),
			childWidgetNodes = childNodes.filter(function(node){
				return dojo.hasAttr(node, "dojoType") || dojo.hasAttr(node, "widgetId");
			}),
			candidateWidgets = dojo.filter(childWidgetNodes.map(dijit.byNode), function(widget){
				return widget && widget.domNode && widget.resize;
			});

		if(
			// all child nodes are widgets
			childNodes.length == childWidgetNodes.length &&

			// all but one are invisible (like dojo.data)
			candidateWidgets.length == 1
		){
			this.isContainer = true;
			this._singleChild = candidateWidgets[0];
		}else{
			delete this.isContainer;
			delete this._singleChild;
		}
	},

	refresh: function(){
		// summary:
		//	Force a refresh (re-download) of content, be sure to turn off cache

		// we return result of _prepareLoad here to avoid code dup. in dojox.layout.ContentPane
		return this._prepareLoad(true);
	},

	setHref: function(/*String|Uri*/ href){
		dojo.deprecated("dijit.layout.ContentPane.setHref() is deprecated.	Use attr('href', ...) instead.", "", "2.0");
		return this.attr("href", href);
	},
	_setHrefAttr: function(/*String|Uri*/ href){
		// summary:
		//		Hook so attr("href", ...) works.
		// description:
		//		Reset the (external defined) content of this pane and replace with new url
		//		Note: It delays the download until widget is shown if preload is false.
		//	href:
		//		url to the page you want to get, must be within the same domain as your mainpage
		this.href = href;

		// _setHrefAttr() is called during creation and by the user, after creation.
		// only in the second case do we actually load the URL; otherwise it's done in startup()
		if(this._created){
			// we return result of _prepareLoad here to avoid code dup. in dojox.layout.ContentPane
			return this._prepareLoad();
		}
	},

	setContent: function(/*String|DomNode|Nodelist*/data){
		dojo.deprecated("dijit.layout.ContentPane.setContent() is deprecated.  Use attr('content', ...) instead.", "", "2.0");
		this.attr("content", data);
	},
	_setContentAttr: function(/*String|DomNode|Nodelist*/data){
		// summary:
		//		Hook to make attr("content", ...) work.
		//		Replaces old content with data content, include style classes from old content
		//	data:
		//		the new Content may be String, DomNode or NodeList
		//
		//		if data is a NodeList (or an array of nodes) nodes are copied
		//		so you can import nodes from another document implicitly

		// clear href so we cant run refresh and clear content
		// refresh should only work if we downloaded the content
		this.href = "";

		// Cancel any in-flight requests (an attr('content') will cancel any in-flight attr('href', ...))
		this.cancel();

		this._setContent(data || "");

		this._isDownloaded = false; // mark that content is from a attr('content') not an attr('href')

		if(this.doLayout != "false" && this.doLayout !== false){
			this._checkIfSingleChild();
			if(this._singleChild && this._singleChild.resize){
				this._singleChild.startup();
				var cb = this._contentBox || dojo.contentBox(this.containerNode);
				this._singleChild.resize({w: cb.w, h: cb.h});
			}
		}
	},
	_getContentAttr: function(){
		// summary: hook to make attr("content") work
		return this.containerNode.innerHTML;
	},

	cancel: function(){
		// summary:
		//		Cancels a inflight download of content
		if(this._xhrDfd && (this._xhrDfd.fired == -1)){
			this._xhrDfd.cancel();
		}
		delete this._xhrDfd; // garbage collect
	},

	destroyRecursive: function(/*Boolean*/ preserveDom){
		// summary:
		//		Destroy the ContentPane and it's contents

		// if we have multiple controllers destroying us, bail after the first
		if(this._beingDestroyed){
			return;
		}
		this._beingDestroyed = true;
		this.inherited(arguments);
	},

	resize: function(size){
		dojo.marginBox(this.domNode, size);

		// Compute content box size in case we [later] need to size child
		// If either height or width wasn't specified by the user, then query node for it.
		// But note that setting the margin box and then immediately querying dimensions may return
		// inaccurate results, so try not to depend on it.
		var node = this.containerNode,
			mb = dojo.mixin(dojo.marginBox(node), size||{});

		var cb = this._contentBox = dijit.layout.marginBox2contentBox(node, mb);

		// If we have a single widget child then size it to fit snugly within my borders
		if(this._singleChild && this._singleChild.resize){
			// note: if widget has padding this._contentBox will have l and t set,
			// but don't pass them to resize() or it will doubly-offset the child
			this._singleChild.resize({w: cb.w, h: cb.h});
		}
	},

	_prepareLoad: function(forceLoad){
		// sets up for a xhrLoad, load is deferred until widget onShow
		// cancels a inflight download
		this.cancel();
		this.isLoaded = false;
		this._loadCheck(forceLoad);
	},

	_isShown: function(){
		// summary: returns true if the content is currently shown
		if("open" in this){
			return this.open;		// for TitlePane, etc.
		}else{
			var node = this.domNode;
			return (node.style.display != 'none')  && (node.style.visibility != 'hidden');
		}
	},

	_loadCheck: function(/*Boolean*/ forceLoad){
		// call this when you change onShow (onSelected) status when selected in parent container
		// it's used as a trigger for href download when this.domNode.display != 'none'

		// sequence:
		// if no href -> bail
		// forceLoad -> always load
		// this.preload -> load when download not in progress, domNode display doesn't matter
		// this.refreshOnShow -> load when download in progress bails, domNode display !='none' AND
		//						this.open !== false (undefined is ok), isLoaded doesn't matter
		// else -> load when download not in progress, if this.open !== false (undefined is ok) AND
		//						domNode display != 'none', isLoaded must be false

		var displayState = this._isShown();

		if(this.href && 
			(
				forceLoad ||
				(this.preload && !this.isLoaded && !this._xhrDfd) ||
				(this.refreshOnShow && displayState && !this._xhrDfd) ||
				(!this.isLoaded && displayState && !this._xhrDfd)
			)
		){
			this._downloadExternalContent();
		}
	},

	_downloadExternalContent: function(){
		// display loading message
		this._setContent(this.onDownloadStart(), true);

		var self = this;
		var getArgs = {
			preventCache: (this.preventCache || this.refreshOnShow),
			url: this.href,
			handleAs: "text"
		};
		if(dojo.isObject(this.ioArgs)){
			dojo.mixin(getArgs, this.ioArgs);
		}

		var hand = this._xhrDfd = (this.ioMethod || dojo.xhrGet)(getArgs);

		hand.addCallback(function(html){
			try{
				self._isDownloaded = true;
				self._setContent(html, false); // onload event is called from here
				self.onDownloadEnd();
			}catch(err){
				self._onError('Content', err); // onContentError
			}
			delete self._xhrDfd;
			return html;
		});

		hand.addErrback(function(err){
			if(!hand.cancelled){
				// show error message in the pane
				self._onError('Download', err); // onDownloadError
			}
			delete self._xhrDfd;
			return err;
		});
	},

	_onLoadHandler: function(data){
		// summary:
		//		This is called whenever new content is being loaded
		this.isLoaded = true;
		try{
			this.onLoad(data);
		}catch(e){
			console.error('Error '+this.widgetId+' running custom onLoad code');
		}
	},

	_onUnloadHandler: function(){
		// summary:
		//		This is called whenever the content is being unloaded
		this.isLoaded = false;
		try{
			this.onUnload();
		}catch(e){
			console.error('Error '+this.widgetId+' running custom onUnload code');
		}
	},

	destroyDescendants: function(){
		// summary:
		//		Destroy all the widgets inside the ContentPane and empty containerNode

		// Make sure we call onUnload (but only when the ContentPane has real content)
		if(this._isRealContent){
			this._onUnloadHandler();
		}

		// dojo.html._ContentSetter keeps track of child widgets, so we should use it to
		// destroy them.
		//
		// Only exception is when those child widgets were specified in original page markup
		// and created by the parser (in which case _ContentSetter doesn't know what the widgets
		// are).  Then we need to call Widget.destroyDescendants().
		//
		// Note that calling Widget.destroyDescendants() has various issues (#6954),
		//  namely that popup widgets aren't destroyed (#2056, #4980)
		// and the widgets in templates are destroyed twice (#7706)
		var setter = this._contentSetter; 
		if(setter){
			// calling empty destroys all child widgets as well as emptying the containerNode
			setter.empty();
		}else{
			this.inherited(arguments);
			dojo.html._emptyNode(this.containerNode);
		}
	},

	_setContent: function(cont, isFakeContent){
		// summary: 
		//		Insert the content into the container node

		// first get rid of child widgets
		this.destroyDescendants();

		// mark whether this should be calling the unloadHandler
		// for the content we are about to set
		this._isRealContent = !isFakeContent;
		
		// dojo.html.set will take care of the rest of the details
		// we provide an overide for the error handling to ensure the widget gets the errors 
		// configure the setter instance with only the relevant widget instance properties
		// NOTE: unless we hook into attr, or provide property setters for each property, 
		// we need to re-configure the ContentSetter with each use
		var setter = this._contentSetter; 
		if(! (setter && setter instanceof dojo.html._ContentSetter)) {
			setter = this._contentSetter = new dojo.html._ContentSetter({
				node: this.containerNode,
				_onError: dojo.hitch(this, this._onError),
				onContentError: dojo.hitch(this, function(e){
					// fires if a domfault occurs when we are appending this.errorMessage
					// like for instance if domNode is a UL and we try append a DIV
					var errMess = this.onContentError(e);
					try{
						this.containerNode.innerHTML = errMess;
					}catch(e){
						console.error('Fatal '+this.id+' could not change content due to '+e.message, e);
					}
				})/*,
				_onError */
			});
		};

		var setterParams = dojo.mixin({
			cleanContent: this.cleanContent, 
			extractContent: this.extractContent, 
			parseContent: this.parseOnLoad 
		}, this._contentSetterParams || {});
		
		dojo.mixin(setter, setterParams); 

		setter.set( (dojo.isObject(cont) && cont.domNode) ? cont.domNode : cont );

		// setter params must be pulled afresh from the ContentPane each time
		delete this._contentSetterParams;
		
		if(!isFakeContent){
			this._onLoadHandler(cont);
		}
	},

	_onError: function(type, err, consoleText){
		// shows user the string that is returned by on[type]Error
		// overide on[type]Error and return your own string to customize
		var errText = this['on' + type + 'Error'].call(this, err);
		if(consoleText){
			console.error(consoleText, err);
		}else if(errText){// a empty string won't change current content
			this._setContent(errText, true);
		}
	},

	_createSubWidgets: function(){
		// summary: scan my contents and create subwidgets
		try{
			dojo.parser.parse(this.containerNode, true);
		}catch(e){
			this._onError('Content', e, "Couldn't create widgets in "+this.id
				+(this.href ? " from "+this.href : ""));
		}
	},


	// EVENT's, should be overide-able
	onLoad: function(data){
		// summary:
		//		Event hook, is called after everything is loaded and widgetified
	},

	onUnload: function(){
		// summary:
		//		Event hook, is called before old content is cleared
	},

	onDownloadStart: function(){
		// summary:
		//		called before download starts
		//		the string returned by this function will be the html
		//		that tells the user we are loading something
		//		override with your own function if you want to change text
		return this.loadingMessage;
	},

	onContentError: function(/*Error*/ error){
		// summary:
		//		called on DOM faults, require fault etc in content
		//		default is to display errormessage inside pane
	},

	onDownloadError: function(/*Error*/ error){
		// summary:
		//		Called when download error occurs, default is to display
		//		errormessage inside pane. Overide function to change that.
		//		The string returned by this function will be the html
		//		that tells the user a error happend
		return this.errorMessage;
	},

	onDownloadEnd: function(){
		// summary:
		//		called when download is finished
	}
});

}

if(!dojo._hasResource["dijit.form.Form"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.Form"] = true;
dojo.provide("dijit.form.Form");




dojo.declare("dijit.form._FormMixin", null,
	{
	//
	//	summary:
	//		Widget corresponding to HTML form tag, for validation and serialization
	//
	//	example:
	//	|	<form dojoType="dijit.form.Form" id="myForm">
	//	|		Name: <input type="text" name="name" />
	//	|	</form>
	//	|	myObj = {name: "John Doe"};
	//	|	dijit.byId('myForm').attr('value', myObj);
	//	|
	//	|	myObj=dijit.byId('myForm').attr('value');

/*=====
    // value: Object
	//		Name/value hash for each form element.
	//		If there are multiple elements w/the same name, value is an array,
	//		unless they are radio buttons in which case value is a scalar since only
	//		one can be checked at a time.
	//
	//		If the name is a dot separated list (like a.b.c.d), it's a nested structure.
	//		Only works on widget form elements.
	// example:
	//	| { name: "John Smith", interests: ["sports", "movies"] }
=====*/
	
	//	TODO:
	//	* Repeater
	//	* better handling for arrays.  Often form elements have names with [] like
	//	* people[3].sex (for a list of people [{name: Bill, sex: M}, ...])
	//
	//	

		reset: function(){
			dojo.forEach(this.getDescendants(), function(widget){
				if(widget.reset){
					widget.reset();
				}
			});
		},

		validate: function(){
			// summary: returns if the form is valid - same as isValid - but
			//			provides a few additional (ui-specific) features.
			//			1 - it will highlight any sub-widgets that are not
			//				valid
			//			2 - it will call focus() on the first invalid 
			//				sub-widget
			var didFocus = false;
			return dojo.every(dojo.map(this.getDescendants(), function(widget){
				// Need to set this so that "required" widgets get their 
				// state set.
				widget._hasBeenBlurred = true;
				var valid = widget.disabled || !widget.validate || widget.validate();
				if (!valid && !didFocus) {
					// Set focus of the first non-valid widget
					dijit.scrollIntoView(widget.containerNode||widget.domNode);
					widget.focus();
					didFocus = true;
				}
	 			return valid;
	 		}), function(item) { return item; });
		},
	
		setValues: function(val){
			dojo.deprecated(this.declaredClass+"::setValues() is deprecated. Use attr('value', val) instead.", "", "2.0");
			return this.attr('value', val);
		},
		_setValueAttr: function(/*object*/obj){
			// summary: Fill in form values from according to an Object (in the format returned by attr('value'))

			// generate map from name --> [list of widgets with that name]
			var map = { };
			dojo.forEach(this.getDescendants(), function(widget){
				if(!widget.name){ return; }
				var entry = map[widget.name] || (map[widget.name] = [] );
				entry.push(widget);
			});

			for(var name in map){
				if(!map.hasOwnProperty(name)){
					continue;
				}
				var widgets = map[name],						// array of widgets w/this name
					values = dojo.getObject(name, false, obj);	// list of values for those widgets

				if(values===undefined){
					continue;
				}
				if(!dojo.isArray(values)){
					values = [ values ];
				}
				if(typeof widgets[0].checked == 'boolean'){
					// for checkbox/radio, values is a list of which widgets should be checked
					dojo.forEach(widgets, function(w, i){
						w.attr('value', dojo.indexOf(values, w.value) != -1);
					});
				}else if(widgets[0]._multiValue){
					// it takes an array (e.g. multi-select)
					widgets[0].attr('value', values);
				}else{
					// otherwise, values is a list of values to be assigned sequentially to each widget
					dojo.forEach(widgets, function(w, i){
						w.attr('value', values[i]);
					});					
				}
			}

			/***
			 * 	TODO: code for plain input boxes (this shouldn't run for inputs that are part of widgets)

			dojo.forEach(this.containerNode.elements, function(element){
				if (element.name == ''){return};	// like "continue"	
				var namePath = element.name.split(".");
				var myObj=obj;
				var name=namePath[namePath.length-1];
				for(var j=1,len2=namePath.length;j<len2;++j){
					var p=namePath[j - 1];
					// repeater support block
					var nameA=p.split("[");
					if (nameA.length > 1){
						if(typeof(myObj[nameA[0]]) == "undefined"){
							myObj[nameA[0]]=[ ];
						} // if

						nameIndex=parseInt(nameA[1]);
						if(typeof(myObj[nameA[0]][nameIndex]) == "undefined"){
							myObj[nameA[0]][nameIndex] = { };
						}
						myObj=myObj[nameA[0]][nameIndex];
						continue;
					} // repeater support ends

					if(typeof(myObj[p]) == "undefined"){
						myObj=undefined;
						break;
					};
					myObj=myObj[p];
				}

				if (typeof(myObj) == "undefined"){
					return;		// like "continue"
				}
				if (typeof(myObj[name]) == "undefined" && this.ignoreNullValues){
					return;		// like "continue"
				}

				// TODO: widget values (just call attr('value', ...) on the widget)

				switch(element.type){
					case "checkbox":
						element.checked = (name in myObj) &&
							dojo.some(myObj[name], function(val){ return val==element.value; });
						break;
					case "radio":
						element.checked = (name in myObj) && myObj[name]==element.value;
						break;
					case "select-multiple":
						element.selectedIndex=-1;
						dojo.forEach(element.options, function(option){
							option.selected = dojo.some(myObj[name], function(val){ return option.value == val; });
						});
						break;
					case "select-one":
						element.selectedIndex="0";
						dojo.forEach(element.options, function(option){
							option.selected = option.value == myObj[name];
						});
						break;
					case "hidden":
					case "text":
					case "textarea":
					case "password":
						element.value = myObj[name] || "";
						break;
				}
	  		});
	  		*/
		},

		getValues: function(){
			dojo.deprecated(this.declaredClass+"::getValues() is deprecated. Use attr('value') instead.", "", "2.0");
			return this.attr('value');
		},
		_getValueAttr: function(){
			// summary:
			// 		Returns Object representing form values.
			// description:
			//		Returns name/value hash for each form element.
			//		If there are multiple elements w/the same name, value is an array,
			//		unless they are radio buttons in which case value is a scalar since only
			//		one can be checked at a time.
			//
			//		If the name is a dot separated list (like a.b.c.d), creates a nested structure.
			//		Only works on widget form elements.
			// example:
			//	| { name: "John Smith", interests: ["sports", "movies"] }

			// get widget values
			var obj = { };
			dojo.forEach(this.getDescendants(), function(widget){
				var name = widget.name;
				if(!name||widget.disabled){ return; }

				// Single value widget (checkbox, radio, or plain <input> type widget
				var value = widget.attr('value');

				// Store widget's value(s) as a scalar, except for checkboxes which are automatically arrays
				if(typeof widget.checked == 'boolean'){
					if(/Radio/.test(widget.declaredClass)){
						// radio button
						if(value !== false){
							dojo.setObject(name, value, obj);
						}
					}else{
						// checkbox/toggle button
						var ary=dojo.getObject(name, false, obj);
						if(!ary){
							ary=[];
							dojo.setObject(name, ary, obj);
						}
						if(value !== false){
							ary.push(value);
						}
					}
				}else{
					// plain input
					dojo.setObject(name, value, obj);
				}
			});

			/***
			 * code for plain input boxes (see also dojo.formToObject, can we use that instead of this code?
			 * but it doesn't understand [] notation, presumably)
			var obj = { };
			dojo.forEach(this.containerNode.elements, function(elm){
				if (!elm.name)	{
					return;		// like "continue"
				}
				var namePath = elm.name.split(".");
				var myObj=obj;
				var name=namePath[namePath.length-1];
				for(var j=1,len2=namePath.length;j<len2;++j){
					var nameIndex = null;
					var p=namePath[j - 1];
					var nameA=p.split("[");
					if (nameA.length > 1){
						if(typeof(myObj[nameA[0]]) == "undefined"){
							myObj[nameA[0]]=[ ];
						} // if
						nameIndex=parseInt(nameA[1]);
						if(typeof(myObj[nameA[0]][nameIndex]) == "undefined"){
							myObj[nameA[0]][nameIndex] = { };
						}
					} else if(typeof(myObj[nameA[0]]) == "undefined"){
						myObj[nameA[0]] = { }
					} // if

					if (nameA.length == 1){
						myObj=myObj[nameA[0]];
					} else{
						myObj=myObj[nameA[0]][nameIndex];
					} // if
				} // for

				if ((elm.type != "select-multiple" && elm.type != "checkbox" && elm.type != "radio") || (elm.type=="radio" && elm.checked)){
					if(name == name.split("[")[0]){
						myObj[name]=elm.value;
					} else{
						// can not set value when there is no name
					}
				} else if (elm.type == "checkbox" && elm.checked){
					if(typeof(myObj[name]) == 'undefined'){
						myObj[name]=[ ];
					}
					myObj[name].push(elm.value);
				} else if (elm.type == "select-multiple"){
					if(typeof(myObj[name]) == 'undefined'){
						myObj[name]=[ ];
					}
					for (var jdx=0,len3=elm.options.length; jdx<len3; ++jdx){
						if (elm.options[jdx].selected){
							myObj[name].push(elm.options[jdx].value);
						}
					}
				} // if
				name=undefined;
			}); // forEach
			***/
			return obj;
		},

		// TODO: ComboBox might need time to process a recently input value.  This should be async?
	 	isValid: function(){
	 		// summary: make sure that every widget that has a validator function returns true
			this._invalidWidgets = [];
	 		return dojo.every(this.getDescendants(), function(widget){
				var isValid = widget.disabled || !widget.isValid || widget.isValid();
				if(!isValid){
					this._invalidWidgets.push(widget);
				}
				return isValid;
	 		}, this);
		},
		
		
		onValidStateChange: function(isValid){
			// summary: stub function to connect to if you want to do something
			//			(like disable/enable a submit button) when the valid 
			//			state changes on the form as a whole.
		},
		
		_widgetChange: function(widget){
			// summary: connected to a widgets onChange function - update our 
			//			valid state, if needed.
			var isValid = this._lastValidState;
			if(!widget || this._lastValidState===undefined){
				// We have passed a null widget, or we haven't been validated
				// yet - let's re-check all our children
				// This happens when we connect (or reconnect) our children
				isValid = this.isValid();
				if(this._lastValidState===undefined){
					// Set this so that we don't fire an onValidStateChange 
					// the first time
					this._lastValidState = isValid;
				}
			}else if(widget.isValid){
				this._invalidWidgets = dojo.filter(this._invalidWidgets||[], function(w){
					return (w != widget);
				}, this);
				if(!widget.isValid() && !widget.attr("disabled")){
					this._invalidWidgets.push(widget);
				}
				isValid = (this._invalidWidgets.length === 0);
			}
			if (isValid !== this._lastValidState){
				this._lastValidState = isValid;
				this.onValidStateChange(isValid);
			}
		},
		
		connectChildren: function(){
			// summary: connects to the onChange function of all children to
			//			track valid state changes.  You can call this function
			//			directly, ie. in the event that you programmatically
			//			add a widget to the form *after* the form has been
			//			initialized
			dojo.forEach(this._changeConnections, dojo.hitch(this, "disconnect"));
			var _this = this;
			
			// we connect to validate - so that it better reflects the states
			// of the widgets - also, we only connect if it has a validate
			// function (to avoid too many unneeded connections)
			var conns = this._changeConnections = [];
			dojo.forEach(dojo.filter(this.getDescendants(),
				function(item){ return item.validate; }
			),
			function(widget){
				// We are interested in whenever the widget is validated - or
				// whenever the disabled attribute on that widget is changed
				conns.push(_this.connect(widget, "validate", 
									dojo.hitch(_this, "_widgetChange", widget)));
				conns.push(_this.connect(widget, "_setDisabledAttr", 
									dojo.hitch(_this, "_widgetChange", widget)));
			});

			// Call the widget change function to update the valid state, in 
			// case something is different now.
			this._widgetChange(null);
		},
		
		startup: function(){
			this.inherited(arguments);
			// Initialize our valid state tracking.  Needs to be done in startup
			//  because it's not guaranteed that our children are initialized 
			//  yet.
			this._changeConnections = [];
			this.connectChildren();
		}
	});

dojo.declare(
	"dijit.form.Form",
	[dijit._Widget, dijit._Templated, dijit.form._FormMixin],
	{
		// summary:
		// Adds conveniences to regular HTML form

		// HTML <FORM> attributes
		name: "",
		action: "",
		method: "",
		encType: "",
		"accept-charset": "",
		accept: "",
		target: "",

		templateString: "<form dojoAttachPoint='containerNode' dojoAttachEvent='onreset:_onReset,onsubmit:_onSubmit' name='${name}'></form>",

		attributeMap: dojo.mixin(dojo.clone(dijit._Widget.prototype.attributeMap),
			{action: "", method: "", encType: "", "accept-charset": "", accept: "", target: ""}),

		execute: function(/*Object*/ formContents){
			//	summary:
			//		Deprecated: use submit()
		},

		onExecute: function(){
			// summary:
			//		Deprecated: use onSubmit()
		},

		_setEncTypeAttr: function(/*String*/ value){
			this.encType = value;
			dojo.attr(this.domNode, "encType", value);
			if(dojo.isIE){ this.domNode.encoding = value; }
		},

		postCreate: function(){
			// IE tries to hide encType
			// TODO: this code should be in parser, not here.
			if(dojo.isIE && this.srcNodeRef && this.srcNodeRef.attributes){
				var item = this.srcNodeRef.attributes.getNamedItem('encType');
				if(item && !item.specified && (typeof item.value == "string")){
					this.attr('encType', item.value);
				}
			}
			this.inherited(arguments);
		},

		onReset: function(/*Event?*/e){ 
			//	summary:
			//		Callback when user resets the form. This method is intended
			//		to be over-ridden. When the `reset` method is called
			//		programmatically, the return value from `onReset` is used
			//		to compute whether or not resetting should proceed
			return true; // Boolean
		},

		_onReset: function(e){
			// create fake event so we can know if preventDefault() is called
			var faux = {
				returnValue: true, // the IE way
				preventDefault: function(){  // not IE
							this.returnValue = false;
						},
				stopPropagation: function(){}, currentTarget: e.currentTarget, target: e.target
			};
			// if return value is not exactly false, and haven't called preventDefault(), then reset
			if(!(this.onReset(faux) === false) && faux.returnValue){
				this.reset();
			}
			dojo.stopEvent(e);
			return false;
		},

		_onSubmit: function(e){
			var fp = dijit.form.Form.prototype;
			// TODO: remove ths if statement beginning with 2.0
			if(this.execute != fp.execute || this.onExecute != fp.onExecute){
				dojo.deprecated("dijit.form.Form:execute()/onExecute() are deprecated. Use onSubmit() instead.", "", "2.0");
				this.onExecute();
				this.execute(this.getValues());
			}
			if(this.onSubmit(e) === false){ // only exactly false stops submit
				dojo.stopEvent(e);
			}
		},
		
		onSubmit: function(/*Event?*/e){ 
			//	summary:
			//		Callback when user submits the form. This method is
			//		intended to be over-ridden, but by default it checks and
			//		returns the validity of form elements. When the `submit`
			//		method is called programmatically, the return value from
			//		`onSubmit` is used to compute whether or not submission
			//		should proceed

			return this.isValid(); // Boolean
		},

		submit: function(){
			// summary:
			//		programmatically submit form if and only if the `onSubmit` returns true
			if(!(this.onSubmit() === false)){
				this.containerNode.submit();
			}
		}
	}
);

}

if(!dojo._hasResource["dijit.Dialog"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Dialog"] = true;
dojo.provide("dijit.Dialog");











dojo.declare(
	"dijit.DialogUnderlay",
	[dijit._Widget, dijit._Templated],
	{
		// summary: The component that grays out the screen behind the dialog
	
		// Template has two divs; outer div is used for fade-in/fade-out, and also to hold background iframe.
		// Inner div has opacity specified in CSS file.
		templateString: "<div class='dijitDialogUnderlayWrapper' id='${id}_wrapper'><div class='dijitDialogUnderlay ${class}' id='${id}' dojoAttachPoint='node'></div></div>",

		attributeMap: {},

		postCreate: function(){
			// summary: Append the underlay to the body
			dojo.body().appendChild(this.domNode);
			this.bgIframe = new dijit.BackgroundIframe(this.domNode);
		},

		layout: function(){
			// summary: Sets the background to the size of the viewport
			//
			// description:
			//	Sets the background to the size of the viewport (rather than the size
			//	of the document) since we need to cover the whole browser window, even
			//	if the document is only a few lines long.

			var viewport = dijit.getViewport();
			var is = this.node.style,
				os = this.domNode.style;

			os.top = viewport.t + "px";
			os.left = viewport.l + "px";
			is.width = viewport.w + "px";
			is.height = viewport.h + "px";

			// process twice since the scroll bar may have been removed
			// by the previous resizing
			var viewport2 = dijit.getViewport();
			if(viewport.w != viewport2.w){ is.width = viewport2.w + "px"; }
			if(viewport.h != viewport2.h){ is.height = viewport2.h + "px"; }
		},

		show: function(){
			// summary: Show the dialog underlay
			this.domNode.style.display = "block";
			this.layout();
			if(this.bgIframe.iframe){
				this.bgIframe.iframe.style.display = "block";
			}
		},

		hide: function(){
			// summary: hides the dialog underlay
			this.domNode.style.display = "none";
			if(this.bgIframe.iframe){
				this.bgIframe.iframe.style.display = "none";
			}
		},

		uninitialize: function(){
			if(this.bgIframe){
				this.bgIframe.destroy();
			}
		}
	}
);


dojo.declare("dijit._DialogMixin", null,
	{
		attributeMap: dijit._Widget.prototype.attributeMap,

		// execute: Function
		//	User defined function to do stuff when the user hits the submit button
		execute: function(/*Object*/ formContents){},

		// onCancel: Function
		//      Callback when user has canceled dialog, to notify container
		//      (user shouldn't override)
		onCancel: function(){},

		// onExecute: Function
		//	Callback when user is about to execute dialog, to notify container
		//	(user shouldn't override)
		onExecute: function(){},

		_onSubmit: function(){
			// summary: callback when user hits submit button
			this.onExecute();	// notify container that we are about to execute
			this.execute(this.attr('value'));
		},

		_getFocusItems: function(/*Node*/ dialogNode){
			// find focusable Items each time a dialog is opened
			
			var elems = dijit._getTabNavigable(dojo.byId(dialogNode));
			this._firstFocusItem = elems.lowest || elems.first || dialogNode;
			this._lastFocusItem = elems.last || elems.highest || this._firstFocusItem;
			if(dojo.isMoz && this._firstFocusItem.tagName.toLowerCase() == "input" && dojo.attr(this._firstFocusItem, "type").toLowerCase() == "file"){
					//FF doesn't behave well when first element is input type=file, set first focusable to dialog container
					dojo.attr(dialogNode, "tabindex", "0");
					this._firstFocusItem = dialogNode;
			}
		}
	}
);

dojo.declare(
	"dijit.Dialog",
	[dijit.layout.ContentPane, dijit._Templated, dijit.form._FormMixin, dijit._DialogMixin],
	{
		// summary: A modal dialog Widget
		//
		// description:
		//	Pops up a modal dialog window, blocking access to the screen
		//	and also graying out the screen Dialog is extended from
		//	ContentPane so it supports all the same parameters (href, etc.)
		//
		// example:
		// |	<div dojoType="dijit.Dialog" href="test.html"></div>
		//
		// example:
		// |	var foo = new dijit.Dialog({ title: "test dialog", content: "test content" };
		// |	dojo.body().appendChild(foo.domNode);
		// |	foo.startup();
		
		templateString: null,
		templateString:"<div class=\"dijitDialog\" tabindex=\"-1\" waiRole=\"dialog\" waiState=\"labelledby-${id}_title\">\n\t<div dojoAttachPoint=\"titleBar\" class=\"dijitDialogTitleBar\">\n\t<span dojoAttachPoint=\"titleNode\" class=\"dijitDialogTitle\" id=\"${id}_title\"></span>\n\t<span dojoAttachPoint=\"closeButtonNode\" class=\"dijitDialogCloseIcon\" dojoAttachEvent=\"onclick: onCancel\" title=\"${buttonCancel}\">\n\t\t<span dojoAttachPoint=\"closeText\" class=\"closeText\" title=\"${buttonCancel}\">x</span>\n\t</span>\n\t</div>\n\t\t<div dojoAttachPoint=\"containerNode\" class=\"dijitDialogPaneContent\"></div>\n</div>\n",
		attributeMap: dojo.mixin(dojo.clone(dijit._Widget.prototype.attributeMap), {
			title: [{node: "titleNode", type: "innerHTML"}, {node: "titleBar", type: "attribute"}]
		}),

		// open: Boolean
		//		is True or False depending on state of dialog
		open: false,

		// duration: Integer
		//		The time in milliseconds it takes the dialog to fade in and out
		duration: dijit.defaultDuration,

		// refocus: Boolean
		// 		A Toggle to modify the default focus behavior of a Dialog, which
		// 		is to re-focus the element which had focus before being opened.
		//		False will disable refocusing. Default: true
		refocus: true,
		
		// autofocus: Boolean
		// 		A Toggle to modify the default focus behavior of a Dialog, which
		// 		is to focus on the first dialog element after opening the dialog.
		//		False will disable autofocusing. Default: true
		autofocus: true,

		// _firstFocusItem: DomNode
		//		The pointer to the first focusable node in the dialog
		_firstFocusItem:null,
		
		// _lastFocusItem: DomNode
		//		The pointer to which node has focus prior to our dialog
		_lastFocusItem:null,

		// doLayout: Boolean
		//		Don't change this parameter from the default value.
		//		This ContentPane parameter doesn't make sense for Dialog, since Dialog
		//		is never a child of a layout container, nor can you specify the size of
		//		Dialog in order to control the size of an inner widget. 
		doLayout: false,

		// draggable: Boolean
		//		Toggles the moveable aspect of the Dialog. If true, Dialog
		//		can be moved by it's title. If false it will remain centered
		//		in the viewport.
		draggable: true,

		postMixInProperties: function(){
			var _nlsResources = dojo.i18n.getLocalization("dijit", "common");
			dojo.mixin(this, _nlsResources);
			this.inherited(arguments);
		},

		postCreate: function(){
			var s = this.domNode.style;
			s.visibility = "hidden";
			s.position = "absolute";
			s.display = "";
			s.top = "-9999px";
			dojo.body().appendChild(this.domNode);

			this.inherited(arguments);

			this.connect(this, "onExecute", "hide");
			this.connect(this, "onCancel", "hide");
			this._modalconnects = [];
		},

		onLoad: function(){
			// summary: when href is specified we need to reposition the dialog after the data is loaded
			this._position();
			this.inherited(arguments);
		},

		_endDrag: function(e){
			// summary: Called after dragging the Dialog. Calculates the relative offset
			//		of the Dialog in relation to the viewport. 
			if(e && e.node && e.node === this.domNode){
				var vp = dijit.getViewport(); 
				var p = e._leftTop || dojo.coords(e.node,true);
				this._relativePosition = {
					t: p.t - vp.t,
					l: p.l - vp.l
				}			
			}
		},
		
		_setup: function(){
			// summary: 
			//		stuff we need to do before showing the Dialog for the first
			//		time (but we defer it until right beforehand, for
			//		performance reasons)

			var node = this.domNode;

			if(this.titleBar && this.draggable){
				this._moveable = (dojo.isIE == 6) ?
					new dojo.dnd.TimedMoveable(node, { handle: this.titleBar }) :	// prevent overload, see #5285
					new dojo.dnd.Moveable(node, { handle: this.titleBar, timeout: 0 });
				dojo.subscribe("/dnd/move/stop",this,"_endDrag");
			}else{
				dojo.addClass(node,"dijitDialogFixed"); 
			}

			this._underlay = new dijit.DialogUnderlay({
				id: this.id+"_underlay",
				"class": dojo.map(this["class"].split(/\s/), function(s){ return s+"_underlay"; }).join(" ")
			});

			var underlay = this._underlay;

			this._fadeIn = dojo.fadeIn({
				node: node,
				duration: this.duration,
				onBegin: dojo.hitch(underlay, "show")
			 });

			this._fadeOut = dojo.fadeOut({
				node: node,
				duration: this.duration,
				onEnd: function(){
					node.style.visibility="hidden";
					node.style.top = "-9999px";
					underlay.hide();
				}
			 });
		},

		uninitialize: function(){
			if(this._fadeIn && this._fadeIn.status() == "playing"){
				this._fadeIn.stop();
			}
			if(this._fadeOut && this._fadeOut.status() == "playing"){
				this._fadeOut.stop();
			}
			if(this._underlay){
				this._underlay.destroy();
			}
			if(this._moveable){
				this._moveable.destroy();
			}
		},

		_size: function(){
			// summary:
			// 		Make sure the dialog is small enough to fit in viewport.

			var mb = dojo.marginBox(this.domNode);
			var viewport = dijit.getViewport();
			if(mb.w >= viewport.w || mb.h >= viewport.h){
				dojo.style(this.containerNode, {
					width: Math.min(mb.w, Math.floor(viewport.w * 0.75))+"px",
					height: Math.min(mb.h, Math.floor(viewport.h * 0.75))+"px",
					overflow: "auto",
					position: "relative"	// workaround IE bug moving scrollbar or dragging dialog
				});
			}
		},

		_position: function(){
			// summary: Position modal dialog in the viewport. If no relative offset
			//		in the viewport has been determined (by dragging, for instance),
			//		center the node. Otherwise, use the Dialog's stored relative offset,
			//		and position the node to top: left: values based on the viewport.
			if(!dojo.hasClass(dojo.body(),"dojoMove")){
				
				var node = this.domNode;
				var viewport = dijit.getViewport();
				var p = this._relativePosition;
				var mb = p ? null : dojo.marginBox(node);
				dojo.style(node,{
					left: Math.floor(viewport.l + (p ? p.l : (viewport.w - mb.w) / 2)) + "px",
					top: Math.floor(viewport.t + (p ? p.t : (viewport.h - mb.h) / 2)) + "px"
				});
			}

		},

		_onKey: function(/*Event*/ evt){
			// summary: handles the keyboard events for accessibility reasons
			if(evt.charOrCode){
				var dk = dojo.keys;
				var node = evt.target;
				if (evt.charOrCode === dk.TAB){
					this._getFocusItems(this.domNode);
				}
				var singleFocusItem = (this._firstFocusItem == this._lastFocusItem);
				// see if we are shift-tabbing from first focusable item on dialog
				if(node == this._firstFocusItem && evt.shiftKey && evt.charOrCode === dk.TAB){
					if(!singleFocusItem){
						dijit.focus(this._lastFocusItem); // send focus to last item in dialog
					}
					dojo.stopEvent(evt);
				}else if(node == this._lastFocusItem && evt.charOrCode === dk.TAB && !evt.shiftKey){
					if (!singleFocusItem){
						dijit.focus(this._firstFocusItem); // send focus to first item in dialog
					}
					dojo.stopEvent(evt);
				}else{
					// see if the key is for the dialog
					while(node){
						if(node == this.domNode){
							if(evt.charOrCode == dk.ESCAPE){
								this.onCancel(); 
							}else{
								return; // just let it go
							}
						}
						node = node.parentNode;
					}
					// this key is for the disabled document window
					if(evt.charOrCode !== dk.TAB){ // allow tabbing into the dialog for a11y
						dojo.stopEvent(evt);
					// opera won't tab to a div
					}else if(!dojo.isOpera){
						try{
							this._firstFocusItem.focus();
						}catch(e){ /*squelch*/ }
					}
				}
			}
		},

		show: function(){
			// summary: display the dialog

			if(this.open){ return; }
			
			// first time we show the dialog, there's some initialization stuff to do			
			if(!this._alreadyInitialized){
				this._setup();
				this._alreadyInitialized=true;
			}

			if(this._fadeOut.status() == "playing"){
				this._fadeOut.stop();
			}

			this._modalconnects.push(dojo.connect(window, "onscroll", this, "layout"));
			this._modalconnects.push(dojo.connect(window, "onresize", this, "layout"));
			this._modalconnects.push(dojo.connect(dojo.doc.documentElement, "onkeypress", this, "_onKey"));

			dojo.style(this.domNode, {
				opacity:0,
				visibility:""
			});
			
			this.open = true;
			this._loadCheck(); // lazy load trigger

			this._size();
			this._position();

			this._fadeIn.play();

			this._savedFocus = dijit.getFocus(this);

			if(this.autofocus){
				// find focusable Items each time dialog is shown since if dialog contains a widget the 
				// first focusable items can change
				this._getFocusItems(this.domNode);
	
				// set timeout to allow the browser to render dialog
				setTimeout(dojo.hitch(dijit,"focus",this._firstFocusItem), 50);
			}
		},

		hide: function(){
			// summary: Hide the dialog

			// if we haven't been initialized yet then we aren't showing and we can just return		
			if(!this._alreadyInitialized){
				return;
			}

			if(this._fadeIn.status() == "playing"){
				this._fadeIn.stop();
			}
			this._fadeOut.play();

			if (this._scrollConnected){
				this._scrollConnected = false;
			}
			dojo.forEach(this._modalconnects, dojo.disconnect);
			this._modalconnects = [];
			if(this.refocus){
				this.connect(this._fadeOut,"onEnd",dojo.hitch(dijit,"focus",this._savedFocus));
			}
			if(this._relativePosition){
				delete this._relativePosition;	
			}
			this.open = false;
		},

		layout: function() {
			// summary: Position the Dialog and the underlay
			if(this.domNode.style.visibility != "hidden"){
				this._underlay.layout();
				this._position(); 
			}
		},
		
		destroy: function(){
			dojo.forEach(this._modalconnects, dojo.disconnect);
			if(this.refocus && this.open){
				setTimeout(dojo.hitch(dijit,"focus",this._savedFocus), 25);
			}
			this.inherited(arguments);			
		}
	}
);

dojo.declare(
	"dijit.TooltipDialog",
	[dijit.layout.ContentPane, dijit._Templated, dijit.form._FormMixin, dijit._DialogMixin],
	{
		// summary:
		//		Pops up a dialog that appears like a Tooltip
		//
		// title: String
		// 		Description of tooltip dialog (required for a11Y)
		title: "",

		// doLayout: Boolean
		//		Don't change this parameter from the default value.
		//		This ContentPane parameter doesn't make sense for TooltipDialog, since TooltipDialog
		//		is never a child of a layout container, nor can you specify the size of
		//		TooltipDialog in order to control the size of an inner widget. 
		doLayout: false,

		// autofocus: Boolean
		// 		A Toggle to modify the default focus behavior of a Dialog, which
		// 		is to focus on the first dialog element after opening the dialog.
		//		False will disable autofocusing. Default: true
		autofocus: true,

		"class": "dijitTooltipDialog",

		// _firstFocusItem: DomNode
		//		The pointer to the first focusable node in the dialog
		_firstFocusItem:null,
		
		// _lastFocusItem: DomNode
		//		The domNode that had focus before we took it.
		_lastFocusItem: null,

		templateString: null,
		templateString:"<div waiRole=\"presentation\">\n\t<div class=\"dijitTooltipContainer\" waiRole=\"presentation\">\n\t\t<div class =\"dijitTooltipContents dijitTooltipFocusNode\" dojoAttachPoint=\"containerNode\" tabindex=\"-1\" waiRole=\"dialog\"></div>\n\t</div>\n\t<div class=\"dijitTooltipConnector\" waiRole=\"presentation\"></div>\n</div>\n",

		postCreate: function(){
			this.inherited(arguments);
			this.connect(this.containerNode, "onkeypress", "_onKey");
			this.containerNode.title = this.title;
		},

		orient: function(/*DomNode*/ node, /*String*/ aroundCorner, /*String*/ corner){
			// summary: configure widget to be displayed in given position relative to the button
			this.domNode.className = this["class"] +" dijitTooltipAB"+(corner.charAt(1)=='L'?"Left":"Right")+" dijitTooltip"+(corner.charAt(0)=='T' ? "Below" : "Above");
		},

		onOpen: function(/*Object*/ pos){
			// summary: called when dialog is displayed
		
			this.orient(this.domNode,pos.aroundCorner, pos.corner);
			this._loadCheck(); // lazy load trigger
			
			if(this.autofocus){
				this._getFocusItems(this.containerNode);
				dijit.focus(this._firstFocusItem);
			}
		},
		
		_onKey: function(/*Event*/ evt){
			// summary: keep keyboard focus in dialog; close dialog on escape key
			var node = evt.target;
			var dk = dojo.keys;
			if (evt.charOrCode === dk.TAB){
				this._getFocusItems(this.containerNode);
			}
			var singleFocusItem = (this._firstFocusItem == this._lastFocusItem);
			if(evt.charOrCode == dk.ESCAPE){
				this.onCancel();
				dojo.stopEvent(evt);
			}else if(node == this._firstFocusItem && evt.shiftKey && evt.charOrCode === dk.TAB){
				if(!singleFocusItem){
					dijit.focus(this._lastFocusItem); // send focus to last item in dialog
				}
				dojo.stopEvent(evt);
			}else if(node == this._lastFocusItem && evt.charOrCode === dk.TAB && !evt.shiftKey){
				if(!singleFocusItem){
					dijit.focus(this._firstFocusItem); // send focus to first item in dialog
				}
				dojo.stopEvent(evt);
			}else if(evt.charOrCode === dk.TAB){
				// we want the browser's default tab handling to move focus
				// but we don't want the tab to propagate upwards
				evt.stopPropagation();
			}
		}
	}	
);


}

if(!dojo._hasResource["dijit._editor.selection"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.selection"] = true;
dojo.provide("dijit._editor.selection");

// FIXME:
//		all of these methods branch internally for IE. This is probably
//		sub-optimal in terms of runtime performance. We should investigate the
//		size difference for differentiating at definition time.

dojo.mixin(dijit._editor.selection, {
	getType: function(){
		// summary: Get the selection type (like dojo.doc.select.type in IE).
		if(dojo.doc.selection){ //IE
			return dojo.doc.selection.type.toLowerCase();
		}else{
			var stype = "text";

			// Check if the actual selection is a CONTROL (IMG, TABLE, HR, etc...).
			var oSel;
			try{
				oSel = dojo.global.getSelection();
			}catch(e){ /*squelch*/ }

			if(oSel && oSel.rangeCount==1){
				var oRange = oSel.getRangeAt(0);
				if(	(oRange.startContainer == oRange.endContainer) &&
					((oRange.endOffset - oRange.startOffset) == 1) &&
					(oRange.startContainer.nodeType != 3 /* text node*/)
				){
					stype = "control";
				}
			}
			return stype;
		}
	},

	getSelectedText: function(){
		// summary:
		//		Return the text (no html tags) included in the current selection or null if no text is selected
		if(dojo.doc.selection){ //IE
			if(dijit._editor.selection.getType() == 'control'){
				return null;
			}
			return dojo.doc.selection.createRange().text;
		}else{
			var selection = dojo.global.getSelection();
			if(selection){
				return selection.toString();
			}
		}
		return ''
	},

	getSelectedHtml: function(){
		// summary:
		//		Return the html of the current selection or null if unavailable
		if(dojo.doc.selection){ //IE
			if(dijit._editor.selection.getType() == 'control'){
				return null;
			}
			return dojo.doc.selection.createRange().htmlText;
		}else{
			var selection = dojo.global.getSelection();
			if(selection && selection.rangeCount){
				var frag = selection.getRangeAt(0).cloneContents();
				var div = dojo.doc.createElement("div");
				div.appendChild(frag);
				return div.innerHTML;
			}
			return null;
		}
	},

	getSelectedElement: function(){
		// summary:
		//		Retrieves the selected element (if any), just in the case that
		//		a single element (object like and image or a table) is
		//		selected.
		if(dijit._editor.selection.getType() == "control"){
			if(dojo.doc.selection){ //IE
				var range = dojo.doc.selection.createRange();
				if(range && range.item){
					return dojo.doc.selection.createRange().item(0);
				}
			}else{
				var selection = dojo.global.getSelection();
				return selection.anchorNode.childNodes[ selection.anchorOffset ];
			}
		}
		return null;
	},

	getParentElement: function(){
		// summary:
		//		Get the parent element of the current selection
		if(dijit._editor.selection.getType() == "control"){
			var p = this.getSelectedElement();
			if(p){ return p.parentNode; }
		}else{
			if(dojo.doc.selection){ //IE
				var r=dojo.doc.selection.createRange();
				r.collapse(true);
				return r.parentElement();
			}else{
				var selection = dojo.global.getSelection();
				if(selection){
					var node = selection.anchorNode;

					while(node && (node.nodeType != 1)){ // not an element
						node = node.parentNode;
					}

					return node;
				}
			}
		}
		return null;
	},

	hasAncestorElement: function(/*String*/tagName /* ... */){
		// summary:
		// 		Check whether current selection has a  parent element which is
		// 		of type tagName (or one of the other specified tagName)
		return this.getAncestorElement.apply(this, arguments) != null;
	},

	getAncestorElement: function(/*String*/tagName /* ... */){
		// summary:
		//		Return the parent element of the current selection which is of
		//		type tagName (or one of the other specified tagName)

		var node = this.getSelectedElement() || this.getParentElement();
		return this.getParentOfType(node, arguments);
	},

	isTag: function(/*DomNode*/node, /*Array*/tags){
		if(node && node.tagName){
			var _nlc = node.tagName.toLowerCase();
			for(var i=0; i<tags.length; i++){
				var _tlc = String(tags[i]).toLowerCase();
				if(_nlc == _tlc){
					return _tlc;
				}
			}
		}
		return "";
	},

	getParentOfType: function(/*DomNode*/node, /*Array*/tags){
		while(node){
			if(this.isTag(node, tags).length){
				return node;
			}
			node = node.parentNode;
		}
		return null;
	},

	collapse: function(/*Boolean*/beginning) {
		// summary: clear current selection
	  if(window['getSelection']){
	          var selection = dojo.global.getSelection();
	          if(selection.removeAllRanges){ // Mozilla
	                  if(beginning){
	                          selection.collapseToStart();
	                  }else{
	                          selection.collapseToEnd();
	                  }
	          }else{ // Safari
	                  // pulled from WebCore/ecma/kjs_window.cpp, line 2536
	                   selection.collapse(beginning);
	          }
	  }else if(dojo.doc.selection){ // IE
	          var range = dojo.doc.selection.createRange();
	          range.collapse(beginning);
	          range.select();
	  }
	},

	remove: function(){
		// summary: delete current selection
		var _s = dojo.doc.selection;
		if(_s){ //IE
			if(_s.type.toLowerCase() != "none"){
				_s.clear();
			}
			return _s;
		}else{
			_s = dojo.global.getSelection();
			_s.deleteFromDocument();
			return _s;
		}
	},

	selectElementChildren: function(/*DomNode*/element,/*Boolean?*/nochangefocus){
		// summary:
		//		clear previous selection and select the content of the node
		//		(excluding the node itself)
		var _window = dojo.global;
		var _document = dojo.doc;
		element = dojo.byId(element);
		if(_document.selection && dojo.body().createTextRange){ // IE
			var range = element.ownerDocument.body.createTextRange();
			range.moveToElementText(element);
			if(!nochangefocus){
				try{
					range.select(); // IE throws an exception here if the widget is hidden.  See #5439
				}catch(e){ /* squelch */}
			}
		}else if(_window.getSelection){
			var selection = _window.getSelection();
			if(selection.setBaseAndExtent){ // Safari
				selection.setBaseAndExtent(element, 0, element, element.innerText.length - 1);
			}else if(selection.selectAllChildren){ // Mozilla
				selection.selectAllChildren(element);
			}
		}
	},

	selectElement: function(/*DomNode*/element,/*Boolean?*/nochangefocus){
		// summary:
		//		clear previous selection and select element (including all its children)
		var range, _document = dojo.doc;
		element = dojo.byId(element);
		if(_document.selection && dojo.body().createTextRange){ // IE
			try{
				range = dojo.body().createControlRange();
				range.addElement(element);
				if(!nochangefocus){
					range.select();
				}
			}catch(e){
				this.selectElementChildren(element,nochangefocus);
			}
		}else if(dojo.global.getSelection){
			var selection = dojo.global.getSelection();
			// FIXME: does this work on Safari?
			if(selection.removeAllRanges){ // Mozilla
				range = _document.createRange();
				range.selectNode(element);
				selection.removeAllRanges();
				selection.addRange(range);
			}
		}
	}
});

}

if(!dojo._hasResource["dijit._editor.range"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.range"] = true;
dojo.provide("dijit._editor.range");

dijit.range={};

dijit.range.getIndex=function(/*DomNode*/node, /*DomNode*/parent){
//	dojo.profile.start("dijit.range.getIndex");
	var ret=[], retR=[];
	var stop = parent;
	var onode = node;

	var pnode, n;
	while(node != stop){
		var i = 0;
		pnode = node.parentNode;
		while((n=pnode.childNodes[i++])){
			if(n===node){
				--i;
				break;
			}
		}
		if(i>=pnode.childNodes.length){
			dojo.debug("Error finding index of a node in dijit.range.getIndex");
		}
		ret.unshift(i);
		retR.unshift(i-pnode.childNodes.length);
		node = pnode;
	}

	//normalized() can not be called so often to prevent
	//invalidating selection/range, so we have to detect
	//here that any text nodes in a row
	if(ret.length>0 && onode.nodeType==3){
		n = onode.previousSibling;
		while(n && n.nodeType==3){
			ret[ret.length-1]--;
			n = n.previousSibling;
		}
		n = onode.nextSibling;
		while(n && n.nodeType==3){
			retR[retR.length-1]++;
			n = n.nextSibling;
		}
	}
//	dojo.profile.end("dijit.range.getIndex");
	return {o: ret, r:retR};
}

dijit.range.getNode = function(/*Array*/index, /*DomNode*/parent){
	if(!dojo.isArray(index) || index.length==0){
		return parent;
	}
	var node = parent;
//	if(!node)debugger
	dojo.every(index, function(i){
		if(i>=0&&i< node.childNodes.length){
			node = node.childNodes[i];
		}else{
			node = null;
			
			return false; //terminate dojo.every
		}
		return true; //carry on the every loop
	});

	return node;
}

dijit.range.getCommonAncestor = function(n1,n2){
	var getAncestors = function(n){
		var as=[];
		while(n){
			as.unshift(n);
			if(n.nodeName!='BODY'){
				n = n.parentNode;
			}else{
				break;
			}
		}
		return as;
	};
	var n1as = getAncestors(n1);
	var n2as = getAncestors(n2);

	var m = Math.min(n1as.length,n2as.length);
	var com = n1as[0]; //at least, one element should be in the array: the root (BODY by default)
	for(var i=1;i<m;i++){
		if(n1as[i]===n2as[i]){
			com = n1as[i]
		}else{
			break;
		}
	}
	return com;
}

dijit.range.getAncestor = function(/*DomNode*/node, /*RegEx?*/regex, /*DomNode?*/root){
	root = root || node.ownerDocument.body;
	while(node && node !== root){
		var name = node.nodeName.toUpperCase() ;
		if(regex.test(name)){
			return node;
		}

		node = node.parentNode;
	}
	return null;
}

dijit.range.BlockTagNames = /^(?:P|DIV|H1|H2|H3|H4|H5|H6|ADDRESS|PRE|OL|UL|LI|DT|DE)$/;
dijit.range.getBlockAncestor = function(/*DomNode*/node, /*RegEx?*/regex, /*DomNode?*/root){
	root = root || node.ownerDocument.body;
	regex = regex || dijit.range.BlockTagNames;
	var block=null, blockContainer;
	while(node && node !== root){
		var name = node.nodeName.toUpperCase() ;
		if(!block && regex.test(name)){
			block = node;
		}
		if(!blockContainer && (/^(?:BODY|TD|TH|CAPTION)$/).test(name)){
			blockContainer = node;
		}

		node = node.parentNode;
	}
	return {blockNode:block, blockContainer:blockContainer || node.ownerDocument.body};
}

dijit.range.atBeginningOfContainer = function(/*DomNode*/container, /*DomNode*/node, /*Int*/offset){
	var atBeginning = false;
	var offsetAtBeginning = (offset == 0);
	if(!offsetAtBeginning && node.nodeType==3){ //if this is a text node, check whether the left part is all space
		if(dojo.trim(node.nodeValue.substr(0,offset))==0){
			offsetAtBeginning = true;
		}
	}
	if(offsetAtBeginning){
		var cnode = node;
		atBeginning = true;
		while(cnode && cnode !== container){
			if(cnode.previousSibling){
				atBeginning = false;
				break;
			}
			cnode = cnode.parentNode;
		}
	}
	return atBeginning;
}

dijit.range.atEndOfContainer = function(/*DomNode*/container, /*DomNode*/node, /*Int*/offset){
	var atEnd = false;
	var offsetAtEnd = (offset == (node.length || node.childNodes.length));
	if(!offsetAtEnd && node.nodeType==3){ //if this is a text node, check whether the right part is all space
		if(dojo.trim(node.nodeValue.substr(offset))==0){
			offsetAtEnd = true;
		}
	}
	if(offsetAtEnd){
		var cnode = node;
		atEnd = true;
		while(cnode && cnode !== container){
			if(cnode.nextSibling){
				atEnd = false;
				break;
			}
			cnode = cnode.parentNode;
		}
	}
	return atEnd;
}

dijit.range.adjacentNoneTextNode=function(startnode, next){
	var node = startnode;
	var len = (0-startnode.length) || 0;
	var prop = next?'nextSibling':'previousSibling';
	while(node){
		if(node.nodeType!=3){
			break;
		}
		len += node.length
		node = node[prop];
	}
	return [node,len];
}

dijit.range._w3c = Boolean(window['getSelection']);
dijit.range.create = function(){
	if(dijit.range._w3c){
		return dojo.doc.createRange();
	}else{//IE
		return new dijit.range.W3CRange;
	}
}

dijit.range.getSelection = function(win, /*Boolean?*/ignoreUpdate){
	if(dijit.range._w3c){
		return win.getSelection();
	}else{//IE
		var s = new dijit.range.ie.selection(win);
		if(!ignoreUpdate){
			s._getCurrentSelection();
		}
		return s;
	}
}

if(!dijit.range._w3c){
	dijit.range.ie={
		cachedSelection: {},
		selection: function(win){
			this._ranges = [];
			this.addRange = function(r, /*boolean*/internal){
				this._ranges.push(r);
				if(!internal){
					r._select();
				}
				this.rangeCount = this._ranges.length;
			};
			this.removeAllRanges = function(){
				//don't detach, the range may be used later
//				for(var i=0;i<this._ranges.length;i++){
//					this._ranges[i].detach();
//				}
				this._ranges = [];
				this.rangeCount = 0;
			};
			var _initCurrentRange = function(){
				var r = win.document.selection.createRange();
				var type=win.document.selection.type.toUpperCase();
				if(type == "CONTROL"){
					//TODO: multiple range selection(?)
					return new dijit.range.W3CRange(dijit.range.ie.decomposeControlRange(r));
				}else{
					return new dijit.range.W3CRange(dijit.range.ie.decomposeTextRange(r));
				}
			};
			this.getRangeAt = function(i){
				return this._ranges[i];
			};
			this._getCurrentSelection = function(){
				this.removeAllRanges();
				var r=_initCurrentRange();
				if(r){
					this.addRange(r, true);
				}
			};
		},
		decomposeControlRange: function(range){
			var firstnode = range.item(0), lastnode = range.item(range.length-1)
			var startContainer = firstnode.parentNode, endContainer = lastnode.parentNode;
			var startOffset = dijit.range.getIndex(firstnode, startContainer).o;
			var endOffset = dijit.range.getIndex(lastnode, endContainer).o+1;
			return [[startContainer, startOffset],[endContainer, endOffset]];
		},
		getEndPoint: function(range, end){
			var atmrange = range.duplicate();
			atmrange.collapse(!end);
			var cmpstr = 'EndTo' + (end?'End':'Start');
			var parentNode = atmrange.parentElement();

			var startnode, startOffset, lastNode;
			if(parentNode.childNodes.length>0){
				dojo.every(parentNode.childNodes, function(node,i){
					var calOffset;
					if(node.nodeType != 3){
						atmrange.moveToElementText(node);

						if(atmrange.compareEndPoints(cmpstr,range) > 0){
							startnode = node.previousSibling;
							if(lastNode && lastNode.nodeType == 3){
								//where share we put the start? in the text node or after?
								startnode = lastNode;
								calOffset = true;
							}else{
								startnode = parentNode;
								startOffset = i;
								return false;
							}
						}else{
							if(i==parentNode.childNodes.length-1){
								startnode = parentNode;
								startOffset = parentNode.childNodes.length;
								return false;
							}
						}
					}else{
						if(i==parentNode.childNodes.length-1){//at the end of this node
							startnode = node;
							calOffset = true;
						}
					}
		//			try{
						if(calOffset && startnode){
							var prevnode = dijit.range.adjacentNoneTextNode(startnode)[0];
							if(prevnode){
								startnode = prevnode.nextSibling;
							}else{
								startnode = parentNode.firstChild; //firstChild must be a text node
							}
							var prevnodeobj = dijit.range.adjacentNoneTextNode(startnode);
							prevnode = prevnodeobj[0];
							var lenoffset = prevnodeobj[1];
							if(prevnode){
								atmrange.moveToElementText(prevnode);
								atmrange.collapse(false);
							}else{
								atmrange.moveToElementText(parentNode);
							}
							atmrange.setEndPoint(cmpstr, range);
							startOffset = atmrange.text.length-lenoffset;

							return false;
						}
		//			}catch(e){ debugger }
					lastNode = node;
					return true;
				});
			}else{
				startnode = parentNode;
				startOffset = 0;
			}

			//if at the end of startnode and we are dealing with start container, then
			//move the startnode to nextSibling if it is a text node
			//TODO: do this for end container?
			if(!end && startnode.nodeType!=3 && startOffset == startnode.childNodes.length){
				if(startnode.nextSibling && startnode.nextSibling.nodeType==3){
					startnode = startnode.nextSibling;
					startOffset = 0;
				}
			}
			return [startnode, startOffset];
		},
		setEndPoint: function(range, container, offset){
			//text node
			var atmrange = range.duplicate(), node, len;
			if(container.nodeType!=3){ //normal node
				atmrange.moveToElementText(container);
				atmrange.collapse(true);
				if(offset == container.childNodes.length){
					if(offset > 0){
						//a simple atmrange.collapse(false); won't work here:
						//although moveToElementText(node) is supposed to encompass the content of the node,
						//but when collapse to end, it is in fact after the ending tag of node (collapse to start
						//is after the begining tag of node as expected)
						node = container.lastChild;
						len = 0;
						while(node && node.nodeType == 3){
							len += node.length;
							container = node; //pass through
							node = node.previousSibling;
						}
						if(node){
							atmrange.moveToElementText(node);
						}
						atmrange.collapse(false);
						offset = len; //pass through
					}else{ //no childNodes
						atmrange.moveToElementText(container);
						atmrange.collapse(true);
					}
				}else{
					if(offset > 0){
						node = container.childNodes[offset-1];
						if(node.nodeType==3){
							container = node;
							offset = node.length;
							//pass through
						}else{
							atmrange.moveToElementText(node);
							atmrange.collapse(false);
						}
					}
				}
			}
			if(container.nodeType==3){
				var prevnodeobj = dijit.range.adjacentNoneTextNode(container);
				var prevnode = prevnodeobj[0];
				len = prevnodeobj[1];
				if(prevnode){
					atmrange.moveToElementText(prevnode);
					atmrange.collapse(false);
					//if contentEditable is not inherit, the above collapse won't make the end point
					//in the correctly position: it always has a -1 offset, so compensate it
					if(prevnode.contentEditable!='inherit'){
						len++;
					}
				}else{
					atmrange.moveToElementText(container.parentNode);
					atmrange.collapse(true);
				}

				offset += len;
				if(offset>0){
					if(atmrange.move('character',offset) != offset){
						console.error('Error when moving!');
					}
				}
			}

			return atmrange;
		},
		decomposeTextRange: function(range){
			var tmpary = dijit.range.ie.getEndPoint(range);
			var startContainter = tmpary[0], startOffset = tmpary[1];
			var endContainter = tmpary[0], endOffset = tmpary[1];

			if(range.htmlText.length){
				if(range.htmlText == range.text){ //in the same text node
					endOffset = startOffset+range.text.length;
				}else{
					tmpary = dijit.range.ie.getEndPoint(range,true);
					endContainter = tmpary[0], endOffset = tmpary[1];
				}
			}
			return [[startContainter, startOffset],[endContainter, endOffset]];
		},
		setRange: function(range, startContainter,
			startOffset, endContainter, endOffset, collapsed){
			var startrange = dijit.range.ie.setEndPoint(range, startContainter, startOffset);
			range.setEndPoint('StartToStart', startrange);
			var endrange=startrange;
			if(!collapsed){
				endrange = dijit.range.ie.setEndPoint(range, endContainter, endOffset);
			}
			range.setEndPoint('EndToEnd', endrange);

			return range;
		}
	}

dojo.declare("dijit.range.W3CRange",null, {
	constructor: function(){
		if(arguments.length>0){
			this.setStart(arguments[0][0][0],arguments[0][0][1]);
			this.setEnd(arguments[0][1][0],arguments[0][1][1]);
		}else{
			this.commonAncestorContainer = null;
			this.startContainer = null;
			this.startOffset = 0;
			this.endContainer = null;
			this.endOffset = 0;
			this.collapsed = true;
		}
	},
	_simpleSetEndPoint: function(node, range, end){
		var r = (this._body||node.ownerDocument.body).createTextRange();
		if(node.nodeType!=1){
			r.moveToElementText(node.parentNode);
		}else{
			r.moveToElementText(node);
		}
		r.collapse(true);
		range.setEndPoint(end?'EndToEnd':'StartToStart',r);
	},
	_updateInternal: function(){
		if(this.startContainer !== this.endContainer){
			this.commonAncestorContainer = dijit.range.getCommonAncestor(this.startContainer, this.endContainer);
		}else{
			this.commonAncestorContainer = this.startContainer;
		}
		this.collapsed = (this.startContainer === this.endContainer) && (this.startOffset == this.endOffset);
	},
	setStart: function(node, offset){
		offset=parseInt(offset);
		if(this.startContainer === node && this.startOffset == offset){
			return;
		}
		delete this._cachedBookmark;

		this.startContainer = node;
		this.startOffset = offset;
		if(!this.endContainer){
			this.setEnd(node, offset);
		}else{
			this._updateInternal();
		}
	},
	setEnd: function(node, offset){
		offset=parseInt(offset);
		if(this.endContainer === node && this.endOffset == offset){
			return;
		}
		delete this._cachedBookmark;

		this.endContainer = node;
		this.endOffset = offset;
		if(!this.startContainer){
			this.setStart(node, offset);
		}else{
			this._updateInternal();
		}
	},
	setStartAfter: function(node, offset){
		this._setPoint('setStart', node, offset, 1);
	},
	setStartBefore: function(node, offset){
		this._setPoint('setStart', node, offset, 0);
	},
	setEndAfter: function(node, offset){
		this._setPoint('setEnd', node, offset, 1);
	},
	setEndBefore: function(node, offset){
		this._setPoint('setEnd', node, offset, 0);
	},
	_setPoint: function(what, node, offset, ext){
		var index = dijit.range.getIndex(node, node.parentNode).o;
		this[what](node.parentNode, index.pop()+ext);
	},
	_getIERange: function(){
		var r=(this._body||this.endContainer.ownerDocument.body).createTextRange();
		dijit.range.ie.setRange(r, this.startContainer, this.startOffset, this.endContainer, this.endOffset, this.collapsed);
		return r;
	},
	getBookmark: function(body){
		this._getIERange();
		return this._cachedBookmark;
	},
	_select: function(){
		var r = this._getIERange();
		r.select();
	},
	deleteContents: function(){
		var r = this._getIERange();
		r.pasteHTML('');
		this.endContainer = this.startContainer;
		this.endOffset = this.startOffset;
		this.collapsed = true;
	},
	cloneRange: function(){
		var r = new dijit.range.W3CRange([[this.startContainer,this.startOffset],
			[this.endContainer,this.endOffset]]);
		r._body = this._body;
		return r;
	},
	detach: function(){
		this._body = null;
		this.commonAncestorContainer = null;
		this.startContainer = null;
		this.startOffset = 0;
		this.endContainer = null;
		this.endOffset = 0;
		this.collapsed = true;
}
});
} //if(!dijit.range._w3c)

}

if(!dojo._hasResource["dijit._editor.html"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.html"] = true;
dojo.provide("dijit._editor.html");

dijit._editor.escapeXml=function(/*String*/str, /*Boolean?*/noSingleQuotes){
	//summary:
	//		Adds escape sequences for special characters in XML: &<>"'
	//		Optionally skips escapes for single quotes
	str = str.replace(/&/gm, "&amp;").replace(/</gm, "&lt;").replace(/>/gm, "&gt;").replace(/"/gm, "&quot;");
	if(!noSingleQuotes){
		str = str.replace(/'/gm, "&#39;");
	}
	return str; // string
};

dijit._editor.getNodeHtml=function(/* DomNode */node){
	var output;
	switch(node.nodeType){
		case 1: //element node
			output = '<' + node.nodeName.toLowerCase();

			//store the list of attributes and sort it to have the
			//attributes appear in the dictionary order
			var attrarray = [];
			if(dojo.isIE && node.outerHTML){
				var s = node.outerHTML;
				s = s.substr(0, s.indexOf('>'))
					.replace(/(['"])[^"']*\1/g, ''); //to make the following regexp safe
				var reg = /([^\s=]+)=/g;
				var m, key;
				while((m = reg.exec(s))){
					key = m[1];
					if(key.substr(0,3) != '_dj'){
						if(key == 'src' || key == 'href'){
							if(node.getAttribute('_djrealurl')){
								attrarray.push([key,node.getAttribute('_djrealurl')]);
								continue;
							}
						}
						var val;
						switch(key){
							case 'style':
								val = node.style.cssText.toLowerCase();
								break;
							case 'class':
								val = node.className;
								break;
							default:
								val = node.getAttribute(key);
						}
						attrarray.push([key, val.toString()]);
					}
				}
			}else{
				var attr, i = 0;
				while((attr = node.attributes[i++])){
					//ignore all attributes starting with _dj which are
					//internal temporary attributes used by the editor
					var n = attr.name;
					if(n.substr(0,3) != '_dj' /*&&
						(attr.specified == undefined || attr.specified)*/){
						var v = attr.value;
						if(n == 'src' || n == 'href'){
							if(node.getAttribute('_djrealurl')){
								v = node.getAttribute('_djrealurl');
							}
						}
						attrarray.push([n,v]);
					}
				}
			}
			attrarray.sort(function(a,b){
				return a[0]<b[0]?-1:(a[0]==b[0]?0:1);
			});
			var j = 0;
			while((attr = attrarray[j++])){
				output += ' ' + attr[0] + '="' +
					(dojo.isString(attr[1]) ? dijit._editor.escapeXml(attr[1], true) : attr[1]) + '"';
			}
			if(node.childNodes.length){
				output += '>' + dijit._editor.getChildrenHtml(node)+'</'+node.nodeName.toLowerCase()+'>';
			}else{
				output += ' />';
			}
			break;
		case 3: //text
			// FIXME:
			output = dijit._editor.escapeXml(node.nodeValue, true);
			break;
		case 8: //comment
			// FIXME:
			output = '<!--' + dijit._editor.escapeXml(node.nodeValue, true) + '-->';
			break;
		default:
			output = "<!-- Element not recognized - Type: " + node.nodeType + " Name: " + node.nodeName + "-->";
	}
	return output;
};

dijit._editor.getChildrenHtml = function(/* DomNode */dom){
	// summary: Returns the html content of a DomNode and children
	var out = "";
	if(!dom){ return out; }
	var nodes = dom["childNodes"] || dom;
	var node, i = 0;
	while((node = nodes[i++])){
		out += dijit._editor.getNodeHtml(node);
	}
	return out; // String
}

}

if(!dojo._hasResource["dijit._editor.RichText"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.RichText"] = true;
dojo.provide("dijit._editor.RichText");








// used to restore content when user leaves this page then comes back
// but do not try doing dojo.doc.write if we are using xd loading.
// dojo.doc.write will only work if RichText.js is included in the dojo.js
// file. If it is included in dojo.js and you want to allow rich text saving
// for back/forward actions, then set dojo.config.allowXdRichTextSave = true.
if(!dojo.config["useXDomain"] || dojo.config["allowXdRichTextSave"]){
	if(dojo._postLoad){
		(function(){
			var savetextarea = dojo.doc.createElement('textarea');
			savetextarea.id = dijit._scopeName + "._editor.RichText.savedContent";
			var s = savetextarea.style;
			s.display='none';
			s.position='absolute';
			s.top="-100px";
			s.left="-100px";
			s.height="3px";
			s.width="3px";
			dojo.body().appendChild(savetextarea);
		})();
	}else{
		//dojo.body() is not available before onLoad is fired
		try {
			dojo.doc.write('<textarea id="' + dijit._scopeName + '._editor.RichText.savedContent" ' +
				'style="display:none;position:absolute;top:-100px;left:-100px;height:3px;width:3px;overflow:hidden;"></textarea>');
		}catch(e){ }
	}
}

dojo.declare("dijit._editor.RichText", dijit._Widget, {
	constructor: function(params){
		// summary:
		//		dijit._editor.RichText is the core of dijit.Editor, which provides basic
		//		WYSIWYG editing features.
		//
		// description:
		//		dijit._editor.RichText is the core of dijit.Editor, which provides basic
		//		WYSIWYG editing features. It also encapsulates the differences
		//		of different js engines for various browsers.  Do not use this widget
		//		with an HTML &lt;TEXTAREA&gt; tag, since the browser unescapes XML escape characters,
		//		like &lt;.  This can have unexpected behavior and lead to security issues
		//		such as scripting attacks.
		//
		// contentPreFilters: Array
		//		pre content filter function register array.
		//		these filters will be executed before the actual
		//		editing area get the html content
		this.contentPreFilters = [];

		// contentPostFilters: Array
		//		post content filter function register array.
		//		these will be used on the resulting html
		//		from contentDomPostFilters. The resuling
		//		content is the final html (returned by getValue())
		this.contentPostFilters = [];

		// contentDomPreFilters: Array
		//		pre content dom filter function register array.
		//		these filters are applied after the result from
		//		contentPreFilters are set to the editing area
		this.contentDomPreFilters = [];

		// contentDomPostFilters: Array
		//		post content dom filter function register array.
		//		these filters are executed on the editing area dom
		//		the result from these will be passed to contentPostFilters
		this.contentDomPostFilters = [];

		// editingAreaStyleSheets: Array
		//		array to store all the stylesheets applied to the editing area
		this.editingAreaStyleSheets=[];

		this._keyHandlers = {};
		this.contentPreFilters.push(dojo.hitch(this, "_preFixUrlAttributes"));
		if(dojo.isMoz){
			this.contentPreFilters.push(this._fixContentForMoz);
			this.contentPostFilters.push(this._removeMozBogus);
		}
		if(dojo.isSafari){
			this.contentPostFilters.push(this._removeSafariBogus);
		}
		//this.contentDomPostFilters.push(this._postDomFixUrlAttributes);

		this.onLoadDeferred = new dojo.Deferred();
	},

	// inheritWidth: Boolean
	//		whether to inherit the parent's width or simply use 100%
	inheritWidth: false,

	// focusOnLoad: Boolean
	//		whether focusing into this instance of richtext when page onload
	focusOnLoad: false,

	// name: String
	//		If a save name is specified the content is saved and restored when the user
	//		leave this page can come back, or if the editor is not properly closed after
	//		editing has started.
	name: "",

	// styleSheets: String
	//		semicolon (";") separated list of css files for the editing area
	styleSheets: "",

	// _content: String
	//		temporary content storage
	_content: "",

	// height: String
	//		set height to fix the editor at a specific height, with scrolling.
	//		By default, this is 300px. If you want to have the editor always
	//		resizes to accommodate the content, use AlwaysShowToolbar plugin
	//		and set height="". If this editor is used within a layout widget,
	//		set height="100%".
	height: "300px",

	// minHeight: String
	//		The minimum height that the editor should have
	minHeight: "1em",
	
	// isClosed: Boolean
	isClosed: true,

	// isLoaded: Boolean
	isLoaded: false,

	// _SEPARATOR: String
	//		used to concat contents from multiple textareas into a single string
	_SEPARATOR: "@@**%%__RICHTEXTBOUNDRY__%%**@@",

	// onLoadDeferred: dojo.Deferred
	//		deferred which is fired when the editor finishes loading
	onLoadDeferred: null,
	
	// isTabIndent: Boolean
	//		used to allow tab key and shift-tab to indent and outdent rather than navigate
	isTabIndent: false,

	postCreate: function(){
		if("textarea" == this.domNode.tagName.toLowerCase()){
			console.warn("RichText should not be used with the TEXTAREA tag.  See dijit._editor.RichText docs.");
		}
		dojo.publish(dijit._scopeName + "._editor.RichText::init", [this]);
		this.open();
		this.setupDefaultShortcuts();
	},

	setupDefaultShortcuts: function(){
		// summary: add some default key handlers
		// description:
		// 		Overwrite this to setup your own handlers. The default
		// 		implementation does not use Editor commands, but directly
		//		executes the builtin commands within the underlying browser
		//		support.
		var exec = dojo.hitch(this, function(cmd, arg){
			return function(){
				return !this.execCommand(cmd,arg);
			};
		});

		var ctrlKeyHandlers = { 
			b: exec("bold"),
			i: exec("italic"),
			u: exec("underline"),
			a: exec("selectall"),
			s: function(){ this.save(true); },
			m: function(){ this.isTabIndent = !this.isTabIndent; },

			"1": exec("formatblock", "h1"),
			"2": exec("formatblock", "h2"),
			"3": exec("formatblock", "h3"),
			"4": exec("formatblock", "h4"),

			"\\": exec("insertunorderedlist")
		};

		if(!dojo.isIE){
			ctrlKeyHandlers.Z = exec("redo"); //FIXME: undo?
		}

		for(var key in ctrlKeyHandlers){
			this.addKeyHandler(key, true, false, ctrlKeyHandlers[key]);
		}
	},

	// events: Array
	//		 events which should be connected to the underlying editing area
	events: ["onKeyPress", "onKeyDown", "onKeyUp", "onClick"],

	// events: Array
	//		 events which should be connected to the underlying editing
	//		 area, events in this array will be addListener with
	//		 capture=true
	captureEvents: [],

	_editorCommandsLocalized: false,
	_localizeEditorCommands: function(){
		if(this._editorCommandsLocalized){
			return;
		}
		this._editorCommandsLocalized = true;

		//in IE, names for blockformat is locale dependent, so we cache the values here

		//if the normal way fails, we try the hard way to get the list

		//do not use _cacheLocalBlockFormatNames here, as it will
		//trigger security warning in IE7

		//put p after div, so if IE returns Normal, we show it as paragraph
		//We can distinguish p and div if IE returns Normal, however, in order to detect that,
		//we have to call this.document.selection.createRange().parentElement() or such, which
		//could slow things down. Leave it as it is for now
		var formats = ['div', 'p', 'pre', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'ul', 'address'];
		var localhtml = "", format, i=0;
		while((format=formats[i++])){
			//append a <br> after each element to separate the elements more reliably
			if(format.charAt(1) != 'l'){
				localhtml += "<"+format+"><span>content</span></"+format+"><br/>";
			}else{
				localhtml += "<"+format+"><li>content</li></"+format+"><br/>";
			}
		}
		//queryCommandValue returns empty if we hide editNode, so move it out of screen temporary
		var div = dojo.doc.createElement('div');
		dojo.style(div, {
			position: "absolute",
			left: "-2000px",
			top: "-2000px"
		});
		dojo.doc.body.appendChild(div);
		div.innerHTML = localhtml;
		var node = div.firstChild;
		while(node){
			dijit._editor.selection.selectElement(node.firstChild);
			dojo.withGlobal(this.window, "selectElement", dijit._editor.selection, [node.firstChild]);
			var nativename = node.tagName.toLowerCase();
			this._local2NativeFormatNames[nativename] = document.queryCommandValue("formatblock");
			//this.queryCommandValue("formatblock");
			this._native2LocalFormatNames[this._local2NativeFormatNames[nativename]] = nativename;
			node = node.nextSibling.nextSibling;
		}
		dojo.body().removeChild(div);
	},

	open: function(/*DomNode?*/element){
		//	summary:
		//		Transforms the node referenced in this.domNode into a rich text editing
		//		node. 
		//	description:
		//		Sets up the editing area asynchronously. This will result in
		//		the creation and replacement with an <iframe> if
		//		designMode(FF)/contentEditable(IE) is used and stylesheets are
		//		specified, if we're in a browser that doesn't support
		//		contentEditable.
		//
		//		A dojo.Deferred object is created at this.onLoadDeferred, and
		//		users may attach to it to be informed when the rich-text area
		//		initialization is finalized.

		if((!this.onLoadDeferred)||(this.onLoadDeferred.fired >= 0)){
			this.onLoadDeferred = new dojo.Deferred();
		}

		if(!this.isClosed){ this.close(); }
		dojo.publish(dijit._scopeName + "._editor.RichText::open", [ this ]);

		this._content = "";
		if((arguments.length == 1)&&(element["nodeName"])){ // else unchanged
			this.domNode = element; 
		} 

		var dn = this.domNode;

		var html;
		if(	(dn["nodeName"])&&
			(dn.nodeName.toLowerCase() == "textarea")){
			// if we were created from a textarea, then we need to create a
			// new editing harness node.
			var ta = this.textarea = dn;
			this.name = ta.name;
			html = this._preFilterContent(ta.value);
			dn = this.domNode = dojo.doc.createElement("div");
			dn.setAttribute('widgetId', this.id);
			ta.removeAttribute('widgetId');
			dn.cssText = ta.cssText;
			dn.className += " "+ta.className;
			dojo.place(dn, ta, "before");
			var tmpFunc = dojo.hitch(this, function(){
				//some browsers refuse to submit display=none textarea, so
				//move the textarea out of screen instead
				with(ta.style){
					display = "block";
					position = "absolute";
					left = top = "-1000px";

					if(dojo.isIE){ //nasty IE bug: abnormal formatting if overflow is not hidden
						this.__overflow = overflow;
						overflow = "hidden";
					}
				}
			});
			if(dojo.isIE){
				setTimeout(tmpFunc, 10);
			}else{
				tmpFunc();
			}

			// this.domNode.innerHTML = html;

			if(ta.form){
				dojo.connect(ta.form, "onsubmit", this, function(){
					// FIXME: should we be calling close() here instead?
					ta.value = this.getValue();
				});
			}
		}else{
			html = this._preFilterContent(dijit._editor.getChildrenHtml(dn));
			dn.innerHTML = "";
		}
		if(html == ""){ html = "&nbsp;"; }

		var content = dojo.contentBox(dn);
		// var content = dojo.contentBox(this.srcNodeRef);
		this._oldHeight = content.h;
		this._oldWidth = content.w;

		this.savedContent = html;

		// If we're a list item we have to put in a blank line to force the
		// bullet to nicely align at the top of text
		if(	(dn["nodeName"]) &&
			(dn.nodeName == "LI") ){
			dn.innerHTML = " <br>";
		}

		this.editingArea = dn.ownerDocument.createElement("div");
		dn.appendChild(this.editingArea);

		if(this.name != "" && (!dojo.config["useXDomain"] || dojo.config["allowXdRichTextSave"])){
			var saveTextarea = dojo.byId(dijit._scopeName + "._editor.RichText.savedContent");
			if(saveTextarea.value != ""){
				var datas = saveTextarea.value.split(this._SEPARATOR), i=0, dat;
				while((dat=datas[i++])){
					var data = dat.split(":");
					if(data[0] == this.name){
						html = data[1];
						datas.splice(i, 1);
						break;
					}
				}
			}

			// FIXME: need to do something different for Opera/Safari
			this.connect(window, "onbeforeunload", "_saveContent");
			// dojo.connect(window, "onunload", this, "_saveContent");
		}

		this.isClosed = false;

		// Safari's selections go all out of whack if we do it inline,
		// so for now IE is our only hero
		//if(typeof dojo.doc.body.contentEditable != "undefined")
		if(dojo.isIE || dojo.isSafari || dojo.isOpera){ // contentEditable, easy
			var burl = dojo.config["dojoBlankHtmlUrl"] || (dojo.moduleUrl("dojo", "resources/blank.html")+"");
			var ifr = this.editorObject = this.iframe = dojo.doc.createElement('iframe');
			ifr.id = this.id+"_iframe";
			ifr.src = burl;
			ifr.style.border = "none";
			ifr.style.width = "100%";
			ifr.frameBorder = 0;
			// ifr.style.scrolling = this.height ? "auto" : "vertical";
			this.editingArea.appendChild(ifr);
			var h = null; // set later in non-ie6 branch
			var loadFunc = dojo.hitch( this, function(){
				if(h){ dojo.disconnect(h); h = null; }
				this.window = ifr.contentWindow;
				var d = this.document = this.window.document;
				d.open();
				d.write(this._getIframeDocTxt(html));
				d.close();

				if(this._layoutMode){
					// iframe should be 100% height, thus getting it's height from surrounding
					// <div> (which has the correct height set by Editor
					ifr.style.height = "100%";
				}else{
					if(dojo.isIE >= 7){
						if(this.height){
							ifr.style.height = this.height;
						}
						if(this.minHeight){
							ifr.style.minHeight = this.minHeight;
						}
					}else{
						ifr.style.height = this.height ? this.height : this.minHeight;
					}
				}

				if(dojo.isIE){
					this._localizeEditorCommands();
				}

				this.onLoad();
				this.savedContent = this.getValue(true);
			});
			if(dojo.isIE && dojo.isIE <= 7){ // IE 6/7 is a steaming pile...
				var t = setInterval(function(){
					if(ifr.contentWindow.isLoaded){
						clearInterval(t);
						loadFunc();
					}
				}, 100);
			}else{ // blissful sanity!
				h = dojo.connect(
					((dojo.isIE) ? ifr.contentWindow : ifr), "onload", loadFunc
				);
			}
		}else{ // designMode in iframe
			this._drawIframe(html);
			this.savedContent = this.getValue(true);
		}
		
		// TODO: this is a guess at the default line-height, kinda works
		if(dn.nodeName == "LI"){
			dn.lastChild.style.marginTop = "-1.2em";
		}

		if(this.domNode.nodeName == "LI"){ this.domNode.lastChild.style.marginTop = "-1.2em"; }
		dojo.addClass(this.domNode, "RichTextEditable");
	},

	//static cache variables shared among all instance of this class
	_local2NativeFormatNames: {},
	_native2LocalFormatNames: {},
	_localizedIframeTitles: null,

	_getIframeDocTxt: function(/* String */ html){
		var _cs = dojo.getComputedStyle(this.domNode);
		if(dojo.isIE || (!this.height && !dojo.isMoz)){
			html="<div>"+html+"</div>";
		}
		var font = [ _cs.fontWeight, _cs.fontSize, _cs.fontFamily ].join(" ");
		
		// line height is tricky - applying a units value will mess things up.
		// if we can't get a non-units value, bail out.
		var lineHeight = _cs.lineHeight;
		if(lineHeight.indexOf("px") >= 0){
			lineHeight = parseFloat(lineHeight)/parseFloat(_cs.fontSize);
			// 
		}else if(lineHeight.indexOf("em")>=0){
			lineHeight = parseFloat(lineHeight);
		}else{
			lineHeight = "1.0";
		}
		var userStyle = "";
		this.style.replace(/(^|;)(line-|font-?)[^;]+/g, function(match){ userStyle += match.replace(/^;/g,"") + ';' });
		return [
			this.isLeftToRight() ? "<html><head>" : "<html dir='rtl'><head>",
			(dojo.isMoz ? "<title>" + this._localizedIframeTitles.iframeEditTitle + "</title>" : ""),
			"<style>",
			"body,html {",
			"	background:transparent;",
			"	padding: 1em 0 0 0;",
			"	margin: -1em 0 0 0;", // remove extraneous vertical scrollbar on safari and firefox
			"	height: 100%;",
			"}",
			// TODO: left positioning will cause contents to disappear out of view
			//	   if it gets too wide for the visible area
			"body{",
			"	top:0px; left:0px; right:0px;",
			"	font:", font, ";",
				((this.height||dojo.isOpera) ? "" : "position: fixed;"),
			// FIXME: IE 6 won't understand min-height?
			"	min-height:", this.minHeight, ";",
			"	line-height:", lineHeight,
			"}",
			"p{ margin: 1em 0 !important; }",
			(this.height ? // height:auto undoes the height:100%
				"" : "body,html{height:auto;overflow-y:hidden;/*for IE*/} body > div {overflow-x:auto;/*for FF to show vertical scrollbar*/}"
			),
			"li > ul:-moz-first-node, li > ol:-moz-first-node{ padding-top: 1.2em; } ",
			"li{ min-height:1.2em; }",
			"</style>",
			this._applyEditingAreaStyleSheets(),
			"</head><body style='"+userStyle+"'>"+html+"</body></html>"
		].join(""); // String
	},

	_drawIframe: function(/*String*/html){
		// summary:
		//		Draws an iFrame using the existing one if one exists.
		//		Used by Mozilla, Safari, and Opera

		if(!this.iframe){
			var ifr = this.iframe = dojo.doc.createElement("iframe");
			ifr.id=this.id+"_iframe";
			// this.iframe.src = "about:blank";
			// dojo.doc.body.appendChild(this.iframe);
			// 
			// dojo.body().appendChild(this.iframe);
			var ifrs = ifr.style;
			// ifrs.border = "1px solid black";
			ifrs.border = "none";
			ifrs.lineHeight = "0"; // squash line height
			ifrs.verticalAlign = "bottom";
			// ifrs.scrolling = this.height ? "auto" : "vertical";
			this.editorObject = this.iframe;
			// get screen reader text for mozilla here, too
			this._localizedIframeTitles = dojo.i18n.getLocalization("dijit.form", "Textarea");
			// need to find any associated label element and update iframe document title
			var label=dojo.query('label[for="'+this.id+'"]');
			if(label.length){
				this._localizedIframeTitles.iframeEditTitle = label[0].innerHTML + " " + this._localizedIframeTitles.iframeEditTitle;
			}
		}
		// opera likes this to be outside the with block
		//	this.iframe.src = "javascript:void(0)";//dojo.uri.dojoUri("src/widget/templates/richtextframe.html") + ((dojo.doc.domain != currentDomain) ? ("#"+dojo.doc.domain) : "");
		this.iframe.style.width = this.inheritWidth ? this._oldWidth : "100%";

		if(this._layoutMode){
			// iframe should be 100% height, thus getting it's height from surrounding
			// <div> (which has the correct height set by Editor
			this.iframe.style.height = "100%";
		}else{
			if(this.height){
				this.iframe.style.height = this.height;
			}else{
				this.iframe.height = this._oldHeight;
			}
		}

		var tmpContent;
		if(this.textarea){
			tmpContent = this.srcNodeRef;
		}else{
			tmpContent = dojo.doc.createElement('div');
			tmpContent.style.display="none";
			tmpContent.innerHTML = html;
			//append tmpContent to under the current domNode so that the margin
			//calculation below is correct
			this.editingArea.appendChild(tmpContent);
		}

		this.editingArea.appendChild(this.iframe);

		//do we want to show the content before the editing area finish loading here?
		//if external style sheets are used for the editing area, the appearance now
		//and after loading of the editing area won't be the same (and padding/margin
		//calculation above may not be accurate)
		//	tmpContent.style.display = "none";
		//	this.editingArea.appendChild(this.iframe);


		// now we wait for the iframe to load. Janky hack!
		var ifrFunc = dojo.hitch(this, function(){
			if(!this.editNode){
				// Iframe hasn't been loaded yet.
				// First deal w/the document to be available (may have to wait for it)
				if(!this.document){
					try{
						if(this.iframe.contentWindow){
							this.window = this.iframe.contentWindow;
							this.document = this.iframe.contentWindow.document
						}else if(this.iframe.contentDocument){
							// for opera
							// TODO: this method is only being called for FF2; can we remove this?
							this.window = this.iframe.contentDocument.window;
							this.document = this.iframe.contentDocument;
						}
					}catch(e){}
					if(!this.document){
						setTimeout(ifrFunc,50);
						return;
					}

					// note that on Safari lower than 420+, we have to get the iframe
					// by ID in order to get something w/ a contentDocument property
					var contentDoc = this.document;
					contentDoc.open();
					if(dojo.isAIR){
						contentDoc.body.innerHTML = html;
					}else{
						contentDoc.write(this._getIframeDocTxt(html));
					}
					contentDoc.close();
					
					dojo._destroyElement(tmpContent);
				}

				// Wait for body to be available
				// Writing into contentDoc (above) can make <body> temporarily unavailable, may have to delay again
				if(!this.document.body){
					//
					setTimeout(ifrFunc,50);
					return;
				}

				this.onLoad();
			}else{
				// Iframe is already loaded, we are just switching the content
				dojo._destroyElement(tmpContent);
				this.editNode.innerHTML = html;
				this.onDisplayChanged();
			}
			this._preDomFilterContent(this.editNode);
		});

		ifrFunc();
	},

	_applyEditingAreaStyleSheets: function(){
		// summary:
		//		apply the specified css files in styleSheets
		var files = [];
		if(this.styleSheets){
			files = this.styleSheets.split(';');
			this.styleSheets = '';
		}

		//empty this.editingAreaStyleSheets here, as it will be filled in addStyleSheet
		files = files.concat(this.editingAreaStyleSheets);
		this.editingAreaStyleSheets = [];

		var text='', i=0, url;
		while((url=files[i++])){
			var abstring = (new dojo._Url(dojo.global.location, url)).toString();
			this.editingAreaStyleSheets.push(abstring);
			text += '<link rel="stylesheet" type="text/css" href="'+abstring+'"/>'
		}
		return text;
	},

	addStyleSheet: function(/*dojo._Url*/uri){
		// summary:
		//		add an external stylesheet for the editing area
		// uri:	a dojo.uri.Uri pointing to the url of the external css file
		var url=uri.toString();

		//if uri is relative, then convert it to absolute so that it can be resolved correctly in iframe
		if(url.charAt(0) == '.' || (url.charAt(0) != '/' && !uri.host)){
			url = (new dojo._Url(dojo.global.location, url)).toString();
		}

		if(dojo.indexOf(this.editingAreaStyleSheets, url) > -1){
//			
			return;
		}

		this.editingAreaStyleSheets.push(url);
		if(this.document.createStyleSheet){ //IE
			this.document.createStyleSheet(url);
		}else{ //other browser
			var head = this.document.getElementsByTagName("head")[0];
			var stylesheet = this.document.createElement("link");
			with(stylesheet){
				rel="stylesheet";
				type="text/css";
				href=url;
			}
			head.appendChild(stylesheet);
		}
	},

	removeStyleSheet: function(/*dojo._Url*/uri){
		// summary:
		//		remove an external stylesheet for the editing area
		var url=uri.toString();
		//if uri is relative, then convert it to absolute so that it can be resolved correctly in iframe
		if(url.charAt(0) == '.' || (url.charAt(0) != '/' && !uri.host)){
			url = (new dojo._Url(dojo.global.location, url)).toString();
		}
		var index = dojo.indexOf(this.editingAreaStyleSheets, url);
		if(index == -1){
//			
			return;
		}
		delete this.editingAreaStyleSheets[index];
		dojo.withGlobal(this.window,'query', dojo, ['link:[href="'+url+'"]']).orphan()
	},

	disabled: true,
	_mozSettingProps: ['styleWithCSS','insertBrOnReturn'],
	_setDisabledAttr: function(/*Boolean*/ value){
		if(!this.editNode || "_delayedDisabled" in this){ 
			this._delayedDisabled = value;
			return; 
		}
		value = Boolean(value);
		if(dojo.isIE || dojo.isSafari || dojo.isOpera){
			var preventIEfocus = dojo.isIE && (this.isLoaded || !this.focusOnLoad);
			if(preventIEfocus){ this.editNode.unselectable = "on"; }
			this.editNode.contentEditable = !value;
			if(preventIEfocus){
				var _this = this;
				setTimeout(function(){ _this.editNode.unselectable = "off"; }, 0);
			}
		}else{ //moz
			if(value){
				//AP: why isn't this set in the constructor, or put in mozSettingProps as a hash?
				this._mozSettings=[false,this.blockNodeForEnter==='BR'];
			}
			this.document.designMode=(value?'off':'on');
			if(!value && this._mozSettingProps){
				var ps = this._mozSettingProps;
				for(var n in ps){
					if(ps.hasOwnProperty(n)){
						try{
							this.document.execCommand(n,false,ps[n]);
						}catch(e){}
					}
				}
			}
//			this.document.execCommand('contentReadOnly', false, value);
//				if(value){
//					this.blur(); //to remove the blinking caret
//				}
		}
		this.disabled = value;
	},

/* Event handlers
 *****************/

	_isResized: function(){ return false; },

	onLoad: function(/* Event */ e){
		// summary:
		//		handler after the content of the document finishes loading
		if(!this.window.__registeredWindow){
			this.window.__registeredWindow = true;
			dijit.registerWin(this.window);
		}
		if(!dojo.isIE && (this.height || dojo.isMoz)){
			this.editNode=this.document.body;
		}else{
			this.editNode=this.document.body.firstChild;
			var _this = this;
			if(dojo.isIE){ // #4996 IE wants to focus the BODY tag
				var tabStop = this.tabStop = dojo.doc.createElement('<div tabIndex=-1>');
				this.editingArea.appendChild(tabStop);
				this.iframe.onfocus = function(){ _this.editNode.setActive(); }
			}
		}
		this.focusNode = this.editNode; // for InlineEditBox

		try{
			this.attr('disabled',false);
		}catch(e){
			// Firefox throws an exception if the editor is initially hidden
			// so, if this fails, try again onClick by adding "once" advice
			var handle = dojo.connect(this, "onClick", this, function(){
				this.attr('disabled',false);
				dojo.disconnect(handle);
			});
		}

		this._preDomFilterContent(this.editNode);

		var events = this.events.concat(this.captureEvents);
		var ap = (this.iframe) ? this.document : this.editNode;
		dojo.forEach(events, function(item){
			// dojo.connect(ap, item.toLowerCase(), console, "debug");
			this.connect(ap, item.toLowerCase(), item);
		}, this);
		
		if(dojo.isIE){ // IE contentEditable
			// give the node Layout on IE
			this.connect(this.document, "onmousedown", "_onIEMouseDown"); // #4996 fix focus
			this.editNode.style.zoom = 1.0;
		}
		if(this.focusOnLoad){
			dojo.addOnLoad(dojo.hitch(this, "focus"));
		}

		this.onDisplayChanged(e);

		if("_delayedDisabled" in this){
			// We tried to set the disabled attribute previously - but we didn't
			// have everything set up.  Set it up now that we have our nodes
			// created
			var d = this._delayedDisabled;
			delete this._delayedDisabled;
			this.attr("disabled", d);
		}
		this.isLoaded = true;

		if(this.onLoadDeferred){
			this.onLoadDeferred.callback(true);
		}
	},

	onKeyDown: function(/* Event */ e){
		// summary: Fired on keydown

		// we need this event at the moment to get the events from control keys
		// such as the backspace. It might be possible to add this to Dojo, so that
		// keyPress events can be emulated by the keyDown and keyUp detection.
		
		if (e.keyCode === dojo.keys.TAB && this.isTabIndent ){
				dojo.stopEvent(e); //prevent tab from moving focus out of editor

				// FIXME: this is a poor-man's indent/outdent. It would be
				// better if it added 4 "&nbsp;" chars in an undoable way.
				// Unfortunately pasteHTML does not prove to be undoable
				if (this.queryCommandEnabled((e.shiftKey ? "outdent" : "indent"))){
					this.execCommand((e.shiftKey ? "outdent" : "indent"));
				}			
		}
		if(dojo.isIE){
			if (e.keyCode == dojo.keys.TAB && !this.isTabIndent){
				if(e.shiftKey && !e.ctrlKey && !e.altKey){
					// focus the BODY so the browser will tab away from it instead
					this.iframe.focus();
				}else if(!e.shiftKey && !e.ctrlKey && !e.altKey){
					// focus the BODY so the browser will tab away from it instead
					this.tabStop.focus();
				}
			}else if(e.keyCode === dojo.keys.BACKSPACE && this.document.selection.type === "Control"){
				// IE has a bug where if a non-text object is selected in the editor,
				// hitting backspace would act as if the browser's back button was
				// clicked instead of deleting the object. see #1069
				dojo.stopEvent(e);
				this.execCommand("delete");
			}else if((65 <= e.keyCode&&e.keyCode <= 90) ||
				(e.keyCode>=37&&e.keyCode<=40) // FIXME: get this from connect() instead!
			){ //arrow keys
				e.charCode = e.keyCode;
				this.onKeyPress(e);
			}
		}else if(dojo.isMoz  && !this.isTabIndent){
			if(e.keyCode == dojo.keys.TAB && !e.shiftKey && !e.ctrlKey && !e.altKey && this.iframe){
				// update iframe document title for screen reader
				var titleObj = dojo.isFF<3 ? this.iframe.contentDocument : this.iframe;
			 	titleObj.title = this._localizedIframeTitles.iframeFocusTitle;
				// Place focus on the iframe. A subsequent tab or shift tab will put focus
				// on the correct control.
				this.iframe.focus();  // this.focus(); won't work
				dojo.stopEvent(e);
			}else if(e.keyCode == dojo.keys.TAB && e.shiftKey){
				// if there is a toolbar, set focus to it, otherwise ignore
				if(this.toolbar){
					this.toolbar.focus();
				}
				dojo.stopEvent(e);
			}
		}
		return true;
	},

	onKeyUp: function(e){
		// summary: Fired on keyup
		return;
	},

	setDisabled: function(/*Boolean*/ disabled){
		dojo.deprecated('dijit.Editor::setDisabled is deprecated','use dijit.Editor::attr("disabled",boolean) instead', 2);
		this.attr('disabled',disabled);
	},
	_setValueAttr: function(/*String*/ value){
		// summary: registers that attr("value", foo) should call setValue(foo)
		this.setValue(value);
	},

	onKeyPress: function(e){
		// handle the various key events
		//
		var c = (e.keyChar && e.keyChar.toLowerCase()) || e.keyCode
		var handlers = this._keyHandlers[c];
		//
		var args = arguments;
		if(handlers){
			dojo.forEach(handlers, function(h){
				if((!!h.shift == !!e.shiftKey)&&(!!h.ctrl == !!e.ctrlKey)){
					if(!h.handler.apply(this, args)){
						e.preventDefault();
					}
					// break;
				}
			}, this);
		}

		// function call after the character has been inserted
		if(!this._onKeyHitch){
			this._onKeyHitch=dojo.hitch(this, "onKeyPressed");
		}
		setTimeout(this._onKeyHitch, 1);
		return true;
	},

	addKeyHandler: function(/*String*/key, /*Boolean*/ctrl, /*Boolean*/shift, /*Function*/handler){
		// summary: add a handler for a keyboard shortcut
		// description:
		//	The key argument should be in lowercase if it is a letter charater
		if(!dojo.isArray(this._keyHandlers[key])){
			this._keyHandlers[key] = [];
		}
		this._keyHandlers[key].push({
			shift: shift || false,
			ctrl: ctrl || false,
			handler: handler
		});
	},

	onKeyPressed: function(){
		this.onDisplayChanged(/*e*/); // can't pass in e
	},

	onClick: function(/*Event*/e){
		// 
		this.onDisplayChanged(e);
	},

	_onIEMouseDown: function(/*Event*/e){ // IE only to prevent 2 clicks to focus
		if(!this._focused && !this.disabled){
			this.focus();
		}
	},


	_onBlur: function(e){
		// 

		this.inherited(arguments);
		var _c=this.getValue(true);
		
		if(_c!=this.savedContent){
			this.onChange(_c);
			this.savedContent=_c;
		}
		if(dojo.isMoz && this.iframe){
			var titleObj = dojo.isFF<3 ? this.iframe.contentDocument : this.iframe;
			 titleObj.title = this._localizedIframeTitles.iframeEditTitle;
		} 

	},
	_initialFocus: true,
	_onFocus: function(/*Event*/e){
		// summary: Fired on focus

		// 
		if(dojo.isMoz && this._initialFocus){
			this._initialFocus = false;
			if(this.editNode.innerHTML.replace(/^\s+|\s+$/g, "") == "&nbsp;"){
				this.placeCursorAtStart();
				// this.execCommand("selectall");
				// this.window.getSelection().collapseToStart();
			}
		}
		this.inherited(arguments);
	},

	// TODO: why is this needed - should we deprecate this ?
	blur: function(){
		// summary: remove focus from this instance
		if(!dojo.isIE && this.window.document.documentElement && this.window.document.documentElement.focus){
			this.window.document.documentElement.focus();
		}else if(dojo.doc.body.focus){
			dojo.doc.body.focus();
		}
	},

	focus: function(){
		// summary: move focus to this instance
		if(!dojo.isIE){
			dijit.focus(this.iframe);
		}else if(this.editNode && this.editNode.focus){
			// editNode may be hidden in display:none div, lets just punt in this case
			//this.editNode.focus(); -> causes IE to scroll always (strict and quirks mode) to the top the Iframe 
			// if we fire the event manually and let the browser handle the focusing, the latest  
			// cursor position is focused like in FF                         
			this.iframe.fireEvent('onfocus', document.createEventObject()); // createEventObject only in IE 
		//	}else{
		// 	// TODO: should we throw here?
		//	
		}
	},

	// _lastUpdate: 0,
	updateInterval: 200,
	_updateTimer: null,
	onDisplayChanged: function(/*Event*/e){
		// summary:
		//		This event will be fired everytime the display context
		//		changes and the result needs to be reflected in the UI.
		// description:
		//		If you don't want to have update too often,
		//		onNormalizedDisplayChanged should be used instead

		// var _t=new Date();
		if(this._updateTimer){
			clearTimeout(this._updateTimer);
		}
		if(!this._updateHandler){
			this._updateHandler = dojo.hitch(this,"onNormalizedDisplayChanged");
		}
		this._updateTimer = setTimeout(this._updateHandler, this.updateInterval);
	},
	onNormalizedDisplayChanged: function(){
		// summary:
		//		This event is fired every updateInterval ms or more
		// description:
		//		If something needs to happen immidiately after a
		//		user change, please use onDisplayChanged instead
		delete this._updateTimer;
	},
	onChange: function(newContent){
		// summary:
		//		this is fired if and only if the editor loses focus and
		//		the content is changed
	},
	_normalizeCommand: function(/*String*/cmd){
		// summary:
		//		Used as the advice function by dojo.connect to map our
		//		normalized set of commands to those supported by the target
		//		browser

		var command = cmd.toLowerCase();
		if(command == "formatblock"){
			if(dojo.isSafari){ command = "heading"; }
		}else if(command == "hilitecolor" && !dojo.isMoz){
			command = "backcolor";
		}

		return command;
	},

	_qcaCache: {},
	queryCommandAvailable: function(/*String*/command){
		// summary:
		//		Tests whether a command is supported by the host. Clients
		//		SHOULD check whether a command is supported before attempting
		//		to use it, behaviour for unsupported commands is undefined.
		// command: The command to test for

		// memoizing version. See _queryCommandAvailable for computing version
		var ca = this._qcaCache[command];
		if(ca != undefined){ return ca; }
		return this._qcaCache[command] = this._queryCommandAvailable(command);
	},
	
	_queryCommandAvailable: function(/*String*/command){

		var ie = 1;
		var mozilla = 1 << 1;
		var safari = 1 << 2;
		var opera = 1 << 3;
		var safari420 = 1 << 4;

		var gt420 = dojo.isSafari;

		function isSupportedBy(browsers){
			return {
				ie: Boolean(browsers & ie),
				mozilla: Boolean(browsers & mozilla),
				safari: Boolean(browsers & safari),
				safari420: Boolean(browsers & safari420),
				opera: Boolean(browsers & opera)
			}
		}

		var supportedBy = null;

		switch(command.toLowerCase()){
			case "bold": case "italic": case "underline":
			case "subscript": case "superscript":
			case "fontname": case "fontsize":
			case "forecolor": case "hilitecolor":
			case "justifycenter": case "justifyfull": case "justifyleft":
			case "justifyright": case "delete": case "selectall": case "toggledir":
				supportedBy = isSupportedBy(mozilla | ie | safari | opera);
				break;

			case "createlink": case "unlink": case "removeformat":
			case "inserthorizontalrule": case "insertimage":
			case "insertorderedlist": case "insertunorderedlist":
			case "indent": case "outdent": case "formatblock":
			case "inserthtml": case "undo": case "redo": case "strikethrough": case "tabindent":
				supportedBy = isSupportedBy(mozilla | ie | opera | safari420);
				break;

			case "blockdirltr": case "blockdirrtl":
			case "dirltr": case "dirrtl":
			case "inlinedirltr": case "inlinedirrtl":
				supportedBy = isSupportedBy(ie);
				break;
			case "cut": case "copy": case "paste":
				supportedBy = isSupportedBy( ie | mozilla | safari420);
				break;

			case "inserttable":
				supportedBy = isSupportedBy(mozilla | ie);
				break;

			case "insertcell": case "insertcol": case "insertrow":
			case "deletecells": case "deletecols": case "deleterows":
			case "mergecells": case "splitcell":
				supportedBy = isSupportedBy(ie | mozilla);
				break;

			default: return false;
		}

		return (dojo.isIE && supportedBy.ie) ||
			(dojo.isMoz && supportedBy.mozilla) ||
			(dojo.isSafari && supportedBy.safari) ||
			(gt420 && supportedBy.safari420) ||
			(dojo.isOpera && supportedBy.opera);  // Boolean return true if the command is supported, false otherwise
	},

	execCommand: function(/*String*/command, argument){
		// summary: Executes a command in the Rich Text area
		// command: The command to execute
		// argument: An optional argument to the command
		var returnValue;

		//focus() is required for IE to work
		//In addition, focus() makes sure after the execution of
		//the command, the editor receives the focus as expected
		this.focus();

		command = this._normalizeCommand(command);

		if(argument != undefined){
			if(command == "heading"){
				throw new Error("unimplemented");
			}else if((command == "formatblock") && dojo.isIE){
				argument = '<'+argument+'>';
			}
		}
		if(command == "inserthtml"){
			//TODO: we shall probably call _preDomFilterContent here as well
			argument = this._preFilterContent(argument);
			returnValue = true;
			if(dojo.isIE){
				var insertRange = this.document.selection.createRange();
				if(this.document.selection.type.toUpperCase()=='CONTROL'){
					var n=insertRange.item(0);
					while(insertRange.length){
						insertRange.remove(insertRange.item(0));
					}
					n.outerHTML=argument;
				}else{
					insertRange.pasteHTML(argument);
				}
				insertRange.select();
				//insertRange.collapse(true);
			}else if(dojo.isMoz && !argument.length){
				//mozilla can not inserthtml an empty html to delete current selection
				//so we delete the selection instead in this case
				this._sCall("remove"); // FIXME
			}else{
				returnValue = this.document.execCommand(command, false, argument);
			}
		}else if(
			(command == "unlink")&&
			(this.queryCommandEnabled("unlink"))&&
			(dojo.isMoz || dojo.isSafari)
		){
			// fix up unlink in Mozilla to unlink the link and not just the selection

			// grab selection
			// Mozilla gets upset if we just store the range so we have to
			// get the basic properties and recreate to save the selection
			//  var selection = this.window.getSelection();

			//	var selectionRange = selection.getRangeAt(0);
			//	var selectionStartContainer = selectionRange.startContainer;
			//	var selectionStartOffset = selectionRange.startOffset;
			//	var selectionEndContainer = selectionRange.endContainer;
			//	var selectionEndOffset = selectionRange.endOffset;

			// select our link and unlink
			var a = this._sCall("getAncestorElement", [ "a" ]);
			this._sCall("selectElement", [ a ]);

			returnValue = this.document.execCommand("unlink", false, null);
		}else if((command == "hilitecolor")&&(dojo.isMoz)){
			// mozilla doesn't support hilitecolor properly when useCSS is
			// set to false (bugzilla #279330)

			this.document.execCommand("styleWithCSS", false, true);
			returnValue = this.document.execCommand(command, false, argument);
			this.document.execCommand("styleWithCSS", false, false);

		}else if((dojo.isIE)&&( (command == "backcolor")||(command == "forecolor") )){
			// Tested under IE 6 XP2, no problem here, comment out
			// IE weirdly collapses ranges when we exec these commands, so prevent it
			//	var tr = this.document.selection.createRange();
			argument = arguments.length > 1 ? argument : null;
			returnValue = this.document.execCommand(command, false, argument);

			// timeout is workaround for weird IE behavior were the text
			// selection gets correctly re-created, but subsequent input
			// apparently isn't bound to it
			//	setTimeout(function(){tr.select();}, 1);
		}else{
			argument = arguments.length > 1 ? argument : null;
			//	if(dojo.isMoz){
			//		this.document = this.iframe.contentWindow.document
			//	}

//			
			if(argument || command!="createlink"){
				returnValue = this.document.execCommand(command, false, argument);
			}
		}

		this.onDisplayChanged();
		return returnValue;
	},

	queryCommandEnabled: function(/*String*/command){
		// summary: check whether a command is enabled or not

		if(this.disabled){ return false; }
		command = this._normalizeCommand(command);
		if(dojo.isMoz || dojo.isSafari){
			if(command == "unlink"){ // mozilla returns true always
				// 
				this._sCall("hasAncestorElement", ["a"]);
			}else if(command == "inserttable"){
				return true;
			}
		}
		//see #4109
		if(dojo.isSafari){
			if(command == "copy"){
				command = "cut";
			}else if(command == "paste"){
				return true;
			}
		}
		//should not allow user to indent neither a non-list node nor list item which is the first item in its parent 
		if(command == 'indent'){
			var li = this._sCall("getAncestorElement", ["li"]);
			var n = li && li.previousSibling;
			while(n){
				if(n.nodeType == 1){
				  return true;
				}
				n = n.previousSibling;
			}
			return false;
		}else if(command == 'outdent'){
			//should not allow user to outdent a non-list node
			return this._sCall("hasAncestorElement", ["li"]);
		}

		// return this.document.queryCommandEnabled(command);
		var elem = dojo.isIE ? this.document.selection.createRange() : this.document;
		return elem.queryCommandEnabled(command);
	},

	queryCommandState: function(command){
		// summary:
		//		check the state of a given command and returns true or false

		if(this.disabled){ return false; }
		command = this._normalizeCommand(command);
		// try{
			//this.editNode.contentEditable = true;
			return this.document.queryCommandState(command);
		// }catch(e){
		// 	
		// 	return false;
		// }
	},

	queryCommandValue: function(command){
		// summary:
		//		check the value of a given command. This matters most for
		//		custom selections and complex values like font value setting

		if(this.disabled){ return false; }
		var r;
		command = this._normalizeCommand(command);
		if(dojo.isIE && command == "formatblock"){
			r = this._native2LocalFormatNames[this.document.queryCommandValue(command)];
		}else{
			r = this.document.queryCommandValue(command);
		}
		return r;
	},

	// Misc.

	_sCall: function(name, args){
		// summary:
		//		run the named method of dijit._editor.selection over the
		//		current editor instance's window, with the passed args
		return dojo.withGlobal(this.window, name, dijit._editor.selection, args);
	},

	// FIXME: this is a TON of code duplication. Why?

	placeCursorAtStart: function(){
		// summary:
		//		place the cursor at the start of the editing area
		this.focus();

		//see comments in placeCursorAtEnd
		var isvalid=false;
		if(dojo.isMoz){
			var first=this.editNode.firstChild;
			while(first){
				if(first.nodeType == 3){
					if(first.nodeValue.replace(/^\s+|\s+$/g, "").length>0){
						isvalid=true;
						this._sCall("selectElement", [ first ]);
						break;
					}
				}else if(first.nodeType == 1){
					isvalid=true;
					this._sCall("selectElementChildren", [ first ]);
					break;
				}
				first = first.nextSibling;
			}
		}else{
			isvalid=true;
			this._sCall("selectElementChildren", [ this.editNode ]);
		}
		if(isvalid){
			this._sCall("collapse", [ true ]);
		}
	},

	placeCursorAtEnd: function(){
		// summary:
		//		place the cursor at the end of the editing area
		this.focus();

		//In mozilla, if last child is not a text node, we have to use
		// selectElementChildren on this.editNode.lastChild otherwise the
		// cursor would be placed at the end of the closing tag of
		//this.editNode.lastChild
		var isvalid=false;
		if(dojo.isMoz){
			var last=this.editNode.lastChild;
			while(last){
				if(last.nodeType == 3){
					if(last.nodeValue.replace(/^\s+|\s+$/g, "").length>0){
						isvalid=true;
						this._sCall("selectElement", [ last ]);
						break;
					}
				}else if(last.nodeType == 1){
					isvalid=true;
					if(last.lastChild){
						this._sCall("selectElement", [ last.lastChild ]);
					}else{
						this._sCall("selectElement", [ last ]);
					}
					break;
				}
				last = last.previousSibling;
			}
		}else{
			isvalid=true;
			this._sCall("selectElementChildren", [ this.editNode ]);
		}
		if(isvalid){
			this._sCall("collapse", [ false ]);
		}
	},

	getValue: function(/*Boolean?*/nonDestructive){
		// summary:
		//		return the current content of the editing area (post filters
		//		are applied)
		//	nonDestructive:
		//		defaults to false. Should the post-filtering be run over a copy
		//		of the live DOM? Most users should pass "true" here unless they
		//		*really* know that none of the installed filters are going to
		//		mess up the editing session.
		if(this.textarea){
			if(this.isClosed || !this.isLoaded){
				return this.textarea.value;
			}
		}

		return this._postFilterContent(null, nonDestructive);
	},
	_getValueAttr: function(){
		// summary: hook to make attr("value") work
		return this.getValue();
	},

	setValue: function(/*String*/html){
		// summary:
		//		This function sets the content. No undo history is preserved.
		if(!this.isLoaded){ 
			// try again after the editor is finished loading 
			this.onLoadDeferred.addCallback(dojo.hitch(this, function(){ 
				this.setValue(html); 
			})); 
			return;
		} 
		if(this.textarea && (this.isClosed || !this.isLoaded)){
			this.textarea.value=html;
		}else{
			html = this._preFilterContent(html);
			var node = this.isClosed ? this.domNode : this.editNode;
			node.innerHTML = html;
			this._preDomFilterContent(node);
		}
		this.onDisplayChanged();
	},

	replaceValue: function(/*String*/html){
		// summary:
		//		this function set the content while trying to maintain the undo stack
		//		(now only works fine with Moz, this is identical to setValue in all
		//		other browsers)
		if(this.isClosed){
			this.setValue(html);
		}else if(this.window && this.window.getSelection && !dojo.isMoz){ // Safari
			// look ma! it's a totally f'd browser!
			this.setValue(html);
		}else if(this.window && this.window.getSelection){ // Moz
			html = this._preFilterContent(html);
			this.execCommand("selectall");
			if(dojo.isMoz && !html){ html = "&nbsp;" }
			this.execCommand("inserthtml", html);
			this._preDomFilterContent(this.editNode);
		}else if(this.document && this.document.selection){//IE
			//In IE, when the first element is not a text node, say
			//an <a> tag, when replacing the content of the editing
			//area, the <a> tag will be around all the content
			//so for now, use setValue for IE too
			this.setValue(html);
		}
	},

	_preFilterContent: function(/*String*/html){
		// summary:
		//		filter the input before setting the content of the editing
		//		area. DOM pre-filtering may happen after this
		//		string-based filtering takes place but as of 1.2, this is not
		//		gauranteed for operations such as the inserthtml command.
		var ec = html;
		dojo.forEach(this.contentPreFilters, function(ef){ if(ef){ ec = ef(ec); } });
		return ec;
	},
	_preDomFilterContent: function(/*DomNode*/dom){
		// summary:
		//		filter the input's live DOM. All filter operations should be
		//		considered to be "live" and operating on the DOM that the user
		//		will be interacting with in their editing session.
		dom = dom || this.editNode;
		dojo.forEach(this.contentDomPreFilters, function(ef){
			if(ef && dojo.isFunction(ef)){
				ef(dom);
			}
		}, this);
	},

	_postFilterContent: function(
		/*DomNode|DomNode[]|String?*/ dom,
		/*Boolean?*/ nonDestructive){
		// summary:
		//		filter the output after getting the content of the editing area
		//
		//	description:
		//		post-filtering allows plug-ins and users to specify any number
		//		of transforms over the editor's content, enabling many common
		//		use-cases such as transforming absolute to relative URLs (and
		//		vice-versa), ensuring conformance with a particular DTD, etc.
		//		The filters are registered in the contentDomPostFilters and
		//		contentPostFilters arrays. Each item in the
		//		contentDomPostFilters array is a function which takes a DOM
		//		Node or array of nodes as its only argument and returns the
		//		same. It is then passed down the chain for further filtering.
		//		The contentPostFilters array behaves the same way, except each
		//		member operates on strings. Together, the DOM and string-based
		//		filtering allow the full range of post-processing that should
		//		be necessaray to enable even the most agressive of post-editing
		//		conversions to take place.
		//
		//		If nonDestructive is set to "true", the nodes are cloned before
		//		filtering proceeds to avoid potentially destructive transforms
		//		to the content which may still needed to be edited further.
		//		Once DOM filtering has taken place, the serialized version of
		//		the DOM which is passed is run through each of the
		//		contentPostFilters functions.
		//
		//	dom:
		//		a node, set of nodes, which to filter using each of the current
		//		members of the contentDomPostFilters and contentPostFilters arrays. 
		//
		//	nonDestructive:
		//		defaults to "false". If true, ensures that filtering happens on
		//		a clone of the passed-in content and not the actual node
		//		itself.
		var ec;
		if(!dojo.isString(dom)){
			dom = dom || this.editNode;
			if(this.contentDomPostFilters.length){
				if(nonDestructive){
					dom = dojo.clone(dom);
				}
				dojo.forEach(this.contentDomPostFilters, function(ef){
					dom = ef(dom);
				});
			}
			ec = dijit._editor.getChildrenHtml(dom);
		}else{
			ec = dom;
		}

		if(!dojo.trim(ec.replace(/^\xA0\xA0*/, '').replace(/\xA0\xA0*$/, '')).length){
			ec = "";
		}

		//	if(dojo.isIE){
		//		//removing appended <P>&nbsp;</P> for IE
		//		ec = ec.replace(/(?:<p>&nbsp;</p>[\n\r]*)+$/i,"");
		//	}
		dojo.forEach(this.contentPostFilters, function(ef){
			ec = ef(ec);
		});

		return ec;
	},

	_saveContent: function(/*Event*/e){
		// summary:
		//		Saves the content in an onunload event if the editor has not been closed
		var saveTextarea = dojo.byId(dijit._scopeName + "._editor.RichText.savedContent");
		saveTextarea.value += this._SEPARATOR + this.name + ":" + this.getValue();
	},


	escapeXml: function(/*String*/str, /*Boolean*/noSingleQuotes){
		//summary:
		//		Adds escape sequences for special characters in XML: &<>"'
		//		Optionally skips escapes for single quotes
		str = str.replace(/&/gm, "&amp;").replace(/</gm, "&lt;").replace(/>/gm, "&gt;").replace(/"/gm, "&quot;");
		if(!noSingleQuotes){
			str = str.replace(/'/gm, "&#39;");
		}
		return str; // string
	},

	getNodeHtml: function(/* DomNode */node){
		dojo.deprecated('dijit.Editor::getNodeHtml is deprecated','use dijit._editor.getNodeHtml instead', 2);
		return dijit._editor.getNodeHtml(node); // String
	},

	getNodeChildrenHtml: function(/* DomNode */dom){
		dojo.deprecated('dijit.Editor::getNodeChildrenHtml is deprecated','use dijit._editor.getChildrenHtml instead', 2);
		return dijit._editor.getChildrenHtml(dom);
	},

	close: function(/*Boolean*/save, /*Boolean*/force){
		// summary:
		//		Kills the editor and optionally writes back the modified contents to the
		//		element from which it originated.
		// save:
		//		Whether or not to save the changes. If false, the changes are discarded.
		// force:
		if(this.isClosed){return false; }

		if(!arguments.length){ save = true; }
		this._content = this.getValue();
		var changed = (this.savedContent != this._content);

		// line height is squashed for iframes
		// FIXME: why was this here? if (this.iframe){ this.domNode.style.lineHeight = null; }

		if(this.interval){ clearInterval(this.interval); }

		if(this.textarea){
			with(this.textarea.style){
				position = "";
				left = top = "";
				if(dojo.isIE){
					overflow = this.__overflow;
					this.__overflow = null;
				}
			}
			this.textarea.value = save ? this._content : this.savedContent;
			dojo._destroyElement(this.domNode);
			this.domNode = this.textarea;
		}else{
			// if(save){
			// why we treat moz differently? comment out to fix #1061
			//		if(dojo.isMoz){
			//			var nc = dojo.doc.createElement("span");
			//			this.domNode.appendChild(nc);
			//			nc.innerHTML = this.editNode.innerHTML;
			//		}else{
			//			this.domNode.innerHTML = this._content;
			//		}
			// }
			this.domNode.innerHTML = save ? this._content : this.savedContent;
		}

		dojo.removeClass(this.domNode, "RichTextEditable");
		this.isClosed = true;
		this.isLoaded = false;
		// FIXME: is this always the right thing to do?
		delete this.editNode;

		if(this.window && this.window._frameElement){
			this.window._frameElement = null;
		}

		this.window = null;
		this.document = null;
		this.editingArea = null;
		this.editorObject = null;

		return changed; // Boolean: whether the content has been modified
	},

	destroyRendering: function(){
		// summary: stub	
	}, 

	destroy: function(){
		this.destroyRendering();
		if(!this.isClosed){ this.close(false); }
		this.inherited("destroy",arguments);
		//dijit._editor.RichText.superclass.destroy.call(this);
	},

	_removeMozBogus: function(/* String */ html){
		return html.replace(/\stype="_moz"/gi, '').replace(/\s_moz_dirty=""/gi, ''); // String
	},
	_removeSafariBogus: function(/* String */ html){
		return html.replace(/\sclass="webkit-block-placeholder"/gi, ''); // String
	},
	_fixContentForMoz: function(/* String */ html){
		// summary:
		//		Moz can not handle strong/em tags correctly, convert them to b/i
		return html.replace(/<(\/)?strong([ \>])/gi, '<$1b$2')
			.replace(/<(\/)?em([ \>])/gi, '<$1i$2' ); // String
	},

	_preFixUrlAttributes: function(/* String */ html){
		return html.replace(/(?:(<a(?=\s).*?\shref=)("|')(.*?)\2)|(?:(<a\s.*?href=)([^"'][^ >]+))/gi, 
				'$1$4$2$3$5$2 _djrealurl=$2$3$5$2')
			.replace(/(?:(<img(?=\s).*?\ssrc=)("|')(.*?)\2)|(?:(<img\s.*?src=)([^"'][^ >]+))/gi, 
				'$1$4$2$3$5$2 _djrealurl=$2$3$5$2'); // String
	}
});

}

if(!dojo._hasResource["dijit.Toolbar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Toolbar"] = true;
dojo.provide("dijit.Toolbar");





dojo.declare("dijit.Toolbar",
	[dijit._Widget, dijit._Templated, dijit._KeyNavContainer],
	{
	// summary: A Toolbar widget, used to hold things like dijit.Editor buttons

	templateString:
		'<div class="dijit dijitToolbar" waiRole="toolbar" tabIndex="${tabIndex}" dojoAttachPoint="containerNode">' +
		//	'<table style="table-layout: fixed" class="dijitReset dijitToolbarTable">' + // factor out style
		//		'<tr class="dijitReset" dojoAttachPoint="containerNode"></tr>'+
		//	'</table>' +
		'</div>',

	tabIndex: "0",

	postCreate: function(){
		this.connectKeyNavHandlers(
			this.isLeftToRight() ? [dojo.keys.LEFT_ARROW] : [dojo.keys.RIGHT_ARROW],
			this.isLeftToRight() ? [dojo.keys.RIGHT_ARROW] : [dojo.keys.LEFT_ARROW]
		);
	},

	startup: function(){
		if(this._started){ return; }

		this.startupKeyNavChildren();

		this.inherited(arguments);
	}
}
);

// Combine with dijit.MenuSeparator??
dojo.declare("dijit.ToolbarSeparator",
	[ dijit._Widget, dijit._Templated ],
	{
	// summary: A spacer between two Toolbar items
	templateString: '<div class="dijitToolbarSeparator dijitInline"></div>',
	postCreate: function(){ dojo.setSelectable(this.domNode, false); },
	isFocusable: function(){ 
		// summary: This widget isn't focusable, so pass along that fact.
		return false; 
	}

});

}

if(!dojo._hasResource["dijit.form.Button"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.Button"] = true;
dojo.provide("dijit.form.Button");




dojo.declare("dijit.form.Button",
	dijit.form._FormWidget,
	{
	// summary:
	//		Basically the same thing as a normal HTML button, but with special styling.
	// description:
	//		Buttons can display a label, an icon, or both.
	//		A label should always be specified (through innerHTML) or the label
	//		attribute.  It can be hidden via showLabel=false.
	// example:
	// |	<button dojoType="dijit.form.Button" onClick="...">Hello world</button>
	// 
	// example:
	// |	var button1 = new dijit.form.Button({label: "hello world", onClick: foo});
	// |	dojo.body().appendChild(button1.domNode);

	// label: HTML String
	//		Text to display in button.
	//		If the label is hidden (showLabel=false) then and no title has
	//		been specified, then label is also set as title attribute of icon.
	label: "",

	// showLabel: Boolean
	//		Set this to true to hide the label text and display only the icon.
	//		(If showLabel=false then iconClass must be specified.)
	//		Especially useful for toolbars.  
	//		If showLabel=true, the label will become the title (a.k.a. tooltip/hint) of the icon.
	//
	//		The exception case is for computers in high-contrast mode, where the label
	//		will still be displayed, since the icon doesn't appear.
	showLabel: true,

	// iconClass: String
	//		Class to apply to div in button to make it display an icon
	iconClass: "",

	type: "button",
	baseClass: "dijitButton",
	templateString:"<span class=\"dijit dijitReset dijitLeft dijitInline\"\n\tdojoAttachEvent=\"ondijitclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\"\n\t><span class=\"dijitReset dijitRight dijitInline\"\n\t\t><span class=\"dijitReset dijitInline dijitButtonNode\"\n\t\t\t><button class=\"dijitReset dijitStretch dijitButtonContents\"\n\t\t\t\tdojoAttachPoint=\"titleNode,focusNode\" \n\t\t\t\tname=\"${name}\" type=\"${type}\" waiRole=\"button\" waiState=\"labelledby-${id}_label\"\n\t\t\t\t><span class=\"dijitReset dijitInline\" dojoAttachPoint=\"iconNode\" \n\t\t\t\t\t><span class=\"dijitReset dijitToggleButtonIconChar\">&#10003;</span \n\t\t\t\t></span \n\t\t\t\t><span class=\"dijitReset dijitInline dijitButtonText\" \n\t\t\t\t\tid=\"${id}_label\"  \n\t\t\t\t\tdojoAttachPoint=\"containerNode\"\n\t\t\t\t></span\n\t\t\t></button\n\t\t></span\n\t></span\n></span>\n",

	attributeMap: dojo.mixin(dojo.clone(dijit.form._FormWidget.prototype.attributeMap), {
		label: {node: "containerNode", type: "innerHTML" },
		iconClass: {node: "iconNode", type: "class" }
	}),
		

	_onClick: function(/*Event*/ e){
		// summary: internal function to handle click actions
		if(this.disabled || this.readOnly){
			return false;
		}
		this._clicked(); // widget click actions
		return this.onClick(e); // user click actions
	},

	_onButtonClick: function(/*Event*/ e){
		// summary: callback when the user activates the button portion
		// if is activated via a keystroke, stop the event 
		if(e.type!='click'){
			dojo.stopEvent(e);
		}
		if(this._onClick(e) === false){ // returning nothing is same as true
			e.preventDefault(); // needed for checkbox
		}else if(this.type=="submit" && !this.focusNode.form){ // see if a nonform widget needs to be signalled
			for(var node=this.domNode; node.parentNode/*#5935*/; node=node.parentNode){
				var widget=dijit.byNode(node);
				if(widget && typeof widget._onSubmit == "function"){
					widget._onSubmit(e);
					break;
				}
			}
		}
	},

	_fillContent: function(/*DomNode*/ source){
		// summary:
		//		If button label is specified as srcNodeRef.innerHTML rather than
		//		this.params.label, handle it here.
		if(source && !("label" in this.params)){
			this.attr('label', source.innerHTML);
		}
	},

	postCreate: function(){
		if (this.showLabel == false){
			dojo.addClass(this.containerNode,"dijitDisplayNone");
		}
		dojo.setSelectable(this.focusNode, false);
		this.inherited(arguments);
	},

	onClick: function(/*Event*/ e){
		// summary: user callback for when button is clicked
		//      if type="submit", return true to perform submit
		return true;
	},

	_clicked: function(/*Event*/ e){
		// summary: internal replaceable function for when the button is clicked
	},

	setLabel: function(/*String*/ content){
		dojo.deprecated("dijit.form.Button.setLabel() is deprecated.  Use attr('label', ...) instead.", "", "2.0");
		this.attr("label", content);
	},
	_setLabelAttr: function(/*String*/ content){
		// summary:
		//		Hook for attr('label', ...) to work.
		// description:
		//		Set the label (text) of the button; takes an HTML string.
		this.containerNode.innerHTML = this.label = content;
		this._layoutHack();
		if (this.showLabel == false && !this.params.title){
			this.titleNode.title = dojo.trim(this.containerNode.innerText || this.containerNode.textContent || '');
		}
	}		
});


dojo.declare("dijit.form.DropDownButton", [dijit.form.Button, dijit._Container], {
	// summary: A button with a popup
	//
	// example:
	// |	<button dojoType="dijit.form.DropDownButton" label="Hello world">
	// |		<div dojotype="dijit.Menu">...</div>
	// |	</button>
	//
	// example:
	// |	var button1 = new dijit.form.DropDownButton({ label: "hi", dropDown: new dijit.Menu(...) });
	// |	dojo.body().appendChild(button1);
	// 	
	
	baseClass : "dijitDropDownButton",

	templateString:"<span class=\"dijit dijitReset dijitLeft dijitInline\"\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse,onclick:_onDropDownClick,onkeydown:_onDropDownKeydown,onblur:_onDropDownBlur,onkeypress:_onKey\"\n\t><span class='dijitReset dijitRight dijitInline'\n\t\t><span class='dijitReset dijitInline dijitButtonNode'\n\t\t\t><button class=\"dijitReset dijitStretch dijitButtonContents\" \n\t\t\t\ttype=\"${type}\" name=\"${name}\"\n\t\t\t\tdojoAttachPoint=\"focusNode,titleNode\" \n\t\t\t\twaiRole=\"button\" waiState=\"haspopup-true,labelledby-${id}_label\"\n\t\t\t\t><span class=\"dijitReset dijitInline\" \n\t\t\t\t\tdojoAttachPoint=\"iconNode\"\n\t\t\t\t></span\n\t\t\t\t><span class=\"dijitReset dijitInline dijitButtonText\"  \n\t\t\t\t\tdojoAttachPoint=\"containerNode,popupStateNode\" \n\t\t\t\t\tid=\"${id}_label\"\n\t\t\t\t></span\n\t\t\t\t><span class=\"dijitReset dijitInline dijitArrowButtonInner\">&thinsp;</span\n\t\t\t\t><span class=\"dijitReset dijitInline dijitArrowButtonChar\">&#9660;</span\n\t\t\t></button\n\t\t></span\n\t></span\n></span>\n",

	_fillContent: function(){
		// my inner HTML contains both the button contents and a drop down widget, like
		// <DropDownButton>  <span>push me</span>  <Menu> ... </Menu> </DropDownButton>
		// The first node is assumed to be the button content. The widget is the popup.
		if(this.srcNodeRef){ // programatically created buttons might not define srcNodeRef
			//FIXME: figure out how to filter out the widget and use all remaining nodes as button
			//	content, not just nodes[0]
			var nodes = dojo.query("*", this.srcNodeRef);
			dijit.form.DropDownButton.superclass._fillContent.call(this, nodes[0]);

			// save pointer to srcNode so we can grab the drop down widget after it's instantiated
			this.dropDownContainer = this.srcNodeRef;
		}
	},

	startup: function(){
		if(this._started){ return; }

		// the child widget from srcNodeRef is the dropdown widget.  Insert it in the page DOM,
		// make it invisible, and store a reference to pass to the popup code.
		if(!this.dropDown){
			var dropDownNode = dojo.query("[widgetId]", this.dropDownContainer)[0];
			this.dropDown = dijit.byNode(dropDownNode);
			delete this.dropDownContainer;
		}
		dijit.popup.prepare(this.dropDown.domNode);

		this.inherited(arguments);
	},

	destroyDescendants: function(){
		if(this.dropDown){
			this.dropDown.destroyRecursive();
			delete this.dropDown;
		}
		this.inherited(arguments);
	},

	_onArrowClick: function(/*Event*/ e){
		// summary: callback when the user mouse clicks on menu popup node
		if(this.disabled || this.readOnly){ return; }
		this._toggleDropDown();
	},

	_onDropDownClick: function(/*Event*/ e){
		// on Firefox 2 on the Mac it is possible to fire onclick
		// by pressing enter down on a second element and transferring
		// focus to the DropDownButton;
		// we want to prevent opening our menu in this situation
		// and only do so if we have seen a keydown on this button;
		// e.detail != 0 means that we were fired by mouse
		var isMacFFlessThan3 = dojo.isFF && dojo.isFF < 3
			&& navigator.appVersion.indexOf("Macintosh") != -1;
		if(!isMacFFlessThan3 || e.detail != 0 || this._seenKeydown){
			this._onArrowClick(e);
		}
		this._seenKeydown = false;
	},

	_onDropDownKeydown: function(/*Event*/ e){
		this._seenKeydown = true;
	},

	_onDropDownBlur: function(/*Event*/ e){
		this._seenKeydown = false;
	},

	_onKey: function(/*Event*/ e){
		// summary: callback when the user presses a key on menu popup node
		if(this.disabled || this.readOnly){ return; }
		if(e.charOrCode == dojo.keys.DOWN_ARROW){
			if(!this.dropDown || this.dropDown.domNode.style.visibility=="hidden"){
				dojo.stopEvent(e);
				this._toggleDropDown();
			}
		}
	},

	_onBlur: function(){
		// summary: called magically when focus has shifted away from this widget and it's dropdown
		this._closeDropDown();
		// don't focus on button.  the user has explicitly focused on something else.
		this.inherited(arguments);
	},

	_toggleDropDown: function(){
		// summary: toggle the drop-down widget; if it is up, close it, if not, open it
		if(this.disabled || this.readOnly){ return; }
		dijit.focus(this.popupStateNode);
		var dropDown = this.dropDown;
		if(!dropDown){ return; }
		if(!this._opened){
			// If there's an href, then load that first, so we don't get a flicker
			if(dropDown.href && !dropDown.isLoaded){
				var self = this;
				var handler = dojo.connect(dropDown, "onLoad", function(){
					dojo.disconnect(handler);
					self._openDropDown();
				});
				dropDown._loadCheck(true);
				return;
			}else{
				this._openDropDown();
			}
		}else{
			this._closeDropDown();
		}
	},

	_openDropDown: function(){
		var dropDown = this.dropDown;
		var oldWidth=dropDown.domNode.style.width;
		var self = this;

		dijit.popup.open({
			parent: this,
			popup: dropDown,
			around: this.domNode,
			orient:
				// TODO: add user-defined positioning option, like in Tooltip.js
				this.isLeftToRight() ? {'BL':'TL', 'BR':'TR', 'TL':'BL', 'TR':'BR'}
				: {'BR':'TR', 'BL':'TL', 'TR':'BR', 'TL':'BL'},
			onExecute: function(){
				self._closeDropDown(true);
			},
			onCancel: function(){
				self._closeDropDown(true);
			},
			onClose: function(){
				dropDown.domNode.style.width = oldWidth;
				self.popupStateNode.removeAttribute("popupActive");
				self._opened = false;
			}
		});
		if(this.domNode.offsetWidth > dropDown.domNode.offsetWidth){
			var adjustNode = null;
			if(!this.isLeftToRight()){
				adjustNode = dropDown.domNode.parentNode;
				var oldRight = adjustNode.offsetLeft + adjustNode.offsetWidth;
			}
			// make menu at least as wide as the button
			dojo.marginBox(dropDown.domNode, {w: this.domNode.offsetWidth});
			if(adjustNode){
				adjustNode.style.left = oldRight - this.domNode.offsetWidth + "px";
			}
		}
		this.popupStateNode.setAttribute("popupActive", "true");
		this._opened=true;
		if(dropDown.focus){
			dropDown.focus();
		}
		// TODO: set this.checked and call setStateClass(), to affect button look while drop down is shown
	},
	
	_closeDropDown: function(/*Boolean*/ focus){
		if(this._opened){
			dijit.popup.close(this.dropDown);
			if(focus){ this.focus(); }
			this._opened = false;			
		}
	}
});

dojo.declare("dijit.form.ComboButton", dijit.form.DropDownButton, {
	// summary: A Normal Button with a DropDown
	//
	// example:
	// |	<button dojoType="dijit.form.ComboButton" onClick="...">
	// |		<span>Hello world</span>
	// |		<div dojoType="dijit.Menu">...</div>
	// |	</button>
	//
	// example:
	// |	var button1 = new dijit.form.ComboButton({label: "hello world", onClick: foo, dropDown: "myMenu"});
	// |	dojo.body().appendChild(button1.domNode);
	// 

	templateString:"<table class='dijit dijitReset dijitInline dijitLeft'\n\tcellspacing='0' cellpadding='0' waiRole=\"presentation\"\n\t><tbody waiRole=\"presentation\"><tr waiRole=\"presentation\"\n\t\t><td class=\"dijitReset dijitStretch dijitButtonContents dijitButtonNode\"\n\t\t\tdojoAttachEvent=\"ondijitclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\"  dojoAttachPoint=\"titleNode\"\n\t\t\twaiRole=\"button\" waiState=\"labelledby-${id}_label\"\n\t\t\t><div class=\"dijitReset dijitInline\" dojoAttachPoint=\"iconNode\" waiRole=\"presentation\"></div\n\t\t\t><div class=\"dijitReset dijitInline dijitButtonText\" id=\"${id}_label\" dojoAttachPoint=\"containerNode\" waiRole=\"presentation\"></div\n\t\t></td\n\t\t><td class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton'\n\t\t\tdojoAttachPoint=\"popupStateNode,focusNode\"\n\t\t\tdojoAttachEvent=\"ondijitclick:_onArrowClick, onkeypress:_onKey,onmouseenter:_onMouse,onmouseleave:_onMouse\"\n\t\t\tstateModifier=\"DownArrow\"\n\t\t\ttitle=\"${optionsTitle}\" name=\"${name}\"\n\t\t\twaiRole=\"button\" waiState=\"haspopup-true\"\n\t\t\t><div class=\"dijitReset dijitArrowButtonInner\" waiRole=\"presentation\">&thinsp;</div\n\t\t\t><div class=\"dijitReset dijitArrowButtonChar\" waiRole=\"presentation\">&#9660;</div\n\t\t></td\n\t></tr></tbody\n></table>\n",

	attributeMap: dojo.mixin(dojo.clone(dijit.form.Button.prototype.attributeMap), {
		id:"",
		name:"",
		tabIndex: ["focusNode", "titleNode"]
	}),

	// optionsTitle: String
	//  text that describes the options menu (accessibility)
	optionsTitle: "",

	baseClass: "dijitComboButton",

	_focusedNode: null,

	postCreate: function(){
		this.inherited(arguments);
		this._focalNodes = [this.titleNode, this.popupStateNode];
		dojo.forEach(this._focalNodes, dojo.hitch(this, function(node){
			if(dojo.isIE){
				this.connect(node, "onactivate", this._onNodeFocus);
				this.connect(node, "ondeactivate", this._onNodeBlur);
			}else{
				this.connect(node, "onfocus", this._onNodeFocus);
				this.connect(node, "onblur", this._onNodeBlur);
			}
		}));
	},

	focusFocalNode: function(node){
		// summary: Focus the focal node node.
		this._focusedNode = node;
		dijit.focus(node);
	},

	hasNextFocalNode: function(){
		// summary: Returns true if this widget has no node currently
		//		focused or if there is a node following the focused one.
		//		False is returned if the last node has focus.
		return this._focusedNode !== this.getFocalNodes()[1];
	},

	focusNext: function(){
		// summary: Focus the focal node following the current node with focus
		//		or the first one if no node currently has focus.
		this._focusedNode = this.getFocalNodes()[this._focusedNode ? 1 : 0];
		dijit.focus(this._focusedNode);
	},

	hasPrevFocalNode: function(){
		// summary: Returns true if this widget has no node currently
		//		focused or if there is a node before the focused one.
		//		False is returned if the first node has focus.
		return this._focusedNode !== this.getFocalNodes()[0];
	},

	focusPrev: function(){
		// summary: Focus the focal node before the current node with focus
		//		or the last one if no node currently has focus.
		this._focusedNode = this.getFocalNodes()[this._focusedNode ? 0 : 1];
		dijit.focus(this._focusedNode);
	},

	getFocalNodes: function(){
		// summary: Returns an array of focal nodes for this widget.
		return this._focalNodes;
	},

	_onNodeFocus: function(evt){
		this._focusedNode = evt.currentTarget;
		var fnc = this._focusedNode == this.focusNode ? "dijitDownArrowButtonFocused" : "dijitButtonContentsFocused";
		dojo.addClass(this._focusedNode, fnc);
	},

	_onNodeBlur: function(evt){
		var fnc = evt.currentTarget == this.focusNode ? "dijitDownArrowButtonFocused" : "dijitButtonContentsFocused";
		dojo.removeClass(evt.currentTarget, fnc);
	},

	_onBlur: function(){
		this.inherited(arguments);
		this._focusedNode = null;
	}
});

dojo.declare("dijit.form.ToggleButton", dijit.form.Button, {
	// summary:
	//	A button that can be in two states (checked or not).
	//	Can be base class for things like tabs or checkbox or radio buttons

	baseClass: "dijitToggleButton",

	// checked: Boolean
	//		Corresponds to the native HTML <input> element's attribute.
	//		In markup, specified as "checked='checked'" or just "checked".
	//		True if the button is depressed, or the checkbox is checked,
	//		or the radio button is selected, etc.
	checked: false,

	attributeMap: dojo.mixin(dojo.clone(dijit.form.Button.prototype.attributeMap),
		{checked:"focusNode"}),

	_clicked: function(/*Event*/ evt){
		this.attr('checked', !this.checked);
	},

	_setCheckedAttr: function(/*Boolean*/ value){
		this.checked = value;
		dojo.attr(this.focusNode || this.domNode, "checked", value);
		dijit.setWaiState(this.focusNode || this.domNode, "pressed", value);
		this._setStateClass();		
		this._handleOnChange(value, true);
	},

	setChecked: function(/*Boolean*/ checked){
		// summary:
		//	Programatically deselect the button
		dojo.deprecated("setChecked("+checked+") is deprecated. Use attr('checked',"+checked+") instead.", "", "2.0");
		this.attr('checked', checked);
	},
	
	reset: function(){
		this._hasBeenBlurred = false;

		// set checked state to original setting
		this.attr('checked', this.params.checked || false);
	}
});

}

if(!dojo._hasResource["dijit._editor._Plugin"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor._Plugin"] = true;
dojo.provide("dijit._editor._Plugin");




dojo.declare("dijit._editor._Plugin", null, {
	// summary
	//		This represents a "plugin" to the editor, which is basically
	//		a single button on the Toolbar and some associated code
	constructor: function(/*Object?*/args, /*DomNode?*/node){
		if(args){
			dojo.mixin(this, args);
		}
		this._connects=[];
	},

	editor: null,
	iconClassPrefix: "dijitEditorIcon",
	button: null,
	queryCommand: null,
	command: "",
	commandArg: null,
	useDefaultCommand: true,
	buttonClass: dijit.form.Button,
	getLabel: function(key){
		return this.editor.commands[key];
	},
	_initButton: function(props){
		if(this.command.length){
			var label = this.getLabel(this.command);
			var className = this.iconClassPrefix+" "+this.iconClassPrefix + this.command.charAt(0).toUpperCase() + this.command.substr(1);
			if(!this.button){
				props = dojo.mixin({
					label: label,
					showLabel: false,
					iconClass: className,
					dropDown: this.dropDown,
					tabIndex: "-1"
				}, props || {});
				this.button = new this.buttonClass(props);
			}
		}
	},
	destroy: function(f){
		dojo.forEach(this._connects, dojo.disconnect);
	},
	connect: function(o, f, tf){
		this._connects.push(dojo.connect(o, f, this, tf));
	},
	updateState: function(){
		var _e = this.editor;
		var _c = this.command;
		if(!_e){ return; }
		if(!_e.isLoaded){ return; }
		if(!_c.length){ return; }
		if(this.button){
			try{
				var enabled = _e.queryCommandEnabled(_c);
				if(this.enabled!==enabled){
					this.enabled=enabled;
					this.button.attr('disabled', !enabled);
				}
				if(typeof this.button.checked == 'boolean'){
					var checked=_e.queryCommandState(_c);
					if(this.checked!==checked){
						this.checked=checked;
						this.button.attr('checked', _e.queryCommandState(_c));
					}
				}
			}catch(e){
				
			}
		}
	},
	setEditor: function(/*Widget*/editor){
		// FIXME: detatch from previous editor!!
		this.editor = editor;

		// FIXME: prevent creating this if we don't need to (i.e., editor can't handle our command)
		this._initButton();

		// FIXME: wire up editor to button here!
		if(this.command.length &&
			!this.editor.queryCommandAvailable(this.command)
		){
			// 
			if(this.button){
				this.button.domNode.style.display = "none";
			}
		}
		if(this.button && this.useDefaultCommand){
			this.connect(this.button, "onClick",
				dojo.hitch(this.editor, "execCommand", this.command, this.commandArg)
			);
		}
		this.connect(this.editor, "onNormalizedDisplayChanged", "updateState");
	},
	setToolbar: function(/*Widget*/toolbar){
		if(this.button){
			toolbar.addChild(this.button);
		}
		// 
	}
});

}

if(!dojo._hasResource["dijit._editor.plugins.EnterKeyHandling"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.plugins.EnterKeyHandling"] = true;
dojo.provide("dijit._editor.plugins.EnterKeyHandling");

dojo.declare("dijit._editor.plugins.EnterKeyHandling", dijit._editor._Plugin, {
	// summary:
	//		This plugin tries to make all browsers have identical behavior
	//		when the user presses the ENTER key.
	//		Specifically, it fixes the double-spaced line problem on IE.
	// description:
	//		On IE the ENTER key creates a new paragraph, which visually looks
	//		bad (ie, "double-spaced") and is also different than FF, which
	//		makes a <br> in that.
	//
	//		In this plugin's default operation, where blockNodeForEnter==BR, it
	//		makes the Editor on IE appear to work like other browsers, by:
	//			1. changing the CSS for the <p> node to not have top/bottom margins,
	//				thus eliminating the double-spaced appearance.
	//			2. adds the singleLinePsToRegularPs callback when the
	//				editor writes out it's data, in order to convert adjacent <p>
	//				nodes into a single node
	//		There's also a pre-filter to convert a single <p> with <br> line breaks
	//		 into separate <p> nodes, to mirror the post-filter.
	//
	//		(Note: originally based on http://bugs.dojotoolkit.org/ticket/2859)
	//
	//		If you set the blockNodeForEnter option to another value, then this
	//		plugin will monitor keystrokes (as they are typed) and apparently
	//		update the editor's content on the fly so that the ENTER key will
	//		either create a new <div>, or a new <p>.
	//
	//		This is useful because in some cases, you need the editor content to be
	//		consistent with the serialized html even while the user is editing
	//		(such as in a collaboration mode extension to the editor).
	//
	//		The handleEnterKey() code was mainly written for the IE double-spacing
	//		issue that is now handled in the pre/post filters.  And it has some
	//		issues... on IE setting blockNodeForEnter to P or BR
	//		causes screen jumps as you type (making it unusable), and on safari
	//		it just has no effect (safari creates a <div> every time the user
	//		hits the enter key).  But apparently useful for case mentioned above.
	//
	//		(Note: originally based on http://bugs.dojotoolkit.org/ticket/1331)

	// blockNodeForEnter: String
	//		this property decides the behavior of Enter key. It can be either P,
	//		DIV, BR, or empty (which means disable this feature). Anything else
	//		will trigger errors.
	blockNodeForEnter: 'BR',

	constructor: function(args){
		if(args){
			dojo.mixin(this,args);
		}
	},
	setEditor: function(editor){
		this.editor = editor;
		if(this.blockNodeForEnter == 'BR'){
			if(dojo.isIE){
				editor.contentDomPreFilters.push(dojo.hitch(this, "regularPsToSingleLinePs"));
				editor.contentDomPostFilters.push(dojo.hitch(this, "singleLinePsToRegularPs"));
				editor.onLoadDeferred.addCallback(dojo.hitch(this, "_fixNewLineBehaviorForIE"));
			}else{
				editor.onLoadDeferred.addCallback(dojo.hitch(this,function(d){
					try{
						this.editor.document.execCommand("insertBrOnReturn", false, true);
					}catch(e){}
					return d;
				}));
			}
		}else if(this.blockNodeForEnter){
			//add enter key handler
			// FIXME: need to port to the new event code!!
			dojo['require']('dijit._editor.range');
			var h = dojo.hitch(this,this.handleEnterKey);
			editor.addKeyHandler(13, 0, 0, h); //enter
			editor.addKeyHandler(13, 0, 1, h); //shift+enter
			this.connect(this.editor,'onKeyPressed','onKeyPressed');
		}
	},
	connect: function(o,f,tf){
		if(!this._connects){
			this._connects=[];
		}
		this._connects.push(dojo.connect(o,f,this,tf));
	},
	destroy: function(){
		dojo.forEach(this._connects,dojo.disconnect);
		this._connects=[];
	},
	onKeyPressed: function(e){
		if(this._checkListLater){
			if(dojo.withGlobal(this.editor.window, 'isCollapsed', dijit)){
				var liparent=dojo.withGlobal(this.editor.window, 'getAncestorElement', dijit._editor.selection, ['LI']);
				if(!liparent){
					//circulate the undo detection code by calling RichText::execCommand directly
					dijit._editor.RichText.prototype.execCommand.call(this.editor, 'formatblock',this.blockNodeForEnter);
					//set the innerHTML of the new block node
					var block = dojo.withGlobal(this.editor.window, 'getAncestorElement', dijit._editor.selection, [this.blockNodeForEnter]);
					if(block){
						block.innerHTML=this.bogusHtmlContent;
						if(dojo.isIE){
							//the following won't work, it will move the caret to the last list item in the previous list
							/*var newrange = dijit.range.create();
							newrange.setStart(block.firstChild,0);
							var selection = dijit.range.getSelection(this.editor.window)
							selection.removeAllRanges();
							selection.addRange(newrange);*/
							//move to the start by move backward one char
							var r = this.editor.document.selection.createRange();
							r.move('character',-1);
							r.select();
						}
					}else{
						alert('onKeyPressed: Can not find the new block node'); //FIXME
					}
				}else{
					
					if(dojo.isMoz){
						if(liparent.parentNode.parentNode.nodeName=='LI'){
							liparent=liparent.parentNode.parentNode;
						}
					}
					var fc=liparent.firstChild;
					if(fc && fc.nodeType==1 && (fc.nodeName=='UL' || fc.nodeName=='OL')){
						liparent.insertBefore(fc.ownerDocument.createTextNode('\xA0'),fc);
						var newrange = dijit.range.create();
						newrange.setStart(liparent.firstChild,0);
						var selection = dijit.range.getSelection(this.editor.window,true)
						selection.removeAllRanges();
						selection.addRange(newrange);
					}
				}
			}
			this._checkListLater = false;
		}
		if(this._pressedEnterInBlock){
			//the new created is the original current P, so we have previousSibling below
			if(this._pressedEnterInBlock.previousSibling){
			    this.removeTrailingBr(this._pressedEnterInBlock.previousSibling);
			}
			delete this._pressedEnterInBlock;
		}
	},
	bogusHtmlContent: '&nbsp;',
	blockNodes: /^(?:P|H1|H2|H3|H4|H5|H6|LI)$/,
	handleEnterKey: function(e){
		// summary:
		//		Manually handle enter key event to make the behavior consistant across
		//		all supported browsers. See property blockNodeForEnter for available options
		
		 // let browser handle this
		// TODO: delete.  this code will never fire because 
		// onKeyPress --> handleEnterKey is only called when blockNodeForEnter != null
		if(!this.blockNodeForEnter){ return true; }

		var selection, range, newrange, doc=this.editor.document,br;
		if(e.shiftKey  //shift+enter always generates <br>
			|| this.blockNodeForEnter=='BR'){
			// TODO: above condition 'this.blockNodeForEnter=='BR'' is meaningless,
			// onKeyPress --> handleEnterKey is only called when blockNodeForEnter != BR
			var parent = dojo.withGlobal(this.editor.window, "getParentElement", dijit._editor.selection);
			var header = dijit.range.getAncestor(parent,this.blockNodes);
			if(header){
				if(!e.shiftKey && header.tagName=='LI'){
					return true; //let brower handle
				}
				selection = dijit.range.getSelection(this.editor.window);
				range = selection.getRangeAt(0);
				if(!range.collapsed){
					range.deleteContents();
				}
				if(dijit.range.atBeginningOfContainer(header, range.startContainer, range.startOffset)){
					if(e.shiftKey){
						br=doc.createElement('br');
						newrange = dijit.range.create();
						header.insertBefore(br,header.firstChild);
						newrange.setStartBefore(br.nextSibling);
						selection.removeAllRanges();
						selection.addRange(newrange);
					}else{
						dojo.place(br, header, "before");
					}
				}else if(dijit.range.atEndOfContainer(header, range.startContainer, range.startOffset)){
					newrange = dijit.range.create();
					br=doc.createElement('br');
					if(e.shiftKey){
						header.appendChild(br);
						header.appendChild(doc.createTextNode('\xA0'));
						newrange.setStart(header.lastChild,0);
					}else{
						dojo.place(br, header, "after");
						newrange.setStartAfter(header);
					}

					selection.removeAllRanges();
					selection.addRange(newrange);
				}else{
					return true; //let brower handle
				}
			}else{
				//don't change this: do not call this.execCommand, as that may have other logic in subclass
				// FIXME
				dijit._editor.RichText.prototype.execCommand.call(this.editor, 'inserthtml', '<br>');
			}
			return false;
		}
		var _letBrowserHandle = true;
		//blockNodeForEnter is either P or DIV
		//first remove selection
		selection = dijit.range.getSelection(this.editor.window);
		range = selection.getRangeAt(0);
		if(!range.collapsed){
			range.deleteContents();
		}

		var block = dijit.range.getBlockAncestor(range.endContainer, null, this.editor.editNode);
		var blockNode = block.blockNode;

		//if this is under a LI or the parent of the blockNode is LI, just let browser to handle it
		if((this._checkListLater = (blockNode && (blockNode.nodeName == 'LI' || blockNode.parentNode.nodeName == 'LI')))){
			
		    if(dojo.isMoz){
				//press enter in middle of P may leave a trailing <br/>, let's remove it later
				this._pressedEnterInBlock = blockNode;
			}
			//if this li only contains spaces, set the content to empty so the browser will outdent this item
			if(/^(?:\s|&nbsp;)$/.test(blockNode.innerHTML)){
				blockNode.innerHTML='';
			}

			return true;
		}

		//text node directly under body, let's wrap them in a node
		if(!block.blockNode || block.blockNode===this.editor.editNode){
			dijit._editor.RichText.prototype.execCommand.call(this.editor, 'formatblock',this.blockNodeForEnter);
			//get the newly created block node
			// FIXME
			block = {blockNode:dojo.withGlobal(this.editor.window, "getAncestorElement", dijit._editor.selection, [this.blockNodeForEnter]),
					blockContainer: this.editor.editNode};
			if(block.blockNode){
				if(!(block.blockNode.textContent || block.blockNode.innerHTML).replace(/^\s+|\s+$/g, "").length){
					this.removeTrailingBr(block.blockNode);
					return false;
				}
			}else{
				block.blockNode = this.editor.editNode;
			}
			selection = dijit.range.getSelection(this.editor.window);
			range = selection.getRangeAt(0);
		}

		var newblock = doc.createElement(this.blockNodeForEnter);
		newblock.innerHTML=this.bogusHtmlContent;
		this.removeTrailingBr(block.blockNode);
		if(dijit.range.atEndOfContainer(block.blockNode, range.endContainer, range.endOffset)){
			if(block.blockNode === block.blockContainer){
				block.blockNode.appendChild(newblock);
			}else{
				dojo.place(newblock, block.blockNode, "after");
			}
			_letBrowserHandle = false;
			//lets move caret to the newly created block
			newrange = dijit.range.create();
			newrange.setStart(newblock,0);
			selection.removeAllRanges();
			selection.addRange(newrange);
			if(this.editor.height){
				newblock.scrollIntoView(false);
			}
		}else if(dijit.range.atBeginningOfContainer(block.blockNode,
				range.startContainer, range.startOffset)){
			dojo.place(newblock, block.blockNode, block.blockNode === block.blockContainer ? "first" : "before");
			if(newblock.nextSibling && this.editor.height){
				//browser does not scroll the caret position into view, do it manually
				newblock.nextSibling.scrollIntoView(false);
			}
			_letBrowserHandle = false;
		}else{ //press enter in the middle of P
			if(dojo.isMoz){
				//press enter in middle of P may leave a trailing <br/>, let's remove it later
				this._pressedEnterInBlock = block.blockNode;
			}
		}
		return _letBrowserHandle;
	},
	removeTrailingBr: function(container){
		var para = /P|DIV|LI/i.test(container.tagName) ?
			container : dijit._editor.selection.getParentOfType(container,['P','DIV','LI']);

		if(!para){ return; }
		if(para.lastChild){
			if((para.childNodes.length > 1 && para.lastChild.nodeType == 3 && /^[\s\xAD]*$/.test(para.lastChild.nodeValue)) ||
				(para.lastChild && para.lastChild.tagName=='BR')){

				dojo._destroyElement(para.lastChild);
			}
		}
		if(!para.childNodes.length){
			para.innerHTML=this.bogusHtmlContent;
		}
	},
	_fixNewLineBehaviorForIE: function(d){
		// summary:
		//		Insert CSS so <p> nodes don't have spacing around them,
		//		thus hiding the fact that ENTER key on IE is creating new
		//		paragraphs
		if(this.editor.document.__INSERTED_EDITIOR_NEWLINE_CSS === undefined){
			var lineFixingStyles = "p{margin:0 !important;}";
			var insertCssText = function(
				/*String*/ cssStr,
				/*Document*/ doc,
				/*String*/ URI)
			{
				//	summary:
				//		Attempt to insert CSS rules into the document through inserting a
				//		style element

				// DomNode Style  = insertCssText(String ".dojoMenu {color: green;}"[, DomDoc document, dojo.uri.Uri Url ])
				if(!cssStr){
					return null; //	HTMLStyleElement
				}
				if(!doc){ doc = document; }
//					if(URI){// fix paths in cssStr
//						cssStr = dojo.html.fixPathsInCssText(cssStr, URI);
//					}
				var style = doc.createElement("style");
				style.setAttribute("type", "text/css");
				// IE is b0rken enough to require that we add the element to the doc
				// before changing it's properties
				var head = doc.getElementsByTagName("head")[0];
				if(!head){ // must have a head tag
					
					return null;	//	HTMLStyleElement
				}else{
					head.appendChild(style);
				}
				if(style.styleSheet){// IE
					var setFunc = function(){
						try{
							style.styleSheet.cssText = cssStr;
						}catch(e){  }
					};
					if(style.styleSheet.disabled){
						setTimeout(setFunc, 10);
					}else{
						setFunc();
					}
				}else{ // w3c
					var cssText = doc.createTextNode(cssStr);
					style.appendChild(cssText);
				}
				return style;	//	HTMLStyleElement
			}
			insertCssText(lineFixingStyles, this.editor.document);
			this.editor.document.__INSERTED_EDITIOR_NEWLINE_CSS = true;
			// this.regularPsToSingleLinePs(this.editNode);
			return d;
		}
		return null;
	},
	regularPsToSingleLinePs: function(element, noWhiteSpaceInEmptyP){
		// summary:
		//		Converts a <p> node containing <br>'s into multiple <p> nodes.
		// description:
		//		See singleLinePsToRegularPs().   This method does the
		//		opposite thing, and is used as a pre-filter when loading the
		//		editor, to mirror the effects of the post-filter at end of edit.
		function wrapLinesInPs(el){
		  // move "lines" of top-level text nodes into ps
			function wrapNodes(nodes){
				// nodes are assumed to all be siblings
				var newP = nodes[0].ownerDocument.createElement('p'); // FIXME: not very idiomatic
				nodes[0].parentNode.insertBefore(newP, nodes[0]);
				dojo.forEach(nodes, function(node){
					newP.appendChild(node);
				});
			}

			var currentNodeIndex = 0;
			var nodesInLine = [];
			var currentNode;
			while(currentNodeIndex < el.childNodes.length){
				currentNode = el.childNodes[currentNodeIndex];
				if( currentNode.nodeType==3 ||	// text node
					(currentNode.nodeType==1 && currentNode.nodeName!='BR' && dojo.style(currentNode, "display")!="block")
				){
					nodesInLine.push(currentNode);
				}else{
					// hit line delimiter; process nodesInLine if there are any
					var nextCurrentNode = currentNode.nextSibling;
					if(nodesInLine.length){
						wrapNodes(nodesInLine);
						currentNodeIndex = (currentNodeIndex+1)-nodesInLine.length;
						if(currentNode.nodeName=="BR"){
							dojo._destroyElement(currentNode);
						}
					}
					nodesInLine = [];
				}
				currentNodeIndex++;
			}
			if(nodesInLine.length){ wrapNodes(nodesInLine); }
		}

		function splitP(el){
			// split a paragraph into seperate paragraphs at BRs
			var currentNode = null;
			var trailingNodes = [];
			var lastNodeIndex = el.childNodes.length-1;
			for(var i=lastNodeIndex; i>=0; i--){
				currentNode = el.childNodes[i];
				if(currentNode.nodeName=="BR"){
					var newP = currentNode.ownerDocument.createElement('p');
					dojo.place(newP, el, "after");
					if (trailingNodes.length==0 && i != lastNodeIndex) {
						newP.innerHTML = "&nbsp;"
					}
					dojo.forEach(trailingNodes, function(node){
						newP.appendChild(node);
					});
					dojo._destroyElement(currentNode);
					trailingNodes = [];
				}else{
					trailingNodes.unshift(currentNode);
				}
			}
		}

		var pList = [];
		var ps = element.getElementsByTagName('p');
		dojo.forEach(ps, function(p){ pList.push(p); });
		dojo.forEach(pList, function(p){
			if(	(p.previousSibling) &&
				(p.previousSibling.nodeName == 'P' || dojo.style(p.previousSibling, 'display') != 'block')
			){
				var newP = p.parentNode.insertBefore(this.document.createElement('p'), p);
				// this is essential to prevent IE from losing the P.
				// if it's going to be innerHTML'd later we need
				// to add the &nbsp; to _really_ force the issue
				newP.innerHTML = noWhiteSpaceInEmptyP ? "" : "&nbsp;";
			}
			splitP(p);
	  },this.editor);
		wrapLinesInPs(element);
		return element;
	},

	singleLinePsToRegularPs: function(element){
		// summary:
		//		Called as post-filter.
		//		Apparently collapses adjacent <p> nodes into a single <p>
		//		nodes with <br> separating each line.
		//
		//	example:
		//		Given this input:
		//	|	<p>line 1</p>
		//	|	<p>line 2</p>
		//	|	<ol>
		//	|		<li>item 1
		//	|		<li>item 2
		//	|	</ol>
		//	|	<p>line 3</p>
		//	|	<p>line 4</p>
		//
		//		Will convert to:
		//	|	<p>line 1<br>line 2</p>
		//	|	<ol>
		//	|		<li>item 1
		//	|		<li>item 2
		//	|	</ol>
		//	|	<p>line 3<br>line 4</p>
		//
		// Not sure why this situation would even come up after the pre-filter and
		// the enter-key-handling code.
	
		function getParagraphParents(node){
			// summary:
			//		Used to get list of all nodes that contain paragraphs.
			//		Seems like that would just be the very top node itself, but apparently not.
			var ps = node.getElementsByTagName('p');
			var parents = [];
			for(var i=0; i<ps.length; i++){
				var p = ps[i];
				var knownParent = false;
				for(var k=0; k < parents.length; k++){
					if(parents[k] === p.parentNode){
						knownParent = true;
						break;
					}
				}
				if(!knownParent){
					parents.push(p.parentNode);
				}
			}
			return parents;
		}

		function isParagraphDelimiter(node){
			if(node.nodeType != 1 || node.tagName != 'P'){
				return dojo.style(node, 'display') == 'block';
			}else{
				if(!node.childNodes.length || node.innerHTML=="&nbsp;"){ return true; }
				//return node.innerHTML.match(/^(<br\ ?\/?>| |\&nbsp\;)$/i);
			}
			return false;
		}

		var paragraphContainers = getParagraphParents(element);
		for(var i=0; i<paragraphContainers.length; i++){
			var container = paragraphContainers[i];
			var firstPInBlock = null;
			var node = container.firstChild;
			var deleteNode = null;
			while(node){
				if(node.nodeType != "1" || node.tagName != 'P'){
					firstPInBlock = null;
				}else if (isParagraphDelimiter(node)){
					deleteNode = node;
					firstPInBlock = null;
				}else{
					if(firstPInBlock == null){
						firstPInBlock = node;
					}else{
						if( (!firstPInBlock.lastChild || firstPInBlock.lastChild.nodeName != 'BR') &&
							(node.firstChild) &&
							(node.firstChild.nodeName != 'BR')
						){
							firstPInBlock.appendChild(this.editor.document.createElement('br'));
						}
						while(node.firstChild){
							firstPInBlock.appendChild(node.firstChild);
						}
						deleteNode = node;
					}
				}
				node = node.nextSibling;
				if(deleteNode){
					dojo._destroyElement(deleteNode);
					deleteNode = null;
				}
			}
		}
		return element;
	}
});

}

if(!dojo._hasResource["dijit.Editor"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Editor"] = true;
dojo.provide("dijit.Editor");









dojo.declare(
	"dijit.Editor",
	dijit._editor.RichText,
	{
		// summary:
		//	A rich text Editing widget
		//
		// description:
		//	This widget provides basic WYSIWYG editing features, based on the browser's
		//	underlying rich text editing capability, accompanied by a toolbar (dijit.Toolbar).
		//  A plugin model is available to extend the editor's capabilities as well as the
		//	the options available in the toolbar.  Content generation may vary across
		//	browsers, and clipboard operations may have different results, to name
		//	a few limitations.  Note: this widget should not be used with the HTML
		//	&lt;TEXTAREA&gt; tag -- see dijit._editor.RichText for details.

		// plugins: Array
		//		a list of plugin names (as strings) or instances (as objects)
		//		for this widget.
		plugins: null,

		// extraPlugins: Array
		//		a list of extra plugin names which will be appended to plugins array
		extraPlugins: null,

		constructor: function(){
			if(!dojo.isArray(this.plugins)){
				this.plugins=["undo","redo","|","cut","copy","paste","|","bold","italic","underline","strikethrough","|",
				"insertOrderedList","insertUnorderedList","indent","outdent","|","justifyLeft","justifyRight","justifyCenter","justifyFull",
				"dijit._editor.plugins.EnterKeyHandling" /*, "createLink"*/];
			}

			this._plugins=[];
			this._editInterval = this.editActionInterval * 1000;

			//IE will always lose focus when other element gets focus, while for FF and safari,
			//when no iframe is used, focus will be lost whenever another element gets focus.
			//For IE, we can connect to onBeforeDeactivate, which will be called right before
			//the focus is lost, so we can obtain the selected range. For other browsers,
			//no equivelent of onBeforeDeactivate, so we need to do two things to make sure 
			//selection is properly saved before focus is lost: 1) when user clicks another 
			//element in the page, in which case we listen to mousedown on the entire page and
			//see whether user clicks out of a focus editor, if so, save selection (focus will
			//only lost after onmousedown event is fired, so we can obtain correct caret pos.)
			//2) when user tabs away from the editor, which is handled in onKeyDown below.
			if(dojo.isIE){
	            this.events.push("onBeforeDeactivate");
							}
		},

		postCreate: function(){
			//for custom undo/redo
			if(this.customUndo){
				dojo['require']("dijit._editor.range");
				this._steps=this._steps.slice(0);
				this._undoedSteps=this._undoedSteps.slice(0);
//				this.addKeyHandler('z',this.KEY_CTRL,this.undo);
//				this.addKeyHandler('y',this.KEY_CTRL,this.redo);
			}
			if(dojo.isArray(this.extraPlugins)){
				this.plugins=this.plugins.concat(this.extraPlugins);
			}

//			try{
			this.inherited(arguments);
//			dijit.Editor.superclass.postCreate.apply(this, arguments);

			this.commands = dojo.i18n.getLocalization("dijit._editor", "commands", this.lang);

			if(!this.toolbar){
				// if we haven't been assigned a toolbar, create one
				this.toolbar = new dijit.Toolbar({});
				dojo.place(this.toolbar.domNode, this.editingArea, "before");
			}

			dojo.forEach(this.plugins, this.addPlugin, this);
			this.onNormalizedDisplayChanged(); //update toolbar button status
//			}catch(e){  }

			this.toolbar.startup();
		},
		destroy: function(){
			dojo.forEach(this._plugins, function(p){
				if(p && p.destroy){
					p.destroy();
				}
			});
			this._plugins=[];
			this.toolbar.destroy(); delete this.toolbar;
			this.inherited(arguments);
		},
		addPlugin: function(/*String||Object*/plugin, /*Integer?*/index){
			//	summary:
			//		takes a plugin name as a string or a plugin instance and
			//		adds it to the toolbar and associates it with this editor
			//		instance. The resulting plugin is added to the Editor's
			//		plugins array. If index is passed, it's placed in the plugins
			//		array at that index. No big magic, but a nice helper for
			//		passing in plugin names via markup.
			//
			//	plugin: String, args object or plugin instance
			//
			//	args: This object will be passed to the plugin constructor
			//
			//	index: Integer
			//		Used when creating an instance from
			//		something already in this.plugins. Ensures that the new
			//		instance is assigned to this.plugins at that index.
			var args=dojo.isString(plugin)?{name:plugin}:plugin;
			if(!args.setEditor){
				var o={"args":args,"plugin":null,"editor":this};
				dojo.publish(dijit._scopeName + ".Editor.getPlugin",[o]);
				if(!o.plugin){
					var pc = dojo.getObject(args.name);
					if(pc){
						o.plugin=new pc(args);
					}
				}
				if(!o.plugin){
					console.warn('Cannot find plugin',plugin);
					return;
				}
				plugin=o.plugin;
			}
			if(arguments.length > 1){
				this._plugins[index] = plugin;
			}else{
				this._plugins.push(plugin);
			}
			plugin.setEditor(this);
			if(dojo.isFunction(plugin.setToolbar)){
				plugin.setToolbar(this.toolbar);
			}
		},
		//the following 3 functions are required to make the editor play nice under a layout widget, see #4070
		startup: function(){
			//
		},
		resize: function(){
			dijit.layout._LayoutWidget.prototype.resize.apply(this,arguments);
		},
		layout: function(){
			this.editingArea.style.height=(this._contentBox.h - dojo.marginBox(this.toolbar.domNode).h)+"px";
			if(this.iframe){
				this.iframe.style.height="100%";
			}
			this._layoutMode = true;
		},
		onBeforeDeactivate: function(e){
			if(this.customUndo){
				this.endEditing(true);
			}
			//in IE, the selection will be lost when other elements get focus,
			//let's save focus before the editor is deactivated
			this._saveSelection();
	        //
	    },
		/* beginning of custom undo/redo support */

		// customUndo: Boolean
		//		Whether we shall use custom undo/redo support instead of the native
		//		browser support. By default, we only enable customUndo for IE, as it
		//		has broken native undo/redo support. Note: the implementation does
		//		support other browsers which have W3C DOM2 Range API implemented.
		customUndo: dojo.isIE,

		//	editActionInterval: Integer
		//		When using customUndo, not every keystroke will be saved as a step.
		//		Instead typing (including delete) will be grouped together: after
		//		a user stop typing for editActionInterval seconds, a step will be
		//		saved; if a user resume typing within editActionInterval seconds,
		//		the timeout will be restarted. By default, editActionInterval is 3
		//		seconds.
		editActionInterval: 3,
		beginEditing: function(cmd){
			if(!this._inEditing){
				this._inEditing=true;
				this._beginEditing(cmd);
			}
			if(this.editActionInterval>0){
				if(this._editTimer){
					clearTimeout(this._editTimer);
				}
				this._editTimer = setTimeout(dojo.hitch(this, this.endEditing), this._editInterval);
			}
		},
		_steps:[],
		_undoedSteps:[],
		execCommand: function(cmd){
			if(this.customUndo && (cmd=='undo' || cmd=='redo')){
				return this[cmd]();
			}else{
				if(this.customUndo){
					this.endEditing();
					this._beginEditing();
				}
				try{
					var r = this.inherited('execCommand',arguments);
                    if(dojo.isSafari && cmd=='paste' && !r){ //see #4598: safari does not support invoking paste from js
                        var su = dojo.string.substitute, _isM = navigator.userAgent.indexOf("Macintosh") != -1;
                        alert(su(this.commands.systemShortcut, [this.commands[cmd], su(this.commands[_isM ? 'appleKey' : 'ctrlKey'], ['V'])]));
                    }
				}catch(e){
					if(dojo.isMoz && /copy|cut|paste/.test(cmd)){
						// Warn user of platform limitation.  Cannot programmatically access keyboard. See ticket #4136
						var sub = dojo.string.substitute,
							accel = {cut:'X', copy:'C', paste:'V'},
							isMac = navigator.userAgent.indexOf("Macintosh") != -1;
						alert(sub(this.commands.systemShortcutFF,
							[this.commands[cmd], sub(this.commands[isMac ? 'appleKey' : 'ctrlKey'], [accel[cmd]])]));
					}
					r = false;
				}
				if(this.customUndo){
					this._endEditing();
				}
				return r;
			}
		},
		queryCommandEnabled: function(cmd){
			if(this.customUndo && (cmd=='undo' || cmd=='redo')){
				return cmd=='undo'?(this._steps.length>1):(this._undoedSteps.length>0);
			}else{
				return this.inherited('queryCommandEnabled',arguments);
			}
		},

		focus: function(){
			var restore=0;
			//
			if(this._savedSelection && dojo.isIE){
				restore = dijit._curFocus!=this.editNode;
			}
		    this.inherited(arguments);
		    if(restore){
		    	this._restoreSelection();
		    }
		},
		_moveToBookmark: function(b){
			var bookmark=b;
			if(dojo.isIE){
				if(dojo.isArray(b)){//IE CONTROL
					bookmark=[];
					dojo.forEach(b,function(n){
						bookmark.push(dijit.range.getNode(n,this.editNode));
					},this);
				}
			}else{//w3c range
				var r=dijit.range.create();
				r.setStart(dijit.range.getNode(b.startContainer,this.editNode),b.startOffset);
				r.setEnd(dijit.range.getNode(b.endContainer,this.editNode),b.endOffset);
				bookmark=r;
			}
			dojo.withGlobal(this.window,'moveToBookmark',dijit,[bookmark]);
		},
		_changeToStep: function(from,to){
			this.setValue(to.text);
			var b=to.bookmark;
			if(!b){ return; }
			this._moveToBookmark(b);
		},
		undo: function(){
//			
			this.endEditing(true);
			var s=this._steps.pop();
			if(this._steps.length>0){
				this.focus();
				this._changeToStep(s,this._steps[this._steps.length-1]);
				this._undoedSteps.push(s);
				this.onDisplayChanged();
				return true;
			}
			return false;
		},
		redo: function(){
//			
			this.endEditing(true);
			var s=this._undoedSteps.pop();
			if(s && this._steps.length>0){
				this.focus();
				this._changeToStep(this._steps[this._steps.length-1],s);
				this._steps.push(s);
				this.onDisplayChanged();
				return true;
			}
			return false;
		},
		endEditing: function(ignore_caret){
			if(this._editTimer){
				clearTimeout(this._editTimer);
			}
			if(this._inEditing){
				this._endEditing(ignore_caret);
				this._inEditing=false;
			}
		},
		_getBookmark: function(){
			var b=dojo.withGlobal(this.window,dijit.getBookmark);
			var tmp=[];
			if(dojo.isIE){
				if(dojo.isArray(b)){//CONTROL
					dojo.forEach(b,function(n){
						tmp.push(dijit.range.getIndex(n,this.editNode).o);
					},this);
					b=tmp;
				}
			}else{//w3c range
				tmp=dijit.range.getIndex(b.startContainer,this.editNode).o;
				b={startContainer:tmp,
					startOffset:b.startOffset,
					endContainer:b.endContainer===b.startContainer?tmp:dijit.range.getIndex(b.endContainer,this.editNode).o,
					endOffset:b.endOffset};
			}
			return b;
		},
		_beginEditing: function(cmd){
			if(this._steps.length===0){
				this._steps.push({'text':this.savedContent,'bookmark':this._getBookmark()});
			}
		},
		_endEditing: function(ignore_caret){
			var v=this.getValue(true);

			this._undoedSteps=[];//clear undoed steps
			this._steps.push({text: v, bookmark: this._getBookmark()});
		},
		onKeyDown: function(e){
			//We need to save selection if the user TAB away from this editor
			//no need to call _saveSelection for IE, as that will be taken care of in onBeforeDeactivate
			if(!dojo.isIE && !this.iframe && e.keyCode==dojo.keys.TAB && !this.tabIndent){
				this._saveSelection();
			}
			if(!this.customUndo){
				this.inherited('onKeyDown',arguments);
				return;
			}
			var k = e.keyCode, ks = dojo.keys;
			if(e.ctrlKey && !e.altKey){//undo and redo only if the special right Alt + z/y are not pressed #5892
				if(k == 90 || k == 122){ //z
					dojo.stopEvent(e);
					this.undo();
					return;
				}else if(k == 89 || k == 121){ //y
					dojo.stopEvent(e);
					this.redo();
					return;
				}
			}
			this.inherited('onKeyDown',arguments);

			switch(k){
					case ks.ENTER:
					case ks.BACKSPACE:
					case ks.DELETE:
						this.beginEditing();
						break;
					case 88: //x
					case 86: //v
						if(e.ctrlKey && !e.altKey && !e.metaKey){
							this.endEditing();//end current typing step if any
							if(e.keyCode == 88){
								this.beginEditing('cut');
								//use timeout to trigger after the cut is complete
								setTimeout(dojo.hitch(this, this.endEditing), 1);
							}else{
								this.beginEditing('paste');
								//use timeout to trigger after the paste is complete
								setTimeout(dojo.hitch(this, this.endEditing), 1);
							}
							break;
						}
						//pass through
					default:
						if(!e.ctrlKey && !e.altKey && !e.metaKey && (e.keyCode<dojo.keys.F1 || e.keyCode>dojo.keys.F15)){
							this.beginEditing();
							break;
						}
						//pass through
					case ks.ALT:
						this.endEditing();
						break;
					case ks.UP_ARROW:
					case ks.DOWN_ARROW:
					case ks.LEFT_ARROW:
					case ks.RIGHT_ARROW:
					case ks.HOME:
					case ks.END:
					case ks.PAGE_UP:
					case ks.PAGE_DOWN:
						this.endEditing(true);
						break;
					//maybe ctrl+backspace/delete, so don't endEditing when ctrl is pressed
					case ks.CTRL:
					case ks.SHIFT:
					case ks.TAB:
						break;
				}
		},
		_onBlur: function(){
			//this._saveSelection();
			this.inherited('_onBlur',arguments);
			this.endEditing(true);
		},
		_saveSelection: function(){
			this._savedSelection=this._getBookmark();
			//
		},
		_restoreSelection: function(){
			if(this._savedSelection){
				//only restore the selection if the current range is collapsed
    			//if not collapsed, then it means the editor does not lose 
    			//selection and there is no need to restore it
    			//if(dojo.withGlobal(this.window,'isCollapsed',dijit)){
    				//
					this._moveToBookmark(this._savedSelection);
				//}
				delete this._savedSelection;
			}
		},
		_onFocus: function(){
			//
			this._restoreSelection();
			this.inherited(arguments);
		},
		onClick: function(){
			this.endEditing(true);
			this.inherited('onClick',arguments);
		}
		/* end of custom undo/redo support */
	}
);

/* the following code is to registered a handler to get default plugins */
dojo.subscribe(dijit._scopeName + ".Editor.getPlugin",null,function(o){
	if(o.plugin){ return; }
	var args = o.args, p;
	var _p = dijit._editor._Plugin;
	var name = args.name;
	switch(name){
		case "undo": case "redo": case "cut": case "copy": case "paste": case "insertOrderedList":
		case "insertUnorderedList": case "indent": case "outdent": case "justifyCenter":
		case "justifyFull": case "justifyLeft": case "justifyRight": case "delete":
		case "selectAll": case "removeFormat": case "unlink":
		case "insertHorizontalRule":
			p = new _p({ command: name });
			break;

		case "bold": case "italic": case "underline": case "strikethrough":
		case "subscript": case "superscript":
			p = new _p({ buttonClass: dijit.form.ToggleButton, command: name });
			break;
		case "|":
			p = new _p({ button: new dijit.ToolbarSeparator() });
	}
//	
	o.plugin=p;
});

}

if(!dojo._hasResource["dijit.Menu"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Menu"] = true;
dojo.provide("dijit.Menu");





dojo.declare("dijit.Menu",
	[dijit._Widget, dijit._Templated, dijit._KeyNavContainer],
	{
	// summary
	//	A context menu you can assign to multiple elements

	constructor: function(){
		this._bindings = [];
	},

	templateString:
			'<table class="dijit dijitMenu dijitReset dijitMenuTable" waiRole="menu" dojoAttachEvent="onkeypress:_onKeyPress">' +
				'<tbody class="dijitReset" dojoAttachPoint="containerNode"></tbody>'+
			'</table>',

	// targetNodeIds: String[]
	//	Array of dom node ids of nodes to attach to.
	//	Fill this with nodeIds upon widget creation and it becomes context menu for those nodes.
	targetNodeIds: [],

	// contextMenuForWindow: Boolean
	//	if true, right clicking anywhere on the window will cause this context menu to open;
	//	if false, must specify targetNodeIds
	contextMenuForWindow: false,

	// leftClickToOpen: Boolean
	//	If true, menu will open on left click instead of right click, similiar to a file menu.
	leftClickToOpen: false,
	
	// parentMenu: Widget
	// pointer to menu that displayed me
	parentMenu: null,

	// popupDelay: Integer
	//	number of milliseconds before hovering (without clicking) causes the popup to automatically open
	popupDelay: 500,

	// _contextMenuWithMouse: Boolean
	//	used to record mouse and keyboard events to determine if a context
	//	menu is being opened with the keyboard or the mouse
	_contextMenuWithMouse: false,

	postCreate: function(){
		if(this.contextMenuForWindow){
			this.bindDomNode(dojo.body());
		}else{
			dojo.forEach(this.targetNodeIds, this.bindDomNode, this);
		}
		this.connectKeyNavHandlers([dojo.keys.UP_ARROW], [dojo.keys.DOWN_ARROW]);
	},

	startup: function(){
		if(this._started){ return; }

		dojo.forEach(this.getChildren(), function(child){ child.startup(); });
		this.startupKeyNavChildren();

		this.inherited(arguments);
	},

	onExecute: function(){
		// summary: attach point for notification about when a menu item has been executed
	},

	onCancel: function(/*Boolean*/ closeAll){
		// summary: attach point for notification about when the user cancels the current menu
	},

	_moveToPopup: function(/*Event*/ evt){
		if(this.focusedChild && this.focusedChild.popup && !this.focusedChild.disabled){
			this.focusedChild._onClick(evt);
		}
	},

	_onKeyPress: function(/*Event*/ evt){
		// summary: Handle keyboard based menu navigation.
		if(evt.ctrlKey || evt.altKey){ return; }

		switch(evt.charOrCode){
			case dojo.keys.RIGHT_ARROW:
				this._moveToPopup(evt);
				dojo.stopEvent(evt);
				break;
			case dojo.keys.LEFT_ARROW:
				if(this.parentMenu){
					this.onCancel(false);
				}else{
					dojo.stopEvent(evt);
				}
				break;
		}
	},

	onItemHover: function(/*MenuItem*/ item){
		// summary: Called when cursor is over a MenuItem
		this.focusChild(item);

		if(this.focusedChild.popup && !this.focusedChild.disabled && !this.hover_timer){
			this.hover_timer = setTimeout(dojo.hitch(this, "_openPopup"), this.popupDelay);
		}
	},

	_onChildBlur: function(item){
		// summary: Close all popups that are open and descendants of this menu
		dijit.popup.close(item.popup);
		item._blur();
		this._stopPopupTimer();
	},

	onItemUnhover: function(/*MenuItem*/ item){
		// summary: Callback fires when mouse exits a MenuItem
	},

	_stopPopupTimer: function(){
		if(this.hover_timer){
			clearTimeout(this.hover_timer);
			this.hover_timer = null;
		}
	},

	_getTopMenu: function(){
		for(var top=this; top.parentMenu; top=top.parentMenu);
		return top;
	},

	onItemClick: function(/*Widget*/ item, /*Event*/ evt){
		// summary: user defined function to handle clicks on an item
		if(item.disabled){ return false; }

		if(item.popup){
			if(!this.is_open){
				this._openPopup();
			}
		}else{
			// before calling user defined handler, close hierarchy of menus
			// and restore focus to place it was when menu was opened
			this.onExecute();

			// user defined handler for click
			item.onClick(evt);
		}
	},

	// thanks burstlib!
	_iframeContentWindow: function(/* HTMLIFrameElement */iframe_el){
		// summary:
		//	Returns the window reference of the passed iframe
		var win = dijit.getDocumentWindow(dijit.Menu._iframeContentDocument(iframe_el)) ||
			// Moz. TODO: is this available when defaultView isn't?
			dijit.Menu._iframeContentDocument(iframe_el)['__parent__'] ||
			(iframe_el.name && dojo.doc.frames[iframe_el.name]) || null;
		return win;	//	Window
	},

	_iframeContentDocument: function(/* HTMLIFrameElement */iframe_el){
		// summary:
		//	Returns a reference to the document object inside iframe_el
		var doc = iframe_el.contentDocument // W3
			|| (iframe_el.contentWindow && iframe_el.contentWindow.document) // IE
			|| (iframe_el.name && dojo.doc.frames[iframe_el.name] && dojo.doc.frames[iframe_el.name].document)
			|| null;
		return doc;	//	HTMLDocument
	},

	bindDomNode: function(/*String|DomNode*/ node){
		// summary: attach menu to given node
		node = dojo.byId(node);

		//TODO: this is to support context popups in Editor.  Maybe this shouldn't be in dijit.Menu
		var win = dijit.getDocumentWindow(node.ownerDocument);
		if(node.tagName.toLowerCase()=="iframe"){
			win = this._iframeContentWindow(node);
			node = dojo.withGlobal(win, dojo.body);
		}

		// to capture these events at the top level,
		// attach to document, not body
		var cn = (node == dojo.body() ? dojo.doc : node);

		node[this.id] = this._bindings.push([
			dojo.connect(cn, (this.leftClickToOpen)?"onclick":"oncontextmenu", this, "_openMyself"),
			dojo.connect(cn, "onkeydown", this, "_contextKey"),
			dojo.connect(cn, "onmousedown", this, "_contextMouse")
		]);
	},

	unBindDomNode: function(/*String|DomNode*/ nodeName){
		// summary: detach menu from given node
		var node = dojo.byId(nodeName);
		if(node){
			var bid = node[this.id]-1, b = this._bindings[bid];
			dojo.forEach(b, dojo.disconnect);
			delete this._bindings[bid];
		}
	},

	_contextKey: function(e){
		this._contextMenuWithMouse = false;
		if(e.keyCode == dojo.keys.F10){
			dojo.stopEvent(e);
			if(e.shiftKey && e.type=="keydown"){
				// FF: copying the wrong property from e will cause the system
				// context menu to appear in spite of stopEvent. Don't know
				// exactly which properties cause this effect.
				var _e = { target: e.target, pageX: e.pageX, pageY: e.pageY };
				_e.preventDefault = _e.stopPropagation = function(){};
				// IE: without the delay, focus work in "open" causes the system
				// context menu to appear in spite of stopEvent.
				window.setTimeout(dojo.hitch(this, function(){ this._openMyself(_e); }), 1);
			}
		}
	},

	_contextMouse: function(e){
		this._contextMenuWithMouse = true;
	},

	_openMyself: function(/*Event*/ e){
		// summary:
		//		Internal function for opening myself when the user
		//		does a right-click or something similar

		if(this.leftClickToOpen&&e.button>0){
			return;
		}
		dojo.stopEvent(e);

		// Get coordinates.
		// if we are opening the menu with the mouse or on safari open
		// the menu at the mouse cursor
		// (Safari does not have a keyboard command to open the context menu
		// and we don't currently have a reliable way to determine
		// _contextMenuWithMouse on Safari)
		var x,y;
		if(dojo.isSafari || this._contextMenuWithMouse){
			x=e.pageX;
			y=e.pageY;
		}else{
			// otherwise open near e.target
			var coords = dojo.coords(e.target, true);
			x = coords.x + 10;
			y = coords.y + 10;
		}

		var self=this;
		var savedFocus = dijit.getFocus(this);
		function closeAndRestoreFocus(){
			// user has clicked on a menu or popup
			dijit.focus(savedFocus);
			dijit.popup.close(self);
		}
		dijit.popup.open({
			popup: this,
			x: x,
			y: y,
			onExecute: closeAndRestoreFocus,
			onCancel: closeAndRestoreFocus,
			orient: this.isLeftToRight() ? 'L' : 'R'
		});
		this.focus();

		this._onBlur = function(){
			this.inherited('_onBlur', arguments);
			// Usually the parent closes the child widget but if this is a context
			// menu then there is no parent
			dijit.popup.close(this);
			// don't try to restore focus; user has clicked another part of the screen
			// and set focus there
		}
	},

	onOpen: function(/*Event*/ e){
		// summary: Open menu relative to the mouse
		this.isShowingNow = true;
	},

	onClose: function(){
		// summary: callback when this menu is closed
		this._stopPopupTimer();
		this.parentMenu = null;
		this.isShowingNow = false;
		this.currentPopup = null;
		if(this.focusedChild){
			this._onChildBlur(this.focusedChild);
			this.focusedChild = null;
		}
	},

	_openPopup: function(){
		// summary: open the popup to the side of the current menu item
		this._stopPopupTimer();
		var from_item = this.focusedChild;
		var popup = from_item.popup;

		if(popup.isShowingNow){ return; }
		popup.parentMenu = this;
		var self = this;
		dijit.popup.open({
			parent: this,
			popup: popup,
			around: from_item.domNode,
			orient: this.isLeftToRight() ? {'TR': 'TL', 'TL': 'TR'} : {'TL': 'TR', 'TR': 'TL'},
			onCancel: function(){
				// called when the child menu is canceled
				dijit.popup.close(popup);
				from_item.focus();	// put focus back on my node
				self.currentPopup = null;
			}
		});

		this.currentPopup = popup;

		if(popup.focus){
			popup.focus();
		}
	},
	
	uninitialize: function(){
 		dojo.forEach(this.targetNodeIds, this.unBindDomNode, this);
 		this.inherited(arguments);
	}
}
);

dojo.declare("dijit.MenuItem",
	[dijit._Widget, dijit._Templated, dijit._Contained],
	{
	// summary: A line item in a Menu Widget

	// Make 3 columns
	//   icon, label, and expand arrow (BiDi-dependent) indicating sub-menu
	templateString:
		 '<tr class="dijitReset dijitMenuItem" dojoAttachPoint="focusNode" waiRole="menuitem" tabIndex="-1"'
		+'dojoAttachEvent="onmouseenter:_onHover,onmouseleave:_onUnhover,ondijitclick:_onClick">'
		+'<td class="dijitReset" waiRole="presentation"><div class="dijitMenuItemIcon" dojoAttachPoint="iconNode"></div></td>'
		+'<td class="dijitReset dijitMenuItemLabel" dojoAttachPoint="containerNode"></td>'
		+'<td class="dijitReset dijitMenuArrowCell" waiRole="presentation">'
			+'<div dojoAttachPoint="arrowWrapper" style="display: none">'
				+'<div class="dijitMenuExpand"></div>'
				+'<span class="dijitMenuExpandA11y">+</span>'
			+'</div>'
		+'</td>'
		+'</tr>',

	attributeMap: dojo.mixin(dojo.clone(dijit._Widget.prototype.attributeMap), {
		label: {node: "containerNode", type: "innerHTML"},
		iconClass: {node: "iconNode", type: "class" }
	}),

	// label: String
	//	menu text
	label: '',

	// iconClass: String
	//		Class to apply to div in button to make it display an icon
	iconClass: "",

	// disabled: Boolean
	//  if true, the menu item is disabled
	//  if false, the menu item is enabled
	disabled: false,

	_fillContent: function(/*DomNode*/ source){
		// If button label is specified as srcNodeRef.innerHTML rather than
		// this.params.label, handle it here.
		if(source && !("label" in this.params)){
			this.attr('label', source.innerHTML);
		}
	},

	postCreate: function(){
		dojo.setSelectable(this.domNode, false);
		dojo.attr(this.containerNode, "id", this.id+"_text");
		dijit.setWaiState(this.domNode, "labelledby", this.id+"_text");
	},

	_onHover: function(){
		// summary: callback when mouse is moved onto menu item
		this.getParent().onItemHover(this);
	},

	_onUnhover: function(){
		// summary: callback when mouse is moved off of menu item

		// if we are unhovering the currently selected item
		// then unselect it
		this.getParent().onItemUnhover(this);
	},

	_onClick: function(evt){
		this.getParent().onItemClick(this, evt);
		dojo.stopEvent(evt);
	},

	onClick: function(/*Event*/ evt){
		// summary: User defined function to handle clicks
	},

	focus: function(){
		dojo.addClass(this.domNode, 'dijitMenuItemHover');
		try{
			dijit.focus(this.focusNode);
		}catch(e){
			// this throws on IE (at least) in some scenarios
		}
	},

	_blur: function(){
		dojo.removeClass(this.domNode, 'dijitMenuItemHover');
	},

	setLabel: function(/*String*/ content){
		dojo.deprecated("dijit.MenuItem.setLabel() is deprecated.  Use attr('label', ...) instead.", "", "2.0");
		this.attr("label", content);
	},

	setDisabled: function(/*Boolean*/ disabled){
		dojo.deprecated("dijit.Menu.setDisabled() is deprecated.  Use attr('disabled', bool) instead.", "", "2.0");
		this.attr('disabled', disabled);
	},
	_setDisabledAttr: function(/*Boolean*/ value){
		// summary:
		//		Hook for attr('disabled', ...) to work.
		//		Enable or disable this menu item.
		this.disabled = value;
		dojo[value ? "addClass" : "removeClass"](this.domNode, 'dijitMenuItemDisabled');
		dijit.setWaiState(this.focusNode, 'disabled', value ? 'true' : 'false');
	}
});

dojo.declare("dijit.PopupMenuItem",
	dijit.MenuItem,
	{
	_fillContent: function(){
		// summary: The innerHTML contains both the menu item text and a popup widget
		// description: the first part holds the menu item text and the second part is the popup
		// example: 
		// |	<div dojoType="dijit.PopupMenuItem">
		// |		<span>pick me</span>
		// |		<popup> ... </popup>
		// |	</div>
		if(this.srcNodeRef){
			var nodes = dojo.query("*", this.srcNodeRef);
			dijit.PopupMenuItem.superclass._fillContent.call(this, nodes[0]);

			// save pointer to srcNode so we can grab the drop down widget after it's instantiated
			this.dropDownContainer = this.srcNodeRef;
		}
	},

	startup: function(){
		if(this._started){ return; }
		this.inherited(arguments);

		// we didn't copy the dropdown widget from the this.srcNodeRef, so it's in no-man's
		// land now.  move it to dojo.doc.body.
		if(!this.popup){
			var node = dojo.query("[widgetId]", this.dropDownContainer)[0];
			this.popup = dijit.byNode(node);
		}
		dojo.body().appendChild(this.popup.domNode);

		this.popup.domNode.style.display="none";
		dojo.style(this.arrowWrapper, "display", "");
		dijit.setWaiState(this.focusNode, "haspopup", "true");
	},
	
	destroyDescendants: function(){
		if(this.popup){
			this.popup.destroyRecursive();
			delete this.popup;
		}
		this.inherited(arguments);
	}
});

dojo.declare("dijit.MenuSeparator",
	[dijit._Widget, dijit._Templated, dijit._Contained],
	{
	// summary: A line between two menu items

	templateString: '<tr class="dijitMenuSeparator"><td colspan=3>'
			+'<div class="dijitMenuSeparatorTop"></div>'
			+'<div class="dijitMenuSeparatorBottom"></div>'
			+'</td></tr>',

	postCreate: function(){
		dojo.setSelectable(this.domNode, false);
	},
	
	isFocusable: function(){
		// summary: over ride to always return false
		return false; // Boolean
	}
});

dojo.declare("dijit.CheckedMenuItem",
	dijit.MenuItem,
	{
	// summary: a checkbox-like menu item for toggling on and off
	
	templateString:
		 '<tr class="dijitReset dijitMenuItem" dojoAttachPoint="focusNode" waiRole="menuitemcheckbox" tabIndex="-1"'
		+'dojoAttachEvent="onmouseenter:_onHover,onmouseleave:_onUnhover,ondijitclick:_onClick">'
		+'<td class="dijitReset" waiRole="presentation"><div class="dijitMenuItemIcon dijitCheckedMenuItemIcon" dojoAttachPoint="iconNode">'
		+'<div class="dijitCheckedMenuItemIconChar">&#10003;</div>'
		+'</div></td>'
		+'<td class="dijitReset dijitMenuItemLabel" dojoAttachPoint="containerNode,labelNode"></td>'
		+'<td class="dijitReset dijitMenuArrowCell" waiRole="presentation">'
			+'<div dojoAttachPoint="arrowWrapper" style="display: none">'
				+'<div class="dijitMenuExpand"></div>'
				+'<span class="dijitMenuExpandA11y">+</span>'
			+'</div>'
		+'</td>'
		+'</tr>',

	// checked: Boolean
	//		Our checked state
	checked: false,
	_setCheckedAttr: function(/*Boolean*/ checked){
		// summary:
		//		Hook so attr('checked', bool) works.
		//		Sets the class and state for the check box.
		dojo.toggleClass(this.iconNode, "dijitCheckedMenuItemIconChecked", checked);
		dijit.setWaiState(this.domNode, "checked", checked);
		this.checked = checked;
	},

	onChange: function(/*Boolean*/ checked){
		// summary: User defined function to handle change events
	},

	_onClick: function(/*Event*/ e){
		// summary: Clicking this item just toggles its state
		if(!this.disabled){
			this.attr("checked", !this.checked);
			this.onChange(this.checked);
		}
		this.inherited(arguments);
	}
});

}

if(!dojo._hasResource["dojo.regexp"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.regexp"] = true;
dojo.provide("dojo.regexp");

/*=====
dojo.regexp = {
	// summary: Regular expressions and Builder resources
};
=====*/

dojo.regexp.escapeString = function(/*String*/str, /*String?*/except){
	//	summary:
	//		Adds escape sequences for special characters in regular expressions
	// except:
	//		a String with special characters to be left unescaped

//	return str.replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm, "\\$1"); // string
	return str.replace(/([\.$?*!=:|{}\(\)\[\]\\\/^])/g, function(ch){
		if(except && except.indexOf(ch) != -1){
			return ch;
		}
		return "\\" + ch;
	}); // String
}

dojo.regexp.buildGroupRE = function(/*Object|Array*/arr, /*Function*/re, /*Boolean?*/nonCapture){
	//	summary:
	//		Builds a regular expression that groups subexpressions
	//	description:
	//		A utility function used by some of the RE generators. The
	//		subexpressions are constructed by the function, re, in the second
	//		parameter.  re builds one subexpression for each elem in the array
	//		a, in the first parameter. Returns a string for a regular
	//		expression that groups all the subexpressions.
	// arr:
	//		A single value or an array of values.
	// re:
	//		A function. Takes one parameter and converts it to a regular
	//		expression. 
	// nonCapture:
	//		If true, uses non-capturing match, otherwise matches are retained
	//		by regular expression. Defaults to false

	// case 1: a is a single value.
	if(!(arr instanceof Array)){
		return re(arr); // String
	}

	// case 2: a is an array
	var b = [];
	for(var i = 0; i < arr.length; i++){
		// convert each elem to a RE
		b.push(re(arr[i]));
	}

	 // join the REs as alternatives in a RE group.
	return dojo.regexp.group(b.join("|"), nonCapture); // String
}

dojo.regexp.group = function(/*String*/expression, /*Boolean?*/nonCapture){
	// summary:
	//		adds group match to expression
	// nonCapture:
	//		If true, uses non-capturing match, otherwise matches are retained
	//		by regular expression. 
	return "(" + (nonCapture ? "?:":"") + expression + ")"; // String
}

}

if(!dojo._hasResource["dojo.number"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.number"] = true;
dojo.provide("dojo.number");







/*=====
dojo.number = {
	// summary: localized formatting and parsing routines for Number
}

dojo.number.__FormatOptions = function(){
	//	pattern: String?
	//		override [formatting pattern](http://www.unicode.org/reports/tr35/#Number_Format_Patterns)
	//		with this string
	//	type: String?
	//		choose a format type based on the locale from the following:
	//		decimal, scientific, percent, currency. decimal by default.
	//	places: Number?
	//		fixed number of decimal places to show.  This overrides any
	//		information in the provided pattern.
	//	round: Number?
	//		5 rounds to nearest .5; 0 rounds to nearest whole (default). -1
	//		means don't round.
	//	currency: String?
	//		an [ISO4217](http://en.wikipedia.org/wiki/ISO_4217) currency code, a three letter sequence like "USD"
	//	symbol: String?
	//		localized currency symbol
	//	locale: String?
	//		override the locale used to determine formatting rules
	this.pattern = pattern;
	this.type = type;
	this.places = places;
	this.round = round;
	this.currency = currency;
	this.symbol = symbol;
	this.locale = locale;
}
=====*/

dojo.number.format = function(/*Number*/value, /*dojo.number.__FormatOptions?*/options){
	// summary:
	//		Format a Number as a String, using locale-specific settings
	// description:
	//		Create a string from a Number using a known localized pattern.
	//		Formatting patterns appropriate to the locale are chosen from the
	//		[CLDR](http://unicode.org/cldr) as well as the appropriate symbols and
	//		delimiters.  See <http://www.unicode.org/reports/tr35/#Number_Elements>
	// value:
	//		the number to be formatted.  If not a valid JavaScript number,
	//		return null.

	options = dojo.mixin({}, options || {});
	var locale = dojo.i18n.normalizeLocale(options.locale);
	var bundle = dojo.i18n.getLocalization("dojo.cldr", "number", locale);
	options.customs = bundle;
	var pattern = options.pattern || bundle[(options.type || "decimal") + "Format"];
	if(isNaN(value)){ return null; } // null
	return dojo.number._applyPattern(value, pattern, options); // String
};

//dojo.number._numberPatternRE = /(?:[#0]*,?)*[#0](?:\.0*#*)?/; // not precise, but good enough
dojo.number._numberPatternRE = /[#0,]*[#0](?:\.0*#*)?/; // not precise, but good enough

dojo.number._applyPattern = function(/*Number*/value, /*String*/pattern, /*dojo.number.__FormatOptions?*/options){
	// summary:
	//		Apply pattern to format value as a string using options. Gives no
	//		consideration to local customs.
	// value:
	//		the number to be formatted.
	// pattern:
	//		a pattern string as described by
	//		[unicode.org TR35](http://www.unicode.org/reports/tr35/#Number_Format_Patterns)
	// options: dojo.number.__FormatOptions?
	//		_applyPattern is usually called via `dojo.number.format()` which
	//		populates an extra property in the options parameter, "customs".
	//		The customs object specifies group and decimal parameters if set.

	//TODO: support escapes
	options = options || {};
	var group = options.customs.group;
	var decimal = options.customs.decimal;

	var patternList = pattern.split(';');
	var positivePattern = patternList[0];
	pattern = patternList[(value < 0) ? 1 : 0] || ("-" + positivePattern);

	//TODO: only test against unescaped
	if(pattern.indexOf('%') != -1){
		value *= 100;
	}else if(pattern.indexOf('\u2030') != -1){
		value *= 1000; // per mille
	}else if(pattern.indexOf('\u00a4') != -1){
		group = options.customs.currencyGroup || group;//mixins instead?
		decimal = options.customs.currencyDecimal || decimal;// Should these be mixins instead?
		pattern = pattern.replace(/\u00a4{1,3}/, function(match){
			var prop = ["symbol", "currency", "displayName"][match.length-1];
			return options[prop] || options.currency || "";
		});
	}else if(pattern.indexOf('E') != -1){
		throw new Error("exponential notation not supported");
	}
	
	//TODO: support @ sig figs?
	var numberPatternRE = dojo.number._numberPatternRE;
	var numberPattern = positivePattern.match(numberPatternRE);
	if(!numberPattern){
		throw new Error("unable to find a number expression in pattern: "+pattern);
	}
	if(options.fractional === false){ options.places = 0; }
	return pattern.replace(numberPatternRE,
		dojo.number._formatAbsolute(value, numberPattern[0], {decimal: decimal, group: group, places: options.places, round: options.round}));
}

dojo.number.round = function(/*Number*/value, /*Number*/places, /*Number?*/multiple){
	//	summary:
	//		Rounds the number at the given number of places
	//	value:
	//		the number to round
	//	places:
	//		the number of decimal places where rounding takes place
	//	multiple:
	//		rounds next place to nearest multiple

	var pieces = String(value).split(".");
	var length = (pieces[1] && pieces[1].length) || 0;
	if(length > places){
		var factor = Math.pow(10, places);
		if(multiple > 0){factor *= 10/multiple;places++;} //FIXME
		value = Math.round(value * factor)/factor;

		// truncate to remove any residual floating point values
		pieces = String(value).split(".");
		length = (pieces[1] && pieces[1].length) || 0;
		if(length > places){
			pieces[1] = pieces[1].substr(0, places);
			value = Number(pieces.join("."));
		}
	}
	return value; //Number
}

/*=====
dojo.number.__FormatAbsoluteOptions = function(){
	//	decimal: String?
	//		the decimal separator
	//	group: String?
	//		the group separator
	//	places: Integer?|String?
	//		number of decimal places.  the range "n,m" will format to m places.
	//	round: Number?
	//		5 rounds to nearest .5; 0 rounds to nearest whole (default). -1
	//		means don't round.
	this.decimal = decimal;
	this.group = group;
	this.places = places;
	this.round = round;
}
=====*/

dojo.number._formatAbsolute = function(/*Number*/value, /*String*/pattern, /*dojo.number.__FormatAbsoluteOptions?*/options){
	// summary: 
	//		Apply numeric pattern to absolute value using options. Gives no
	//		consideration to local customs.
	// value:
	//		the number to be formatted, ignores sign
	// pattern:
	//		the number portion of a pattern (e.g. `#,##0.00`)
	options = options || {};
	if(options.places === true){options.places=0;}
	if(options.places === Infinity){options.places=6;} // avoid a loop; pick a limit

	var patternParts = pattern.split(".");
	var maxPlaces = (options.places >= 0) ? options.places : (patternParts[1] && patternParts[1].length) || 0;
	if(!(options.round < 0)){
		value = dojo.number.round(value, maxPlaces, options.round);
	}

	var valueParts = String(Math.abs(value)).split(".");
	var fractional = valueParts[1] || "";
	if(options.places){
		var comma = dojo.isString(options.places) && options.places.indexOf(",");
		if(comma){
			options.places = options.places.substring(comma+1);
		}
		valueParts[1] = dojo.string.pad(fractional.substr(0, options.places), options.places, '0', true);
	}else if(patternParts[1] && options.places !== 0){
		// Pad fractional with trailing zeros
		var pad = patternParts[1].lastIndexOf("0") + 1;
		if(pad > fractional.length){
			valueParts[1] = dojo.string.pad(fractional, pad, '0', true);
		}

		// Truncate fractional
		var places = patternParts[1].length;
		if(places < fractional.length){
			valueParts[1] = fractional.substr(0, places);
		}
	}else{
		if(valueParts[1]){ valueParts.pop(); }
	}

	// Pad whole with leading zeros
	var patternDigits = patternParts[0].replace(',', '');
	pad = patternDigits.indexOf("0");
	if(pad != -1){
		pad = patternDigits.length - pad;
		if(pad > valueParts[0].length){
			valueParts[0] = dojo.string.pad(valueParts[0], pad);
		}

		// Truncate whole
		if(patternDigits.indexOf("#") == -1){
			valueParts[0] = valueParts[0].substr(valueParts[0].length - pad);
		}
	}

	// Add group separators
	var index = patternParts[0].lastIndexOf(',');
	var groupSize, groupSize2;
	if(index != -1){
		groupSize = patternParts[0].length - index - 1;
		var remainder = patternParts[0].substr(0, index);
		index = remainder.lastIndexOf(',');
		if(index != -1){
			groupSize2 = remainder.length - index - 1;
		}
	}
	var pieces = [];
	for(var whole = valueParts[0]; whole;){
		var off = whole.length - groupSize;
		pieces.push((off > 0) ? whole.substr(off) : whole);
		whole = (off > 0) ? whole.slice(0, off) : "";
		if(groupSize2){
			groupSize = groupSize2;
			delete groupSize2;
		}
	}
	valueParts[0] = pieces.reverse().join(options.group || ",");

	return valueParts.join(options.decimal || ".");
};

/*=====
dojo.number.__RegexpOptions = function(){
	//	pattern: String?
	//		override pattern with this string.  Default is provided based on
	//		locale.
	//	type: String?
	//		choose a format type based on the locale from the following:
	//		decimal, scientific, percent, currency. decimal by default.
	//	locale: String?
	//		override the locale used to determine formatting rules
	//	strict: Boolean?
	//		strict parsing, false by default
	//	places: Number|String?
	//		number of decimal places to accept: Infinity, a positive number, or
	//		a range "n,m".  Defined by pattern or Infinity if pattern not provided.
	this.pattern = pattern;
	this.type = type;
	this.locale = locale;
	this.strict = strict;
	this.places = places;
}
=====*/
dojo.number.regexp = function(/*dojo.number.__RegexpOptions?*/options){
	//	summary:
	//		Builds the regular needed to parse a number
	//	description:
	//		Returns regular expression with positive and negative match, group
	//		and decimal separators
	return dojo.number._parseInfo(options).regexp; // String
}

dojo.number._parseInfo = function(/*Object?*/options){
	options = options || {};
	var locale = dojo.i18n.normalizeLocale(options.locale);
	var bundle = dojo.i18n.getLocalization("dojo.cldr", "number", locale);
	var pattern = options.pattern || bundle[(options.type || "decimal") + "Format"];
//TODO: memoize?
	var group = bundle.group;
	var decimal = bundle.decimal;
	var factor = 1;

	if(pattern.indexOf('%') != -1){
		factor /= 100;
	}else if(pattern.indexOf('\u2030') != -1){
		factor /= 1000; // per mille
	}else{
		var isCurrency = pattern.indexOf('\u00a4') != -1;
		if(isCurrency){
			group = bundle.currencyGroup || group;
			decimal = bundle.currencyDecimal || decimal;
		}
	}

	//TODO: handle quoted escapes
	var patternList = pattern.split(';');
	if(patternList.length == 1){
		patternList.push("-" + patternList[0]);
	}

	var re = dojo.regexp.buildGroupRE(patternList, function(pattern){
		pattern = "(?:"+dojo.regexp.escapeString(pattern, '.')+")";
		return pattern.replace(dojo.number._numberPatternRE, function(format){
			var flags = {
				signed: false,
				separator: options.strict ? group : [group,""],
				fractional: options.fractional,
				decimal: decimal,
				exponent: false};
			var parts = format.split('.');
			var places = options.places;
			if(parts.length == 1 || places === 0){flags.fractional = false;}
			else{
				if(places === undefined){ places = options.pattern ? parts[1].lastIndexOf('0')+1 : Infinity; }
				if(places && options.fractional == undefined){flags.fractional = true;} // required fractional, unless otherwise specified
				if(!options.places && (places < parts[1].length)){ places += "," + parts[1].length; }
				flags.places = places;
			}
			var groups = parts[0].split(',');
			if(groups.length>1){
				flags.groupSize = groups.pop().length;
				if(groups.length>1){
					flags.groupSize2 = groups.pop().length;
				}
			}
			return "("+dojo.number._realNumberRegexp(flags)+")";
		});
	}, true);

	if(isCurrency){
		// substitute the currency symbol for the placeholder in the pattern
		re = re.replace(/([\s\xa0]*)(\u00a4{1,3})([\s\xa0]*)/g, function(match, before, target, after){
			var prop = ["symbol", "currency", "displayName"][target.length-1];
			var symbol = dojo.regexp.escapeString(options[prop] || options.currency || "");
			before = before ? "[\\s\\xa0]" : "";
			after = after ? "[\\s\\xa0]" : "";
			if(!options.strict){
				if(before){before += "*";}
				if(after){after += "*";}
				return "(?:"+before+symbol+after+")?";
			}
			return before+symbol+after;
		});
	}

//TODO: substitute localized sign/percent/permille/etc.?

	// normalize whitespace and return
	return {regexp: re.replace(/[\xa0 ]/g, "[\\s\\xa0]"), group: group, decimal: decimal, factor: factor}; // Object
}

/*=====
dojo.number.__ParseOptions = function(){
	//	pattern: String
	//		override pattern with this string.  Default is provided based on
	//		locale.
	//	type: String?
	//		choose a format type based on the locale from the following:
	//		decimal, scientific, percent, currency. decimal by default.
	//	locale: String
	//		override the locale used to determine formatting rules
	//	strict: Boolean?
	//		strict parsing, false by default
	//	currency: Object
	//		object with currency information
	this.pattern = pattern;
	this.type = type;
	this.locale = locale;
	this.strict = strict;
	this.currency = currency;
}
=====*/
dojo.number.parse = function(/*String*/expression, /*dojo.number.__ParseOptions?*/options){
	// summary:
	//		Convert a properly formatted string to a primitive Number, using
	//		locale-specific settings.
	// description:
	//		Create a Number from a string using a known localized pattern.
	//		Formatting patterns are chosen appropriate to the locale
	//		and follow the syntax described by
	//		[unicode.org TR35](http://www.unicode.org/reports/tr35/#Number_Format_Patterns)
	// expression:
	//		A string representation of a Number
	var info = dojo.number._parseInfo(options);
	var results = (new RegExp("^"+info.regexp+"$")).exec(expression);
	if(!results){
		return NaN; //NaN
	}
	var absoluteMatch = results[1]; // match for the positive expression
	if(!results[1]){
		if(!results[2]){
			return NaN; //NaN
		}
		// matched the negative pattern
		absoluteMatch =results[2];
		info.factor *= -1;
	}

	// Transform it to something Javascript can parse as a number.  Normalize
	// decimal point and strip out group separators or alternate forms of whitespace
	absoluteMatch = absoluteMatch.
		replace(new RegExp("["+info.group + "\\s\\xa0"+"]", "g"), "").
		replace(info.decimal, ".");
	// Adjust for negative sign, percent, etc. as necessary
	return Number(absoluteMatch) * info.factor; //Number
};

/*=====
dojo.number.__RealNumberRegexpFlags = function(){
	//	places: Number?
	//		The integer number of decimal places or a range given as "n,m".  If
	//		not given, the decimal part is optional and the number of places is
	//		unlimited.
	//	decimal: String?
	//		A string for the character used as the decimal point.  Default
	//		is ".".
	//	fractional: Boolean|Array?
	//		Whether decimal places are allowed.  Can be true, false, or [true,
	//		false].  Default is [true, false]
	//	exponent: Boolean|Array?
	//		Express in exponential notation.  Can be true, false, or [true,
	//		false]. Default is [true, false], (i.e. will match if the
	//		exponential part is present are not).
	//	eSigned: Boolean|Array?
	//		The leading plus-or-minus sign on the exponent.  Can be true,
	//		false, or [true, false].  Default is [true, false], (i.e. will
	//		match if it is signed or unsigned).  flags in regexp.integer can be
	//		applied.
	this.places = places;
	this.decimal = decimal;
	this.fractional = fractional;
	this.exponent = exponent;
	this.eSigned = eSigned;
}
=====*/

dojo.number._realNumberRegexp = function(/*dojo.number.__RealNumberRegexpFlags?*/flags){
	// summary:
	//		Builds a regular expression to match a real number in exponential
	//		notation

	// assign default values to missing paramters
	flags = flags || {};
	//TODO: use mixin instead?
	if(!("places" in flags)){ flags.places = Infinity; }
	if(typeof flags.decimal != "string"){ flags.decimal = "."; }
	if(!("fractional" in flags) || /^0/.test(flags.places)){ flags.fractional = [true, false]; }
	if(!("exponent" in flags)){ flags.exponent = [true, false]; }
	if(!("eSigned" in flags)){ flags.eSigned = [true, false]; }

	// integer RE
	var integerRE = dojo.number._integerRegexp(flags);

	// decimal RE
	var decimalRE = dojo.regexp.buildGroupRE(flags.fractional,
		function(q){
			var re = "";
			if(q && (flags.places!==0)){
				re = "\\" + flags.decimal;
				if(flags.places == Infinity){ 
					re = "(?:" + re + "\\d+)?"; 
				}else{
					re += "\\d{" + flags.places + "}"; 
				}
			}
			return re;
		},
		true
	);

	// exponent RE
	var exponentRE = dojo.regexp.buildGroupRE(flags.exponent,
		function(q){ 
			if(q){ return "([eE]" + dojo.number._integerRegexp({ signed: flags.eSigned}) + ")"; }
			return ""; 
		}
	);

	// real number RE
	var realRE = integerRE + decimalRE;
	// allow for decimals without integers, e.g. .25
	if(decimalRE){realRE = "(?:(?:"+ realRE + ")|(?:" + decimalRE + "))";}
	return realRE + exponentRE; // String
};

/*=====
dojo.number.__IntegerRegexpFlags = function(){
	//	signed: Boolean?
	//		The leading plus-or-minus sign. Can be true, false, or `[true,false]`.
	//		Default is `[true, false]`, (i.e. will match if it is signed
	//		or unsigned).
	//	separator: String?
	//		The character used as the thousands separator. Default is no
	//		separator. For more than one symbol use an array, e.g. `[",", ""]`,
	//		makes ',' optional.
	//	groupSize: Number?
	//		group size between separators
	//	groupSize2: Number?
	//		second grouping, where separators 2..n have a different interval than the first separator (for India)
	this.signed = signed;
	this.separator = separator;
	this.groupSize = groupSize;
	this.groupSize2 = groupSize2;
}
=====*/

dojo.number._integerRegexp = function(/*dojo.number.__IntegerRegexpFlags?*/flags){
	// summary: 
	//		Builds a regular expression that matches an integer

	// assign default values to missing paramters
	flags = flags || {};
	if(!("signed" in flags)){ flags.signed = [true, false]; }
	if(!("separator" in flags)){
		flags.separator = "";
	}else if(!("groupSize" in flags)){
		flags.groupSize = 3;
	}
	// build sign RE
	var signRE = dojo.regexp.buildGroupRE(flags.signed,
		function(q){ return q ? "[-+]" : ""; },
		true
	);

	// number RE
	var numberRE = dojo.regexp.buildGroupRE(flags.separator,
		function(sep){
			if(!sep){
				return "(?:0|[1-9]\\d*)";
			}

			sep = dojo.regexp.escapeString(sep);
			if(sep == " "){ sep = "\\s"; }
			else if(sep == "\xa0"){ sep = "\\s\\xa0"; }

			var grp = flags.groupSize, grp2 = flags.groupSize2;
			if(grp2){
				var grp2RE = "(?:0|[1-9]\\d{0," + (grp2-1) + "}(?:[" + sep + "]\\d{" + grp2 + "})*[" + sep + "]\\d{" + grp + "})";
				return ((grp-grp2) > 0) ? "(?:" + grp2RE + "|(?:0|[1-9]\\d{0," + (grp-1) + "}))" : grp2RE;
			}
			return "(?:0|[1-9]\\d{0," + (grp-1) + "}(?:[" + sep + "]\\d{" + grp + "})*)";
		},
		true
	);

	// integer RE
	return signRE + numberRE; // String
}

}

if(!dojo._hasResource["dijit.ProgressBar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.ProgressBar"] = true;
dojo.provide("dijit.ProgressBar");







dojo.declare("dijit.ProgressBar", [dijit._Widget, dijit._Templated], {
	// summary: A progress indication widget
	//
	// example:
	// |	<div dojoType="ProgressBar"
	// |		 places="0"
	// |		 progress="..." maximum="...">
	// |	</div>
	//
	// progress: String (Percentage or Number)
	// 	initial progress value.
	// 	with "%": percentage value, 0% <= progress <= 100%
	// 	or without "%": absolute value, 0 <= progress <= maximum
	progress: "0",

	// maximum: Float
	// 	max sample number
	maximum: 100,

	// places: Number
	// 	number of places to show in values; 0 by default
	places: 0,

	// indeterminate: Boolean
	// 	If false: show progress.
	// 	If true: show that a process is underway but that the progress is unknown
	indeterminate: false,

	templateString:"<div class=\"dijitProgressBar dijitProgressBarEmpty\"\n\t><div waiRole=\"progressbar\" tabindex=\"0\" dojoAttachPoint=\"internalProgress\" class=\"dijitProgressBarFull\"\n\t\t><div class=\"dijitProgressBarTile\"></div\n\t\t><span style=\"visibility:hidden\">&nbsp;</span\n\t></div\n\t><div dojoAttachPoint=\"label\" class=\"dijitProgressBarLabel\" id=\"${id}_label\">&nbsp;</div\n\t><img dojoAttachPoint=\"inteterminateHighContrastImage\" class=\"dijitProgressBarIndeterminateHighContrastImage\"\n\t></img\n></div>\n",

	_indeterminateHighContrastImagePath:
		dojo.moduleUrl("dijit", "themes/a11y/indeterminate_progress.gif"),

	// public functions
	postCreate: function(){
		this.inherited(arguments);
		this.inteterminateHighContrastImage.setAttribute("src",
			this._indeterminateHighContrastImagePath);
		this.update();
	},

	update: function(/*Object?*/attributes){
		// summary: update progress information
		//
		// attributes: may provide progress and/or maximum properties on this parameter,
		//	see attribute specs for details.
		dojo.mixin(this, attributes || {});
		var tip = this.internalProgress;
		var percent = 1, classFunc;
		if(this.indeterminate){
			classFunc = "addClass";
			dijit.removeWaiState(tip, "valuenow");
			dijit.removeWaiState(tip, "valuemin");
			dijit.removeWaiState(tip, "valuemax");
		}else{
			classFunc = "removeClass";
			if(String(this.progress).indexOf("%") != -1){
				percent = Math.min(parseFloat(this.progress)/100, 1);
				this.progress = percent * this.maximum;
			}else{
				this.progress = Math.min(this.progress, this.maximum);
				percent = this.progress / this.maximum;
			}
			var text = this.report(percent);
			this.label.firstChild.nodeValue = text;
			dijit.setWaiState(tip, "describedby", this.label.id);
			dijit.setWaiState(tip, "valuenow", this.progress);
			dijit.setWaiState(tip, "valuemin", 0);
			dijit.setWaiState(tip, "valuemax", this.maximum);
		}
		dojo[classFunc](this.domNode, "dijitProgressBarIndeterminate");
		tip.style.width = (percent * 100) + "%";
		this.onChange();
	},

	report: function(/*float*/percent){
		// summary: Generates message to show; may be overridden by user
		return dojo.number.format(percent, { type: "percent", places: this.places, locale: this.lang });
	},

	onChange: function(){
		// summary: User definable function fired when progress updates.
	}
});

}

if(!dojo._hasResource["dijit.TitlePane"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.TitlePane"] = true;
dojo.provide("dijit.TitlePane");






dojo.declare(
	"dijit.TitlePane",
	[dijit.layout.ContentPane, dijit._Templated],
{
	// summary: A pane with a title on top, that can be opened or collapsed.
	//
	// description: An accessible container with a Title Heading, and a content
	//	section that slides open and closed. TitlePane is an extension to 
	//	ContentPane, providing all the usesful content-control aspects from.
	//
	// example:
	// | 	// load a TitlePane from remote file:
	// |	var foo = new dijit.TitlePane({ href: "foobar.html", title:"Title" });
	// |	foo.startup();
	//
	// example:
	// |	<!-- markup href example: -->
	// |	<div dojoType="dijit.TitlePane" href="foobar.html" title="Title"></div>
	// 
	// example:
	// |	<!-- markup with inline data -->
	// | 	<div dojoType="dijit.TitlePane" title="Title">
	// |		<p>I am content</p>
	// |	</div>

	// title: String
	//		Title of the pane
	title: "",

	// open: Boolean
	//		Whether pane is opened or closed.
	open: true,

	// duration: Integer
	//		Time in milliseconds to fade in/fade out
	duration: dijit.defaultDuration,

	// baseClass: String
	//	The root className to use for the various states of this widget
	baseClass: "dijitTitlePane",

	templateString:"<div class=\"${baseClass}\">\n\t<div dojoAttachEvent=\"onclick:toggle,onkeypress: _onTitleKey,onfocus:_handleFocus,onblur:_handleFocus\" tabindex=\"0\"\n\t\t\twaiRole=\"button\" class=\"dijitTitlePaneTitle\" dojoAttachPoint=\"titleBarNode,focusNode\">\n\t\t<img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"arrowNode\" class=\"dijitArrowNode\" waiRole=\"presentation\"\n\t\t><span dojoAttachPoint=\"arrowNodeInner\" class=\"dijitArrowNodeInner\"></span\n\t\t><span dojoAttachPoint=\"titleNode\" class=\"dijitTitlePaneTextNode\"></span>\n\t</div>\n\t<div class=\"dijitTitlePaneContentOuter\" dojoAttachPoint=\"hideNode\">\n\t\t<div class=\"dijitReset\" dojoAttachPoint=\"wipeNode\">\n\t\t\t<div class=\"dijitTitlePaneContentInner\" dojoAttachPoint=\"containerNode\" waiRole=\"region\" tabindex=\"-1\">\n\t\t\t\t<!-- nested divs because wipeIn()/wipeOut() doesn't work right on node w/padding etc.  Put padding on inner div. -->\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</div>\n",

	attributeMap: dojo.mixin(dojo.clone(dijit.layout.ContentPane.prototype.attributeMap), {
		title: {node: "titleNode", type: "innerHTML" }
	}),

	postCreate: function(){
		if(!this.open){
			this.hideNode.style.display = this.wipeNode.style.display = "none";
		}
		this._setCss();
		dojo.setSelectable(this.titleNode, false);
		dijit.setWaiState(this.containerNode, "labelledby", this.titleNode.id);
		dijit.setWaiState(this.focusNode, "haspopup", "true");

		// setup open/close animations
		var hideNode = this.hideNode, wipeNode = this.wipeNode;
		this._wipeIn = dojo.fx.wipeIn({
			node: this.wipeNode,
			duration: this.duration,
			beforeBegin: function(){
				hideNode.style.display="";
			}
		});
		this._wipeOut = dojo.fx.wipeOut({
			node: this.wipeNode,
			duration: this.duration,
			onEnd: function(){
				hideNode.style.display="none";
			}
		});
		this.inherited(arguments);
	},

	_setOpenAttr: function(/* Boolean */ open){
		// summary:
		//		Hook to make attr("open", boolean) control the open/closed state of the pane.
		// open: Boolean
		//		True if you want to open the pane, false if you want to close it.
		if(this.open !== open){ this.toggle(); }
	},

	_setContentAttr: function(content){
		// summary:
		//		Hook to make attr("content", ...) work.
		// 		Typically called when an href is loaded.  Our job is to make the animation smooth

		if(!this.open || !this._wipeOut || this._wipeOut.status() == "playing"){
			// we are currently *closing* the pane (or the pane is closed), so just let that continue
			this.inherited(arguments);
		}else{
			if(this._wipeIn && this._wipeIn.status() == "playing"){
				this._wipeIn.stop();
			}

			// freeze container at current height so that adding new content doesn't make it jump
			dojo.marginBox(this.wipeNode, { h: dojo.marginBox(this.wipeNode).h });

			// add the new content (erasing the old content, if any)
			this.inherited(arguments);

			// call _wipeIn.play() to animate from current height to new height
			if(this._wipeIn){
				this._wipeIn.play();
			}else{
				this.hideNode.style.display = "";
			}
		}
	},

	toggle: function(){
		// summary: switches between opened and closed state
		dojo.forEach([this._wipeIn, this._wipeOut], function(animation){
			if(animation && animation.status() == "playing"){
				animation.stop();
			}
		});

		var anim = this[this.open ? "_wipeOut" : "_wipeIn"]
		if(anim){
			anim.play();
		}else{
			this._hideNode.style.display = this.open ? "" : "none";
		}
		this.open =! this.open;

		// load content (if this is the first time we are opening the TitlePane
		// and content is specified as an href, or href was set when hidden)
		this._loadCheck();

		this._setCss();
	},

	_setCss: function(){
		// summary: set the open/close css state for the TitlePane
		var classes = ["dijitClosed", "dijitOpen"];
		var boolIndex = this.open;
		var node = this.titleBarNode || this.focusNode;
		dojo.removeClass(node, classes[!boolIndex+0]);
		node.className += " " + classes[boolIndex+0];

		// provide a character based indicator for images-off mode
		this.arrowNodeInner.innerHTML = this.open ? "-" : "+";
	},

	_onTitleKey: function(/*Event*/ e){
		// summary: callback when user hits a key
		if(e.charOrCode == dojo.keys.ENTER || e.charOrCode == ' '){
			this.toggle();
		}else if(e.charOrCode == dojo.keys.DOWN_ARROW && this.open){
			this.containerNode.focus();
			e.preventDefault();
	 	}
	},
	
	_handleFocus: function(/*Event*/ e){
		// summary: handle blur and focus for this widget
		
		// add/removeClass is safe to call without hasClass in this case
		dojo[(e.type == "focus" ? "addClass" : "removeClass")](this.focusNode, this.baseClass + "Focused");
	},

	setTitle: function(/*String*/ title){
		// summary: sets the text of the title
		dojo.deprecated("dijit.TitlePane.setTitle() is deprecated.  Use attr('title', ...) instead.", "", "2.0");
		this.titleNode.innerHTML = title;
	}
});

}

if(!dojo._hasResource["dijit.Tooltip"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Tooltip"] = true;
dojo.provide("dijit.Tooltip");




dojo.declare(
	"dijit._MasterTooltip",
	[dijit._Widget, dijit._Templated],
	{
		// summary
		//		Internal widget that holds the actual tooltip markup,
		//		which occurs once per page.
		//		Called by Tooltip widgets which are just containers to hold
		//		the markup

		// duration: Integer
		//		Milliseconds to fade in/fade out
		duration: dijit.defaultDuration,

		templateString:"<div class=\"dijitTooltip dijitTooltipLeft\" id=\"dojoTooltip\">\n\t<div class=\"dijitTooltipContainer dijitTooltipContents\" dojoAttachPoint=\"containerNode\" waiRole='alert'></div>\n\t<div class=\"dijitTooltipConnector\"></div>\n</div>\n",

		postCreate: function(){
			dojo.body().appendChild(this.domNode);

			this.bgIframe = new dijit.BackgroundIframe(this.domNode);

			// Setup fade-in and fade-out functions.
			this.fadeIn = dojo.fadeIn({ node: this.domNode, duration: this.duration, onEnd: dojo.hitch(this, "_onShow") });
			this.fadeOut = dojo.fadeOut({ node: this.domNode, duration: this.duration, onEnd: dojo.hitch(this, "_onHide") });

		},

		show: function(/*String*/ innerHTML, /*DomNode*/ aroundNode, /*String[]?*/ position){
			// summary:
			//	Display tooltip w/specified contents to right specified node
			//	(To left if there's no space on the right, or if LTR==right)

			if(this.aroundNode && this.aroundNode === aroundNode){
				return;
			}

			if(this.fadeOut.status() == "playing"){
				// previous tooltip is being hidden; wait until the hide completes then show new one
				this._onDeck=arguments;
				return;
			}
			this.containerNode.innerHTML=innerHTML;

			// Firefox bug. when innerHTML changes to be shorter than previous
			// one, the node size will not be updated until it moves.
			this.domNode.style.top = (this.domNode.offsetTop + 1) + "px";

			// position the element and change CSS according to position[] (a list of positions to try)
			var align = {};
			var ltr = this.isLeftToRight();
			dojo.forEach( (position && position.length) ? position : dijit.Tooltip.defaultPosition, function(pos){
				switch(pos){
					case "after":				
						align[ltr ? "BR" : "BL"] = ltr ? "BL" : "BR";
						break;
					case "before":
						align[ltr ? "BL" : "BR"] = ltr ? "BR" : "BL";
						break;
					case "below":
						// first try to align left borders, next try to align right borders (or reverse for RTL mode)
						align[ltr ? "BL" : "BR"] = ltr ? "TL" : "TR";
						align[ltr ? "BR" : "BL"] = ltr ? "TR" : "TL";
						break;
					case "above":
					default:
						// first try to align left borders, next try to align right borders (or reverse for RTL mode)
						align[ltr ? "TL" : "TR"] = ltr ? "BL" : "BR";
						align[ltr ? "TR" : "TL"] = ltr ? "BR" : "BL";
						break;
				}
			});
			var pos = dijit.placeOnScreenAroundElement(this.domNode, aroundNode, align, dojo.hitch(this, "orient"));

			// show it
			dojo.style(this.domNode, "opacity", 0);
			this.fadeIn.play();
			this.isShowingNow = true;
			this.aroundNode = aroundNode;
		},

		orient: function(/* DomNode */ node, /* String */ aroundCorner, /* String */ tooltipCorner){
			// summary: private function to set CSS for tooltip node based on which position it's in
			node.className = "dijitTooltip " +
				{
					"BL-TL": "dijitTooltipBelow dijitTooltipABLeft",
					"TL-BL": "dijitTooltipAbove dijitTooltipABLeft",
					"BR-TR": "dijitTooltipBelow dijitTooltipABRight",
					"TR-BR": "dijitTooltipAbove dijitTooltipABRight",
					"BR-BL": "dijitTooltipRight",
					"BL-BR": "dijitTooltipLeft"
				}[aroundCorner + "-" + tooltipCorner];
		},

		_onShow: function(){
			if(dojo.isIE){
				// the arrow won't show up on a node w/an opacity filter
				this.domNode.style.filter="";
			}
		},

		hide: function(aroundNode){
			// summary: hide the tooltip
			if(this._onDeck && this._onDeck[1] == aroundNode){
				// this hide request is for a show() that hasn't even started yet;
				// just cancel the pending show()
				this._onDeck=null;
			}else if(this.aroundNode === aroundNode){
				// this hide request is for the currently displayed tooltip
				this.fadeIn.stop();
				this.isShowingNow = false;
				this.aroundNode = null;
				this.fadeOut.play();
			}else{
				// just ignore the call, it's for a tooltip that has already been erased
			}
		},

		_onHide: function(){
			this.domNode.style.cssText="";	// to position offscreen again
			if(this._onDeck){
				// a show request has been queued up; do it now
				this.show.apply(this, this._onDeck);
				this._onDeck=null;
			}
		}

	}
);

dijit.showTooltip = function(/*String*/ innerHTML, /*DomNode*/ aroundNode, /*String[]?*/ position){
	// summary:
	//	Display tooltip w/specified contents in specified position.
	//	See description of dijit.Tooltip.defaultPosition for details on position parameter.
	//	If position is not specified then dijit.Tooltip.defaultPosition is used.
	if(!dijit._masterTT){ dijit._masterTT = new dijit._MasterTooltip(); }
	return dijit._masterTT.show(innerHTML, aroundNode, position);
};

dijit.hideTooltip = function(aroundNode){
	// summary: hide the tooltip
	if(!dijit._masterTT){ dijit._masterTT = new dijit._MasterTooltip(); }
	return dijit._masterTT.hide(aroundNode);
};

dojo.declare(
	"dijit.Tooltip",
	dijit._Widget,
	{
		// summary
		//		Pops up a tooltip (a help message) when you hover over a node.

		// label: String
		//		Text to display in the tooltip.
		//		Specified as innerHTML when creating the widget from markup.
		label: "",

		// showDelay: Integer
		//		Number of milliseconds to wait after hovering over/focusing on the object, before
		//		the tooltip is displayed.
		showDelay: 400,

		// connectId: String[]
		//		Id(s) of domNodes to attach the tooltip to.
		//		When user hovers over any of the specified dom nodes, the tooltip will appear.
		connectId: [],

		//	position: String[]
		//		See description of dijit.Tooltip.defaultPosition for details on position parameter.
		position: [],

		postCreate: function(){
			
			dojo.addClass(this.domNode,"dijitTooltipData");

			this._connectNodes = [];
			
			dojo.forEach(this.connectId, function(id) {
				var node = dojo.byId(id);
				if (node) {
					this._connectNodes.push(node);
					dojo.forEach(["onMouseEnter", "onMouseLeave", "onFocus", "onBlur"], function(event){
						this.connect(node, event.toLowerCase(), "_"+event);
					}, this);
					if(dojo.isIE){
						// BiDi workaround
						node.style.zoom = 1;
					}
				}
			}, this);
		},

		_onMouseEnter: function(/*Event*/ e){
			this._onHover(e);
		},

		_onMouseLeave: function(/*Event*/ e){
			this._onUnHover(e);
		},

		_onFocus: function(/*Event*/ e){
			this._focus = true;
			this._onHover(e);
			this.inherited(arguments);
		},
		
		_onBlur: function(/*Event*/ e){
			this._focus = false;
			this._onUnHover(e);
			this.inherited(arguments);
		},

		_onHover: function(/*Event*/ e){
			if(!this._showTimer){
				var target = e.target;
				this._showTimer = setTimeout(dojo.hitch(this, function(){this.open(target)}), this.showDelay);
			}
		},

		_onUnHover: function(/*Event*/ e){
			// keep a tooltip open if the associated element has focus
			if(this._focus){ return; }
			if(this._showTimer){
				clearTimeout(this._showTimer);
				delete this._showTimer;
			}
			this.close();
		},

		open: function(/*DomNode*/ target){
 			// summary: display the tooltip; usually not called directly.
			target = target || this._connectNodes[0];
			if(!target){ return; }

			if(this._showTimer){
				clearTimeout(this._showTimer);
				delete this._showTimer;
			}
			dijit.showTooltip(this.label || this.domNode.innerHTML, target, this.position);
			
			this._connectNode = target;
		},

		close: function(){
			// summary: hide the tooltip or cancel timer for show of tooltip
			if(this._connectNode){
				// if tooltip is currently shown
				dijit.hideTooltip(this._connectNode);
				delete this._connectNode;
			}
			if(this._showTimer){
				// if tooltip is scheduled to be shown (after a brief delay)
				clearTimeout(this._showTimer);
				delete this._showTimer;
			}
		},

		uninitialize: function(){
			this.close();
		}
	}
);

// dijit.Tooltip.defaultPosition: String[]
//		This variable controls the position of tooltips, if the position is not specified to
//		the Tooltip widget or *TextBox widget itself.  It's an array of strings with the following values:
//
//			* before: places tooltip to the left of the target node/widget, or to the right in
//			  the case of RTL scripts like Hebrew and Arabic
//			* after: places tooltip to the right of the target node/widget, or to the left in
//			  the case of RTL scripts like Hebrew and Arabic
//			* above: tooltip goes above target node
//			* below: tooltip goes below target node
//
//		The list is positions is tried, in order, until a position is found where the tooltip fits
//		within the viewport.
//
//		Be careful setting this parameter.  A value of "above" may work fine until the user scrolls
//		the screen so that there's no room above the target node.   Nodes with drop downs, like
//		DropDownButton or FilteringSelect, are especially problematic, in that you need to be sure
//		that the drop down and tooltip don't overlap, even when the viewport is scrolled so that there
//		is only room below (or above) the target node, but not both.
dijit.Tooltip.defaultPosition = ["after", "before"];

}

if(!dojo._hasResource["dojo.cookie"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.cookie"] = true;
dojo.provide("dojo.cookie");



/*=====
dojo.__cookieProps = function(){
	//	expires: Date|String|Number?
	//		If a number, the number of days from today at which the cookie
	//		will expire. If a date, the date past which the cookie will expire.
	//		If expires is in the past, the cookie will be deleted.
	//		If expires is omitted or is 0, the cookie will expire when the browser closes. << FIXME: 0 seems to disappear right away? FF3.
	//	path: String?
	//		The path to use for the cookie.
	//	domain: String?
	//		The domain to use for the cookie.
	//	secure: Boolean?
	//		Whether to only send the cookie on secure connections
	this.expires = expires;
	this.path = path;
	this.domain = domain;
	this.secure = secure;
}
=====*/


dojo.cookie = function(/*String*/name, /*String?*/value, /*dojo.__cookieProps?*/props){
	//	summary: 
	//		Get or set a cookie.
	//	description:
	// 		If one argument is passed, returns the value of the cookie
	// 		For two or more arguments, acts as a setter.
	//	name:
	//		Name of the cookie
	//	value:
	//		Value for the cookie
	//	props: 
	//		Properties for the cookie
	//	example:
	//		set a cookie with the JSON-serialized contents of an object which
	//		will expire 5 days from now:
	//	|	dojo.cookie("configObj", dojo.toJson(config), { expires: 5 });
	//	
	//	example:
	//		de-serialize a cookie back into a JavaScript object:
	//	|	var config = dojo.fromJson(dojo.cookie("configObj"));
	//	
	//	example:
	//		delete a cookie:
	//	|	dojo.cookie("configObj", null, {expires: -1});
	var c = document.cookie;
	if(arguments.length == 1){
		var matches = c.match(new RegExp("(?:^|; )" + dojo.regexp.escapeString(name) + "=([^;]*)"));
		return matches ? decodeURIComponent(matches[1]) : undefined; // String or undefined
	}else{
		props = props || {};
// FIXME: expires=0 seems to disappear right away, not on close? (FF3)  Change docs?
		var exp = props.expires;
		if(typeof exp == "number"){ 
			var d = new Date();
			d.setTime(d.getTime() + exp*24*60*60*1000);
			exp = props.expires = d;
		}
		if(exp && exp.toUTCString){ props.expires = exp.toUTCString(); }

		value = encodeURIComponent(value);
		var updatedCookie = name + "=" + value, propName;
		for(propName in props){
			updatedCookie += "; " + propName;
			var propValue = props[propName];
			if(propValue !== true){ updatedCookie += "=" + propValue; }
		}
		document.cookie = updatedCookie;
	}
};

dojo.cookie.isSupported = function(){
	//	summary:
	//		Use to determine if the current browser supports cookies or not.
	//		
	//		Returns true if user allows cookies.
	//		Returns false if user doesn't allow cookies.

	if(!("cookieEnabled" in navigator)){
		this("__djCookieTest__", "CookiesAllowed");
		navigator.cookieEnabled = this("__djCookieTest__") == "CookiesAllowed";
		if(navigator.cookieEnabled){
			this("__djCookieTest__", "", {expires: -1});
		}
	}
	return navigator.cookieEnabled;
};

}

if(!dojo._hasResource["dijit.Tree"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.Tree"] = true;
dojo.provide("dijit.Tree");








dojo.declare(
	"dijit._TreeNode",
	[dijit._Widget, dijit._Templated, dijit._Container, dijit._Contained],
{
	// summary:
	//		Single node within a tree

	// item: dojo.data.Item
	//		the dojo.data entry this tree represents
	item: null,	

	isTreeNode: true,

	// label: String
	//		Text of this tree node
	label: "",
	
	isExpandable: null, // show expando node
	
	isExpanded: false,

	// state: String
	//		dynamic loading-related stuff.
	//		When an empty folder node appears, it is "UNCHECKED" first,
	//		then after dojo.data query it becomes "LOADING" and, finally "LOADED"	
	state: "UNCHECKED",
	
	templateString:"<div class=\"dijitTreeNode\" waiRole=\"presentation\"\n\t><div dojoAttachPoint=\"rowNode\" class=\"dijitTreeRow\" waiRole=\"presentation\"\n\t\t><img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"expandoNode\" class=\"dijitTreeExpando\" waiRole=\"presentation\"\n\t\t><span dojoAttachPoint=\"expandoNodeText\" class=\"dijitExpandoText\" waiRole=\"presentation\"\n\t\t></span\n\t\t><span dojoAttachPoint=\"contentNode\" dojoAttachEvent=\"onmouseenter:_onMouseEnter, onmouseleave:_onMouseLeave\"\n\t\t\tclass=\"dijitTreeContent\" waiRole=\"presentation\">\n\t\t\t<img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"iconNode\" class=\"dijitTreeIcon\" waiRole=\"presentation\"\n\t\t\t><span dojoAttachPoint=\"labelNode\" class=\"dijitTreeLabel\" wairole=\"treeitem\" tabindex=\"-1\" waiState=\"selected-false\" dojoAttachEvent=\"onfocus:_onNodeFocus\"></span>\n\t\t</span\n\t></div>\n\t<div dojoAttachPoint=\"containerNode\" class=\"dijitTreeContainer\" waiRole=\"presentation\" style=\"display: none;\"></div>\n</div>\n",		

	postCreate: function(){
		// set label, escaping special characters
		this.setLabelNode(this.label);

		// set expand icon for leaf
		this._setExpando();

		// set icon and label class based on item
		this._updateItemClasses(this.item);

		if(this.isExpandable){
			dijit.setWaiState(this.labelNode, "expanded", this.isExpanded);
		}
	},

	markProcessing: function(){
		// summary: visually denote that tree is loading data, etc.
		this.state = "LOADING";
		this._setExpando(true);	
	},

	unmarkProcessing: function(){
		// summary: clear markup from markProcessing() call
		this._setExpando(false);	
	},

	_updateItemClasses: function(item){
		// summary: set appropriate CSS classes for icon and label dom node (used to allow for item updates to change respective CSS)
		var tree = this.tree, model = tree.model;
		if(tree._v10Compat && item === model.root){
			// For back-compat with 1.0, need to use null to specify root item (TODO: remove in 2.0)
			item = null;
		}
		this.iconNode.className = "dijitTreeIcon " + tree.getIconClass(item, this.isExpanded);
		this.labelNode.className = "dijitTreeLabel " + tree.getLabelClass(item, this.isExpanded);
	},

	_updateLayout: function(){
		// summary: set appropriate CSS classes for this.domNode
		var parent = this.getParent();
		if(!parent || parent.rowNode.style.display == "none"){
			/* if we are hiding the root node then make every first level child look like a root node */
			dojo.addClass(this.domNode, "dijitTreeIsRoot");
		}else{
			dojo.toggleClass(this.domNode, "dijitTreeIsLast", !this.getNextSibling());
		}
	},

	_setExpando: function(/*Boolean*/ processing){
		// summary: set the right image for the expando node

		// apply the appropriate class to the expando node
		var styles = ["dijitTreeExpandoLoading", "dijitTreeExpandoOpened",
			"dijitTreeExpandoClosed", "dijitTreeExpandoLeaf"];
		var _a11yStates = ["*","-","+","*"];
		
		var idx = processing ? 0 : (this.isExpandable ?	(this.isExpanded ? 1 : 2) : 3);
		dojo.forEach(styles,
			function(s){
				dojo.removeClass(this.expandoNode, s);
			}, this
		);
		dojo.addClass(this.expandoNode, styles[idx]);

		// provide a non-image based indicator for images-off mode
		this.expandoNodeText.innerHTML = _a11yStates[idx];

	},	

	expand: function(){
		// summary: show my children
		if(this.isExpanded){ return; }
		// cancel in progress collapse operation

		this._wipeOut && this._wipeOut.stop();

		this.isExpanded = true;
		dijit.setWaiState(this.labelNode, "expanded", "true");
		dijit.setWaiRole(this.containerNode, "group");
		this.contentNode.className = "dijitTreeContent dijitTreeContentExpanded";
		this._setExpando();
		this._updateItemClasses(this.item);

		if(!this._wipeIn){
			this._wipeIn = dojo.fx.wipeIn({
				node: this.containerNode, duration: dijit.defaultDuration
			});
		}
		this._wipeIn.play();
	},

	collapse: function(){					
		if(!this.isExpanded){ return; }

		// cancel in progress expand operation
		this._wipeIn && this._wipeIn.stop();

		this.isExpanded = false;
		dijit.setWaiState(this.labelNode, "expanded", "false");
		this.contentNode.className = "dijitTreeContent";
		this._setExpando();
		this._updateItemClasses(this.item);

		if(!this._wipeOut){
			this._wipeOut = dojo.fx.wipeOut({
				node: this.containerNode, duration: dijit.defaultDuration
			});
		}
		this._wipeOut.play();
	},

	setLabelNode: function(label){
		this.labelNode.innerHTML = "";
		this.labelNode.appendChild(dojo.doc.createTextNode(label));
	},

	setChildItems: function(/* Object[] */ items){
		// summary:
		//		Sets the child items of this node, removing/adding nodes
		//		from current children to match specified items[] array.

		var tree = this.tree,
			model = tree.model;

		// Orphan all my existing children.
		// If items contains some of the same items as before then we will reattach them.
		// Don't call this.removeChild() because that will collapse the tree etc.
		this.getChildren().forEach(function(child){
			dijit._Container.prototype.removeChild.call(this, child);
		}, this);

		this.state = "LOADED";

		if(items && items.length > 0){
			this.isExpandable = true;

			// Create _TreeNode widget for each specified tree node, unless one already
			// exists and isn't being used (presumably it's from a DnD move and was recently
			// released
			dojo.forEach(items, function(item){
				var id = model.getIdentity(item),
					existingNode = tree._itemNodeMap[id],
					node = 
						( existingNode && !existingNode.getParent() ) ?
						existingNode :
						this.tree._createTreeNode({
							item: item,
							tree: tree,
							isExpandable: model.mayHaveChildren(item),
							label: tree.getLabel(item)
						});
				this.addChild(node);
				// note: this won't work if there are two nodes for one item (multi-parented items); will be fixed later
				tree._itemNodeMap[id] = node;
				if(this.tree.persist){
					if(tree._openedItemIds[id]){
						tree._expandNode(node);
					}
				}
			}, this);

			// note that updateLayout() needs to be called on each child after
			// _all_ the children exist
			dojo.forEach(this.getChildren(), function(child, idx){
				child._updateLayout();
			});
		}else{
			this.isExpandable=false;
		}

		if(this._setExpando){
			// change expando to/from dot or + icon, as appropriate
			this._setExpando(false);
		}

		// On initial tree show, put focus on either the root node of the tree,
		// or the first child, if the root node is hidden
		if(this == tree.rootNode){
			var fc = this.tree.showRoot ? this : this.getChildren()[0],
				tabnode = fc ? fc.labelNode : this.domNode;
			tabnode.setAttribute("tabIndex", "0");
			tree.lastFocused = fc;
		}
	},

	removeChild: function(/* treeNode */ node){
		this.inherited(arguments);

		var children = this.getChildren();		
		if(children.length == 0){
			this.isExpandable = false;
			this.collapse();
		}

		dojo.forEach(children, function(child){
				child._updateLayout();
		});
	},

	makeExpandable: function(){
		//summary:
		//		if this node wasn't already showing the expando node,
		//		turn it into one and call _setExpando()
		this.isExpandable = true;
		this._setExpando(false);
	},

	_onNodeFocus: function(evt){
		var node = dijit.getEnclosingWidget(evt.target);
		this.tree._onTreeFocus(node);
	},
	
	_onMouseEnter: function(evt){
		dojo.addClass(this.contentNode, "dijitTreeNodeHover");
	},

	_onMouseLeave: function(evt){
		dojo.removeClass(this.contentNode, "dijitTreeNodeHover");
	}
});

dojo.declare(
	"dijit.Tree",
	[dijit._Widget, dijit._Templated],
{
	// summary:
	//	This widget displays hierarchical data from a store.  A query is specified
	//	to get the "top level children" from a data store, and then those items are
	//	queried for their children and so on (but lazily, as the user clicks the expand node).
	//
	//	Thus in the default mode of operation this widget is technically a forest, not a tree,
	//	in that there can be multiple "top level children".  However, if you specify label,
	//	then a special top level node (not corresponding to any item in the datastore) is
	//	created, to father all the top level children.

	// store: String||dojo.data.Store
	//	The store to get data to display in the tree.
	//	May remove for 2.0 in favor of "model".
	store: null,

	// model: dijit.Tree.model
	//	Alternate interface from store to access data (and changes to data) in the tree
	model: null,

	// query: anything
	//	Specifies datastore query to return the root item for the tree.
	//
	//	Deprecated functionality: if the query returns multiple items, the tree is given
	//	a fake root node (not corresponding to any item in the data store), 
	//	whose children are the items that match this query.
	//
	//	The root node is shown or hidden based on whether a label is specified.
	//
	//	Having a query return multiple items is deprecated.
	//	If your store doesn't have a root item, wrap the store with
	//	dijit.tree.ForestStoreModel, and specify model=myModel
	//
	// example:
	//		{type:'continent'}
	query: null,

	// label: String
	//	Deprecated.  Use dijit.tree.ForestStoreModel directly instead.
	//	Used in conjunction with query parameter.
	//	If a query is specified (rather than a root node id), and a label is also specified,
	//	then a fake root node is created and displayed, with this label.
	label: "",

	// showRoot: Boolean
	//	Should the root node be displayed, or hidden?
	showRoot: true,

	// childrenAttr: String[]
	//		one ore more attributes that holds children of a tree node
	childrenAttr: ["children"],

	// openOnClick: Boolean
	//		If true, clicking a folder node's label will open it, rather than calling onClick()
	openOnClick: false,

	templateString:"<div class=\"dijitTreeContainer\" waiRole=\"tree\"\n\tdojoAttachEvent=\"onclick:_onClick,onkeypress:_onKeyPress\">\n</div>\n",		

	isExpandable: true,

	isTree: true,

	// persist: Boolean
	//	enables/disables use of cookies for state saving.
	persist: true,
	
	// dndController: String
	//	class name to use as as the dnd controller
	dndController: null,

	//parameters to pull off of the tree and pass on to the dndController as its params
	dndParams: ["onDndDrop","itemCreator","onDndCancel","checkAcceptance", "checkItemAcceptance", "dragThreshold"],

	//declare the above items so they can be pulled from the tree's markup
	onDndDrop:null,
	itemCreator:null,
	onDndCancel:null,
	checkAcceptance:null,	
	checkItemAcceptance:null,
	dragThreshold:0,

	_publish: function(/*String*/ topicName, /*Object*/ message){
		// summary:
		//		Publish a message for this widget/topic
		dojo.publish(this.id, [dojo.mixin({tree: this, event: topicName}, message||{})]);
	},

	postMixInProperties: function(){
		this.tree = this;

		this._itemNodeMap={};

		if(!this.cookieName){
			this.cookieName = this.id + "SaveStateCookie";
		}
	},

	postCreate: function(){
		// load in which nodes should be opened automatically
		if(this.persist){
			var cookie = dojo.cookie(this.cookieName);
			this._openedItemIds = {};
			if(cookie){
				dojo.forEach(cookie.split(','), function(item){
					this._openedItemIds[item] = true;
				}, this);
			}
		}
		
		// Create glue between store and Tree, if not specified directly by user
		if(!this.model){
			this._store2model();
		}

		// monitor changes to items
		this.connect(this.model, "onChange", "_onItemChange");
		this.connect(this.model, "onChildrenChange", "_onItemChildrenChange");
		this.connect(this.model, "onDelete", "_onItemDelete");

		this._load();

		this.inherited(arguments);

		if(this.dndController){
			if(dojo.isString(this.dndController)){
				this.dndController = dojo.getObject(this.dndController);
			}	
			var params={};
			for (var i=0; i<this.dndParams.length;i++){
				if(this[this.dndParams[i]]){
					params[this.dndParams[i]] = this[this.dndParams[i]];
				}
			}
			this.dndController = new this.dndController(this, params);
		}
	},

	_store2model: function(){
		// summary: user specified a store&query rather than model, so create model from store/query
		this._v10Compat = true;
		dojo.deprecated("Tree: from version 2.0, should specify a model object rather than a store/query");

		var modelParams = {
			id: this.id + "_ForestStoreModel",
			store: this.store,
			query: this.query,
			childrenAttrs: this.childrenAttr
		};

		// Only override the model's mayHaveChildren() method if the user has specified an override
		if(this.params.mayHaveChildren){
			modelParams.mayHaveChildren = dojo.hitch(this, "mayHaveChildren");
		}
					
		if(this.params.getItemChildren){
			modelParams.getChildren = dojo.hitch(this, function(item, onComplete, onError){
				this.getItemChildren((this._v10Compat && item === this.model.root) ? null : item, onComplete, onError);
			});
		}
		this.model = new dijit.tree.ForestStoreModel(modelParams);
		
		// For backwards compatibility, the visibility of the root node is controlled by
		// whether or not the user has specified a label
		this.showRoot = Boolean(this.label);
	},

	_load: function(){
		// summary: initial load of the tree
		// load root node (possibly hidden) and it's children
		this.model.getRoot(
			dojo.hitch(this, function(item){
				var rn = this.rootNode = this.tree._createTreeNode({
					item: item,
					tree: this,
					isExpandable: true,
					label: this.label || this.getLabel(item)
				});
				if(!this.showRoot){
					rn.rowNode.style.display="none";
				}
				this.domNode.appendChild(rn.domNode);
				this._itemNodeMap[this.model.getIdentity(item)] = rn;

				rn._updateLayout();		// sets "dijitTreeIsRoot" CSS classname

				// load top level children
				this._expandNode(rn);
			}),
			function(err){
				console.error(this, ": error loading root: ", err);
			}
		);
	},

	////////////// Data store related functions //////////////////////
	// These just get passed to the model; they are here for back-compat

	mayHaveChildren: function(/*dojo.data.Item*/ item){
		// summary:
		//		User overridable function to tell if an item has or may have children.
		//		Controls whether or not +/- expando icon is shown.
		//		(For efficiency reasons we may not want to check if an element actually
		//		has children until user clicks the expando node)
	},

	getItemChildren: function(/*dojo.data.Item*/ parentItem, /*function(items)*/ onComplete){
		// summary:
		// 		User overridable function that return array of child items of given parent item,
		//		or if parentItem==null then return top items in tree
	},

	///////////////////////////////////////////////////////
	// Functions for converting an item to a TreeNode
	getLabel: function(/*dojo.data.Item*/ item){
		// summary: user overridable function to get the label for a tree node (given the item)
		return this.model.getLabel(item);	// String
	},

	getIconClass: function(/*dojo.data.Item*/ item, /*Boolean*/ opened){
		// summary: user overridable function to return CSS class name to display icon
		return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
	},

	getLabelClass: function(/*dojo.data.Item*/ item, /*Boolean*/ opened){
		// summary: user overridable function to return CSS class name to display label
	},

	/////////// Keyboard and Mouse handlers ////////////////////

	_onKeyPress: function(/*Event*/ e){
		// summary: translates keypress events into commands for the controller
		if(e.altKey){ return; }
		var dk = dojo.keys;
		var treeNode = dijit.getEnclosingWidget(e.target);
		if(!treeNode){ return; }

		var key = e.charOrCode;
		if(typeof key == "string"){  // handle printables (letter navigation)
			// Check for key navigation.
			if(!e.altKey && !e.ctrlKey && !e.shiftKey && !e.metaKey){
				this._onLetterKeyNav( { node: treeNode, key: key.toLowerCase() } );
				dojo.stopEvent(e);
			}
		}else{  // handle non-printables (arrow keys)
			var map = this._keyHandlerMap;
			if(!map){
				// setup table mapping keys to events
				map = {};
				map[dk.ENTER]="_onEnterKey";
				map[this.isLeftToRight() ? dk.LEFT_ARROW : dk.RIGHT_ARROW]="_onLeftArrow";
				map[this.isLeftToRight() ? dk.RIGHT_ARROW : dk.LEFT_ARROW]="_onRightArrow";
				map[dk.UP_ARROW]="_onUpArrow";
				map[dk.DOWN_ARROW]="_onDownArrow";
				map[dk.HOME]="_onHomeKey";
				map[dk.END]="_onEndKey";
				this._keyHandlerMap = map;
			}
			if(this._keyHandlerMap[key]){
				this[this._keyHandlerMap[key]]( { node: treeNode, item: treeNode.item } );	
				dojo.stopEvent(e);
			}
		}
	},

	_onEnterKey: function(/*Object*/ message){
		this._publish("execute", { item: message.item, node: message.node} );
		this.onClick(message.item, message.node);
	},

	_onDownArrow: function(/*Object*/ message){
		// summary: down arrow pressed; get next visible node, set focus there
		var node = this._getNextNode(message.node);
		if(node && node.isTreeNode){
			this.focusNode(node);
		}	
	},

	_onUpArrow: function(/*Object*/ message){
		// summary: up arrow pressed; move to previous visible node

		var node = message.node;

		// if younger siblings		
		var previousSibling = node.getPreviousSibling();
		if(previousSibling){
			node = previousSibling;
			// if the previous node is expanded, dive in deep
			while(node.isExpandable && node.isExpanded && node.hasChildren()){
				// move to the last child
				var children = node.getChildren();
				node = children[children.length-1];
			}
		}else{
			// if this is the first child, return the parent
			// unless the parent is the root of a tree with a hidden root
			var parent = node.getParent();
			if(!(!this.showRoot && parent === this.rootNode)){
				node = parent;
			}
		}

		if(node && node.isTreeNode){
			this.focusNode(node);
		}
	},

	_onRightArrow: function(/*Object*/ message){
		// summary: right arrow pressed; go to child node
		var node = message.node;

		// if not expanded, expand, else move to 1st child
		if(node.isExpandable && !node.isExpanded){
			this._expandNode(node);
		}else if(node.hasChildren()){
			node = node.getChildren()[0];
			if(node && node.isTreeNode){
				this.focusNode(node);
			}
		}
	},

	_onLeftArrow: function(/*Object*/ message){
		// summary:
		//		Left arrow pressed.
		//		If not collapsed, collapse, else move to parent.

		var node = message.node;

		if(node.isExpandable && node.isExpanded){
			this._collapseNode(node);
		}else{
			var parent = node.getParent();
			if(parent && parent.isTreeNode && !(!this.showRoot && parent === this.rootNode)){
				this.focusNode(parent);
			}
		}
	},

	_onHomeKey: function(){
		// summary: home pressed; get first visible node, set focus there
		var node = this._getRootOrFirstNode();
		if(node){
			this.focusNode(node);
		}
	},

	_onEndKey: function(/*Object*/ message){
		// summary: end pressed; go to last visible node

		var node = this;
		while(node.isExpanded){
			var c = node.getChildren();
			node = c[c.length - 1];
		}

		if(node && node.isTreeNode){
			this.focusNode(node);
		}
	},

	_onLetterKeyNav: function(message){
		// summary: letter key pressed; search for node starting with first char = key
		var node = message.node, startNode = node, 
			key = message.key;
		do{
			node = this._getNextNode(node);
			//check for last node, jump to first node if necessary
			if(!node){
				node = this._getRootOrFirstNode();
			}
		}while(node !== startNode && (node.label.charAt(0).toLowerCase() != key));
		if(node && node.isTreeNode){
			// no need to set focus if back where we started
			if(node !== startNode){
				this.focusNode(node);
			}
		}
	},

	_onClick: function(/*Event*/ e){
		// summary: translates click events into commands for the controller to process
		var domElement = e.target;

		// find node
		var nodeWidget = dijit.getEnclosingWidget(domElement);	
		if(!nodeWidget || !nodeWidget.isTreeNode){
			return;
		}

		if( (this.openOnClick && nodeWidget.isExpandable) ||
			(domElement == nodeWidget.expandoNode || domElement == nodeWidget.expandoNodeText) ){
			// expando node was clicked, or label of a folder node was clicked; open it
			if(nodeWidget.isExpandable){
				this._onExpandoClick({node:nodeWidget});
			}
		}else{
			this._publish("execute", { item: nodeWidget.item, node: nodeWidget} );
			this.onClick(nodeWidget.item, nodeWidget);
			this.focusNode(nodeWidget);
		}
		dojo.stopEvent(e);
	},

	_onExpandoClick: function(/*Object*/ message){
		// summary: user clicked the +/- icon; expand or collapse my children.
		var node = message.node;
		
		// If we are collapsing, we might be hiding the currently focused node.
		// Also, clicking the expando node might have erased focus from the current node.
		// For simplicity's sake just focus on the node with the expando.
		this.focusNode(node);

		if(node.isExpanded){
			this._collapseNode(node);
		}else{
			this._expandNode(node);
		}
	},

	onClick: function(/* dojo.data */ item, /*TreeNode*/ node){
		// summary: user overridable function for executing a tree item
	},
	onOpen: function(/* dojo.data */ item, /*TreeNode*/ node){
		// summary: callback when a node is opened
	},
	onClose: function(/* dojo.data */ item, /*TreeNode*/ node){
		// summary: callback when a node is closed
	},

	_getNextNode: function(node){
		// summary: get next visible node

		if(node.isExpandable && node.isExpanded && node.hasChildren()){
			// if this is an expanded node, get the first child
			return node.getChildren()[0];		// _TreeNode	
		}else{
			// find a parent node with a sibling
			while(node && node.isTreeNode){
				var returnNode = node.getNextSibling();
				if(returnNode){
					return returnNode;		// _TreeNode
				}
				node = node.getParent();
			}
			return null;
		}
	},

	_getRootOrFirstNode: function(){
		// summary: get first visible node
		return this.showRoot ? this.rootNode : this.rootNode.getChildren()[0];
	},

	_collapseNode: function(/*_TreeNode*/ node){
		// summary: called when the user has requested to collapse the node

		if(node.isExpandable){
			if(node.state == "LOADING"){
				// ignore clicks while we are in the process of loading data
				return;
			}

			node.collapse();
			this.onClose(node.item, node);

			if(this.persist && node.item){
				delete this._openedItemIds[this.model.getIdentity(node.item)];
				this._saveState();
			}
		}
	},

	_expandNode: function(/*_TreeNode*/ node){
		// summary: called when the user has requested to expand the node

		if(!node.isExpandable){
			return;
		}

		var model = this.model,
			item = node.item;

		switch(node.state){
			case "LOADING":
				// ignore clicks while we are in the process of loading data
				return;

			case "UNCHECKED":
				// need to load all the children, and then expand
				node.markProcessing();
				var _this = this;
				model.getChildren(item, function(items){
						node.unmarkProcessing();
						node.setChildItems(items);
						_this._expandNode(node);
					},
					function(err){
						console.error(_this, ": error loading root children: ", err);
					});
				break;

			default:
				// data is already loaded; just proceed
				node.expand();
				this.onOpen(node.item, node);

				if(this.persist && item){
					this._openedItemIds[model.getIdentity(item)] = true;
					this._saveState();
				}
		}
	},

	////////////////// Miscellaneous functions ////////////////

	blurNode: function(){
		// summary:
		//	Removes focus from the currently focused node (which must be visible).
		//	Usually not called directly (just call focusNode() on another node instead)
		var node = this.lastFocused;
		if(!node){ return; }
		var labelNode = node.labelNode;
		dojo.removeClass(labelNode, "dijitTreeLabelFocused");
		labelNode.setAttribute("tabIndex", "-1");
		dijit.setWaiState(labelNode, "selected", false);
		this.lastFocused = null;
	},

	focusNode: function(/* _tree.Node */ node){
		// summary:
		//	Focus on the specified node (which must be visible)

		// set focus so that the label will be voiced using screen readers
		node.labelNode.focus();
	},

	_onBlur: function(){
		// summary:
		// 		We've moved away from the whole tree.  The currently "focused" node
		//		(see focusNode above) should remain as the lastFocused node so we can
		//		tab back into the tree.  Just change CSS to get rid of the dotted border
		//		until that time

		this.inherited(arguments);
		if(this.lastFocused){
			var labelNode = this.lastFocused.labelNode;
			dojo.removeClass(labelNode, "dijitTreeLabelFocused");	
		}
	},

	_onTreeFocus: function(/*Widget*/ node){
		// summary:
		//		called from onFocus handler of treeitem labelNode to set styles, wai state and tabindex
		//		for currently focused treeitem.
		
		if (node){
			if(node != this.lastFocused){
				this.blurNode();
			}
			var labelNode = node.labelNode;
			// set tabIndex so that the tab key can find this node
			labelNode.setAttribute("tabIndex", "0");
			dijit.setWaiState(labelNode, "selected", true);
			dojo.addClass(labelNode, "dijitTreeLabelFocused");
			this.lastFocused = node;
		}
	},

	//////////////// Events from the model //////////////////////////
	
	_onItemDelete: function(/*Object*/ item){
		//summary: delete event from the store
		// TODO: currently this isn't called, and technically doesn't need to be,
		// but it would help with garbage collection

		var identity = this.model.getIdentity(item);
		var node = this._itemNodeMap[identity];

		if(node){
			var parent = node.getParent();
			if(parent){
				// if node has not already been orphaned from a _onSetItem(parent, "children", ..) call...
				parent.removeChild(node);
			}
			delete this._itemNodeMap[identity];
			node.destroyRecursive();
		}
	},

	_onItemChange: function(/*Item*/ item){
		//summary: processes notification of a change to an item's scalar values like label
		var model = this.model,
			identity = model.getIdentity(item),
			node = this._itemNodeMap[identity];

		if(node){
			node.setLabelNode(this.getLabel(item));
			node._updateItemClasses(item);
		}
	},

	_onItemChildrenChange: function(/*dojo.data.Item*/ parent, /*dojo.data.Item[]*/ newChildrenList){
		//summary: processes notification of a change to an item's children
		var model = this.model,
			identity = model.getIdentity(parent),
			parentNode = this._itemNodeMap[identity];

		if(parentNode){
			parentNode.setChildItems(newChildrenList);
		}
	},

	_onItemDelete: function(/*Item*/ item){
		//summary: processes notification of a deletion of an item
		var model = this.model,
			identity = model.getIdentity(item),
			node = this._itemNodeMap[identity];

		if(node){
			node.destroyRecursive();
			delete this._itemNodeMap[identity];
		}
	},

	/////////////// Miscellaneous funcs
	
	_saveState: function(){
		//summary: create and save a cookie with the currently expanded nodes identifiers
		if(!this.persist){
			return;
		}
		var ary = [];
		for(var id in this._openedItemIds){
			ary.push(id);
		}
		dojo.cookie(this.cookieName, ary.join(","), {expires:365});
	},

	destroy: function(){
		if(this.rootNode){
			this.rootNode.destroyRecursive();
		}
		if(this.dndController && !dojo.isString(this.dndController)){
			this.dndController.destroy();
		}
		this.rootNode = null;
		this.inherited(arguments);
	},
	
	destroyRecursive: function(){
		// A tree is treated as a leaf, not as a node with children (like a grid),
		// but defining destroyRecursive for back-compat.
		this.destroy();
	},

	_createTreeNode: function(/*Object*/ args){
		// summary:
		//		creates a TreeNode
		// description:
		//		Developers can override this method to define their own TreeNode class;
		//		However it will probably be removed in a future release in favor of a way
		//		of just specifying a widget for the label, rather than one that contains
		//		the children too.
		return new dijit._TreeNode(args);
	}
});


dojo.declare(
	"dijit.tree.TreeStoreModel",
	null,
{
	// summary:
	//		Implements dijit.Tree.model connecting to a store with a single
	//		root item.  Any methods passed into the constructor will override
	//		the ones defined here.

	// store: dojo.data.Store
	//		Underlying store
	store: null,

	// childrenAttrs: String[]
	//		one ore more attributes that holds children of a tree node
	childrenAttrs: ["children"],

	// labelAttr: String
	//		If specified, get label for tree node from this attribute, rather
	//		than by calling store.getLabel()
	labelAttr: "",
 
 	// root: dojo.data.Item
	//		Pointer to the root item (read only, not a parameter)
	root: null,

	// query: anything
	//		Specifies datastore query to return the root item for the tree.
	//		Must only return a single item.   Alternately can just pass in pointer
	//		to root item.
	// example:
	//		{id:'ROOT'}
	query: null,

	constructor: function(/* Object */ args){
		// summary: passed the arguments listed above (store, etc)
		dojo.mixin(this, args);

		this.connects = [];

		var store = this.store;
		if(!store.getFeatures()['dojo.data.api.Identity']){
			throw new Error("dijit.Tree: store must support dojo.data.Identity");			
		}

		// if the store supports Notification, subscribe to the notification events
		if(store.getFeatures()['dojo.data.api.Notification']){
			this.connects = this.connects.concat([
				dojo.connect(store, "onNew", this, "_onNewItem"),
				dojo.connect(store, "onDelete", this, "_onDeleteItem"),
				dojo.connect(store, "onSet", this, "_onSetItem")
			]);
		}
	},

	destroy: function(){
		dojo.forEach(this.connects, dojo.disconnect);
	},

	// =======================================================================
	// Methods for traversing hierarchy

	getRoot: function(onItem, onError){
		// summary:
		//		Calls onItem with the root item for the tree, possibly a fabricated item.
		//		Calls onError on error.
		if(this.root){
			onItem(this.root);
		}else{
			this.store.fetch({
				query: this.query,
				onComplete: dojo.hitch(this, function(items){
					if(items.length != 1){
						throw new Error(this.declaredClass + ": query " + dojo.toJson(this.query) + " returned " + items.length +
						 	" items, but must return exactly one item");
					}
					this.root = items[0];
					onItem(this.root);
				}),
				onError: onError
			});
		}
	},

	mayHaveChildren: function(/*dojo.data.Item*/ item){
		// summary:
		//		Tells if an item has or may have children.  Implementing logic here
		//		avoids showing +/- expando icon for nodes that we know don't have children.
		//		(For efficiency reasons we may not want to check if an element actually
		//		has children until user clicks the expando node)
		return dojo.some(this.childrenAttrs, function(attr){
			return this.store.hasAttribute(item, attr);
		}, this);
	},

	getChildren: function(/*dojo.data.Item*/ parentItem, /*function(items)*/ onComplete, /*function*/ onError){
		// summary:
		// 		Calls onComplete() with array of child items of given parent item, all loaded.

		var store = this.store;

		// get children of specified item
		var childItems = [];
		for (var i=0; i<this.childrenAttrs.length; i++){
			var vals = store.getValues(parentItem, this.childrenAttrs[i]);
			childItems = childItems.concat(vals);
		}

		// count how many items need to be loaded
		var _waitCount = 0;
		dojo.forEach(childItems, function(item){ if(!store.isItemLoaded(item)){ _waitCount++; } });

		if(_waitCount == 0){
			// all items are already loaded.  proceed...
			onComplete(childItems);
		}else{
			// still waiting for some or all of the items to load
			var onItem = function onItem(item){
				if(--_waitCount == 0){
					// all nodes have been loaded, send them to the tree
					onComplete(childItems);
				}
			}
			dojo.forEach(childItems, function(item){
				if(!store.isItemLoaded(item)){
					store.loadItem({
						item: item,
						onItem: onItem,
						onError: onError
					});
				}
			});
		}
	},

	// =======================================================================
	// Inspecting items

	getIdentity: function(/* item */ item){
		return this.store.getIdentity(item);	// Object
	},

	getLabel: function(/*dojo.data.Item*/ item){
		// summary: get the label for an item
		if(this.labelAttr){
			return this.store.getValue(item,this.labelAttr);	// String
		}else{
			return this.store.getLabel(item);	// String
		}
	},

	// =======================================================================
	// Write interface

	newItem: function(/* Object? */ args, /*Item*/ parent){
		// summary:
		//		Creates a new item.   See dojo.data.api.Write for details on args.
		//		Used in drag & drop when item from external source dropped onto tree.
		var pInfo = {parent: parent, attribute: this.childrenAttrs[0]};
		return this.store.newItem(args, pInfo);
	},

	pasteItem: function(/*Item*/ childItem, /*Item*/ oldParentItem, /*Item*/ newParentItem, /*Boolean*/ bCopy){
		// summary:
		//		Move or copy an item from one parent item to another.
		//		Used in drag & drop
		var store = this.store,
			parentAttr = this.childrenAttrs[0];	// name of "children" attr in parent item

		// remove child from source item, and record the attributee that child occurred in	
		if(oldParentItem){
			dojo.forEach(this.childrenAttrs, function(attr){
				if(store.containsValue(oldParentItem, attr, childItem)){
					if(!bCopy){
						var values = dojo.filter(store.getValues(oldParentItem, attr), function(x){
							return x != childItem;
						});
						store.setValues(oldParentItem, attr, values);
					}
					parentAttr = attr;
				}
			});
		}

		// modify target item's children attribute to include this item
		if(newParentItem){
			store.setValues(newParentItem, parentAttr,
				store.getValues(newParentItem, parentAttr).concat(childItem));
		}
	},

	// =======================================================================
	// Callbacks
	
	onChange: function(/*dojo.data.Item*/ item){
		// summary:
		//		Callback whenever an item has changed, so that Tree
		//		can update the label, icon, etc.   Note that changes
		//		to an item's children or parent(s) will trigger an
		//		onChildrenChange() so you can ignore those changes here.
	},

	onChildrenChange: function(/*dojo.data.Item*/ parent, /*dojo.data.Item[]*/ newChildrenList){
		// summary:
		//		Callback to do notifications about new, updated, or deleted items.
	},

	onDelete: function(/*dojo.data.Item*/ parent, /*dojo.data.Item[]*/ newChildrenList){
		// summary:
		//		Callback when an item has been deleted.
		// description:
		//		Note that there will also be an onChildrenChange() callback for the parent
		//		of this item.
	},

	// =======================================================================
	///Events from data store

	_onNewItem: function(/* dojo.data.Item */ item, /* Object */ parentInfo){
		// summary: handler for when new items appear in the store.

		//	In this case there's no correspond onSet() call on the parent of this
		//	item, so need to get the new children list of the parent manually somehow.
		if(!parentInfo){
			return;
		}
		this.getChildren(parentInfo.item, dojo.hitch(this, function(children){
			// NOTE: maybe can be optimized since parentInfo contains the new and old attribute value
			this.onChildrenChange(parentInfo.item, children);
		}));
	},
	
	_onDeleteItem: function(/*Object*/ item){
		// summary: handler for delete notifications from underlying store
		this.onDelete(item);
	},

	_onSetItem: function(/* item */ item, 
					/* attribute-name-string */ attribute, 
					/* object | array */ oldValue,
					/* object | array */ newValue){
		//summary: set data event on an item in the store
	
		if(dojo.indexOf(this.childrenAttrs, attribute) != -1){
			// item's children list changed
			this.getChildren(item, dojo.hitch(this, function(children){
				// NOTE: maybe can be optimized since parentInfo contains the new and old attribute value
				this.onChildrenChange(item, children);
			}));
		}else{
			// item's label/icon/etc. changed.
			this.onChange(item);
		}
	}
});

dojo.declare("dijit.tree.ForestStoreModel", dijit.tree.TreeStoreModel, {
	// summary:
	//		Interface between Tree and a dojo.store that doesn't have a root item, ie,
	//		has multiple "top level" items.
	//
	// description
	//		Use this class to wrap a dojo.store, making all the items matching the specified query
	//		appear as children of a fabricated "root item".  If no query is specified then all the
	//		items returned by fetch() on the underlying store become children of the root item.
	//		It allows dijit.Tree to assume a single root item, even if the store doesn't have one.

	// Parameters to constructor

	// rootId: String
	//	ID of fabricated root item
	rootId: "$root$",

	// rootLabel: String
	//	Label of fabricated root item
	rootLabel: "ROOT",

	// query: String
	//	Specifies the set of children of the root item.
	// example:
	//		{type:'continent'}
	query: null,

	// End of parameters to constructor

	constructor: function(params){
		// Make dummy root item
		this.root = {
			store: this,
			root: true,
			id: params.rootId,
			label: params.rootLabel,
			children: params.rootChildren	// optional param
		};
	},

	// =======================================================================
	// Methods for traversing hierarchy

	mayHaveChildren: function(/*dojo.data.Item*/ item){
		// summary:
		//		Tells if an item has or may have children.  Implementing logic here
		//		avoids showing +/- expando icon for nodes that we know don't have children.
		//		(For efficiency reasons we may not want to check if an element actually
		//		has children until user clicks the expando node)
		return item === this.root || this.inherited(arguments);
	},

	getChildren: function(/*dojo.data.Item*/ parentItem, /*function(items)*/ callback, /*function*/ onError){
		// summary:
		// 		Calls onComplete() with array of child items of given parent item, all loaded.
		if(parentItem === this.root){
			if(this.root.children){
				// already loaded, just return
				callback(this.root.children);
			}else{
				this.store.fetch({
					query: this.query,
					onComplete: dojo.hitch(this, function(items){
						this.root.children = items;
						callback(items);
					}),
					onError: onError
				});
			}
		}else{
			this.inherited(arguments);
		}
	},

	// =======================================================================
	// Inspecting items

	getIdentity: function(/* item */ item){
		return (item === this.root) ? this.root.id : this.inherited(arguments);
	},

	getLabel: function(/* item */ item){
		return	(item === this.root) ? this.root.label : this.inherited(arguments);
	},

	// =======================================================================
	// Write interface

	newItem: function(/* Object? */ args, /*Item*/ parent){
		// summary:
		//		Creates a new item.   See dojo.data.api.Write for details on args.
		//		Used in drag & drop when item from external source dropped onto tree.
		if(parent===this.root){
			this.onNewRootItem(args);
			return this.store.newItem(args);
		}else{
			return this.inherited(arguments);
		}
	},
 
	onNewRootItem: function(args){
		// summary:
		//		User can override this method to modify a new element that's being
		//		added to the root of the tree, for example to add a flag like root=true
	},

	pasteItem: function(/*Item*/ childItem, /*Item*/ oldParentItem, /*Item*/ newParentItem, /*Boolean*/ bCopy){
		// summary:
		//		Move or copy an item from one parent item to another.
		//		Used in drag & drop
		if(oldParentItem === this.root){
			if(!bCopy){
				// It's onLeaveRoot()'s responsibility to modify the item so it no longer matches
				// this.query... thus triggering an onChildrenChange() event to notify the Tree
				// that this element is no longer a child of the root node
				this.onLeaveRoot(childItem);
			}
		}
		dijit.tree.TreeStoreModel.prototype.pasteItem.call(this, childItem,
			oldParentItem === this.root ? null : oldParentItem,
			newParentItem === this.root ? null : newParentItem
		);
		if(newParentItem === this.root){
			// It's onAddToRoot()'s responsibility to modify the item so it matches
			// this.query... thus triggering an onChildrenChange() event to notify the Tree
			// that this element is now a child of the root node
			this.onAddToRoot(childItem);
		}
	},

	// =======================================================================
	// Callbacks
	
	onAddToRoot: function(/* item */ item){
		// summary:
		//		Called when item added to root of tree; user must override
		//		to modify the item so that it matches the query for top level items
		// example
		//	|	store.setValue(item, "root", true);
		
	},

	onLeaveRoot: function(/* item */ item){
		// summary:
		//		Called when item removed from root of tree; user must override
		//		to modify the item so it doesn't match the query for top level items
		// example
		// 	|	store.unsetAttribute(item, "root");
		
	},
	
	// =======================================================================
	// Events from data store

	_requeryTop: function(){
		// reruns the query for the children of the root node,
		// sending out an onSet notification if those children have changed
		var oldChildren = this.root.children || [];
		this.store.fetch({
			query: this.query,
			onComplete: dojo.hitch(this, function(newChildren){
				this.root.children = newChildren;

				// If the list of children or the order of children has changed...	
				if(oldChildren.length != newChildren.length ||
					dojo.some(oldChildren, function(item, idx){ return newChildren[idx] != item;})){
					this.onChildrenChange(this.root, newChildren);
				}
			})
		});
	},

	_onNewItem: function(/* dojo.data.Item */ item, /* Object */ parentInfo){
		// summary: handler for when new items appear in the store.

		//		In theory, any new item could be a top level item.
		//		Do the safe but inefficient thing by requerying the top
		//		level items.   User can override this function to do something
		//		more efficient.
		this._requeryTop();

		this.inherited(arguments);
	},

	_onDeleteItem: function(/*Object*/ item){
		// summary: handler for delete notifications from underlying store

		// check if this was a child of root, and if so send notification that root's children
		// have changed
		if(dojo.indexOf(this.root.children, item) != -1){
			this._requeryTop();
		}

		this.inherited(arguments);
	}
});

}

if(!dojo._hasResource["dijit.form.TextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.TextBox"] = true;
dojo.provide("dijit.form.TextBox");



dojo.declare(
	"dijit.form.TextBox",
	dijit.form._FormValueWidget,
	{
		//	summary:
		//		A base class for textbox form inputs

		//	trim: Boolean
		//		Removes leading and trailing whitespace if true.  Default is false.
		trim: false,

		//	uppercase: Boolean
		//		Converts all characters to uppercase if true.  Default is false.
		uppercase: false,

		//	lowercase: Boolean
		//		Converts all characters to lowercase if true.  Default is false.
		lowercase: false,

		//	propercase: Boolean
		//		Converts the first character of each word to uppercase if true.
		propercase: false,

		//	maxLength: String
		//		HTML INPUT tag maxLength declaration.
		maxLength: "",

		templateString:"<input class=\"dijit dijitReset dijitLeft\" dojoAttachPoint='textbox,focusNode' name=\"${name}\"\n\tdojoAttachEvent='onmouseenter:_onMouse,onmouseleave:_onMouse,onfocus:_onMouse,onblur:_onMouse,onkeypress:_onKeyPress'\n\tautocomplete=\"off\" type=\"${type}\"\n\t/>\n",
		baseClass: "dijitTextBox",

		attributeMap: dojo.mixin(dojo.clone(dijit.form._FormValueWidget.prototype.attributeMap),
			{maxLength:"focusNode"}),

		_getValueAttr: function(){
			// summary:
			//		Hook so attr('value') works as we like.
			// description:
			//		For TextBox this simply returns the value of the <input>,
			//		but the parse() call is so subclasses can change this
			//		behavior w/out overriding this method.
			return this.parse(this.attr('displayedValue'), this.constraints);
		},

		_setValueAttr: function(value, /*Boolean?*/ priorityChange, /*String?*/ formattedValue){
			//	summary:
			//		Hook so attr('value', ...) works.
			//
			//	description: 
			//		Sets the value of the widget to "value" which can be of
			//		any type as determined by the widget.
			//
			//	value:
			//		The visual element value is also set to a corresponding,
			//		but not necessarily the same, value.
			//
			//	formattedValue:
			//		If specified, used to set the visual element value,
			//		otherwise a computed visual value is used.
			//
			//	priorityChange:
			//		If true, an onChange event is fired immediately instead of 
			//		waiting for the next blur event.

			var filteredValue;
			if(value !== undefined){
				filteredValue = this.filter(value);
				if(filteredValue !== null && ((typeof filteredValue != "number") || !isNaN(filteredValue))){
					if(typeof formattedValue != "string"){
						formattedValue = this.format(filteredValue, this.constraints);
					}
				}else{ formattedValue = ''; }
			}
			if(formattedValue != null && formattedValue != undefined){
				this.textbox.value = formattedValue;
			}
			dijit.form.TextBox.superclass._setValueAttr.call(this, filteredValue, priorityChange);
		},

		// displayedValue: String
		//		For subclasses like ComboBox where the displayed value
		//		(ex: Kentucky) and the serialized value (ex: KY) are different,
		//		this represents the displayed value.
		//
		//		Setting 'displayedValue' through attr('displayedValue', ...)
		//		updates 'value', and vice-versa.  Othewise 'value' is updated
		//		from 'displayedValue' periodically, like onBlur etc.
		//
		//		TODO: move declaration to MappedTextBox?
		//		Problem is that ComboBox references displayedValue,
		//		for benefit of FilteringSelect.
		displayedValue: "",

		getDisplayedValue: function(){
			dojo.deprecated(this.declaredClass+"::getDisplayedValue() is deprecated. Use attr('displayedValue') instead.", "", "2.0");
			return this.attr('displayedValue');
		},

		_getDisplayedValueAttr: function(){
			//	summary:
			//		Hook so attr('displayedValue') works.
			//	description:
			//		Returns the displayed value (what the user sees on the screen),
			// 		after filtering (ie, trimming spaces etc.).
			//
			//		For some subclasses of TextBox (like ComboBox), the displayed value
			//		is different from the serialized value that's actually 
			//		sent to the server (see dijit.form.ValidationTextBox.serialize)
			
			return this.filter(this.textbox.value);
		},

		setDisplayedValue: function(/*String*/value){
			dojo.deprecated(this.declaredClass+"::setDisplayedValue() is deprecated. Use attr('displayedValue', ...) instead.", "", "2.0");
			this.attr('displayedValue', value);
		},
			
		_setDisplayedValueAttr: function(/*String*/value){
			// summary:
			//		Hook so attr('displayedValue', ...) works.
			//	description: 
			//		Sets the value of the visual element to the string "value".
			//		The widget value is also set to a corresponding,
			//		but not necessarily the same, value.

			this.textbox.value = value;
			this._setValueAttr(this.attr('value'));
		},

		format: function(/* String */ value, /* Object */ constraints){
			//	summary:
			//		Replacable function to convert a value to a properly formatted string
			return ((value == null || value == undefined) ? "" : (value.toString ? value.toString() : value));
		},

		parse: function(/* String */ value, /* Object */ constraints){
			//	summary:
			//		Replacable function to convert a formatted string to a value
			return value;
		},

		postCreate: function(){
			// setting the value here is needed since value="" in the template causes "undefined"
			// and setting in the DOM (instead of the JS object) helps with form reset actions
			this.textbox.setAttribute("value", this.textbox.value); // DOM and JS values should be the same
			this.inherited(arguments);

			/*#5297:if(this.srcNodeRef){
				dojo.style(this.textbox, "cssText", this.style);
				this.textbox.className += " " + this["class"];
			}*/
			this._layoutHack();
		},

		filter: function(val){
			//	summary:
			//		Auto-corrections (such as trimming) that are applied to textbox
			//		value on blur or form submit
			if(typeof val != "string"){ return val; }
			if(this.trim){
				val = dojo.trim(val);
			}
			if(this.uppercase){
				val = val.toUpperCase();
			}
			if(this.lowercase){
				val = val.toLowerCase();
			}
			if(this.propercase){
				val = val.replace(/[^\s]+/g, function(word){
					return word.substring(0,1).toUpperCase() + word.substring(1);
				});
			}
			return val;
		},

		_setBlurValue: function(){
			this._setValueAttr(this.attr('value'), (this.isValid ? this.isValid() : true));
		},

		_onBlur: function(){
			this._setBlurValue();
			this.inherited(arguments);
		}

	}
);

dijit.selectInputText = function(/*DomNode*/element, /*Number?*/ start, /*Number?*/ stop){
	//	summary:
	//		Select text in the input element argument, from start (default 0), to stop (default end).

	// TODO: use functions in _editor/selection.js?
	var _window = dojo.global;
	var _document = dojo.doc;
	element = dojo.byId(element);
	if(isNaN(start)){ start = 0; }
	if(isNaN(stop)){ stop = element.value ? element.value.length : 0; }
	element.focus();
	if(_document["selection"] && dojo.body()["createTextRange"]){ // IE
		if(element.createTextRange){
			var range = element.createTextRange();
			with(range){
				collapse(true);
				moveStart("character", start);
				moveEnd("character", stop);
				select();
			}
		}
	}else if(_window["getSelection"]){
		var selection = _window.getSelection();
		// FIXME: does this work on Safari?
		if(element.setSelectionRange){
			element.setSelectionRange(start, stop);
		}
	}
}

}

if(!dojo._hasResource["dijit.InlineEditBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.InlineEditBox"] = true;
dojo.provide("dijit.InlineEditBox");










dojo.declare("dijit.InlineEditBox",
	dijit._Widget,
	{
	// summary: An element with in-line edit capabilitites
	//
	// description:
	//		Behavior for an existing node (`<p>`, `<div>`, `<span>`, etc.) so that
	// 		when you click it, an editor shows up in place of the original
	//		text.  Optionally, Save and Cancel button are displayed below the edit widget.
	//		When Save is clicked, the text is pulled from the edit
	//		widget and redisplayed and the edit widget is again hidden.
	//		By default a plain Textarea widget is used as the editor (or for
	//		inline values a TextBox), but you can specify an editor such as
	//		dijit.Editor (for editing HTML) or a Slider (for adjusting a number).
	//		An edit widget must support the following API to be used:
	//			- displayedValue or value as initialization parameter,
	//			and available through attr('displayedValue') / attr('value')
	//			- void focus()
	//			- DOM-node focusNode = node containing editable text

	// editing: Boolean
	//		Is the node currently in edit mode?
	editing: false,

	// autoSave: Boolean
	//		Changing the value automatically saves it; don't have to push save button
	//		(and save button isn't even displayed)
	autoSave: true,

	// buttonSave: String
	//		Save button label
	buttonSave: "",

	// buttonCancel: String
	//		Cancel button label
	buttonCancel: "",

	// renderAsHtml: Boolean
	//		Set this to true if the specified Editor's value should be interpreted as HTML
	//		rather than plain text (ie, dijit.Editor)
	renderAsHtml: false,

	// editor: String
	//		Class name for Editor widget
	editor: "dijit.form.TextBox",

	// editorParams: Object
	//		Set of parameters for editor, like {required: true}
	editorParams: {},

	onChange: function(value){
		// summary: User should set this handler to be notified of changes to value
	},
	
	onCancel: function(){
		// summary: User should set this handler to be notified when no change occured
	},

	// width: String
	//		Width of editor.  By default it's width=100% (ie, block mode)
	width: "100%",

	// value: String
	//		The display value of the widget in read-only mode
	value: "",

	// noValueIndicator: String
	//		The text that gets displayed when there is no value (so that the user has a place to click to edit)
	noValueIndicator: "<span style='font-family: wingdings; text-decoration: underline;'>&nbsp;&nbsp;&nbsp;&nbsp;&#x270d;&nbsp;&nbsp;&nbsp;&nbsp;</span>",

	constructor: function(){
		this.editorParams = {};
	},

	postMixInProperties: function(){
		this.inherited(arguments);

		// save pointer to original source node, since Widget nulls-out srcNodeRef
		this.displayNode = this.srcNodeRef;

		// connect handlers to the display node
		var events = {
			ondijitclick: "_onClick",
			onmouseover: "_onMouseOver",
			onmouseout: "_onMouseOut",
			onfocus: "_onMouseOver",
			onblur: "_onMouseOut"			
		};
		for(var name in events){
			this.connect(this.displayNode, name, events[name]);
		}
		dijit.setWaiRole(this.displayNode, "button");
		if(!this.displayNode.getAttribute("tabIndex")){
			this.displayNode.setAttribute("tabIndex", 0);
		}

		this.attr('value', this.value || this.displayNode.innerHTML);
	},

	setDisabled: function(/*Boolean*/ disabled){
		dojo.deprecated("dijit.InlineEditBox.setDisabled() is deprecated.  Use attr('disabled', bool) instead.", "", "2.0");
		this.attr('disabled', disabled);
	},
	_setDisabledAttr: function(/*Boolean*/ disabled){
		// summary: 
		//		Hook to make attr("disabled", ...) work.
		//		Set disabled state of widget.
		this.disabled = disabled;
		dijit.setWaiState(this.domNode, "disabled", disabled);
	},

	_onMouseOver: function(){
		dojo.addClass(this.displayNode, this.disabled ? "dijitDisabledClickableRegion" : "dijitClickableRegion");
	},

	_onMouseOut: function(){
		dojo.removeClass(this.displayNode, this.disabled ? "dijitDisabledClickableRegion" : "dijitClickableRegion");
	},

	_onClick: function(/*Event*/ e){
		if(this.disabled){ return; }
		if(e){ dojo.stopEvent(e); }
		this._onMouseOut();

		// Since FF gets upset if you move a node while in an event handler for that node...
		setTimeout(dojo.hitch(this, "edit"), 0);
	},

	edit: function(){
		// summary:
		//		Display the editor widget in place of the original (read only) markup.

		if(this.disabled || this.editing){ return; }
		this.editing = true;

		var editValue = 
				(this.renderAsHtml ?
				this.value :
				this.value.replace(/\s*\r?\n\s*/g,"").replace(/<br\/?>/gi,"\n").replace(/&gt;/g,">").replace(/&lt;/g,"<").replace(/&amp;/g,"&").replace(/&quot;/g,"\""));

		// Placeholder for edit widget
		// Put place holder (and eventually editWidget) before the display node so that it's positioned correctly
		// when Calendar dropdown appears, which happens automatically on focus.
		var placeholder = dojo.doc.createElement("span");
		dojo.place(placeholder, this.domNode, "before");

		var ew = this.editWidget = new dijit._InlineEditor({
			value: dojo.trim(editValue),
			autoSave: this.autoSave,
			buttonSave: this.buttonSave,
			buttonCancel: this.buttonCancel,
			renderAsHtml: this.renderAsHtml,
			editor: this.editor,
			editorParams: this.editorParams,
			style: dojo.getComputedStyle(this.displayNode),
			save: dojo.hitch(this, "save"),
			cancel: dojo.hitch(this, "cancel"),
			width: this.width
		}, placeholder);

		// to avoid screen jitter, we first create the editor with position:absolute, visibility:hidden,
		// and then when it's finished rendering, we switch from display mode to editor
		var ews = ew.domNode.style;
		this.displayNode.style.display="none";
		ews.position = "static";
		ews.visibility = "visible";

		// Replace the display widget with edit widget, leaving them both displayed for a brief time so that
		// focus can be shifted without incident.  (browser may needs some time to render the editor.)
		this.domNode = ew.domNode;
		setTimeout(function(){
			if(ew.editWidget._resetValue === undefined){
				ew.editWidget._resetValue = ew.getValue();
			}
			ew.focus();
		}, 100);
	},

	_showText: function(/*Boolean*/ focus){
		// summary: revert to display mode, and optionally focus on display node

		// display the read-only text and then quickly hide the editor (to avoid screen jitter)
		this.displayNode.style.display="";
		var ew = this.editWidget;
		var ews = ew.domNode.style;
		ews.position="absolute";
		ews.visibility="hidden";

		this.domNode = this.displayNode;

		if(focus){
			dijit.focus(this.displayNode);
		}
		ews.display = "none";
		// give the browser some time to render the display node and then shift focus to it
		// and hide the edit widget before garbage collecting the edit widget
		setTimeout(function(){
			ew.destroy();
			delete ew;
			if(dojo.isIE){
				// messing with the DOM tab order can cause IE to focus the body - so restore
				dijit.focus(dijit.getFocus());
			}
		}, 1000); // no hurry - wait for things to quiesce
	},

	save: function(/*Boolean*/ focus){
		// summary:
		//		Save the contents of the editor and revert to display mode.
		// focus: Boolean
		//		Focus on the display mode text
		if(this.disabled || !this.editing){ return; }
		this.editing = false;

		var value = this.editWidget.getValue() + "";
		this.attr('value', this.renderAsHtml? value
			: value.replace(/&/gm, "&amp;").replace(/</gm, "&lt;").replace(/>/gm, "&gt;").replace(/"/gm, "&quot;").replace(/\n/g, "<br>")
		);

		// tell the world that we have changed
		this.onChange(value);

		this._showText(focus);	
	},

	setValue: function(/*String*/ val){
		dojo.deprecated("dijit.InlineEditBox.setValue() is deprecated.  Use attr('value', ...) instead.", "", "2.0");
		return this.attr("value", val);
	},
	_setValueAttr: function(/*String*/ val){
		// summary:
		// 		Hook to make attr("value", ...) work.
		//		Inserts specified HTML value into this node, or an "input needed" character if node is blank.
		this.value = val;
		this.displayNode.innerHTML = dojo.trim(val) || this.noValueIndicator;
	},

	getValue: function(){
		dojo.deprecated("dijit.InlineEditBox.getValue() is deprecated.  Use attr('value') instead.", "", "2.0");
		return this.attr("value");
	},

	cancel: function(/*Boolean*/ focus){
		// summary:
		//		Revert to display mode, discarding any changes made in the editor
		this.editing = false;
		
		// tell the world that we have no changes
		this.onCancel();
		
		this._showText(focus);
	}
});

dojo.declare(
	"dijit._InlineEditor",
	 [dijit._Widget, dijit._Templated],
{
	// summary:
	// 		internal widget used by InlineEditBox, displayed when in editing mode
	//		to display the editor and maybe save/cancel buttons.  Calling code should
	//		connect to save/cancel methods to detect when editing is finished
	//
	//		Has mainly the same parameters as InlineEditBox, plus these values:
	//
	// style: Object
	//		Set of CSS attributes of display node, to replicate in editor
	//
	// value: String
	//		Value as an HTML string or plain text string, depending on renderAsHTML flag

	templateString:"<span dojoAttachPoint=\"editNode\" waiRole=\"presentation\" style=\"position: absolute; visibility:hidden\" class=\"dijitReset dijitInline\"\n\tdojoAttachEvent=\"onkeypress: _onKeyPress\" \n\t><span dojoAttachPoint=\"editorPlaceholder\"></span\n\t><span dojoAttachPoint=\"buttonContainer\"\n\t\t><button class='saveButton' dojoAttachPoint=\"saveButton\" dojoType=\"dijit.form.Button\" dojoAttachEvent=\"onClick:save\" disabled=\"true\" label=\"${buttonSave}\"></button\n\t\t><button class='cancelButton' dojoAttachPoint=\"cancelButton\" dojoType=\"dijit.form.Button\" dojoAttachEvent=\"onClick:cancel\" label=\"${buttonCancel}\"></button\n\t></span\n></span>\n",
	widgetsInTemplate: true,

	postMixInProperties: function(){
		this.inherited(arguments);
		this.messages = dojo.i18n.getLocalization("dijit", "common", this.lang);
		dojo.forEach(["buttonSave", "buttonCancel"], function(prop){
			if(!this[prop]){ this[prop] = this.messages[prop]; }
		}, this);
	},

	postCreate: function(){
		// Create edit widget in place in the template
		var cls = dojo.getObject(this.editor);

		// Copy the style from the source
		// Don't copy ALL properties though, just the necessary/applicable ones
		var srcStyle = this.style;
		var editStyle = "line-height:" + srcStyle.lineHeight + ";";
		dojo.forEach(["Weight","Family","Size","Style"], function(prop){
			editStyle += "font-"+prop+":"+srcStyle["font"+prop]+";";
		}, this);
		dojo.forEach(["marginTop","marginBottom","marginLeft", "marginRight"], function(prop){
			this.domNode.style[prop] = srcStyle[prop];
		}, this);
		if(this.width=="100%"){
			// block mode
			editStyle += "width:100%;";
			this.domNode.style.display = "block";
		}else{
			// inline-block mode
			editStyle += "width:" + (this.width + (Number(this.width)==this.width ? "px" : "")) + ";";
		}
		this.editorParams.style = editStyle;
		this.editorParams[ "displayedValue" in cls.prototype ? "displayedValue" : "value"]= this.value;
		var ew = this.editWidget = new cls(this.editorParams, this.editorPlaceholder);

		this.connect(ew, "onChange", "_onChange");

		// Monitor keypress on the edit widget.   Note that edit widgets do a stopEvent() on ESC key (to
		// prevent Dialog from closing when the user just wants to revert the value in the edit widget),
		// so this is the only way we can see the key press event.
		this.connect(ew, "onKeyPress", "_onKeyPress");

		if(this.autoSave){
			this.buttonContainer.style.display="none";
		}
	},

	destroy: function(){
		this.editWidget.destroy();
		this.inherited(arguments);
	},

	getValue: function(){
		var ew = this.editWidget;
		return ew.attr("displayedValue" in ew ? "displayedValue" : "value");
	},

	_onKeyPress: function(e){
		// summary: Callback when keypress in the edit box (see template).
		// description:
		//		For autoSave widgets, if Esc/Enter, call cancel/save.
		//		For non-autoSave widgets, enable save button if the text value is
		//		different than the original value.
		if(this._exitInProgress){
			return;
		}
		if(this.autoSave){
			if(e.altKey || e.ctrlKey){ return; }
			// If Enter/Esc pressed, treat as save/cancel.
			if(e.charOrCode == dojo.keys.ESCAPE){
				dojo.stopEvent(e);
				this._exitInProgress = true;
				this.cancel(true);
			}else if(e.charOrCode == dojo.keys.ENTER){
				dojo.stopEvent(e);
				this._exitInProgress = true;
				this.save(true);
			}else if(e.charOrCode === dojo.keys.TAB){
				this._exitInProgress = true;
				// allow the TAB to change focus before we mess with the DOM: #6227
				// Expounding by request:
				// 	The current focus is on the edit widget input field.
				//	save() will hide and destroy this widget.
				//	We want the focus to jump from the currently hidden
				//	displayNode, but since it's hidden, it's impossible to
				//	unhide it, focus it, and then have the browser focus
				//	away from it to the next focusable element since each
				//	of these events is asynchronous and the focus-to-next-element
				//	is already queued.
				//	So we allow the browser time to unqueue the move-focus event 
				//	before we do all the hide/show stuff.
				setTimeout(dojo.hitch(this, "save", false), 0);
			}
		}else{
			var _this = this;
			// Delay before calling getValue().
			// The delay gives the browser a chance to update the Textarea.
			setTimeout(
				function(){
					_this.saveButton.attr("disabled", _this.getValue() == _this.editWidget._resetValue);
				}, 100);
		}
	},

	_onBlur: function(){
		// summary:
		//	Called when focus moves outside the editor
		this.inherited(arguments);
		if(this._exitInProgress){
			// when user clicks the "save" button, focus is shifted back to display text, causing this
			// function to be called, but in that case don't do anything
			return;
		}
		if(this.autoSave){
			this._exitInProgress = true;
			if(this.getValue() == this.editWidget._resetValue){
				this.cancel(false);
			}else{
				this.save(false);
			}
		}
	},

	_onChange: function(){
		// summary:
		//	Called when the underlying widget fires an onChange event,
		//	which means that the user has finished entering the value
		if(this._exitInProgress){
			// TODO: the onChange event might happen after the return key for an async widget
			// like FilteringSelect.  Shouldn't be deleting the edit widget on end-of-edit
			return;
		}
		if(this.autoSave){
			this._exitInProgress = true;
			this.save(true);
		}else{
			// in case the keypress event didn't get through (old problem with Textarea that has been fixed
			// in theory) or if the keypress event comes too quickly and the value inside the Textarea hasn't
			// been updated yet)
			this.saveButton.attr("disabled", (this.getValue() == this.editWidget._resetValue) || !this.enableSave());
		}
	},
	
	enableSave: function(){
		// summary: User replacable function returning a Boolean to indicate
		// 	if the Save button should be enabled or not - usually due to invalid conditions
		return this.editWidget.isValid ? this.editWidget.isValid() : true;
	},

	focus: function(){
		this.editWidget.focus();
		dijit.selectInputText(this.editWidget.focusNode);
	}
});

}

if(!dojo._hasResource["dijit.form.CheckBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.CheckBox"] = true;
dojo.provide("dijit.form.CheckBox");



dojo.declare(
	"dijit.form.CheckBox",
	dijit.form.ToggleButton,
	{
		// summary:
		// 		Same as an HTML checkbox, but with fancy styling.
		//
		// description:
		// User interacts with real html inputs.
		// On onclick (which occurs by mouse click, space-bar, or
		// using the arrow keys to switch the selected radio button),
		// we update the state of the checkbox/radio.
		//
		// There are two modes:
		//   1. High contrast mode
		//   2. Normal mode
		// In case 1, the regular html inputs are shown and used by the user.
		// In case 2, the regular html inputs are invisible but still used by
		// the user. They are turned quasi-invisible and overlay the background-image.

		templateString:"<div class=\"dijitReset dijitInline\" waiRole=\"presentation\"\n\t><input\n\t \ttype=\"${type}\" name=\"${name}\"\n\t\tclass=\"dijitReset dijitCheckBoxInput\"\n\t\tdojoAttachPoint=\"focusNode\"\n\t \tdojoAttachEvent=\"onmouseover:_onMouse,onmouseout:_onMouse,onclick:_onClick\"\n/></div>\n",

		baseClass: "dijitCheckBox",

		//	Value of "type" attribute for <input>
		type: "checkbox",

		// value: String
		//		As an initialization parameter, equivalent to value field on normal checkbox
		//		(if checked, the value is passed as the value when form is submitted).
		//
		//		However, attr('value') will return either the string or false depending on
		//		whether or not the checkbox is checked.
		//
		//		attr('value', string) will check the checkbox and change the value to the
		//		specified string
		//
		//		attr('value', boolean) will change the checked state.
		value: "on",

		_setValueAttr: function(/*String or Boolean*/ newValue){
			// summary:
			//		Handler for value= attribute to constructor, and also calls to
			//		attr('value', val).
			// description:
			//		During initialization, just saves as attribute to the <input type=checkbox>.
			//		
			//		After initialization,
			//		when passed a boolean, controls whether or not the CheckBox is checked.
			//		If passed a string, changes the value attribute of the CheckBox (the one
			//		specified as "value" when the CheckBox was constructed (ex: <input
			//		dojoType="dijit.CheckBox" value="chicken">)
			if(typeof newValue == "string"){
				this.value = newValue;
				dojo.attr(this.focusNode, 'value', newValue);
				newValue = true;
			}
			if(this._created){
				this.attr('checked', newValue);
			}
		},

		_getValueAttr: function(){
			// summary:
			//		Hook so attr('value') works.
			// description:
			//		If the CheckBox is checked, returns the value attribute.
			//		Otherwise returns false.
			return (this.checked ? this.value : false);
		},

		postMixInProperties: function(){
			if(this.value == ""){
				this.value = "on";
			}
			this.inherited(arguments);
		},
		
		 _fillContent: function(/*DomNode*/ source){
			// Override Button::_fillContent() since it doesn't make sense for CheckBox,
			// since CheckBox doesn't even have a container
		},

		reset: function(){
			this._hasBeenBlurred = false;

			this.attr('checked', this.params.checked || false);

			// Handle unlikely event that the <input type=checkbox> value attribute has changed
			this.value = this.params.value || "on";
			dojo.attr(this.focusNode, 'value', this.value);
		},
		
		_onFocus: function(){
			if(this.id){
				dojo.query("label[for='"+this.id+"']").addClass("dijitFocusedLabel");
			}
		},

		_onBlur: function(){
			if(this.id){
				dojo.query("label[for='"+this.id+"']").removeClass("dijitFocusedLabel");
			}
		}
	}
);

dojo.declare(
	"dijit.form.RadioButton",
	dijit.form.CheckBox,
	{
		// summary:
		// 		Same as an HTML radio, but with fancy styling.

		type: "radio",
		baseClass: "dijitRadio",

		_setCheckedAttr: function(/*Boolean*/ value){
			// If I am being checked then have to deselect currently checked radio button
			this.inherited(arguments);
			if(!this._created){ return; }
			if(value){
				var _this = this;
				// search for radio buttons with the same name that need to be unchecked
				dojo.query('INPUT[type=radio][name='+this.name+']', this.focusNode.form||dojo.doc).forEach(
					function(inputNode){
						if(inputNode != _this.focusNode && inputNode.form == _this.focusNode.form){
							var widget = dijit.getEnclosingWidget(inputNode);
							if(widget && widget.checked){
								widget.attr('checked', false);
							}
						}
					}
				);
			}
		},

		_clicked: function(/*Event*/ e){
			if(!this.checked){
				this.attr('checked', true);
			}
		}
	}
);

}

if(!dojo._hasResource["dijit.form.ValidationTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.ValidationTextBox"] = true;
dojo.provide("dijit.form.ValidationTextBox");








/*=====
	dijit.form.ValidationTextBox.__Constraints = function(){
		// locale: String
		//		locale used for validation, picks up value from this widget's lang attribute
		// _flags_: anything
		//		various flags passed to regExpGen function
		this.locale = "";
		this._flags_ = "";
	}
=====*/

dojo.declare(
	"dijit.form.ValidationTextBox",
	dijit.form.TextBox,
	{
		// summary:
		//		A TextBox subclass with the ability to validate content of various types and provide user feedback.

		templateString:"<div class=\"dijit dijitReset dijitInlineTable dijitLeft\"\n\tid=\"widget_${id}\"\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\" waiRole=\"presentation\"\n\t><div style=\"overflow:hidden;\"\n\t\t><div class=\"dijitReset dijitValidationIcon\"><br></div\n\t\t><div class=\"dijitReset dijitValidationIconText\">&Chi;</div\n\t\t><div class=\"dijitReset dijitInputField\"\n\t\t\t><input class=\"dijitReset\" dojoAttachPoint='textbox,focusNode' dojoAttachEvent='onfocus:_update,onkeyup:_update,onblur:_onMouse,onkeypress:_onKeyPress' autocomplete=\"off\"\n\t\t\ttype='${type}' name='${name}'\n\t\t/></div\n\t></div\n></div>\n",
		baseClass: "dijitTextBox",

		// default values for new subclass properties
		// required: Boolean
		//		Can be true or false, default is false.
		required: false,

		// promptMessage: String
		//		Hint string
		promptMessage: "",

		// invalidMessage: String
		// 		The message to display if value is invalid.
		invalidMessage: "$_unset_$", // read from the message file if not overridden

		// constraints: dijit.form.ValidationTextBox.__Constraints
		//		user-defined object needed to pass parameters to the validator functions
		constraints: {},

		// regExp: String
		//		regular expression string used to validate the input
		//		Do not specify both regExp and regExpGen
		regExp: ".*",

		// regExpGen: Function
		//		user replaceable function used to generate regExp when dependent on constraints
		//		Do not specify both regExp and regExpGen
		regExpGen: function(/*dijit.form.ValidationTextBox.__Constraints*/constraints){ return this.regExp; },

		// state: String
		//		Shows current state (ie, validation result) of input (Normal, Warning, or Error)
		state: "",

		//	tooltipPosition: String[]
		//		See description of dijit.Tooltip.defaultPosition for details on this parameter.
		tooltipPosition: [],

		_setValueAttr: function(){
			// summary:
			//		Hook so attr('value', ...) works.
			this.inherited(arguments);
			this.validate(this._focused);
		},

		validator: function(/*anything*/value, /*dijit.form.ValidationTextBox.__Constraints*/constraints){
			// summary: user replaceable function used to validate the text input against the regular expression.
			return (new RegExp("^(?:" + this.regExpGen(constraints) + ")"+(this.required?"":"?")+"$")).test(value) &&
				(!this.required || !this._isEmpty(value)) &&
				(this._isEmpty(value) || this.parse(value, constraints) !== undefined); // Boolean
		},

		_isValidSubset: function(){
			// summary:
			//	Returns true if the value is either already valid or could be made valid by appending characters.
			return this.textbox.value.search(this._partialre) == 0;
		},

		isValid: function(/*Boolean*/ isFocused){
			// summary: Need to over-ride with your own validation code in subclasses
			return this.validator(this.textbox.value, this.constraints);
		},

		_isEmpty: function(value){
			// summary: Checks for whitespace
			return /^\s*$/.test(value); // Boolean
		},

		getErrorMessage: function(/*Boolean*/ isFocused){
			// summary: return an error message to show if appropriate
			return this.invalidMessage; // String
		},

		getPromptMessage: function(/*Boolean*/ isFocused){
			// summary: return a hint to show if appropriate
			return this.promptMessage; // String
		},

		_maskValidSubsetError: true,
		validate: function(/*Boolean*/ isFocused){
			// summary:
			//		Called by oninit, onblur, and onkeypress.
			// description:
			//		Show missing or invalid messages if appropriate, and highlight textbox field.
			var message = "";
			var isValid = this.disabled || this.isValid(isFocused);
			if(isValid){ this._maskValidSubsetError = true; }
			var isValidSubset = !isValid && isFocused && this._isValidSubset();
			var isEmpty = this._isEmpty(this.textbox.value);
			this.state = (isValid || (!this._hasBeenBlurred && isEmpty) || isValidSubset) ? "" : "Error";
			if(this.state == "Error"){ this._maskValidSubsetError = false; }
			this._setStateClass();
			dijit.setWaiState(this.focusNode, "invalid", isValid ? "false" : "true");
			if(isFocused){
				if(isEmpty){
					message = this.getPromptMessage(true);
				}
				if(!message && (this.state == "Error" || (isValidSubset && !this._maskValidSubsetError))){
					message = this.getErrorMessage(true);
				}
			}
			this.displayMessage(message);
			return isValid;
		},

		// currently displayed message
		_message: "",

		displayMessage: function(/*String*/ message){
			// summary:
			//		User overridable method to display validation errors/hints.
			//		By default uses a tooltip.
			if(this._message == message){ return; }
			this._message = message;
			dijit.hideTooltip(this.domNode);
			if(message){
				dijit.showTooltip(message, this.domNode, this.tooltipPosition);
			}
		},

		_refreshState: function(){
			this.validate(this._focused);
		},

		_update: function(/*Event*/e){
			this._refreshState();
			this._onMouse(e);	// update CSS classes
		},

		//////////// INITIALIZATION METHODS ///////////////////////////////////////

		constructor: function(){
			this.constraints = {};
		},

		postMixInProperties: function(){
			this.inherited(arguments);
			this.constraints.locale = this.lang;
			this.messages = dojo.i18n.getLocalization("dijit.form", "validate", this.lang);
			if(this.invalidMessage == "$_unset_$"){ this.invalidMessage = this.messages.invalidMessage; }
			var p = this.regExpGen(this.constraints);
			this.regExp = p;
			var partialre = "";
			// parse the regexp and produce a new regexp that matches valid subsets
			// if the regexp is .* then there's no use in matching subsets since everything is valid
			if(p != ".*"){ this.regExp.replace(/\\.|\[\]|\[.*?[^\\]{1}\]|\{.*?\}|\(\?[=:!]|./g,
				function (re){
					switch(re.charAt(0)){
						case '{':
						case '+':
						case '?':
						case '*':
						case '^':
						case '$':
						case '|':
						case '(': partialre += re; break;
						case ")": partialre += "|$)"; break;
						 default: partialre += "(?:"+re+"|$)"; break;
					}
				}
			);}
			try{ // this is needed for now since the above regexp parsing needs more test verification
				"".search(partialre);
			}catch(e){ // should never be here unless the original RE is bad or the parsing is bad
				partialre = this.regExp;
				
			} // should never be here unless the original RE is bad or the parsing is bad
			this._partialre = "^(?:" + partialre + ")$";
		},

		_setDisabledAttr: function(/*Boolean*/ value){
			this.inherited(arguments);	// call FormValueWidget._setDisabledAttr()
					if(this.valueNode){
				this.valueNode.disabled = value;
					}
					this._refreshState();
		},
		
		_setRequiredAttr: function(/*Boolean*/ value){
			this.required = value;
			dijit.setWaiState(this.focusNode,"required", value);
					this._refreshState();				
		},

		postCreate: function(){
			if(dojo.isIE){ // IE INPUT tag fontFamily has to be set directly using STYLE
				var s = dojo.getComputedStyle(this.focusNode);
				if(s){
					var ff = s.fontFamily;
					if(ff){
						this.focusNode.style.fontFamily = ff;
					}
				}
			}
			this.inherited(arguments);
		}
	}
);

dojo.declare(
	"dijit.form.MappedTextBox",
	dijit.form.ValidationTextBox,
	{
		// summary:
		//		A dijit.form.ValidationTextBox subclass which provides a visible formatted display and a serializable
		//		value in a hidden input field which is actually sent to the server.  The visible display may
		//		be locale-dependent and interactive.  The value sent to the server is stored in a hidden
		//		input field which uses the `name` attribute declared by the original widget.  That value sent
		//		to the serveris defined by the dijit.form.MappedTextBox.serialize method and is typically
		//		locale-neutral.

		serialize: function(/*anything*/val, /*Object?*/options){
			// summary: user replaceable function used to convert the attr('value') result to a String
			return val.toString ? val.toString() : ""; // String
		},

		toString: function(){
			// summary: display the widget as a printable string using the widget's value
			
			// TODO: seems like the filter() call here is unnecessary as attr('value') should do that
			var val = this.filter(this.attr('value'));
			return val != null ? (typeof val == "string" ? val : this.serialize(val, this.constraints)) : ""; // String
		},

		validate: function(){
			this.valueNode.value = this.toString();
			return this.inherited(arguments);
		},

		buildRendering: function(){
			this.inherited(arguments);

			// Create a hidden <input> node with the serialized value used for submit
			// (as opposed to the displayed value)
			var textbox = this.textbox;
			var valueNode = (this.valueNode = dojo.doc.createElement("input"));
			valueNode.setAttribute("type", textbox.type);
			dojo.style(valueNode, "display", "none");
			this.valueNode.name = this.textbox.name;
			dojo.place(valueNode, textbox, "after");

			// try to give the displayed node a different name, or ideally
			// remove that attribute altogether
			this.textbox.name = this.textbox.name + "_displayed_";
			this.textbox.removeAttribute("name");
		},

		_setDisabledAttr: function(/*Boolean*/ value){
			this.inherited(arguments);
			dojo.attr(this.valueNode, 'disabled', value);
		}
	}
);

/*=====
	dijit.form.RangeBoundTextBox.__Constraints = function(){
		// min: Number
		//		Minimum signed value.  Default is -Infinity
		// max: Number
		//		Maximum signed value.  Default is +Infinity
		this.min = min;
		this.max = max;
	}
=====*/

dojo.declare(
	"dijit.form.RangeBoundTextBox",
	dijit.form.MappedTextBox,
	{
		// summary:
		//		A dijit.form.MappedTextBox subclass which defines a range of valid values
		//
		// constraints: dijit.form.RangeBoundTextBox.__Constraints
		//
		// rangeMessage: String
		//		The message to display if value is out-of-range

		/*=====
		constraints: {},
		======*/
		rangeMessage: "",

		rangeCheck: function(/*Number*/ primitive, /*dijit.form.RangeBoundTextBox.__Constraints*/ constraints){
			// summary: user replaceable function used to validate the range of the numeric input value
			var isMin = "min" in constraints;
			var isMax = "max" in constraints;
			if(isMin || isMax){
				return (!isMin || this.compare(primitive,constraints.min) >= 0) &&
					(!isMax || this.compare(primitive,constraints.max) <= 0);
			}
			return true; // Boolean
		},

		isInRange: function(/*Boolean*/ isFocused){
			// summary: Need to over-ride with your own validation code in subclasses
			return this.rangeCheck(this.attr('value'), this.constraints);
		},

		_isDefinitelyOutOfRange: function(){
			// summary:
			//	Returns true if the value is out of range and will remain
			//	out of range even if the user types more characters
			var val = this.attr('value');
			var isTooLittle = false;
			var isTooMuch = false;
			if("min" in this.constraints){
				var min = this.constraints.min;
				val = this.compare(val, ((typeof min == "number") && min >= 0 && val !=0)? 0 : min);
				isTooLittle = (typeof val == "number") && val < 0;
			}
			if("max" in this.constraints){
				var max = this.constraints.max;
				val = this.compare(val, ((typeof max != "number") || max > 0)? max : 0);
				isTooMuch = (typeof val == "number") && val > 0;
			}
			return isTooLittle || isTooMuch;
		},

		_isValidSubset: function(){
			return this.inherited(arguments) && !this._isDefinitelyOutOfRange();
		},

		isValid: function(/*Boolean*/ isFocused){
			return this.inherited(arguments) &&
				((this._isEmpty(this.textbox.value) && !this.required) || this.isInRange(isFocused)); // Boolean
		},

		getErrorMessage: function(/*Boolean*/ isFocused){
			if(dijit.form.RangeBoundTextBox.superclass.isValid.call(this, false) && !this.isInRange(isFocused)){ return this.rangeMessage; } // String
			return this.inherited(arguments);
		},

		postMixInProperties: function(){
			this.inherited(arguments);
			if(!this.rangeMessage){
				this.messages = dojo.i18n.getLocalization("dijit.form", "validate", this.lang);
				this.rangeMessage = this.messages.rangeMessage;
			}
		},

		postCreate: function(){
			this.inherited(arguments);
			if(this.constraints.min !== undefined){
				dijit.setWaiState(this.focusNode, "valuemin", this.constraints.min);
			}
			if(this.constraints.max !== undefined){
				dijit.setWaiState(this.focusNode, "valuemax", this.constraints.max);
			}
		},
		
		_setValueAttr: function(/*Number*/ value, /*Boolean?*/ priorityChange){
			// summary:
			//		Hook so attr('value', ...) works.
			dijit.setWaiState(this.focusNode, "valuenow", value);
			this.inherited(arguments);
		}
	}
);

}

if(!dojo._hasResource["dojo.data.util.sorter"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.util.sorter"] = true;
dojo.provide("dojo.data.util.sorter");

dojo.data.util.sorter.basicComparator = function(	/*anything*/ a, 
													/*anything*/ b){
	//	summary:  
	//		Basic comparision function that compares if an item is greater or less than another item
	//	description:  
	//		returns 1 if a > b, -1 if a < b, 0 if equal.
	//		undefined values are treated as larger values so that they're pushed to the end of the list.

	var ret = 0;
	if(a > b || typeof a === "undefined" || a === null){
		ret = 1;
	}else if(a < b || typeof b === "undefined" || b === null){
		ret = -1;
	}
	return ret; //int, {-1,0,1}
};

dojo.data.util.sorter.createSortFunction = function(	/* attributes array */sortSpec,
														/*dojo.data.core.Read*/ store){
	//	summary:  
	//		Helper function to generate the sorting function based off the list of sort attributes.
	//	description:  
	//		The sort function creation will look for a property on the store called 'comparatorMap'.  If it exists
	//		it will look in the mapping for comparisons function for the attributes.  If one is found, it will
	//		use it instead of the basic comparator, which is typically used for strings, ints, booleans, and dates.
	//		Returns the sorting function for this particular list of attributes and sorting directions.
	//
	//	sortSpec: array
	//		A JS object that array that defines out what attribute names to sort on and whether it should be descenting or asending.
	//		The objects should be formatted as follows:
	//		{
	//			attribute: "attributeName-string" || attribute,
	//			descending: true|false;   // Default is false.
	//		}
	//	store: object
	//		The datastore object to look up item values from.
	//
	var sortFunctions=[];   

	function createSortFunction(attr, dir){
		return function(itemA, itemB){
			var a = store.getValue(itemA, attr);
			var b = store.getValue(itemB, attr);
			//See if we have a override for an attribute comparison.
			var comparator = null;
			if(store.comparatorMap){
				if(typeof attr !== "string"){
					 attr = store.getIdentity(attr);
				}
				comparator = store.comparatorMap[attr]||dojo.data.util.sorter.basicComparator;
			}
			comparator = comparator||dojo.data.util.sorter.basicComparator; 
			return dir * comparator(a,b); //int
		};
	}
	var sortAttribute;
	for(var i = 0; i < sortSpec.length; i++){
		sortAttribute = sortSpec[i];
		if(sortAttribute.attribute){
			var direction = (sortAttribute.descending) ? -1 : 1;
			sortFunctions.push(createSortFunction(sortAttribute.attribute, direction));
		}
	}

	return function(rowA, rowB){
		var i=0;
		while(i < sortFunctions.length){
			var ret = sortFunctions[i++](rowA, rowB);
			if(ret !== 0){
				return ret;//int
			}
		}
		return 0; //int  
	};  //  Function
};

}

if(!dojo._hasResource["dojo.data.util.simpleFetch"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.util.simpleFetch"] = true;
dojo.provide("dojo.data.util.simpleFetch");


dojo.data.util.simpleFetch.fetch = function(/* Object? */ request){
	//	summary:
	//		The simpleFetch mixin is designed to serve as a set of function(s) that can
	//		be mixed into other datastore implementations to accelerate their development.  
	//		The simpleFetch mixin should work well for any datastore that can respond to a _fetchItems() 
	//		call by returning an array of all the found items that matched the query.  The simpleFetch mixin
	//		is not designed to work for datastores that respond to a fetch() call by incrementally
	//		loading items, or sequentially loading partial batches of the result
	//		set.  For datastores that mixin simpleFetch, simpleFetch 
	//		implements a fetch method that automatically handles eight of the fetch()
	//		arguments -- onBegin, onItem, onComplete, onError, start, count, sort and scope
	//		The class mixing in simpleFetch should not implement fetch(),
	//		but should instead implement a _fetchItems() method.  The _fetchItems() 
	//		method takes three arguments, the keywordArgs object that was passed 
	//		to fetch(), a callback function to be called when the result array is
	//		available, and an error callback to be called if something goes wrong.
	//		The _fetchItems() method should ignore any keywordArgs parameters for
	//		start, count, onBegin, onItem, onComplete, onError, sort, and scope.  
	//		The _fetchItems() method needs to correctly handle any other keywordArgs
	//		parameters, including the query parameter and any optional parameters 
	//		(such as includeChildren).  The _fetchItems() method should create an array of 
	//		result items and pass it to the fetchHandler along with the original request object 
	//		-- or, the _fetchItems() method may, if it wants to, create an new request object 
	//		with other specifics about the request that are specific to the datastore and pass 
	//		that as the request object to the handler.
	//
	//		For more information on this specific function, see dojo.data.api.Read.fetch()
	request = request || {};
	if(!request.store){
		request.store = this;
	}
	var self = this;

	var _errorHandler = function(errorData, requestObject){
		if(requestObject.onError){
			var scope = requestObject.scope || dojo.global;
			requestObject.onError.call(scope, errorData, requestObject);
		}
	};

	var _fetchHandler = function(items, requestObject){
		var oldAbortFunction = requestObject.abort || null;
		var aborted = false;

		var startIndex = requestObject.start?requestObject.start:0;
		var endIndex   = (requestObject.count && (requestObject.count !== Infinity))?(startIndex + requestObject.count):items.length;

		requestObject.abort = function(){
			aborted = true;
			if(oldAbortFunction){
				oldAbortFunction.call(requestObject);
			}
		};

		var scope = requestObject.scope || dojo.global;
		if(!requestObject.store){
			requestObject.store = self;
		}
		if(requestObject.onBegin){
			requestObject.onBegin.call(scope, items.length, requestObject);
		}
		if(requestObject.sort){
			items.sort(dojo.data.util.sorter.createSortFunction(requestObject.sort, self));
		}
		if(requestObject.onItem){
			for(var i = startIndex; (i < items.length) && (i < endIndex); ++i){
				var item = items[i];
				if(!aborted){
					requestObject.onItem.call(scope, item, requestObject);
				}
			}
		}
		if(requestObject.onComplete && !aborted){
			var subset = null;
			if (!requestObject.onItem) {
				subset = items.slice(startIndex, endIndex);
			}
			requestObject.onComplete.call(scope, subset, requestObject);   
		}
	};
	this._fetchItems(request, _fetchHandler, _errorHandler);
	return request;	// Object
};

}

if(!dojo._hasResource["dojo.data.util.filter"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.data.util.filter"] = true;
dojo.provide("dojo.data.util.filter");

dojo.data.util.filter.patternToRegExp = function(/*String*/pattern, /*boolean?*/ ignoreCase){
	//	summary:  
	//		Helper function to convert a simple pattern to a regular expression for matching.
	//	description:
	//		Returns a regular expression object that conforms to the defined conversion rules.
	//		For example:  
	//			ca*   -> /^ca.*$/
	//			*ca*  -> /^.*ca.*$/
	//			*c\*a*  -> /^.*c\*a.*$/
	//			*c\*a?*  -> /^.*c\*a..*$/
	//			and so on.
	//
	//	pattern: string
	//		A simple matching pattern to convert that follows basic rules:
	//			* Means match anything, so ca* means match anything starting with ca
	//			? Means match single character.  So, b?b will match to bob and bab, and so on.
	//      	\ is an escape character.  So for example, \* means do not treat * as a match, but literal character *.
	//				To use a \ as a character in the string, it must be escaped.  So in the pattern it should be 
	//				represented by \\ to be treated as an ordinary \ character instead of an escape.
	//
	//	ignoreCase:
	//		An optional flag to indicate if the pattern matching should be treated as case-sensitive or not when comparing
	//		By default, it is assumed case sensitive.

	var rxp = "^";
	var c = null;
	for(var i = 0; i < pattern.length; i++){
		c = pattern.charAt(i);
		switch (c) {
			case '\\':
				rxp += c;
				i++;
				rxp += pattern.charAt(i);
				break;
			case '*':
				rxp += ".*"; break;
			case '?':
				rxp += "."; break;
			case '$':
			case '^':
			case '/':
			case '+':
			case '.':
			case '|':
			case '(':
			case ')':
			case '{':
			case '}':
			case '[':
			case ']':
				rxp += "\\"; //fallthrough
			default:
				rxp += c;
		}
	}
	rxp += "$";
	if(ignoreCase){
		return new RegExp(rxp,"mi"); //RegExp
	}else{
		return new RegExp(rxp,"m"); //RegExp
	}
	
};

}

if(!dojo._hasResource["dijit.form.ComboBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.ComboBox"] = true;
dojo.provide("dijit.form.ComboBox");







dojo.declare(
	"dijit.form.ComboBoxMixin",
	null,
	{
		// summary:
		//		Implements the base functionality for ComboBox/FilteringSelect
		// description:
		//		All widgets that mix in dijit.form.ComboBoxMixin must extend dijit.form._FormValueWidget

		// item: Object
		//		This is the item returned by the dojo.data.store implementation that
		//		provides the data for this cobobox, it's the currently selected item.
		item: null,

		// pageSize: Integer
		//		Argument to data provider.
		//		Specifies number of search results per page (before hitting "next" button)
		pageSize: Infinity,

		// store: Object
		//		Reference to data provider object used by this ComboBox
		store: null,

		// fetchProperties: Object
		//		Mixin to the dojo.data store's fetch.
		//		For example, to set the sort order of the ComboBox menu, pass:
		//		{sort:{attribute:"name",descending:true}}
		fetchProperties:{},

		// query: Object
		//		A query that can be passed to 'store' to initially filter the items,
		//		before doing further filtering based on `searchAttr` and the key.
		//		Any reference to the `searchAttr` is ignored.
		query: {},

		// autoComplete: Boolean
		//		If you type in a partial string, and then tab out of the `<input>` box,
		//		automatically copy the first entry displayed in the drop down list to
		//		the `<input>` field
		autoComplete: true,

		// highlightMatch: String
		// 		One of: "first", "all" or "none".
		//		If the ComboBox opens with the serach results and the searched
		//		string can be found it will be highlighted.
		//		This value is not considered when labelType!="text" to not
		//		screw up any mark up the label might contain.
		highlightMatch: "first",
		
		// searchDelay: Integer
		//		Delay in milliseconds between when user types something and we start
		//		searching based on that value
		searchDelay: 100,

		// searchAttr: String
		//		Searches pattern match against this field
		searchAttr: "name",

		// labelAttr: String
		//		Optional.  The text that actually appears in the drop down.
		//		If not specified, the searchAttr text is used instead.
		labelAttr: "",

		// labelType: String
		//		"html" or "text"
		labelType: "text",

		// queryExpr: String
		//		dojo.data query expression pattern.
		//		`${0}` will be substituted for the user text.
		//		`*` is used for wildcards.
		//		`${0}*` means "starts with", `*${0}*` means "contains", `${0}` means "is"
		queryExpr: "${0}*",

		// ignoreCase: Boolean
		//		Set true if the ComboBox should ignore case when matching possible items
		ignoreCase: true,

		// hasDownArrow: Boolean
		//		Set this textbox to have a down arrow button.
		//		Defaults to true.
		hasDownArrow:true,

		templateString:"<div class=\"dijit dijitReset dijitInlineTable dijitLeft\"\n\tid=\"widget_${id}\"\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\" dojoAttachPoint=\"comboNode\" waiRole=\"combobox\" tabIndex=\"-1\"\n\t><div style=\"overflow:hidden;\"\n\t\t><div class='dijitReset dijitRight dijitButtonNode dijitArrowButton dijitDownArrowButton'\n\t\t\tdojoAttachPoint=\"downArrowNode\" waiRole=\"presentation\"\n\t\t\tdojoAttachEvent=\"onmousedown:_onArrowMouseDown,onmouseup:_onMouse,onmouseenter:_onMouse,onmouseleave:_onMouse\"\n\t\t\t><div class=\"dijitArrowButtonInner\">&thinsp;</div\n\t\t\t><div class=\"dijitArrowButtonChar\">&#9660;</div\n\t\t></div\n\t\t><div class=\"dijitReset dijitValidationIcon\"><br></div\n\t\t><div class=\"dijitReset dijitValidationIconText\">&Chi;</div\n\t\t><div class=\"dijitReset dijitInputField\"\n\t\t\t><input type=\"text\" autocomplete=\"off\" name=\"${name}\" class='dijitReset'\n\t\t\tdojoAttachEvent=\"onkeypress:_onKeyPress, onfocus:_update, compositionend\"\n\t\t\tdojoAttachPoint=\"textbox,focusNode\" waiRole=\"textbox\" waiState=\"haspopup-true,autocomplete-list\"\n\t\t/></div\n\t></div\n></div>\n",

		baseClass:"dijitComboBox",

		_getCaretPos: function(/*DomNode*/ element){
			// khtml 3.5.2 has selection* methods as does webkit nightlies from 2005-06-22
			var pos = 0;
			if(typeof(element.selectionStart)=="number"){
				// FIXME: this is totally borked on Moz < 1.3. Any recourse?
				pos = element.selectionStart;
			}else if(dojo.isIE){
				// in the case of a mouse click in a popup being handled,
				// then the dojo.doc.selection is not the textarea, but the popup
				// var r = dojo.doc.selection.createRange();
				// hack to get IE 6 to play nice. What a POS browser.
				var tr = dojo.doc.selection.createRange().duplicate();
				var ntr = element.createTextRange();
				tr.move("character",0);
				ntr.move("character",0);
				try{
					// If control doesnt have focus, you get an exception.
					// Seems to happen on reverse-tab, but can also happen on tab (seems to be a race condition - only happens sometimes).
					// There appears to be no workaround for this - googled for quite a while.
					ntr.setEndPoint("EndToEnd", tr);
					pos = String(ntr.text).replace(/\r/g,"").length;
				}catch(e){
					// If focus has shifted, 0 is fine for caret pos.
				}
			}
			return pos;
		},

		_setCaretPos: function(/*DomNode*/ element, /*Number*/ location){
			location = parseInt(location);
			dijit.selectInputText(element, location, location);
		},

		_setDisabledAttr: function(/*Boolean*/ value){
			// summary:
			//		Call this from superclass as part of _setDisabledAttr() method.
			//		Superclass _must_ define _setDisabledAttr().
			// description:
			//		Additional code to set disabled state of combobox node
				dijit.setWaiState(this.comboNode, "disabled", value);
		},	
		
		_onKeyPress: function(/*Event*/ evt){
			// summary: handles keyboard events
			var key = evt.charOrCode;
			//except for cutting/pasting case - ctrl + x/v
			if(evt.altKey || (evt.ctrlKey && (key != 'x' && key != 'v')) || evt.key == dojo.keys.SHIFT){
				return; // throw out weird key combinations and spurious events
			}
			var doSearch = false;
			var pw = this._popupWidget;
			var dk = dojo.keys;
			if(this._isShowingNow){
				pw.handleKey(key);
			}
			switch(key){
				case dk.PAGE_DOWN:
				case dk.DOWN_ARROW:
					if(!this._isShowingNow||this._prev_key_esc){
						this._arrowPressed();
						doSearch=true;
					}else{
						this._announceOption(pw.getHighlightedOption());
					}
					dojo.stopEvent(evt);
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					break;

				case dk.PAGE_UP:
				case dk.UP_ARROW:
					if(this._isShowingNow){
						this._announceOption(pw.getHighlightedOption());
					}
					dojo.stopEvent(evt);
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					break;

				case dk.ENTER:
					// prevent submitting form if user presses enter. Also
					// prevent accepting the value if either Next or Previous
					// are selected
					var highlighted;
					if(this._isShowingNow && 
						(highlighted = pw.getHighlightedOption())
					){
						// only stop event on prev/next
						if(highlighted == pw.nextButton){
							this._nextSearch(1);
							dojo.stopEvent(evt);
							break;
						}else if(highlighted == pw.previousButton){
							this._nextSearch(-1);
							dojo.stopEvent(evt);
							break;
						}
					}else{
						// Update 'value' (ex: KY) according to currently displayed text
						this._setDisplayedValueAttr(this.attr('displayedValue'), true);
					}
					// default case:
					// prevent submit, but allow event to bubble
					evt.preventDefault();
					// fall through

				case dk.TAB:
					var newvalue = this.attr('displayedValue');
					// #4617: 
					//		if the user had More Choices selected fall into the
					//		_onBlur handler
					if(pw && (
						newvalue == pw._messages["previousMessage"] ||
						newvalue == pw._messages["nextMessage"])
					){
						break;
					}
					if(this._isShowingNow){
						this._prev_key_backspace = false;
						this._prev_key_esc = false;
						if(pw.getHighlightedOption()){
							pw.attr('value', { target: pw.getHighlightedOption() });
						}
						this._lastQuery = null; // in case results come back later
						this._hideResultList();
					}
					break;

				case ' ':
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					if(this._isShowingNow && pw.getHighlightedOption()){
						dojo.stopEvent(evt);
						this._selectOption();
						this._hideResultList();
					}else{
						doSearch = true;
					}
					break;

				case dk.ESCAPE:
					this._prev_key_backspace = false;
					this._prev_key_esc = true;
					if(this._isShowingNow){
						dojo.stopEvent(evt);
						this._hideResultList();
					}else{
						this.inherited(arguments);
					}
					break;

				case dk.DELETE:
				case dk.BACKSPACE:
					this._prev_key_esc = false;
					this._prev_key_backspace = true;
					doSearch = true;
					break;

				case dk.RIGHT_ARROW: // fall through
				case dk.LEFT_ARROW: 
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					break;

				default: // non char keys (F1-F12 etc..)  shouldn't open list
					this._prev_key_backspace = false;
					this._prev_key_esc = false;
					doSearch = typeof key == 'string';
			}
			if(this.searchTimer){
				clearTimeout(this.searchTimer);
			}
			if(doSearch){
				// need to wait a tad before start search so that the event
				// bubbles through DOM and we have value visible
				setTimeout(dojo.hitch(this, "_startSearchFromInput"),1);
			}
		},

		_autoCompleteText: function(/*String*/ text){
			// summary:
			// 		Fill in the textbox with the first item from the drop down
			// 		list, and highlight the characters that were
			// 		auto-completed. For example, if user typed "CA" and the
			// 		drop down list appeared, the textbox would be changed to
			// 		"California" and "ifornia" would be highlighted.

			var fn = this.focusNode;

			// IE7: clear selection so next highlight works all the time
			dijit.selectInputText(fn, fn.value.length);
			// does text autoComplete the value in the textbox?
			var caseFilter = this.ignoreCase? 'toLowerCase' : 'substr';
			if(text[caseFilter](0).indexOf(this.focusNode.value[caseFilter](0)) == 0){
				var cpos = this._getCaretPos(fn);
				// only try to extend if we added the last character at the end of the input
				if((cpos+1) > fn.value.length){
					// only add to input node as we would overwrite Capitalisation of chars
					// actually, that is ok
					fn.value = text;//.substr(cpos);
					// visually highlight the autocompleted characters
					dijit.selectInputText(fn, cpos);
				}
			}else{
				// text does not autoComplete; replace the whole value and highlight
				fn.value = text;
				dijit.selectInputText(fn);
			}
		},

		_openResultList: function(/*Object*/ results, /*Object*/ dataObject){
			if(	this.disabled || 
				this.readOnly || 
				(dataObject.query[this.searchAttr] != this._lastQuery)
			){
				return;
			}
			this._popupWidget.clearResultList();
			if(!results.length){
				this._hideResultList();
				return;
			}

			// Fill in the textbox with the first item from the drop down list,
			// and highlight the characters that were auto-completed. For
			// example, if user typed "CA" and the drop down list appeared, the
			// textbox would be changed to "California" and "ifornia" would be
			// highlighted.

			var zerothvalue = new String(this.store.getValue(results[0], this.searchAttr));
			if(zerothvalue && this.autoComplete && !this._prev_key_backspace &&
				(dataObject.query[this.searchAttr] != "*")){
				// when the user clicks the arrow button to show the full list,
				// startSearch looks for "*".
				// it does not make sense to autocomplete
				// if they are just previewing the options available.
				this._autoCompleteText(zerothvalue);
			}
			dataObject._maxOptions = this._maxOptions;
			this._popupWidget.createOptions(
				results, 
				dataObject, 
				dojo.hitch(this, "_getMenuLabelFromItem")
			);

			// show our list (only if we have content, else nothing)
			this._showResultList();

			// #4091:
			//		tell the screen reader that the paging callback finished by
			//		shouting the next choice
			if(dataObject.direction){
				if(1 == dataObject.direction){
					this._popupWidget.highlightFirstOption();
				}else if(-1 == dataObject.direction){
					this._popupWidget.highlightLastOption();
				}
				this._announceOption(this._popupWidget.getHighlightedOption());
			}
		},

		_showResultList: function(){
			this._hideResultList();
			var items = this._popupWidget.getItems(),
				visibleCount = Math.min(items.length,this.maxListLength);
			this._arrowPressed();
			// hide the tooltip
			this.displayMessage("");
			
			// Position the list and if it's too big to fit on the screen then
			// size it to the maximum possible height
			// Our dear friend IE doesnt take max-height so we need to
			// calculate that on our own every time

			// TODO: want to redo this, see 
			//		http://trac.dojotoolkit.org/ticket/3272
			//	and
			//		http://trac.dojotoolkit.org/ticket/4108


			// natural size of the list has changed, so erase old
			// width/height settings, which were hardcoded in a previous
			// call to this function (via dojo.marginBox() call)
			dojo.style(this._popupWidget.domNode, {width: "", height: ""});

			var best = this.open();
			// #3212:
			//		only set auto scroll bars if necessary prevents issues with
			//		scroll bars appearing when they shouldn't when node is made
			//		wider (fractional pixels cause this)
			var popupbox = dojo.marginBox(this._popupWidget.domNode);
			this._popupWidget.domNode.style.overflow = 
				((best.h==popupbox.h)&&(best.w==popupbox.w)) ? "hidden" : "auto";
			// #4134:
			//		borrow TextArea scrollbar test so content isn't covered by
			//		scrollbar and horizontal scrollbar doesn't appear
			var newwidth = best.w;
			if(best.h < this._popupWidget.domNode.scrollHeight){
				newwidth += 16;
			}
			dojo.marginBox(this._popupWidget.domNode, {
				h: best.h,
				w: Math.max(newwidth, this.domNode.offsetWidth)
			});
			dijit.setWaiState(this.comboNode, "expanded", "true");
		},

		_hideResultList: function(){
			if(this._isShowingNow){
				dijit.popup.close(this._popupWidget);
				this._arrowIdle();
				this._isShowingNow=false;
				dijit.setWaiState(this.comboNode, "expanded", "false");
				dijit.removeWaiState(this.focusNode,"activedescendant");
			}
		},

		_setBlurValue: function(){
			// if the user clicks away from the textbox OR tabs away, set the
			// value to the textbox value
			// #4617: 
			//		if value is now more choices or previous choices, revert
			//		the value
			var newvalue=this.attr('displayedValue');
			var pw = this._popupWidget;
			if(pw && (
				newvalue == pw._messages["previousMessage"] ||
				newvalue == pw._messages["nextMessage"]
				)
			){
				this._setValueAttr(this._lastValueReported, true);
			}else{
				// Update 'value' (ex: KY) according to currently displayed text
				this.attr('displayedValue', newvalue);
			}
		},

		_onBlur: function(){
			// summary: called magically when focus has shifted away from this widget and it's dropdown
			this._hideResultList();
			this._arrowIdle();
			this.inherited(arguments);
		},

		_announceOption: function(/*Node*/ node){
			// summary:
			//		a11y code that puts the highlighted option in the textbox
			//		This way screen readers will know what is happening in the
			//		menu

			if(node == null){
				return;
			}
			// pull the text value from the item attached to the DOM node
			var newValue;
			if( node == this._popupWidget.nextButton ||
				node == this._popupWidget.previousButton){
				newValue = node.innerHTML;
			}else{
				newValue = this.store.getValue(node.item, this.searchAttr);
			}
			// get the text that the user manually entered (cut off autocompleted text)
			this.focusNode.value = this.focusNode.value.substring(0, this._getCaretPos(this.focusNode));
			//set up ARIA activedescendant
			dijit.setWaiState(this.focusNode, "activedescendant", dojo.attr(node, "id")); 
			// autocomplete the rest of the option to announce change
			this._autoCompleteText(newValue);
		},

		_selectOption: function(/*Event*/ evt){
			var tgt = null;
			if(!evt){
				evt ={ target: this._popupWidget.getHighlightedOption()};
			}
				// what if nothing is highlighted yet?
			if(!evt.target){
				// handle autocompletion where the the user has hit ENTER or TAB
				this.attr('displayedValue', this.attr('displayedValue'));
				return;
			// otherwise the user has accepted the autocompleted value
			}else{
				tgt = evt.target;
			}
			if(!evt.noHide){
				this._hideResultList();
				this._setCaretPos(this.focusNode, this.store.getValue(tgt.item, this.searchAttr).length);
			}
			this._doSelect(tgt);
		},

		_doSelect: function(tgt){
			this.item = tgt.item;
			this.attr('value', this.store.getValue(tgt.item, this.searchAttr));
		},

		_onArrowMouseDown: function(evt){
			// summary: callback when arrow is clicked
			if(this.disabled || this.readOnly){
				return;
			}
			dojo.stopEvent(evt);
			this.focus();
			if(this._isShowingNow){
				this._hideResultList();
			}else{
				// forces full population of results, if they click
				// on the arrow it means they want to see more options
				this._startSearch("");
			}
		},

		_startSearchFromInput: function(){
			this._startSearch(this.focusNode.value.replace(/([\\\*\?])/g, "\\$1"));
		},

		_getQueryString: function(/*String*/ text){
			return dojo.string.substitute(this.queryExpr, [text]);
		},

		_startSearch: function(/*String*/ key){
			if(!this._popupWidget){
				var popupId = this.id + "_popup";
				this._popupWidget = new dijit.form._ComboBoxMenu({
					onChange: dojo.hitch(this, this._selectOption),
					id:popupId
				});
				dijit.removeWaiState(this.focusNode,"activedescendant");
				dijit.setWaiState(this.textbox,"owns",popupId); // associate popup with textbox
			}
			// create a new query to prevent accidentally querying for a hidden
			// value from FilteringSelect's keyField
			this.item = null; // #4872
			var query = dojo.clone(this.query); // #5970
			this._lastInput = key; // Store exactly what was entered by the user.
			this._lastQuery = query[this.searchAttr] = this._getQueryString(key);
			// #5970: set _lastQuery, *then* start the timeout
			// otherwise, if the user types and the last query returns before the timeout,
			// _lastQuery won't be set and their input gets rewritten
			this.searchTimer=setTimeout(dojo.hitch(this, function(query, _this){
				var fetch = {
					queryOptions: {
						ignoreCase: this.ignoreCase, 
						deep: true
					},
					query: query,
					onBegin: dojo.hitch(this, "_setMaxOptions"),
					onComplete: dojo.hitch(this, "_openResultList"), 
					onError: function(errText){
						console.error('dijit.form.ComboBox: ' + errText);
						dojo.hitch(_this, "_hideResultList")();
					},
					start:0,
					count:this.pageSize
				};
				dojo.mixin(fetch, _this.fetchProperties);
				var dataObject = _this.store.fetch(fetch);

				var nextSearch = function(dataObject, direction){
					dataObject.start += dataObject.count*direction;
					// #4091:
					//		tell callback the direction of the paging so the screen
					//		reader knows which menu option to shout
					dataObject.direction = direction;
					this.store.fetch(dataObject);
				};
				this._nextSearch = this._popupWidget.onPage = dojo.hitch(this, nextSearch, dataObject);
			}, query, this), this.searchDelay);
		},

		_setMaxOptions: function(size, request){
			 this._maxOptions = size;
		},

		_getValueField:function(){
			return this.searchAttr;
		},

		/////////////// Event handlers /////////////////////

		_arrowPressed: function(){
			if(!this.disabled && !this.readOnly && this.hasDownArrow){
				dojo.addClass(this.downArrowNode, "dijitArrowButtonActive");
			}
		},

		_arrowIdle: function(){
			if(!this.disabled && !this.readOnly && this.hasDownArrow){
				dojo.removeClass(this.downArrowNode, "dojoArrowButtonPushed");
			}
		},

		// FIXME: 
		//		this is public so we can't remove until 2.0, but the name
		//		SHOULD be "compositionEnd"

		compositionend: function(/*Event*/ evt){
			//	summary:
			//		When inputting characters using an input method, such as
			//		Asian languages, it will generate this event instead of
			//		onKeyDown event Note: this event is only triggered in FF
			//		(not in IE)
			this._onKeyPress({charCode:-1});
		},

		//////////// INITIALIZATION METHODS ///////////////////////////////////////

		constructor: function(){
			this.query={};
			this.fetchProperties={};
		},

		postMixInProperties: function(){
			if(!this.hasDownArrow){
				this.baseClass = "dijitTextBox";
			}
			if(!this.store){
				var srcNodeRef = this.srcNodeRef;

				// if user didn't specify store, then assume there are option tags
				this.store = new dijit.form._ComboBoxDataStore(srcNodeRef);

				// if there is no value set and there is an option list, set
				// the value to the first value to be consistent with native
				// Select

				// Firefox and Safari set value
				// IE6 and Opera set selectedIndex, which is automatically set
				// by the selected attribute of an option tag
				// IE6 does not set value, Opera sets value = selectedIndex
				if(	!this.value || (
						(typeof srcNodeRef.selectedIndex == "number") && 
						srcNodeRef.selectedIndex.toString() === this.value)
				){
					var item = this.store.fetchSelectedItem();
					if(item){
						this.value = this.store.getValue(item, this._getValueField());
					}
				}
			}
		},
		
		_postCreate:function(){
			//find any associated label element and add to combobox node.
			var label=dojo.query('label[for="'+this.id+'"]');
			if(label.length){
				label[0].id = (this.id+"_label");
				var cn=this.comboNode;
				dijit.setWaiState(cn, "labelledby", label[0].id);
				
			}
		},

		uninitialize:function(){
			if(this._popupWidget){
				this._hideResultList();
				this._popupWidget.destroy();
			}
		},

		_getMenuLabelFromItem:function(/*Item*/ item){
			var label = this.store.getValue(item, this.labelAttr || this.searchAttr);
			var labelType = this.labelType;
			// If labelType is not "text" we don't want to screw any markup ot whatever.
			if (this.highlightMatch!="none" && this.labelType=="text" && this._lastInput){
				label = this.doHighlight(label, this._escapeHtml(this._lastInput));
				labelType = "html";
			}
			return {html: labelType=="html", label: label};
		},
		
		doHighlight:function(/*String*/label, /*String*/find){
			// summary:
			//		Highlights the string entered by the user in the menu, by default this
			//		highlights the first occurence found. Override this method
			//		to implement your custom highlighing.
			// Add greedy when this.highlightMatch=="all"
			var modifiers = "i"+(this.highlightMatch=="all"?"g":"");
			var escapedLabel = this._escapeHtml(label);
			var ret = escapedLabel.replace(new RegExp("^("+ find +")", modifiers),
					'<span class="dijitComboBoxHighlightMatch">$1</span>');
			if (escapedLabel==ret){ // Nothing replaced, try to replace at word boundaries.
				ret = escapedLabel.replace(new RegExp(" ("+ find +")", modifiers),
					' <span class="dijitComboBoxHighlightMatch">$1</span>');
			}
			return ret;// returns String, (almost) valid HTML (entities encoded)
		},
		
		_escapeHtml:function(/*string*/str){
			// TODO Should become dojo.html.entities(), when exists use instead
			// summary:
			//		Adds escape sequences for special characters in XML: &<>"'
			str = String(str).replace(/&/gm, "&amp;").replace(/</gm, "&lt;")
				.replace(/>/gm, "&gt;").replace(/"/gm, "&quot;");
			return str; // string
		},

		open:function(){
			this._isShowingNow=true;
			return dijit.popup.open({
				popup: this._popupWidget,
				around: this.domNode,
				parent: this
			});
		},
		
		reset:function(){
			//	summary:
			//		Additionally reset the .item (to clean up).
			this.item = null;
			this.inherited(arguments);
		}
		
	}
);

dojo.declare(
	"dijit.form._ComboBoxMenu",
	[dijit._Widget, dijit._Templated],
	{
		//	summary:
		//		Focus-less div based menu for internal use in ComboBox

		templateString: "<ul class='dijitReset dijitMenu' dojoAttachEvent='onmousedown:_onMouseDown,onmouseup:_onMouseUp,onmouseover:_onMouseOver,onmouseout:_onMouseOut' tabIndex='-1' style='overflow: \"auto\"; overflow-x: \"hidden\";'>"
				+"<li class='dijitMenuItem dijitMenuPreviousButton' dojoAttachPoint='previousButton'></li>"
				+"<li class='dijitMenuItem dijitMenuNextButton' dojoAttachPoint='nextButton'></li>"
			+"</ul>",
		_messages: null,

		postMixInProperties: function(){
			this._messages = dojo.i18n.getLocalization("dijit.form", "ComboBox", this.lang);
			this.inherited("postMixInProperties", arguments);
		},

		_setValueAttr: function(/*Object*/ value){
			this.value = value;
			this.onChange(value);
		},

		// stubs
		onChange: function(/*Object*/ value){},
		onPage: function(/*Number*/ direction){},

		postCreate:function(){
			// fill in template with i18n messages
			this.previousButton.innerHTML = this._messages["previousMessage"];
			this.nextButton.innerHTML = this._messages["nextMessage"];
			this.inherited(arguments);
		},

		onClose:function(){
			this._blurOptionNode();
		},

		_createOption:function(/*Object*/ item, labelFunc){
			//	summary: 
			//		creates an option to appear on the popup menu subclassed by
			//		FilteringSelect

			var labelObject = labelFunc(item);
			var menuitem = dojo.doc.createElement("li");
			dijit.setWaiRole(menuitem, "option");
			if(labelObject.html){
				menuitem.innerHTML = labelObject.label;
			}else{
				menuitem.appendChild(
					dojo.doc.createTextNode(labelObject.label)
				);
			}
			// #3250: in blank options, assign a normal height
			if(menuitem.innerHTML == ""){
				menuitem.innerHTML = "&nbsp;";
			}
			menuitem.item=item;
			return menuitem;
		},

		createOptions: function(results, dataObject, labelFunc){
			// TODOC: needs summary. What does this do and what are the params supposed to mean? 
			//this._dataObject=dataObject;
			//this._dataObject.onComplete=dojo.hitch(comboBox, comboBox._openResultList);
			// display "Previous . . ." button
			this.previousButton.style.display = (dataObject.start == 0) ? "none" : "";
			dojo.attr(this.previousButton, "id", this.id + "_prev");
			// create options using _createOption function defined by parent
			// ComboBox (or FilteringSelect) class
			// #2309:
			//		iterate over cache nondestructively
			dojo.forEach(results, function(item, i){
				var menuitem = this._createOption(item, labelFunc);
				menuitem.className = "dijitReset dijitMenuItem";
				dojo.attr(menuitem, "id", this.id + i);
				this.domNode.insertBefore(menuitem, this.nextButton);
			}, this);
			// display "Next . . ." button
			var displayMore = false;
			//Try to determine if we should show 'more'...
			if(dataObject._maxOptions && dataObject._maxOptions != -1){
				if((dataObject.start + dataObject.count) < dataObject._maxOptions){
					displayMore = true;
				}else if((dataObject.start + dataObject.count) > (dataObject._maxOptions - 1)){
					//Weird return from a datastore, where a start + count > maxOptions
					//implies maxOptions isn't really valid and we have to go into faking it.
					//And more or less assume more if count == results.length
					if(dataObject.count == results.length){
						displayMore = true;
					}
				}
			}else if(dataObject.count == results.length){
				//Don't know the size, so we do the best we can based off count alone.
				//So, if we have an exact match to count, assume more.
				displayMore = true;
			}

			this.nextButton.style.display = displayMore ? "" : "none";
			dojo.attr(this.nextButton,"id", this.id + "_next");
		},

		clearResultList: function(){
			// keep the previous and next buttons of course
			while(this.domNode.childNodes.length>2){
				this.domNode.removeChild(this.domNode.childNodes[this.domNode.childNodes.length-2]);
			}
		},

		// these functions are called in showResultList
		getItems: function(){
			return this.domNode.childNodes;
		},

		getListLength: function(){
			return this.domNode.childNodes.length-2;
		},

		_onMouseDown: function(/*Event*/ evt){
			dojo.stopEvent(evt);
		},

		_onMouseUp: function(/*Event*/ evt){
			if(evt.target === this.domNode){
				return;
			}else if(evt.target==this.previousButton){
				this.onPage(-1);
			}else if(evt.target==this.nextButton){
				this.onPage(1);
			}else{
				var tgt = evt.target;
				// while the clicked node is inside the div
				while(!tgt.item){
					// recurse to the top
					tgt = tgt.parentNode;
				}
				this._setValueAttr({ target: tgt }, true);
			}
		},

		_onMouseOver: function(/*Event*/ evt){
			if(evt.target === this.domNode){ return; }
			var tgt = evt.target;
			if(!(tgt == this.previousButton || tgt == this.nextButton)){
				// while the clicked node is inside the div
				while(!tgt.item){
					// recurse to the top
					tgt = tgt.parentNode;
				}
			}
			this._focusOptionNode(tgt);
		},

		_onMouseOut:function(/*Event*/ evt){
			if(evt.target === this.domNode){ return; }
			this._blurOptionNode();
		},

		_focusOptionNode:function(/*DomNode*/ node){
			// summary:
			//	does the actual highlight
			if(this._highlighted_option != node){
				this._blurOptionNode();
				this._highlighted_option = node;
				dojo.addClass(this._highlighted_option, "dijitMenuItemHover");
			}
		},

		_blurOptionNode:function(){
			// summary:
			//	removes highlight on highlighted option
			if(this._highlighted_option){
				dojo.removeClass(this._highlighted_option, "dijitMenuItemHover");
				this._highlighted_option = null;
			}
		},

		_highlightNextOption:function(){
			//	summary:
			// 		Highlight the item just below the current selection.
			// 		If nothing selected, highlight first option

			// because each press of a button clears the menu,
			// the highlighted option sometimes becomes detached from the menu!
			// test to see if the option has a parent to see if this is the case.
			var fc = this.domNode.firstChild;
			if(!this.getHighlightedOption()){
				this._focusOptionNode(fc.style.display=="none" ? fc.nextSibling : fc);
			}else{
				var ns = this._highlighted_option.nextSibling;
				if(ns && ns.style.display!="none"){
					this._focusOptionNode(ns);
				}
			}
			// scrollIntoView is called outside of _focusOptionNode because in IE putting it inside causes the menu to scroll up on mouseover
			dijit.scrollIntoView(this._highlighted_option);
		},

		highlightFirstOption:function(){
			//	summary:
			// 		Highlight the first real item in the list (not Previous Choices).
			this._focusOptionNode(this.domNode.firstChild.nextSibling);
			dijit.scrollIntoView(this._highlighted_option);
		},

		highlightLastOption:function(){
			//	summary:
			// 		Highlight the last real item in the list (not More Choices).
			this._focusOptionNode(this.domNode.lastChild.previousSibling);
			dijit.scrollIntoView(this._highlighted_option);
		},

		_highlightPrevOption:function(){
			//	summary:
			// 		Highlight the item just above the current selection.
			// 		If nothing selected, highlight last option (if
			// 		you select Previous and try to keep scrolling up the list)
			var lc = this.domNode.lastChild;
			if(!this.getHighlightedOption()){
				this._focusOptionNode(lc.style.display == "none" ? lc.previousSibling : lc);
			}else{
				var ps = this._highlighted_option.previousSibling;
				if(ps && ps.style.display != "none"){
					this._focusOptionNode(ps);
				}
			}
			dijit.scrollIntoView(this._highlighted_option);
		},

		_page:function(/*Boolean*/ up){
			var scrollamount = 0;
			var oldscroll = this.domNode.scrollTop;
			var height = dojo.style(this.domNode, "height");
			// if no item is highlighted, highlight the first option
			if(!this.getHighlightedOption()){
				this._highlightNextOption();
			}
			while(scrollamount<height){
				if(up){
					// stop at option 1
					if(!this.getHighlightedOption().previousSibling ||
						this._highlighted_option.previousSibling.style.display == "none"){
						break;
					}
					this._highlightPrevOption();
				}else{
					// stop at last option
					if(!this.getHighlightedOption().nextSibling ||
						this._highlighted_option.nextSibling.style.display == "none"){
						break;
					}
					this._highlightNextOption();
				}
				// going backwards
				var newscroll=this.domNode.scrollTop;
				scrollamount+=(newscroll-oldscroll)*(up ? -1:1);
				oldscroll=newscroll;
			}
		},

		pageUp: function(){ this._page(true); },

		pageDown: function(){ this._page(false); },

		getHighlightedOption: function(){
			//	summary:
			//		Returns the highlighted option.
			var ho = this._highlighted_option;
			return (ho && ho.parentNode) ? ho : null;
		},

		handleKey: function(key){
			switch(key){
				case dojo.keys.DOWN_ARROW:
					this._highlightNextOption();
					break;
				case dojo.keys.PAGE_DOWN:
					this.pageDown();
					break;	
				case dojo.keys.UP_ARROW:
					this._highlightPrevOption();
					break;
				case dojo.keys.PAGE_UP:
					this.pageUp();
					break;	
			}
		}
	}
);

dojo.declare(
	"dijit.form.ComboBox",
	[dijit.form.ValidationTextBox, dijit.form.ComboBoxMixin],
	{
		//	summary:
		//		Auto-completing text box, and base class for dijit.form.FilteringSelect.
		// 
		//	description:
		//		The drop down box's values are populated from an class called
		//		a data provider, which returns a list of values based on the characters
		//		that the user has typed into the input box.
		// 
		//		Some of the options to the ComboBox are actually arguments to the data
		//		provider.

		postMixInProperties: function(){
			// this.inherited(arguments); // ??
			dijit.form.ComboBoxMixin.prototype.postMixInProperties.apply(this, arguments);
			dijit.form.ValidationTextBox.prototype.postMixInProperties.apply(this, arguments);
		},

		postCreate: function(){
			dijit.form.ComboBoxMixin.prototype._postCreate.apply(this, arguments);
			dijit.form.ValidationTextBox.prototype.postCreate.apply(this, arguments);
		},

		_setDisabledAttr: function(/*Boolean*/ value){
			dijit.form.ValidationTextBox.prototype._setDisabledAttr.apply(this, arguments);
			dijit.form.ComboBoxMixin.prototype._setDisabledAttr.apply(this, arguments);
		},

		_setValueAttr: function(/*String*/ value, /*Boolean?*/ priorityChange){
			// summary:
			//              Hook so attr('value', value) works.
			// description:
			//              Sets the value of the select.
			if(!value){ value = ''; } // null translates to blank
			dijit.form.ValidationTextBox.prototype._setValueAttr.call(this, value, priorityChange);
		}
	}
);

dojo.declare("dijit.form._ComboBoxDataStore", null, {
	//	summary:
	//		Inefficient but small data store specialized for inlined ComboBox data
	//
	//	description:
	//		Provides a store for inlined data like:
	//
	//	|	<select>
	//	|		<option value="AL">Alabama</option>
	//	|		...
	//
	//		Actually. just implements the subset of dojo.data.Read/Notification
	//		needed for ComboBox and FilteringSelect to work.
	//
	//		Note that an item is just a pointer to the <option> DomNode.

	constructor: function( /*DomNode*/ root){
		this.root = root;

		dojo.query("> option", root).forEach(function(node){
			//	TODO: this was added in #3858 but unclear why/if it's needed;  doesn't seem to be.
			//	If it is needed then can we just hide the select itself instead?
			//node.style.display="none";
			node.innerHTML = dojo.trim(node.innerHTML);
		});

	},

	getValue: function(	/* item */ item, 
						/* attribute-name-string */ attribute, 
						/* value? */ defaultValue){
		return (attribute == "value") ? item.value : (item.innerText || item.textContent || '');
	},

	isItemLoaded: function(/* anything */ something) {
		return true;
	},

	getFeatures: function(){
		return {"dojo.data.api.Read": true, "dojo.data.api.Identity": true};
	},
	
	_fetchItems: function(	/* Object */ args,
							/* Function */ findCallback, 
							/* Function */ errorCallback){
		//	summary: 
		//		See dojo.data.util.simpleFetch.fetch()
		if(!args.query){ args.query = {}; }
		if(!args.query.name){ args.query.name = ""; }
		if(!args.queryOptions){ args.queryOptions = {}; }
		var matcher = dojo.data.util.filter.patternToRegExp(args.query.name, args.queryOptions.ignoreCase),
			items = dojo.query("> option", this.root).filter(function(option){
				return (option.innerText || option.textContent || '').match(matcher);
			} );
		if(args.sort){
			items.sort(dojo.data.util.sorter.createSortFunction(args.sort, this));
		}
		findCallback(items, args);
	},

	close: function(/*dojo.data.api.Request || args || null */ request){
		return;
	},

	getLabel: function(/* item */ item){
		return item.innerHTML;
	},

	getIdentity: function(/* item */ item){
		return dojo.attr(item, "value");
	},

	fetchItemByIdentity: function(/* Object */ args){
		//	summary:
		//		Given the identity of an item, this method returns the item that has
		//		that identity through the onItem callback.
		//		Refer to dojo.data.api.Identity.fetchItemByIdentity() for more details.
		//
		//	description:
		//		Given arguments like:
		//
		//	|		{identity: "CA", onItem: function(item){...}
		//
		//		Call `onItem()` with the DOM node `<option value="CA">California</option>`
		var item = dojo.query("option[value='" + args.identity + "']", this.root)[0];
		args.onItem(item);
	},
	
	fetchSelectedItem: function(){
		//	summary:
		//		Get the option marked as selected, like `<option selected>`.
		//		Not part of dojo.data API.
		var root = this.root,
			si = root.selectedIndex;
		return dojo.query("> option:nth-child(" +
			(si != -1 ? si+1 : 1) + ")",
			root)[0];	// dojo.data.Item
	}
});
//Mix in the simple fetch implementation to this class. 
dojo.extend(dijit.form._ComboBoxDataStore,dojo.data.util.simpleFetch);

}

if(!dojo._hasResource["dojo.cldr.monetary"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.cldr.monetary"] = true;
dojo.provide("dojo.cldr.monetary");

dojo.cldr.monetary.getData = function(/*String*/code){
// summary: A mapping of currency code to currency-specific formatting information. Returns a unique object with properties: places, round.
// code: an [ISO 4217](http://en.wikipedia.org/wiki/ISO_4217) currency code

// from http://www.unicode.org/cldr/data/common/supplemental/supplementalData.xml:supplementalData/currencyData/fractions

	var placesData = {
		ADP:0,BHD:3,BIF:0,BYR:0,CLF:0,CLP:0,DJF:0,ESP:0,GNF:0,
		IQD:3,ITL:0,JOD:3,JPY:0,KMF:0,KRW:0,KWD:3,LUF:0,LYD:3,
		MGA:0,MGF:0,OMR:3,PYG:0,RWF:0,TND:3,TRL:0,VUV:0,XAF:0,
		XOF:0,XPF:0
	};

	var roundingData = {CHF:5};

	var places = placesData[code], round = roundingData[code];
	if(typeof places == "undefined"){ places = 2; }
	if(typeof round == "undefined"){ round = 0; }

	return {places: places, round: round}; // Object
};

}

if(!dojo._hasResource["dojo.currency"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.currency"] = true;
dojo.provide("dojo.currency");






/*=====
dojo.currency = {
	// summary: localized formatting and parsing routines for currencies
}
=====*/

dojo.currency._mixInDefaults = function(options){
	options = options || {};
	options.type = "currency";

	// Get locale-depenent currency data, like the symbol
	var bundle = dojo.i18n.getLocalization("dojo.cldr", "currency", options.locale) || {};

	// Mixin locale-independent currency data, like # of places
	var iso = options.currency;
	var data = dojo.cldr.monetary.getData(iso);

	dojo.forEach(["displayName","symbol","group","decimal"], function(prop){
		data[prop] = bundle[iso+"_"+prop];
	});

	data.fractional = [true, false];

	// Mixin with provided options
	return dojo.mixin(data, options);
}

dojo.currency.format = function(/*Number*/value, /*dojo.number.__FormatOptions?*/options){
// summary:
//		Format a Number as a currency, using locale-specific settings
//
// description:
//		Create a string from a Number using a known, localized pattern.
//		[Formatting patterns](http://www.unicode.org/reports/tr35/#Number_Elements) appropriate to the locale are chosen from the [CLDR](http://unicode.org/cldr)
//		as well as the appropriate symbols and delimiters.
//
// value:
//		the number to be formatted.

	return dojo.number.format(value, dojo.currency._mixInDefaults(options));
}

dojo.currency.regexp = function(/*dojo.number.__RegexpOptions?*/options){
//
// summary:
//		Builds the regular needed to parse a currency value
//
// description:
//		Returns regular expression with positive and negative match, group and decimal separators
//		Note: the options.places default, the number of decimal places to accept, is defined by the currency type.
	return dojo.number.regexp(dojo.currency._mixInDefaults(options)); // String
}

/*=====
dojo.declare("dojo.currency.__ParseOptions", [dojo.number.__ParseOptions], {
	//	type: String?
	//		currency, set by default.
	//	symbol: String?
	//		override currency symbol. Normally, will be looked up in table of supported currencies,
	//		and ISO currency code will be used if not found.  See dojo.i18n.cldr.nls->currency.js
	//	places: Number?
	//		number of decimal places to accept.  Default is defined by currency.
	//	fractional: Boolean?|Array?
	//		where places are implied by pattern or explicit 'places' parameter, whether to include the fractional portion.
	//		By default for currencies, it the fractional portion is optional.
	type: "",
	symbol: "",
	places: "",
	fractional: ""
});
=====*/

dojo.currency.parse = function(/*String*/expression, /*dojo.currency.__ParseOptions?*/options){
	//
	// summary:
	//		Convert a properly formatted currency string to a primitive Number,
	//		using locale-specific settings.
	//
	// description:
	//		Create a Number from a string using a known, localized pattern.
	//		[Formatting patterns](http://www.unicode.org/reports/tr35/#Number_Format_Patterns) are chosen appropriate to the locale.
	//
	// expression: A string representation of a Number

	return dojo.number.parse(expression, dojo.currency._mixInDefaults(options));
}

}

if(!dojo._hasResource["dijit.form.NumberTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.NumberTextBox"] = true;
dojo.provide("dijit.form.NumberTextBox");




/*=====
dojo.declare(
	"dijit.form.NumberTextBox.__Constraints",
	[dijit.form.RangeBoundTextBox.__Constraints, dojo.number.__FormatOptions, dojo.number.__ParseOptions]
);
=====*/

dojo.declare(
	"dijit.form.NumberTextBoxMixin",
	null,
	{
		// summary:
		//		A mixin for all number textboxes

		regExpGen: dojo.number.regexp,

		/*=====
		// constraints: dijit.form.NumberTextBox.__Constraints 
		constraints: {},
		======*/

		// editOptions: Object
		//		properties to mix into constraints when the value is being edited
		editOptions: { pattern: '#.######' },

		_onFocus: function(){
			this._setValueAttr(this.attr('value'), false);	
			this.inherited(arguments);
		},

		_formatter: dojo.number.format,

		format: function(/*Number*/ value, /*dojo.number.__FormatOptions*/ constraints){
			//	summary: formats the value as a Number, according to constraints

			if(typeof value == "string") { return value; }
			if(isNaN(value)){ return ""; }
			if(this.editOptions && this._focused){
				constraints = dojo.mixin(dojo.mixin({}, this.editOptions), this.constraints);
			}
			return this._formatter(value, constraints);
		},

		parse: dojo.number.parse,
		/*=====
		parse: function(value, constraints){
			//	summary: parses the value as a Number, according to constraints
			//	value: String
			//
			//	constraints: dojo.number.__ParseOptions
		},
		=====*/

		filter: function(/*Number*/ value){
			return (value === null || value === '' || value === undefined)? NaN : this.inherited(arguments); // attr('value', null||''||undefined) should fire onChange(NaN)
		},

		serialize: function(/*Number*/ value, /*Object?*/options){
			return (typeof value != "number" || isNaN(value))? '' : this.inherited(arguments);
		},

		_getValueAttr: function(){
			// summary:
			//		Hook so attr('value') works.
			var v = this.inherited(arguments);
			if(isNaN(v) && this.textbox.value !== ''){ return undefined; }
			return v;
		},

		value: NaN
	}
);

dojo.declare(
	"dijit.form.NumberTextBox",
	[dijit.form.RangeBoundTextBox,dijit.form.NumberTextBoxMixin],
	{
		// summary:
		//		A validating, serializable, range-bound text box.
	}
);

}

if(!dojo._hasResource["dijit.form.CurrencyTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.CurrencyTextBox"] = true;
dojo.provide("dijit.form.CurrencyTextBox");

//FIXME: dojo.experimental throws an unreadable exception?
//dojo.experimental("dijit.form.CurrencyTextBox");




/*=====
dojo.declare(
	"dijit.form.CurrencyTextBox.__Constraints",
	[dijit.form.NumberTextBox.__Constraints, dojo.currency.__FormatOptions, dojo.currency.__ParseOptions]
);
=====*/

dojo.declare(
	"dijit.form.CurrencyTextBox",
	dijit.form.NumberTextBox,
	{
		// summary:
		//		A validating currency textbox
		//
		// currency: String
		//		the [ISO4217](http://en.wikipedia.org/wiki/ISO_4217) currency code, a three letter sequence like "USD"
		currency: "",

		/*=====
		// constraints: dijit.form.CurrencyTextBox.__Constraints 
		constraints: {},
		======*/

		regExpGen: dojo.currency.regexp,
		_formatter: dojo.currency.format,
/*=====
		parse: function(value, constraints){
			//	summary: parses the value as a Currency, according to constraints
			//	value: String
			//
			//	constraints: dojo.currency.__ParseOptions
		},
=====*/
		parse: dojo.currency.parse,

		postMixInProperties: function(){
			if(this.constraints === dijit.form.ValidationTextBox.prototype.constraints){
				// declare a constraints property on 'this' so we don't overwrite the shared default object in 'prototype'
				this.constraints = {};
			}
			this.constraints.currency = this.currency;
			dijit.form.CurrencyTextBox.superclass.postMixInProperties.apply(this, arguments);
		}
	}
);

}

if(!dojo._hasResource["dojo.cldr.supplemental"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.cldr.supplemental"] = true;
dojo.provide("dojo.cldr.supplemental");



dojo.cldr.supplemental.getFirstDayOfWeek = function(/*String?*/locale){
// summary: Returns a zero-based index for first day of the week
// description:
//		Returns a zero-based index for first day of the week, as used by the local (Gregorian) calendar.
//		e.g. Sunday (returns 0), or Monday (returns 1)

	// from http://www.unicode.org/cldr/data/common/supplemental/supplementalData.xml:supplementalData/weekData/firstDay
	var firstDay = {/*default is 1=Monday*/
		mv:5,
		ae:6,af:6,bh:6,dj:6,dz:6,eg:6,er:6,et:6,iq:6,ir:6,jo:6,ke:6,kw:6,lb:6,ly:6,ma:6,om:6,qa:6,sa:6,
		sd:6,so:6,tn:6,ye:6,
		as:0,au:0,az:0,bw:0,ca:0,cn:0,fo:0,ge:0,gl:0,gu:0,hk:0,ie:0,il:0,is:0,jm:0,jp:0,kg:0,kr:0,la:0,
		mh:0,mo:0,mp:0,mt:0,nz:0,ph:0,pk:0,sg:0,th:0,tt:0,tw:0,um:0,us:0,uz:0,vi:0,za:0,zw:0,
		et:0,mw:0,ng:0,tj:0,
// variant. do not use?		gb:0,
		sy:4
	};

	var country = dojo.cldr.supplemental._region(locale);
	var dow = firstDay[country];
	return (dow === undefined) ? 1 : dow; /*Number*/
};

dojo.cldr.supplemental._region = function(/*String?*/locale){
	locale = dojo.i18n.normalizeLocale(locale);
	var tags = locale.split('-');
	var region = tags[1];
	if(!region){
		// IE often gives language only (#2269)
		// Arbitrary mappings of language-only locales to a country:
		region = {de:"de", en:"us", es:"es", fi:"fi", fr:"fr", he:"il", hu:"hu", it:"it",
			ja:"jp", ko:"kr", nl:"nl", pt:"br", sv:"se", zh:"cn"}[tags[0]];
	}else if(region.length == 4){
		// The ISO 3166 country code is usually in the second position, unless a
		// 4-letter script is given. See http://www.ietf.org/rfc/rfc4646.txt
		region = tags[2];
	}
	return region;
}

dojo.cldr.supplemental.getWeekend = function(/*String?*/locale){
// summary: Returns a hash containing the start and end days of the weekend
// description:
//		Returns a hash containing the start and end days of the weekend according to local custom using locale,
//		or by default in the user's locale.
//		e.g. {start:6, end:0}

	// from http://www.unicode.org/cldr/data/common/supplemental/supplementalData.xml:supplementalData/weekData/weekend{Start,End}
	var weekendStart = {/*default is 6=Saturday*/
		eg:5,il:5,sy:5,
		'in':0,
		ae:4,bh:4,dz:4,iq:4,jo:4,kw:4,lb:4,ly:4,ma:4,om:4,qa:4,sa:4,sd:4,tn:4,ye:4		
	};

	var weekendEnd = {/*default is 0=Sunday*/
		ae:5,bh:5,dz:5,iq:5,jo:5,kw:5,lb:5,ly:5,ma:5,om:5,qa:5,sa:5,sd:5,tn:5,ye:5,af:5,ir:5,
		eg:6,il:6,sy:6
	};

	var country = dojo.cldr.supplemental._region(locale);
	var start = weekendStart[country];
	var end = weekendEnd[country];
	if(start === undefined){start=6;}
	if(end === undefined){end=0;}
	return {start:start, end:end}; /*Object {start,end}*/
};

}

if(!dojo._hasResource["dojo.date"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.date"] = true;
dojo.provide("dojo.date");

/*=====
dojo.date = {
	// summary: Date manipulation utilities
}
=====*/

dojo.date.getDaysInMonth = function(/*Date*/dateObject){
	//	summary:
	//		Returns the number of days in the month used by dateObject
	var month = dateObject.getMonth();
	var days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if(month == 1 && dojo.date.isLeapYear(dateObject)){ return 29; } // Number
	return days[month]; // Number
}

dojo.date.isLeapYear = function(/*Date*/dateObject){
	//	summary:
	//		Determines if the year of the dateObject is a leap year
	//	description:
	//		Leap years are years with an additional day YYYY-02-29, where the
	//		year number is a multiple of four with the following exception: If
	//		a year is a multiple of 100, then it is only a leap year if it is
	//		also a multiple of 400. For example, 1900 was not a leap year, but
	//		2000 is one.

	var year = dateObject.getFullYear();
	return !(year%400) || (!(year%4) && !!(year%100)); // Boolean
}

// FIXME: This is not localized
dojo.date.getTimezoneName = function(/*Date*/dateObject){
	//	summary:
	//		Get the user's time zone as provided by the browser
	// dateObject:
	//		Needed because the timezone may vary with time (daylight savings)
	//	description:
	//		Try to get time zone info from toString or toLocaleString method of
	//		the Date object -- UTC offset is not a time zone.  See
	//		http://www.twinsun.com/tz/tz-link.htm Note: results may be
	//		inconsistent across browsers.

	var str = dateObject.toString(); // Start looking in toString
	var tz = ''; // The result -- return empty string if nothing found
	var match;

	// First look for something in parentheses -- fast lookup, no regex
	var pos = str.indexOf('(');
	if(pos > -1){
		tz = str.substring(++pos, str.indexOf(')'));
	}else{
		// If at first you don't succeed ...
		// If IE knows about the TZ, it appears before the year
		// Capital letters or slash before a 4-digit year 
		// at the end of string
		var pat = /([A-Z\/]+) \d{4}$/;
		if((match = str.match(pat))){
			tz = match[1];
		}else{
		// Some browsers (e.g. Safari) glue the TZ on the end
		// of toLocaleString instead of putting it in toString
			str = dateObject.toLocaleString();
			// Capital letters or slash -- end of string, 
			// after space
			pat = / ([A-Z\/]+)$/;
			if((match = str.match(pat))){
				tz = match[1];
			}
		}
	}

	// Make sure it doesn't somehow end up return AM or PM
	return (tz == 'AM' || tz == 'PM') ? '' : tz; // String
}

// Utility methods to do arithmetic calculations with Dates

dojo.date.compare = function(/*Date*/date1, /*Date?*/date2, /*String?*/portion){
	//	summary:
	//		Compare two date objects by date, time, or both.
	//	description:
	//  	Returns 0 if equal, positive if a > b, else negative.
	//	date1:
	//		Date object
	//	date2:
	//		Date object.  If not specified, the current Date is used.
	//	portion:
	//		A string indicating the "date" or "time" portion of a Date object.
	//		Compares both "date" and "time" by default.  One of the following:
	//		"date", "time", "datetime"

	// Extra step required in copy for IE - see #3112
	date1 = new Date(Number(date1));
	date2 = new Date(Number(date2 || new Date()));

	if(portion !== "undefined"){
		if(portion == "date"){
			// Ignore times and compare dates.
			date1.setHours(0, 0, 0, 0);
			date2.setHours(0, 0, 0, 0);
		}else if(portion == "time"){
			// Ignore dates and compare times.
			date1.setFullYear(0, 0, 0);
			date2.setFullYear(0, 0, 0);
		}
	}
	
	if(date1 > date2){ return 1; } // int
	if(date1 < date2){ return -1; } // int
	return 0; // int
};

dojo.date.add = function(/*Date*/date, /*String*/interval, /*int*/amount){
	//	summary:
	//		Add to a Date in intervals of different size, from milliseconds to years
	//	date: Date
	//		Date object to start with
	//	interval:
	//		A string representing the interval.  One of the following:
	//			"year", "month", "day", "hour", "minute", "second",
	//			"millisecond", "quarter", "week", "weekday"
	//	amount:
	//		How much to add to the date.

	var sum = new Date(Number(date)); // convert to Number before copying to accomodate IE (#3112)
	var fixOvershoot = false;
	var property = "Date";

	switch(interval){
		case "day":
			break;
		case "weekday":
			//i18n FIXME: assumes Saturday/Sunday weekend, but this is not always true.  see dojo.cldr.supplemental

			// Divide the increment time span into weekspans plus leftover days
			// e.g., 8 days is one 5-day weekspan / and two leftover days
			// Can't have zero leftover days, so numbers divisible by 5 get
			// a days value of 5, and the remaining days make up the number of weeks
			var days, weeks;
			var mod = amount % 5;
			if(!mod){
				days = (amount > 0) ? 5 : -5;
				weeks = (amount > 0) ? ((amount-5)/5) : ((amount+5)/5);
			}else{
				days = mod;
				weeks = parseInt(amount/5);
			}
			// Get weekday value for orig date param
			var strt = date.getDay();
			// Orig date is Sat / positive incrementer
			// Jump over Sun
			var adj = 0;
			if(strt == 6 && amount > 0){
				adj = 1;
			}else if(strt == 0 && amount < 0){
			// Orig date is Sun / negative incrementer
			// Jump back over Sat
				adj = -1;
			}
			// Get weekday val for the new date
			var trgt = strt + days;
			// New date is on Sat or Sun
			if(trgt == 0 || trgt == 6){
				adj = (amount > 0) ? 2 : -2;
			}
			// Increment by number of weeks plus leftover days plus
			// weekend adjustments
			amount = (7 * weeks) + days + adj;
			break;
		case "year":
			property = "FullYear";
			// Keep increment/decrement from 2/29 out of March
			fixOvershoot = true;
			break;
		case "week":
			amount *= 7;
			break;
		case "quarter":
			// Naive quarter is just three months
			amount *= 3;
			// fallthrough...
		case "month":
			// Reset to last day of month if you overshoot
			fixOvershoot = true;
			property = "Month";
			break;
		case "hour":
		case "minute":
		case "second":
		case "millisecond":
			property = "UTC"+interval.charAt(0).toUpperCase() + interval.substring(1) + "s";
	}

	if(property){
		sum["set"+property](sum["get"+property]()+amount);
	}

	if(fixOvershoot && (sum.getDate() < date.getDate())){
		sum.setDate(0);
	}

	return sum; // Date
};

dojo.date.difference = function(/*Date*/date1, /*Date?*/date2, /*String?*/interval){
	//	summary:
	//		Get the difference in a specific unit of time (e.g., number of
	//		months, weeks, days, etc.) between two dates, rounded to the
	//		nearest integer.
	//	date1:
	//		Date object
	//	date2:
	//		Date object.  If not specified, the current Date is used.
	//	interval:
	//		A string representing the interval.  One of the following:
	//			"year", "month", "day", "hour", "minute", "second",
	//			"millisecond", "quarter", "week", "weekday"
	//		Defaults to "day".

	date2 = date2 || new Date();
	interval = interval || "day";
	var yearDiff = date2.getFullYear() - date1.getFullYear();
	var delta = 1; // Integer return value

	switch(interval){
		case "quarter":
			var m1 = date1.getMonth();
			var m2 = date2.getMonth();
			// Figure out which quarter the months are in
			var q1 = Math.floor(m1/3) + 1;
			var q2 = Math.floor(m2/3) + 1;
			// Add quarters for any year difference between the dates
			q2 += (yearDiff * 4);
			delta = q2 - q1;
			break;
		case "weekday":
			var days = Math.round(dojo.date.difference(date1, date2, "day"));
			var weeks = parseInt(dojo.date.difference(date1, date2, "week"));
			var mod = days % 7;

			// Even number of weeks
			if(mod == 0){
				days = weeks*5;
			}else{
				// Weeks plus spare change (< 7 days)
				var adj = 0;
				var aDay = date1.getDay();
				var bDay = date2.getDay();

				weeks = parseInt(days/7);
				mod = days % 7;
				// Mark the date advanced by the number of
				// round weeks (may be zero)
				var dtMark = new Date(date1);
				dtMark.setDate(dtMark.getDate()+(weeks*7));
				var dayMark = dtMark.getDay();

				// Spare change days -- 6 or less
				if(days > 0){
					switch(true){
						// Range starts on Sat
						case aDay == 6:
							adj = -1;
							break;
						// Range starts on Sun
						case aDay == 0:
							adj = 0;
							break;
						// Range ends on Sat
						case bDay == 6:
							adj = -1;
							break;
						// Range ends on Sun
						case bDay == 0:
							adj = -2;
							break;
						// Range contains weekend
						case (dayMark + mod) > 5:
							adj = -2;
					}
				}else if(days < 0){
					switch(true){
						// Range starts on Sat
						case aDay == 6:
							adj = 0;
							break;
						// Range starts on Sun
						case aDay == 0:
							adj = 1;
							break;
						// Range ends on Sat
						case bDay == 6:
							adj = 2;
							break;
						// Range ends on Sun
						case bDay == 0:
							adj = 1;
							break;
						// Range contains weekend
						case (dayMark + mod) < 0:
							adj = 2;
					}
				}
				days += adj;
				days -= (weeks*2);
			}
			delta = days;
			break;
		case "year":
			delta = yearDiff;
			break;
		case "month":
			delta = (date2.getMonth() - date1.getMonth()) + (yearDiff * 12);
			break;
		case "week":
			// Truncate instead of rounding
			// Don't use Math.floor -- value may be negative
			delta = parseInt(dojo.date.difference(date1, date2, "day")/7);
			break;
		case "day":
			delta /= 24;
			// fallthrough
		case "hour":
			delta /= 60;
			// fallthrough
		case "minute":
			delta /= 60;
			// fallthrough
		case "second":
			delta /= 1000;
			// fallthrough
		case "millisecond":
			delta *= date2.getTime() - date1.getTime();
	}

	// Round for fractional values and DST leaps
	return Math.round(delta); // Number (integer)
};

}

if(!dojo._hasResource["dojo.date.locale"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojo.date.locale"] = true;
dojo.provide("dojo.date.locale");

// Localization methods for Date.   Honor local customs using locale-dependent dojo.cldr data.







// Load the bundles containing localization information for
// names and formats


//NOTE: Everything in this module assumes Gregorian calendars.
// Other calendars will be implemented in separate modules.

(function(){
	// Format a pattern without literals
	function formatPattern(dateObject, bundle, fullYear, pattern){
		return pattern.replace(/([a-z])\1*/ig, function(match){
			var s, pad;
			var c = match.charAt(0);
			var l = match.length;
			var widthList = ["abbr", "wide", "narrow"];
			switch(c){
				case 'G':
					s = bundle[(l < 4) ? "eraAbbr" : "eraNames"][dateObject.getFullYear() < 0 ? 0 : 1];
					break;
				case 'y':
					s = dateObject.getFullYear();
					switch(l){
						case 1:
							break;
						case 2:
							if(!fullYear){
								s = String(s); s = s.substr(s.length - 2);
								break;
							}
							// fallthrough
						default:
							pad = true;
					}
					break;
				case 'Q':
				case 'q':
					s = Math.ceil((dateObject.getMonth()+1)/3);
//					switch(l){
//						case 1: case 2:
							pad = true;
//							break;
//						case 3: case 4: // unimplemented
//					}
					break;
				case 'M':
					var m = dateObject.getMonth();
					if(l<3){
						s = m+1; pad = true;
					}else{
						var propM = ["months", "format", widthList[l-3]].join("-");
						s = bundle[propM][m];
					}
					break;
				case 'w':
					var firstDay = 0;
					s = dojo.date.locale._getWeekOfYear(dateObject, firstDay); pad = true;
					break;
				case 'd':
					s = dateObject.getDate(); pad = true;
					break;
				case 'D':
					s = dojo.date.locale._getDayOfYear(dateObject); pad = true;
					break;
				case 'E':
					var d = dateObject.getDay();
					if(l<3){
						s = d+1; pad = true;
					}else{
						var propD = ["days", "format", widthList[l-3]].join("-");
						s = bundle[propD][d];
					}
					break;
				case 'a':
					var timePeriod = (dateObject.getHours() < 12) ? 'am' : 'pm';
					s = bundle[timePeriod];
					break;
				case 'h':
				case 'H':
				case 'K':
				case 'k':
					var h = dateObject.getHours();
					// strange choices in the date format make it impossible to write this succinctly
					switch (c){
						case 'h': // 1-12
							s = (h % 12) || 12;
							break;
						case 'H': // 0-23
							s = h;
							break;
						case 'K': // 0-11
							s = (h % 12);
							break;
						case 'k': // 1-24
							s = h || 24;
							break;
					}
					pad = true;
					break;
				case 'm':
					s = dateObject.getMinutes(); pad = true;
					break;
				case 's':
					s = dateObject.getSeconds(); pad = true;
					break;
				case 'S':
					s = Math.round(dateObject.getMilliseconds() * Math.pow(10, l-3)); pad = true;
					break;
				case 'v': // FIXME: don't know what this is. seems to be same as z?
				case 'z':
					// We only have one timezone to offer; the one from the browser
					s = dojo.date.getTimezoneName(dateObject);
					if(s){break;}
					l=4;
					// fallthrough... use GMT if tz not available
				case 'Z':
					var offset = dateObject.getTimezoneOffset();
					var tz = [
						(offset<=0 ? "+" : "-"),
						dojo.string.pad(Math.floor(Math.abs(offset)/60), 2),
						dojo.string.pad(Math.abs(offset)% 60, 2)
					];
					if(l==4){
						tz.splice(0, 0, "GMT");
						tz.splice(3, 0, ":");
					}
					s = tz.join("");
					break;
//				case 'Y': case 'u': case 'W': case 'F': case 'g': case 'A': case 'e':
//					
				default:
					throw new Error("dojo.date.locale.format: invalid pattern char: "+pattern);
			}
			if(pad){ s = dojo.string.pad(s, l); }
			return s;
		});
	}

/*=====
	dojo.date.locale.__FormatOptions = function(){
	//	selector: String
	//		choice of 'time','date' (default: date and time)
	//	formatLength: String
	//		choice of long, short, medium or full (plus any custom additions).  Defaults to 'short'
	//	datePattern:String
	//		override pattern with this string
	//	timePattern:String
	//		override pattern with this string
	//	am: String
	//		override strings for am in times
	//	pm: String
	//		override strings for pm in times
	//	locale: String
	//		override the locale used to determine formatting rules
	//	fullYear: Boolean
	//		(format only) use 4 digit years whenever 2 digit years are called for
	//	strict: Boolean
	//		(parse only) strict parsing, off by default
		this.selector = selector;
		this.formatLength = formatLength;
		this.datePattern = datePattern;
		this.timePattern = timePattern;
		this.am = am;
		this.pm = pm;
		this.locale = locale;
		this.fullYear = fullYear;
		this.strict = strict;
	}
=====*/

dojo.date.locale.format = function(/*Date*/dateObject, /*dojo.date.locale.__FormatOptions?*/options){
	// summary:
	//		Format a Date object as a String, using locale-specific settings.
	//
	// description:
	//		Create a string from a Date object using a known localized pattern.
	//		By default, this method formats both date and time from dateObject.
	//		Formatting patterns are chosen appropriate to the locale.  Different
	//		formatting lengths may be chosen, with "full" used by default.
	//		Custom patterns may be used or registered with translations using
	//		the dojo.date.locale.addCustomFormats method.
	//		Formatting patterns are implemented using [the syntax described at
	//		unicode.org](http://www.unicode.org/reports/tr35/tr35-4.html#Date_Format_Patterns)
	//
	// dateObject:
	//		the date and/or time to be formatted.  If a time only is formatted,
	//		the values in the year, month, and day fields are irrelevant.  The
	//		opposite is true when formatting only dates.

	options = options || {};

	var locale = dojo.i18n.normalizeLocale(options.locale);
	var formatLength = options.formatLength || 'short';
	var bundle = dojo.date.locale._getGregorianBundle(locale);
	var str = [];
	var sauce = dojo.hitch(this, formatPattern, dateObject, bundle, options.fullYear);
	if(options.selector == "year"){
		// Special case as this is not yet driven by CLDR data
		var year = dateObject.getFullYear();
		if(locale.match(/^zh|^ja/)){
			year += "\u5E74";
		}
		return year;
	}
	if(options.selector != "time"){
		var datePattern = options.datePattern || bundle["dateFormat-"+formatLength];
		if(datePattern){str.push(_processPattern(datePattern, sauce));}
	}
	if(options.selector != "date"){
		var timePattern = options.timePattern || bundle["timeFormat-"+formatLength];
		if(timePattern){str.push(_processPattern(timePattern, sauce));}
	}
	var result = str.join(" "); //TODO: use locale-specific pattern to assemble date + time
	return result; // String
};

dojo.date.locale.regexp = function(/*dojo.date.locale.__FormatOptions?*/options){
	// summary:
	//		Builds the regular needed to parse a localized date

	return dojo.date.locale._parseInfo(options).regexp; // String
};

dojo.date.locale._parseInfo = function(/*dojo.date.locale.__FormatOptions?*/options){
	options = options || {};
	var locale = dojo.i18n.normalizeLocale(options.locale);
	var bundle = dojo.date.locale._getGregorianBundle(locale);
	var formatLength = options.formatLength || 'short';
	var datePattern = options.datePattern || bundle["dateFormat-" + formatLength];
	var timePattern = options.timePattern || bundle["timeFormat-" + formatLength];
	var pattern;
	if(options.selector == 'date'){
		pattern = datePattern;
	}else if(options.selector == 'time'){
		pattern = timePattern;
	}else{
		pattern = datePattern + ' ' + timePattern; //TODO: use locale-specific pattern to assemble date + time
	}

	var tokens = [];
	var re = _processPattern(pattern, dojo.hitch(this, _buildDateTimeRE, tokens, bundle, options));
	return {regexp: re, tokens: tokens, bundle: bundle};
};

dojo.date.locale.parse = function(/*String*/value, /*dojo.date.locale.__FormatOptions?*/options){
	// summary:
	//		Convert a properly formatted string to a primitive Date object,
	//		using locale-specific settings.
	//
	// description:
	//		Create a Date object from a string using a known localized pattern.
	//		By default, this method parses looking for both date and time in the string.
	//		Formatting patterns are chosen appropriate to the locale.  Different
	//		formatting lengths may be chosen, with "full" used by default.
	//		Custom patterns may be used or registered with translations using
	//		the dojo.date.locale.addCustomFormats method.
	//	
	//		Formatting patterns are implemented using [the syntax described at
	//		unicode.org](http://www.unicode.org/reports/tr35/tr35-4.html#Date_Format_Patterns)
	//		When two digit years are used, a century is chosen according to a sliding 
	//		window of 80 years before and 20 years after present year, for both `yy` and `yyyy` patterns.
	//		year < 100CE requires strict mode.
	//
	// value:
	//		A string representation of a date

	var info = dojo.date.locale._parseInfo(options);
	var tokens = info.tokens, bundle = info.bundle;
	var re = new RegExp("^" + info.regexp + "$", info.strict ? "" : "i");
	var match = re.exec(value);
	if(!match){ return null; } // null

	var widthList = ['abbr', 'wide', 'narrow'];
	var result = [1970,0,1,0,0,0,0]; // will get converted to a Date at the end
	var amPm = "";
	var valid = dojo.every(match, function(v, i){
		if(!i){return true;}
		var token=tokens[i-1];
		var l=token.length;
		switch(token.charAt(0)){
			case 'y':
				if(l != 2 && options.strict){
					//interpret year literally, so '5' would be 5 A.D.
					result[0] = v;
				}else{
					if(v<100){
						v = Number(v);
						//choose century to apply, according to a sliding window
						//of 80 years before and 20 years after present year
						var year = '' + new Date().getFullYear();
						var century = year.substring(0, 2) * 100;
						var cutoff = Math.min(Number(year.substring(2, 4)) + 20, 99);
						var num = (v < cutoff) ? century + v : century - 100 + v;
						result[0] = num;
					}else{
						//we expected 2 digits and got more...
						if(options.strict){
							return false;
						}
						//interpret literally, so '150' would be 150 A.D.
						//also tolerate '1950', if 'yyyy' input passed to 'yy' format
						result[0] = v;
					}
				}
				break;
			case 'M':
				if(l>2){
					var months = bundle['months-format-' + widthList[l-3]].concat();
					if(!options.strict){
						//Tolerate abbreviating period in month part
						//Case-insensitive comparison
						v = v.replace(".","").toLowerCase();
						months = dojo.map(months, function(s){ return s.replace(".","").toLowerCase(); } );
					}
					v = dojo.indexOf(months, v);
					if(v == -1){
//						
						return false;
					}
				}else{
					v--;
				}
				result[1] = v;
				break;
			case 'E':
			case 'e':
				var days = bundle['days-format-' + widthList[l-3]].concat();
				if(!options.strict){
					//Case-insensitive comparison
					v = v.toLowerCase();
					days = dojo.map(days, function(d){return d.toLowerCase();});
				}
				v = dojo.indexOf(days, v);
				if(v == -1){
//					
					return false;
				}

				//TODO: not sure what to actually do with this input,
				//in terms of setting something on the Date obj...?
				//without more context, can't affect the actual date
				//TODO: just validate?
				break;
			case 'D':
				result[1] = 0;
				// fallthrough...
			case 'd':
				result[2] = v;
				break;
			case 'a': //am/pm
				var am = options.am || bundle.am;
				var pm = options.pm || bundle.pm;
				if(!options.strict){
					var period = /\./g;
					v = v.replace(period,'').toLowerCase();
					am = am.replace(period,'').toLowerCase();
					pm = pm.replace(period,'').toLowerCase();
				}
				if(options.strict && v != am && v != pm){
//					
					return false;
				}

				// we might not have seen the hours field yet, so store the state and apply hour change later
				amPm = (v == pm) ? 'p' : (v == am) ? 'a' : '';
				break;
			case 'K': //hour (1-24)
				if(v == 24){ v = 0; }
				// fallthrough...
			case 'h': //hour (1-12)
			case 'H': //hour (0-23)
			case 'k': //hour (0-11)
				//TODO: strict bounds checking, padding
				if(v > 23){
//					
					return false;
				}

				//in the 12-hour case, adjusting for am/pm requires the 'a' part
				//which could come before or after the hour, so we will adjust later
				result[3] = v;
				break;
			case 'm': //minutes
				result[4] = v;
				break;
			case 's': //seconds
				result[5] = v;
				break;
			case 'S': //milliseconds
				result[6] = v;
//				break;
//			case 'w':
//TODO				var firstDay = 0;
//			default:
//TODO: throw?
//				
		}
		return true;
	});

	var hours = +result[3];
	if(amPm === 'p' && hours < 12){
		result[3] = hours + 12; //e.g., 3pm -> 15
	}else if(amPm === 'a' && hours == 12){
		result[3] = 0; //12am -> 0
	}

	//TODO: implement a getWeekday() method in order to test 
	//validity of input strings containing 'EEE' or 'EEEE'...

	var dateObject = new Date(result[0], result[1], result[2], result[3], result[4], result[5], result[6]); // Date
	if(options.strict){
		dateObject.setFullYear(result[0]);
	}

	// Check for overflow.  The Date() constructor normalizes things like April 32nd...
	//TODO: why isn't this done for times as well?
	var allTokens = tokens.join("");
	if(!valid ||
		(allTokens.indexOf('M') != -1 && dateObject.getMonth() != result[1]) ||
		(allTokens.indexOf('d') != -1 && dateObject.getDate() != result[2])){
		return null;
	}

	return dateObject; // Date
};

function _processPattern(pattern, applyPattern, applyLiteral, applyAll){
	//summary: Process a pattern with literals in it

	// Break up on single quotes, treat every other one as a literal, except '' which becomes '
	var identity = function(x){return x;};
	applyPattern = applyPattern || identity;
	applyLiteral = applyLiteral || identity;
	applyAll = applyAll || identity;

	//split on single quotes (which escape literals in date format strings) 
	//but preserve escaped single quotes (e.g., o''clock)
	var chunks = pattern.match(/(''|[^'])+/g); 
	var literal = pattern.charAt(0) == "'";

	dojo.forEach(chunks, function(chunk, i){
		if(!chunk){
			chunks[i]='';
		}else{
			chunks[i]=(literal ? applyLiteral : applyPattern)(chunk);
			literal = !literal;
		}
	});
	return applyAll(chunks.join(''));
}

function _buildDateTimeRE(tokens, bundle, options, pattern){
	pattern = dojo.regexp.escapeString(pattern);
	if(!options.strict){ pattern = pattern.replace(" a", " ?a"); } // kludge to tolerate no space before am/pm
	return pattern.replace(/([a-z])\1*/ig, function(match){
		// Build a simple regexp.  Avoid captures, which would ruin the tokens list
		var s;
		var c = match.charAt(0);
		var l = match.length;
		var p2 = '', p3 = '';
		if(options.strict){
			if(l > 1){ p2 = '0' + '{'+(l-1)+'}'; }
			if(l > 2){ p3 = '0' + '{'+(l-2)+'}'; }
		}else{
			p2 = '0?'; p3 = '0{0,2}';
		}
		switch(c){
			case 'y':
				s = '\\d{2,4}';
				break;
			case 'M':
				s = (l>2) ? '\\S+?' : p2+'[1-9]|1[0-2]';
				break;
			case 'D':
				s = p2+'[1-9]|'+p3+'[1-9][0-9]|[12][0-9][0-9]|3[0-5][0-9]|36[0-6]';
				break;
			case 'd':
				s = '[12]\\d|'+p2+'[1-9]|3[01]';
				break;
			case 'w':
				s = p2+'[1-9]|[1-4][0-9]|5[0-3]';
				break;
		    case 'E':
				s = '\\S+';
				break;
			case 'h': //hour (1-12)
				s = p2+'[1-9]|1[0-2]';
				break;
			case 'k': //hour (0-11)
				s = p2+'\\d|1[01]';
				break;
			case 'H': //hour (0-23)
				s = p2+'\\d|1\\d|2[0-3]';
				break;
			case 'K': //hour (1-24)
				s = p2+'[1-9]|1\\d|2[0-4]';
				break;
			case 'm':
			case 's':
				s = '[0-5]\\d';
				break;
			case 'S':
				s = '\\d{'+l+'}';
				break;
			case 'a':
				var am = options.am || bundle.am || 'AM';
				var pm = options.pm || bundle.pm || 'PM';
				if(options.strict){
					s = am + '|' + pm;
				}else{
					s = am + '|' + pm;
					if(am != am.toLowerCase()){ s += '|' + am.toLowerCase(); }
					if(pm != pm.toLowerCase()){ s += '|' + pm.toLowerCase(); }
					if(s.indexOf('.') != -1){ s += '|' + s.replace(/\./g, ""); }
				}
				s = s.replace(/\./g, "\\.");
				break;
			default:
			// case 'v':
			// case 'z':
			// case 'Z':
				s = ".*";
//				
		}

		if(tokens){ tokens.push(match); }

		return "(" + s + ")"; // add capture
	}).replace(/[\xa0 ]/g, "[\\s\\xa0]"); // normalize whitespace.  Need explicit handling of \xa0 for IE.
}
})();

(function(){
var _customFormats = [];
dojo.date.locale.addCustomFormats = function(/*String*/packageName, /*String*/bundleName){
	// summary:
	//		Add a reference to a bundle containing localized custom formats to be
	//		used by date/time formatting and parsing routines.
	//
	// description:
	//		The user may add custom localized formats where the bundle has properties following the
	//		same naming convention used by dojo.cldr: `dateFormat-xxxx` / `timeFormat-xxxx`
	//		The pattern string should match the format used by the CLDR.
	//		See dojo.date.locale.format() for details.
	//		The resources must be loaded by dojo.requireLocalization() prior to use

	_customFormats.push({pkg:packageName,name:bundleName});
};

dojo.date.locale._getGregorianBundle = function(/*String*/locale){
	var gregorian = {};
	dojo.forEach(_customFormats, function(desc){
		var bundle = dojo.i18n.getLocalization(desc.pkg, desc.name, locale);
		gregorian = dojo.mixin(gregorian, bundle);
	}, this);
	return gregorian; /*Object*/
};
})();

dojo.date.locale.addCustomFormats("dojo.cldr","gregorian");

dojo.date.locale.getNames = function(/*String*/item, /*String*/type, /*String?*/use, /*String?*/locale){
	// summary:
	//		Used to get localized strings from dojo.cldr for day or month names.
	//
	// item:
	//	'months' || 'days'
	// type:
	//	'wide' || 'narrow' || 'abbr' (e.g. "Monday", "Mon", or "M" respectively, in English)
	// use:
	//	'standAlone' || 'format' (default)
	// locale:
	//	override locale used to find the names

	var label;
	var lookup = dojo.date.locale._getGregorianBundle(locale);
	var props = [item, use, type];
	if(use == 'standAlone'){
		var key = props.join('-');
		label = lookup[key];
		// Fall back to 'format' flavor of name
		if(label[0] == 1){ label = undefined; } // kludge, in the absense of real aliasing support in dojo.cldr
	}
	props[1] = 'format';

	// return by copy so changes won't be made accidentally to the in-memory model
	return (label || lookup[props.join('-')]).concat(); /*Array*/
};

dojo.date.locale.isWeekend = function(/*Date?*/dateObject, /*String?*/locale){
	// summary:
	//	Determines if the date falls on a weekend, according to local custom.

	var weekend = dojo.cldr.supplemental.getWeekend(locale);
	var day = (dateObject || new Date()).getDay();
	if(weekend.end < weekend.start){
		weekend.end += 7;
		if(day < weekend.start){ day += 7; }
	}
	return day >= weekend.start && day <= weekend.end; // Boolean
};

// These are used only by format and strftime.  Do they need to be public?  Which module should they go in?

dojo.date.locale._getDayOfYear = function(/*Date*/dateObject){
	// summary: gets the day of the year as represented by dateObject
	return dojo.date.difference(new Date(dateObject.getFullYear(), 0, 1, dateObject.getHours()), dateObject) + 1; // Number
};

dojo.date.locale._getWeekOfYear = function(/*Date*/dateObject, /*Number*/firstDayOfWeek){
	if(arguments.length == 1){ firstDayOfWeek = 0; } // Sunday

	var firstDayOfYear = new Date(dateObject.getFullYear(), 0, 1).getDay();
	var adj = (firstDayOfYear - firstDayOfWeek + 7) % 7;
	var week = Math.floor((dojo.date.locale._getDayOfYear(dateObject) + adj - 1) / 7);

	// if year starts on the specified day, start counting weeks at 1
	if(firstDayOfYear == firstDayOfWeek){ week++; }

	return week; // Number
};

}

if(!dojo._hasResource["dijit._Calendar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._Calendar"] = true;
dojo.provide("dijit._Calendar");








dojo.declare(
	"dijit._Calendar",
	[dijit._Widget, dijit._Templated],
	{
	//	
	//	summary:
	//		A simple GUI for choosing a date in the context of a monthly calendar.
	//
	//	description:
	//		A simple GUI for choosing a date in the context of a monthly calendar.
	//		This widget is used internally by other widgets and is not accessible
	//		as a standalone widget.
	//		This widget can't be used in a form because it doesn't serialize the date to an
	//		`<input>` field.  For a form element, use dijit.form.DateTextBox instead.
	//
	//		Note that the parser takes all dates attributes passed in the
	//		[RFC 3339 format](http://www.faqs.org/rfcs/rfc3339.html), e.g. `2005-06-30T08:05:00-07:00`
	//		so that they are serializable and locale-independent.
	//
	//	example:
	//	|	var calendar = new dijit._Calendar({}, dojo.byId("calendarNode"));
	//
	//	example:
	//	|	<div dojoType="dijit._Calendar"></div>
	//	
		templateString:"<table cellspacing=\"0\" cellpadding=\"0\" class=\"dijitCalendarContainer\">\n\t<thead>\n\t\t<tr class=\"dijitReset dijitCalendarMonthContainer\" valign=\"top\">\n\t\t\t<th class='dijitReset' dojoAttachPoint=\"decrementMonth\">\n\t\t\t\t<div class=\"dijitInline dijitCalendarIncrementControl dijitCalendarDecrease\"><span dojoAttachPoint=\"decreaseArrowNode\" class=\"dijitA11ySideArrow dijitCalendarIncrementControl dijitCalendarDecreaseInner\">-</span></div>\n\t\t\t</th>\n\t\t\t<th class='dijitReset' colspan=\"5\">\n\t\t\t\t<div dojoAttachPoint=\"monthLabelSpacer\" class=\"dijitCalendarMonthLabelSpacer\"></div>\n\t\t\t\t<div dojoAttachPoint=\"monthLabelNode\" class=\"dijitCalendarMonthLabel\"></div>\n\t\t\t</th>\n\t\t\t<th class='dijitReset' dojoAttachPoint=\"incrementMonth\">\n\t\t\t\t<div class=\"dijitInline dijitCalendarIncrementControl dijitCalendarIncrease\"><span dojoAttachPoint=\"increaseArrowNode\" class=\"dijitA11ySideArrow dijitCalendarIncrementControl dijitCalendarIncreaseInner\">+</span></div>\n\t\t\t</th>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<th class=\"dijitReset dijitCalendarDayLabelTemplate\"><span class=\"dijitCalendarDayLabel\"></span></th>\n\t\t</tr>\n\t</thead>\n\t<tbody dojoAttachEvent=\"onclick: _onDayClick, onmouseover: _onDayMouseOver, onmouseout: _onDayMouseOut\" class=\"dijitReset dijitCalendarBodyContainer\">\n\t\t<tr class=\"dijitReset dijitCalendarWeekTemplate\">\n\t\t\t<td class=\"dijitReset dijitCalendarDateTemplate\"><span class=\"dijitCalendarDateLabel\"></span></td>\n\t\t</tr>\n\t</tbody>\n\t<tfoot class=\"dijitReset dijitCalendarYearContainer\">\n\t\t<tr>\n\t\t\t<td class='dijitReset' valign=\"top\" colspan=\"7\">\n\t\t\t\t<h3 class=\"dijitCalendarYearLabel\">\n\t\t\t\t\t<span dojoAttachPoint=\"previousYearLabelNode\" class=\"dijitInline dijitCalendarPreviousYear\"></span>\n\t\t\t\t\t<span dojoAttachPoint=\"currentYearLabelNode\" class=\"dijitInline dijitCalendarSelectedYear\"></span>\n\t\t\t\t\t<span dojoAttachPoint=\"nextYearLabelNode\" class=\"dijitInline dijitCalendarNextYear\"></span>\n\t\t\t\t</h3>\n\t\t\t</td>\n\t\t</tr>\n\t</tfoot>\n</table>\t\n",

		// value: Date
		// 	the currently selected Date
		value: new Date(),

		// dayWidth: String
		// 	How to represent the days of the week in the calendar header. See dojo.date.locale
		dayWidth: "narrow",

		setValue: function(/*Date*/ value){
			dojo.deprecated("dijit.Calendar:setValue() is deprecated.  Use attr('value', ...) instead.", "", "2.0");
			this.attr('value', value);
		},
		_setValueAttr: function(/*Date*/ value){
			// summary:
			//		Hook to make attr("value", ...) work.
			// description:
			// 		Set the current date and update the UI.  If the date is disabled, the selection will
			//		not change, but the display will change to the corresponding month.
			if(!this.value || dojo.date.compare(value, this.value)){
				value = new Date(value);
				this.displayMonth = new Date(value);
				if(!this.isDisabledDate(value, this.lang)){
					this.value = value;
					this.value.setHours(0,0,0,0);
					this.onChange(this.value);
				}
				this._populateGrid();
			}
		},

		_setText: function(node, text){
			while(node.firstChild){
				node.removeChild(node.firstChild);
			}
			node.appendChild(dojo.doc.createTextNode(text));
		},

		_populateGrid: function(){
			var month = this.displayMonth;
			month.setDate(1);
			var firstDay = month.getDay();
			var daysInMonth = dojo.date.getDaysInMonth(month);
			var daysInPreviousMonth = dojo.date.getDaysInMonth(dojo.date.add(month, "month", -1));
			var today = new Date();
			var selected = this.value;

			var dayOffset = dojo.cldr.supplemental.getFirstDayOfWeek(this.lang);
			if(dayOffset > firstDay){ dayOffset -= 7; }

			// Iterate through dates in the calendar and fill in date numbers and style info
			dojo.query(".dijitCalendarDateTemplate", this.domNode).forEach(function(template, i){
				i += dayOffset;
				var date = new Date(month);
				var number, clazz = "dijitCalendar", adj = 0;

				if(i < firstDay){
					number = daysInPreviousMonth - firstDay + i + 1;
					adj = -1;
					clazz += "Previous";
				}else if(i >= (firstDay + daysInMonth)){
					number = i - firstDay - daysInMonth + 1;
					adj = 1;
					clazz += "Next";
				}else{
					number = i - firstDay + 1;
					clazz += "Current";
				}

				if(adj){
					date = dojo.date.add(date, "month", adj);
				}
				date.setDate(number);

				if(!dojo.date.compare(date, today, "date")){
					clazz = "dijitCalendarCurrentDate " + clazz;
				}

				if(!dojo.date.compare(date, selected, "date")){
					clazz = "dijitCalendarSelectedDate " + clazz;
				}

				if(this.isDisabledDate(date, this.lang)){
					clazz = "dijitCalendarDisabledDate " + clazz;
				}

				var clazz2 = this.getClassForDate(date, this.lang);
				if(clazz2){
					clazz = clazz2 + " " + clazz;
				}

				template.className =  clazz + "Month dijitCalendarDateTemplate";
				template.dijitDateValue = date.valueOf();
				var label = dojo.query(".dijitCalendarDateLabel", template)[0];
				this._setText(label, date.getDate());
			}, this);

			// Fill in localized month name
			var monthNames = dojo.date.locale.getNames('months', 'wide', 'standAlone', this.lang);
			this._setText(this.monthLabelNode, monthNames[month.getMonth()]);

			// Fill in localized prev/current/next years
			var y = month.getFullYear() - 1;
			var d = new Date();
			dojo.forEach(["previous", "current", "next"], function(name){
				d.setFullYear(y++);
				this._setText(this[name+"YearLabelNode"],
					dojo.date.locale.format(d, {selector:'year', locale:this.lang}));
			}, this);

			// Set up repeating mouse behavior
			var _this = this;
			var typematic = function(nodeProp, dateProp, adj){
				_this._connects.push(
					dijit.typematic.addMouseListener(_this[nodeProp], _this, function(count){
						if(count >= 0){ _this._adjustDisplay(dateProp, adj); }
					}, 0.8, 500)
				);
			};
			typematic("incrementMonth", "month", 1);
			typematic("decrementMonth", "month", -1);
			typematic("nextYearLabelNode", "year", 1);
			typematic("previousYearLabelNode", "year", -1);
		},

		goToToday: function(){
			this.attr('value', new Date());
		},

		postCreate: function(){
			this.inherited(arguments);
			
			var cloneClass = dojo.hitch(this, function(clazz, n){
				var template = dojo.query(clazz, this.domNode)[0];
	 			for(var i=0; i<n; i++){
					template.parentNode.appendChild(template.cloneNode(true));
				}
			});

			// clone the day label and calendar day templates 6 times to make 7 columns
			cloneClass(".dijitCalendarDayLabelTemplate", 6);
			cloneClass(".dijitCalendarDateTemplate", 6);

			// now make 6 week rows
			cloneClass(".dijitCalendarWeekTemplate", 5);

			// insert localized day names in the header
			var dayNames = dojo.date.locale.getNames('days', this.dayWidth, 'standAlone', this.lang);
			var dayOffset = dojo.cldr.supplemental.getFirstDayOfWeek(this.lang);
			dojo.query(".dijitCalendarDayLabel", this.domNode).forEach(function(label, i){
				this._setText(label, dayNames[(i + dayOffset) % 7]);
			}, this);

			// Fill in spacer element with all the month names (invisible) so that the maximum width will affect layout
			var monthNames = dojo.date.locale.getNames('months', 'wide', 'standAlone', this.lang);
			dojo.forEach(monthNames, function(name){
				var monthSpacer = dojo.doc.createElement("div");
				this._setText(monthSpacer, name);
				this.monthLabelSpacer.appendChild(monthSpacer);
			}, this);

			this.value = null;
			this.attr('value', new Date());
		},

		_adjustDisplay: function(/*String*/part, /*int*/amount){
			this.displayMonth = dojo.date.add(this.displayMonth, part, amount);
			this._populateGrid();
		},

		_onDayClick: function(/*Event*/evt){
			var node = evt.target;
			dojo.stopEvent(evt);
			while(!node.dijitDateValue){
				node = node.parentNode;
			}
			if(!dojo.hasClass(node, "dijitCalendarDisabledDate")){
				this.attr('value', node.dijitDateValue);
				this.onValueSelected(this.value);
			}
		},

		_onDayMouseOver: function(/*Event*/evt){
			var node = evt.target;
			if(node && (node.dijitDateValue || node == this.previousYearLabelNode || node == this.nextYearLabelNode) ){
				dojo.addClass(node, "dijitCalendarHoveredDate");
				this._currentNode = node;
			}
		},

		_onDayMouseOut: function(/*Event*/evt){
			if(!this._currentNode){ return; }
			for(var node = evt.relatedTarget; node;){
				if(node == this._currentNode){ return; }
				try{
					node = node.parentNode;
				}catch(x){
					node = null;
				}
			}
			dojo.removeClass(this._currentNode, "dijitCalendarHoveredDate");
			this._currentNode = null;
		},

		onValueSelected: function(/*Date*/date){
			// summary: a date cell was selected.  It may be the same as the previous value.
		},

		onChange: function(/*Date*/date){
			// summary: called only when the selected date has changed
		},

		isDisabledDate: function(/*Date*/dateObject, /*String?*/locale){
			// summary:
			//	May be overridden to disable certain dates in the calendar e.g. `isDisabledDate=dojo.date.locale.isWeekend`
/*=====
			return false; // Boolean
=====*/
		},

		getClassForDate: function(/*Date*/dateObject, /*String?*/locale){
			// summary:
			//  May be overridden to return CSS classes to associate with the date entry for the given dateObject,
			//  for example to indicate a holiday in specified locale.

/*=====
			return ""; // String
=====*/
		}
	}
);

}

if(!dojo._hasResource["dijit.form._DateTimeTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form._DateTimeTextBox"] = true;
dojo.provide("dijit.form._DateTimeTextBox");






/*=====
dojo.declare(
	"dijit.form._DateTimeTextBox.__Constraints",
	[dijit.form.RangeBoundTextBox.__Constraints, dojo.date.locale.__FormatOptions]
);
=====*/

dojo.declare(
	"dijit.form._DateTimeTextBox",
	dijit.form.RangeBoundTextBox,
	{
		// summary:
		//		A validating, serializable, range-bound date or time text box.
		//
		// constraints: dijit.form._DateTimeTextBox.__Constraints 

		/*=====
		constraints: {},
		======*/
		regExpGen: dojo.date.locale.regexp,
		compare: dojo.date.compare,
		format: function(/*Date*/ value, /*dojo.date.locale.__FormatOptions*/ constraints){
			//	summary: formats the value as a Date, according to constraints
			if(!value){ return ''; }
			return dojo.date.locale.format(value, constraints);
		},
		parse: function(/*String*/ value, /*dojo.date.locale.__FormatOptions*/ constraints){
			//	summary: parses the value as a Date, according to constraints
			return dojo.date.locale.parse(value, constraints) || (this._isEmpty(value)? null : undefined);
		},

		serialize: dojo.date.stamp.toISOString,

		//	value: Date
		//		The value of this widget as a JavaScript Date object.  Use attr("value") / attr("value", val) to manipulate.
		//		When passed to the parser in markup, must be specified according to `dojo.date.stamp.fromISOString`
		value: new Date(""),	// value.toString()="NaN"

		//	popupClass: String
		//		Name of the popup widget class used to select a date/time
		popupClass: "", // default is no popup = text only
		
		_selector: "",

		postMixInProperties: function(){
			//dijit.form.RangeBoundTextBox.prototype.postMixInProperties.apply(this, arguments);
			this.inherited(arguments);
			if(!this.value || this.value.toString() == dijit.form._DateTimeTextBox.prototype.value.toString()){
				this.value = null;
			}
			var constraints = this.constraints;
			constraints.selector = this._selector;
			constraints.fullYear = true; // see #5465 - always format with 4-digit years
			var fromISO = dojo.date.stamp.fromISOString;
			if(typeof constraints.min == "string"){ constraints.min = fromISO(constraints.min); }
 			if(typeof constraints.max == "string"){ constraints.max = fromISO(constraints.max); }
		},
		
		_onFocus: function(/*Event*/ evt){
			// summary: open the TimePicker popup
			this._open();
		},

		_setValueAttr: function(/*Date*/ value, /*Boolean?*/ priorityChange, /*String?*/ formattedValue){
			// summary:
			//	Sets the date on this textbox.  Note that `value` must be a Javascript Date object.
			this.inherited(arguments);
			if(this._picker){
				// #3948: fix blank date on popup only
				if(!value){value=new Date();}
				this._picker.attr('value', value);
			}
		},

		_open: function(){
			// summary:
			//	opens the TimePicker, and sets the onValueSelected value

			if(this.disabled || this.readOnly || !this.popupClass){return;}

			var textBox = this;

			if(!this._picker){
				var PopupProto=dojo.getObject(this.popupClass, false);
				this._picker = new PopupProto({
					onValueSelected: function(value){
						if(textBox._tabbingAway){
							delete textBox._tabbingAway;
						}else{
							textBox.focus(); // focus the textbox before the popup closes to avoid reopening the popup
						}
						setTimeout(dojo.hitch(textBox, "_close"), 1); // allow focus time to take

						// this will cause InlineEditBox and other handlers to do stuff so make sure it's last
						dijit.form._DateTimeTextBox.superclass._setValueAttr.call(textBox, value, true);
					},
					lang: textBox.lang,
					constraints: textBox.constraints,
					isDisabledDate: function(/*Date*/ date){
						// summary:
						// 	disables dates outside of the min/max of the _DateTimeTextBox
						var compare = dojo.date.compare;
						var constraints = textBox.constraints;
						return constraints && (constraints.min && (compare(constraints.min, date, "date") > 0) || 
							(constraints.max && compare(constraints.max, date, "date") < 0));
					}
				});
				this._picker.attr('value', this.attr('value') || new Date());
			}
			if(!this._opened){
				dijit.popup.open({
					parent: this,
					popup: this._picker,
					around: this.domNode,
					onCancel: dojo.hitch(this, this._close),
					onClose: function(){ textBox._opened=false; }
				});
				this._opened=true;
			}
			
			dojo.marginBox(this._picker.domNode,{ w:this.domNode.offsetWidth });
		},

		_close: function(){
			if(this._opened){
				dijit.popup.close(this._picker);
				this._opened=false;
			}			
		},

		_onBlur: function(){
			// summary: called magically when focus has shifted away from this widget and it's dropdown
			this._close();
			if(this._picker){
				// teardown so that constraints will be rebuilt next time (redundant reference: #6002)
				this._picker.destroy();
				delete this._picker;
			}
			this.inherited(arguments);
			// don't focus on <input>.  the user has explicitly focused on something else.
		},

		_getDisplayedValueAttr: function(){
			return this.textbox.value;
		},

		_setDisplayedValueAttr: function(/*String*/ value, /*Boolean?*/ priorityChange){
			this._setValueAttr(this.parse(value, this.constraints), priorityChange, value);
		},

		destroy: function(){
			if(this._picker){
				this._picker.destroy();
				delete this._picker;
			}
			this.inherited(arguments);
		},

		_onKeyPress: function(/*Event*/e){
			var p = this._picker, dk = dojo.keys;
			// Handle the key in the picker, if it has a handler.  If the handler
			// returns false, then don't handle any other keys.
			if(p && this._opened && p.handleKey){
				if(p.handleKey(e) === false){ return; }
			}
			if(this._opened && e.charOrCode == dk.ESCAPE && !e.shiftKey && !e.ctrlKey && !e.altKey){
				this._close();
				dojo.stopEvent(e);
			}else if(!this._opened && e.charOrCode == dk.DOWN_ARROW){
				this._open();
				dojo.stopEvent(e);
			}else if(dijit.form._DateTimeTextBox.superclass._onKeyPress.apply(this, arguments)){
				if(e.charOrCode === dk.TAB){
					this._tabbingAway = true;
				}else if(this._opened && (e.keyChar || e.charOrCode === dk.BACKSPACE || e.charOrCode == dk.DELETE)){
					// Replace the element - but do it after a delay to allow for 
					// filtering to occur
					setTimeout(dojo.hitch(this, function(){
						dijit.placeOnScreenAroundElement(p.domNode.parentNode, this.domNode, {'BL':'TL', 'TL':'BL'}, p.orient ? dojo.hitch(p, "orient") : null);
					}), 1);
				}
			}
		}
	}
);

}

if(!dojo._hasResource["dijit.form.DateTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.DateTextBox"] = true;
dojo.provide("dijit.form.DateTextBox");




dojo.declare(
	"dijit.form.DateTextBox",
	dijit.form._DateTimeTextBox,
	{
		// summary:
		//		A validating, serializable, range-bound date text box with a popup calendar

		baseClass: "dijitTextBox dijitDateTextBox",
		popupClass: "dijit._Calendar",
		_selector: "date"
	}
);

}

if(!dojo._hasResource["dijit.form.FilteringSelect"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.FilteringSelect"] = true;
dojo.provide("dijit.form.FilteringSelect");



dojo.declare(
	"dijit.form.FilteringSelect",
	[dijit.form.MappedTextBox, dijit.form.ComboBoxMixin],
	{
		// summary
		// An enhanced version of the HTML SELECT tag, populated dynamically
		//
		// description
		// An enhanced version of the HTML SELECT tag, populated dynamically. It works
		// very nicely with very large data sets because it can load and page data as needed.
		// It also resembles ComboBox, but does not allow values outside of the provided ones.
		//  
		// Similar features:
		//  - There is a drop down list of possible values.
		//	- You can only enter a value from the drop down list.  (You can't
		//	  enter an arbitrary value.)
		//	- The value submitted with the form is the hidden value (ex: CA),
		//	  not the displayed value a.k.a. label (ex: California)
		// 
		//	Enhancements over plain HTML version:
		//	- If you type in some text then it will filter down the list of
		//	  possible values in the drop down list.
		//	- List can be specified either as a static list or via a javascript
		//	  function (that can get the list from a server)
		//
		_isvalid:true,

		required:true,

		_lastDisplayedValue: "",

		isValid:function(){
			return this._isvalid || (!this.required && this.attr('displayedValue') == ""); // #5974
		},

		_callbackSetLabel: function(	/*Array*/ result, 
						/*Object*/ dataObject, 
						/*Boolean?*/ priorityChange){
			// summary:
			//		Callback function that dynamically sets the label of the
			//		ComboBox

			// setValue does a synchronous lookup,
			// so it calls _callbackSetLabel directly,
			// and so does not pass dataObject
			// still need to test against _lastQuery in case it came too late
			if((dataObject && dataObject.query[this.searchAttr] != this._lastQuery)||(!dataObject && result.length && this.store.getIdentity(result[0])!= this._lastQuery)){
				return;
			}
			if(!result.length){
				//#3268: do nothing on bad input
				//this._setValue("", "");
				//#3285: change CSS to indicate error
				if(priorityChange || !this._focused){ this.valueNode.value=""; }
				dijit.form.TextBox.superclass._setValueAttr.call(this, "", priorityChange || !this._focused);
				this._isvalid=false;
				this.validate(this._focused);
				this.item=null;
			}else{
				this._setValueFromItem(result[0], priorityChange);
			}
		},

		_openResultList: function(/*Object*/ results, /*Object*/ dataObject){
			// #3285: tap into search callback to see if user's query resembles a match
			if(dataObject.query[this.searchAttr] != this._lastQuery){
				return;
			}
			this._isvalid = results.length != 0; // FIXME: should this be greater-than?
			this.validate(true);
			dijit.form.ComboBoxMixin.prototype._openResultList.apply(this, arguments);
		},

		_getValueAttr: function(){
			// summary:
			//		Hook for attr('value') to work.

			// don't get the textbox value but rather the previously set hidden value
			// TODO: seems suspicious that we need this; how is FilteringSelect different
			// than another MappedTextBox widget?
			return this.valueNode.value;
		},

		_getValueField:function(){
			// used for option tag selects
			return "value";
		},

		_setValue: function(	/*String*/ value, 
					/*String*/ displayedValue,
					/*Boolean?*/ priorityChange){
			this.valueNode.value = value;
			dijit.form.FilteringSelect.superclass._setValueAttr.call(this, value, priorityChange, displayedValue);
			this._lastDisplayedValue = displayedValue;
		},

		_setValueAttr: function(/*String*/ value, /*Boolean?*/ priorityChange){
			// summary:
			//		Hook so attr('value', value) works.
			// description:
			//		Sets the value of the select.
			//		Also sets the label to the corresponding value by reverse lookup.
			if(!this._onChangeActive){ priorityChange = null; }
			this._lastQuery=value;

			if(value === null){
				this._setDisplayedValueAttr('', priorityChange);
				return;
			}

			//#3347: fetchItemByIdentity if no keyAttr specified
			var self=this;
			var handleFetchByIdentity = function(item, priorityChange){
				if(item){
					if(self.store.isItemLoaded(item)){
						self._callbackSetLabel([item], undefined, priorityChange);
					}else{
						self.store.loadItem({
							item: item, 
							onItem: function(result, dataObject){
								self._callbackSetLabel(result, dataObject, priorityChange);
							}
						});
					}
				}else{
					self._isvalid=false;
					// prevent errors from Tooltip not being created yet
					self.validate(false);
				}
			}
			this.store.fetchItemByIdentity({
				identity: value, 
				onItem: function(item){
					handleFetchByIdentity(item, priorityChange);
				}
			});
		},

		_setValueFromItem: function(/*item*/ item, /*Boolean?*/ priorityChange){
			//	summary:
			//		Set the displayed valued in the input box, based on a
			//		selected item.
			//	description:
			//		Users shouldn't call this function; they should be calling
			//		attr('displayedValue', value) instead
			this._isvalid=true;
			this.item = item; // Fix #6381
			this._setValue(	this.store.getIdentity(item), 
							this.labelFunc(item, this.store), 
							priorityChange);
		},

		labelFunc: function(/*item*/ item, /*dojo.data.store*/ store){
			// summary: Event handler called when the label changes
			// return: the label that the ComboBox should display
			return store.getValue(item, this.searchAttr);
		},

		_doSelect: function(/*Event*/ tgt){
			// summary:
			//		ComboBox's menu callback function
			//	description:
			//		FilteringSelect overrides this to set both the visible and
			//		hidden value from the information stored in the menu
			this._setValueFromItem(tgt.item, true);
		},

		_setDisplayedValueAttr: function(/*String*/ label, /*Boolean?*/ priorityChange){
			// summary:
			//		Hook so attr('displayedValue', label) works.
			// description:
			//		Set textbox to display label. Also performs reverse lookup
			//		to set the hidden value.

			// When this is called during initialization it'll ping the datastore
			// for reverse lookup, and when that completes (after an XHR request)
			// will call setValueAttr()... but that shouldn't trigger an onChange()
			//  event, even when it happens after creation has finished
			if(!this._created){
				priorityChange = false;
			}

			if(this.store){
				var query = dojo.clone(this.query); // #6196: populate query with user-specifics
				// escape meta characters of dojo.data.util.filter.patternToRegExp().
				this._lastQuery = query[this.searchAttr] = label.replace(/([\\\*\?])/g, "\\$1");
				// if the label is not valid, the callback will never set it,
				// so the last valid value will get the warning textbox set the
				// textbox value now so that the impending warning will make
				// sense to the user
				this.textbox.value = label;
				this._lastDisplayedValue = label;
				var _this = this;
				var fetch = {
					query: query, 
					queryOptions: {
						ignoreCase: this.ignoreCase, 
						deep: true
					}, 
					onComplete: function(result, dataObject){
						        dojo.hitch(_this, "_callbackSetLabel")(result, dataObject, priorityChange);
					},
					onError: function(errText){
						console.error('dijit.form.FilteringSelect: ' + errText);
						dojo.hitch(_this, "_setValue")("", label, false);
					}
				};
				dojo.mixin(fetch, this.fetchProperties);
				this.store.fetch(fetch);
			}
		},

		postMixInProperties: function(){
			// FIXME: shouldn't this just be a call to inherited?
			dijit.form.ComboBoxMixin.prototype.postMixInProperties.apply(this, arguments);
			dijit.form.MappedTextBox.prototype.postMixInProperties.apply(this, arguments);
		},

		postCreate: function(){
			dijit.form.ComboBoxMixin.prototype._postCreate.apply(this, arguments);
			dijit.form.MappedTextBox.prototype.postCreate.apply(this, arguments);
		},
		
		_setDisabledAttr: function(/*String*/ attr, /*anything*/ value){
			dijit.form.MappedTextBox.prototype._setDisabledAttr.apply(this, arguments);
			dijit.form.ComboBoxMixin.prototype._setDisabledAttr.apply(this, arguments);
		},

		undo: function(){
			this.attr('displayedValue', this._lastDisplayedValue);
		},

		_valueChanged: function(){
			return this.attr('displayedValue')!=this._lastDisplayedValue;
		}
	}
);

}

if(!dojo._hasResource["dijit.form._Spinner"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form._Spinner"] = true;
dojo.provide("dijit.form._Spinner");



dojo.declare(
	"dijit.form._Spinner",
	dijit.form.RangeBoundTextBox,
	{

		// summary: Mixin for validation widgets with a spinner
		// description: This class basically (conceptually) extends dijit.form.ValidationTextBox.
		//	It modifies the template to have up/down arrows, and provides related handling code.

		// defaultTimeout: Number
		//	  number of milliseconds before a held key or button becomes typematic
		defaultTimeout: 500,

		// timeoutChangeRate: Number
		//	  fraction of time used to change the typematic timer between events
		//	  1.0 means that each typematic event fires at defaultTimeout intervals
		//	  < 1.0 means that each typematic event fires at an increasing faster rate
		timeoutChangeRate: 0.90,

		// smallDelta: Number
		//	  adjust the value by this much when spinning using the arrow keys/buttons
		smallDelta: 1,
		// largeDelta: Number
		//	  adjust the value by this much when spinning using the PgUp/Dn keys
		largeDelta: 10,

		templateString:"<div class=\"dijit dijitReset dijitInlineTable dijitLeft\"\n\tid=\"widget_${id}\"\n\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\" waiRole=\"presentation\"\n\t><div class=\"dijitInputLayoutContainer\"\n\t\t><div class=\"dijitReset dijitSpinnerButtonContainer\"\n\t\t\t>&nbsp;<div class=\"dijitReset dijitLeft dijitButtonNode dijitArrowButton dijitUpArrowButton\"\n\t\t\t\tdojoAttachPoint=\"upArrowNode\"\n\t\t\t\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse\"\n\t\t\t\tstateModifier=\"UpArrow\"\n\t\t\t\t><div class=\"dijitArrowButtonInner\">&thinsp;</div\n\t\t\t\t><div class=\"dijitArrowButtonChar\">&#9650;</div\n\t\t\t></div\n\t\t\t><div class=\"dijitReset dijitLeft dijitButtonNode dijitArrowButton dijitDownArrowButton\"\n\t\t\t\tdojoAttachPoint=\"downArrowNode\"\n\t\t\t\tdojoAttachEvent=\"onmouseenter:_onMouse,onmouseleave:_onMouse\"\n\t\t\t\tstateModifier=\"DownArrow\"\n\t\t\t\t><div class=\"dijitArrowButtonInner\">&thinsp;</div\n\t\t\t\t><div class=\"dijitArrowButtonChar\">&#9660;</div\n\t\t\t></div\n\t\t></div\n\t\t><div class=\"dijitReset dijitValidationIcon\"><br></div\n\t\t><div class=\"dijitReset dijitValidationIconText\">&Chi;</div\n\t\t><div class=\"dijitReset dijitInputField\"\n\t\t\t><input class='dijitReset' dojoAttachPoint=\"textbox,focusNode\" type=\"${type}\" dojoAttachEvent=\"onfocus:_update,onkeyup:_update,onkeypress:_onKeyPress\"\n\t\t\t\twaiRole=\"spinbutton\" autocomplete=\"off\" name=\"${name}\"\n\t\t/></div\n\t></div\n></div>\n",
		baseClass: "dijitSpinner",

		adjust: function(/* Object */ val, /*Number*/ delta){
			// summary: user replaceable function used to adjust a primitive value(Number/Date/...) by the delta amount specified
			// the val is adjusted in a way that makes sense to the object type
			return val;
		},

		_arrowState: function(/*Node*/ node, /*Boolean*/ pressed){
			this._active = pressed;
			this.stateModifier = node.getAttribute("stateModifier") || "";
			this._setStateClass();
		},

		_arrowPressed: function(/*Node*/ nodePressed, /*Number*/ direction, /*Number*/ increment){
			if(this.disabled || this.readOnly){ return; }
			this._arrowState(nodePressed, true);
			this._setValueAttr(this.adjust(this.attr('value'), direction*increment), false);
			dijit.selectInputText(this.textbox, this.textbox.value.length);
		},

		_arrowReleased: function(/*Node*/ node){
			this._wheelTimer = null;
			if(this.disabled || this.readOnly){ return; }
			this._arrowState(node, false);
		},

		_typematicCallback: function(/*Number*/ count, /*DOMNode*/ node, /*Event*/ evt){
			var inc=this.smallDelta;
			if(node == this.textbox){
				k=dojo.keys;
				var key = evt.charOrCode; 
				inc = (key == k.PAGE_UP || key == k.PAGE_DOWN) ? this.largeDelta : this.smallDelta;
				node = (key == k.UP_ARROW ||key == k.PAGE_UP) ? this.upArrowNode : this.downArrowNode;
			}
			if(count == -1){ this._arrowReleased(node); }
			else{ this._arrowPressed(node, (node == this.upArrowNode) ? 1 : -1, inc); }
		},

		_wheelTimer: null,
		_mouseWheeled: function(/*Event*/ evt){
			// summary: Mouse wheel listener where supported
			dojo.stopEvent(evt);	
			// FIXME: Safari bubbles

			// be nice to DOH and scroll as much as the event says to
			var scrollAmount = evt.detail ? (evt.detail * -1) : (evt.wheelDelta / 120);
			if(scrollAmount !== 0){
				var node = this[(scrollAmount > 0 ? "upArrowNode" : "downArrowNode" )];
				
				this._arrowPressed(node, scrollAmount, this.smallDelta);

				if(!this._wheelTimer){
					clearTimeout(this._wheelTimer);
				}
				this._wheelTimer = setTimeout(dojo.hitch(this,"_arrowReleased",node), 50);
			}
			
		},

		postCreate: function(){
			this.inherited('postCreate', arguments);

			// extra listeners
			this.connect(this.domNode, !dojo.isMozilla ? "onmousewheel" : 'DOMMouseScroll', "_mouseWheeled");
			this._connects.push(dijit.typematic.addListener(this.upArrowNode, this.textbox, {charOrCode:dojo.keys.UP_ARROW,ctrlKey:false,altKey:false,shiftKey:false}, this, "_typematicCallback", this.timeoutChangeRate, this.defaultTimeout));
			this._connects.push(dijit.typematic.addListener(this.downArrowNode, this.textbox, {charOrCode:dojo.keys.DOWN_ARROW,ctrlKey:false,altKey:false,shiftKey:false}, this, "_typematicCallback", this.timeoutChangeRate, this.defaultTimeout));
			this._connects.push(dijit.typematic.addListener(this.upArrowNode, this.textbox, {charOrCode:dojo.keys.PAGE_UP,ctrlKey:false,altKey:false,shiftKey:false}, this, "_typematicCallback", this.timeoutChangeRate, this.defaultTimeout));
			this._connects.push(dijit.typematic.addListener(this.downArrowNode, this.textbox, {charOrCode:dojo.keys.PAGE_DOWN,ctrlKey:false,altKey:false,shiftKey:false}, this, "_typematicCallback", this.timeoutChangeRate, this.defaultTimeout));
			if(dojo.isIE){
				var _this = this;
				this.connect(this.domNode, "onresize",
					function(){ setTimeout(dojo.hitch(_this,
						function(){
				        		var sz = this.upArrowNode.parentNode.offsetHeight;
							if(sz){
								this.upArrowNode.style.height = sz >> 1;
								this.downArrowNode.style.height = sz - (sz >> 1);
								this.focusNode.parentNode.style.height = sz;
							}
							// cause IE to rerender when spinner is moved from hidden to visible
							this._setStateClass();
						}), 0);
					}
				);
			}
		}
});

}

if(!dojo._hasResource["dijit.form.NumberSpinner"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.NumberSpinner"] = true;
dojo.provide("dijit.form.NumberSpinner");




dojo.declare(
"dijit.form.NumberSpinner",
[dijit.form._Spinner, dijit.form.NumberTextBoxMixin],
{
	// summary:
	// extends NumberTextBox to add up/down arrows and pageup/pagedown for incremental change to the value

	required: true,

	adjust: function(/* Object */ val, /*Number*/ delta){
		// summary: change Number val by the given amount
		if(isNaN(val) && delta != 0){ // blank or invalid value and they want to spin, so create defaults
			var increasing = (delta > 0),
				gotMax = (typeof this.constraints.max == "number"),
				gotMin = (typeof this.constraints.min == "number");
			val = increasing? (gotMin? this.constraints.min : (gotMax? this.constraints.max : 0)) :
					(gotMax? this.constraints.max : (gotMin? this.constraints.min : 0));
		}
		var newval = val+delta;
		if(isNaN(val) || isNaN(newval)){ return val; }
		if((typeof this.constraints.max == "number") && (newval > this.constraints.max)){
			newval = this.constraints.max;
		}
		if((typeof this.constraints.min == "number") && (newval < this.constraints.min)){
			newval = this.constraints.min;
		}
		return newval;
	},
	_onKeyPress: function(e){
		if((e.charOrCode == dojo.keys.HOME || e.charOrCode == dojo.keys.END) && !e.ctrlKey && !e.altKey){
			var value = e.charOrCode == dojo.keys.HOME ? this.constraints["min"] : this.constraints["max"];
			if (value){
				this._setValueAttr(value,true);
			}
			// eat home or end key whether we change the value or not
			dojo.stopEvent(e);
			return false;
		}
		else{
			return this.inherited(arguments);
		}
	}
});

}

if(!dojo._hasResource["dijit.form.Slider"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.Slider"] = true;
dojo.provide("dijit.form.Slider");








dojo.declare(
	"dijit.form.HorizontalSlider",
	[dijit.form._FormValueWidget, dijit._Container],
{
	// summary
	//	A form widget that allows one to select a value with a horizontally draggable image

	templateString:"<table class=\"dijit dijitReset dijitSlider\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" rules=\"none\" dojoAttachEvent=\"onkeypress:_onKeyPress\"\n\t><tr class=\"dijitReset\"\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\n\t\t><td dojoAttachPoint=\"containerNode,topDecoration\" class=\"dijitReset\" style=\"text-align:center;width:100%;\"></td\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\n\t></tr\n\t><tr class=\"dijitReset\"\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerH\"\n\t\t\t><div class=\"dijitSliderDecrementIconH\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"decrementButton\"><span class=\"dijitSliderButtonInner\">-</span></div\n\t\t></td\n\t\t><td class=\"dijitReset\"\n\t\t\t><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperH dijitSliderLeftBumper dijitSliderLeftBumper\" dojoAttachEvent=\"onclick:_onClkDecBumper\"></div\n\t\t></td\n\t\t><td class=\"dijitReset\"\n\t\t\t><input dojoAttachPoint=\"valueNode\" type=\"hidden\" name=\"${name}\"\n\t\t\t/><div class=\"dijitReset dijitSliderBarContainerH\" waiRole=\"presentation\" dojoAttachPoint=\"sliderBarContainer\"\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"progressBar\" class=\"dijitSliderBar dijitSliderBarH dijitSliderProgressBar dijitSliderProgressBarH\" dojoAttachEvent=\"onclick:_onBarClick\"\n\t\t\t\t\t><div class=\"dijitSliderMoveable dijitSliderMoveableH\" \n\t\t\t\t\t\t><div dojoAttachPoint=\"sliderHandle,focusNode\" class=\"dijitSliderImageHandle dijitSliderImageHandleH\" dojoAttachEvent=\"onmousedown:_onHandleClick\" waiRole=\"slider\" valuemin=\"${minimum}\" valuemax=\"${maximum}\"></div\n\t\t\t\t\t></div\n\t\t\t\t></div\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"remainingBar\" class=\"dijitSliderBar dijitSliderBarH dijitSliderRemainingBar dijitSliderRemainingBarH\" dojoAttachEvent=\"onclick:_onBarClick\"></div\n\t\t\t></div\n\t\t></td\n\t\t><td class=\"dijitReset\"\n\t\t\t><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperH dijitSliderRightBumper dijitSliderRightBumper\" dojoAttachEvent=\"onclick:_onClkIncBumper\"></div\n\t\t></td\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerH\" style=\"right:0px;\"\n\t\t\t><div class=\"dijitSliderIncrementIconH\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"incrementButton\"><span class=\"dijitSliderButtonInner\">+</span></div\n\t\t></td\n\t></tr\n\t><tr class=\"dijitReset\"\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\n\t\t><td dojoAttachPoint=\"containerNode,bottomDecoration\" class=\"dijitReset\" style=\"text-align:center;\"></td\n\t\t><td class=\"dijitReset\" colspan=\"2\"></td\n\t></tr\n></table>\n",
	value: 0,

	// showButtons: boolean
	//	Show increment/decrement buttons at the ends of the slider?
	showButtons: true,

	// minimum:: integer
	//	The minimum value allowed.
	minimum: 0,

	// maximum: integer
	//	The maximum allowed value.
	maximum: 100,

	// discreteValues: integer
	//	The maximum allowed values dispersed evenly between minimum and maximum (inclusive).
	discreteValues: Infinity,

	// pageIncrement: integer
	//	The amount of change via pageup/down
	pageIncrement: 2,

	// clickSelect: boolean
	//	If clicking the progress bar changes the value or not
	clickSelect: true,

	// slideDuration: Number
	//	The time in ms to take to animate the slider handle from 0% to 100%
	slideDuration: dijit.defaultDuration,

	widgetsInTemplate: true,

	attributeMap: dojo.mixin(dojo.clone(dijit.form._FormWidget.prototype.attributeMap),
		{id:"", name:"valueNode"}),

	baseClass: "dijitSlider",

	_mousePixelCoord: "pageX",
	_pixelCount: "w",
	_startingPixelCoord: "x",
	_startingPixelCount: "l",
	_handleOffsetCoord: "left",
	_progressPixelSize: "width",

	_onKeyPress: function(/*Event*/ e){
		if(this.disabled || this.readOnly || e.altKey || e.ctrlKey){ return; }
		switch(e.charOrCode){
			case dojo.keys.HOME:
				this._setValueAttr(this.minimum, true);
				break;
			case dojo.keys.END:
				this._setValueAttr(this.maximum, true);
				break;
			// this._descending === false: if ascending vertical (min on top)
			// (this._descending || this.isLeftToRight()): if left-to-right horizontal or descending vertical
			case ((this._descending || this.isLeftToRight()) ? dojo.keys.RIGHT_ARROW : dojo.keys.LEFT_ARROW):
			case (this._descending === false ? dojo.keys.DOWN_ARROW : dojo.keys.UP_ARROW):
			case (this._descending === false ? dojo.keys.PAGE_DOWN : dojo.keys.PAGE_UP):
				this.increment(e);
				break;
			case ((this._descending || this.isLeftToRight()) ? dojo.keys.LEFT_ARROW : dojo.keys.RIGHT_ARROW):
			case (this._descending === false ? dojo.keys.UP_ARROW : dojo.keys.DOWN_ARROW):
			case (this._descending === false ? dojo.keys.PAGE_UP : dojo.keys.PAGE_DOWN):
				this.decrement(e);
				break;
			default:
				this.inherited(arguments);
				return;
		}
		dojo.stopEvent(e);
	},

	_onHandleClick: function(e){
		if(this.disabled || this.readOnly){ return; }
		if(!dojo.isIE){
			// make sure you get focus when dragging the handle
			// (but don't do on IE because it causes a flicker on mouse up (due to blur then focus)
			dijit.focus(this.sliderHandle);
		}
		dojo.stopEvent(e);
	},
	
	_isReversed: function(){
		return !this.isLeftToRight();
	},

	_onBarClick: function(e){
		if(this.disabled || this.readOnly || !this.clickSelect){ return; }
		dijit.focus(this.sliderHandle);
		dojo.stopEvent(e);
		var abspos = dojo.coords(this.sliderBarContainer, true);
		var pixelValue = e[this._mousePixelCoord] - abspos[this._startingPixelCoord];
		this._setPixelValue(this._isReversed() ? (abspos[this._pixelCount] - pixelValue) : pixelValue, abspos[this._pixelCount], true);
	},

	_setPixelValue: function(/*Number*/ pixelValue, /*Number*/ maxPixels, /*Boolean, optional*/ priorityChange){
		if(this.disabled || this.readOnly){ return; }
		pixelValue = pixelValue < 0 ? 0 : maxPixels < pixelValue ? maxPixels : pixelValue;
		var count = this.discreteValues;
		if(count <= 1 || count == Infinity){ count = maxPixels; }
		count--;
		var pixelsPerValue = maxPixels / count;
		var wholeIncrements = Math.round(pixelValue / pixelsPerValue);
		this._setValueAttr((this.maximum-this.minimum)*wholeIncrements/count + this.minimum, priorityChange);
	},

	_setValueAttr: function(/*Number*/ value, /*Boolean, optional*/ priorityChange){
		// summary:
		//		Hook so attr('value', value) works.
		this.valueNode.value = this.value = value;
		dijit.setWaiState(this.focusNode, "valuenow", value);
		this.inherited(arguments);
		var percent = (value - this.minimum) / (this.maximum - this.minimum);
		var progressBar = (this._descending === false) ? this.remainingBar : this.progressBar;
		var remainingBar = (this._descending === false) ? this.progressBar : this.remainingBar;
		if(this._inProgressAnim && this._inProgressAnim.status != "stopped"){
			this._inProgressAnim.stop(true);
		}
		if(priorityChange && this.slideDuration > 0 && progressBar.style[this._progressPixelSize]){
			// animate the slider
			var _this = this;
			var props = {};
			var start = parseFloat(progressBar.style[this._progressPixelSize]);
			var duration = this.slideDuration * (percent-start/100);
			if(duration == 0){ return; }
			if(duration < 0){ duration = 0 - duration; }
			props[this._progressPixelSize] = { start: start, end: percent*100, units:"%" };
			this._inProgressAnim = dojo.animateProperty({ node: progressBar, duration: duration, 
				onAnimate: function(v){ remainingBar.style[_this._progressPixelSize] = (100-parseFloat(v[_this._progressPixelSize])) + "%"; },
				onEnd: function(){ delete _this._inProgressAnim; },
				properties: props
			})
			this._inProgressAnim.play();
		}
		else{
			progressBar.style[this._progressPixelSize] = (percent*100) + "%";
			remainingBar.style[this._progressPixelSize] = ((1-percent)*100) + "%";
		}
	},

	_bumpValue: function(signedChange){
		if(this.disabled || this.readOnly){ return; }
		var s = dojo.getComputedStyle(this.sliderBarContainer);
		var c = dojo._getContentBox(this.sliderBarContainer, s);
		var count = this.discreteValues;
		if(count <= 1 || count == Infinity){ count = c[this._pixelCount]; }
		count--;
		var value = (this.value - this.minimum) * count / (this.maximum - this.minimum) + signedChange;
		if(value < 0){ value = 0; }
		if(value > count){ value = count; }
		value = value * (this.maximum - this.minimum) / count + this.minimum;
		this._setValueAttr(value, true);
	},

	_onClkIncBumper: function(){
		this._setValueAttr(this._descending === false ? this.minimum : this.maximum, true);
	},

	_onClkDecBumper: function(){
		this._setValueAttr(this._descending === false ? this.maximum : this.minimum, true);
	},

	decrement: function(e){
		// summary
		//	decrement slider by 1 unit
		this._bumpValue(e.charOrCode == dojo.keys.PAGE_DOWN ? -this.pageIncrement : -1);
	},

	increment: function(e){
		// summary
		//	increment slider by 1 unit
		this._bumpValue(e.charOrCode == dojo.keys.PAGE_UP ? this.pageIncrement : 1);
	},

	_mouseWheeled: function(/*Event*/ evt){
		// summary: Event handler for mousewheel where supported
		dojo.stopEvent(evt);
		// FIXME: this adds mouse wheel support for safari, though stopEvent doesn't prevent
		// it from bleeding to window?!
		var janky = !dojo.isMozilla;
		var scroll = evt[(janky ? "wheelDelta" : "detail")] * (janky ? 1 : -1);
		this[(scroll < 0 ? "decrement" : "increment")](evt);
	},

	startup: function(){
		dojo.forEach(this.getChildren(), function(child){
			if(this[child.container] != this.containerNode){
				this[child.container].appendChild(child.domNode);
			}
		}, this);
	},

	_typematicCallback: function(/*Number*/ count, /*Object*/ button, /*Event*/ e){
		if(count == -1){ return; }
		this[(button == (this._descending? this.incrementButton : this.decrementButton))? "decrement" : "increment"](e);
	},

	postCreate: function(){
		if(this.showButtons){
			this.incrementButton.style.display="";
			this.decrementButton.style.display="";
			this._connects.push(dijit.typematic.addMouseListener(
				this.decrementButton, this, "_typematicCallback", 25, 500));
			this._connects.push(dijit.typematic.addMouseListener(
				this.incrementButton, this, "_typematicCallback", 25, 500));
		}
		this.connect(this.domNode, !dojo.isMozilla ? "onmousewheel" : "DOMMouseScroll", "_mouseWheeled");

		// define a custom constructor for a SliderMover that points back to me
		var _self = this;
		var mover = function(){
			dijit.form._SliderMover.apply(this, arguments);
			this.widget = _self;
		};
		dojo.extend(mover, dijit.form._SliderMover.prototype);

		this._movable = new dojo.dnd.Moveable(this.sliderHandle, {mover: mover});
		//find any associated label element and add to slider focusnode.
		var label=dojo.query('label[for="'+this.id+'"]');
		if(label.length){
			label[0].id = (this.id+"_label");
			dijit.setWaiState(this.focusNode, "labelledby", label[0].id);
		}
		dijit.setWaiState(this.focusNode, "valuemin", this.minimum);
		dijit.setWaiState(this.focusNode, "valuemax", this.maximum);

		this.inherited(arguments);
	},

	destroy: function(){
		this._movable.destroy();
		if(this._inProgressAnim && this._inProgressAnim.status != "stopped"){
			this._inProgressAnim.stop(true);
		}
		this.inherited(arguments);	
	}
});

dojo.declare(
	"dijit.form.VerticalSlider",
	dijit.form.HorizontalSlider,
{
	// summary
	//	A form widget that allows one to select a value with a vertically draggable image

	templateString:"<table class=\"dijitReset dijitSlider\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" rules=\"none\" dojoAttachEvent=\"onkeypress:_onKeyPress\"\n><tbody class=\"dijitReset\"\n\t><tr class=\"dijitReset\"\n\t\t><td class=\"dijitReset\"></td\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerV\"\n\t\t\t><div class=\"dijitSliderIncrementIconV\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"decrementButton\"><span class=\"dijitSliderButtonInner\">+</span></div\n\t\t></td\n\t\t><td class=\"dijitReset\"></td\n\t></tr\n\t><tr class=\"dijitReset\"\n\t\t><td class=\"dijitReset\"></td\n\t\t><td class=\"dijitReset\"\n\t\t\t><center><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperV dijitSliderTopBumper dijitSliderTopBumper\" dojoAttachEvent=\"onclick:_onClkIncBumper\"></div></center\n\t\t></td\n\t\t><td class=\"dijitReset\"></td\n\t></tr\n\t><tr class=\"dijitReset\"\n\t\t><td dojoAttachPoint=\"leftDecoration\" class=\"dijitReset\" style=\"text-align:center;height:100%;\"></td\n\t\t><td class=\"dijitReset\" style=\"height:100%;\"\n\t\t\t><input dojoAttachPoint=\"valueNode\" type=\"hidden\" name=\"${name}\"\n\t\t\t/><center class=\"dijitReset dijitSliderBarContainerV\" waiRole=\"presentation\" dojoAttachPoint=\"sliderBarContainer\"\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"remainingBar\" class=\"dijitSliderBar dijitSliderBarV dijitSliderRemainingBar dijitSliderRemainingBarV\" dojoAttachEvent=\"onclick:_onBarClick\"><!--#5629--></div\n\t\t\t\t><div waiRole=\"presentation\" dojoAttachPoint=\"progressBar\" class=\"dijitSliderBar dijitSliderBarV dijitSliderProgressBar dijitSliderProgressBarV\" dojoAttachEvent=\"onclick:_onBarClick\"\n\t\t\t\t\t><div class=\"dijitSliderMoveable\" style=\"vertical-align:top;\" \n\t\t\t\t\t\t><div dojoAttachPoint=\"sliderHandle,focusNode\" class=\"dijitSliderImageHandle dijitSliderImageHandleV\" dojoAttachEvent=\"onmousedown:_onHandleClick\" waiRole=\"slider\" valuemin=\"${minimum}\" valuemax=\"${maximum}\"></div\n\t\t\t\t\t></div\n\t\t\t\t></div\n\t\t\t></center\n\t\t></td\n\t\t><td dojoAttachPoint=\"containerNode,rightDecoration\" class=\"dijitReset\" style=\"text-align:center;height:100%;\"></td\n\t></tr\n\t><tr class=\"dijitReset\"\n\t\t><td class=\"dijitReset\"></td\n\t\t><td class=\"dijitReset\"\n\t\t\t><center><div class=\"dijitSliderBar dijitSliderBumper dijitSliderBumperV dijitSliderBottomBumper dijitSliderBottomBumper\" dojoAttachEvent=\"onclick:_onClkDecBumper\"></div></center\n\t\t></td\n\t\t><td class=\"dijitReset\"></td\n\t></tr\n\t><tr class=\"dijitReset\"\n\t\t><td class=\"dijitReset\"></td\n\t\t><td class=\"dijitReset dijitSliderButtonContainer dijitSliderButtonContainerV\"\n\t\t\t><div class=\"dijitSliderDecrementIconV\" tabIndex=\"-1\" style=\"display:none\" dojoAttachPoint=\"incrementButton\"><span class=\"dijitSliderButtonInner\">-</span></div\n\t\t></td\n\t\t><td class=\"dijitReset\"></td\n\t></tr\n></tbody></table>\n",
	_mousePixelCoord: "pageY",
	_pixelCount: "h",
	_startingPixelCoord: "y",
	_startingPixelCount: "t",
	_handleOffsetCoord: "top",
	_progressPixelSize: "height",

	// _descending: boolean
	//      Specifies if the slider values go from high-on-top (true), or low-on-top (false)
	//	TODO: expose this in 1.2 - the css progress/remaining bar classes need to be reversed
	_descending: true,

	startup: function(){
		if(this._started){ return; }

		if(!this.isLeftToRight() && dojo.isMoz){
			if(this.leftDecoration){this._rtlRectify(this.leftDecoration);}
			if(this.rightDecoration){this._rtlRectify(this.rightDecoration);}
		}

		this.inherited(arguments);
	},
		
	_isReversed: function(){
		return this._descending;
	},

	_rtlRectify: function(decorationNode/*NodeList*/){
		// summary:
		//      Rectify children nodes for left/right decoration in rtl case.
		//		Simply switch the rule and label child for each decoration node.
		var childNodes = [];
		while(decorationNode.firstChild){
				childNodes.push(decorationNode.firstChild);
				decorationNode.removeChild(decorationNode.firstChild);
		}
		for(var i = childNodes.length-1; i >=0; i--){
			if(childNodes[i]){
				decorationNode.appendChild(childNodes[i]);
			}
		}
	}
});

dojo.declare("dijit.form._SliderMover",
	dojo.dnd.Mover,
{
	onMouseMove: function(e){
		var widget = this.widget;
		var abspos = widget._abspos;
		if(!abspos){
			abspos = widget._abspos = dojo.coords(widget.sliderBarContainer, true);
			widget._setPixelValue_ = dojo.hitch(widget, "_setPixelValue");
			widget._isReversed_ = widget._isReversed();
		}
		var pixelValue = e[widget._mousePixelCoord] - abspos[widget._startingPixelCoord];
		widget._setPixelValue_(widget._isReversed_ ? (abspos[widget._pixelCount]-pixelValue) : pixelValue, abspos[widget._pixelCount], false);
	},
	
	destroy: function(e){
		dojo.dnd.Mover.prototype.destroy.apply(this, arguments);
		var widget = this.widget;
		widget._abspos = null;
		widget._setValueAttr(widget.value, true);
	}
});


dojo.declare("dijit.form.HorizontalRule", [dijit._Widget, dijit._Templated],
{
	//	Summary:
	//		Create hash marks for the Horizontal slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerH"></div>',

	// count: Integer
	//		Number of hash marks to generate
	count: 3,

	// container: Node
	//		If this is a child widget, connect it to this parent node
	container: "containerNode",

	// ruleStyle: String
	//		CSS style to apply to individual hash marks
	ruleStyle: "",

	_positionPrefix: '<div class="dijitRuleMark dijitRuleMarkH" style="left:',
	_positionSuffix: '%;',
	_suffix: '"></div>',

	_genHTML: function(pos, ndx){
		return this._positionPrefix + pos + this._positionSuffix + this.ruleStyle + this._suffix;
	},
	
	_isHorizontal: true,

	postCreate: function(){
		var innerHTML;
		if(this.count==1){
			innerHTML = this._genHTML(50, 0);
		}else{
			var i;
			var interval = 100 / (this.count-1);
			if(!this._isHorizontal || this.isLeftToRight()){
				innerHTML = this._genHTML(0, 0);
				for(i=1; i < this.count-1; i++){
					innerHTML += this._genHTML(interval*i, i);
				}
				innerHTML += this._genHTML(100, this.count-1);
			}else{
				innerHTML = this._genHTML(100, 0);
				for(i=1; i < this.count-1; i++){
					innerHTML += this._genHTML(100-interval*i, i);
				}
				innerHTML += this._genHTML(0, this.count-1);
			}
		}
		this.domNode.innerHTML = innerHTML;
	}
});

dojo.declare("dijit.form.VerticalRule", dijit.form.HorizontalRule,
{
	//	Summary:
	//		Create hash marks for the Vertical slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerV"></div>',
	_positionPrefix: '<div class="dijitRuleMark dijitRuleMarkV" style="top:',
	
	_isHorizontal: false
});

dojo.declare("dijit.form.HorizontalRuleLabels", dijit.form.HorizontalRule,
{
	//	Summary:
	//		Create labels for the Horizontal slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerH dijitRuleLabelsContainer dijitRuleLabelsContainerH"></div>',

	// labelStyle: String
	//		CSS style to apply to individual text labels
	labelStyle: "",

	// labels: Array
	//	Array of text labels to render - evenly spaced from left-to-right or bottom-to-top
	labels: [],

	// numericMargin: Integer
	//	Number of generated numeric labels that should be rendered as '' on the ends when labels[] are not specified
	numericMargin: 0,

	// numericMinimum: Integer
	//	Leftmost label value for generated numeric labels when labels[] are not specified
	minimum: 0,

	// numericMaximum: Integer
	//	Rightmost label value for generated numeric labels when labels[] are not specified
	maximum: 1,

	// constraints: object
	//	pattern, places, lang, et al (see dojo.number) for generated numeric labels when labels[] are not specified
	constraints: {pattern:"#%"},

	_positionPrefix: '<div class="dijitRuleLabelContainer dijitRuleLabelContainerH" style="left:',
	_labelPrefix: '"><span class="dijitRuleLabel dijitRuleLabelH">',
	_suffix: '</span></div>',

	_calcPosition: function(pos){
		return pos;
	},

	_genHTML: function(pos, ndx){
		return this._positionPrefix + this._calcPosition(pos) + this._positionSuffix + this.labelStyle + this._labelPrefix + this.labels[ndx] + this._suffix;
	},

	getLabels: function(){
		// summary: user replaceable function to return the labels array

		// if the labels array was not specified directly, then see if <li> children were
		var labels = this.labels;
		if(!labels.length){
			// for markup creation, labels are specified as child elements
			labels = dojo.query("> li", this.srcNodeRef).map(function(node){
				return String(node.innerHTML);
			});
		}
		this.srcNodeRef.innerHTML = '';
		// if the labels were not specified directly and not as <li> children, then calculate numeric labels
		if(!labels.length && this.count > 1){
			var start = this.minimum;
			var inc = (this.maximum - start) / (this.count-1);
			for (var i=0; i < this.count; i++){
				labels.push((i<this.numericMargin||i>=(this.count-this.numericMargin))? '' : dojo.number.format(start, this.constraints));
				start += inc;
			}
		}
		return labels;
	},

	postMixInProperties: function(){
		this.inherited(arguments);
		this.labels = this.getLabels();
		this.count = this.labels.length;
	}
});

dojo.declare("dijit.form.VerticalRuleLabels", dijit.form.HorizontalRuleLabels,
{
	//	Summary:
	//		Create labels for the Vertical slider
	templateString: '<div class="dijitRuleContainer dijitRuleContainerV dijitRuleLabelsContainer dijitRuleLabelsContainerV"></div>',

	_positionPrefix: '<div class="dijitRuleLabelContainer dijitRuleLabelContainerV" style="top:',
	_labelPrefix: '"><span class="dijitRuleLabel dijitRuleLabelV">',

	_calcPosition: function(pos){
		return 100-pos;
	},
	
	_isHorizontal: false
});

}

if(!dojo._hasResource["dijit.form.Textarea"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.Textarea"] = true;
dojo.provide("dijit.form.Textarea");





dojo.declare(
	"dijit.form.Textarea",
	dijit.form._FormValueWidget,
	{
	// summary: A resizing textarea widget
	//
	// description:
	//	A textarea that resizes vertically to contain the data.
	//	Takes nearly all the parameters (name, value, etc.) that a vanilla textarea takes.
	//	Cols is not supported and the width should be specified with style width.
	//	Rows is not supported since this widget adjusts the height.
	//
	// example:
	// |	<textarea dojoType="dijit.form.TextArea">...</textarea>

	attributeMap: dojo.mixin(dojo.clone(dijit.form._FormValueWidget.prototype.attributeMap),
		{style:"styleNode", 'class':"styleNode"}),

	templateString: (dojo.isIE || dojo.isSafari || dojo.isFF) ?
				((dojo.isIE || dojo.isSafari || dojo.isFF >= 3) ? '<fieldset id="${id}" class="dijitInline" dojoAttachPoint="styleNode" waiRole="presentation"><div dojoAttachPoint="editNode,focusNode,eventNode" dojoAttachEvent="onpaste:_changing,oncut:_changing" waiRole="textbox" waiState="multiline-true" contentEditable="true"></div>'
					: '<span id="${id}" class="dijitReset">'+
					'<iframe src="javascript:<html><head><title>${_iframeEditTitle}</title></head><body><script>var _postCreate=window.frameElement?window.frameElement.postCreate:null;if(_postCreate)_postCreate();</script></body></html>"'+
							' dojoAttachPoint="iframe,styleNode,stateNode" dojoAttachEvent="onblur:_onIframeBlur" class="dijitInline dijitInputField"></iframe>')
				+ '<textarea name="${name}" value="${value}" dojoAttachPoint="formValueNode" style="display:none;" autocomplete="off"></textarea>'
				+ ((dojo.isIE || dojo.isSafari || dojo.isFF >= 3) ? '</fieldset>':'</span>')
			: '<textarea id="${id}" name="${name}" value="${value}" dojoAttachPoint="formValueNode,editNode,focusNode,styleNode">'+dojo.isFF+'</textarea>',

	baseClass: "dijitTextArea",

	_setDisabledAttr: function(/*Boolean*/ value){
		this.inherited(arguments);
		this.formValueNode.disabled = this.disabled;
		this._adjustWritable();
	},
	_setReadOnlyAttr: function(/*Boolean*/ value){
		this.readOnly = value;
		this._adjustWritable();
	},
	_adjustWritable: function(){
		// summary: set whether user can write into textbox, based on this.disabled and this.readOnly
		if(dojo.isIE || dojo.isSafari || dojo.isFF >= 3){
			this.editNode.contentEditable = (!this.disabled && !this.readOnly);
		}else if(dojo.isFF){
			this.iframe.contentDocument.designMode = (this.disabled || this.readOnly)? "off" : "on";
		}
	},

	focus: function(){
		// summary: Received focus, needed for the InlineEditBox widget
		if(!this.disabled && !this.readOnly){
			this._changing(); // set initial height
		}
		dijit.focus(this.iframe || this.focusNode);
	},

	_setValueAttr: function(/*String*/ value, /*Boolean, optional*/ priorityChange){
		// summary:
		//		Hook so attr('value', ...) works.
		var editNode = this.editNode;
		if(typeof value == "string"){
			editNode.innerHTML = ""; // wipe out old nodes
			if(value.split){
				var _this=this;
				var isFirst = true;
				dojo.forEach(value.split("\n"), function(line){
					if(isFirst){ isFirst = false; }
					else{
						editNode.appendChild(dojo.doc.createElement("BR")); // preserve line breaks
					}
					if(line){
						editNode.appendChild(dojo.doc.createTextNode(line)); // use text nodes so that imbedded tags can be edited
					}
				});
			}else if(value){
				editNode.appendChild(dojo.doc.createTextNode(value));
			}
			if(!dojo.isIE){
				editNode.appendChild(dojo.doc.createElement("BR")); // so that you see a cursor
			}
		}else{
			// blah<BR>blah --> blah\nblah
			// <P>blah</P><P>blah</P> --> blah\nblah
			// <DIV>blah</DIV><DIV>blah</DIV> --> blah\nblah
			// &amp;&lt;&gt; -->&< >
			value = editNode.innerHTML;
			if(this.iframe){ // strip sizeNode
				value = value.replace(/<div><\/div>\r?\n?$/i,"");
			}
			value = value.replace(/\s*\r?\n|^\s+|\s+$|&nbsp;/g,"").replace(/>\s+</g,"><").replace(/<\/(p|div)>$|^<(p|div)[^>]*>/gi,"").replace(/([^>])<div>/g,"$1\n").replace(/<\/p>\s*<p[^>]*>|<br[^>]*>|<\/div>\s*<div[^>]*>/gi,"\n").replace(/<[^>]*>/g,"").replace(/&amp;/gi,"\&").replace(/&lt;/gi,"<").replace(/&gt;/gi,">");
			if(!dojo.isIE){
				value = value.replace(/\n$/,""); // remove added <br>
			}
		}
		this.value = this.formValueNode.value = value;
		if(this.iframe){
			var sizeNode = dojo.doc.createElement('div');
			editNode.appendChild(sizeNode);
			var newHeight = sizeNode.offsetTop;
			if(editNode.scrollWidth > editNode.clientWidth){ newHeight+=16; } // scrollbar space needed?
			if(this.lastHeight != newHeight){ // cache size so that we don't get a resize event because of a resize event
				if(newHeight == 0){ newHeight = 16; } // height = 0 causes the browser to not set scrollHeight
				dojo.contentBox(this.iframe, {h: newHeight});
				this.lastHeight = newHeight;
			}
			editNode.removeChild(sizeNode);
		}
		dijit.form.Textarea.superclass._setValueAttr.call(this, this.attr('value'), priorityChange);
	},

	_getValueAttr: function(){
		// summary:
		//		Hook so attr('value') works.
		return this.value.replace(/\r/g,"");
	},

	postMixInProperties: function(){
		this.inherited(arguments);
		// don't let the source text be converted to a DOM structure since we just want raw text
		if(this.srcNodeRef && this.srcNodeRef.innerHTML != ""){
			this.value = this.srcNodeRef.innerHTML;
			this.srcNodeRef.innerHTML = "";
		}
		if((!this.value || this.value == "") && this.srcNodeRef && this.srcNodeRef.value){
			this.value = this.srcNodeRef.value;
		}
		if(!this.value){ this.value = ""; }
		this.value = this.value.replace(/\r\n/g,"\n").replace(/&gt;/g,">").replace(/&lt;/g,"<").replace(/&amp;/g,"&");
		if(dojo.isFF == 2){
			// In the case of Firefox < 3 an iframe is used and when the text gets focus,
			// focus is fired from the document object.  There isn't a way to put a
			// waiRole on the document object and as a result screen readers don't
			// announce the role.  As a result screen reader users are lost.
			//
			// An additional problem is that the browser gives the document object a
			// very cryptic accessible name, e.g.
			// wysiwyg://13/http://archive.dojotoolkit.org/nightly/dojotoolkit/dijit/tests/form/test_InlineEditBox.html
			// When focus is fired from the document object, the screen reader speaks
			// the accessible name.  The cyptic accessile name is confusing.
			//
			// A workaround for both of these problems is to give the iframe's
			// document a title, the name of which is similar to a role name, i.e.
			// "edit area".  This will be used as the accessible name which will replace
			// the cryptic name and will also convey the role information to the user.
			// Because it is read directly to the user, the string must be localized.
			// In addition, since a <label> element can not be associated with an iframe, if 
			// this control has a label, insert the label text into the title as well.
			var _nlsResources = dojo.i18n.getLocalization("dijit.form", "Textarea");
			this._iframeEditTitle = _nlsResources.iframeEditTitle;
			this._iframeFocusTitle = _nlsResources.iframeFocusTitle;
			var label=dojo.query('label[for="'+this.id+'"]');
			if(label.length){
				this._iframeEditTitle = label[0].innerHTML + " " + this._iframeEditTitle;
			}
			this.focusNode = this.editNode = dojo.doc.createElement('BODY');
		}
	},

	postCreate: function(){
		var userStyle = "";
		if(dojo.isIE || dojo.isSafari || dojo.isFF >= 3){
			this.domNode.style.overflowY = 'hidden';
		}else if(dojo.isFF){
			var w = this.iframe.contentWindow;
			var title = '';
			try { // #4715: peeking at the title can throw a security exception during iframe setup
				title = this.iframe.contentDocument.title;
			} catch(e) {}
			if(!w || !title){
				this.iframe.postCreate = dojo.hitch(this, this.postCreate);
				return;
			}
			var d = w.document;
			d.getElementsByTagName('HTML')[0].replaceChild(this.editNode, d.getElementsByTagName('BODY')[0]);
			if(!this.isLeftToRight()){
				d.getElementsByTagName('HTML')[0].dir = "rtl";
			}			
			this.iframe.style.overflowY = 'hidden';
			this.eventNode = d;
			// this.connect won't destroy this handler cleanly since its on the iframe's window object
			// resize is a method of window, not document
			w.addEventListener("resize", dojo.hitch(this, this._changed), false); // resize is only on the window object
			dijit.registerWin(w);
			userStyle="margin:0px;padding:0px;border:0px;";
		}else{
			this.focusNode = this.domNode;
		}
		this.style.replace(/(^|;)(line-|font-?)[^;]+/g, function(match){ userStyle += match.replace(/^;/g,"") + ';' });
		dojo.attr(this.focusNode, 'style', userStyle);
		if(this.eventNode){
			this.connect(this.eventNode, "keypress", this._onKeyPress);
			this.connect(this.eventNode, "mousemove", this._changed);
			this.connect(this.eventNode, "focus", this._focusedEventNode);
			this.connect(this.eventNode, "blur", this._blurredEventNode);
		}
		if(this.editNode){
			this.connect(this.editNode, "change", this._changed); // needed for mouse paste events per #3479
		}
		this.inherited('postCreate', arguments);
	},

	// event handlers, you can over-ride these in your own subclasses
	_focusedEventNode: function(e){
		// note: this is needed when we have an iframe
		this._focused = true;
		this._setStateClass();
		this._changed(e);
	},

	_blurredEventNode: function(e){
		// note: this is needed when we have an iframe
		this._focused = false;
		this._setStateClass();
		this._changed(e, true);
	},

	_onIframeBlur: function(){
		// Reset the title back to "edit area".
		this.iframe.contentDocument.title = this._iframeEditTitle;
	},

	_onKeyPress: function(e){
		if(e.charOrCode === dojo.keys.TAB && !e.shiftKey && !e.ctrlKey && !e.altKey && this.iframe){
			// Pressing the tab key in the iframe (with designMode on) will cause the
			// entry of a tab character so we have to trap that here.  Since we don't
			// know the next focusable object we put focus on the iframe and then the
			// user has to press tab again (which then does the expected thing).
			// A problem with that is that the screen reader user hears "edit area"
			// announced twice which causes confusion.  By setting the
			// contentDocument's title to "edit area frame" the confusion should be
			// eliminated.
			this.iframe.contentDocument.title = this._iframeFocusTitle;
			// Place focus on the iframe. A subsequent tab or shift tab will put focus
			// on the correct control.
			// Note: Can't use this.focus() because that results in a call to
			// dijit.focus and if that receives an iframe target it will set focus
			// on the iframe's contentWindow.
			this.iframe.focus();  // this.focus(); won't work
			dojo.stopEvent(e);
		}else if(e.charOrCode == dojo.keys.ENTER){
			e.stopPropagation();
		}else if(this.inherited("_onKeyPress", arguments) && this.iframe){
			// #3752:
			// The key press will not make it past the iframe.
			// If a widget is listening outside of the iframe, (like InlineEditBox)
			// it will not hear anything.
			// Create an equivalent event so everyone else knows what is going on.
			var te = dojo.doc.createEvent("KeyEvents");
			te.initKeyEvent("keypress", true, true, null, e.ctrlKey, e.altKey, e.shiftKey, e.metaKey, e.keyCode, e.charCode);
			this.iframe.dispatchEvent(te);
		}
		this._changing();
	},

	_changing: function(e){
		// summary: event handler for when a change is imminent
		setTimeout(dojo.hitch(this, "_changed", e, false), 1);
	},

	_changed: function(e, priorityChange){
		// summary: event handler for when a change has already happened
		if(this.iframe && this.iframe.contentDocument.designMode != "on" && !this.disabled && !this.readOnly){
			this.iframe.contentDocument.designMode="on"; // in case this failed on init due to being hidden
		}
		this._setValueAttr(null, priorityChange || false);
	}
});

}

if(!dojo._hasResource["dijit.layout.StackContainer"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.StackContainer"] = true;
dojo.provide("dijit.layout.StackContainer");







dojo.declare(
	"dijit.layout.StackContainer",
	dijit.layout._LayoutWidget,
	{
	// summary: 
	//	A container that has multiple children, but shows only
	//	one child at a time
	//
	// description:
	// 	A container for widgets (ContentPanes, for example) That displays
	//	only one Widget at a time.
	//	
	//	Publishes topics [widgetId]-addChild, [widgetId]-removeChild, and [widgetId]-selectChild
	//
	//	Can be base class for container, Wizard, Show, etc.

	// doLayout: Boolean
	//  if true, change the size of my currently displayed child to match my size
	doLayout: true,

	baseClass: "dijitStackContainer",

	_started: false,
/*=====
	// selectedChildWidget: Widget
	//	References the currently selected child widget, if any
	//
	selectedChildWidget: null,
=====*/
	postCreate: function(){
		this.inherited(arguments);
		dijit.setWaiRole(this.containerNode, "tabpanel");
		this.connect(this.domNode, "onkeypress", this._onKeyPress);
	},
	
	startup: function(){
		if(this._started){ return; }

		var children = this.getChildren();

		// Setup each page panel
		dojo.forEach(children, this._setupChild, this);

		// Figure out which child to initially display
		dojo.some(children, function(child){
			if(child.selected){
				this.selectedChildWidget = child;
			}
			return child.selected;
		}, this);

		var selected = this.selectedChildWidget;

		// Default to the first child
		if(!selected && children[0]){
			selected = this.selectedChildWidget = children[0];
			selected.selected = true;
		}
		if(selected){
			this._showChild(selected);
		}

		// Now publish information about myself so any StackControllers can initialize..
		dojo.publish(this.id+"-startup", [{children: children, selected: selected}]);

		this.inherited(arguments);
	},

	_setupChild: function(/*Widget*/ child){
		// Summary: prepare the given child

		this.inherited(arguments);

		child.domNode.style.display = "none";

		// since we are setting the width/height of the child elements, they need
		// to be position:relative, or IE has problems (See bug #2033)
		child.domNode.style.position = "relative";

		// remove the title attribute so it doesn't show up when i hover
		// over a node
		child.domNode.title = "";

		return child; // dijit._Widget
	},

	addChild: function(/*Widget*/ child, /*Integer?*/ insertIndex){
		// summary: Adds a widget to the stack
		 
		this.inherited(arguments);

		if(this._started){
			dojo.publish(this.id+"-addChild", [child, insertIndex]);

			// in case the tab titles have overflowed from one line to two lines
			// (or, if this if first child, from zero lines to one line)
			this.layout();

			// if this is the first child, then select it
			if(!this.selectedChildWidget){
				this.selectChild(child);
			}
		}
	},

	removeChild: function(/*Widget*/ page){
		// summary: Removes the pane from the stack

		this.inherited(arguments);

		// If we are being destroyed than don't run the code below (to select another page), because we are deleting
		// every page one by one
		if(this._beingDestroyed){ return; }

		if(this._started){
			// this will notify any tablists to remove a button; do this first because it may affect sizing
			dojo.publish(this.id+"-removeChild", [page]);

			// in case the tab titles now take up one line instead of two lines
			this.layout();
		}

		if(this.selectedChildWidget === page){
			this.selectedChildWidget = undefined;
			if(this._started){
				var children = this.getChildren();
				if(children.length){
					this.selectChild(children[0]);
				}
			}
		}
	},

	selectChild: function(/*Widget*/ page){
		// summary:
		//	Show the given widget (which must be one of my children)

		page = dijit.byId(page);

		if(this.selectedChildWidget != page){
			// Deselect old page and select new one
			this._transition(page, this.selectedChildWidget);
			this.selectedChildWidget = page;
			dojo.publish(this.id+"-selectChild", [page]);
		}
	},

	_transition: function(/*Widget*/newWidget, /*Widget*/oldWidget){
		if(oldWidget){
			this._hideChild(oldWidget);
		}
		this._showChild(newWidget);

		// Size the new widget, in case this is the first time it's being shown,
		// or I have been resized since the last time it was shown.
		// page must be visible for resizing to work
		if(this.doLayout && newWidget.resize){
			newWidget.resize(this._containerContentBox || this._contentBox);
		}
	},

	_adjacent: function(/*Boolean*/ forward){
		// summary: Gets the next/previous child widget in this container from the current selection
		var children = this.getChildren();
		var index = dojo.indexOf(children, this.selectedChildWidget);
		index += forward ? 1 : children.length - 1;
		return children[ index % children.length ]; // dijit._Widget
	},

	forward: function(){
		// Summary: advance to next page
		this.selectChild(this._adjacent(true));
	},

	back: function(){
		// Summary: go back to previous page
		this.selectChild(this._adjacent(false));
	},

	_onKeyPress: function(e){
		dojo.publish(this.id+"-containerKeyPress", [{ e: e, page: this}]);
	},

	layout: function(){
		if(this.doLayout && this.selectedChildWidget && this.selectedChildWidget.resize){
			this.selectedChildWidget.resize(this._contentBox);
		}
	},

	_showChild: function(/*Widget*/ page){
		var children = this.getChildren();
		page.isFirstChild = (page == children[0]);
		page.isLastChild = (page == children[children.length-1]);
		page.selected = true;

		page.domNode.style.display="";
		if(page._loadCheck){
			page._loadCheck(); // trigger load in ContentPane
		}
		if(page.onShow){
			page.onShow();
		}
	},

	_hideChild: function(/*Widget*/ page){
		page.selected=false;
		page.domNode.style.display="none";
		if(page.onHide){
			page.onHide();
		}
	},

	closeChild: function(/*Widget*/ page){
		// summary:
		//	callback when user clicks the [X] to remove a page
		//	if onClose() returns true then remove and destroy the child
		var remove = page.onClose(this, page);
		if(remove){
			this.removeChild(page);
			// makes sure we can clean up executeScripts in ContentPane onUnLoad
			page.destroyRecursive();
		}
	},

	destroy: function(){
		this._beingDestroyed = true;
		this.inherited(arguments);
	}
});

dojo.declare(
	"dijit.layout.StackController",
	[dijit._Widget, dijit._Templated, dijit._Container],
	{
	// summary:
	//	Set of buttons to select a page in a page list.
	//	Monitors the specified StackContainer, and whenever a page is
	//	added, deleted, or selected, updates itself accordingly.

		templateString: "<span wairole='tablist' dojoAttachEvent='onkeypress' class='dijitStackController'></span>",

		// containerId: String
		//	the id of the page container that I point to
		containerId: "",

		// buttonWidget: String
		//	the name of the button widget to create to correspond to each page
		buttonWidget: "dijit.layout._StackButton",

		postCreate: function(){
			dijit.setWaiRole(this.domNode, "tablist");

			// TODO: change key from object to id, to get more separation from StackContainer
			this.pane2button = {};		// mapping from panes to buttons
			this.pane2handles = {};		// mapping from panes to dojo.connect() handles
			this.pane2menu = {};		// mapping from panes to close menu

			this._subscriptions=[
				dojo.subscribe(this.containerId+"-startup", this, "onStartup"),
				dojo.subscribe(this.containerId+"-addChild", this, "onAddChild"),
				dojo.subscribe(this.containerId+"-removeChild", this, "onRemoveChild"),
				dojo.subscribe(this.containerId+"-selectChild", this, "onSelectChild"),
				dojo.subscribe(this.containerId+"-containerKeyPress", this, "onContainerKeyPress")
			];
		},

		onStartup: function(/*Object*/ info){
			// summary: called after StackContainer has finished initializing
			dojo.forEach(info.children, this.onAddChild, this);
			this.onSelectChild(info.selected);
		},

		destroy: function(){
			for(var pane in this.pane2button){
				this.onRemoveChild(pane);
			}
			dojo.forEach(this._subscriptions, dojo.unsubscribe);
			this.inherited(arguments);
		},

		onAddChild: function(/*Widget*/ page, /*Integer?*/ insertIndex){
			// summary:
			//   Called whenever a page is added to the container.
			//   Create button corresponding to the page.

			// add a node that will be promoted to the button widget
			var refNode = dojo.doc.createElement("span");
			this.domNode.appendChild(refNode);
			// create an instance of the button widget
			var cls = dojo.getObject(this.buttonWidget);
			var button = new cls({label: page.title, closeButton: page.closable}, refNode);
			this.addChild(button, insertIndex);
			this.pane2button[page] = button;
			page.controlButton = button;	// this value might be overwritten if two tabs point to same container

			var handles = [];
			handles.push(dojo.connect(button, "onClick", dojo.hitch(this,"onButtonClick",page)));
			if(page.closable){
				handles.push(dojo.connect(button, "onClickCloseButton", dojo.hitch(this,"onCloseButtonClick",page)));
				// add context menu onto title button
				var _nlsResources = dojo.i18n.getLocalization("dijit", "common");
				var closeMenu = new dijit.Menu({targetNodeIds:[button.id], id:button.id+"_Menu"});
				var mItem = new dijit.MenuItem({label:_nlsResources.itemClose});
				handles.push(dojo.connect(mItem, "onClick", dojo.hitch(this, "onCloseButtonClick", page)));
				closeMenu.addChild(mItem);
				this.pane2menu[page] = closeMenu;
			}
			this.pane2handles[page] = handles;
			if(!this._currentChild){ // put the first child into the tab order
				button.focusNode.setAttribute("tabIndex", "0");
				this._currentChild = page;
			}
			//make sure all tabs have the same length
			if(!this.isLeftToRight() && dojo.isIE && this._rectifyRtlTabList){
				this._rectifyRtlTabList();
			}
		},

		onRemoveChild: function(/*Widget*/ page){
			// summary:
			//   Called whenever a page is removed from the container.
			//   Remove the button corresponding to the page.
			if(this._currentChild === page){ this._currentChild = null; }
			dojo.forEach(this.pane2handles[page], dojo.disconnect);
			delete this.pane2handles[page];
			var menu = this.pane2menu[page];
			if (menu){
				menu.destroyRecursive();
				delete this.pane2menu[page];
			}
			var button = this.pane2button[page];
			if(button){
				// TODO? if current child { reassign }
				button.destroy();
				delete this.pane2button[page];
			}
		},

		onSelectChild: function(/*Widget*/ page){
			// summary:
			//	Called when a page has been selected in the StackContainer, either by me or by another StackController

			if(!page){ return; }

			if(this._currentChild){
				var oldButton=this.pane2button[this._currentChild];
				oldButton.attr('checked', false);
				oldButton.focusNode.setAttribute("tabIndex", "-1");
			}

			var newButton=this.pane2button[page];
			newButton.attr('checked', true);
			this._currentChild = page;
			newButton.focusNode.setAttribute("tabIndex", "0");
			var container = dijit.byId(this.containerId);
			dijit.setWaiState(container.containerNode, "labelledby", newButton.id);
		},

		onButtonClick: function(/*Widget*/ page){
			// summary:
			//   Called whenever one of my child buttons is pressed in an attempt to select a page
			var container = dijit.byId(this.containerId);	// TODO: do this via topics?
			container.selectChild(page); 
		},

		onCloseButtonClick: function(/*Widget*/ page){
			// summary:
			//   Called whenever one of my child buttons [X] is pressed in an attempt to close a page
			var container = dijit.byId(this.containerId);
			container.closeChild(page);
			var b = this.pane2button[this._currentChild];
			if(b){
				dijit.focus(b.focusNode || b.domNode);
			}
		},
		
		// TODO: this is a bit redundant with forward, back api in StackContainer
		adjacent: function(/*Boolean*/ forward){
			if(!this.isLeftToRight() && (!this.tabPosition || /top|bottom/.test(this.tabPosition))){ forward = !forward; }
			// find currently focused button in children array
			var children = this.getChildren();
			var current = dojo.indexOf(children, this.pane2button[this._currentChild]);
			// pick next button to focus on
			var offset = forward ? 1 : children.length - 1;
			return children[ (current + offset) % children.length ]; // dijit._Widget
		},

		onkeypress: function(/*Event*/ e){
			// summary:
			//   Handle keystrokes on the page list, for advancing to next/previous button
			//   and closing the current page if the page is closable.

			if(this.disabled || e.altKey ){ return; }
			var forward = null;
			if(e.ctrlKey || !e._djpage){
				var k = dojo.keys;
				switch(e.charOrCode){
					case k.LEFT_ARROW:
					case k.UP_ARROW:
						if(!e._djpage){ forward = false; }
						break;
					case k.PAGE_UP:
						if(e.ctrlKey){ forward = false; }
						break;
					case k.RIGHT_ARROW:
					case k.DOWN_ARROW:
						if(!e._djpage){ forward = true; }
						break;
					case k.PAGE_DOWN:
						if(e.ctrlKey){ forward = true; }
						break;
					case k.DELETE:
						if(this._currentChild.closable){
							this.onCloseButtonClick(this._currentChild);
						}
						dojo.stopEvent(e);
						break;
					default:
						if(e.ctrlKey){
							if(e.charOrCode === k.TAB){
								this.adjacent(!e.shiftKey).onClick();
								dojo.stopEvent(e);
							}else if(e.charOrCode == "w"){
								if(this._currentChild.closable){
									this.onCloseButtonClick(this._currentChild);
								}
								dojo.stopEvent(e); // avoid browser tab closing.
							}
						}
				}
				// handle page navigation
				if(forward !== null){
					this.adjacent(forward).onClick();
					dojo.stopEvent(e);
				}
			}
		},

		onContainerKeyPress: function(/*Object*/ info){
			info.e._djpage = info.page;
			this.onkeypress(info.e);
		}
});

dojo.declare("dijit.layout._StackButton",
	dijit.form.ToggleButton,
	{
	// summary
	//	Internal widget used by StackContainer.
	//	The button-like or tab-like object you click to select or delete a page
	
	tabIndex: "-1", // StackContainer buttons are not in the tab order by default
	
	postCreate: function(/*Event*/ evt){
		dijit.setWaiRole((this.focusNode || this.domNode), "tab");
		this.inherited(arguments);
	},
	
	onClick: function(/*Event*/ evt){
		// summary: This is for TabContainer where the tabs are <span> rather than button,
		// 	so need to set focus explicitly (on some browsers)
		dijit.focus(this.focusNode);

		// ... now let StackController catch the event and tell me what to do
	},

	onClickCloseButton: function(/*Event*/ evt){
		// summary
		//	StackContainer connects to this function; if your widget contains a close button
		//	then clicking it should call this function.
		evt.stopPropagation();
	}
});

// These arguments can be specified for the children of a StackContainer.
// Since any widget can be specified as a StackContainer child, mix them
// into the base widget class.  (This is a hack, but it's effective.)
dojo.extend(dijit._Widget, {
	// title: String
	//		Title of this widget.  Used by TabContainer to the name the tab, etc.
	title: "",

	// selected: Boolean
	//		Is this child currently selected?
	selected: false,

	// closable: Boolean
	//		True if user can close (destroy) this child, such as (for example) clicking the X on the tab.
	closable: false,	// true if user can close this tab pane

	onClose: function(){
		// summary: Callback if someone tries to close the child, child will be closed if func returns true
		return true;
	}
});

}

if(!dojo._hasResource["dijit.layout.AccordionContainer"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.AccordionContainer"] = true;
dojo.provide("dijit.layout.AccordionContainer");








dojo.declare(
	"dijit.layout.AccordionContainer",
	dijit.layout.StackContainer,
	{
		// summary:
		//		Holds a set of panes where every pane's title is visible, but only one pane's content is visible at a time,
		//		and switching between panes is visualized by sliding the other panes up/down.
		// example:
		// | 	<div dojoType="dijit.layout.AccordionContainer">
		// |		<div dojoType="dijit.layout.AccordionPane" title="pane 1">
		// |			<div dojoType="dijit.layout.ContentPane">...</div>
		// | 	</div>
		// |		<div dojoType="dijit.layout.AccordionPane" title="pane 2">
		// |			<p>This is some text</p>
		// ||		...
		// |	</div>
		//
		// duration: Integer
		//		Amount of time (in ms) it takes to slide panes
		duration: dijit.defaultDuration,

		_verticalSpace: 0,

		baseClass: "dijitAccordionContainer",
		
		postCreate: function(){
			this.domNode.style.overflow = "hidden";
			this.inherited(arguments); 
			dijit.setWaiRole(this.domNode, "tablist");
		},

		startup: function(){
			if(this._started){ return; }
			this.inherited(arguments);	
			if(this.selectedChildWidget){
				var style = this.selectedChildWidget.containerNode.style;
				style.display = "";
				style.overflow = "auto";
				this.selectedChildWidget._setSelectedState(true);
			}
		},
		
		_getTargetHeight: function(/* Node */ node){
			// summary:
			//		For the given node, returns the height that should be
			//		set to achieve our vertical space (subtract any padding
			//		we may have)
			var cs = dojo.getComputedStyle(node);
			return Math.max(this._verticalSpace - dojo._getPadBorderExtents(node, cs).h, 0);
		},
		
		layout: function(){
			// summary: 
			//		Set the height of the open pane based on what room remains

			// get cumulative height of all the title bars, and figure out which pane is open
			var totalCollapsedHeight = 0;
			var openPane = this.selectedChildWidget;
			dojo.forEach(this.getChildren(), function(child){
				totalCollapsedHeight += child.getTitleHeight();
			});
			var mySize = this._contentBox;
			this._verticalSpace = mySize.h - totalCollapsedHeight;
			if(openPane){
				openPane.containerNode.style.height = this._getTargetHeight(openPane.containerNode) + "px";
/***
TODO: this is wrong.  probably you wanted to call resize on the SplitContainer
inside the AccordionPane??
				if(openPane.resize){
					openPane.resize({h: this._verticalSpace});
				}
***/
			}
		},

		_setupChild: function(/*Widget*/ page){
			// Summary: prepare the given child
			return page;
		},

		_transition: function(/*Widget?*/newWidget, /*Widget?*/oldWidget){
//TODO: should be able to replace this with calls to slideIn/slideOut
			if(this._inTransition){ return; }
			this._inTransition = true;
			var animations = [];
			var paneHeight = this._verticalSpace;
			if(newWidget){
				newWidget.setSelected(true);
				var newContents = newWidget.containerNode;
				newContents.style.display = "";
				paneHeight = this._getTargetHeight(newWidget.containerNode)
				animations.push(dojo.animateProperty({
					node: newContents,
					duration: this.duration,
					properties: {
						height: { start: 1, end: paneHeight }
					},
					onEnd: function(){
						newContents.style.overflow = "auto";
					}
				}));
			}
			if(oldWidget){
				oldWidget.setSelected(false);
				var oldContents = oldWidget.containerNode;
				oldContents.style.overflow = "hidden";
				paneHeight = this._getTargetHeight(oldWidget.containerNode);
				animations.push(dojo.animateProperty({
					node: oldContents,
					duration: this.duration,
					properties: {
						height: { start: paneHeight, end: "1" }
					},
					onEnd: function(){
						oldContents.style.display = "none";
					}
				}));
			}

			this._inTransition = false;

			dojo.fx.combine(animations).play();
		},

		// note: we are treating the container as controller here
		_onKeyPress: function(/*Event*/ e){
			if(this.disabled || e.altKey || !(e._dijitWidget || e.ctrlKey)){ return; }
			var k = dojo.keys;
			var fromTitle = e._dijitWidget;
			switch(e.charOrCode){
				case k.LEFT_ARROW:
				case k.UP_ARROW:
					if (fromTitle){
						this._adjacent(false)._onTitleClick();
						dojo.stopEvent(e);
					}
					break;
				case k.PAGE_UP:
					if (e.ctrlKey){
						this._adjacent(false)._onTitleClick();
						dojo.stopEvent(e);
					}
					break;
				case k.RIGHT_ARROW:
				case k.DOWN_ARROW:
					if (fromTitle){
						this._adjacent(true)._onTitleClick();
						dojo.stopEvent(e);
					}
					break;
				case k.PAGE_DOWN:
					if (e.ctrlKey){
						this._adjacent(true)._onTitleClick();
						dojo.stopEvent(e);
					}
					break;
				default:
					if(e.ctrlKey && e.charOrCode === k.TAB){
						this._adjacent(e._dijitWidget, !e.shiftKey)._onTitleClick();
						dojo.stopEvent(e);
					}
				
			}
		}
	}
);

dojo.declare("dijit.layout.AccordionPane",
	[dijit.layout.ContentPane, dijit._Templated, dijit._Contained],
	{
	// summary:
	//		AccordionPane is a ContentPane with a title that may contain another widget.
	//		Nested layout widgets, such as SplitContainer, are not supported at this time.
	// example: 
	// | see dijit.layout.AccordionContainer

	templateString:"<div waiRole=\"presentation\"\n\t><div dojoAttachPoint='titleNode,focusNode' dojoAttachEvent='ondijitclick:_onTitleClick,onkeypress:_onTitleKeyPress,onfocus:_handleFocus,onblur:_handleFocus,onmouseenter:_onTitleEnter,onmouseleave:_onTitleLeave'\n\t\tclass='dijitAccordionTitle' wairole=\"tab\" waiState=\"expanded-false\"\n\t\t><span class='dijitInline dijitAccordionArrow' waiRole=\"presentation\"></span\n\t\t><span class='arrowTextUp' waiRole=\"presentation\">+</span\n\t\t><span class='arrowTextDown' waiRole=\"presentation\">-</span\n\t\t><span waiRole=\"presentation\" dojoAttachPoint='titleTextNode' class='dijitAccordionText'></span></div\n\t><div waiRole=\"presentation\"><div dojoAttachPoint='containerNode' style='overflow: hidden; height: 1px; display: none'\n\t\tclass='dijitAccordionBody' wairole=\"tabpanel\"\n\t></div></div>\n</div>\n",
	attributeMap: dojo.mixin(dojo.clone(dijit.layout.ContentPane.prototype.attributeMap), {
		title: {node: "titleTextNode", type: "innerHTML" }
	}),

	baseClass: "dijitAccordionPane",
	
	postCreate: function(){
		this.inherited(arguments)
		dojo.setSelectable(this.titleNode, false);
		this.setSelected(this.selected);
		dojo.attr(this.titleTextNode, "id", this.domNode.id+"_title");
		dijit.setWaiState(this.focusNode, "labelledby", dojo.attr(this.titleTextNode, "id"));
	},

	getTitleHeight: function(){
		// summary: returns the height of the title dom node
		return dojo.marginBox(this.titleNode).h;	// Integer
	},

	_onTitleClick: function(){
		// summary: callback when someone clicks my title
		var parent = this.getParent();
		if(!parent._inTransition){
			parent.selectChild(this);
			dijit.focus(this.focusNode);
		}
	},

	_onTitleEnter: function(){
		// summary: callback when someone hovers over my title
		dojo.addClass(this.focusNode, "dijitAccordionTitle-hover");
	},

	_onTitleLeave: function(){
		// summary: callback when someone stops hovering over my title
		dojo.removeClass(this.focusNode, "dijitAccordionTitle-hover");
	},

	_onTitleKeyPress: function(/*Event*/ evt){
		evt._dijitWidget = this;
		return this.getParent()._onKeyPress(evt);
	},

	_setSelectedState: function(/*Boolean*/ isSelected){
		this.selected = isSelected;
		dojo[(isSelected ? "addClass" : "removeClass")](this.titleNode,"dijitAccordionTitle-selected");
		dijit.setWaiState(this.focusNode, "expanded", isSelected);
		this.focusNode.setAttribute("tabIndex", isSelected ? "0" : "-1");
	},

	_handleFocus: function(/*Event*/e){
		// summary: handle the blur and focus state of this widget
		dojo[(e.type=="focus" ? "addClass" : "removeClass")](this.focusNode,"dijitAccordionFocused");		
	},

	setSelected: function(/*Boolean*/ isSelected){
		// summary: change the selected state on this pane
		this._setSelectedState(isSelected);
		if(isSelected){
			this.onSelected();
			this._loadCheck(); // if href specified, trigger load
		}
	},

	onSelected: function(){
		// summary: called when this pane is selected
	}
});

}

if(!dojo._hasResource["dijit.layout.BorderContainer"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.BorderContainer"] = true;
dojo.provide("dijit.layout.BorderContainer");




dojo.declare(
	"dijit.layout.BorderContainer",
//	[dijit._Widget, dijit._Container, dijit._Contained],
	dijit.layout._LayoutWidget,
{
	// summary:
	//	Provides layout in 5 regions, a center and borders along its 4 sides.
	//
	// description:
	//	A BorderContainer is a box with a specified size (like style="width: 500px; height: 500px;"),
	//	that contains a child widget marked region="center" and optionally children widgets marked
	//	region equal to "top", "bottom", "leading", "trailing", "left" or "right".
	//	Children along the edges will be laid out according to width or height dimensions.  The remaining
	//	space is designated for the center region.
	//	The outer size must be specified on the BorderContainer node.  Width must be specified for the sides
	//  and height for the top and bottom, respectively.  No dimensions should be specified on the center;
	//	it will fill the remaining space.  Regions named "leading" and "trailing" may be used just like
	//	"left" and "right" except that they will be reversed in right-to-left environments.
	//  Optional splitters may be specified on the edge widgets only to make them resizable by the user.
	//  NOTE: Splitters must not be more than 50 pixels in width.
	//
	// example:
	// |	<style>
	// |		html, body { height: 100%; width: 100%; }
	// |	</style>
	// |	<div dojoType="BorderContainer" design="sidebar" style="width: 100%; height: 100%">
	// |		<div dojoType="ContentPane" region="top">header text</div>
	// |		<div dojoType="ContentPane" region="right" style="width: 200px;">table of contents</div>
	// |		<div dojoType="ContentPane" region="center">client area</div>
	// |	</div>

	// design: String
	//  choose which design is used for the layout: "headline" (default) where the top and bottom extend
	//  the full width of the container, or "sidebar" where the left and right sides extend from top to bottom.
	design: "headline",

	// gutters: Boolean
	//	Give each pane a border and margin.
	//	Margin determined by domNode.paddingLeft.
	//	When false, only resizable panes have a gutter (i.e. draggable splitter) for resizing.
	gutters: true,

	// liveSplitters: Boolean
	//  specifies whether splitters resize as you drag (true) or only upon mouseup (false)
	liveSplitters: true,

	// persist: Boolean
	//		Save splitter positions in a cookie.
	persist: false,	// Boolean

	baseClass: "dijitBorderContainer",

	// _splitterClass: String
	// 		Optional hook to override the default Splitter widget used by BorderContainer
	_splitterClass: "dijit.layout._Splitter",

	postMixInProperties: function(){
		// change class name to indicate that BorderContainer is being used purely for
		// layout (like LayoutContainer) rather than for pretty formatting.
		if(!this.gutters){
			this.baseClass += "NoGutter";
		}
		this.inherited(arguments);
	},

	postCreate: function(){
		this.inherited(arguments);

		this._splitters = {};
		this._splitterThickness = {};
	},

	startup: function(){
		if(this._started){ return; }
		dojo.forEach(this.getChildren(), this._setupChild, this);
		this.inherited(arguments);
	},

	_setupChild: function(/*Widget*/child){
		var region = child.region;
		if(region){
			this.inherited(arguments);

			dojo.addClass(child.domNode, this.baseClass+"Pane");

			var ltr = this.isLeftToRight();
			if(region == "leading"){ region = ltr ? "left" : "right"; }
			if(region == "trailing"){ region = ltr ? "right" : "left"; }

			//FIXME: redundant?
			this["_"+region] = child.domNode;
			this["_"+region+"Widget"] = child;

			// Create draggable splitter for resizing pane,
			// or alternately if splitter=false but BorderContainer.gutters=true then
			// insert dummy div just for spacing
			if((child.splitter || this.gutters) && !this._splitters[region]){
				var _Splitter = dojo.getObject(child.splitter ? this._splitterClass : "dijit.layout._Gutter");
				var flip = {left:'right', right:'left', top:'bottom', bottom:'top', leading:'trailing', trailing:'leading'};
				var splitter = new _Splitter({
					container: this,
					child: child,
					region: region,
					oppNode: dojo.query('[region=' + flip[child.region] + ']', this.domNode)[0],
					live: this.liveSplitters
				});
				splitter.isSplitter = true;
				this._splitters[region] = splitter.domNode;
				dojo.place(this._splitters[region], child.domNode, "after");

				// Splitters arent added as Contained children, so we need to call startup explicitly
				splitter.startup();
			}
			child.region = region;
		}
	},

	_computeSplitterThickness: function(region){
		this._splitterThickness[region] = this._splitterThickness[region] ||
			dojo.marginBox(this._splitters[region])[(/top|bottom/.test(region) ? 'h' : 'w')];
	},

	layout: function(){
		for(var region in this._splitters){ this._computeSplitterThickness(region); }
		this._layoutChildren();
	},

	addChild: function(/*Widget*/ child, /*Integer?*/ insertIndex){
		this.inherited(arguments);
		if(this._started){
			this._layoutChildren(); //OPT
		}
	},

	removeChild: function(/*Widget*/ child){
		var region = child.region;
		var splitter = this._splitters[region];
		if(splitter){
			dijit.byNode(splitter).destroy();
			delete this._splitters[region];
			delete this._splitterThickness[region];
		}
		this.inherited(arguments);
		delete this["_"+region];
		delete this["_" +region+"Widget"];
		if(this._started){
			this._layoutChildren(child.region);
		}
		dojo.removeClass(child.domNode, this.baseClass+"Pane");
	},

	getChildren: function(){
		return dojo.filter(this.inherited(arguments), function(widget){
			return !widget.isSplitter;
		});
	},

	getSplitter: function(/*String*/region){
		// summary: returns the widget responsible for rendering the splitter associated with region 
		var splitter = this._splitters[region];
		return splitter ? dijit.byNode(splitter) : null;
	},

	resize: function(newSize, currentSize){
		// resetting potential padding to 0px to provide support for 100% width/height + padding
		// TODO: this hack doesn't respect the box model and is a temporary fix
		if (!this.cs || !this.pe){
			var node = this.domNode;
			this.cs = dojo.getComputedStyle(node);
			this.pe = dojo._getPadExtents(node, this.cs);
			this.pe.r = dojo._toPixelValue(node, this.cs.paddingRight);
			this.pe.b = dojo._toPixelValue(node, this.cs.paddingBottom);

			dojo.style(node, "padding", "0px");
		}

		this.inherited(arguments);
	},

	_layoutChildren: function(/*String?*/changedRegion){
		var sidebarLayout = (this.design == "sidebar");
		var topHeight = 0, bottomHeight = 0, leftWidth = 0, rightWidth = 0;
		var topStyle = {}, leftStyle = {}, rightStyle = {}, bottomStyle = {},
			centerStyle = (this._center && this._center.style) || {};

		var changedSide = /left|right/.test(changedRegion);

		var layoutSides = !changedRegion || (!changedSide && !sidebarLayout);
		var layoutTopBottom = !changedRegion || (changedSide && sidebarLayout);

		// Ask browser for width/height of side panes.
		// Would be nice to cache this but height can change according to width
		// (because words wrap around).  I don't think width will ever change though
		// (except when the user drags a splitter). 
		if(this._top){
			topStyle = layoutTopBottom && this._top.style;
			topHeight = dojo.marginBox(this._top).h;
		}
		if(this._left){
			leftStyle = layoutSides && this._left.style;
			leftWidth = dojo.marginBox(this._left).w;
		}
		if(this._right){
			rightStyle = layoutSides && this._right.style;
			rightWidth = dojo.marginBox(this._right).w;
		}
		if(this._bottom){
			bottomStyle = layoutTopBottom && this._bottom.style;
			bottomHeight = dojo.marginBox(this._bottom).h;
		}

		var splitters = this._splitters;
		var topSplitter = splitters.top, bottomSplitter = splitters.bottom,
			leftSplitter = splitters.left, rightSplitter = splitters.right;
		var splitterThickness = this._splitterThickness;
		var topSplitterThickness = splitterThickness.top || 0,
			leftSplitterThickness = splitterThickness.left || 0,
			rightSplitterThickness = splitterThickness.right || 0,
			bottomSplitterThickness = splitterThickness.bottom || 0;

		// Check for race condition where CSS hasn't finished loading, so
		// the splitter width == the viewport width (#5824)
		if(leftSplitterThickness > 50 || rightSplitterThickness > 50){
			setTimeout(dojo.hitch(this, function(){
				// Results are invalid.  Clear them out.
				this._splitterThickness = {};

				for(var region in this._splitters){
					this._computeSplitterThickness(region);
				}
				this._layoutChildren();
			}), 50);
			return false;
		}

		var pe = this.pe;

		var splitterBounds = {
			left: (sidebarLayout ? leftWidth + leftSplitterThickness: 0) + pe.l + "px",
			right: (sidebarLayout ? rightWidth + rightSplitterThickness: 0) + pe.r + "px"
		};

		if(topSplitter){
			dojo.mixin(topSplitter.style, splitterBounds);
			topSplitter.style.top = topHeight + pe.t + "px";
		}

		if(bottomSplitter){
			dojo.mixin(bottomSplitter.style, splitterBounds);
			bottomSplitter.style.bottom = bottomHeight + pe.b + "px";
		}

		splitterBounds = {
			top: (sidebarLayout ? 0 : topHeight + topSplitterThickness) + pe.t + "px",
			bottom: (sidebarLayout ? 0 : bottomHeight + bottomSplitterThickness) + pe.b + "px"
		};

		if(leftSplitter){
			dojo.mixin(leftSplitter.style, splitterBounds);
			leftSplitter.style.left = leftWidth + pe.l + "px";
		}

		if(rightSplitter){
			dojo.mixin(rightSplitter.style, splitterBounds);
			rightSplitter.style.right = rightWidth + pe.r +  "px";
		}

		dojo.mixin(centerStyle, {
			top: pe.t + topHeight + topSplitterThickness + "px",
			left: pe.l + leftWidth + leftSplitterThickness + "px",
			right: pe.r + rightWidth + rightSplitterThickness + "px",
			bottom: pe.b + bottomHeight + bottomSplitterThickness + "px"
		});

		var bounds = {
			top: sidebarLayout ? pe.t + "px" : centerStyle.top,
			bottom: sidebarLayout ? pe.b + "px" : centerStyle.bottom
		};
		dojo.mixin(leftStyle, bounds);
		dojo.mixin(rightStyle, bounds);
		leftStyle.left = pe.l + "px"; rightStyle.right = pe.r + "px"; topStyle.top = pe.t + "px"; bottomStyle.bottom = pe.b + "px";
		if(sidebarLayout){
			topStyle.left = bottomStyle.left = leftWidth + leftSplitterThickness + pe.l + "px";
			topStyle.right = bottomStyle.right = rightWidth + rightSplitterThickness + pe.r + "px";
		}else{
			topStyle.left = bottomStyle.left = pe.l + "px";
			topStyle.right = bottomStyle.right = pe.r + "px";
		}

		// More calculations about sizes of panes
		var containerHeight = this._borderBox.h - pe.t - pe.b,
			middleHeight = containerHeight - ( topHeight + topSplitterThickness + bottomHeight + bottomSplitterThickness),
			sidebarHeight = sidebarLayout ? containerHeight : middleHeight;

		var containerWidth = this._borderBox.w - pe.l - pe.r,
			middleWidth = containerWidth - (leftWidth  + leftSplitterThickness + rightWidth + rightSplitterThickness),
			sidebarWidth = sidebarLayout ? middleWidth : containerWidth;

		// New margin-box size of each pane
		var dim = {
			top:	{ w: sidebarWidth, h: topHeight },
			bottom: { w: sidebarWidth, h: bottomHeight },
			left:	{ w: leftWidth, h: sidebarHeight },
			right:	{ w: rightWidth, h: sidebarHeight },
			center:	{ h: middleHeight, w: middleWidth }
		};

		// Nodes in IE don't respond to t/l/b/r, and TEXTAREA doesn't respond in any browser
		var janky = dojo.isIE || dojo.some(this.getChildren(), function(child){
			return child.domNode.tagName == "TEXTAREA" || child.domNode.tagName == "INPUT";
		});
		if(janky){
			// Set the size of the children the old fashioned way, by setting
			// CSS width and height

			var resizeWidget = function(widget, changes, result){
				if(widget){
					(widget.resize ? widget.resize(changes, result) : dojo.marginBox(widget.domNode, changes));
				}
			};

			if(leftSplitter){ leftSplitter.style.height = sidebarHeight; }
			if(rightSplitter){ rightSplitter.style.height = sidebarHeight; }
			resizeWidget(this._leftWidget, {h: sidebarHeight}, dim.left);
			resizeWidget(this._rightWidget, {h: sidebarHeight}, dim.right);

			if(topSplitter){ topSplitter.style.width = sidebarWidth; }
			if(bottomSplitter){ bottomSplitter.style.width = sidebarWidth; }
			resizeWidget(this._topWidget, {w: sidebarWidth}, dim.top);
			resizeWidget(this._bottomWidget, {w: sidebarWidth}, dim.bottom);

			resizeWidget(this._centerWidget, dim.center);
		}else{
			// We've already sized the children by setting style.top/bottom/left/right...
			// Now just need to call resize() on those children telling them their new size,
			// so they can re-layout themselves

			// Calculate which panes need a notification
			var resizeList = {};
			if(changedRegion){
				resizeList[changedRegion] = resizeList.center = true;
				if(/top|bottom/.test(changedRegion) && this.design != "sidebar"){
					resizeList.left = resizeList.right = true;
				}else if(/left|right/.test(changedRegion) && this.design == "sidebar"){
					resizeList.top = resizeList.bottom = true;
				}
			}

			dojo.forEach(this.getChildren(), function(child){
				if(child.resize && (!changedRegion || child.region in resizeList)){
					child.resize(null, dim[child.region]);
				}
			}, this);
		}
	},

	destroy: function(){
		for(region in this._splitters){
			var splitter = this._splitters[region];
			dijit.byNode(splitter).destroy();
			dojo._destroyElement(splitter);
		}
		delete this._splitters;
		delete this._splitterThickness;
		this.inherited(arguments);
	}
});

// This argument can be specified for the children of a BorderContainer.
// Since any widget can be specified as a LayoutContainer child, mix it
// into the base widget class.  (This is a hack, but it's effective.)
dojo.extend(dijit._Widget, {
	// region: String
	//		"top", "bottom", "leading", "trailing", "left", "right", "center".
	//		See the BorderContainer description for details on this parameter.
	region: '',

	// splitter: Boolean
	splitter: false,

	// minSize: Number
	minSize: 0,

	// maxSize: Number
	maxSize: Infinity
});



dojo.declare("dijit.layout._Splitter", [ dijit._Widget, dijit._Templated ],
{
/*=====
	container: null,
	child: null,
	region: null,
=====*/

	// live: Boolean
	//		If true, the child's size changes and the child widget is redrawn as you drag the splitter;
	//		otherwise, the size doesn't change until you drop the splitter (by mouse-up)
	live: true,

	// summary: A draggable spacer between two items in a BorderContainer
	templateString: '<div class="dijitSplitter" dojoAttachEvent="onkeypress:_onKeyPress,onmousedown:_startDrag" tabIndex="0" waiRole="separator"><div class="dijitSplitterThumb"></div></div>',

	postCreate: function(){
		this.inherited(arguments);
		this.horizontal = /top|bottom/.test(this.region);
		dojo.addClass(this.domNode, "dijitSplitter" + (this.horizontal ? "H" : "V"));
//		dojo.addClass(this.child.domNode, "dijitSplitterPane");
//		dojo.setSelectable(this.domNode, false); //TODO is this necessary?

		this._factor = /top|left/.test(this.region) ? 1 : -1;
		this._minSize = this.child.minSize;

		// trigger constraints calculations
		this.child.domNode._recalc = true;
		this.connect(this.container, "resize", function(){ this.child.domNode._recalc = true; });

		this._cookieName = this.container.id + "_" + this.region;
		if(this.container.persist){
			// restore old size
			var persistSize = dojo.cookie(this._cookieName);
			if(persistSize){
				this.child.domNode.style[this.horizontal ? "height" : "width"] = persistSize;
			}
		}
	},

	_computeMaxSize: function(){
		var dim = this.horizontal ? 'h' : 'w',
			thickness = this.container._splitterThickness[this.region];
		var available = dojo.contentBox(this.container.domNode)[dim] -
			(this.oppNode ? dojo.marginBox(this.oppNode)[dim] : 0) -
			20 - thickness * 2;
		this._maxSize = Math.min(this.child.maxSize, available);
	},

	_startDrag: function(e){
		if(this.child.domNode._recalc){
			this._computeMaxSize();
			this.child.domNode._recalc = false;
		}

		if(!this.cover){
			this.cover = dojo.doc.createElement('div');
			dojo.addClass(this.cover, "dijitSplitterCover");
			dojo.place(this.cover, this.child.domNode, "after");
		}else{
			this.cover.style.zIndex = 1;
		}

		// Safeguard in case the stop event was missed.  Shouldn't be necessary if we always get the mouse up.
		if(this.fake){ dojo._destroyElement(this.fake); }
		if(!(this._resize = this.live)){ //TODO: disable live for IE6?
			// create fake splitter to display at old position while we drag
			(this.fake = this.domNode.cloneNode(true)).removeAttribute("id");
			dojo.addClass(this.domNode, "dijitSplitterShadow");
			dojo.place(this.fake, this.domNode, "after");
		}
		dojo.addClass(this.domNode, "dijitSplitterActive");

		//Performance: load data info local vars for onmousevent function closure
		var factor = this._factor,
			max = this._maxSize,
			min = this._minSize || 20,
			isHorizontal = this.horizontal,
			axis = isHorizontal ? "pageY" : "pageX",
			pageStart = e[axis],
			splitterStyle = this.domNode.style,
			dim = isHorizontal ? 'h' : 'w',
			childStart = dojo.marginBox(this.child.domNode)[dim],
			region = this.region,
			splitterStart = parseInt(this.domNode.style[region], 10),
			resize = this._resize,
			mb = {},
			childNode = this.child.domNode,
			layoutFunc = dojo.hitch(this.container, this.container._layoutChildren),
			de = dojo.doc.body;

		this._handlers = (this._handlers || []).concat([
			dojo.connect(de, "onmousemove", this._drag = function(e, forceResize){
				var delta = e[axis] - pageStart,
					childSize = factor * delta + childStart,
					boundChildSize = Math.max(Math.min(childSize, max), min);

				if(resize || forceResize){
					mb[dim] = boundChildSize;
					// TODO: inefficient; we set the marginBox here and then immediately layoutFunc() needs to query it
					dojo.marginBox(childNode, mb);
					layoutFunc(region);
				}
				splitterStyle[region] = factor * delta + splitterStart + (boundChildSize - childSize) + "px";
			}),
			dojo.connect(de, "onmouseup", this, "_stopDrag")
		]);
		dojo.stopEvent(e);
	},

	_stopDrag: function(e){
		try{
			if(this.cover){ this.cover.style.zIndex = -1; }
			if(this.fake){ dojo._destroyElement(this.fake); }
			dojo.removeClass(this.domNode, "dijitSplitterActive");
			dojo.removeClass(this.domNode, "dijitSplitterShadow");
			this._drag(e); //TODO: redundant with onmousemove?
			this._drag(e, true);
		}finally{
			this._cleanupHandlers();
			if(this.oppNode){ this.oppNode._recalc = true; }
			delete this._drag;
		}

		if(this.container.persist){
			dojo.cookie(this._cookieName, this.child.domNode.style[this.horizontal ? "height" : "width"], {expires:365});
		}
	},

	_cleanupHandlers: function(){
		dojo.forEach(this._handlers, dojo.disconnect);
		delete this._handlers;
	},

	_onKeyPress: function(/*Event*/ e){
		if(this.child.domNode._recalc){
			this._computeMaxSize();
			this.child.domNode._recalc = false;
		}

		// should we apply typematic to this?
		this._resize = true;
		var horizontal = this.horizontal;
		var tick = 1;
		var dk = dojo.keys;
		switch(e.charOrCode){
			case horizontal ? dk.UP_ARROW : dk.LEFT_ARROW:
				tick *= -1;
//				break;
			case horizontal ? dk.DOWN_ARROW : dk.RIGHT_ARROW:
				break;
			default:
//				this.inherited(arguments);
				return;
		}
		var childSize = dojo.marginBox(this.child.domNode)[ horizontal ? 'h' : 'w' ] + this._factor * tick;
		var mb = {};
		mb[ this.horizontal ? "h" : "w"] = Math.max(Math.min(childSize, this._maxSize), this._minSize);
		dojo.marginBox(this.child.domNode, mb);
		if(this.oppNode){ this.oppNode._recalc = true; }
		this.container._layoutChildren(this.region);
		dojo.stopEvent(e);
	},

	destroy: function(){
		this._cleanupHandlers();
		delete this.child;
		delete this.container;
		delete this.fake;
		this.inherited(arguments);
	}
});

dojo.declare("dijit.layout._Gutter", [dijit._Widget, dijit._Templated ],
{
	// summary:
	// 		Just a spacer div to separate side pane from center pane.
	//		Basically a trick to lookup the gutter/splitter width from the theme.

	templateString: '<div class="dijitGutter" waiRole="presentation"></div>',

	postCreate: function(){
		this.horizontal = /top|bottom/.test(this.region);
		dojo.addClass(this.domNode, "dijitGutter" + (this.horizontal ? "H" : "V"));
	}
});

}

if(!dojo._hasResource["dijit.layout.LayoutContainer"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.LayoutContainer"] = true;
dojo.provide("dijit.layout.LayoutContainer");



dojo.declare("dijit.layout.LayoutContainer",
	dijit.layout._LayoutWidget,
	{
	// summary:
	//	Provides Delphi-style panel layout semantics.
	//
	// description:
	//	A LayoutContainer is a box with a specified size (like style="width: 500px; height: 500px;"),
	//	that contains children widgets marked with "layoutAlign" of "left", "right", "bottom", "top", and "client".
	//	It takes it's children marked as left/top/bottom/right, and lays them out along the edges of the box,
	//	and then it takes the child marked "client" and puts it into the remaining space in the middle.
	//
	//  Left/right positioning is similar to CSS's "float: left" and "float: right",
	//	and top/bottom positioning would be similar to "float: top" and "float: bottom", if there were such
	//	CSS.
	//
	//	Note that there can only be one client element, but there can be multiple left, right, top,
	//	or bottom elements.
	//
	// example:
	// |	<style>
	// |		html, body{ height: 100%; width: 100%; }
	// |	</style>
	// |	<div dojoType="dijit.layout.LayoutContainer" style="width: 100%; height: 100%">
	// |		<div dojoType="dijit.layout.ContentPane" layoutAlign="top">header text</div>
	// |		<div dojoType="dijit.layout.ContentPane" layoutAlign="left" style="width: 200px;">table of contents</div>
	// |		<div dojoType="dijit.layout.ContentPane" layoutAlign="client">client area</div>
	// |	</div>
	//
	//	Lays out each child in the natural order the children occur in.
	//	Basically each child is laid out into the "remaining space", where "remaining space" is initially
	//	the content area of this widget, but is reduced to a smaller rectangle each time a child is added.

	baseClass: "dijitLayoutContainer",

	constructor: function(){
		dojo.deprecated("dijit.layout.LayoutContainer is deprecated", "use BorderContainer instead", 2.0);
	},

	layout: function(){
		dijit.layout.layoutChildren(this.domNode, this._contentBox, this.getChildren());
	},

	addChild: function(/*Widget*/ child, /*Integer?*/ insertIndex){
		this.inherited(arguments);
		if(this._started){
			dijit.layout.layoutChildren(this.domNode, this._contentBox, this.getChildren());
		}
	},

	removeChild: function(/*Widget*/ widget){
		this.inherited(arguments);
		if(this._started){
			dijit.layout.layoutChildren(this.domNode, this._contentBox, this.getChildren());
		}
	}
});

// This argument can be specified for the children of a LayoutContainer.
// Since any widget can be specified as a LayoutContainer child, mix it
// into the base widget class.  (This is a hack, but it's effective.)
dojo.extend(dijit._Widget, {
	// layoutAlign: String
	//		"none", "left", "right", "bottom", "top", and "client".
	//		See the LayoutContainer description for details on this parameter.
	layoutAlign: 'none'
});

}

if(!dojo._hasResource["dijit.layout.LinkPane"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.LinkPane"] = true;
dojo.provide("dijit.layout.LinkPane");




dojo.declare("dijit.layout.LinkPane",
	[dijit.layout.ContentPane, dijit._Templated],
	{
	// summary: 
	//	A ContentPane that loads data remotely
	// description:
	//	LinkPane is just a ContentPane that loads data remotely (via the href attribute),
	//	and has markup similar to an anchor.  The anchor's body (the words between `<a>` and `</a>`)
	//	become the title of the widget (used for TabContainer, AccordionContainer, etc.)
	// example:
	//	<a href="foo.html">my title</a>

	// I'm using a template because the user may specify the input as
	// <a href="foo.html">title</a>, in which case we need to get rid of the
	// <a> because we don't want a link.
	templateString: '<div class="dijitLinkPane"></div>',

	 buildRendering: function(){
		this.inherited(arguments);

		// make getDescendants() work
		this.containerNode = this.domNode;
	},

	postCreate: function(){
		// If user has specified node contents, they become the title
		// (the link must be plain text)
		if(this.srcNodeRef){
			this.title += this.srcNodeRef.innerHTML;
		}
		this.inherited("postCreate",arguments);
	}
});

}

if(!dojo._hasResource["dijit.layout.SplitContainer"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.SplitContainer"] = true;
dojo.provide("dijit.layout.SplitContainer");

//
// FIXME: make it prettier
// FIXME: active dragging upwards doesn't always shift other bars (direction calculation is wrong in this case)
//




dojo.declare("dijit.layout.SplitContainer",
	dijit.layout._LayoutWidget,
	{
	// summary: 
	//	A Container widget with sizing handles in-between each child
	// description:
	//		Contains multiple children widgets, all of which are displayed side by side
	//		(either horizontally or vertically); there's a bar between each of the children,
	//		and you can adjust the relative size of each child by dragging the bars.
	//
	//		You must specify a size (width and height) for the SplitContainer.

	constructor: function(){
		dojo.deprecated("dijit.layout.SplitContainer is deprecated", "use BorderContainer with splitter instead", 2.0);
	},

	// activeSizing: Boolean
	//		If true, the children's size changes as you drag the bar;
	//		otherwise, the sizes don't change until you drop the bar (by mouse-up)
	activeSizing: false,

	// sizerWidth: Integer
	//		Size in pixels of the bar between each child
	sizerWidth: 7, // FIXME: this should be a CSS attribute (at 7 because css wants it to be 7 until we fix to css)

	// orientation: String
	//		either 'horizontal' or vertical; indicates whether the children are
	//		arranged side-by-side or up/down.
	orientation: 'horizontal',

	// persist: Boolean
	//		Save splitter positions in a cookie
	persist: true,

	baseClass: "dijitSplitContainer",

	postMixInProperties: function(){
		this.inherited("postMixInProperties",arguments);
		this.isHorizontal = (this.orientation == 'horizontal');
	},

	postCreate: function(){
		this.inherited(arguments);
		this.sizers = [];

		// overflow has to be explicitly hidden for splitContainers using gekko (trac #1435)
		// to keep other combined css classes from inadvertantly making the overflow visible
		if(dojo.isMozilla){
			this.domNode.style.overflow = '-moz-scrollbars-none'; // hidden doesn't work
		}

		// create the fake dragger
		if(typeof this.sizerWidth == "object"){
			try{ //FIXME: do this without a try/catch
				this.sizerWidth = parseInt(this.sizerWidth.toString());
			}catch(e){ this.sizerWidth = 7; }
		}
		var sizer = this.virtualSizer = dojo.doc.createElement('div');
		sizer.style.position = 'relative';

		// #1681: work around the dreaded 'quirky percentages in IE' layout bug
		// If the splitcontainer's dimensions are specified in percentages, it
		// will be resized when the virtualsizer is displayed in _showSizingLine
		// (typically expanding its bounds unnecessarily). This happens because
		// we use position: relative for .dijitSplitContainer.
		// The workaround: instead of changing the display style attribute,
		// switch to changing the zIndex (bring to front/move to back)

		sizer.style.zIndex = 10;
		sizer.className = this.isHorizontal ? 'dijitSplitContainerVirtualSizerH' : 'dijitSplitContainerVirtualSizerV';
		this.domNode.appendChild(sizer);
		dojo.setSelectable(sizer, false);
	},

	destroy: function(){
		delete this.virtualSizer;
		dojo.forEach(this._ownconnects, dojo.disconnect);
		this.inherited(arguments);
	},
	startup: function(){
		if(this._started){ return; }

		dojo.forEach(this.getChildren(), function(child, i, children){
			// attach the children and create the draggers
			this._setupChild(child);

			if(i < children.length-1){
				this._addSizer();
			}
		}, this);

		if(this.persist){
			this._restoreState();
		}

		this.inherited(arguments); 
	},

	_setupChild: function(/*Widget*/ child){
		this.inherited(arguments);
		child.domNode.style.position = "absolute";
		dojo.addClass(child.domNode, "dijitSplitPane");
	},

	_addSizer: function(){
		var i = this.sizers.length;

		// TODO: use a template for this!!!
		var sizer = this.sizers[i] = dojo.doc.createElement('div');
		this.domNode.appendChild(sizer);

		sizer.className = this.isHorizontal ? 'dijitSplitContainerSizerH' : 'dijitSplitContainerSizerV';

		// add the thumb div
		var thumb = dojo.doc.createElement('div');
		thumb.className = 'thumb';
		sizer.appendChild(thumb);

		// FIXME: are you serious? why aren't we using mover start/stop combo?
		var self = this;
		var handler = (function(){ var sizer_i = i; return function(e){ self.beginSizing(e, sizer_i); } })();
		this.connect(sizer, "onmousedown", handler);
		
		dojo.setSelectable(sizer, false);
	},

	removeChild: function(widget){
		// summary: Remove sizer, but only if widget is really our child and
		// we have at least one sizer to throw away
		if(this.sizers.length){
			var i=dojo.indexOf(this.getChildren(), widget)
			if(i != -1){
				if(i==this.sizers.length){
					i--;
				}
				dojo._destroyElement(this.sizers[i]);
				this.sizers.splice(i,1);
			}
		}

		// Remove widget and repaint
		this.inherited(arguments); 
		if(this._started){
			this.layout();
		}
	},

	addChild: function(/*Widget*/ child, /*Integer?*/ insertIndex){
		// summary: Add a child widget to the container
		// child: a widget to add
		// insertIndex: postion in the "stack" to add the child widget
		
		this.inherited(arguments); 

		if(this._started){
			// Do the stuff that startup() does for each widget
			var children = this.getChildren();
			if(children.length > 1){
				this._addSizer();
			}

			// and then reposition (ie, shrink) every pane to make room for the new guy
			this.layout();
		}
	},

	layout: function(){
		// summary:
		//		Do layout of panels

		// base class defines this._contentBox on initial creation and also
		// on resize
		this.paneWidth = this._contentBox.w;
		this.paneHeight = this._contentBox.h;

		var children = this.getChildren();
		if(!children.length){ return; }

		//
		// calculate space
		//

		var space = this.isHorizontal ? this.paneWidth : this.paneHeight;
		if(children.length > 1){
			space -= this.sizerWidth * (children.length - 1);
		}

		//
		// calculate total of SizeShare values
		//
		var outOf = 0;
		dojo.forEach(children, function(child){
			outOf += child.sizeShare;
		});

		//
		// work out actual pixels per sizeshare unit
		//
		var pixPerUnit = space / outOf;

		//
		// set the SizeActual member of each pane
		//
		var totalSize = 0;
		dojo.forEach(children.slice(0, children.length - 1), function(child){
			var size = Math.round(pixPerUnit * child.sizeShare);
			child.sizeActual = size;
			totalSize += size;
		});

		children[children.length-1].sizeActual = space - totalSize;

		//
		// make sure the sizes are ok
		//
		this._checkSizes();

		//
		// now loop, positioning each pane and letting children resize themselves
		//

		var pos = 0;
		var size = children[0].sizeActual;
		this._movePanel(children[0], pos, size);
		children[0].position = pos;
		pos += size;

		// if we don't have any sizers, our layout method hasn't been called yet
		// so bail until we are called..TODO: REVISIT: need to change the startup
		// algorithm to guaranteed the ordering of calls to layout method
		if(!this.sizers){
			return;
		}

		dojo.some(children.slice(1), function(child, i){
			// error-checking
			if(!this.sizers[i]){
				return true;
			}
			// first we position the sizing handle before this pane
			this._moveSlider(this.sizers[i], pos, this.sizerWidth);
			this.sizers[i].position = pos;
			pos += this.sizerWidth;

			size = child.sizeActual;
			this._movePanel(child, pos, size);
			child.position = pos;
			pos += size;
		}, this);
	},

	_movePanel: function(panel, pos, size){
		if(this.isHorizontal){
			panel.domNode.style.left = pos + 'px';	// TODO: resize() takes l and t parameters too, don't need to set manually
			panel.domNode.style.top = 0;
			var box = {w: size, h: this.paneHeight};
			if(panel.resize){
				panel.resize(box);
			}else{
				dojo.marginBox(panel.domNode, box);
			}
		}else{
			panel.domNode.style.left = 0;	// TODO: resize() takes l and t parameters too, don't need to set manually
			panel.domNode.style.top = pos + 'px';
			var box = {w: this.paneWidth, h: size};
			if(panel.resize){
				panel.resize(box);
			}else{
				dojo.marginBox(panel.domNode, box);
			}
		}
	},

	_moveSlider: function(slider, pos, size){
		if(this.isHorizontal){
			slider.style.left = pos + 'px';
			slider.style.top = 0;
			dojo.marginBox(slider, { w: size, h: this.paneHeight });
		}else{
			slider.style.left = 0;
			slider.style.top = pos + 'px';
			dojo.marginBox(slider, { w: this.paneWidth, h: size });
		}
	},

	_growPane: function(growth, pane){
		if(growth > 0){
			if(pane.sizeActual > pane.sizeMin){
				if((pane.sizeActual - pane.sizeMin) > growth){

					// stick all the growth in this pane
					pane.sizeActual = pane.sizeActual - growth;
					growth = 0;
				}else{
					// put as much growth in here as we can
					growth -= pane.sizeActual - pane.sizeMin;
					pane.sizeActual = pane.sizeMin;
				}
			}
		}
		return growth;
	},

	_checkSizes: function(){

		var totalMinSize = 0;
		var totalSize = 0;
		var children = this.getChildren();

		dojo.forEach(children, function(child){
			totalSize += child.sizeActual;
			totalMinSize += child.sizeMin;
		});

		// only make adjustments if we have enough space for all the minimums

		if(totalMinSize <= totalSize){

			var growth = 0;

			dojo.forEach(children, function(child){
				if(child.sizeActual < child.sizeMin){
					growth += child.sizeMin - child.sizeActual;
					child.sizeActual = child.sizeMin;
				}
			});

			if(growth > 0){
				var list = this.isDraggingLeft ? children.reverse() : children;
				dojo.forEach(list, function(child){
					growth = this._growPane(growth, child);
				}, this);
			}
		}else{
			dojo.forEach(children, function(child){
				child.sizeActual = Math.round(totalSize * (child.sizeMin / totalMinSize));
			});
		}
	},

	beginSizing: function(e, i){
		var children = this.getChildren();
		this.paneBefore = children[i];
		this.paneAfter = children[i+1];

		this.isSizing = true;
		this.sizingSplitter = this.sizers[i];

		if(!this.cover){
			this.cover = dojo.doc.createElement('div');
			this.domNode.appendChild(this.cover);
			var s = this.cover.style;
			s.position = 'absolute';
			s.zIndex = 1;
			s.top = 0;
			s.left = 0;
			s.width = "100%";
			s.height = "100%";
		}else{
			this.cover.style.zIndex = 1;
		}
		this.sizingSplitter.style.zIndex = 2;

		// TODO: REVISIT - we want MARGIN_BOX and core hasn't exposed that yet (but can't we use it anyway if we pay attention? we do elsewhere.)
		this.originPos = dojo.coords(children[0].domNode, true);
		if(this.isHorizontal){
			var client = e.layerX || e.offsetX || 0;
			var screen = e.pageX;
			this.originPos = this.originPos.x;
		}else{
			var client = e.layerY || e.offsetY || 0;
			var screen = e.pageY;
			this.originPos = this.originPos.y;
		}
		this.startPoint = this.lastPoint = screen;
		this.screenToClientOffset = screen - client;
		this.dragOffset = this.lastPoint - this.paneBefore.sizeActual - this.originPos - this.paneBefore.position;

		if(!this.activeSizing){
			this._showSizingLine();
		}

		//					
		// attach mouse events
		//
		this._ownconnects = [];
		this._ownconnects.push(dojo.connect(dojo.doc.documentElement, "onmousemove", this, "changeSizing"));
		this._ownconnects.push(dojo.connect(dojo.doc.documentElement, "onmouseup", this, "endSizing"));

		dojo.stopEvent(e);
	},

	changeSizing: function(e){
		if(!this.isSizing){ return; }
		this.lastPoint = this.isHorizontal ? e.pageX : e.pageY;
		this.movePoint();
		if(this.activeSizing){
			this._updateSize();
		}else{
			this._moveSizingLine();
		}
		dojo.stopEvent(e);
	},

	endSizing: function(e){
		if(!this.isSizing){ return; }
		if(this.cover){
			this.cover.style.zIndex = -1;
		}
		if(!this.activeSizing){
			this._hideSizingLine();
		}

		this._updateSize();

		this.isSizing = false;

		if(this.persist){
			this._saveState(this);
		}

		dojo.forEach(this._ownconnects,dojo.disconnect); 
	},

	movePoint: function(){

		// make sure lastPoint is a legal point to drag to
		var p = this.lastPoint - this.screenToClientOffset;

		var a = p - this.dragOffset;
		a = this.legaliseSplitPoint(a);
		p = a + this.dragOffset;

		this.lastPoint = p + this.screenToClientOffset;
	},

	legaliseSplitPoint: function(a){

		a += this.sizingSplitter.position;

		this.isDraggingLeft = !!(a > 0);

		if(!this.activeSizing){
			var min = this.paneBefore.position + this.paneBefore.sizeMin;
			if(a < min){
				a = min;
			}

			var max = this.paneAfter.position + (this.paneAfter.sizeActual - (this.sizerWidth + this.paneAfter.sizeMin));
			if(a > max){
				a = max;
			}
		}

		a -= this.sizingSplitter.position;

		this._checkSizes();

		return a;
	},

	_updateSize: function(){
	//FIXME: sometimes this.lastPoint is NaN
		var pos = this.lastPoint - this.dragOffset - this.originPos;

		var start_region = this.paneBefore.position;
		var end_region   = this.paneAfter.position + this.paneAfter.sizeActual;

		this.paneBefore.sizeActual = pos - start_region;
		this.paneAfter.position	= pos + this.sizerWidth;
		this.paneAfter.sizeActual  = end_region - this.paneAfter.position;

		dojo.forEach(this.getChildren(), function(child){
			child.sizeShare = child.sizeActual;
		});

		if(this._started){
			this.layout();
		}
	},

	_showSizingLine: function(){

		this._moveSizingLine();

		dojo.marginBox(this.virtualSizer,
			this.isHorizontal ? { w: this.sizerWidth, h: this.paneHeight } : { w: this.paneWidth, h: this.sizerWidth });

		this.virtualSizer.style.display = 'block';
	},

	_hideSizingLine: function(){
		this.virtualSizer.style.display = 'none';
	},

	_moveSizingLine: function(){
		var pos = (this.lastPoint - this.startPoint) + this.sizingSplitter.position;
		dojo.style(this.virtualSizer,(this.isHorizontal ? "left" : "top"),pos+"px");
		// this.virtualSizer.style[ this.isHorizontal ? "left" : "top" ] = pos + 'px'; // FIXME: remove this line if the previous is better
	},

	_getCookieName: function(i){
		return this.id + "_" + i;
	},

	_restoreState: function(){
		dojo.forEach(this.getChildren(), function(child, i){
			var cookieName = this._getCookieName(i);
			var cookieValue = dojo.cookie(cookieName);
			if(cookieValue){
				var pos = parseInt(cookieValue);
				if(typeof pos == "number"){
					child.sizeShare = pos;
				}
			}
		}, this);
	},

	_saveState: function(){
		if(!this.persist){
			return;
		}
		dojo.forEach(this.getChildren(), function(child, i){
			dojo.cookie(this._getCookieName(i), child.sizeShare, {expires:365});
		}, this);
	}
});

// These arguments can be specified for the children of a SplitContainer.
// Since any widget can be specified as a SplitContainer child, mix them
// into the base widget class.  (This is a hack, but it's effective.)
dojo.extend(dijit._Widget, {
	// sizeMin: Integer
	//	Minimum size (width or height) of a child of a SplitContainer.
	//	The value is relative to other children's sizeShare properties.
	sizeMin: 10,

	// sizeShare: Integer
	//	Size (width or height) of a child of a SplitContainer.
	//	The value is relative to other children's sizeShare properties.
	//	For example, if there are two children and each has sizeShare=10, then
	//	each takes up 50% of the available space.
	sizeShare: 10
});

}

if(!dojo._hasResource["dijit.layout.TabContainer"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.layout.TabContainer"] = true;
dojo.provide("dijit.layout.TabContainer");





dojo.declare("dijit.layout.TabContainer",
	[dijit.layout.StackContainer, dijit._Templated],
	{	
	// summary: 
	//	A Container with Title Tabs, each one pointing at a pane in the container.
	// description:
	//	A TabContainer is a container that has multiple panes, but shows only
	//	one pane at a time.  There are a set of tabs corresponding to each pane,
	//	where each tab has the title (aka title) of the pane, and optionally a close button.
	//
	//	Publishes topics [widgetId]-addChild, [widgetId]-removeChild, and [widgetId]-selectChild
	//	(where [widgetId] is the id of the TabContainer itself.

	// tabPosition: String
	//   Defines where tabs go relative to tab content.
	//   "top", "bottom", "left-h", "right-h"
	tabPosition: "top",

	baseClass: "dijitTabContainer",
	
	// tabStrip: bool
	//   Defines whether the tablist gets an extra class for layouting
	tabStrip: false,

	templateString: null,	// override setting in StackContainer
	templateString:"<div class=\"dijitTabContainer\">\n\t<div dojoAttachPoint=\"tablistNode\"></div>\n\t<div dojoAttachPoint=\"tablistSpacer\" class=\"dijitTabSpacer ${baseClass}-spacer\"></div>\n\t<div class=\"dijitTabPaneWrapper ${baseClass}-container\" dojoAttachPoint=\"containerNode\"></div>\n</div>\n",

	// _controllerWidget: String
	//		An optional parameter to overrider the default TabContainer controller used.
	_controllerWidget: "dijit.layout.TabController",

	postMixInProperties: function(){
		// set class name according to tab position, ex: dijiTabContainerTop
		this.baseClass += this.tabPosition.charAt(0).toUpperCase() + this.tabPosition.substr(1).replace(/-.*/, "");
		this.inherited(arguments);
	},

	postCreate: function(){
		this.inherited(arguments);

		// create the tab list that will have a tab (a.k.a. tab button) for each tab panel
		var TabController = dojo.getObject(this._controllerWidget);
		this.tablist = new TabController({
			id: this.id + "_tablist",
			tabPosition: this.tabPosition,
			doLayout: this.doLayout,
			containerId: this.id,
			"class": this.baseClass + "-tabs" + (this.doLayout ? "" : " dijitTabNoLayout")
		}, this.tablistNode);
		
		// add Class for tabstrip
		if (this.tabStrip){	dojo.addClass(this.tablist.domNode, this.baseClass+"Strip"); }			
	},

	_setupChild: function(/* Widget */tab){
		dojo.addClass(tab.domNode, "dijitTabPane");
		this.inherited(arguments);
		return tab; // Widget
	},

	startup: function(){
		if(this._started){ return; }

		// wire up the tablist and its tabs
		this.tablist.startup();
		this.inherited(arguments);
	},

	layout: function(){
		// Summary: Configure the content pane to take up all the space except for where the tabs are
		if(!this.doLayout){ return; }

		// position and size the titles and the container node
		var titleAlign = this.tabPosition.replace(/-h/,"");
		var children = [
			{ domNode: this.tablist.domNode, layoutAlign: titleAlign },
			{ domNode: this.tablistSpacer, layoutAlign: titleAlign },
			{ domNode: this.containerNode, layoutAlign: "client" }
		];
		dijit.layout.layoutChildren(this.domNode, this._contentBox, children);

		// Compute size to make each of my children.
		// children[2] is the margin-box size of this.containerNode, set by layoutChildren() call above
		this._containerContentBox = dijit.layout.marginBox2contentBox(this.containerNode, children[2]);

		if(this.selectedChildWidget){
			this._showChild(this.selectedChildWidget);
			if(this.doLayout && this.selectedChildWidget.resize){
				this.selectedChildWidget.resize(this._containerContentBox);
			}
		}
	},

	destroy: function(){
		if(this.tablist){
			this.tablist.destroy();
		}
		this.inherited(arguments);
	}
});

//TODO: make private?
dojo.declare("dijit.layout.TabController",
	dijit.layout.StackController,
	{
	// summary:
	// 	Set of tabs (the things with titles and a close button, that you click to show a tab panel).
	// description:
	//	Lets the user select the currently shown pane in a TabContainer or StackContainer.
	//	TabController also monitors the TabContainer, and whenever a pane is
	//	added or deleted updates itself accordingly.

	templateString: "<div wairole='tablist' dojoAttachEvent='onkeypress:onkeypress'></div>",

	// tabPosition: String
	//   Defines where tabs go relative to the content.
	//   "top", "bottom", "left-h", "right-h"
	tabPosition: "top",

	// doLayout: Boolean
	doLayout: true,

	// buttonWidget: String
	//	The name of the tab widget to create to correspond to each page
	buttonWidget: "dijit.layout._TabButton",

	_rectifyRtlTabList: function(){
		//summary: Rectify the width of all tabs in rtl, otherwise the tab widths are different in IE
		if(0 >= this.tabPosition.indexOf('-h')){ return; }
		if(!this.pane2button){ return; }

		var maxWidth = 0;
		for(var pane in this.pane2button){
			var ow = this.pane2button[pane].innerDiv.scrollWidth;
			maxWidth = Math.max(maxWidth, ow);
		}
		//unify the length of all the tabs
		for(pane in this.pane2button){
			this.pane2button[pane].innerDiv.style.width = maxWidth + 'px';
		}	
	}
});

dojo.declare("dijit.layout._TabButton",
	dijit.layout._StackButton,
	{
	// summary:
	//	A tab (the thing you click to select a pane).
	// description:
	//	Contains the title of the pane, and optionally a close-button to destroy the pane.
	//	This is an internal widget and should not be instantiated directly.

	baseClass: "dijitTab",

	templateString:"<div waiRole=\"presentation\" dojoAttachEvent='onclick:onClick,onmouseenter:_onMouse,onmouseleave:_onMouse'>\n    <div waiRole=\"presentation\" class='dijitTabInnerDiv' dojoAttachPoint='innerDiv'>\n        <div waiRole=\"presentation\" class='dijitTabContent' dojoAttachPoint='tabContent'>\n\t        <span dojoAttachPoint='containerNode,focusNode' class='tabLabel'>${!label}</span><img class =\"dijitTabButtonSpacer\" src=\"${_blankGif}\" />\n\t        <div class=\"dijitInline closeNode\" dojoAttachPoint='closeNode' dojoAttachEvent='onclick:onClickCloseButton'>\n\t        \t<img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint='closeButtonNode' class='closeImage' dojoAttachEvent='onmouseenter:_onMouse, onmouseleave:_onMouse' stateModifier='CloseButton' waiRole=\"presentation\"/>\n\t            <span dojoAttachPoint='closeText' class='closeText'>x</span>\n\t        </div>\n        </div>\n    </div>\n</div>\n",

	_scroll: false, // don't scroll the whole tab container into view when the button is focused

	postCreate: function(){
		if(this.closeButton){
			dojo.addClass(this.innerDiv, "dijitClosable");
			var _nlsResources = dojo.i18n.getLocalization("dijit", "common");
			if(this.closeNode){
				dojo.attr(this.closeNode,"title", _nlsResources.itemClose);
				// IE needs title set directly on image
				dojo.attr(this.closeButtonNode,"title", _nlsResources.itemClose);
			}
		}else{
			this.closeNode.style.display="none";		
		}
		this.inherited(arguments); 
		dojo.setSelectable(this.containerNode, false);
	}
});

}

if(!dojo._hasResource["dijit.dijit-all"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.dijit-all"] = true;
console.warn("dijit-all may include much more code than your application actually requires. We strongly recommend that you investigate a custom build or the web build tool");
dojo.provide("dijit.dijit-all");

/*=====
dijit["dijit-all"] = { 
	// summary: A rollup that includes every dijit. You probably don't need this.
};
=====*/

































 //deprecated

 //deprecated




}


dojo.i18n._preloadLocalizations("dijit.nls.dijit-all", ["he","nl","tr","no","ko","el","en","en-gb","ROOT","zh-cn","hu","es","fi-fi","pt-br","ca","fi","he-il","xx","ru","it","fr","cs","de-de","fr-fr","it-it","es-es","ja","sk","da","sl","pl","de","sv","pt","pt-pt","nl-nl","zh-tw","ko-kr","ar","en-us","zh","th","ja-jp"]);
