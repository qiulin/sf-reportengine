/*
 * Created on 27.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import net.sf.reportengine.util.ReportIoUtils;

public class TestTextReportInput extends TestCase {
    
    
    private String TEST_FILE = "2x3x1.txt";    
    
    private final Object[][] EXPECTED_RESULT = 
        new Object[][]{
           {"Europe","North","Sweden","Males","under 30","1"},
           {"Europe","North","Sweden","Males","between 31 and 60","2"},
           {"Europe","North","Sweden","Males","over 61","3"},
           {"Europe","North","Sweden","Females","under 30","4"},
           {"Europe","North","Sweden","Females","between 31 and 60","5"},
           {"Europe","North","Sweden","Females","over 61","6"},
           {"Europe","North","Finland","Males","under 30","7"},
           {"Europe","North","Finland","Males","between 31 and 60","8"},
           {"Europe","North","Finland","Males","over 61","9"},
           {"Europe","North","Finland","Females","under 30","10"},
           {"Europe","North","Finland","Females","between 31 and 60","11"},
           {"Europe","North","Finland","Females","over 61","12"},
           {"Europe","South","Italy","Males","under 30","13"},
           {"Europe","South","Italy","Males","between 31 and 60","14"},
           {"Europe","South","Italy","Males","over 61","15"},
           {"Europe","South","Italy","Females","under 30","16"},
           {"Europe","South","Italy","Females","under 30","17"},
           {"Europe","South","Italy","Females","between 31 and 60","18"},
           {"Europe","South","Italy","Females","over 61","19"},
           {"Asia","South","Thailand","Males","under 30","20"},
           {"Asia","South","Thailand","Males","between 31 and 60","21"},
           {"Asia","South","Thailand","Males","over 61","22"},
           {"Asia","South","Thailand","Females","under 30","23"},
           {"Asia","South","Thailand","Females","between 31 and 60","24"},
           {"Asia","South","Thailand","Females","over 61","25"},
    };
    
    private TextInput classUnderTest;

    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.nextRow()'
     */
    public void testNextRow(){
        try {
            classUnderTest = new TextInput();
            classUnderTest.setInputReader(ReportIoUtils.createReaderFromClassPath(TEST_FILE));
            classUnderTest.setSeparator(",");
            
            int index = 0;
            List<Object> data;
            classUnderTest.open();
            while (classUnderTest.hasMoreRows()) {
                data = classUnderTest.nextRow();
                boolean arraysAreEqual = Arrays.equals(EXPECTED_RESULT[index++], data.toArray(new Object[]{}));
                assertTrue(arraysAreEqual);
            }

            assertEquals(index, 25);
        } catch (Throwable exc) {
            exc.printStackTrace();
            fail(exc.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
        
        
    }

    public void testHasMoreRows() {
        int rowsCount = 0;
        try{
            classUnderTest = new TextInput(ReportIoUtils.createReaderFromClassPath(TEST_FILE));
            classUnderTest.open();
            while(classUnderTest.hasMoreRows()){
                classUnderTest.nextRow();
                rowsCount ++;
            }
        }catch(ReportInputException ioEx){
            ioEx.printStackTrace();
            fail(ioEx.getMessage());
        }catch(Exception ex){
            ex.printStackTrace();
            fail(ex.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
        
        assertEquals(rowsCount, 25);
    }

    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.getColumnsCount()'
     */
    public void testGetColumnsCount1() {
        int result = -1;
        try {
            classUnderTest = new TextInput(ReportIoUtils.createInputStreamFromClassPath((TEST_FILE)));
            classUnderTest.open();
            result = classUnderTest.getColumnMetadata().size();
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage()); 
        } finally{
        	classUnderTest.close(); 
        }
        assertEquals(result, 6);
    }
    
    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.getColumnsCount()'
     */
    public void testGetColumnsCount2() {
        
        try {
            classUnderTest = new TextInput(ReportIoUtils.createInputStreamFromClassPath(TEST_FILE));
            classUnderTest.open();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            
            assertEquals(classUnderTest.getColumnMetadata().size(), 6);
            
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
    }
    
   
    
    
    public void testFirstRow(){
        try {
            classUnderTest = new TextInput();
            classUnderTest.setInputReader(ReportIoUtils.createReaderFromClassPath(TEST_FILE));
            
            classUnderTest.open();
            //classUnderTest.first();
            List<Object> firstRow = classUnderTest.nextRow();
            assertTrue(firstRow.equals(Arrays.asList(EXPECTED_RESULT[0])));
            
            assertTrue(classUnderTest.hasMoreRows());
            List<Object> secondRow = classUnderTest.nextRow();
            assertTrue(secondRow.equals(Arrays.asList(EXPECTED_RESULT[1])));
            
            
            assertTrue(classUnderTest.hasMoreRows());
            List<Object> thirdRow = classUnderTest.nextRow();
            assertTrue(thirdRow.equals(Arrays.asList(EXPECTED_RESULT[2])));            
            
        } catch (ReportInputException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
    }
    
    public void testExceptionNonExistentFile(){
        try {
            classUnderTest = new TextInput("inexistent.txt");
            classUnderTest.open();
            fail("an exception should have been thrown by the method above");
        } catch (ReportInputException e) {
        	assertEquals(e.getCause().getClass(), FileNotFoundException.class);
        }                
    }
    
    
    public void testExceptionNoWriterSet(){
        try {
            classUnderTest = new TextInput();
            classUnderTest.open();
            fail("an exception should have been thrown by the method above");
        } catch (ReportInputException e) {
        	assertEquals(TextInput.NO_WRITER_SET_ERROR_MESSAGE, e.getMessage());
        }                
    }
    
    /**
     * testing hasMoreRows method for an empty file 
     */
    public void testHasMoreRowsOnEmptyFile(){
        boolean hasMoreRows = true;
        try {
            classUnderTest = new TextInput(ReportIoUtils.createInputStreamFromClassPath("empty.txt"));
            classUnderTest.open();
            hasMoreRows = classUnderTest.hasMoreRows();
        } catch (ReportInputException e) {
            e.printStackTrace();
            fail(e.getMessage()); 
        }finally{
        	classUnderTest.close(); 
        }
        
        assertFalse(hasMoreRows);
        
    }
    
    /**
     * testing nextRows method for an empty file 
     */
    public void testNextRowOnEmptyFile(){
        List<Object> nextRow = null;
        try {
            classUnderTest = new TextInput(ReportIoUtils.createInputStreamFromClassPath("empty.txt"));
            classUnderTest.open();
            nextRow = classUnderTest.nextRow();
        } catch (ReportInputException e) {
            e.printStackTrace();
            fail(e.getMessage()); 
        }finally{
        	classUnderTest.close(); 
        }
        
        assertNull(nextRow);
        
    }
    
    /**
     * testing first method for an empty file 
     */
    public void testFirstOnEmptyFile(){
        List<Object> nextRow = null;
        boolean hasMoreRows = true;
        try {
            classUnderTest = new TextInput(ReportIoUtils.createInputStreamFromClassPath("empty.txt"));
            classUnderTest.open();
            //classUnderTest.first();
            
            nextRow = classUnderTest.nextRow();
            assertNull(nextRow);
            
            hasMoreRows = classUnderTest.hasMoreRows();
            assertFalse(hasMoreRows);
            
        } catch (ReportInputException e) {
            e.printStackTrace();
            fail("An error occured "+e.getMessage()); 
        }finally{
        	classUnderTest.close(); 
        }
    }
    
    
    public void testUtf8Characters(){
    	int linesCount = 0;
    	classUnderTest = new TextInput(ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt"), ",", "UTF-8");
    	classUnderTest.open(); 
    	List<Object> row = null; 
    	while(classUnderTest.hasMoreRows()){
    		row  = classUnderTest.nextRow();
    		assertNotNull(row);
    		assertEquals(4, row.size());
    		linesCount++;
    		
    		if(linesCount == 1){
    			assertEquals("Действията", row.get(0));
    		}else{
    			if(linesCount == 2){
    				assertEquals("и канализация са от", row.get(1)); 
    			}else{
    				if(linesCount == 6){
    					assertEquals("устойчиви резултати.", row.get(3));
    				}
    			}
    		}
    	}
    	classUnderTest.close(); 
    	assertEquals(6,linesCount);
    }
    
    public void testSkipFirstLines(){
    	int rowsCount = 0;
    	InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("2x3x1WithColumnHeaders.txt");
    	classUnderTest = new TextInput(inputStream); 
    	classUnderTest.setFirstLineHeader(true);
    	classUnderTest.open(); 
    	
    	assertTrue(classUnderTest.hasMoreRows()); 
    	
    	while(classUnderTest.hasMoreRows()){
             List<Object> row = classUnderTest.nextRow();
             assertNotNull(row); 
             assertEquals(6, row.size()); 
             
             rowsCount++;
        }
    	assertEquals(25, rowsCount);
    	assertNotNull(classUnderTest.getColumnMetadata()); 
    	assertEquals(6, classUnderTest.getColumnMetadata().size()); 
    	
    	classUnderTest.close(); 
    }
}
