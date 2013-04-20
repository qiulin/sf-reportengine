/**
 * 
 */
package net.sf.reportengine.out;

import java.io.StringWriter;
import java.io.Writer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * @author dragos balan
 * @since 0.8
 */
public class FoOutput implements IReportOutput{
	
	/**
	 * 
	 */
	public final static String DEFAULT_FO_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/freemarker/fo";
	
	/**
	 * 
	 */
	private FreemarkerOutput freemarkerOutput; 
	
	/**
	 * 
	 */
	public FoOutput(){
		this(new StringWriter());
	}
	
	/**
	 * 
	 * @param writer
	 */
	public FoOutput(Writer writer){
		freemarkerOutput = new FreemarkerOutput(writer, getDefaultFreemarkerConfig()); 
	}
	
	/**
	 * 
	 */
	public FoOutput(Writer writer, Configuration freemarkerConfig){
		freemarkerOutput = new FreemarkerOutput(writer, freemarkerConfig); 
	}
	
	public FoOutput(String filePath){
		freemarkerOutput = new FreemarkerOutput(filePath, getDefaultFreemarkerConfig()); 
	}
	
	
	public FoOutput(String filePath, Configuration freemarkerConfig){
		freemarkerOutput = new FreemarkerOutput(filePath, freemarkerConfig); 
	}
	
	private Configuration getDefaultFreemarkerConfig(){
		Configuration freemarkerConfig = new Configuration(); 
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		freemarkerConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), 
										DEFAULT_FO_TEMPLATES_CLASS_PATH));
		return freemarkerConfig; 
	}
	
	
	public void open(){
		freemarkerOutput.open(); 
	}
	
	public void close(){
		freemarkerOutput.close(); 
	}
	
	public void startRow(RowProps rowProps){
		freemarkerOutput.startRow(rowProps); 
	}
	
	public void endRow(){
		freemarkerOutput.endRow(); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.IReportOutput#output(net.sf.reportengine.out.CellProps)
	 */
	public void output(CellProps cellProps) {
		freemarkerOutput.output(cellProps); 
	}
}
