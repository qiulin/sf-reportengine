/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * output report data into a stream (Writer or OutputStream)
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
	private StringBuilder dataToOutput = new StringBuilder();
	
	/**
	 * data separator
	 */
	private String separator;
	
	/**
	 * empty stream output. 
	 * Use the setter methods to be able to use this output
	 * {@link #setFileName(String)}
	 * {@link #setOutputStream(OutputStream)}
	 * {@link #setOutputStream(OutputStream, String)}
	 * {@link #setWriter(Writer)}
	 */
	public StreamReportOutput(){
		setSeparator(DEFAULT_DATA_SEPARATOR); 
	}
	
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
		this(out, separator, SYSTEM_FILE_ENCODING); 
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
			getWriter().write(dataToOutput.toString());
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
