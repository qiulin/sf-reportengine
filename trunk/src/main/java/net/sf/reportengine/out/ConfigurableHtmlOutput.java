/**
 * 
 */
package net.sf.reportengine.out;

import java.io.Writer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * Configurable Html output ( based on freemarker templates).
 * 
 * The templates that you can configure are : 
 * 
 * <ol>
 * 		<li>startReport.ftl</li>
 * 		<li>startRow.ftl</li>
 *  	<li>cell.ftl</li>
 *  	<li>endRow.ftl</li>
 *  	<li>endReport.ftl</li>
 * </ol>
 *  
 * @author dragos balan
 * @since 0.7
 */
public class ConfigurableHtmlOutput extends FreemarkerOutput {
	
	/**
	 * Output into memory (using a StringWriter). 
	 * To change the default output writer one can use this constructor together 
	 * with {@link #setOutputWriter(Writer)} or {@link #setFilePath(String)}
	 */
	public ConfigurableHtmlOutput(){
		super(); 
	}
	
	/**
	 * Output into the specified file using Utf-8 encoding
	 * @param filePath	the path of the file
	 */
	public ConfigurableHtmlOutput(String filePath){
		super(filePath);
	}
	
	/**
	 * Output into the specified file using the provided freemarker configuration
	 * 
	 * @param filePath			the path of the output file
	 * @param freemarkerConfig	the freemarker configuration 
	 */
	public ConfigurableHtmlOutput(String filePath, Configuration freemarkerConfig){
		super(filePath, freemarkerConfig); 
	}
	
	/**
	 * output into the specified writer
	 * 
	 * @param writer	the output writer
	 */
	public ConfigurableHtmlOutput(Writer writer){
		super(writer); 
	}
	
	/**
	 * output into the specified writer using the configuration provided
	 * 
	 * @param writer			the output writer
	 * @param freemarkerConfig	the freemarker configuration
	 */
	public ConfigurableHtmlOutput(Writer writer, Configuration freemarkerConfig){
		super(writer, freemarkerConfig); 
	}
	
	
	
	/**
	 * initializer for the default html freemarker configuration
	 */
	protected Configuration initFreemarkerDefaultConfig(){
		Configuration freemarkerConfig = new Configuration(); 
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		freemarkerConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), 
										DEFAULT_HTML_TEMPLATES_CLASS_PATH)); 
		return freemarkerConfig; 
	}
	
	
}
