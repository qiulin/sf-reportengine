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
public class FreemarkerOutput extends AbstractOutput {
	
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
	 * empty constructor
	 */
	public FreemarkerOutput(){
		
	}
	
	/**
	 * 
	 * @param filename
	 */
	public FreemarkerOutput(String filename){
		super(filename);
		initFreemarkerDefaultConfig(); 
		initRootFreemarkerData(); 
	}
	
	/**
	 * 
	 * @param fileName
	 * @param freemarkerConfig
	 */
	public FreemarkerOutput(String fileName, Configuration freemarkerConfig){
		super(fileName); 
		initRootFreemarkerData(); 
		this.freemarkerConfig = freemarkerConfig; 
	}
	
	/**
	 * 
	 * @param writer
	 */
	public FreemarkerOutput(Writer writer){
		super(writer); 
		initFreemarkerDefaultConfig(); 
		initRootFreemarkerData(); 
	}
	
	/**
	 * 
	 * @param writer
	 * @param freemarkerConfig
	 */
	public FreemarkerOutput(Writer writer, Configuration freemarkerConfig){
		super(writer); 
		initRootFreemarkerData(); 
		this.freemarkerConfig = freemarkerConfig; 
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
			//get the templates from classpath
			startReportTemplate = freemarkerConfig.getTemplate(START_REPORT_TEMPLATE); 
			endReportTemplate = freemarkerConfig.getTemplate(END_REPORT_TEMPLATE); 
			startRowTemplate = freemarkerConfig.getTemplate(START_ROW_TEMPLATE);
			endRowTemplate = freemarkerConfig.getTemplate(END_ROW_TEMPLATE); 
			cellTemplate = freemarkerConfig.getTemplate(CELL_TEMPLATE);
			
			//process the start report template
			ReportProps reportProps = new ReportProps(); 
			rootDataForReportTemplate.put("reportProps", reportProps);
			startReportTemplate.process(rootDataForReportTemplate, getWriter()); 
			
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * 
	 */
	public void startRow(RowProps rowProperties) {
		try {
			rootDataForRowTemplate.put("rowProps", rowProperties); 
			startRowTemplate.process(rootDataForRowTemplate, getWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}		
	}
	
	/**
	 * 
	 */
	public void endRow() {
		try {
			endRowTemplate.process(rootDataForRowTemplate, getWriter());
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
			cellTemplate.process(rootDataForCellTemplate, getWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * 
	 */
	public void close() {
		try {
			endReportTemplate.process(rootDataForReportTemplate, getWriter());
			super.close(); 
			
			getWriter().flush(); 
			getWriter().close();
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} 
	}
}
