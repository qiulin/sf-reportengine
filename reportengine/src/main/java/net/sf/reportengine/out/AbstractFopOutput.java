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
 * @author dragos balan
 *
 */
public abstract class AbstractFopOutput implements ReportOutput{
	
	/**
	 * 
	 */
	private static final String DEFAULT_FO_CONF_PATH = "net/sf/reportengine/fop/fop.xconf";
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFopOutput.class);
	
	/**
	 * 
	 */
	private FoOutput foOutput; 
	
	/**
	 * 
	 */
	private OutputStream outputStream; 
	
	/**
	 * 
	 */
	private File tempFoFile;
	
	
	/**
	 * the fop configuration
	 */
	private Configuration fopConfiguration; 
	
	/**
	 * 
	 */
	public AbstractFopOutput(){
		this(new ByteArrayOutputStream()); 
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public AbstractFopOutput(String filePath){
		this(filePath, getDefaultConfiguration());
	}
	
	/**
	 * 
	 * @param filePath
	 * @param fopConfig
	 */
	public AbstractFopOutput(String filePath, Configuration fopConfig){
		this(ReportIoUtils.createOutputStreamFromPath(filePath), fopConfig); 
	}
	
	
	/**
	 * 
	 * @param out
	 */
	public AbstractFopOutput(OutputStream out){
		this(out, getDefaultConfiguration()); 
	}
	
	
	/**
	 * 
	 * @param out
	 */
	public AbstractFopOutput(OutputStream out, Configuration fopConfig){
		this.outputStream = out;
		this.fopConfiguration = fopConfig; 
	}
	
	/**
	 * 
	 * @return
	 */
	protected abstract String getMimeType();
	
	/**
	 * 
	 * @return
	 */
	private static Configuration getDefaultConfiguration(){
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
	 * 
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
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.ReportOutput#output(net.sf.reportengine.out.CellProps)
	 */
	public void outputDataCell(CellProps cellProps) {
		foOutput.outputDataCell(cellProps); 
	}
	
	public void endDataRow(){
		foOutput.endDataRow(); 
	}
	
	public void endReport(){
		foOutput.endReport(); 
	}
	
	public void close(){
		try {
			foOutput.close(); 
			transformFo(); 
			outputStream.close();
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
	}
	
	public void transformFo(){
    	try {
    		String mimeType = getMimeType(); 
    		LOGGER.info("transforming temporary fo file to {}", mimeType); 
    		
    		//DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
    		//Configuration configuration  = configBuilder.build(configInputStream); 
    	
    		FopFactory fopFactory = FopFactory.newInstance();
    		if(fopConfiguration != null){
    			fopFactory.setUserConfig(fopConfiguration);
    		}
    		
    		//unfortunately fop requires an outputStream 
    		Fop fop = fopFactory.newFop(mimeType, outputStream);
		
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			
			Source foSource = new StreamSource(ReportIoUtils.createReaderFromFile(tempFoFile)); 
			Result result = new SAXResult(fop.getDefaultHandler());
			transformer.transform(foSource, result); 
			
		}catch(TransformerConfigurationException e){
			throw new ReportOutputException(e);
		}catch(TransformerException e){
			throw new ReportOutputException(e);
    	}catch (FOPException e) {
    		throw new ReportOutputException(e);
//		} catch (ConfigurationException e) {
//			throw new ReportOutputException(e);
//		} catch (SAXException e) {
//			throw new ReportOutputException(e);
//		} catch (IOException e) {
//			throw new ReportOutputException(e);
		}finally{
			 
		}
    }
}
