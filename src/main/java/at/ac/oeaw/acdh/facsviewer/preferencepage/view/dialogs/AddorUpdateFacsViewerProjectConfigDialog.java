package at.ac.oeaw.acdh.facsviewer.preferencepage.view.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ro.sync.exml.workspace.api.PluginResourceBundle;
import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.standalone.ui.OKCancelDialog;
import ro.sync.exml.workspace.api.standalone.ui.ToolbarButton;

import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import java.awt.Insets;
import javax.swing.SwingConstants;

import at.ac.oeaw.acdh.facsviewer.preferencepage.configtabledata.FacsViewerProjectConfig;
import at.ac.oeaw.acdh.facsviewer.preferencepage.tags.Tags;

import java.io.File;
import java.awt.event.ActionEvent;

public class AddorUpdateFacsViewerProjectConfigDialog extends OKCancelDialog {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5681272456323825889L;
	StandalonePluginWorkspace pluginWorkspace = (StandalonePluginWorkspace) PluginWorkspaceProvider
			.getPluginWorkspace();
	PluginResourceBundle messages = pluginWorkspace.getResourceBundle();
	
	private final JTextField localImgDirTextField = new JTextField();
	private final JTextField projectNameTextField = new JTextField();
	private final JTextField imageServerUrlTextField = new JTextField();
	private final JTextField facsElementTextField = new JTextField();
	private final JTextField facsAttributeTextField = new JTextField();
	JLabel projectNameTextFieldLabel = new JLabel(messages.getMessage(Tags.PROJECT));
	JLabel localImageDirecoryLabel = new JLabel(messages.getMessage(Tags.LOCAL_IMG_DIR));
	JLabel imageServerUrlTextFieldLabel = new JLabel(messages.getMessage(Tags.IMAGE_SERVER_URL));
	JLabel localImgDirTextFieldLabel = new JLabel(messages.getMessage(Tags.LOCAL_IMG_DIR));
	JLabel facsElementTextFieldLabel = new JLabel(messages.getMessage(Tags.IMAGE_REF_ELEM));
	JLabel facsAttributeTextFieldLabel = new JLabel(messages.getMessage(Tags.IMAGE_REF_ATTR));
	private FacsViewerProjectConfig facsViewerProjectConfig = new FacsViewerProjectConfig();
	
