package at.ac.oeaw.acdh.facsviewer.plugin;

import ro.sync.exml.plugin.Plugin;
import ro.sync.exml.plugin.PluginDescriptor;

/**
 * 
 * 
 * 
 *
 */
public class FacsViewerPlugin extends Plugin {
	/**
	 * The static plugin instance.
	 */
	private static FacsViewerPlugin instance = null;

	/**
	 * Constructs the plugin.
	 * 
	 * @param descriptor
	 *          The plugin descriptor
	 */
	public FacsViewerPlugin(PluginDescriptor descriptor) {
		super(descriptor);

		if (instance != null) {
			throw new IllegalStateException("Already instantiated!");
		}
		instance = this;
	}

	/**
	 * Get the plugin instance.
	 * 
	 * @return the shared plugin instance.
	 */
	public static FacsViewerPlugin getInstance() {
		return instance;
	}

}
