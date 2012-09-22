/*
 * Created on Sep 8, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

/**
 * Xsl Fo based report done in two steps: <br/>
 * 1. Create temporary xml report (Stax) 
 * 2. Transform temporary xml according to the given xsl-fo template
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.3
 */
public class XslFoReportOutput extends XsltReportOutput {
	
	/**
	 * the mime type of the report
	 */
	private String mimeType;
	
    public XslFoReportOutput(OutputStream outStream) {
        this(outStream, MimeConstants.MIME_PDF);
    }
    
    public XslFoReportOutput(OutputStream outStream, String mimeType){
    	this(outStream,  
    		mimeType,
    		ClassLoader.getSystemResourceAsStream("net/sf/reportengine/default-xml2fo.xslt"));
    }
    
    public XslFoReportOutput(	OutputStream outStream, 
    							String mimeType,
    							InputStream xsltInputStream) {
        super(outStream, xsltInputStream);
        setMimeType(mimeType);
    }

    @Override
    public void transform(){
            
    	FopFactory fopFactory = FopFactory.newInstance();

    	Fop fop = null;
		try {
			fop = fopFactory.newFop(mimeType, getOutputStream());
		
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer(new StreamSource(getXsltInputStream()));
			
			Source xmlSource = new StreamSource(getTempXmlFile());
			//Result result = new StreamResult(new FileOutputStream("c:/reports/output.xml"));
			Result result = new SAXResult(fop.getDefaultHandler());
			transformer.transform(xmlSource, result); 
			
		}catch(TransformerConfigurationException e){
			throw new RuntimeException(e);
		}catch(TransformerException e){
			throw new RuntimeException(e);
    	}catch (FOPException e) {
			throw new RuntimeException(e);
		}
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

}
