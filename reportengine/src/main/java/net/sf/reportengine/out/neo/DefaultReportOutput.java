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
public class DefaultReportOutput extends NewReportOutput {
	
	/**
	 * the output format
	 */
	private final OutputFormat format; 
	
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
	 */
	public DefaultReportOutput(Writer writer){
		this(writer, new HtmlOutputFormat()); 
	}

	/**
	 * 
	 */
	public DefaultReportOutput(Writer writer, OutputFormat outFormat){
		
		this.writer = writer;
		this.format = outFormat; 
		
		fmConfig= new Configuration(); 
		fmConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		fmConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), format.getFormatTemplateClasspath())); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#open()
	 */
	public void open() {
		// TODO Auto-generated method stub

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

	public void output(String templateName, Map rootModel) {
		try {
			fmConfig.getTemplate(templateName).process(rootModel, writer);
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}
	}

	@Override
	public OutputFormat getFormat() {
		return format;
	}
	
}
