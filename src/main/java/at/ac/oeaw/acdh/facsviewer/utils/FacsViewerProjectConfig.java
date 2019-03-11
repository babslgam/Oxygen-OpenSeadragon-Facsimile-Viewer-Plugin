package at.ac.oeaw.acdh.facsviewer.utils;

import java.io.File;
import java.util.ArrayList;

import ro.sync.exml.workspace.api.PluginWorkspaceProvider;

public class FacsViewerProjectConfig {

	String projectName;
	String localImagePath;
	String imageServerUrl;
	String facsElementName;
	String facsAttributeName;
	
	public FacsViewerProjectConfig(String... optionsArray) {
		this.projectName = optionsArray[0];
		this.localImagePath = optionsArray[1];
		this.imageServerUrl = optionsArray[2];
		this.facsElementName = optionsArray[3];
		this.facsAttributeName = optionsArray[4];
	}

	public FacsViewerProjectConfig() {
		// TODO Auto-generated constructor stub
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLocalImagePath() {
		return localImagePath;
	}

	public void setLocalImagePath(String localImagePath) {
		this.localImagePath = localImagePath;
	}
	
	public String getImageServerUrl() {
		return imageServerUrl;
	}

	public void setImageServerUrl(String imageServerUrl) {
		this.imageServerUrl = imageServerUrl;
	}
	
	public String getFacsElementName() {
		return facsElementName;
	}

	public void setFacsElementName(String facsElementName) {
		this.facsElementName = facsElementName;
	}
	
	public String getFacsAttributeName() {
		return facsAttributeName;
	}

	public void setFacsAttributeName(String facsAttributeName) {
		this.facsAttributeName = facsAttributeName;
	}
	
	public String browseLocalImagePath() {
		String result;
		final File chosenDir = PluginWorkspaceProvider.getPluginWorkspace().chooseDirectory();
		if (chosenDir == null) {
			result = "canceled";
		} else {
			result = chosenDir.getAbsolutePath();
		}
		return result;
	}
	
	public void storeConfig(ArrayList <FacsViewerProjectConfig> configs) {
		String[] projectsoptions = new String[configs.size() * 5];
		for (int i = 0; i < configs.size(); i++) {
			if (this.getProjectName().equals(configs.get(i).getProjectName())){
				configs.set(i, this);
			}
			projectsoptions[i] = configs.get(i).getProjectName();
			projectsoptions[i+1] = configs.get(i).getLocalImagePath();
			projectsoptions[i+2] = configs.get(i).getImageServerUrl();
			projectsoptions[i+3] = configs.get(i).getFacsElementName();
			projectsoptions[i+4] = configs.get(i).getFacsAttributeName();
			
		}
		PluginWorkspaceProvider.getPluginWorkspace().getOptionsStorage().setStringArrayOption("facsviewer.projects.settings", projectsoptions);
	}
	

}
