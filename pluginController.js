PluginController = function (pluginWorkspaceAccess) {

    pluginWorkspaceAccess = pluginWorkspaceAccess;
    this.currentProjectSettings = {};
    this.pluginSettings = {};
    this.guiUtils = new GUIUtils();
    this.projectChangedEvent = null;
    this.currentPage;
    this.caretListeners = {
        caretListenerTextMode: null,
        caretListenerAuthorMode: null
    };
    var self = this;


    function loadSettings() {
        editorAccess = pluginWorkspaceAccess.getCurrentEditorAccess(Packages.ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace.MAIN_EDITING_AREA);
        pathToSettingsFile = jsDirURL.toString() + 'settings.json';
        settingsURI = new Packages.java.net.URL(pathToSettingsFile).toURI();
        if (Packages.java.io.File(settingsURI).isFile()) {
            pluginSettingsFile = pluginWorkspaceAccess.getUtilAccess().locateFile(new Packages.java.net.URL(pathToSettingsFile));
        } else {
            pluginSettingsFile = new Packages.java.io.File(settingsURI);
            fw = new Packages.java.io.FileWriter(pluginSettingsFile);
            fw.write("{}");
            fw.close();
        }
        this.pluginSettings = JSON.parse(Packages.com.google.common.io.Files.toString(pluginSettingsFile, Packages.com.google.common.base.Charsets.UTF_8));
    }

    function setupViewCustomizer() {
        viewCustomizer = {
            customizeView: function (viewInfo) {
                if ("ACDH Facs Viewer" == viewInfo.getViewID()) {
                    self.guiUtils.buildSwingGUI(viewInfo);
                }
            }
        }
        pluginWorkspaceAccess.addViewComponentCustomizer(viewCustomizer);
    }

    function setupProjectChangeListener() {
        projectChangeListener = {
            projectChanged: function (oldProjectURL, newProjectURL) {
                projectPath = newProjectURL.getPath();
                if (new Packages.java.io.File(projectPath).isFile()) {

                    if (pluginSettings[projectPath]) {
                        self.currentProjectSettings = pluginSettings[projectPath]
                        self.projectChangedEvent();
                    } else {
                        self.guiUtils.clearProjectSettingsGUI();
                    }
                }
            }
        }

        pluginWorkspaceAccess.getProjectManager().addProjectChangeListener(projectChangeListener);

    }


    editorChangeListener = {
        editorOpened: function (Url) {
            currentPage = pluginWorkspaceAccess.getCurrentEditorAccess(Packages.ro.sync.exml.workspace.api.PluginWorkspace.MAIN_EDITING_AREA).getCurrentPage();
            self.currentPage = currentPage;
            if (currentPage instanceof Packages.ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage) {
                currentPage.addAuthorCaretListener(self.caretListeners.caretListenerAuthorMode);
            } else if (currentPage instanceof Packages.ro.sync.exml.workspace.api.editor.page.text.xml.WSXMLTextEditorPage) {
                Packages.java.lang.System.out.print("a" + self.caretListeners.caretListenerTextMode);
                currentPage.getTextComponent().addCaretListener(new JavaAdapter(javax.swing.event.CaretListener,
                    self.caretListeners.caretListenerTextMode));
            }


            // self.facsViewerController.generateCaretListener();
            self.guiUtils.executeOnJAVAFXThread(
                'webengine.executeScript("enableControls()");'
            );
        },
        editorPageChanged: function (Url) {
            // checkSettings(Url.getPath());
            currentPage = pluginWorkspaceAccess.getCurrentEditorAccess(Packages.ro.sync.exml.workspace.api.PluginWorkspace.MAIN_EDITING_AREA).getCurrentPage();
            elf.currentPage = currentPage;
            if (currentPage instanceof Packages.ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage) {
                currentPage.addAuthorCaretListener(self.caretListeners.caretListenerAuthorMode);
            } else if (currentPage instanceof Packages.ro.sync.exml.workspace.api.editor.page.text.xml.WSXMLTextEditorPage) {
                currentPage.getTextComponent().addCaretListener(new JavaAdapter(javax.swing.event.CaretListener,
                    self.caretListeners.caretListenerTextMode));
            }
            self.guiUtils.executeOnJAVAFXThread(
                'webengine.executeScript("enableControls()");'
            );
        },
        editorActivated: function (Url) {

        }
    }


    pluginWorkspaceAccess.addEditorChangeListener(
        new JavaAdapter(Packages.ro.sync.exml.workspace.api.listeners.WSEditorChangeListener,
            editorChangeListener),
        Packages.ro.sync.exml.workspace.api.PluginWorkspace.MAIN_EDITING_AREA);

    loadSettings();

    setupViewCustomizer();

    setupProjectChangeListener();


}