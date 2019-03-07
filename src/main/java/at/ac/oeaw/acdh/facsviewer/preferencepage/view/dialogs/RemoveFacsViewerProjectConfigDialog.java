package at.ac.oeaw.acdh.facsviewer.preferencepage.view.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import at.ac.oeaw.acdh.facsviewer.preferencepage.tags.Tags;
import ro.sync.exml.workspace.api.PluginResourceBundle;
import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ui.OKCancelDialog;

public class RemoveFacsViewerProjectConfigDialog extends OKCancelDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -693346159590006039L;
	StandalonePluginWorkspace pluginWorkspace = (StandalonePluginWorkspace) PluginWorkspaceProvider
			.getPluginWorkspace();
	PluginResourceBundle messages = pluginWorkspace.getResourceBundle();
	
	private final JLabel deleteSettingsConfirmationLabel = new JLabel(messages.getMessage(Tags.REMOVE_PROJECT_QUESTION));
	
	public RemoveFacsViewerProjectConfigDialog(String title) {
		
		super((JFrame) ((StandalonePluginWorkspace) PluginWorkspaceProvider.getPluginWorkspace()).getParentFrame(), title, true);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gbc_deleteSettingsConfirmationLabel = new GridBagConstraints();
		gbc_deleteSettingsConfirmationLabel.gridx = 1;
		gbc_deleteSettingsConfirmationLabel.gridy = 0;
		getContentPane().add(deleteSettingsConfirmationLabel, gbc_deleteSettingsConfirmationLabel);
		
        setResizable(true);
        pack();
        
	}
	
	@Override
	protected void doOK() {
		super.doOK();
		dispose();
	}

}
