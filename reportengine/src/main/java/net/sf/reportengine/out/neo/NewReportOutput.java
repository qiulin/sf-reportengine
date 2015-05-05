package net.sf.reportengine.out.neo;

import java.util.Map;


/**
 * the output of the report 
 * 
 * @author dragos balan
 *
 */
public interface NewReportOutput {
	
	/**
	 * 
	 */
	public void open();
	
	/**
	 * 
	 */
	public void close(); 
	
	/**
	 * 
	 * @param templateName
	 * @param rootModel
	 */
	public void output(String templateName, Map rootModel);
	
	
	/**
	 * 
	 * @return
	 */
	public OutputFormat getFormat(); 
	
	
}
