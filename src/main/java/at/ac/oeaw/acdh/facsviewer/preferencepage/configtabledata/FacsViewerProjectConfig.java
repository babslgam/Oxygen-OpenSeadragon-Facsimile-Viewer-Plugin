package at.ac.oeaw.acdh.facsviewer.preferencepage.configtabledata;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;

import org.w3c.dom.Element;

import at.ac.oeaw.acdh.facsviewer.utils.FacsIndexElement;
import ro.sync.exml.workspace.api.editor.page.WSEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.WSTextEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.xml.WSXMLTextEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.xml.WSXMLTextNodeRange;
import ro.sync.exml.workspace.api.editor.page.text.xml.XPathException;

public class FacsViewerProjectConfig {

	String projectName;
	String localImagePath;
	String imageServerUrl;
	String facsElementName;
	String facsAttributeName;
	
	public FacsViewerProjectConfig(final String... optionsArray) {
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
	
	public ArrayList<FacsIndexElement> createFacsIndex(WSEditorPage editorPage) throws XPathException, BadLocationException {
		
		ArrayList<FacsIndexElement> facsIndexModel = new ArrayList<FacsIndexElement>();
		
		if (editorPage instanceof WSXMLTextEditorPage) {
			String facsXPath = "//"+ this.getFacsElementName();
			Object[] facsElements = ((WSXMLTextEditorPage) editorPage).evaluateXPath(facsXPath);
			WSXMLTextNodeRange[] facsElementsRanges = ((WSXMLTextEditorPage) editorPage).findElementsByXPath(facsXPath);
			for (int i = 0;i < facsElements.length;i++)  {
				FacsIndexElement facsIndexElement = new FacsIndexElement();
				facsIndexElement.setImageName(((Element) facsElements[i]).getAttribute(this.getFacsAttributeName()));
				int facsIndexElementOffsetStart = ((WSTextEditorPage) editorPage).getOffsetOfLineStart(facsElementsRanges[i].getStartLine()) + facsElementsRanges[i].getStartColumn() - 1;
				facsIndexElement.setOffsetStart(facsIndexElementOffsetStart);
				facsIndexModel.add(facsIndexElement);
			}
		 }
		return facsIndexModel;
	}
	

}
