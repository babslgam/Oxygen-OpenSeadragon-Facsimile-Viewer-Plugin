package at.ac.oeaw.acdh.facsviewer.preferencepage.configtabledata;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FacsViewerSettingsTableModel extends AbstractTableModel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5916553031965556457L;
	private final String[] colNames = {"Project", "local Image Directory", "Image Server Url", "Facs Element", "Facs Attribute"};
	private List<FacsViewerProjectConfig> rowData = new ArrayList<FacsViewerProjectConfig>(); 
	 
	
	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
	
	
	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return getRowData().size() ;
	}
	

	public void updateSetting(FacsViewerProjectConfig setting, int rowIndex) {
		getRowData().set(rowIndex, setting);
		this.fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		FacsViewerProjectConfig settingsObj = getRowData().get(rowIndex);
		switch (columnIndex) {
			case 0: return settingsObj.getProjectName();
			case 1: return settingsObj.getLocalImagePath();
			case 2: return settingsObj.getImageServerUrl();
			case 3: return settingsObj.getFacsElementName();
			case 4: return settingsObj.getFacsAttributeName();
			default:
		}
		return null;
	}

	public void addRow(FacsViewerProjectConfig settingsObject) {
		getRowData().add(settingsObject);
		int row = getRowData().indexOf(settingsObject);
		fireTableRowsInserted(row, row);
	}
	public void deleteRow(int row) {
		getRowData().remove(row);
		this.fireTableRowsDeleted(row, row);
		
	}
	
	public FacsViewerProjectConfig getSetting(int row) {
		return getRowData().get(row);
		
	}
	
	public FacsViewerProjectConfig getSetting(String projectname) {
		FacsViewerProjectConfig current = null;
		for (FacsViewerProjectConfig config : rowData) {
			if (config.projectName == projectname) {
				current = config;
			}
		}
		return current;
	}
	
	
	public List<FacsViewerProjectConfig> getRowData() {
		return rowData;
	}


	public void setRowData(List<FacsViewerProjectConfig> rowData) {
		this.rowData = rowData;
	}

}
