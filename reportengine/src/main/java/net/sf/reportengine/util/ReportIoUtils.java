/**
 * 
 */
package net.sf.reportengine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.in.ReportInputException;
import net.sf.reportengine.out.ReportOutputException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 * @since 0.7
 */
public final class ReportIoUtils {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportIoUtils.class);
	
	/**
	 * system file encoding 
	 */
	public final static String SYSTEM_FILE_ENCODING = System.getProperty("file.encoding"); 
	
	/**
	 * utf-8 encoding 
	 */
	public final static String UTF8_ENCODING =  "UTF-8"; 
	
	/**
	 * the default system line separator
	 */
	public final static String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * 
	 */
	private ReportIoUtils(){
		
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static Reader createReaderFromPath(String filePath){
		return createReaderFromPath(filePath, UTF8_ENCODING);
	}
	
	
	/**
	 * 
	 * @param filePath
	 * @param encoding
	 * @return
	 */
	public static Reader createReaderFromPath(String filePath, String encoding){
		try{
			return new InputStreamReader(	new FileInputStream(filePath), 
											encoding);
		}catch(FileNotFoundException fnf){
			throw new ReportInputException(fnf);
		}catch(UnsupportedEncodingException uee){
			throw new ReportInputException(uee);
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static Reader createReaderFromFile(File file){
		return createReaderFromFile(file, UTF8_ENCODING); 
	}
	
	/**
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 */
	public static Reader createReaderFromFile(File file, String encoding){
		try {
			return new InputStreamReader(new FileInputStream(file), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new ReportInputException(e); 
		} catch (FileNotFoundException e) {
			throw new ReportInputException(e); 
		} 
	}
	
	/**
	 * 
	 * @param filePath	the complete file path
	 * 
	 * @return
	 */
	public static Writer createWriterFromPath(String filePath){
		return createWriterFromPath(filePath, false, UTF8_ENCODING); 
	}
	
	/**
	 * 
	 * @param filePath	the complete file path
	 * @param append 	true if you want to create the file writer in append mode
	 * @return
	 */
	public static Writer createWriterFromPath(String filePath, boolean append){
		return createWriterFromPath(filePath, append, UTF8_ENCODING); 
	}
	
	/**
	 * 
	 * @param filePath
	 * @param encoding
	 * @return
	 */
	public static Writer createWriterFromPath(String filePath, boolean append, String encoding){
		try {
			return new OutputStreamWriter(	new FileOutputStream(filePath, append), 
											encoding);
		} catch (FileNotFoundException e) {
			throw new ReportOutputException(e); 
		} catch(UnsupportedEncodingException e){
			throw new ReportOutputException(e); 
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static Writer createWriterFromFile(File file){
		return createWriterFromFile(file, UTF8_ENCODING); 
	}
	
	
	/**
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 */
	public static Writer createWriterFromFile(File file, String encoding){
		try{
			return new OutputStreamWriter(new FileOutputStream(file), encoding); 
		} catch (FileNotFoundException e) {
			throw new ReportOutputException(e); 
		} catch(UnsupportedEncodingException e){
			throw new ReportOutputException(e); 
		}
	}
	
	/**
	 * 
	 * @param classpath
	 * @return
	 */
	public static Reader createReaderFromClassPath(String classpath){
		return createReaderFromClassPath(classpath, UTF8_ENCODING);
	}
	
	/**
	 * 
	 * @param classPath
	 * @param encoding
	 * @return
	 */
	public static Reader createReaderFromClassPath(String classPath, String encoding){
		try {
			return new InputStreamReader(
					ClassLoader.getSystemResourceAsStream(classPath), 
					encoding);
		} catch (UnsupportedEncodingException e) {
			throw new ReportEngineRuntimeException(e); 
		}
	}
	
	/**
	 * 
	 * @param classPath
	 * @return
	 */
	public static InputStream createInputStreamFromClassPath(String classPath){
		return ClassLoader.getSystemResourceAsStream(classPath); 
	}
	
	
	public static File createFileFromClassPath(String classPath){
		return new File(ReportIoUtils.class.getResource(classPath).getFile()); 
	}
	
	/**
	 * 
	 * @param filePath	the full path to the file
	 * @return
	 */
	public static FileOutputStream createOutputStreamFromPath(String filePath){
		return createOutputStreamFromPath(filePath, false); 
	}
	
	/**
	 * 
	 * @param filePath	the full path to the file
	 * @param append 	if true the resulting output stream will be created in append mode
	 * @return
	 */
	public static FileOutputStream createOutputStreamFromPath(String filePath, boolean append){
		try {
			return new FileOutputStream(filePath, append);
		} catch (FileNotFoundException e) {
			throw new ReportOutputException(e); 
		}
	}
	
	/**
	 * creates a temporary file which will be deleted on VM exit
	 * 
	 * @param prefix
	 * @param extension
	 * @return
	 */
	public static File createTempFile(String prefix, String extension){
		File tempFile;
		try {
			tempFile = File.createTempFile(prefix, extension);
			if(!ReportUtils.DEBUG){
				tempFile.deleteOnExit(); 
			}
			
			LOGGER.info("temporary file created on path {}", tempFile.getAbsolutePath()); 
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e); 
		}
		
		return tempFile; 
	}
	
	/**
	 * 
	 * @param prefix
	 * @return
	 */
	public static File createTempFile(String prefix){
		return createTempFile(prefix, ".tmp"); 
	}
	
	/**
	 * 
	 * @return
	 */
	public static File createTempFile(){
		return createTempFile("report"); 
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static FileInputStream createInputStreamFromFile(File file){
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ReportEngineRuntimeException(e); 
		} 
	}
}
