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
 * 		
 &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
	&lt;report engineVersion=&quot;0.9&quot;&gt;
		&lt;title&gt;Test flat report 2x3x1d&lt;/title&gt;
		&lt;header-row rowNumber=&quot;0&quot;&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Continent&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Direction&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Country&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Sex&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Age&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Count&lt;/data-cell&gt;
		&lt;/header-row&gt;
		&lt;data-row rowNumber=&quot;0&quot;&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Europe&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;North&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Sweden&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;Males&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;under 30&lt;/data-cell&gt;
			&lt;data-cell colspan=&quot;1&quot; horizAlign=&quot;CENTER&quot; vertAlign=&quot;MIDDLE&quot;&gt;1&lt;/data-cell&gt;
		&lt;/data-row&gt;
		&lt;data-row&gt; 
			...
	&lt;/report&gt;
 * </pre>
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class StaxReportOutput extends AbstractXmlOutput {
	
	/**
	 * the version of the engine
	 */
	private static final String ENGINE_VERSION = "0.9";
	
	/**
	 * the xml stream writer
	 */
	private XMLStreamWriter xmlWriter;
	
	/**
	 * outputs into a string writer (memory)
	 */
	public StaxReportOutput(){}
	
	/**
	 * output into the given file
	 * 
	 * @param outFilePath	the output file path
	 */
	public StaxReportOutput(String outFilePath){
		super(outFilePath); 
	}
	
	/**
	 * outputs into the specified writer
	 * 
	 * @param writer	the output writer
	 */
	public StaxReportOutput(Writer writer){
		super(writer);
	}
	
	/**
	 * opens the output
	 */
	public void open(){
		markAsOpen();
		try{
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			xmlWriter = factory.createXMLStreamWriter(getOutputWriter());
		}catch(XMLStreamException streamException){
			throw new RuntimeException(streamException);
		}
	}
	
	
	
	/**
	 * marks the start of the report by sending to the writer the "report" tag
	 */
	public void startReport(ReportProps reportProps){
		try{
			xmlWriter.writeStartDocument();
			xmlWriter.writeStartElement(TAG_REPORT);
			xmlWriter.writeAttribute(ATTR_ENGINE_VERSION, ENGINE_VERSION);
		}catch(XMLStreamException streamException){
			throw new RuntimeException(streamException);
		}
	}
	
	/**
	 * outputs the title
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
	 * writes the header row 
	 */
	public void startHeaderRow(RowProps rowProps){
		try{
        	xmlWriter.writeStartElement(TAG_HEADER_ROW);
            xmlWriter.writeAttribute(ATTR_ROW_NUMBER,""+rowProps.getRowNumber());
        }catch(XMLStreamException streamExc){
        	throw new ReportOutputException(streamExc);
        }
	}
	
	/**
	 * output the header cell
	 */
	public void outputHeaderCell(CellProps cellProps){
		outputDataCell(cellProps); 
	}
	
	/**
	 * ends the header row
	 */
	public void endHeaderRow(){
		endDataRow(); 
	}
	
	/**
     * starts the data row
     * 
     * @param rowProperties the properties of the current row
     */
    public void startDataRow(RowProps rowProperties) {
        try{
        	xmlWriter.writeStartElement(TAG_DATA_ROW);
            xmlWriter.writeAttribute(ATTR_ROW_NUMBER,""+rowProperties.getRowNumber());
        }catch(XMLStreamException streamExc){
        	throw new ReportOutputException(streamExc);
        }
    }
    
    /**
     * ends a data row
     */
    public void endDataRow(){
        try{
        	xmlWriter.writeEndElement();
        }catch(XMLStreamException streamExc){
        	throw new RuntimeException(streamExc);
        }
    }
	
	/**
     * output of a data cell
     * 
     * @param cellProps the cell properties
     */
    public void outputDataCell(CellProps cellProps) {
    	try{
        	xmlWriter.writeStartElement(TAG_DATA_CELL);
        	xmlWriter.writeAttribute(ATTR_COLSPAN, ""+cellProps.getColspan());
        	xmlWriter.writeAttribute(ATTR_HORIZ_ALIGN, cellProps.getHorizAlign().toString()); 
        	xmlWriter.writeAttribute(ATTR_VERT_ALIGN, cellProps.getVertAlign().toString()); 
        	xmlWriter.writeCharacters(purifyData(cellProps.getValue()));
        	xmlWriter.writeEndElement();
        }catch(XMLStreamException streamException){
        	throw new RuntimeException(streamException);
        }
    }
    
    /**
	 * ends this report by sending the end report tag
	 */
	public void endReport(){
		try{
			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
		}catch(XMLStreamException streamExc){
			throw new RuntimeException(streamExc);
		}
	}
    
    /**
	 * flushes the writer and closes it.
	 */
	public void close(){
		try{
			xmlWriter.flush();
			xmlWriter.close();
		}catch(XMLStreamException streamExc){
			throw new RuntimeException(streamExc);
		}
		super.close();
	}
}
