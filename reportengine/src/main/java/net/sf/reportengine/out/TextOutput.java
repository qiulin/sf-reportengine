/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.Writer;

import net.sf.reportengine.util.ReportIoUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * txt output report data as comma (or something else) separated data. 
 * Because the data is separated by commas (or something else) this output is not 
 * recommended for Crosstab reports. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class TextOutput extends AbstractCharOutput{
	
	/**
	 * default data/columns separator
	 */
	public static final String DEFAULT_DATA_SEPARATOR = ",";
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TextOutput.class);
	
	/**
	 * buffer for the current line to be output
	 */
	private StringBuilder rowDataBuffer;  
		
	/**
	 * data separator
	 */
	private String separator = DEFAULT_DATA_SEPARATOR;
	
	/**
	 * text output into a String Writer (memory)
	 */
	public TextOutput(){
		super(); 
	}
	
	/**
	 * text output into the specified writer
	 * 
	 * @param writer	the output writer
	 */
	public TextOutput(Writer writer){
		this(writer, DEFAULT_DATA_SEPARATOR);
	}
	
	/**
	 * text output into the specified writer using the separator
	 * 
	 * @param writer		the output writer
	 * @param separator		the separator
	 */
	public TextOutput(Writer writer, String separator){
		this(writer, separator, true);
	}
	
	
	/**
	 * text output into the specified writer using the separator
	 * 
	 * @param writer		the output writer
	 * @param separator		the separator
	 * @param managedWriter	if true, this class is responsible for the lifecycle of the writer
	 */
	public TextOutput(Writer writer, String separator, boolean managedWriter){
		super(writer, managedWriter);
		setSeparator(separator); 
	}
	
	/**
	 * 
	 */
	public void open(){
		markAsOpen(); 
	}
	
	/**
	 * empty implementation
	 */
	public void startReport(ReportProps reportProps){
		
	}
	
	
	/**
	 * writes the title to the output
	 */
	public void outputTitle(TitleProps titleProps){
		try{
			getOutputWriter().write(titleProps.getTitle()); 
			getOutputWriter().write(ReportIoUtils.LINE_SEPARATOR); 
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
	}
	
	/**
	 * re-init the header buffer
	 */
	public void startHeaderRow(RowProps rowProps){
		startDataRow(rowProps);
	}
	
	/**
	 * writes the cell data into the header buffer
	 */
	public void outputHeaderCell(CellProps cellProps){
		outputDataCell(cellProps); 
	}
	
	/**
	 * writes the header buffer to the writer
	 */
	public void endHeaderRow(){
		endDataRow(); 
	}
	
	/**
     * re-init the data row buffer
     */ 
    public void startDataRow(RowProps rowProperties){
    	ensureOutputIsOpen(); 
        rowDataBuffer = new StringBuilder();
	}
	
    /**
     * writes the data row buffer to the output writer
     */
	public void endDataRow(){
		try {
			rowDataBuffer.append(ReportIoUtils.LINE_SEPARATOR);
			getOutputWriter().write(rowDataBuffer.toString());
		} catch (IOException e) {
			LOGGER.error("error writting the row ", e);
			throw new ReportOutputException(e);
		}
	}
	
	/**
	 * writes the cell value into the data row buffer
	 */
	public void outputDataCell(CellProps cellProps) {
		rowDataBuffer.append(cellProps.getValue()).append(separator);
	}

	/**
	 * empty implementation
	 */
	public void endReport(){
		
	}
	
	/**
	 * getter for the separator of data in the report
	 * 
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * setter for the separator
	 * 
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
