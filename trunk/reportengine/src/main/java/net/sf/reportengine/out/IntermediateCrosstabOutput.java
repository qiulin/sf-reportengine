/**
 * 
 */
package net.sf.reportengine.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;
import net.sf.reportengine.util.ReportIoUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is for internal use only. 
 * This is a special type of output because it serializes the provided data into a temporary file. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class IntermediateCrosstabOutput implements ReportOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IntermediateCrosstabOutput.class);
	
	/**
	 * the output stream where all inermediaterows will be serialized
	 */
	private ObjectOutputStream objectOutputStream = null; 
	
	/**
	 * this is the file that holds all intermediary data
	 */
	private File result = null; 
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.IAlgorithmOutput#open()
	 */
	public void open() {
		try {
			result = ReportIoUtils.createTempFile("interm-obj");
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(result));
		} catch (FileNotFoundException e) {
			throw new ReportEngineRuntimeException(e);
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e);
		}
	}
	
	/**
	 * empty implementation
	 */
	public void startReport(ReportProps props){}
	
	/**
	 * empty implementation
	 */
	public void endReport(){}
	
	/**
	 * empty implementation
	 */
	public void startHeaderRow(RowProps props){}
	
	/**
	 * empty implementation
	 */
	public void outputHeaderCell(CellProps props){}
	
	/**
	 * empty implementation
	 */
	public void endHeaderRow(){}
	
	/**
	 * empty implementation
	 */
	public void outputTitle(TitleProps titleProps){}
	
	/**
	 * empty implementation
	 */
	public void startDataRow(RowProps rowProperties) {}
	
	/**
	 * resets the object output stream
	 */
	public void endDataRow() {
		try {
			objectOutputStream.reset();
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e);
		} 
	}

	/**
	 * serializes the values provided ( IntermediateReportRow )
	 */
	public void outputDataCell(CellProps cellProps) {
		//TODO: check if open
		Object value = cellProps.getValue();
		
		//TODO: see if you can fix the check below and get rid of the instanceof
		if(value instanceof IntermediateReportRow){
			IntermediateReportRow intermediateRow = (IntermediateReportRow)value; 
			//serialize
			try {
				LOGGER.debug("writting object to intermediate object stream {}", intermediateRow);
				objectOutputStream.writeObject(intermediateRow);
			} catch (IOException e) {
				throw new ReportEngineRuntimeException(e);
			}
		}else{
			throw new IllegalArgumentException("The intermediate crosstab output works only with IntermediateReportRow.");
		}
	}
	

	/**
	 * flushes the object output stream and then closes it
	 */
	public void close() {
		try {
			objectOutputStream.flush();
			objectOutputStream.close(); 
			LOGGER.info("IntermediateCrosstabReport closed"); 
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e); 
		} 
	}
	
	/**
	 * 
	 * @return
	 */
	public File getSerializedOutputFile(){
		return result; 
	}
}
