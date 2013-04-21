/**
 * 
 */
package net.sf.reportengine.out;

/**
 * @author dragos balan
 *
 */
public final class TitleProps {
	
	private final String title; 
	private final int colspan; 
	
	public TitleProps(String title, int colspan){
		this.title = title; 
		this.colspan = colspan ;
	}
	
	public String getTitle(){
		return title; 
	}
	
	public int getColspan(){
		return colspan; 
	}
	
}
