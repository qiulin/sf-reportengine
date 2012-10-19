/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.reportengine.in.ReportInputException;

/**
 * XML report output based on Stax technology (fast & low memory footprint)
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class StaxReportOutput extends AbstractXmlOutput {
	
	/**
	 * 
	 */
	public static final String ENGINE_VERSION = "0.4";
	
	/**
	 * 
	 */
	private XMLStreamWriter xmlWriter;
	
	/**
	 * 
	 */
	public StaxReportOutput(){
		try{
			init(new OutputStreamWriter(new FileOutputStream("StaxReportOutput.xml"), 
					System.getProperty("file.encoding")));
		}catch(IOException e){
			throw new ReportInputException(e); 
		}
	}
	
	/**
	 * 
	 * @param outFileName
	 */
	public StaxReportOutput(String outFileName){
		try{
			init(new OutputStreamWriter(new FileOutputStream(outFileName), 
					System.getProperty("file.encoding"))); 
		}catch(IOException e){
			throw new ReportInputException(e); 
		}
	}
	
	/**
	 * 
	 * @param outStream
	 */
	public StaxReportOutput(OutputStream outStream){
		try{
			init(new OutputStreamWriter(outStream, System.getProperty("file.encoding")));
		}catch(IOException e){
			throw new ReportInputException(e); 
		}
	}
	
	/**
	 * 
	 * @param outStream
	 * @param encoding
	 */
	public StaxReportOutput(OutputStream outStream, String encoding){
		try{
			init(new OutputStreamWriter(outStream, encoding));
		}catch(IOException e){
			throw new ReportInputException(e); 
		}
	}
	
	/**
	 * 
	 * @param writer
	 */
	public StaxReportOutput(Writer writer){
		init(writer);
	}
	
	/**
	 * 
	 * @param writer
	 */
	private void init(Writer writer){
		try{
			setWriter(writer);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			this.xmlWriter = factory.createXMLStreamWriter(getWriter());
		}catch(XMLStreamException streamExc){
			throw new RuntimeException(streamExc);
		}
	}
	
	/**
	 * 
	 */
	public void open(){
		super.open();
		try{
			xmlWriter.writeStartDocument();
			xmlWriter.writeStartElement(REPORT_TAG_NAME);
			xmlWriter.writeAttribute(ATTR_ENGINE_VERSION, ENGINE_VERSION);
		}catch(XMLStreamException streamException){
			throw new RuntimeException(streamException);
		}
	}
	
	/**
	 * 
	 */
	public void close(){
		try{
			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
			xmlWriter.flush();
			xmlWriter.close();
		}catch(XMLStreamException streamExc){
			throw new RuntimeException(streamExc);
		}
		super.close();
	}
	
	/**
     * new line
     */
    public void startRow() {
        super.startRow();
        try{
        	xmlWriter.writeStartElement(REC_DETAILS_TAG_NAME);
        	xmlWriter.writeAttribute(ATTR_ROW_NUMBER,""+getRowCount());
        }catch(XMLStreamException streamExc){
        	throw new RuntimeException(streamExc);
        }
    }
    
    /**
     * end up a line
     */
    public void endRow(){
        super.endRow();
        try{
        	xmlWriter.writeEndElement();
        }catch(XMLStreamException streamExc){
        	throw new RuntimeException(streamExc);
        }
    }
	
	
	
	/**
     * output
     */
    @Override
    public void output(CellProps cellProps) {
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
        try{
        	xmlWriter.writeStartElement(elementName);        
        	xmlWriter.writeAttribute(ATTR_COLSPAN, ""+cellProps.getColspan());
        	//xmlWriter.writeAttribute(ATTR_COLUMN_NUMBER,""+cellProps.getColCount());
        	xmlWriter.writeAttribute(ATTR_CONTENT_TYPE,""+cellProps.getContentType());
        	xmlWriter.writeCharacters(purifyData(cellProps.getValue()));
        	xmlWriter.writeEndElement();
        }catch(XMLStreamException streamException){
        	throw new RuntimeException(streamException);
        }
    }
}
