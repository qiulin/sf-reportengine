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

import org.apache.commons.io.IOUtils;

import net.sf.reportengine.core.ReportEngineRuntimeException;


/**
 * XSLT based report output is created in two steps: <br/>
 * 
 * 1. Create a temporary XML report (STax) <br/> 
 * 2. Transform the temporary xml when closing the report
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class XsltReportOutput implements IReportOutput {
    /**
     * 
     */
    private InputStream xsltInputStream;
    
    /**
     * 
     */
    private StaxReportOutput staxReportOutput;
    
    /**
     * 
     */
    private File tempXmlFile;
    
    /**
     * 
     */
    private OutputStream outputStream; 
    
    /**
     * 
     * @param outStream
     */
    public XsltReportOutput(OutputStream outStream){
        this(outStream, ClassLoader.getSystemResourceAsStream("net/sf/reportengine/defaultTemplate.xslt"));        
    }
    
    /**
     * 
     * @param outStream
     * @param xsltInputStream
     */
    public XsltReportOutput(OutputStream outStream, InputStream xsltInputStream) {
        try{
        	this.outputStream = outStream; 
        	this.xsltInputStream = xsltInputStream;
        	this.tempXmlFile = File.createTempFile("report", ".tmp");
        	this.staxReportOutput = new StaxReportOutput(new FileOutputStream(tempXmlFile));
        }catch(IOException exc){
        	throw new ReportEngineRuntimeException(exc);
        }
    }
    
    public void open(){
    	staxReportOutput.open();
    }
    
    public void close(){
    	staxReportOutput.close(); 
    	transform();
		IOUtils.closeQuietly(outputStream); 
    }	
    
    protected void transform(){
    	try{
    		//get the temporary xml file created and transform it
    		Source xmlSource = new StreamSource(tempXmlFile);
    		Result result = new StreamResult(outputStream);
    	
    		Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(getXsltInputStream()));
    		transformer.transform(xmlSource, result);
    	}catch(TransformerConfigurationException exc){
    		throw new RuntimeException(exc);
    	}catch(TransformerException exc){
    		throw new RuntimeException(exc);
    	}
    }
    
    public void startRow(){
    	staxReportOutput.startRow();
    }
    
    public void endRow(){
    	staxReportOutput.endRow();
    }
    
    
    public void output(CellProps cellProps){
    	staxReportOutput.output(cellProps);
    }
    
    public void setOuputStream(OutputStream output){
    	this.outputStream = output; 
    }
    
    public OutputStream getOutputStream(){
    	return this.outputStream; 
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
