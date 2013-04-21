/**
 * 
 */
package net.sf.reportengine.out;

import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML report output based on Stax technology (fast & low memory footprint)
 * The final xml should be : 
 * <pre>
 * 		<report>
 * 				<title>Title of the report</title>
 * 				<header>
 * 					<row>
 * 						<cell>Column header 1</cell>
 * 						<cell>Column header 2</cell>
 * 					</row>
 * 					<row>
 * 						...
 * 					</row>
 * 				<data>
 * 					<row>
 * 						<cell>Value 1</cell>
 * 						<cell>Value 2</cell>
 * 					</row>
 * 					<row>
 * 						...
 * 					</row>
 * 				</data>
 * 		</report>
 * </pre>
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class StaxReportOutput extends AbstractXmlOutput {
	
	/**
	 * 
	 */
	public static final String ENGINE_VERSION = "0.9";
	
	/**
	 * 
	 */
	private XMLStreamWriter xmlWriter;
	
	/**
	 * outputs into a string if no other writer is set
	 */
	public StaxReportOutput(){}
	
	/**
	 * outputs into a file
	 * @param outFileName
	 */
	public StaxReportOutput(String outFileName){
		super(outFileName); 
	}
	
	/**
	 * outputs into the specified writer
	 * @param writer
	 */
	public StaxReportOutput(Writer writer){
		super(writer);
	}
	
	/**
	 * 
	 */
	public void open(){
		markAsOpen();
		try{
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			xmlWriter = factory.createXMLStreamWriter(getOutputWriter());
			xmlWriter.writeStartDocument();
			xmlWriter.writeStartElement(TAG_REPORT);
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
	 * 
	 */
	public void outputTitle(TitleProps titleProps){
		try {
			xmlWriter.writeStartElement(TAG_TITLE);
			xmlWriter.writeCharacters(purifyData(titleProps.getTitle()));
			xmlWriter.writeEndElement();
		} catch (XMLStreamException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
     * new line
     */
    public void startRow(RowProps rowProperties) {
        try{
        	switch(rowProperties.getContent()){
        	case COLUMN_HEADER: 
        		xmlWriter.writeStartElement(TAG_TABLE_HEADER);
            	xmlWriter.writeAttribute(ATTR_ROW_NUMBER,""+rowProperties.getRowNumber());
        		break; 
        	default: 
        		xmlWriter.writeStartElement(TAG_ROW);
            	xmlWriter.writeAttribute(ATTR_ROW_NUMBER,""+rowProperties.getRowNumber());
        	}
        }catch(XMLStreamException streamExc){
        	throw new ReportOutputException(streamExc);
        }
    }
    
    /**
     * end a report line
     */
    public void endRow(){
        try{
        	xmlWriter.writeEndElement();
        }catch(XMLStreamException streamExc){
        	throw new RuntimeException(streamExc);
        }
    }
	
	/**
     * output
     */
    public void output(CellProps cellProps) {
    	try{
        	xmlWriter.writeStartElement(TAG_CELL);
        	xmlWriter.writeAttribute(ATTR_COLSPAN, ""+cellProps.getColspan());
        	xmlWriter.writeAttribute(ATTR_CONTENT_TYPE,""+cellProps.getContentType());
        	xmlWriter.writeAttribute(ATTR_HORIZ_ALIGN, cellProps.getHorizontalAlign().toString()); 
        	xmlWriter.writeCharacters(purifyData(cellProps.getValue()));
        	xmlWriter.writeEndElement();
        }catch(XMLStreamException streamException){
        	throw new RuntimeException(streamException);
        }
    }
}
