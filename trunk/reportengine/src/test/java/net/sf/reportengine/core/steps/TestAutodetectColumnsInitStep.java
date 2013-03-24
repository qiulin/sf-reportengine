/**
 * 
 */
package net.sf.reportengine.core.steps;


import static net.sf.reportengine.util.ContextKeys.DATA_COLUMNS;
import static net.sf.reportengine.util.ContextKeys.GROUP_COLUMNS;
import static net.sf.reportengine.util.ContextKeys.USER_COLUMN_PREFERENCES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.scenarios.AutodetectConfigurationScenario;


/**
 * @author dragos balan
 *
 */
public class TestAutodetectColumnsInitStep extends ReportAlgorithmStepTC {
	
	private ReportContext reportContext; 
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AutodetectConfigurationScenario.initScenario();
		
		reportContext = new DefaultReportContext(); 
		reportContext.setInput(AutodetectConfigurationScenario.INPUT); 
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.AutodetectColumnsInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	public void testInit_AsManyPreferencesAsMetadataColumns() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.group(false).header("Header Data Col 1").align(HorizontalAlign.LEFT); 
		userPrefs.put("col1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.group(false).header("Header Data Col 2").align(HorizontalAlign.RIGHT); 
		userPrefs.put("col2", column2Prefs);
		
		reportContext.set(USER_COLUMN_PREFERENCES, userPrefs); 
		
		//the class/method under test
		new AutodetectColumnsInitStep().init(reportContext); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)reportContext.get(DATA_COLUMNS);
		assertNotNull(dataCols);
		assertEquals(2, dataCols.size()); 
		assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
		assertEquals("Header Data Col 2", dataCols.get(1).getHeader()); 
		
		assertNull(reportContext.get(GROUP_COLUMNS)); 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.AutodetectColumnsInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	public void testInit_MorePreferencesThanMetadata() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.group(false).header("Header Data Col 1").align(HorizontalAlign.LEFT); 
		userPrefs.put("col1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.group(false).header("Header Data Col 2").align(HorizontalAlign.RIGHT); 
		userPrefs.put("col3", column2Prefs);//NOT TAKEN INTO ACCCOUNT BECAUSE THERE is no metadata with id col3
		
		reportContext.set(USER_COLUMN_PREFERENCES, userPrefs); 
		
		//the class/method under test
		new AutodetectColumnsInitStep().init(reportContext); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)reportContext.get(DATA_COLUMNS);
		assertNotNull(dataCols);
		assertEquals(2, dataCols.size()); 
		assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
		assertEquals("col2label", dataCols.get(1).getHeader()); 
		
		assertNull(reportContext.get(GROUP_COLUMNS));
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.AutodetectColumnsInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	public void testInit_LessPreferencesThanMetadata() {
		AutodetectColumnsInitStep classUnderTest = new AutodetectColumnsInitStep();
		
		//set up report context
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.group(false).header("Second Column Prefs").align(HorizontalAlign.LEFT); 
		
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		userPrefs.put("col2", column1Prefs); 
		
		reportContext.set(USER_COLUMN_PREFERENCES, userPrefs); 
		
		classUnderTest.init(reportContext); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)reportContext.get(DATA_COLUMNS);
		assertNotNull(dataCols);
		assertEquals(2, dataCols.size()); 
		assertEquals("col1label", dataCols.get(0).getHeader()); 
		assertEquals("Second Column Prefs", dataCols.get(1).getHeader()); 
		
		assertNull(reportContext.get(GROUP_COLUMNS)); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.AutodetectColumnsInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	public void testInit_AsManyPreferencesAsMetadataWithGroups() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.group(true).header("Header Group Col 1").align(HorizontalAlign.LEFT); 
		userPrefs.put("col1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.group(true).header("Header Group Col 2").align(HorizontalAlign.RIGHT); 
		userPrefs.put("col2", column2Prefs);
		
		reportContext.set(USER_COLUMN_PREFERENCES, userPrefs); 
		
		//the class/method under test
		new AutodetectColumnsInitStep().init(reportContext); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)reportContext.get(DATA_COLUMNS);
		assertNotNull(dataCols);
		assertEquals(0, dataCols.size()); 
		 
		List<GroupColumn>groupCols = (List<GroupColumn>)reportContext.get(GROUP_COLUMNS); 
		assertNotNull(groupCols);
		assertEquals(2, groupCols.size());
		assertEquals("Header Group Col 1", groupCols.get(0).getHeader());
		assertEquals("Header Group Col 2", groupCols.get(1).getHeader());
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.AutodetectColumnsInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	public void testInit_LessPreferencesThanMetadataWithGroups() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.group(true).header("Header Group Col 2").align(HorizontalAlign.RIGHT); 
		userPrefs.put("col2", column2Prefs);
		
		reportContext.set(USER_COLUMN_PREFERENCES, userPrefs); 
		
		//the class/method under test
		new AutodetectColumnsInitStep().init(reportContext); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)reportContext.get(DATA_COLUMNS);
		assertNotNull(dataCols);
		assertEquals(1, dataCols.size()); 
		assertEquals("col1label", dataCols.get(0).getHeader());
		
		List<GroupColumn>groupCols = (List<GroupColumn>)reportContext.get(GROUP_COLUMNS); 
		assertNotNull(groupCols);
		assertEquals(1, groupCols.size());
		assertEquals("Header Group Col 2", groupCols.get(0).getHeader());
	}
}