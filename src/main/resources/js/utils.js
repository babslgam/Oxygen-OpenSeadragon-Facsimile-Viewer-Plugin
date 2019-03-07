$(document).ready(function(){
	
	
	$(document).on('change',"#facsSelector", function() {
		facsViewerGUIPluginInstance.loadViewer($(this).val());
	});
});
var facsViewerGUIPlugin = function (currentProjectConfig) {

	var facsIndexJSON = [];

	this.setFacsIndex = function (facsindex) {
		for (var i = 0; i < facsindex.size(); i++) {
			var facsobj = {
				"imagename": "",
				"offsetstart": 0
			}
			facsobj.imagename = facsindex.get(i).getImageName();
			facsobj.offsetstart = facsindex.get(i).getOffsetStart();
			facsIndexJSON.push(facsobj);
		}

	}

	this.getFacsIndex = function () {

		return facsIndexJSON;
	}

}


facsViewerGUIPlugin.prototype.changePage = function() {
	
}

facsViewerGUIPlugin.prototype.selectProject = function (projectConfig) {
	if (projectConfig != null) {


	//	$("#debuginfo").html(projectConfig.getProjectName());
		$("#nosettings").hide();
	} else {
		$("#nosettings").show();
		//	$("#debuginfo").html("dasds");
	}
}

facsViewerGUIPlugin.prototype.generateDropDown = function () {
	var fcsIndex = this.getFacsIndex();
	document.getElementById("facsSelector").options.length = 0;
	for (var i = 0; i < fcsIndex.length; i++) {
		$("#facsSelector").append("<option value='" + fcsIndex[i].imagename + "'>" + fcsIndex[i].imagename + "</option>");
	}
	$("#facsSelector").val(fcsIndex[0].imagename).change();
	
}

facsViewerGUIPlugin.prototype.selectImageByCursorPosition = function (currentCarretOffset) {
	/* clone facsindex to not revert referenced array */
	var fcsIndex = this.getFacsIndex().slice();
	if (currentCarretOffset <= fcsIndex[0].offsetstart) {

		$("#facsSelector").val(fcsIndex[0].imagename).change();
	} else {
		var imagetoselect = fcsIndex.reverse().find(function (obj) {
			return obj.offsetstart <= currentCarretOffset;
		}).imagename;
		$("#facsSelector").val(imagetoselect).change();
	}
}

facsViewerGUIPlugin.prototype.createViewer = function (imageName) {
	var viewer = null;
	
	document.getElementById("openseadragonContainer").innerHTML = '';
	if (currentProjectConfig.getImageServerUrl() !== '') {

		viewer = OpenSeadragon({
			id: "openseadragonContainer",
			tileSources: currentProjectConfig.getImageServerUrl() + imageName.replace(".jpg", "/info.json")
		});
	} else if (document.getElementById("localfolder").value !== '') {

		viewer = OpenSeadragon({
			id: "openseadragonContainer",
			tileSources: {
				type: "image",
				url: document.getElementById("localfolder").value + imageName
			}
		});
	} else {
		viewer.destroy();
	}
	return viewer;

}

facsViewerGUIPlugin.prototype.loadViewer = function (imageName) {

	try {
		if (currentProjectConfig.getImageServerUrl() === '' && currentProjectConfig.getImageServerUrl() ===
			'') {
			document.getElementById("openseadragonContainer").innerHTML = '<b>Please specify Url!</b>';
		} else {
			var viewer = this.createViewer(imageName);
			return viewer;
		}
	} catch (error) {
		document.getElementById("openseadragonContainer").innerHTML = error;
	}

	// viewer.destroy();
}

facsViewerGUIPlugin.prototype.showErrorMessage = function () {
	var message = "you haven't defined settings for this project.";
	document.getElementById("main").innerHTML = "<p>" + message + "</p>";
}

facsViewerGUIPlugin.prototype.showDebugInfo = function (debugmessage) {
	$("#debuginfo").html(debugmessage);
}