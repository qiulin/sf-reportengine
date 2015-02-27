/**
 * 
 */
package net.sf.reportengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.ReportOutputException;
import net.sf.reportengine.out.neo.NewReportOutput;
import freemarker.template.TemplateException;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportComponent<T> implements ReportComponent {
	
	/**
	 * the freemarker id of this component
	 */
	private final String fmId; 
	
	/**
	 * the freemarker template
	 */
	private final String templateName; 
	
	
	private final Map templateRootModel; 
	
	/**
	 * 
	 * @param freemarkerConfig
	 */
	public AbstractReportComponent(String fmId, String templateName){
		this.fmId = fmId; 
		this.templateName = templateName;
		this.templateRootModel = new HashMap<String, T>(); 
	}
	
	public void output(NewReportOutput reportOutput, T model){
		try {
			templateRootModel.put(fmId, model); 
			reportOutput.getFmConfig().getTemplate(templateName).process(templateRootModel, reportOutput.getWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}
	}
}
