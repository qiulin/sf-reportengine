/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public class FoOutputFormat extends AbstractPrintOutputFormat {
	
	

	public FoOutputFormat(){
		this("A4"); 
	}
	
	public FoOutputFormat(String pageSize){
		super(pageSize); 
	}
	
}
