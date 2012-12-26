/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import junit.framework.TestCase;

/**
 * @author dragos balan
 *
 */
public class TestTxtOutput extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link net.sf.reportengine.out.TxtOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOpenWhenNoWriterSet() {
		TxtOutput classUnderTest = new TxtOutput(); 
		classUnderTest.open();
		classUnderTest.startRow(new RowProps()); 
		classUnderTest.output(new CellProps.Builder("value x").build()); 
		classUnderTest.endRow(); 
		classUnderTest.close(); 
		
		Writer result = classUnderTest.getOutputWriter(); 
		assertNotNull(result); 
		assertTrue(result instanceof StringWriter);
		StringBuffer resultBuffer = ((StringWriter)result).getBuffer();  
		assertNotNull(resultBuffer); 
		assertTrue(resultBuffer.indexOf("value x") > 0);
	}
	
	/**
	 * 
	 */
	public void testStartRowWithoutOpen(){
		try{
			TxtOutput classUnderTest = new TxtOutput(); 
			classUnderTest.startRow(new RowProps()); 
			fail("an exception should have been thrown"); 
		}catch(ReportOutputException roe){
			assertEquals(TxtOutput.OUTPUT_NOT_OPEN, roe.getMessage()); 
		}
	}
	
	/**
	 * 
	 */
	public void testOutputWhenFilenameSet(){
		TxtOutput classUnderTest = new TxtOutput(); 
		classUnderTest.setFilePath("target/streamOutputHavingFilenameSet.txt");
		
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 1").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close(); 
	}
	
	public void testOutputWhenWriterSet(){
		TxtOutput classUnderTest = new TxtOutput(); 
		try {
			classUnderTest.setOutputWriter(new FileWriter("target/streamOutputHavingWriterSet.txt"));
			classUnderTest.open(); 
			classUnderTest.startRow(new RowProps());
			classUnderTest.output(new CellProps.Builder("cell 0 0").build());
			classUnderTest.output(new CellProps.Builder("cell 0 1").build());
			classUnderTest.output(new CellProps.Builder("cell 0 2").build());
			classUnderTest.endRow(); 
			classUnderTest.close(); 
		} catch (IOException e) {
			fail(e.getMessage()); 
		}
	}
	
	public void testManyOutputsForDifferentFilenames(){
		TxtOutput classUnderTest = new TxtOutput(); 
		classUnderTest.setFilePath("target/reusedStreamOutput1.txt");
		
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close();
		
		classUnderTest.setFilePath("target/reusedStreamOutput2.txt");
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close();
	}
	
	public void testManyOutputsForDifferentWriters(){
		StringWriter writer1 = new StringWriter(); 
		TxtOutput classUnderTest = new TxtOutput(); 
		classUnderTest.setOutputWriter(writer1);
		
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close();
		
		StringBuffer result = writer1.getBuffer(); 
		assertNotNull(result);
		assertTrue(result.indexOf("cell 0 0") > 0);
		assertTrue(result.indexOf("cell 0 2") > 0);
		
		
		StringWriter writer2 = new StringWriter(); 
		classUnderTest.setOutputWriter(writer2);
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close();
		
		result = writer2.getBuffer(); 
		assertNotNull(result);
		assertTrue(result.indexOf("cell 0 0") > 0);
		assertTrue(result.indexOf("cell 0 2") > 0);
	}
	
}
