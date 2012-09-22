/*
 * Created on Sep 1, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * XSLT based report output is created in two steps: <br/>
 * 
 * 1. Create a temporary XML report (STax) <br/> 
 * 2. Transform the temporary xml when closing the report
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @version $Revision$
 * $log$
 */
public class XsltReportOutput extends AbstractOutput {
    
    private InputStream xsltInputStream;
    private StaxReportOutput staxReportOutput;
    private File tempXmlFile; 
    
    public XsltReportOutput(OutputStream outStream){
        this(outStream, ClassLoader.getSystemResourceAsStream("net/sf/reportengine/defaultTemplate.xslt"));        
    }
    
    public XsltReportOutput(OutputStream outStream, InputStream xsltInputStream) {
        super(outStream);
        try{
        	this.xsltInputStream = xsltInputStream;
        	this.tempXmlFile = File.createTempFile("report", ".tmp");
        	this.staxReportOutput = new StaxReportOutput(new FileOutputStream(tempXmlFile));
        	//this.xmlTransformer = TransformerFactory.newInstance().newTransformer(new StreamSource(getXsltInputStream()));
        }catch(IOException exc){
        	throw new RuntimeException(exc);
        }
    }
    
    public void open(){
    	staxReportOutput.open();
    }
    
    public void close(){
    	staxReportOutput.close();
    	transform();
    }	
    
    protected void transform(){
    	try{
    		//get the temporary xml file created and transform it
    		Source xmlSource = new StreamSource(tempXmlFile);
    		Result result = new StreamResult(getOutputStream());
    	
    		Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(getXsltInputStream()));
    		transformer.transform(xmlSource, result);
    	}catch(TransformerConfigurationException exc){
    		throw new RuntimeException(exc);
    	}catch(TransformerException exc){
    		throw new RuntimeException(exc);
    	}
    }
    
    @Override
    public void startRow(){
    	staxReportOutput.startRow();
    }
    
    @Override
    public void endRow(){
    	staxReportOutput.endRow();
    }
    
    
    public void output(CellProps cellProps){
    	staxReportOutput.output(cellProps);
    }
    
    public InputStream getXsltInputStream() {
        return xsltInputStream;
    }

    public void setXsltInputStream(InputStream xsltInputStream) {
        this.xsltInputStream = xsltInputStream;
    }

	/**
	 * @return the tempXmlFile
	 */
	public File getTempXmlFile() {
		return tempXmlFile;
	}

	/**
	 * @param tempXmlFile the tempXmlFile to set
	 */
	public void setTempXmlFile(File tempXmlFile) {
		this.tempXmlFile = tempXmlFile;
	}
}
