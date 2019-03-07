package at.ac.oeaw.acdh.facsviewer.preferencepage.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import at.ac.oeaw.acdh.facsviewer.preferencepage.view.dialogs.AddorUpdateFacsViewerProjectConfigDialog;
import at.ac.oeaw.acdh.facsviewer.preferencepage.view.dialogs.RemoveFacsViewerProjectConfigDialog;
import at.ac.oeaw.acdh.facsviewer.preferencepage.configtabledata.FacsViewerProjectConfig;
import at.ac.oeaw.acdh.facsviewer.preferencepage.configtabledata.FacsViewerSettingsTableModel;
import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.options.WSOptionsStorage;
import ro.sync.exml.workspace.api.standalone.ui.Table;
import ro.sync.exml.workspace.api.standalone.ui.ToolbarButton;

import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class OptionPage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1242714447922738907L;

	FacsViewerSettingsTableModel fvstm = new FacsViewerSettingsTableModel();

	private final Table FacsViewerProjectSettingsTable = new Table(fvstm);
	JScrollPane tablePane = new JScrollPane(FacsViewerProjectSettingsTable);

	ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace pluginWorkspace = (ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace) PluginWorkspaceProvider
			.getPluginWorkspace();
	ro.sync.exml.workspace.api.PluginResourceBundle messages = pluginWorkspace.getResourceBundle();

	final AddorUpdateFacsViewerProjectConfigDialog addSettingsDialog = new AddorUpdateFacsViewerProjectConfigDialog("Add new Project Settings");
	final AddorUpdateFacsViewerProjectConfigDialog updateSettingsDialog = new AddorUpdateFacsViewerProjectConfigDialog("Update Project Settings");
	final RemoveFacsViewerProjectConfigDialog removeSettingsDialog = new RemoveFacsViewerProjectConfigDialog("Remove Project Settings");

	private int selectedRow;
	private FacsViewerProjectConfig selectedFacsViewerProjectConfig;

	ToolbarButton modifySettingsButton;
	ToolbarButton deleteSettingsButton;
	FacsViewerProjectConfig currentFacsViewerProjectConfig;

	public OptionPage() {
		FacsViewerProjectSettingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		FacsViewerProjectSettingsTable.setCellSelectionEnabled(false);
		FacsViewerProjectSettingsTable.setRowSelectionAllowed(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.anchor = GridBagConstraints.NORTHWEST;
		gbc_table.insets = new Insets(5, 5, 5, 0);
		gbc_table.fill = GridBagConstraints.HORIZONTAL;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		gbc_table.gridwidth = 3;
		add(tablePane, gbc_table);

		addSettingsDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (addSettingsDialog.getResult() == 1) {

					currentFacsViewerProjectConfig = addSettingsDialog.getFacsViewerProjectConfig();
					fvstm.addRow(currentFacsViewerProjectConfig);

				}
			}
		});

		updateSettingsDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (updateSettingsDialog.getResult() == 1) {
					updateSettingsDialog.getFacsViewerProjectConfig();
					fvstm.updateSetting(selectedFacsViewerProjectConfig, selectedRow);
				}
			}
		});

		removeSettingsDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (removeSettingsDialog.getResult() == 1) {
					fvstm.deleteRow(selectedRow);
				}
			}
		});

		AbstractAction showAddSettingsDialog = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1473663310056092070L;

			@Override
			public void actionPerformed(ActionEvent e) {
				addSettingsDialog.setLocationRelativeTo((JFrame) pluginWorkspace.getParentFrame());
				addSettingsDialog.setVisible(true);
			}
		};

		AbstractAction showUpdateSettingsDialog = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5578027939961277803L;

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSettingsDialog.setLocationRelativeTo((JFrame) pluginWorkspace.getParentFrame());
				updateSettingsDialog.setVisible(true);
			}
		};

		AbstractAction showRemoveSettingsDialog = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3104178739649528965L;

			@Override
			public void actionPerformed(ActionEvent e) {
				removeSettingsDialog.setLocationRelativeTo((JFrame) pluginWorkspace.getParentFrame());
				removeSettingsDialog.setVisible(true);
			}
		};

		ToolbarButton addSettingsButton = new ToolbarButton(showAddSettingsDialog, true);
		addSettingsButton.setIcon(new ImageIcon(OptionPage.class.getResource("/images/Add16@2x.png")));

		GridBagConstraints gbc_addSettingsButton = new GridBagConstraints();
		gbc_addSettingsButton.insets = new Insets(0, 0, 0, 5);
		gbc_addSettingsButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_addSettingsButton.gridx = 0;
		gbc_addSettingsButton.gridy = 1;
		add(addSettingsButton, gbc_addSettingsButton);

		ToolbarButton modifySettingsButton = new ToolbarButton(showUpdateSettingsDialog, true);
		modifySettingsButton.setIcon(new ImageIcon(OptionPage.class.getResource("/images/SettingsToolbar16@2x.png")));
		modifySettingsButton.setDisabledIcon(
				new ImageIcon(OptionPage.class.getResource("/images/SettingsToolbarDisabled16@2x.png")));
		modifySettingsButton.setEnabled(false);

		GridBagConstraints gbc_modifySettingsButton = new GridBagConstraints();
		gbc_modifySettingsButton.insets = new Insets(0, 0, 0, 5);
		gbc_modifySettingsButton.anchor = GridBagConstraints.NORTH;
		gbc_modifySettingsButton.gridx = 1;
		gbc_modifySettingsButton.gridy = 1;
		add(modifySettingsButton, gbc_modifySettingsButton);

		ToolbarButton deleteSettingsButton = new ToolbarButton(showRemoveSettingsDialog, true);
		deleteSettingsButton.setIcon(new ImageIcon(OptionPage.class.getResource("/images/Remove16@2x.png")));
		deleteSettingsButton
				.setDisabledIcon(new ImageIcon(OptionPage.class.getResource("/images/RemoveDisabled16@2x.png")));
		deleteSettingsButton.setEnabled(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		add(deleteSettingsButton, gbc_btnNewButton);

		FacsViewerProjectSettingsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && FacsViewerProjectSettingsTable.getSelectedRow() != -1) {
					selectedRow = FacsViewerProjectSettingsTable.getSelectedRow();
					FacsViewerSettingsTableModel fvstm = (FacsViewerSettingsTableModel) FacsViewerProjectSettingsTable
							.getModel();
					selectedFacsViewerProjectConfig = fvstm.getSetting(selectedRow);
					updateSettingsDialog.setFacsViewerProjectConfig(selectedFacsViewerProjectConfig);
					modifySettingsButton.setEnabled(true);
					deleteSettingsButton.setEnabled(true);
				}
			}
		});
		loadPageState();
	}

	public void restoreDefault() {
		// TODO Auto-generated method stub

	}

	public void savePageState() {
		WSOptionsStorage optionStorage = pluginWorkspace.getOptionsStorage();
		FacsViewerSettingsTableModel tm = (FacsViewerSettingsTableModel) FacsViewerProjectSettingsTable.getModel();
		
		/* todo: iterate through getters by using FacsViewerProjectConfig.class.getDeclaredMethods() */
		String[] projectsoptions = new String[tm.getRowCount() * 5];
		int i = 0;
		
		for (FacsViewerProjectConfig setting : tm.getRowData()) {
			
			projectsoptions[i] =   setting.getProjectName();
			projectsoptions[i+1] = setting.getLocalImagePath();
			projectsoptions[i+2] = setting.getImageServerUrl();
			projectsoptions[i+3] = setting.getFacsElementName();
			projectsoptions[i+4] = setting.getFacsAttributeName();
			i=+5;
		}
		optionStorage.setStringArrayOption("facsviewer.projects.settings", projectsoptions);
	}

	private void loadPageState() {
		FacsViewerSettingsTableModel tm = (FacsViewerSettingsTableModel) FacsViewerProjectSettingsTable.getModel();
		WSOptionsStorage optionStorage = pluginWorkspace.getOptionsStorage();
		String[] defaultOptions = {};
		String[] projectsoptions = optionStorage.getStringArrayOption("facsviewer.projects.settings", defaultOptions);
		if (projectsoptions.length > 0 && projectsoptions.length % 5 == 0) {
			for (int i = 0; i < projectsoptions.length; i += 5) {
				FacsViewerProjectConfig projectSetting = new FacsViewerProjectConfig(Arrays.copyOfRange(projectsoptions,i,i+5));
				tm.addRow(projectSetting);

			}
		}
	}
	
	public FacsViewerSettingsTableModel getTableModel() {
		return this.fvstm;
	}

}
