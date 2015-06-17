/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package net.sf.reportengine.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.scenarios.AutodetectConfigurationScenario;

/**
 * @author dragos
 *
 */
public class TestReportUtils extends TestCase {

//	public void testConstructDataColumnsFromMetadataAndUserPrefs() {
//		AutodetectConfigurationScenario.initScenario(); 
//		AutoconfigFlatReport classUnderTest = new AutoconfigFlatReport(); 
//		
//		//prepare first test: AS MANY USER PREFS AS METADATA COLS
//		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
//		ColumnPreferences column1Prefs = new ColumnPreferences(); 
//		column1Prefs.header("Header Data Col 1").horizAlign(HorizAlign.LEFT); 
//		userPrefs.put("COL1", column1Prefs); 
//		
//		ColumnPreferences column2Prefs = new ColumnPreferences(); 
//		column2Prefs.header("Header Data Col 2").horizAlign(HorizAlign.RIGHT); 
//		userPrefs.put("COL2", column2Prefs);
//		
//		//the method to test
//		List<DataColumn> dataCols = ReportUtils.dataColsFromMetadataAndUserPrefs(
//										AutodetectConfigurationScenario.COLUMN_METADATA, 
//										userPrefs);
//		
//		assertNotNull(dataCols);
//		assertEquals(2, dataCols.size()); 
//		assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
//		assertEquals("Header Data Col 2", dataCols.get(1).getHeader()); 
//		
//		
//		//prepare second test: MORE USER PREFERENCES THAN METADATA
//		userPrefs = new HashMap<String, ColumnPreferences>(); 
//		column1Prefs = new ColumnPreferences(); 
//		column1Prefs.header("Header Data Col 1").horizAlign(HorizAlign.LEFT); 
//		userPrefs.put("COL1", column1Prefs); 
//		
//		column2Prefs = new ColumnPreferences(); 
//		column2Prefs.header("Header Data Col 2").horizAlign(HorizAlign.RIGHT); 
//		userPrefs.put("COL3", column2Prefs);//NOT TAKEN INTO ACCCOUNT BECAUSE THERE is no metadata with id col3
//		
//		//test the result
//		dataCols = ReportUtils.dataColsFromMetadataAndUserPrefs(
//									AutodetectConfigurationScenario.COLUMN_METADATA, 
//									userPrefs);
//		assertNotNull(dataCols);
//		assertEquals(2, dataCols.size()); 
//		assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
//		assertEquals("col2label", dataCols.get(1).getHeader()); 
//		
//		
//		//prepare the third test: less preferences than metadata
//		column1Prefs = new ColumnPreferences(); 
//		column1Prefs.header("Second Column Prefs").horizAlign(HorizAlign.LEFT); 
//		
//		userPrefs = new HashMap<String, ColumnPreferences>(); 
//		userPrefs.put("COL2", column1Prefs); 
//		
//		dataCols = ReportUtils.dataColsFromMetadataAndUserPrefs(
//						AutodetectConfigurationScenario.COLUMN_METADATA, 
//						userPrefs);
//		
//		assertNotNull(dataCols);
//		assertEquals(2, dataCols.size()); 
//		assertEquals("col1label", dataCols.get(0).getHeader()); 
//		assertEquals("Second Column Prefs", dataCols.get(1).getHeader()); 
//	}
	
	public void testConstructGroupColumnsFromMetadataAndUserPrefs() {
		AutodetectConfigurationScenario.initScenario(); 
		
		//prepare first test: AS MANY USER PREFS AS METADATA COLS
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.header("Header Data Col 1").horizAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.header("Header Data Col 2").horizAlign(HorizAlign.RIGHT); 
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
		column1Prefs.header("Header Data Col 1").horizAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		column2Prefs = new ColumnPreferences(); 
		column2Prefs.header("Header Data Col 2").horizAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL3", column2Prefs);//NOT TAKEN INTO ACCCOUNT BECAUSE THERE is no metadata with id col3
		
		//test the result
		groupCols = ReportUtils.groupColsFromMetadataAndUserPrefs(
						AutodetectConfigurationScenario.COLUMN_METADATA, 
						userPrefs);
		
		assertNotNull(groupCols);
		assertEquals(0, groupCols.size());
		
		
		//prepare the third test: less preferences than metadata
		column1Prefs = new ColumnPreferences(); 
		column1Prefs.header("Second Column Prefs").horizAlign(HorizAlign.LEFT); 
		
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