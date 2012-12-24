/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author dragos balan
 *
 */
public class TestStreamOutput extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link net.sf.reportengine.out.StreamReportOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOpenWhenNoWriterSet() {
		try{
			StreamReportOutput classUnderTest = new StreamReportOutput(); 
			classUnderTest.open();
			fail("An exception should have been thrown");
		}catch(ReportOutputException roe){
			assertEquals(AbstractOutput.NO_WRITER_SET_ERROR_MESSAGE, roe.getMessage());
		}
	}
	
	
	public void testOutputWhenFilenameSet(){
		StreamReportOutput classUnderTest = new StreamReportOutput(); 
		classUnderTest.setFileName("target/streamOutputHavingFilenameSet.txt");
		
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 1").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close(); 
	}
	
	public void testOutputWhenWriterSet(){
		StreamReportOutput classUnderTest = new StreamReportOutput(); 
		try {
			classUnderTest.setWriter(new FileWriter("target/streamOutputHavingWriterSet.txt"));
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
	
	public void testManyOutputsForTheSameConfiguration(){
		StreamReportOutput classUnderTest = new StreamReportOutput(); 
		classUnderTest.setFileName("target/reusedStreamOutput1.txt");
		
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close();
		
		classUnderTest.setFileName("target/reusedStreamOutput2.txt");
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps());
		classUnderTest.output(new CellProps.Builder("cell 0 0").build());
		classUnderTest.output(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endRow(); 
		classUnderTest.close();
	}
	
}
