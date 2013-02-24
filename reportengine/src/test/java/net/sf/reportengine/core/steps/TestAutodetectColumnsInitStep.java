/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.util.ContextKeys;


/**
 * @author dragos
 *
 */
public class TestAutodetectColumnsInitStep extends ReportAlgorithmStepTC {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.AutodetectColumnsInitStep#init(net.sf.reportengine.core.algorithm.IReportContext)}.
	 */
	public void testInit() {
		AutodetectColumnsInitStep classUnderTest = new AutodetectColumnsInitStep(); 
		IReportContext context = new DefaultReportContext();  
		context.setInput(new ArrayReportInput(new String[][]{})); 
		
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.setGroup(true); 
		column1Prefs.setHeader("Header Group Col 1"); 
		column1Prefs.setHorizAlign(HorizontalAlign.LEFT); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.setGroup(false); 
		column2Prefs.setHeader("Header Data Col 1"); 
		column2Prefs.setHorizAlign(HorizontalAlign.RIGHT); 
		
		
		ColumnPreferences[] prefs = new ColumnPreferences[]{column1Prefs, column2Prefs}; 
		
		context.set(ContextKeys.USER_COLUMN_PREFERENCES, prefs); 
		
		classUnderTest.init(context); 
		
		//test the result
		Object dataColsObj = context.get(ContextKeys.DATA_COLUMNS); 
		assertTrue(dataColsObj instanceof IDataColumn[]);
		IDataColumn[] dataCols = (IDataColumn[])dataColsObj; 
		assertEquals(1, dataCols.length); 
		
		Object groupColsObj = context.get(ContextKeys.GROUPING_COLUMNS); 
		assertTrue(groupColsObj instanceof IGroupColumn[]);
		IGroupColumn[] groupCols = (IGroupColumn[])groupColsObj; 
		assertEquals(1, groupCols.length); 
		
	}

}
