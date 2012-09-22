/*
 * Created on Oct 11, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

public class ReportengineTC extends TestCase {
    
      protected String TEST_REPORTS_PATH = "./target/";
      
      protected InputStream getTestFileFromClasspath(String file){
    	  return this.getClass().getClassLoader().getResourceAsStream(file);
      }
      
      protected OutputStream createTestOutputFile(String fileName) throws FileNotFoundException{
    	  return new FileOutputStream(TEST_REPORTS_PATH+fileName);
      }
}
