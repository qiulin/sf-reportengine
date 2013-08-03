/**
 * 
 */
package net.sf.reportengine.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.AutoconfigFlatReport;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.scenarios.AutodetectConfigurationScenario;
import junit.framework.TestCase;

/**
 * @author dragos
 *
 */
public class TestReportUtils extends TestCase {

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

	public void testConstructDataColumnsFromMetadataAndUserPrefs() {
		AutodetectConfigurationScenario.initScenario(); 
		AutoconfigFlatReport classUnderTest = new AutoconfigFlatReport(); 
		
		//prepare first test: AS MANY USER PREFS AS METADATA COLS
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.setHeader("Header Data Col 1").setHAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.setHeader("Header Data Col 2").setHAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL2", column2Prefs);
		
		//the method to test
		List<DataColumn> dataCols = ReportUtils.dataColsFromMetadataAndUserPrefs(
										AutodetectConfigurationScenario.COLUMN_METADATA, 
										userPrefs);
		
		assertNotNull(dataCols);
		assertEquals(2, dataCols.size()); 
		assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
		assertEquals("Header Data Col 2", dataCols.get(1).getHeader()); 
		
		
		//prepare second test: MORE USER PREFERENCES THAN METADATA
		userPrefs = new HashMap<String, ColumnPreferences>(); 
		column1Prefs = new ColumnPreferences(); 
		column1Prefs.setHeader("Header Data Col 1").setHAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		column2Prefs = new ColumnPreferences(); 
		column2Prefs.setHeader("Header Data Col 2").setHAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL3", column2Prefs);//NOT TAKEN INTO ACCCOUNT BECAUSE THERE is no metadata with id col3
		
		//test the result
		dataCols = ReportUtils.dataColsFromMetadataAndUserPrefs(
									AutodetectConfigurationScenario.COLUMN_METADATA, 
									userPrefs);
		assertNotNull(dataCols);
		assertEquals(2, dataCols.size()); 
		assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
		assertEquals("col2label", dataCols.get(1).getHeader()); 
		
		
		//prepare the third test: less preferences than metadata
		column1Prefs = new ColumnPreferences(); 
		column1Prefs.setHeader("Second Column Prefs").setHAlign(HorizAlign.LEFT); 
		
		userPrefs = new HashMap<String, ColumnPreferences>(); 
		userPrefs.put("COL2", column1Prefs); 
		
		dataCols = ReportUtils.dataColsFromMetadataAndUserPrefs(
						AutodetectConfigurationScenario.COLUMN_METADATA, 
						userPrefs);
		
		assertNotNull(dataCols);
		assertEquals(2, dataCols.size()); 
		assertEquals("col1label", dataCols.get(0).getHeader()); 
		assertEquals("Second Column Prefs", dataCols.get(1).getHeader()); 
	}
	
	public void testConstructGroupColumnsFromMetadataAndUserPrefs() {
		AutodetectConfigurationScenario.initScenario(); 
		
		//prepare first test: AS MANY USER PREFS AS METADATA COLS
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.setHeader("Header Data Col 1").setHAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.setHeader("Header Data Col 2").setHAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL2", column2Prefs);
		
		//the method to test
		List<GroupColumn> groupCols = ReportUtils.groupColsFromMetadataAndUserPrefs(
										AutodetectConfigurationScenario.COLUMN_METADATA, 
										userPrefs);
		
		assertNotNull(groupCols);
		assertEquals(0, groupCols.size());
		
		
		//prepare second test: MORE USER PREFERENCES THAN METADATA
		userPrefs = new HashMap<String, ColumnPreferences>(); 
		column1Prefs = new ColumnPreferences(); 
		column1Prefs.setHeader("Header Data Col 1").setHAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		column2Prefs = new ColumnPreferences(); 
		column2Prefs.setHeader("Header Data Col 2").setHAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL3", column2Prefs);//NOT TAKEN INTO ACCCOUNT BECAUSE THERE is no metadata with id col3
		
		//test the result
		groupCols = ReportUtils.groupColsFromMetadataAndUserPrefs(
						AutodetectConfigurationScenario.COLUMN_METADATA, 
						userPrefs);
		
		assertNotNull(groupCols);
		assertEquals(0, groupCols.size());
		
		
		//prepare the third test: less preferences than metadata
		column1Prefs = new ColumnPreferences(); 
		column1Prefs.setHeader("Second Column Prefs").setHAlign(HorizAlign.LEFT); 
		
		userPrefs = new HashMap<String, ColumnPreferences>(); 
		userPrefs.put("COL2", column1Prefs); 
		
		//test the result
		groupCols = ReportUtils.groupColsFromMetadataAndUserPrefs(
						AutodetectConfigurationScenario.COLUMN_METADATA, 
						userPrefs);
		
		assertNotNull(groupCols);
		assertEquals(0, groupCols.size());
	}
}