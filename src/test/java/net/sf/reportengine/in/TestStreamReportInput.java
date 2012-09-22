/*
 * Created on 27.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.io.FileNotFoundException;

import net.sf.reportengine.test.ReportengineTC;
import net.sf.reportengine.util.ReportEngineArrayUtils;

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
        boolean flawless = true;
        try {
            classUnderTest = new StreamReportInput(getTestFileFromClasspath(TEST_FILE),",");
            
            int index = 0;
            Object[] data;
            classUnderTest.open();
            while (classUnderTest.hasMoreRows()) {
                data = classUnderTest.nextRow();
                boolean arraysAreEqual = ReportEngineArrayUtils.equalArrays(data, EXPECTED_RESULT[index++]);
                assertTrue(arraysAreEqual);
            }

            assertEquals(index, 25);
        } catch (Throwable exc) {
            flawless = false;
            exc.printStackTrace();
        }
        
        assertTrue(flawless);
        
    }

    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.hasMoreRows()'
     * PLEASE AVOID THIS TEST SINCE THIS SHOULD BE AN INFINITE LOOP
     */
//    public void testHasMoreRows() {
//        try{
//            System.out.println("test has more rows  ");
//            classUnderTest = new FileDataProvider(TEST_FILES[1]);
//            int rowsCount = 0;
//            // if you call this without nextRow() you will get an infinite loop 
//            while(classUnderTest.hasMoreRows() && rowsCount < Integer.MAX_VALUE){
//                //classUnderTest.nextRow();
//                rowsCount ++;
//            }
//        
//            assertEquals(rowsCount, Integer.MAX_VALUE);
//            
//        }catch(Exception exc){
//            exc.printStackTrace();
//        }
//        
//    }
    
    public void testHasMoreRows2() {
        int rowsCount = 0;
        boolean flawless = true;
        try{
            classUnderTest = new StreamReportInput(getTestFileFromClasspath(TEST_FILE));
            classUnderTest.open();
            while(classUnderTest.hasMoreRows()){
                classUnderTest.nextRow();
                rowsCount ++;
            }
        }catch(ReportInputException ioEx){
            ioEx.printStackTrace();
            flawless = false;
        }catch(Exception ex){
            ex.printStackTrace();
            flawless = false;
        }
        
        assertTrue(flawless);
        assertEquals(rowsCount, 25);
        
    }

    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.getColumnsCount()'
     */
    public void testGetColumnsCount1() {
        //System.out.println(" test get columns count");
        int result = -1;
        boolean flawless = true;
        try {
            classUnderTest = new StreamReportInput(getTestFileFromClasspath(TEST_FILE));
            classUnderTest.open();
            result = classUnderTest.getColumnsCount();
        } catch (Throwable e) {
            flawless = false;
            e.printStackTrace();
        } 
        assertTrue(flawless);
        assertEquals(result, 6);
    }
    
    /*
     * Test method for 'net.sf.reportengine.in.FileDataProvider.getColumnsCount()'
     */
    public void testGetColumnsCount2() {
        
        boolean flawless = true;
        int result= -1;
        try {
            classUnderTest = new StreamReportInput(getTestFileFromClasspath(TEST_FILE));
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
            flawless = false;
            e.printStackTrace();
        }
        
        assertTrue(flawless);
        assertEquals(result, 6);
    }
    
    public void testFirstRow(){
        //System.out.println(" test first");
        boolean flawless = true;
        
        try {
            classUnderTest = new StreamReportInput(getTestFileFromClasspath(TEST_FILE));
            classUnderTest.open();
            //classUnderTest.first();
            Object[] firstRow = classUnderTest.nextRow();
            assertTrue(ReportEngineArrayUtils.equalArrays(firstRow, EXPECTED_RESULT[0]));
            
            assertTrue(classUnderTest.hasMoreRows());
            Object[] secondRow = classUnderTest.nextRow();
            assertTrue(ReportEngineArrayUtils.equalArrays(secondRow, EXPECTED_RESULT[1]));
            
            
            assertTrue(classUnderTest.hasMoreRows());
            Object[] thirdRow = classUnderTest.nextRow();
            assertTrue(ReportEngineArrayUtils.equalArrays(thirdRow, EXPECTED_RESULT[2]));            
            
        } catch (ReportInputException e) {
            flawless = false;
            e.printStackTrace();
        }
        
        assertTrue(flawless);
    }
    
    public void testException(){
        boolean fnfExcThrown = false;
        String exceptionMessage = "";
        try {
            classUnderTest = new StreamReportInput("inexistent.txt");
            classUnderTest.open();
        }catch(FileNotFoundException fnfExc){
        	fnfExcThrown = true;
        } catch (ReportInputException e) {
            fnfExcThrown = false;            
        }
        
        assertTrue(fnfExcThrown);        
    }
    
    
    /**
     * testing hasMoreRows method for an empty file 
     */
    public void testHasMoreRowsOnEmptyFile(){
        boolean flawless = true;
        boolean hasMoreRows = true;
        try {
            classUnderTest = new StreamReportInput(getTestFileFromClasspath("empty.txt"));
            classUnderTest.open();
            hasMoreRows = classUnderTest.hasMoreRows();
        } catch (ReportInputException e) {
            flawless = false;
            e.printStackTrace();
        }
        
        assertTrue(flawless);
        assertFalse(hasMoreRows);
        
    }
    
    /**
     * testing nextRows method for an empty file 
     */
    public void testNextRowOnEmptyFile(){
        boolean flawless = true;
        Object[] nextRow = null;
        try {
            classUnderTest = new StreamReportInput(getTestFileFromClasspath("empty.txt"));
            classUnderTest.open();
            nextRow = classUnderTest.nextRow();
        } catch (ReportInputException e) {
            flawless = false;
            e.printStackTrace();
        }
        
        assertTrue(flawless);
        assertNull(nextRow);
        
    }
    
    /**
     * testing first method for an empty file 
     */
    public void testFirstOnEmptyFile(){
        boolean flawless = true;
        Object[] nextRow = null;
        boolean hasMoreRows = true;
        try {
            classUnderTest = new StreamReportInput(getTestFileFromClasspath("empty.txt"));
            classUnderTest.open();
            //classUnderTest.first();
            
            nextRow = classUnderTest.nextRow();
            assertNull(nextRow);
            
            hasMoreRows = classUnderTest.hasMoreRows();
            assertFalse(hasMoreRows);
        } catch (ReportInputException e) {
            flawless = false;
            e.printStackTrace();
        }
        
        assertTrue(flawless);       
    }
    

}
