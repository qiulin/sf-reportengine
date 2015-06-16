/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
final class FopUserAgentProperties {
	
	private final String author; 
	private final String filePath; 
	
	public FopUserAgentProperties(String author, String filePath){
		this.author = author; 
		this.filePath = filePath; 
	}
	
	public String getAuthor(){
		return author; 
	}
	
	public String getFilePath(){
		return filePath; 
	}
}