	public AddorUpdateFacsViewerProjectConfigDialog(final String title) {
		
		super((JFrame) ((StandalonePluginWorkspace) PluginWorkspaceProvider.getPluginWorkspace()).getParentFrame(), title, true);
		
		final AbstractAction chooseLocalImageDir = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8430055323098351207L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				final File chosenDir = PluginWorkspaceProvider.getPluginWorkspace().chooseDirectory();
            	localImgDirTextField.setText(chosenDir.getAbsolutePath());	
			}
		};
		
		final ToolbarButton browseLocalImageDirButton = new ToolbarButton(chooseLocalImageDir,true);
		browseLocalImageDirButton.setIcon(new ImageIcon(AddorUpdateFacsViewerProjectConfigDialog.class.getResource("/images/Open16_dark@2x.png")));
	
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		/* gridbagcontraints for each gui-element because of using WindowBuilder 
		https://www.eclipse.org/windowbuilder */  
		final GridBagConstraints gbc_projectNameTextFieldLabel = new GridBagConstraints();
		gbc_projectNameTextFieldLabel.anchor = GridBagConstraints.EAST;
		gbc_projectNameTextFieldLabel.insets = new Insets(0, 0, 5, 5);
		gbc_projectNameTextFieldLabel.gridx = 0;
		gbc_projectNameTextFieldLabel.gridy = 0;
		getContentPane().add(projectNameTextFieldLabel, gbc_projectNameTextFieldLabel);
		
		localImageDirecoryLabel.setVerticalAlignment(SwingConstants.TOP);
		projectNameTextField.setColumns(10);
		
		final GridBagConstraints gbc_projectNameTextField = new GridBagConstraints();
		gbc_projectNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_projectNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_projectNameTextField.gridx = 1;
		gbc_projectNameTextField.gridy = 0;
		getContentPane().add(projectNameTextField, gbc_projectNameTextField);
		
		final GridBagConstraints gbc_localImgDirTextFieldLabel = new GridBagConstraints();
		gbc_localImgDirTextFieldLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localImgDirTextFieldLabel.anchor = GridBagConstraints.EAST;
		gbc_localImgDirTextFieldLabel.gridx = 0;
		gbc_localImgDirTextFieldLabel.gridy = 1;
		getContentPane().add(localImgDirTextFieldLabel, gbc_localImgDirTextFieldLabel);
		localImgDirTextField.setHorizontalAlignment(SwingConstants.LEFT);
		localImgDirTextField.setColumns(10);
		
		final GridBagConstraints gbc_localImgDirTextField = new GridBagConstraints();
		gbc_localImgDirTextField.insets = new Insets(0, 0, 5, 5);
		gbc_localImgDirTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_localImgDirTextField.gridx = 1;
		gbc_localImgDirTextField.gridy = 1;
		getContentPane().add(localImgDirTextField, gbc_localImgDirTextField);
		final GridBagConstraints gbc_browseLocalImageDirButton = new GridBagConstraints();
		gbc_browseLocalImageDirButton.insets = new Insets(0, 0, 5, 0);
		gbc_browseLocalImageDirButton.gridx = 2;
		gbc_browseLocalImageDirButton.gridy = 1;
		
		getContentPane().add(browseLocalImageDirButton, gbc_browseLocalImageDirButton);
		
		final GridBagConstraints gbc_imageServerUrlTextFieldLabel = new GridBagConstraints();
		gbc_imageServerUrlTextFieldLabel.anchor = GridBagConstraints.EAST;
		gbc_imageServerUrlTextFieldLabel.insets = new Insets(0, 0, 5, 5);
		gbc_imageServerUrlTextFieldLabel.gridx = 0;
		gbc_imageServerUrlTextFieldLabel.gridy = 2;
		getContentPane().add(imageServerUrlTextFieldLabel, gbc_imageServerUrlTextFieldLabel);
		
		imageServerUrlTextField.setColumns(10);
		
		final GridBagConstraints gbc_imageServerUrlTextField = new GridBagConstraints();
		gbc_imageServerUrlTextField.insets = new Insets(0, 0, 5, 5);
		gbc_imageServerUrlTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageServerUrlTextField.gridx = 1;
		gbc_imageServerUrlTextField.gridy = 2;
		getContentPane().add(imageServerUrlTextField, gbc_imageServerUrlTextField);
		
		final GridBagConstraints gbc_facsimileElementTextFieldLabel = new GridBagConstraints();
		gbc_facsimileElementTextFieldLabel.anchor = GridBagConstraints.EAST;
		gbc_facsimileElementTextFieldLabel.insets = new Insets(0, 0, 5, 5);
		gbc_facsimileElementTextFieldLabel.gridx = 0;
		gbc_facsimileElementTextFieldLabel.gridy = 3;
		getContentPane().add(facsElementTextFieldLabel, gbc_facsimileElementTextFieldLabel);
		
		facsElementTextField.setColumns(10);
		
		final GridBagConstraints gbc_facsElementTextField = new GridBagConstraints();
		gbc_facsElementTextField.insets = new Insets(0, 0, 5, 5);
		gbc_facsElementTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_facsElementTextField.gridx = 1;
		gbc_facsElementTextField.gridy = 3;
		getContentPane().add(facsElementTextField, gbc_facsElementTextField);
		
		final GridBagConstraints gbc_facsimileAttributeTextFieldLabel = new GridBagConstraints();
		gbc_facsimileAttributeTextFieldLabel.anchor = GridBagConstraints.EAST;
		gbc_facsimileAttributeTextFieldLabel.insets = new Insets(0, 0, 0, 5);
		gbc_facsimileAttributeTextFieldLabel.gridx = 0;
		gbc_facsimileAttributeTextFieldLabel.gridy = 4;
		getContentPane().add(facsAttributeTextFieldLabel, gbc_facsimileAttributeTextFieldLabel);
		
		facsAttributeTextField.setColumns(10);
		
		final GridBagConstraints gbc_facsimileAttributeTextField = new GridBagConstraints();
		gbc_facsimileAttributeTextField.insets = new Insets(0, 0, 0, 5);
		gbc_facsimileAttributeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_facsimileAttributeTextField.gridx = 1;
		gbc_facsimileAttributeTextField.gridy = 4;
		getContentPane().add(facsAttributeTextField, gbc_facsimileAttributeTextField);
        
        setResizable(true);
        pack();
        
	}

	@Override
	protected void doOK() {
		super.doOK();
		facsViewerProjectConfig.setProjectName(projectNameTextField.getText());
		facsViewerProjectConfig.setLocalImagePath(localImgDirTextField.getText());
		facsViewerProjectConfig.setImageServerUrl(imageServerUrlTextField.getText());
		facsViewerProjectConfig.setFacsElementName(facsElementTextField.getText());
		facsViewerProjectConfig.setFacsAttributeName(facsAttributeTextField.getText());
		dispose();
	}
	
	public FacsViewerProjectConfig getFacsViewerProjectConfig() {
		return facsViewerProjectConfig;
	}
	
	public void setFacsViewerProjectConfig(FacsViewerProjectConfig facsViewerProjectConfig) {
		this.facsViewerProjectConfig = facsViewerProjectConfig;
		projectNameTextField.setText(facsViewerProjectConfig.getProjectName());
		localImgDirTextField.setText(facsViewerProjectConfig.getLocalImagePath());
		imageServerUrlTextField.setText(facsViewerProjectConfig.getImageServerUrl());
		facsElementTextField.setText(facsViewerProjectConfig.getFacsElementName());
		facsAttributeTextField.setText(facsViewerProjectConfig.getFacsAttributeName());
	}
	
	

	
}
