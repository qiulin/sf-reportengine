/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.reportengine.out.ReportOutputException;
import net.sf.reportengine.util.ReportIoUtils;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author dragos balan
 *
 */
public class FopTransformerPostProcessor implements PostProcessor{
	
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FopTransformerPostProcessor.class);
	
	/**
	 * the default configuration class path
	 */
	private static final String DEFAULT_FO_CONF_PATH = "net/sf/reportengine/fop/fop.xconf";
	
	/**
	 * the fop configuration
	 */
	private final Configuration fopConfiguration;
	
	/**
	 * 
	 */
	private final String mimeType; 
	
	
	public FopTransformerPostProcessor(){
		this(MimeConstants.MIME_PDF, buildDefaultConfiguration()); 
	}
	
	
	public FopTransformerPostProcessor(String mimeType, Configuration fopConfig){
		this.fopConfiguration = fopConfig; 
		this.mimeType = mimeType; 
	}
	
	
	/**
	 * constructs and returns the default fop configuration
	 * 
	 * @return	the default fop configuration
	 */
	private static Configuration buildDefaultConfiguration(){
		DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
		Configuration configuration;
		try {
			configuration = configBuilder.build(ClassLoader.getSystemResourceAsStream(DEFAULT_FO_CONF_PATH));
		} catch (ConfigurationException e) {
			throw new ReportOutputException(e); 
		} catch (SAXException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
		
		return configuration; 
	}
	
	
	/**
	 * transforms the fo file into the requested mime type 
	 */
	public void process(File tempFoFile, OutputStream outputStream){
    	try {
    		LOGGER.info("transforming temporary fo file {} to {}", tempFoFile, mimeType); 
    		
    		FopFactory fopFactory = FopFactory.newInstance();
    		if(fopConfiguration != null){
    			fopFactory.setUserConfig(fopConfiguration);
    		}
    		
    		//unfortunately fop requires an outputStream 
    		Fop fop = fopFactory.newFop(mimeType, outputStream);
		
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			
			Source foSource = new StreamSource(ReportIoUtils.createReaderFromFile(tempFoFile)); 
			Result result = new SAXResult(fop.getDefaultHandler());
			transformer.transform(foSource, result); 
			
			LOGGER.info("succesful tansformation to {}", mimeType);
		}catch(TransformerConfigurationException e){
			throw new ReportOutputException(e);
		}catch(TransformerException e){
			throw new ReportOutputException(e);
    	}catch (FOPException e) {
    		throw new ReportOutputException(e);
		}
    }
}
