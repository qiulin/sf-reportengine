/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
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
	private Map<String, RowProps> rootDataForRowTemplate = null;
	private Map<String, CellProps> rootDataForCellTemplate = null; 
	
	/**
	 * the template loader
	 */
	private TemplateLoader templateLoader = null; 
	
	/**
	 * 
	 * @param filename
	 */
	public FreemarkerOutput(String filename){
		super(filename); 
		
		freemarkerConfig = new Configuration(); 
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		
		templateLoader = new ClassTemplateLoader(getClass(), "/net/sf/reportengine"); 
		freemarkerConfig.setTemplateLoader(templateLoader); 
		
		//init root data
		rootDataForCellTemplate = new HashMap<String, CellProps>(); 
		rootDataForRowTemplate = new HashMap<String, RowProps>(); 
	}
	
	
	
	/**
	 * 
	 */
	public void open() {
		super.open(); 
		try {
			Map<String, String> root = new HashMap<String, String>(); 
			root.put("title", "this is the report title"); 
			
			startReportTemplate = freemarkerConfig.getTemplate("startReportTemplate.ftl"); 
			endReportTemplate = freemarkerConfig.getTemplate("endReportTemplate.ftl"); 
			
			startRowTemplate = freemarkerConfig.getTemplate("startRowTemplate.ftl");
			endRowTemplate = freemarkerConfig.getTemplate("endRowTemplate.ftl"); 
			
			cellTemplate = freemarkerConfig.getTemplate("cellTemplate.ftl");
			startReportTemplate.process(root, getWriter()); 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 */
	public void endRow() {
		try {
			endRowTemplate.process(rootDataForRowTemplate, getWriter());
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	public void close() {
		try {
			endReportTemplate.process(new HashMap(), getWriter());
			super.close(); 
			
			getWriter().flush(); 
			getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} 
	}
}
