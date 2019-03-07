package at.ac.oeaw.acdh.facsviewer.preferencepage.configtabledata;

public class FacsViewerProjectConfig {

	String projectName;
	String localImagePath;
	String imageServerUrl;
	String facsElementName;
	String facsAttributeName;
	
	public FacsViewerProjectConfig(String[] optionsArray) {
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
	

}
