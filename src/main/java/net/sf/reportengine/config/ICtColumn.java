/**
 * 
 */
package net.sf.reportengine.config;

/**
 * The interface for a cross-tab report column configuration
 * 
 * @author dragos balan
 * @deprecated
 */
public interface ICtColumn extends IColumn /*, Cloneable*/ {
	
	public boolean isShownInHeader();
	
	
	//public ICtColumn clone();
	
}
