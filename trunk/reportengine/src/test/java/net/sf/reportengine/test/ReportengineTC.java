/*
 * Created on Oct 11, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import junit.framework.TestCase;

public class ReportengineTC extends TestCase {
    
      protected String TEST_REPORTS_PATH = "./target/";
      
      /**
       * 
       * @param file
       * @return
       * @deprecated use ReportIoUtils.createInputStreamFromClassPath instead
       */
      protected InputStream getInputStreamFromClasspath(String file){
    	  return this.getClass().getClassLoader().getResourceAsStream(file);
      }
      
      /**
       * 
       * @param path
       * @return
       * @deprecated use ReportIoUtils.createReaderFromClassPath instead
       */
      protected Reader getReaderFromClasspath(String path){
    	  return new InputStreamReader(getInputStreamFromClasspath(path)); 
      }
      
      
      protected OutputStream createTestOutputFile(String fileName) {
    	OutputStream result = null; 
    	try {
			result = new FileOutputStream(TEST_REPORTS_PATH+fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
    	return result; 
      }
      
      protected Writer createTestWriter(String fileName){
    	  Writer result = null; 
    	  
    	  try {
    		  result = new OutputStreamWriter(new FileOutputStream(TEST_REPORTS_PATH+fileName), "UTF-8");
    	  } catch (IOException e) {
			throw new RuntimeException(e);
    	  } 
    	  
    	  return result; 
      }
}
