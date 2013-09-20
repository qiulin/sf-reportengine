/**
 * 
 */
package net.sf.reportengine.out;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.reportengine.util.ReportIoUtils;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Abstract parent class for any fop based report output.
 * The final output is created in two steps : 
 * 	1. output to fo (as a temporary file)
 *  2. output to desired format (pdf, png ...) by transforming the temporary fo.
 *  
 * @author dragos balan
 */
public abstract class AbstractFopOutput implements ReportOutput{
	
	/**
	 * the default configuration class path
	 */
	private static final String DEFAULT_FO_CONF_PATH = "net/sf/reportengine/fop/fop.xconf";
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFopOutput.class);
	
	/**
	 * fo output
	 */
	private FoOutput foOutput; 
	
	/**
	 * the output stream
	 */
	private OutputStream outputStream; 
	
	/**
	 * the temporary fo file
	 */
	private File tempFoFile;
	
	
	/**
	 * the fop configuration
	 */
	private Configuration fopConfiguration; 
	
	/**
	 * default output into a {@code ByteArrayOutputStream}
	 */
	public AbstractFopOutput(){
		this(new ByteArrayOutputStream()); 
	}
	
	/**
	 * output into a file 
	 * 
	 * @param filePath	the path of the resulting output file
	 */
	public AbstractFopOutput(String filePath){
		this(filePath, buildDefaultConfiguration());
	}
	
	/**
	 * this outputs into a file with custom fop configuration
	 * 
	 * @param filePath		the output file path
	 * @param fopConfig		the custom fop configuration
	 */
	public AbstractFopOutput(String filePath, Configuration fopConfig){
		this(ReportIoUtils.createOutputStreamFromPath(filePath), fopConfig); 
		LOGGER.info("output into file {}", filePath); 
	}
	
	
	/**
	 * outputs into the given outputStream
	 * 
	 * @param out	the output stream
	 */
	public AbstractFopOutput(OutputStream out){
		this(out, buildDefaultConfiguration()); 
	}
	
	
	/**
	 * outputs into the output stream using a fop custom configuration
	 * 
	 * @param out the output stream 
	 * @param fopConfig the custom fop configuration
	 */
	public AbstractFopOutput(OutputStream out, Configuration fopConfig){
		this.outputStream = out;
		this.fopConfiguration = fopConfig; 
		LOGGER.info("output into stream "); 
	}
	
	/**
	 * 
	 * @return	the mime type of the result
	 */
	protected abstract String getMimeType();
	
	/**
	 * constructs and returns the default fop configuration
	 * 
	 * @return	the default fop configuration
	 */
	private static Configuration buildDefaultConfiguration(){
		DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
		Configuration configuration;
		try {
			configuration = configBuilder.build(ClassLoader.getSystemResourceAsStream(DEFAULT_FO_CONF_PATH));
		} catch (ConfigurationException e) {
			throw new ReportOutputException(e); 
		} catch (SAXException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
		
		return configuration; 
	}
	
	/**
	 * creates a temporary file for the fo content and opens the fo output
	 */
	public void open(){
		tempFoFile = ReportIoUtils.createTempFile("report-fo");
		foOutput = new FoOutput(ReportIoUtils.createWriterFromFile(tempFoFile));
		foOutput.open(); 
	}
	
	public void startReport(ReportProps reportProps){
		foOutput.startReport(reportProps); 
	}
	
	public void outputTitle(TitleProps titleProps){
		foOutput.outputTitle(titleProps); 
	}
	
	public void startHeaderRow(RowProps rowProps){
		foOutput.startHeaderRow(rowProps); 
	}
	
	public void outputHeaderCell(CellProps cellProps){
		foOutput.outputHeaderCell(cellProps); 
	}
	
	public void endHeaderRow(){
		foOutput.endHeaderRow(); 
	}
	
	public void startDataRow(RowProps rowProps){
		foOutput.startDataRow(rowProps); 
	}
	
	
	public void outputDataCell(CellProps cellProps) {
		foOutput.outputDataCell(cellProps); 
	}
	
	public void endDataRow(){
		foOutput.endDataRow(); 
	}
	
	public void endReport(){
		foOutput.endReport(); 
	}
	
	/**
	 * 1.closes the fo output
	 * 2. transforms the fo into the specific format(pdf, png, ...)
	 * 3. closes the outputStream 
	 */
	public void close(){
		try {
			foOutput.close(); 
			transformFo(); 
			outputStream.close();
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
	}
	
	/**
	 * transforms the fo file into the requested mime type 
	 */
	private void transformFo(){
    	try {
    		LOGGER.info("transforming temporary fo file to {}", getMimeType()); 
    		
    		FopFactory fopFactory = FopFactory.newInstance();
    		if(fopConfiguration != null){
    			fopFactory.setUserConfig(fopConfiguration);
    		}
    		
    		//unfortunately fop requires an outputStream 
    		Fop fop = fopFactory.newFop(getMimeType(), outputStream);
		
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			
			Source foSource = new StreamSource(ReportIoUtils.createReaderFromFile(tempFoFile)); 
			Result result = new SAXResult(fop.getDefaultHandler());
			transformer.transform(foSource, result); 
			
			LOGGER.info("succesful tansformation to {}", getMimeType());
		}catch(TransformerConfigurationException e){
			throw new ReportOutputException(e);
		}catch(TransformerException e){
			throw new ReportOutputException(e);
    	}catch (FOPException e) {
    		throw new ReportOutputException(e);
		}
    }
}
