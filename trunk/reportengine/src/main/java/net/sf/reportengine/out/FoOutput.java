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
public class FoOutput implements ReportOutput{
	
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
	 * @param writer
	 * @param freemarkerConfig
	 */
	public FoOutput(Writer writer, Configuration freemarkerConfig){
		freemarkerOutput = new FreemarkerOutput(writer, freemarkerConfig); 
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public FoOutput(String filePath){
		freemarkerOutput = new FreemarkerOutput(filePath, false, getDefaultFreemarkerConfig()); 
	}
	
	/**
	 * 
	 * @param filePath
	 * @param append 		true if you want to append the current report to an existing file	
	 */
	public FoOutput(String filePath, boolean append ){
		freemarkerOutput = new FreemarkerOutput(filePath, append, getDefaultFreemarkerConfig()); 
	}    
	
	/**
	 * 
	 * @param filePath
	 * @param append 			true if you want to append the current report to an existing file
	 * @param freemarkerConfig
	 */
	public FoOutput(String filePath, boolean append, Configuration freemarkerConfig){
		freemarkerOutput = new FreemarkerOutput(filePath, append, freemarkerConfig); 
	}
	
	/**
	 * 
	 * @return
	 */
	private Configuration getDefaultFreemarkerConfig(){
		Configuration freemarkerConfig = new Configuration(); 
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		freemarkerConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), 
										DEFAULT_FO_TEMPLATES_CLASS_PATH));
		return freemarkerConfig; 
	}
	
	/**
	 * 
	 */
	public void open(){
		freemarkerOutput.open(); 
	}
	
	public void startReport(ReportProps reportProps){
		freemarkerOutput.startReport(reportProps); 
	}
	
	public void outputTitle(TitleProps titleProps){
		freemarkerOutput.outputTitle(titleProps); 
	}
	
	public void startHeaderRow(RowProps rowProps){
		freemarkerOutput.startHeaderRow(rowProps); 
	}
	
	public void outputHeaderCell(CellProps cellProps){
		freemarkerOutput.outputHeaderCell(cellProps); 
	}
	
	public void endHeaderRow(){
		freemarkerOutput.endHeaderRow(); 
	}
	
	public void startDataRow(RowProps rowProps){
		freemarkerOutput.startDataRow(rowProps); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.ReportOutput#output(net.sf.reportengine.out.CellProps)
	 */
	public void outputDataCell(CellProps cellProps) {
		freemarkerOutput.outputDataCell(cellProps); 
	}
	
	public void endDataRow(){
		freemarkerOutput.endDataRow(); 
	}
	
	public void endReport(){
		freemarkerOutput.endReport(); 
	}
	
	public void close(){
		freemarkerOutput.close(); 
	}
}
