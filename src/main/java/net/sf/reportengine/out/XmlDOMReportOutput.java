/*
 * Created on Sep 8, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.reportengine.core.ReportConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlDOMReportOutput extends AbstractXmlReportOutput {
    
    /**
     *  xml document
     */
    private Document document = null;

    /**
     * the root element
     */
    private Element root = null;

    /**
     * the row element
     */
    private Element rowElement = null;
    
    
    public XmlDOMReportOutput(OutputStream stream){
        super(stream);
        try{
            DocumentBuilderFactory docBuilderFactory = 
                DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            document = docBuilder.newDocument();  
            
        }catch(Exception exc){
            throw new RuntimeException(exc);
        }        
    }
    
    /**
     * opens the report
     */    
    public void open() {
        super.open();
        root = document.createElement(REPORT_TAG_NAME);
        root.setAttribute(ATTR_ENGINE_VERSION,"0.3");
        document.appendChild(root);
    }
    
    /**
     * new line
     */
    public void startRow() {
        super.startRow();
        rowElement = document.createElement(REC_DETAILS_TAG_NAME);
        rowElement.setAttribute(ATTR_ROW_NUMBER,""+getRowCount());
    }
    
    /**
     * end up a line
     */
    public void endRow(){
        super.endRow();
        if (rowElement != null) {
            root.appendChild(rowElement);
        }else{
            System.err.println("row element is null ");
        }
    }
    
    /**
     * output
     */
    public void output(CellProps cellProps) {
        Element dataElement = null;
        String elementName = null;
        switch (cellProps.getContentType()) {
            case CONTENT_REPORT_TITLE :
                elementName = "title";
                break;
            case CONTENT_DATA:
                elementName = DATA_TAG_NAME;
                break;
            case CONTENT_COLUMN_HEADERS:
                elementName = TABLE_HEADER_TAG_NAME;
                break;
            case CONTENT_ROW_HEADER:
                elementName = REC_DETAILS_HEADER_TAG_NAME;            
                break;
            default:
                throw new IllegalArgumentException(
                        " content parameter " + cellProps.getContentType()+ 
                        " must be one specified in ReportConstants.CONTENT_XXX ");
        }
        
        dataElement = document.createElement(elementName);        
        dataElement.setAttribute(ATTR_COLSPAN, ""+cellProps.getColspan());
        //dataElement.setAttribute(ATTR_COLUMN_NUMBER,""+cellProps.getColCount());
        dataElement.setAttribute(ATTR_CONTENT_TYPE,""+cellProps.getContentType());
        String value = purifyData(cellProps.getValue());
        
        Node text = document.createTextNode(value);
        dataElement.appendChild(text);
        rowElement.appendChild(dataElement);
    }
    
    public Source getXmlSource(){
    	return new DOMSource(document);
    }
    
    public Transformer getXmlTransformer(){
    	Transformer result = null;
    	try{
    		result = TransformerFactory.newInstance().newTransformer();
    	}catch(TransformerConfigurationException configExc){
    		throw new RuntimeException(configExc);
    	}
    	return result;
    }
    
    /**
     * closes the report
     */
    public void close() {
        try {
            transformXml();
            getOutputStream().close();
            super.close();        
        } catch (Exception exc) {
        	exc.printStackTrace();
            throw new ReportOutputException("Error closing the xml output ", exc);
        }
    }
	
    
    protected void transformXml() 
    	throws  	TransformerConfigurationException, 
    				TransformerException, 
    				FileNotFoundException {
    	Source source = getXmlSource();
    	Transformer transformer = getXmlTransformer();
    	Result result = getTransformationResult(); 
    	transformer.transform(source, result);         
    }
    
    public Result getTransformationResult(){
    	return new StreamResult(getOutputStream());
    }
}
