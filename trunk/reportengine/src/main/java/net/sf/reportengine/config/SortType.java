/**
 * 
 */
package net.sf.reportengine.config;

/**
 * @author dragos balan
 *
 */
public enum SortType {
	
	ASC(1), DESC(-1);
	
	private int signum; 
	
	private SortType(int signum){
		this.signum = signum; 
	}
	
	public int getSignum(){
		return signum; 
	}
}
