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
 * <p> Output based on freemarker templates.</p>
 * 
 * This report output has a template for every step of the report: 
 * <ol>
 * 	<li>startReport.ftl - for the report start</li>
 * 	<li>title.ftl - outputting the title of the report </li>
 *  <li>startHeaderRow.ftl - for the start of every header row </li>
 *  <li>headerCell.ftl - for every cell in the header</li>
 *  <li>endHeaderRow.ftl - for the start of every header row </li>
 *  <li>startDataRow.ftl -  for the start of every row</li>
 *  <li>dataCell.ftl -  for the cell </li>
 *  <li>endDataRow.ftl - end of every data row</li>
 *  <li>endReport.ftl - end of the report</li>
 *  </ol>
 *  
 *  The default provided templates  will output html but you are strongly encouraged 
 *  to create your own templates for whatever format you like. Keep in mind that the template names 
 *  should remain the same but you can always change the location whether it's file system or classpath
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.7
 */
public class FreemarkerOutput extends AbstractCharBasedOutput {
	
	/**
	 * freemarker configuration class
	 */
	private Configuration freemarkerConfig = null;
	
	/**
	 * report templates
	 */
	private Template startReportTemplate = null; 
	private Template endReportTemplate = null; 
	
	
	private Template titleTemplate = null; 
	
	/**
	 * data row templates
	 */
	private Template startDataRowTemplate = null;
	private Template endDataRowTemplate = null; 
	
	/**
	 * data cell template
	 */
	private Template dataCellTemplate = null; 
	
	
	/**
	 * data row templates
	 */
	private Template startHeaderRowTemplate = null;
	private Template endHeaderRowTemplate = null; 
	
	/**
	 * data cell template
	 */
	private Template headerCellTemplate = null;
	
	
	/**
	 * root data for templates
	 */
	private Map<String, ReportProps> rootDataForReportTemplate = null;
	private Map<String, RowProps> rootDataForRowTemplate = null;
	private Map<String, CellProps> rootDataForCellTemplate = null; 
	private Map<String, TitleProps> rootDataForTitleTemplate = null; 
	
	/**
	 * the default templates class path
	 */
	public final static String DEFAULT_HTML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/freemarker/html"; 
	public final static String START_REPORT_TEMPLATE = "startReport.ftl";
	public final static String END_REPORT_TEMPLATE = "endReport.ftl";
	
	public final static String START_DATA_ROW_TEMPLATE = "startDataRow.ftl";
	public final static String END_DATA_ROW_TEMPLATE = "endDataRow.ftl";
	public final static String DATA_CELL_TEMPLATE = "dataCell.ftl";
	
	public final static String START_HEADER_ROW_TEMPLATE = "startHeaderRow.ftl";
	public final static String END_HEADER_ROW_TEMPLATE = "endHeaderRow.ftl";
	public final static String HEADER_CELL_TEMPLATE = "headerCell.ftl";
	
	public final static String TITLE_TEMPLATE = "title.ftl"; 
	
	/**
	 * Output into memory (using a StringWriter). 
	 * To change the default output writer one can use this constructor together 
	 * with {@link #setOutputWriter(Writer)} or {@link #setFilePath(String)}
	 */
	public FreemarkerOutput(){
	}
	
	/**
	 * Output into the specified file using Utf-8 encoding
	 * @param filePath	the path of the file
	 */
	public FreemarkerOutput(String filePath){
		super(filePath);
	}
	
	/**
	 * Output into the specified file using the provided freemarker configuration
	 * 
	 * @param filePath			the path of the output file
	 * @param freemarkerConfig	the freemarker configuration 
	 */
	public FreemarkerOutput(String filePath, Configuration freemarkerConfig){
		super(filePath); 
		setFreemarkerConfig(freemarkerConfig); 
	}
	
	/**
	 * output into the specified writer
	 * 
	 * @param writer	the output writer
	 */
	public FreemarkerOutput(Writer writer){
		super(writer); 
	}
	
