/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * @author dragos
 *
 */
public class StreamReportOutput extends AbstractOutput {
	
	/**
	 * the line separator
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(StreamReportOutput.class);
	
	/**
	 * buffer for the current line to be output
	 */
	private StringBuffer dataToLog = new StringBuffer();
	
	/**
	 * data separator
	 */
	private String separator;
	
	/**
	 * 
	 */
	public StreamReportOutput() {
		this(null);//obvious
	}

	/**
	 * @param out
	 */
	public StreamReportOutput(OutputStream out) {
		this(out, "\t");
	}
	
	/**
	 * @param out
	 */
	public StreamReportOutput(OutputStream out, String separator) {
		super(out);
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
			String line = dataToLog.toString();
			getOutputStream().write(line.getBytes());
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
