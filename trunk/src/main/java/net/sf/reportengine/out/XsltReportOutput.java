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

import net.sf.reportengine.in.ReportInputException;

import org.apache.commons.io.IOUtils;


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
	private static final String DEFAULT_XSLT_PATH = "net/sf/reportengine/xslt/defaultTemplate.xslt"; 
	
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
     */
    public XsltReportOutput(){
    	
    }
    
    /**
     * 
     * @param outFileName
     */
    public XsltReportOutput(String outFileName){
    	try{
    		init(new FileOutputStream(outFileName), 
    			ClassLoader.getSystemResourceAsStream(DEFAULT_XSLT_PATH));
    	}catch(IOException e){
    		throw new ReportInputException(e); 
    	}
    }
    
    /**
     * 
     * @param outStream
     */
    public XsltReportOutput(OutputStream outStream){
    	try{
    		init(outStream, 
    			ClassLoader.getSystemResourceAsStream(DEFAULT_XSLT_PATH));
    	}catch(IOException e){
    		throw new ReportInputException(e); 
    	}
    }
    
    /**
     * 
     * @param outStream
     * @param xsltInputStream
     */
    public XsltReportOutput(OutputStream outStream, InputStream xsltInputStream) {
        try {
			init(outStream, xsltInputStream);
		} catch (IOException e) {
			throw new ReportInputException(e); 
		} 
    }
    
    /**
     * 
     * @param outStream
     * @param xsltInputStream
     * @throws IOException
     */
    protected void init(OutputStream outStream, InputStream xsltInputStream) throws IOException{
    	this.outputStream = outStream; 
    	this.xsltInputStream = xsltInputStream;
    	this.tempXmlFile = File.createTempFile("report", ".temp");
    	this.staxReportOutput = new StaxReportOutput(new FileOutputStream(tempXmlFile), "UTF-8");
    }
    
    /**
     * 
     */
    public void open(){
    	staxReportOutput.open();
    }
    
    /**
     * 
     */
    public void close(){
    	staxReportOutput.close(); 
    	transform();
		IOUtils.closeQuietly(outputStream); 
    }	
    
    /**
     * 
     */
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
    
    /**
     * 
     */
    public void startRow(RowProps rowProperties){
    	staxReportOutput.startRow(rowProperties);
    }
    
    /**
     * 
     */
    public void endRow(){
    	staxReportOutput.endRow();
    }
    
    /**
     * 
     */
    public void output(CellProps cellProps){
    	staxReportOutput.output(cellProps);
    }
    
    /**
     * 
     * @param output
     */
    public void setOuputStream(OutputStream output){
    	this.outputStream = output; 
    }
    
    /**
     * 
     * @return
     */
    public OutputStream getOutputStream(){
    	return this.outputStream; 
    }
    
    /**
     * 
     * @return
     */
    public InputStream getXsltInputStream() {
        return xsltInputStream;
    }
    
    /**
     * 
     * @param xsltInputStream
     */
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
