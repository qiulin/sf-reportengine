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
	public static final String ENGINE_VERSION = "0.6";
	
	/**
	 * 
	 */
	private XMLStreamWriter xmlWriter;
	
	/**
	 * 
	 */
	public StaxReportOutput(){
		
	}
	
	/**
	 * 
	 * @param outFileName
	 */
	public StaxReportOutput(String outFileName){
		try{
			init(new OutputStreamWriter(
						new FileOutputStream(outFileName), 
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
     * new line
     */
    @Override public void startRow(RowProps rowProperties) {
        super.startRow(rowProperties);
        try{
        	switch(rowProperties.getContent()){
        	case CONTENT_REPORT_TITLE: 
        		xmlWriter.writeStartElement(TAG_TITLE);
        		break; 
        	case CONTENT_COLUMN_HEADERS: 
        		xmlWriter.writeStartElement(TAG_TABLE_HEADER);
            	xmlWriter.writeAttribute(ATTR_ROW_NUMBER,""+getRowCount());
        		break; 
        	default: 
        		xmlWriter.writeStartElement(TAG_ROW);
            	xmlWriter.writeAttribute(ATTR_ROW_NUMBER,""+getRowCount());
        	}
        }catch(XMLStreamException streamExc){
        	throw new RuntimeException(streamExc);
        }
    }
    
    /**
     * end a report line
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
    @Override public void output(CellProps cellProps) {
    	try{
	        switch (cellProps.getContentType()) {
	            case CONTENT_REPORT_TITLE :
	            	xmlWriter.writeCharacters(purifyData(cellProps.getValue()));
	                break;
	            default:
	            	xmlWriter.writeStartElement(TAG_CELL);
	            	xmlWriter.writeAttribute(ATTR_COLSPAN, ""+cellProps.getColspan());
	            	xmlWriter.writeAttribute(ATTR_CONTENT_TYPE,""+cellProps.getContentType());
	            	xmlWriter.writeAttribute(ATTR_HORIZ_ALIGN, cellProps.getHorizontalAlign().toString()); 
	            	xmlWriter.writeCharacters(purifyData(cellProps.getValue()));
	            	xmlWriter.writeEndElement();
	        }
        }catch(XMLStreamException streamException){
        	throw new RuntimeException(streamException);
        }
    }
}