	/**
	 * output into the specified writer using the configuration provided
	 * 
	 * @param writer			the output writer
	 * @param freemarkerConfig	the freemarker configuration
	 */
	public FreemarkerOutput(Writer writer, Configuration freemarkerConfig){
		super(writer); 
		setFreemarkerConfig(freemarkerConfig); 
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
	
	/**
	 * init for root freemarker data
	 */
	protected void initRootFreemarkerData(){
		//init root data
		rootDataForReportTemplate = new HashMap<String, ReportProps>();
		rootDataForCellTemplate = new HashMap<String, CellProps>(); 
		rootDataForRowTemplate = new HashMap<String, RowProps>();
		rootDataForTitleTemplate = new HashMap<String, TitleProps>(); 
	}
	
	/**
	 * opens the output by : 
	 * <ol>
	 * 		<li>creating the default freemarker configuration (if none was provided)</li>
	 * 		<li>creating the root data for the templates</li>
	 * 		<li>calling the startReport template</li>
	 * </ol>
	 */
	public void open() {
		markAsOpen(); 
		try {
			if(freemarkerConfig == null){
				freemarkerConfig = initFreemarkerDefaultConfig(); 
			}
			//init root hash maps
			initRootFreemarkerData(); 
			
			//get the templates from configuration
			initRootTemplateData();
			
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * initializes the root data for all freemarker templates
	 * @throws IOException
	 */
	protected void initRootTemplateData() throws IOException {
		startReportTemplate = freemarkerConfig.getTemplate(START_REPORT_TEMPLATE); 
		endReportTemplate = freemarkerConfig.getTemplate(END_REPORT_TEMPLATE); 
		
		titleTemplate = freemarkerConfig.getTemplate(TITLE_TEMPLATE); 
		
		startDataRowTemplate = freemarkerConfig.getTemplate(START_DATA_ROW_TEMPLATE);
		endDataRowTemplate = freemarkerConfig.getTemplate(END_DATA_ROW_TEMPLATE); 
		dataCellTemplate = freemarkerConfig.getTemplate(DATA_CELL_TEMPLATE);
		
		startHeaderRowTemplate = freemarkerConfig.getTemplate(START_HEADER_ROW_TEMPLATE);
		endHeaderRowTemplate = freemarkerConfig.getTemplate(END_HEADER_ROW_TEMPLATE); 
		headerCellTemplate = freemarkerConfig.getTemplate(HEADER_CELL_TEMPLATE); 
	}
	
	
	/**
	 *  calls the startReport.ftl template
	 */
	public void startReport(ReportProps reportProps){
		try {
			rootDataForReportTemplate.put("reportProps", reportProps);
			startReportTemplate.process(rootDataForReportTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * calls the title.ftl template
	 */
	public void outputTitle(TitleProps titleProps){
		try {
			rootDataForTitleTemplate.put("titleProps", titleProps); 
			titleTemplate.process(rootDataForTitleTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}
	}
	
	/**
	 * calls the startHeaderRow.ftl template with the given rowProperties
	 * 
	 * @param rowProperties row properties
	 */
	public void startHeaderRow(RowProps headerRowProps){
		try {
			rootDataForRowTemplate.put("rowProps", headerRowProps); 
			startHeaderRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}	
	}
	
	/**
	 * calls the headerCell.ftl template with the given cell properties
	 * 
	 * @param headerCellProps the header cell properties
	 */
	public void outputHeaderCell(CellProps headerCellProps){
		try {
			rootDataForCellTemplate.put("cellProps", headerCellProps); 
			headerCellTemplate.process(rootDataForCellTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * calls the endHeaderRow.ftl template
	 */
	public void endHeaderRow(){
		try {
			endHeaderRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * calls the startDataRow.ftl template
	 */
	public void startDataRow(RowProps rowProperties) {
		try {
			rootDataForRowTemplate.put("rowProps", rowProperties); 
			startDataRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}		
	}
	
	/**
	 * calls the dataCell.ftl template with the given cell properties
	 * 
	 * @param cellProps the data cell properties
	 */
	public void outputDataCell(CellProps cellProps) {
		try {
			rootDataForCellTemplate.put("cellProps", cellProps); 
			dataCellTemplate.process(rootDataForCellTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * calls the endDataRow.ftl template
	 */
	public void endDataRow() {
		try {
			endDataRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * calls the endReport.ftl template
	 */
	public void endReport(){
		try {
			endReportTemplate.process(rootDataForReportTemplate, getOutputWriter());
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
