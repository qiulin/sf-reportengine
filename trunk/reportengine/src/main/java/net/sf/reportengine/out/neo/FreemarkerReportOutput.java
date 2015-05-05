/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.sf.reportengine.out.ReportOutputException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

/**
 * @author dragos balan
 *
 */
public abstract class FreemarkerReportOutput extends AbstractReportOutput {
	
	/**
	 * the freemarker configuration
	 */
	private final Configuration fmConfig; 
	
	/**
	 * the output writer
	 */
	private final Writer writer; 
	

	/**
	 * 
	 * @param writer
	 * @param outFormat
	 */
	public FreemarkerReportOutput(Writer writer, OutputFormat outFormat){
		
		super(outFormat); 
		this.writer = writer;
		
		fmConfig= new Configuration(); 
		fmConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		fmConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), getTemplatesClasspath())); 
	}
	
	/**
	 * 
	 */
	public void output(String templateName, Map rootModel) {
		try {
			fmConfig.getTemplate(templateName).process(rootModel, writer);
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#open()
	 */
	public void open() {

	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#close()
	 */
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}
	}
	
	
	public abstract String getTemplatesClasspath();
	
	/**
	 * 
	 * @return
	 */
	Writer getWriter(){
		return writer;
	}
	
}
