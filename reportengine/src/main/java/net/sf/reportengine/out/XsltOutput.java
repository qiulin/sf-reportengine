/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.out;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.reportengine.util.ReportIoUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * XSLT based report output is created in two steps: <br/>
 * 
 * 1. Create a temporary XML report (STax) <br/> 
 * 2. Transform the temporary xml when closing the report
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class XsltOutput extends AbstractCharOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(XsltOutput.class);	
	/**
	 * class path of the default transformer
	 */
	private static final String DEFAULT_XSLT_PATH = "net/sf/reportengine/xslt/defaultTemplate.xslt"; 
	
    /**
     * the xslt reader
     */
    private Reader xsltReader;
    
    /**
     * the stax output
     */
    private StaxReportOutput staxReportOutput = new StaxReportOutput();
    
    /**
     * temp xml file
     */
    private String tempXmlFilePath;
    
    /**
     * output into a StringWriter (memory)
     */
    public XsltOutput(){}
    
    
    /**
     * output into the specified file 
     * 
     * @param outFilePath	the path of the output file
     */
    public XsltOutput(String outFilePath){
    	super(outFilePath); 
    }
    
    
    /**
     * output into the specified file using the xslt provided
     * 
     * @param filePath		the output file path
     * @param xsltReader	the xslt reader
     */
    public XsltOutput(String outFilePath, boolean append){
    	super(outFilePath, append); 
    	//null xslt reader
    }
    
    
    /**
     * output into the specified file using the xslt provided
     * 
     * @param filePath		the output file path
     * @param xsltReader	the xslt reader
     */
    public XsltOutput(String outFilePath, boolean append, Reader xsltReader){
    	super(outFilePath, append); 
    	setXsltReader(xsltReader); 
    }
    
    /**
     * output into the specified writer 
     * 
     * @param outputWriter
     */
    public XsltOutput(Writer outputWriter){
    	super(outputWriter);
    }
    
    
    /**
     * output into the specified writer using the xslt provided 
     * 
     * @param outputWriter
     * @param managedWriter	if true, the lifecycle of the writer is controlled by this class
     */
    public XsltOutput(Writer outputWriter, boolean managedWriter){
    	this(outputWriter, null, managedWriter); 
    }
    
    /**
     * output into the specified writer using the xslt provided 
     * 
     * @param outputWriter
     * @param xsltReader
     */
    public XsltOutput(Writer outputWriter, Reader xsltReader){
    	this(outputWriter, xsltReader, true); 
    }
    
    
    /**
     * output into the specified writer using the xslt provided 
     * 
     * @param outputWriter
     * @param xsltReader
     * @param managedWriter	if true, the lifecycle of the writer is controlled by this class
     */
    public XsltOutput(Writer outputWriter, Reader xsltReader, boolean managedWriter){
    	super(outputWriter, managedWriter); 
    	setXsltReader(xsltReader); 
    }
    
    
    /**
     * opens the writers
     */
    public void open(){
    	markAsOpen();
    	if(xsltReader == null){
    		LOGGER.debug("No xslt reader found ... creating default xslt reader from classpath"); 
    		setXsltReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(DEFAULT_XSLT_PATH)));
    	}
    	if(tempXmlFilePath == null){
    		File tempXmlFile = ReportIoUtils.createTempFile("report-xml");
    		
    		setTempXmlFilePath(tempXmlFile.getAbsolutePath());
    	}
    	staxReportOutput.setFilePath(getTempXmlFilePath());
    	
    	staxReportOutput.open();
    }
    
    public void startReport(ReportProps reportProps){
    	staxReportOutput.startReport(reportProps);
    }
    
    public void endReport(){
    	staxReportOutput.endReport(); 
    }
    
    public void outputTitle(TitleProps titleProps){
    	staxReportOutput.outputTitle(titleProps); 
    }
    
    public void startHeaderRow(RowProps rowProps){
    	staxReportOutput.startHeaderRow(rowProps); 
    }
    
    public void outputHeaderCell(CellProps cellProps){
    	staxReportOutput.outputHeaderCell(cellProps); 
    }
    
    public void endHeaderRow(){
    	staxReportOutput.endHeaderRow(); 
    }
    
    /**
     * 
     */
    public void close(){
    	try{
	    	staxReportOutput.close(); 
	    	transform();
	    	getXsltReader().close(); 
    	}catch(IOException ioExc){
    		throw new ReportOutputException(ioExc); 
    	}
    	super.close(); 
    }	
    
    /**
     * 
     */
    protected void transform(){
    	LOGGER.debug("applying xslt tranformation to the temporary xml file");
    	try{
    		//get the temporary xml file created and transform it
    		Source xmlSource = constructXmlSourceFromTempFile(); 
    		Result result = new StreamResult(getOutputWriter());
    	
    		Transformer transformer = TransformerFactory.newInstance()
    			.newTransformer(new StreamSource(getXsltReader()));
    		transformer.transform(xmlSource, result);
    		
    	}catch(TransformerConfigurationException exc){
    		throw new ReportOutputException(exc);
    	}catch(TransformerException exc){
    		throw new ReportOutputException(exc);
    	}
    }
    
    /**
     * 
     */
    public void startDataRow(RowProps rowProperties){
    	staxReportOutput.startDataRow(rowProperties);
    }
    
    /**
     * 
     */
    public void endDataRow(){
    	staxReportOutput.endDataRow();
    }
    
    /**
     * 
     */
    public void outputDataCell(CellProps cellProps){
    	staxReportOutput.outputDataCell(cellProps);
    }
    
    /**
     * 
     * @return
     */
    public Reader getXsltReader() {
        return xsltReader;
    }
    
    /**
     * 
     * @param xsltInputStream
     */
    public void setXsltReader(Reader xsltReader) {
        this.xsltReader = xsltReader;
    }
    
    
	/**
	 * @return the tempXmlFile
	 */
	public String getTempXmlFilePath() {
		return tempXmlFilePath;
	}

	/**
	 * @param tempXmlFile the tempXmlFile to set
	 */
	public void setTempXmlFilePath(String tempXmlPath) {
		this.tempXmlFilePath = tempXmlPath;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Source constructXmlSourceFromTempFile(){
		Source result = null; 
		if(tempXmlFilePath != null){
			result = new StreamSource(ReportIoUtils.createReaderFromPath(tempXmlFilePath));
		}else{
			LOGGER.warn("the construction of the xml source failed. No temporary xml file path was found"); 
		}
		return result; 
	}
}
