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
public class FreeMarkerOutput extends AbstractOutput {
	
	private Configuration freemarkerConfig = null;
	
	private Template startReportTemplate = null; 
	private Template endReportTemplate = null; 
	
	private Template startRowTemplate = null;
	private Template endRowTemplate = null; 
	
	private Template cellTemplate = null; 
	
	private TemplateLoader templateLoader = null; 
	
	/**
	 * 
	 */
	public FreeMarkerOutput(String filename){
		super(filename); 
		
		freemarkerConfig = new Configuration(); 
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		
		templateLoader = new ClassTemplateLoader(getClass(), "/net/sf/reportengine"); 
		freemarkerConfig.setTemplateLoader(templateLoader); 
	}
	
	/**
	 * 
	 */
	public void startRow(RowProps rowProperties) {
		try {
			startRowTemplate.process(rowProperties, getWriter());
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
			endRowTemplate.process(new HashMap(), getWriter());
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
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
	public void output(CellProps cellProps) {
		try {
			Map<String, CellProps> root = new HashMap<String, CellProps>(); 
			root.put("cellProps", cellProps); 
			cellTemplate.process(root, getWriter());
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
