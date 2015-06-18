package net.sf.reportengine.in;

import java.util.List;

public interface ColumnMetadataHolder {
	
	/**
     * <p>getter for columns metadata (column label/header, horizontal alignment, etc.)</p>
     * This is an optional method. If your input doesn't contain any meta data then return an empty array. 
     *  
     * @return the column meta data if this input supports metadata otherwise an empty array
     */
	public List<ColumnMetadata> getColumnMetadata(); 
}
