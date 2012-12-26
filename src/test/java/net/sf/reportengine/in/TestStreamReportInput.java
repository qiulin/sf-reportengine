/*
 * Created on 27.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import net.sf.reportengine.test.ReportengineTC;

public class TestStreamReportInput extends ReportengineTC {
    
    
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
    
    private StreamReportInput classUnderTest;

    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.nextRow()'
     */
    public void testNextRow(){
        try {
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath(TEST_FILE),",");
            
            int index = 0;
            Object[] data;
            classUnderTest.open();
            while (classUnderTest.hasMoreRows()) {
                data = classUnderTest.nextRow();
                boolean arraysAreEqual = Arrays.equals(data, EXPECTED_RESULT[index++]);
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
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath(TEST_FILE));
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
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath(TEST_FILE));
            classUnderTest.open();
            result = classUnderTest.getColumnsCount();
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
        int result= -1;
        try {
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath(TEST_FILE));
            classUnderTest.open();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            classUnderTest.nextRow();
            
            result  = classUnderTest.getColumnsCount();
            
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
        
        assertEquals(result, 6);
    }
    
    public void testFirstRow(){
        try {
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath(TEST_FILE));
            classUnderTest.open();
            //classUnderTest.first();
            Object[] firstRow = classUnderTest.nextRow();
            assertTrue(Arrays.equals(firstRow, EXPECTED_RESULT[0]));
            
            assertTrue(classUnderTest.hasMoreRows());
            Object[] secondRow = classUnderTest.nextRow();
            assertTrue(Arrays.equals(secondRow, EXPECTED_RESULT[1]));
            
            
            assertTrue(classUnderTest.hasMoreRows());
            Object[] thirdRow = classUnderTest.nextRow();
            assertTrue(Arrays.equals(thirdRow, EXPECTED_RESULT[2]));            
            
        } catch (ReportInputException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }finally{
        	classUnderTest.close(); 
        }
    }
    
    public void testException(){
        try {
            classUnderTest = new StreamReportInput("inexistent.txt");
            classUnderTest.open();
            fail("an exception should have been thrown by the method above");
        } catch (ReportInputException e) {
        	assertEquals(e.getCause().getClass(), FileNotFoundException.class);
        }                
    }
    
    
    /**
     * testing hasMoreRows method for an empty file 
     */
    public void testHasMoreRowsOnEmptyFile(){
        boolean hasMoreRows = true;
        try {
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath("empty.txt"));
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
        Object[] nextRow = null;
        try {
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath("empty.txt"));
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
        Object[] nextRow = null;
        boolean hasMoreRows = true;
        try {
            classUnderTest = new StreamReportInput(getInputStreamFromClasspath("empty.txt"));
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
    	classUnderTest = new StreamReportInput(getInputStreamFromClasspath("Utf8Input.txt"), ",", "UTF-8");
    	classUnderTest.open(); 
    	Object[] row = null; 
    	while(classUnderTest.hasMoreRows()){
    		row  = classUnderTest.nextRow();
    		assertNotNull(row);
    		assertEquals(4, row.length);
    		linesCount++;
    		
    		if(linesCount == 1){
    			assertEquals("Действията", row[0]);
    		}else{
    			if(linesCount == 2){
    				assertEquals("и канализация са от", row[1]); 
    			}else{
    				if(linesCount == 6){
    					assertEquals("устойчиви резултати.", row[3]);
    				}
    			}
    		}
    	}
    	classUnderTest.close(); 
    	assertEquals(6,linesCount);
    }
    
    public void testSkipFirstLines(){
    	int rowsCount = 0;
    	InputStream inputStream = getInputStreamFromClasspath("2x3x1WithColumnHeaders.txt");
    	classUnderTest = new StreamReportInput(inputStream); 
    	classUnderTest.setSkipFirstLines(1);
    	classUnderTest.open(); 
    	
    	assertTrue(classUnderTest.hasMoreRows()); 
    	
    	while(classUnderTest.hasMoreRows()){
             classUnderTest.nextRow();
             rowsCount++;
        }
    	assertEquals(25, rowsCount);
    	
    	classUnderTest.close(); 
    }
    
    
    public void testSkipFirst2Lines(){
    	int rowsCount = 0;
    	InputStream inputStream = getInputStreamFromClasspath("2x3x1With2ColumnHeaders.txt");
    	classUnderTest = new StreamReportInput(inputStream); 
    	classUnderTest.setSkipFirstLines(2);
    	classUnderTest.open(); 
    	
    	assertTrue(classUnderTest.hasMoreRows()); 
    	
    	while(classUnderTest.hasMoreRows()){
             classUnderTest.nextRow();
             rowsCount++;
        }
    	assertEquals(25, rowsCount);
    	
    	classUnderTest.close(); 
    }
}
