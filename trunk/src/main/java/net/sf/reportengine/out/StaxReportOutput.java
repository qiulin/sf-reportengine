/**
 * 
 */
package net.sf.reportengine.out;

import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.reportengine.core.ReportContent;

/**
 * XML report output based on Stax technology ( fast & low memory footprint)
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.3
 */
public class StaxReportOutput extends AbstractXmlOutput {
	
	private XMLStreamWriter writer;
	
	public StaxReportOutput(OutputStream outputStream){
		super(outputStream);
		try{
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			writer = factory.createXMLStreamWriter(outputStream);
		}catch(XMLStreamException streamExc){
			throw new RuntimeException(streamExc);
		}
	}
	
	public void open(){
		super.open();
		try{
			writer.writeStartDocument();
			writer.writeStartElement(REPORT_TAG_NAME);
			writer.writeAttribute(ATTR_ENGINE_VERSION, "0.3");
		}catch(XMLStreamException streamException){
			throw new RuntimeException(streamException);
		}
	}
	
	public void close(){
		try{
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
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
        	writer.writeStartElement(REC_DETAILS_TAG_NAME);
        	writer.writeAttribute(ATTR_ROW_NUMBER,""+getRowCount());
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
        	writer.writeEndElement();
        }catch(XMLStreamException streamExc){
        	throw new RuntimeException(streamExc);
        }
    }
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.AbstractOutput#output(net.sf.reportengine.out.CellProps)
	 */
	@Override
	/**
     * output
     */
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
        	writer.writeStartElement(elementName);        
        	writer.writeAttribute(ATTR_COLSPAN, ""+cellProps.getColspan());
        	//writer.writeAttribute(ATTR_COLUMN_NUMBER,""+cellProps.getColCount());
        	writer.writeAttribute(ATTR_CONTENT_TYPE,""+cellProps.getContentType());
        	writer.writeCharacters(purifyData(cellProps.getValue()));
        	writer.writeEndElement();
        }catch(XMLStreamException streamException){
        	throw new RuntimeException(streamException);
        }
    }
}
