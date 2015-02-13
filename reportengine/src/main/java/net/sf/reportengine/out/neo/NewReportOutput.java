package net.sf.reportengine.out.neo;

import java.io.Writer;

import freemarker.template.Configuration;


/**
 * the output of the report 
 * 
 * @author dragos balan
 *
 */
public interface NewReportOutput {
	
	public void open();
	
	public void close(); 
	
	public Writer getWriter();
	
	public Configuration getFmConfig(); 
}
