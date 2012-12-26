/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Output based on freemarker templates.
 * It has a template for every step of the report: 
 * 
 *  1. startReport
 *  2. startRow
 *  3. outputCell
 *  4. endRow
 *  5. endReport
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class FreemarkerOutput extends AbstractCharacterOutput {
	
	/**
	 * freemarker configuration class
	 */
	private Configuration freemarkerConfig = null;
	
	/**
	 * report templates
	 */
	private Template startReportTemplate = null; 
	private Template endReportTemplate = null; 
	
	/**
	 * row templates
	 */
	private Template startRowTemplate = null;
	private Template endRowTemplate = null; 
	
	/**
	 * cell template
	 */
	private Template cellTemplate = null; 
	
	/**
	 * root data for templates
	 */
	private Map<String, ReportProps> rootDataForReportTemplate = null;
	private Map<String, RowProps> rootDataForRowTemplate = null;
	private Map<String, CellProps> rootDataForCellTemplate = null; 
	
	
	/**
	 * the default templates class path
	 */
	public final static String DEFAULT_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/freemarker/html"; 
	public final static String START_REPORT_TEMPLATE = "startReport.ftl";
	public final static String END_REPORT_TEMPLATE = "endReport.ftl";
	public final static String START_ROW_TEMPLATE = "startRow.ftl";
	public final static String END_ROW_TEMPLATE = "endRow.ftl";
	public final static String CELL_TEMPLATE = "cell.ftl";
	
	
	/**
	 * output into memory ( using a StringWriter)
	 */
	public FreemarkerOutput(){
		super(); 
	}
	
	/**
	 * 
	 * @param filename
	 */
	public FreemarkerOutput(String filename){
		super(filename);
	}
	
	/**
	 * 
	 * @param fileName
	 * @param freemarkerConfig
	 */
	public FreemarkerOutput(String fileName, Configuration freemarkerConfig){
		super(fileName); 
		setFreemarkerConfig(freemarkerConfig); 
	}
	
	/**
	 * 
	 * @param writer
	 */
	public FreemarkerOutput(Writer writer){
		super(writer); 
	}
	
	/**
	 * 
	 * @param writer
	 * @param freemarkerConfig
	 */
	public FreemarkerOutput(Writer writer, Configuration freemarkerConfig){
		super(writer); 
		setFreemarkerConfig(freemarkerConfig); 
	}
	
	
	/**
	 * initializer for the default freemarker configuration
	 */
	protected void initFreemarkerDefaultConfig(){
		freemarkerConfig = new Configuration(); 
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		freemarkerConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), 
										DEFAULT_TEMPLATES_CLASS_PATH)); 
	}
	
	protected void initRootFreemarkerData(){
		//init root data
		rootDataForReportTemplate = new HashMap<String, ReportProps>();
		rootDataForCellTemplate = new HashMap<String, CellProps>(); 
		rootDataForRowTemplate = new HashMap<String, RowProps>();
	}
	
	/**
	 * 
	 */
	public void open() {
		super.open(); 
		try {
			if(freemarkerConfig == null){
				initFreemarkerDefaultConfig(); 
			}
			
			//init root hash maps
			initRootFreemarkerData(); 
			
			//get the templates from configuration
			initRootTemplateData();
			
			//process the start report template
			ReportProps reportProps = new ReportProps(); 
			rootDataForReportTemplate.put("reportProps", reportProps);
			startReportTemplate.process(rootDataForReportTemplate, getOutputWriter()); 
			
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} 
	}

	/**
	 * @throws IOException
	 */
	protected void initRootTemplateData() throws IOException {
		startReportTemplate = freemarkerConfig.getTemplate(START_REPORT_TEMPLATE); 
		endReportTemplate = freemarkerConfig.getTemplate(END_REPORT_TEMPLATE); 
		startRowTemplate = freemarkerConfig.getTemplate(START_ROW_TEMPLATE);
		endRowTemplate = freemarkerConfig.getTemplate(END_ROW_TEMPLATE); 
		cellTemplate = freemarkerConfig.getTemplate(CELL_TEMPLATE);
	}
	
	/**
	 * calls the start row template
	 */
	public void startRow(RowProps rowProperties) {
		super.startRow(rowProperties); 
		try {
			rootDataForRowTemplate.put("rowProps", rowProperties); 
			startRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}		
	}
	
	/**
	 * calls the end row template 
	 */
	public void endRow() {
		try {
			endRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	
	/**
	 * 
	 */
	public void output(CellProps cellProps) {
		try {
			rootDataForCellTemplate.put("cellProps", cellProps); 
			cellTemplate.process(rootDataForCellTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * calls the end report template and closes the writer
	 */
	public void close() {
		try {
			endReportTemplate.process(rootDataForReportTemplate, getOutputWriter());
			super.close(); //flush and close the writer
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} 
	}

	/**
	 * @return the freemarkerConfig
	 */
	public Configuration getFreemarkerConfig() {
		return freemarkerConfig;
	}

	/**
	 * @param freemarkerConfig the freemarkerConfig to set
	 */
	public void setFreemarkerConfig(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}
}
