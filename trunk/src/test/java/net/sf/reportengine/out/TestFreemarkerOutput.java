package net.sf.reportengine.out;

import junit.framework.TestCase;

public class TestFreemarkerOutput extends TestCase {
	
	private FreeMarkerOutput classUnderTest;
	
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new FreeMarkerOutput("reportOutput.html"); 
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOpen() {
		classUnderTest.open();
		classUnderTest.startRow(new RowProps()); 
		classUnderTest.output(new CellProps.Builder("test value").build()); 
		classUnderTest.endRow(); 
		classUnderTest.close(); 
	}

}
