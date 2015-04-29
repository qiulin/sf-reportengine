/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractPrintOutputFormat extends AbstractOutputFormat {
	
	private String pageSize; 
	
	public AbstractPrintOutputFormat(String templateClassPath, String pageSize){
		super(templateClassPath); 
		this.pageSize = pageSize; 
	}
	
	public String getPageSize(){
		return pageSize; 
	}
	
}
