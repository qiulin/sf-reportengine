/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.out.neo.OutputFormat;


/**
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.9
 */
public class ReportProps {
	
	private final OutputFormat format; 
	
	public ReportProps(){
		this(null); 
	}
	
	public ReportProps(OutputFormat outputFormat){
		this.format = outputFormat; 
	}
	
	public OutputFormat getFormat(){
		return format; 
	}
}
