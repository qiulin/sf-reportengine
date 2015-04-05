/**
 * 
 */
package net.sf.reportengine.in;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.core.steps.NewRowEventWrapper;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestMultipleExternalSortedFilesInput {
	
	private List<File> testFiles; 
	private NewRowComparator testComparator; 
	
	
	@Before
	public void setUp(){
		testFiles = new ArrayList<File>(); 
		testFiles.add(ReportIoUtils.createFileFromClassPath("/ExternalSortedAndSerializedFile1.tmp"));
		testFiles.add(ReportIoUtils.createFileFromClassPath("/ExternalSortedAndSerializedFile2.tmp"));
		
		testComparator = new NewRowComparator(Scenario1.GROUPING_COLUMNS_WITH_SORTING, Scenario1.DATA_COLUMNS); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.in.MultipleExternalSortedFilesTableInput#nextRow()}.
	 */
	@Test
	public void testNextRow() {
		MultipleExternalSortedFilesTableInput classUnderTest = 
				new MultipleExternalSortedFilesTableInput(testFiles, testComparator);
		
		List<Object> newRow = classUnderTest.nextRow(); 
		assertNotNull(newRow); 
		assertTrue(Scenario1.ROW_OF_DATA_4.equals(newRow)); 
		
		newRow = classUnderTest.nextRow(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_5.equals(newRow));  
		
		newRow = classUnderTest.nextRow(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_3.equals(newRow)); 
		
		newRow = classUnderTest.nextRow(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_1.equals(newRow)); 
		
		newRow = classUnderTest.nextRow(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_2.equals(newRow)); 
		
		newRow = classUnderTest.nextRow(); 
		assertNotNull(newRow);
		assertTrue(Scenario1.ROW_OF_DATA_6.equals(newRow));  
		
		newRow = classUnderTest.nextRow(); 
		assertTrue(newRow == null);
		
		newRow = classUnderTest.nextRow(); 
		assertTrue(newRow == null);
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.MultipleExternalSortedFilesTableInput#hasMoreRows()}.
	 */
	@Test
	public void testHasMoreRows() {
		MultipleExternalSortedFilesTableInput classUnderTest = 
				new MultipleExternalSortedFilesTableInput(testFiles, testComparator);
		
		assertTrue(classUnderTest.hasMoreRows());
		classUnderTest.nextRow(); 
		
		assertTrue(classUnderTest.hasMoreRows());
		classUnderTest.nextRow(); 
		
		assertTrue(classUnderTest.hasMoreRows());
		classUnderTest.nextRow(); 
		
		assertTrue(classUnderTest.hasMoreRows());
		classUnderTest.nextRow(); 
		
		assertTrue(classUnderTest.hasMoreRows());
		classUnderTest.nextRow(); 
		
		assertTrue(classUnderTest.hasMoreRows());
		classUnderTest.nextRow(); 
		
		assertFalse(classUnderTest.hasMoreRows());
		assertFalse(classUnderTest.hasMoreRows());
		assertFalse(classUnderTest.hasMoreRows());
	}
	
	/**
	 * show the contents of the test file
	 * @throws Exception
	 */
	@Ignore
	public void caca() throws Exception{
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(testFiles.get(0)));
		NewRowEventWrapper obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		
		
		is = new ObjectInputStream(new FileInputStream(testFiles.get(1)));
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
	}

}
