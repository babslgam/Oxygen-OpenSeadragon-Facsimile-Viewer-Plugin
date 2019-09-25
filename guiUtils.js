GUIUtils = function () {

    /* https://bugs.openjdk.java.net/browse/JDK-8090517 */
    Packages.javafx.application.Platform.setImplicitExit(false);
    fxPanel = new Packages.javafx.embed.swing.JFXPanel();
    
    webengine = null;
    buildSwingGUI = function (viewInfo) {

        pluginPanel = new Packages.javax.swing.JPanel();
        pluginPanel.setLayout(new Packages.javax.swing.BoxLayout(pluginPanel, Packages.javax.swing.BoxLayout.PAGE_AXIS));
        pluginPanel.setBorder(new Packages.javax.swing.border.EmptyBorder(10, 0, 0, 0));
        pluginPanel.add(fxPanel);
        viewInfo.setComponent(pluginPanel);
        initWebView();
        
    }

    clearProjectSettingsGUI = function () {
    }

    createJAVAFXScene = function() {

        sceneLayout = new Packages.javafx.scene.layout.StackPane();
        browser = new Packages.javafx.scene.web.WebView();
        browser.setContextMenuEnabled(false);
        webengine = browser.getEngine();
        webengine.setJavaScriptEnabled(true);
        viewerFileUrl = new Packages.java.net.URL(jsDirURL.toString() + 'viewer.html');
        webengine.load(viewerFileUrl.toExternalForm());
        webEngineChangeListener = {
            changed: function (ov, oldState, newState) {
                if (newState == Packages.javafx.concurrent.Worker.State.SUCCEEDED) {
                    document = webengine.getDocument();
                }
            }
        }
        webengine.getLoadWorker().stateProperty().addListener(new JavaAdapter(Packages.javafx.beans.value.ChangeListener, webEngineChangeListener)); 
        sceneLayout.getChildren().add(browser);
        myscene = new Packages.javafx.scene.Scene(sceneLayout);
       
        fxPanel.setScene(myscene);
    }

    function initWebView() {

        Packages.javafx.application.Platform.runLater(
            new Packages.java.lang.Runnable({
                run: function () {
                    createJAVAFXScene();
                                
                }
            })
        );
        
    }

    function checkSettings(path) {

    }

    function executeOnJAVAFXThread(script) {
        Packages.javafx.application.Platform.runLater(
            new Packages.java.lang.Runnable({
                run: function () {
                    if (webengine.getLoadWorker().getState() == Packages.javafx.concurrent.Worker.State.SUCCEEDED) {
                        (function(){
                           eval(script);
                        })();
                }}
            }));
    }
    return {
        buildSwingGUI: buildSwingGUI,
        clearProjectSettingsGUI: clearProjectSettingsGUI,
        executeOnJAVAFXThread: executeOnJAVAFXThread

    }
}