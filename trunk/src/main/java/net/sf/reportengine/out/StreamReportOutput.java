/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class StreamReportOutput extends AbstractOutput {
	
	/**
	 * the line separator
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * default data/columns separator
	 */
	public static final String DEFAULT_DATA_SEPARATOR = ",";
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(StreamReportOutput.class);
	
	/**
	 * buffer for the current line to be output
	 */
	private StringBuilder dataToLog = new StringBuilder();
	
	/**
	 * data separator
	 */
	private String separator;
	
	/**
	 * @param out
	 */
	public StreamReportOutput(OutputStream out) {
		this(out, DEFAULT_DATA_SEPARATOR);
	}
	
	/**
	 * 
	 * @param out
	 * @param separator
	 */
	public StreamReportOutput(OutputStream out, String separator){
		this(out, separator, System.getProperty("file.encoding")); 
	}
	
	/**
	 * @param out
	 */
	public StreamReportOutput(OutputStream out, String separator, String encoding) {
		super(out, encoding);
		setSeparator(separator);
	}
	
	/**
	 * 
	 * @param writer
	 */
	public StreamReportOutput(Writer writer){
		this(writer, DEFAULT_DATA_SEPARATOR);
	}
	
	/**
	 * 
	 * @param writer
	 * @param separator
	 */
	public StreamReportOutput(Writer writer, String separator){
		super(writer);
		setSeparator(separator); 
	}
	
	/**
     * start row
     */ 
    public void startRow(){
        super.startRow();
        dataToLog.delete(0, dataToLog.length());
	}
	
	public void endRow(){
		try {
			dataToLog.append(LINE_SEPARATOR);
			getWriter().write(dataToLog.toString());
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.AbstractOutput#output(net.sf.reportengine.out.CellProps)
	 */
	@Override
	public void output(CellProps cellProps) {
		dataToLog.append(separator+cellProps);
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
