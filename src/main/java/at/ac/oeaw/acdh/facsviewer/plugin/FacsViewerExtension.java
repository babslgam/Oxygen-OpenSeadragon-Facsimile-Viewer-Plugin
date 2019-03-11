package at.ac.oeaw.acdh.facsviewer.plugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.net.URL;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;

import org.w3c.dom.Element;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import javafx.application.Platform;

import at.ac.oeaw.acdh.facsviewer.plugin.view.FacsViewerBrowserPanel;
import at.ac.oeaw.acdh.facsviewer.utils.FacsIndexElement;
import ro.sync.ecss.extensions.api.AuthorCaretEvent;
import ro.sync.ecss.extensions.api.AuthorCaretListener;

import ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension;

import ro.sync.exml.workspace.api.PluginWorkspace;
import ro.sync.exml.workspace.api.PluginWorkspaceProvider;

import ro.sync.exml.workspace.api.listeners.WSEditorChangeListener;

import ro.sync.exml.workspace.api.options.WSOptionsStorage;

import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer;
import ro.sync.exml.workspace.api.standalone.ViewInfo;

import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.editor.page.WSEditorPage;
import ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage;
import ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPageBase;
import ro.sync.exml.workspace.api.editor.page.text.WSTextEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.xml.WSXMLTextEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.xml.WSXMLTextNodeRange;
import ro.sync.exml.workspace.api.editor.page.text.xml.XPathException;

public class FacsViewerExtension implements WorkspaceAccessPluginExtension {

public void applicationStarted(StandalonePluginWorkspace pluginWorkspaceAccess) {

		WSOptionsStorage optionsStorage = pluginWorkspaceAccess.getOptionsStorage();

		String[] projectsSettings = optionsStorage.getStringArrayOption("facsviewer.projects.settings", null);

		pluginWorkspaceAccess.addViewComponentCustomizer(new ViewComponentCustomizer() {
			/**
			 * @see ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer#customizeView(ro.sync.exml.workspace.api.standalone.ViewInfo)
			 */
			@SuppressWarnings("restriction")
			public void customizeView(ViewInfo viewInfo) {
				if ("ACDH Facs Viewer".equals(viewInfo.getViewID())) {
					String projectView = PluginWorkspaceProvider.getPluginWorkspace().getUtilAccess().expandEditorVariables("${pd}", null);
				    String intiallySelectedProject = null;
					if (projectView != null)		  {
				    	intiallySelectedProject = PluginWorkspaceProvider.getPluginWorkspace().getUtilAccess().expandEditorVariables("${pn}", null);
				
					
					Platform.setImplicitExit(false);
					FacsViewerBrowserPanel facsViewerBrowserPanel = new FacsViewerBrowserPanel(projectsSettings,intiallySelectedProject);
					viewInfo.setComponent(facsViewerBrowserPanel);
					}
				}
				if ("Project".equals(viewInfo.getViewID())) {

					JToolBar projectViewToolBar = (JToolBar) viewInfo.getComponent().getComponents()[2];
					JMenu  projectViewToolBarMenu = (JMenu) projectViewToolBar.getComponents()[0]; 
					projectViewToolBarMenu.addPropertyChangeListener("text",new PropertyChangeListener() {
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
							String currentlySelectedProject = PluginWorkspaceProvider.getPluginWorkspace().getUtilAccess().expandEditorVariables("${pn}", null);
								FacsViewerBrowserPanel.updateCurrentProject(currentlySelectedProject);
			
							}
				});

				}
			}

		});
		pluginWorkspaceAccess.addEditorChangeListener(new WSEditorChangeListener() {
			@Override
			public void editorOpened(URL url) {
				WSEditor editorAccess = pluginWorkspaceAccess.getEditorAccess(url,PluginWorkspace.MAIN_EDITING_AREA);
				WSEditorPage editorPage = editorAccess.getCurrentPage();
				try {
					setupCaretListenerAndFacsIndex(editorPage);
				} catch (XPathException | BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void editorPageChanged(URL url) {
				WSEditor editorAccess = pluginWorkspaceAccess.getEditorAccess(url,PluginWorkspace.MAIN_EDITING_AREA);
				WSEditorPage editorPage = editorAccess.getCurrentPage();
				try {
					setupCaretListenerAndFacsIndex(editorPage);
				} catch (XPathException | BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},PluginWorkspace.MAIN_EDITING_AREA);
		
		
	}
	
	
	
	public void setupCaretListenerAndFacsIndex(WSEditorPage editorPage) throws XPathException, BadLocationException {
		addCaretListener(editorPage);
		generateFacsIndex(editorPage);
	}
	
	public void addCaretListener(WSEditorPage editorPage) {
		if (editorPage instanceof WSAuthorEditorPage) {
			
			((WSAuthorEditorPageBase) editorPage).addAuthorCaretListener(new AuthorCaretListener() {

				@Override
				public void caretMoved(AuthorCaretEvent caretEvent) {
					// TODO Auto-generated method stub
					
				}
				
			});
		} else if (editorPage instanceof WSXMLTextEditorPage) {
		JTextArea pagetextarea = (JTextArea)((WSXMLTextEditorPage) editorPage).getTextComponent();
		pagetextarea.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				
			FacsViewerBrowserPanel.selectImageByCursorPosition(((WSXMLTextEditorPage) editorPage).getCaretOffset());	
				
			}	
		});	
	}
	}
	
public void generateFacsIndex(WSEditorPage editorPage) throws XPathException, BadLocationException {	
	
	if (editorPage instanceof WSAuthorEditorPage) {
		
			
	
	} else if (editorPage instanceof WSXMLTextEditorPage) {
		String facsElementName = FacsViewerBrowserPanel.getCurrentlySelectedProjectConfig().getFacsElementName();
		String facsAttributeName = FacsViewerBrowserPanel.getCurrentlySelectedProjectConfig().getFacsAttributeName();
		String facsXPath = "//"+ facsElementName;
		//currentProjectConfig
		
		Object[] facsElements = ((WSXMLTextEditorPage) editorPage).evaluateXPath(facsXPath);
		WSXMLTextNodeRange[] facsElementsRanges = ((WSXMLTextEditorPage) editorPage).findElementsByXPath(facsXPath);
		ArrayList<FacsIndexElement> facsIndexModel = new ArrayList<FacsIndexElement>();
		for (int i = 0;i < facsElements.length;i++)  {
			FacsIndexElement facsIndexElement = new FacsIndexElement();
			facsIndexElement.setImageName(((Element) facsElements[i]).getAttribute(facsAttributeName));
			int facsIndexElementOffsetStart = ((WSTextEditorPage) editorPage).getOffsetOfLineStart(facsElementsRanges[i].getStartLine()) + facsElementsRanges[i].getStartColumn() - 1;
			facsIndexElement.setOffsetStart(facsIndexElementOffsetStart);
			facsIndexModel.add(facsIndexElement);
		}
		FacsViewerBrowserPanel.setFacsViewerIndex(facsIndexModel);
	}
}


	public boolean applicationClosing() {
		
		return true;
	}

}