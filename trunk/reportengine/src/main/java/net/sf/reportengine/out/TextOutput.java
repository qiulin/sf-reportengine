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
 * txt output report data as separated data. 
 * Because the data is separated by commans (or something else) this output is not 
 * effective for Crosstab reports. A better version will be provided in the next releases
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class TextOutput extends AbstractCharBasedOutput{
	
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
	 * outputs into a String if no other writer is set
	 */
	public TextOutput(){
		super(); 
	}
	
	/**
	 * 
	 * @param writer
	 */
	public TextOutput(Writer writer){
		this(writer, DEFAULT_DATA_SEPARATOR);
	}
	
	/**
	 * 
	 * @param writer
	 * @param separator
	 */
	public TextOutput(Writer writer, String separator){
		super(writer);
		setSeparator(separator); 
	}
	
	public void open(){
		markAsOpen(); 
	}
	
	public void outputTitle(TitleProps titleProps){
		try{
			getOutputWriter().write(titleProps.getTitle()); 
			getOutputWriter().write(ReportIoUtils.LINE_SEPARATOR); 
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
	}
	
	
	/**
     * start row
     */ 
    public void startRow(RowProps rowProperties){
    	ensureOutputIsOpen(); 
        rowDataBuffer = new StringBuilder();
	}
	
    /**
     * end row
     */
	public void endRow(){
		try {
			rowDataBuffer.append(ReportIoUtils.LINE_SEPARATOR);
			getOutputWriter().write(rowDataBuffer.toString());
		} catch (IOException e) {
			LOGGER.error("error writting the row ", e);
			throw new ReportOutputException(e);
		}
	}
	
	/**
	 * output
	 */
	public void output(CellProps cellProps) {
		rowDataBuffer.append(cellProps.getValue()).append(separator);
	}

	/**
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
