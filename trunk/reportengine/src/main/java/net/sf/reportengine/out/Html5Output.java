/*
 Date: 17-11-2004
 */
package net.sf.reportengine.out;

import java.io.Writer;



/**
 * html output 
 * 
 * @author dragos balan
 *
 */
public class Html5Output extends FreemarkerOutput {
    
	/**
	 * html output into memory 
	 */
	public Html5Output(){
		super();
	}
	
	/**
	 * html output on the specified path
	 * @param filePath
	 */
	public Html5Output(String filePath){
		this(filePath, false); 
	}
	
	/**
	 * htnl output where the file is opened in append mode according to append flag
	 * 
	 * @param filePath	the file path
	 * @param append	if true the file will be opened in append mode
	 */
	public Html5Output(String filePath, boolean append){
		super(filePath, append);
	}
	
	/**
	 * html output to the given writer 
	 * 
	 * @param writer		the writer
	 */
	public Html5Output(Writer writer){
		this(writer, true); 
	}
	
	/**
	 * html output to the given writer where the lifecycle of the writer is controlled by this class (if the managedWriter = true)
	 * 
	 * @param writer		the writer
	 * @param managedWriter	if true the lifecycle of the writer is controlled by this class
	 */
	public Html5Output(Writer writer, boolean managedWriter){
		super(writer, managedWriter); 
	}
}