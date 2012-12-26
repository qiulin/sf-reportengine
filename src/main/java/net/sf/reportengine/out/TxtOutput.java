/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * output report data into a stream (Writer or OutputStream)
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class TxtOutput extends AbstractCharacterOutput{
	
	/**
	 * default data/columns separator
	 */
	public static final String DEFAULT_DATA_SEPARATOR = "\t";
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(TxtOutput.class);
	
	/**
	 * buffer for the current line to be output
	 */
	private StringBuilder dataToOutput = new StringBuilder();
	
	/**
	 * data separator
	 */
	private String separator;
	
	/**
	 * outputs into a String if no other writer is set
	 */
	public TxtOutput(){
		super(); 
	}
	
	/**
	 * 
	 * @param writer
	 */
	public TxtOutput(Writer writer){
		this(writer, DEFAULT_DATA_SEPARATOR);
	}
	
	/**
	 * 
	 * @param writer
	 * @param separator
	 */
	public TxtOutput(Writer writer, String separator){
		super(writer);
		setSeparator(separator); 
	}
	
	/**
     * start row
     */ 
    public void startRow(RowProps rowProperties){
        super.startRow(rowProperties);
        dataToOutput.delete(0, dataToOutput.length());
	}
	
    /**
     * end row
     */
	public void endRow(){
		try {
			dataToOutput.append(LINE_SEPARATOR);
			getOutputWriter().write(dataToOutput.toString());
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	
	public void output(CellProps cellProps) {
		dataToOutput.append(separator+cellProps);
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
