function applicationStarted(pluginWorkspaceAccess) {

	pluginController = new PluginController(pluginWorkspaceAccess);
	facsViewerController = new FacsViewerController();


}

function applicationClosing(pluginWorkspaceAccess) {}