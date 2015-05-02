/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportOutput implements NewReportOutput {
	
	/**
	 * the output format
	 */
	private final OutputFormat format; 
	
	/**
	 * 
	 * @param format
	 */
	public AbstractReportOutput(OutputFormat format){
		this.format = format; 
	}
	
	
	/**
	 * 
	 * @param templateName
	 * @param modelName
	 * @param value
	 */
	public <T> void output(String templateName, String modelName, T value){
		Map<String, T> model = new HashMap<String, T>(1); 
		model.put(modelName, value); 
		output(templateName, model); 
	}
	
	/**
	 * 
	 * @param templateName
	 */
	public void output(String templateName){
		output(templateName, null); 
	}
	
	public OutputFormat getFormat() {
		return format;
	}
	
}
