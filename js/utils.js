
var openSeadragonViewer = null;

function initViewerNavigation() {
	$("#totalpagesnr").text($("#imageselector > option").length);
	/* we cannot use jquery to update selector, because we registered a plain js eventlistener */
	document.getElementById("imageselector").dispatchEvent(new Event('change'));
	$(".toolbar").css("display","flex");
	$("#prevpagebtn").on('click',this,function() {
		if (!$(this).hasClass("disabled")) {
			document.getElementById("imageselector").selectedIndex = $("#imageselector").prop("selectedIndex") - 1;
			/* we cannot use jquery to update selector, because we registered a plain js eventlistener */
			document.getElementById("imageselector").dispatchEvent(new Event('change'));
		}
	})
	
	$("#nextpagebtn").on('click',this,function() {
		
		if (!$(this).hasClass("disabled")) {
			document.getElementById("imageselector").selectedIndex = $("#imageselector").prop("selectedIndex") + 1;
			/* we cannot use jquery to update selector, because we registered a plain js eventlistener */
			document.getElementById("imageselector").dispatchEvent(new Event('change'));
			}
	})
}

function updatePageSelector(val) {
	$("#imageselector").val(val);
	$("#currentpagenr").text($("#imageselector").prop("selectedIndex") + 1);
}


function updateViewerNavigation() {
	$("#currentpagenr").text($("#imageselector").prop("selectedIndex") + 1);
	
	var numberofpages = $("#imageselector > option").length;
	var selectedIndex = $("#imageselector").prop("selectedIndex");
	if (numberofpages > 1 && selectedIndex == 0) {
		$("#prevpagebtn").addClass("disabled");
		$("#nextpagebtn").removeClass("disabled");
	} else if (numberofpages > 1 && selectedIndex + 1 == numberofpages) {
		$("#nextpagebtn").addClass("disabled");
		$("#prevpagebtn").removeClass("disabled");
	} else if (numberofpages < 2) {
		$("#prevpagebtn").addClass("disabled");
		$("#nextpagebtn").addClass("disabled");
	} else {
		$("#prevpagebtn").removeClass("disabled");
		$("#nextpagebtn").removeClass("disabled");
	}
	 
}



function showInfo(stuff) {
	document.getElementById("debug").innerHTML = "<p>" + stuff + "</p>";
}

function enableControls() {
	$("#elementselector").attr("disabled",false);
	$("#imageselector").attr("disabled",false);
}

function updateTableAndSelectors(imagerefs) {
	
	$.each(imagerefs,function(key,v){
		$("#elementselector").append("<option value="+key+">"+ key +"</option>");
		$("#settingselementselector").append("<option value="+key+">"+ key +"</option>");
		//$("#projectSettingsTable tbody").append("<tr><td>"+key+"</td><td>"+v.attributeName+"</td><td>"+v.imageServerUrl+"</td><td>"+v.localFolder+"</td></tr>");
		$("#refelementinput").val(key);
		$("#refattributeinput").val(v.attributeName);
		$("#imageserverinput").val(v.imageServerUrl);
		$("#localimagefolderinput").val(v.localFolder);
	});
	
}


function showPageInfo(index, facsCount) {

	document.getElementById("pageinfo").innerHTML = "<p>" + index + " / " + facsCount + "</p>";
}

function createViewerOrLoadImage(imageServerUrl, imageName) {
	
	if (openSeadragonViewer == null) {
	document.getElementById("openseadragonContainer").innerHTML = '';
	openSeadragonViewer = OpenSeadragon({
		id: "openseadragonContainer",
		tileSources: imageServerUrl + imageName.replace(".jpg", "/info.json"),
		showFullPageControl: false,
		prefixUrl:'vendor/openseadragon-flat-toolbar-icons/images/',
		/* toolbar:"toolbarDiv",
		   zoomInButton:   "i-zoom-in",
    	   zoomOutButton:  "i-zoom-out",
    	   homeButton:     "i-zoom-reset", */
	});
} else {
	openSeadragonViewer.open(imageServerUrl + imageName.replace(".jpg", "/info.json"));
}

	$(".openseadragon-container").height($("#openseadragonContainer").height());
	
}

