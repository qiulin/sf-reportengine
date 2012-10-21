/*
 * Created on Sep 8, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.reportengine.in.ReportInputException;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

/**
 * Use this class for PDF, SVG, Postscript output. 
 * 
 * The Xsl-Fo report is done in two steps: <br/>
 * 
 * 1. Create temporary xml report (Stax) 
 * 2. Transform temporary xml according to the given xsl-fo template
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class XslFoOutput extends XsltReportOutput {
	
	/**
	 * 
	 */
	private static final String DEFAULT_XSLFO_PATH = "net/sf/reportengine/default-xml2fo.xslt";
	
	/**
	 * 
	 */
	private static final String DEFAULT_FO_CONF_PATH = "net/sf/reportengine/fop.xconf";
	
	/**
	 * the mime type of the report
	 */
	private String mimeType;
	
	/**
	 * holds the FOP configuration 
	 */
	private InputStream configInputStream; 
	
	
	/**
	 * 
	 */
	public XslFoOutput(){
		
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public XslFoOutput(String fileName){
		try{
			this.init(	new FileOutputStream(fileName), 
			        	MimeConstants.MIME_PDF, 
			        	ClassLoader.getSystemResourceAsStream(DEFAULT_XSLFO_PATH), 
			        	ClassLoader.getSystemResourceAsStream(DEFAULT_FO_CONF_PATH));
    	}catch(IOException e){
    		throw new ReportInputException(e); 
    	}
	}
	
	
	/**
	 * 
	 * @param outStream
	 */
    public XslFoOutput(OutputStream outStream) {
        this(outStream, MimeConstants.MIME_PDF);
    }
    
    /**
     * 
     * @param outStream
     * @param mimeType
     */
    public XslFoOutput(OutputStream outStream, String mimeType){
    	this(outStream,  
    		mimeType,
    		ClassLoader.getSystemResourceAsStream(DEFAULT_XSLFO_PATH));
    }
    
    /**
     * 
     * @param outStream
     * @param mimeType
     * @param xsltInputStream
     */
    public XslFoOutput(OutputStream outStream, String mimeType, InputStream xsltInputStream){
    	this(	outStream, 
    			mimeType, 
    			xsltInputStream, 
    			ClassLoader.getSystemResourceAsStream(DEFAULT_FO_CONF_PATH)); 
    }
    
    /**
     * 
     * @param outStream
     * @param mimeType
     * @param xsltInputStream
     * @param configInputStream
     */
    public XslFoOutput(	OutputStream outStream, 
    					String mimeType,
    					InputStream xsltInputStream, 
    					InputStream configInputStream) {
    	try{
    		this.init(	outStream, 
			        	mimeType, 
			        	xsltInputStream, 
			        	configInputStream);
    	}catch(IOException e){
    		throw new ReportInputException(e); 
    	}
    }
    
    /**
     * 
     * @param outStream
     * @param mimeType
     * @param xsltInputStream
     * @param configInputStream
     */
    protected void init(OutputStream outStream, 
    					String mimeType, 
    					InputStream xsltInputStream, 
    					InputStream configInputStream) throws IOException{
    	super.init(outStream, xsltInputStream);
    	setMimeType(mimeType); 
    	setFopConfigInputStream(configInputStream); 
    }
    
    @Override
    public void transform(){
    	try {
    		DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
    		Configuration configuration  = configBuilder.build(configInputStream); 
    	
    		FopFactory fopFactory = FopFactory.newInstance();
    		fopFactory.setUserConfig(configuration); 
    		
    		Fop fop = fopFactory.newFop(mimeType, getOutputStream());
		
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer(new StreamSource(getXsltInputStream()));
			
			Reader tempXmlReader = new InputStreamReader(new FileInputStream(getTempXmlFile()), "UTF-8"); 
			Source xmlSource = new StreamSource(tempXmlReader);
			Result result = new SAXResult(fop.getDefaultHandler());
			transformer.transform(xmlSource, result); 
			 
		}catch(TransformerConfigurationException e){
			throw new RuntimeException(e);
		}catch(TransformerException e){
			throw new RuntimeException(e);
    	}catch (FOPException e) {
			throw new RuntimeException(e);
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    @Override
    public void close(){
    	super.close(); 
    	IOUtils.closeQuietly(configInputStream);
    	//TODO make sure you've closed everything
    }
    
    
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	/**
	 * 
	 */
	public void setFopConfigInputStream(InputStream fopConfigStream){
		this.configInputStream = fopConfigStream; 
	}
	
	/**
	 * 
	 * @return
	 */
	public InputStream getFopConfigInputStream(){
		return configInputStream; 
	}

}
