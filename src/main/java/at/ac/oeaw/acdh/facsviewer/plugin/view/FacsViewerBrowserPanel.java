package at.ac.oeaw.acdh.facsviewer.plugin.view;
 
import java.util.ArrayList;
import java.util.Arrays;

import at.ac.oeaw.acdh.facsviewer.utils.FacsIndexElement;
import at.ac.oeaw.acdh.facsviewer.utils.FacsViewerProjectConfig;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

 
public class FacsViewerBrowserPanel extends JFXPanel {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 3093683751835873890L;
	private static WebEngine engine;
    private static FacsViewerProjectConfig currentProjectConfig = null;
    private static ArrayList<FacsViewerProjectConfig> projectsConfigs = new ArrayList<FacsViewerProjectConfig>();
    private static JSObject window = null;
    
    public FacsViewerBrowserPanel(String[] projectsSettings, String selectedProject) {
    	
    	for (int i = 0; i < projectsSettings.length; i+=5) {
    		FacsViewerProjectConfig pconfig = new FacsViewerProjectConfig(Arrays.copyOfRange(projectsSettings,i,i+5));
  
    		projectsConfigs.add(pconfig);
    	}
    	
    	selectSettingsForProject(selectedProject,projectsConfigs);
    	
    	init();
        
    }
    
    private void init() {
        Platform.runLater(this::createScene);
    }
    
    
    @SuppressWarnings("restriction")
	public void createScene() {
    	StackPane stackPane = new StackPane();
    	WebView browser = new WebView();
    	
    	engine = browser.getEngine();
    	engine.setJavaScriptEnabled(true);
    	engine.load(FacsViewerBrowserPanel.class.getResource("/viewer.html").toExternalForm());
    	window = (JSObject) engine.executeScript("window");
    	engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
    		
			public void changed(ObservableValue<? extends Worker.State> observable, State oldState, State newState) {
				 if (newState == State.SUCCEEDED) {
				window.setMember("projectsConfigs", projectsConfigs);
				window.setMember("currentProjectConfig", currentProjectConfig);
	            engine.executeScript("var facsViewerGUIPluginInstance = new facsViewerGUIPlugin(currentProjectConfig)");
		 
				 }
			
			}
    		
    	});
    	
    	stackPane.getChildren().add(browser);
    	Scene scene = new Scene(stackPane);
    	this.setScene(scene);
    }
    
    @SuppressWarnings("restriction")
	public static void updateCurrentProject(String projectName) {
    		
    	selectSettingsForProject(projectName,projectsConfigs);
    	Platform.runLater( new Runnable() { 
            @Override
            public void run() {
	            window.setMember("currentProjectConfig", currentProjectConfig);
            	engine.executeScript("facsViewerGUIPluginInstance.selectProject(currentProjectConfig)");
            	
           
                
            }
    	});
    }
    
    public static FacsViewerProjectConfig getCurrentlySelectedProjectConfig() {
		return currentProjectConfig;
    	
    }
    @SuppressWarnings("restriction")
    public static void setFacsViewerIndex(ArrayList<FacsIndexElement> fim) {
    	
    	Platform.runLater( new Runnable() { 
            @Override
            public void run() {
            
            	window.setMember("fcsidx", fim);
	            engine.executeScript("facsViewerGUIPluginInstance.setFacsIndex(fcsidx)");
            	engine.executeScript("facsViewerGUIPluginInstance.generateDropDown()");
            }
    	});
    }
    
    public static void selectSettingsForProject(String projectName, ArrayList<FacsViewerProjectConfig> projectsConfigs) {
    	currentProjectConfig = null;
    	for (FacsViewerProjectConfig fvpc : projectsConfigs) {
    		if (fvpc.getProjectName().equals(projectName)) {
    			currentProjectConfig = fvpc;
    		}
    	}
    	
    }
    
    @SuppressWarnings("restriction")
	public static void selectImageByCursorPosition(int carretPosition) {
    	
    	Platform.runLater( new Runnable() { 
            @Override
            public void run() {
            	
	            window.setMember("currentCarretOffset", carretPosition);
            	engine.executeScript("facsViewerGUIPluginInstance.selectImageByCursorPosition(currentCarretOffset)");
            }
    	});
    	
    }
}