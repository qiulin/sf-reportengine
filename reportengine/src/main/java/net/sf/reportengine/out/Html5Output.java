/*
 Date: 17-11-2004
 */
package net.sf.reportengine.out;

import java.io.Writer;




public class Html5Output extends FreemarkerOutput {
    
	public Html5Output(){
	}
	
	public Html5Output(String filePath){
		super(filePath);
	}
	
	public Html5Output(Writer writer){
		super(writer); 
	}
}