package net.sf.reportengine.out.neo;

import java.util.HashMap;
import java.util.Map;


/**
 * the output of the report 
 * 
 * @author dragos balan
 *
 */
public abstract class NewReportOutput {
	
	public abstract void open();
	
	public abstract void close(); 
	
	public abstract OutputFormat getFormat(); 
	
	public abstract void output(String templateName, Map rootModel);
	
	public <T> void output(String templateName, String modelName, T value){
		Map<String, T> model = new HashMap<String, T>(1); 
		model.put(modelName, value); 
		output(templateName, model); 
	}
	
	public void output(String templateName){
		output(templateName, null); 
	}
}
