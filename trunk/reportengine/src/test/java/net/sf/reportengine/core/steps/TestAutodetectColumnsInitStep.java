/**
 * 
 */
package net.sf.reportengine.core.steps;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep;
import net.sf.reportengine.core.steps.autodetect.ConstrDataColsFromMetadataAndUserPrefsInitStep;
import net.sf.reportengine.core.steps.autodetect.ConstrGroupColsFromMetadataAndUserPrefsInitStep;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.scenarios.AutodetectConfigurationScenario;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author dragos balan
 *
 */
public class TestAutodetectColumnsInitStep {
	
	private AlgoContext reportContext; 
	private Map<IOKeys, Object> mockAlgoInput ;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		AutodetectConfigurationScenario.initScenario();
		
		reportContext = new DefaultAlgorithmContext(); 
		reportContext.set(ContextKeys.LOCAL_REPORT_INPUT, AutodetectConfigurationScenario.INPUT); 
		
		mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		//mockAlgoInput.put(IOKeys.REPORT_INPUT, AutodetectConfigurationScenario.INPUT); 
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep#init(net.sf.reportengine.core.algorithm.AlgoContext)}.
	 */
	@Test
	public void testInit_AsManyPreferencesAsMetadataColumns() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.header("Header Data Col 1").horizAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.header("Header Data Col 2").horizAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL2", column2Prefs);
		
		mockAlgoInput.put(IOKeys.USER_COLUMN_PREFERENCES, userPrefs); 
		
		//the class/method under test
		//new AutodetectConfigInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		StepResult stepResult1 = new ConstrDataColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext));
		reportContext.add(stepResult1);
		StepResult stepResult2 = new ConstrGroupColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)stepResult1.getValue(); //(IOKeys.DATA_COLS);
		Assert.assertNotNull(dataCols);
		Assert.assertEquals(2, dataCols.size()); 
		Assert.assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
		Assert.assertEquals("Header Data Col 2", dataCols.get(1).getHeader()); 
		
		Assert.assertNull(stepResult2.getValue()); //mockAlgoInput.get(IOKeys.GROUP_COLS 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep#init(net.sf.reportengine.core.algorithm.AlgoContext)}.
	 */
	@Test
	public void testInit_MorePreferencesThanMetadata() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.header("Header Data Col 1").horizAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.header("Header Data Col 2").horizAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL3", column2Prefs);//NOT TAKEN INTO ACCCOUNT BECAUSE THERE is no metadata with id col3
		
		mockAlgoInput.put(IOKeys.USER_COLUMN_PREFERENCES, userPrefs); 
		
		//the class/method under test
		//new AutodetectConfigInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		StepResult stepResult1 = new ConstrDataColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		reportContext.add(stepResult1);
		StepResult stepResult2 = new ConstrGroupColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)stepResult1.getValue();//.get(IOKeys.DATA_COLS);
		Assert.assertNotNull(dataCols);
		Assert.assertEquals(2, dataCols.size()); 
		Assert.assertEquals("Header Data Col 1", dataCols.get(0).getHeader());
		Assert.assertEquals("col2label", dataCols.get(1).getHeader()); 
		
		Assert.assertNull(stepResult2.getValue());//mockAlgoInput.get(IOKeys.GROUP_COLS));
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep#init(net.sf.reportengine.core.algorithm.AlgoContext)}.
	 */
	@Test
	public void testInit_LessPreferencesThanMetadata() {
		//AutodetectConfigInitStep classUnderTest = new AutodetectConfigInitStep();
		
		//set up report context
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.header("Second Column Prefs").horizAlign(HorizAlign.LEFT); 
		
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		userPrefs.put("COL2", column1Prefs); 
		
		mockAlgoInput.put(IOKeys.USER_COLUMN_PREFERENCES, userPrefs); 
		
		//new AutodetectConfigInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		StepResult stepResult1 = new ConstrDataColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		reportContext.add(stepResult1);
		StepResult stepResult2 = new ConstrGroupColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)stepResult1.getValue();//mockAlgoInput.get(IOKeys.DATA_COLS);
		Assert.assertNotNull(dataCols);
		Assert.assertEquals(2, dataCols.size()); 
		Assert.assertEquals("col1label", dataCols.get(0).getHeader()); 
		Assert.assertEquals("Second Column Prefs", dataCols.get(1).getHeader()); 
		
		Assert.assertNull(stepResult2.getValue()); //mockAlgoInput.get(IOKeys.GROUP_COLS)); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep#init(net.sf.reportengine.core.algorithm.AlgoContext)}.
	 */
	@Test
	public void testInit_AsManyPreferencesAsMetadataWithGroups() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		ColumnPreferences column1Prefs = new ColumnPreferences(); 
		column1Prefs.group().header("Header Group Col 1").horizAlign(HorizAlign.LEFT); 
		userPrefs.put("COL1", column1Prefs); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.group().header("Header Group Col 2").horizAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL2", column2Prefs);
		
		mockAlgoInput.put(IOKeys.USER_COLUMN_PREFERENCES, userPrefs); 
		
		//the class/method under test
		//new AutodetectConfigInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		StepResult stepResult1 = new ConstrDataColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		reportContext.add(stepResult1);
		StepResult stepResult2 = new ConstrGroupColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)stepResult1.getValue(); //mockAlgoInput.get(IOKeys.DATA_COLS);
		Assert.assertNotNull(dataCols);
		Assert.assertEquals(0, dataCols.size()); 
		 
		List<GroupColumn>groupCols = (List<GroupColumn>)stepResult2.getValue(); //mockAlgoInput.get(IOKeys.GROUP_COLS); 
		Assert.assertNotNull(groupCols);
		Assert.assertEquals(2, groupCols.size());
		Assert.assertEquals("Header Group Col 1", groupCols.get(0).getHeader());
		Assert.assertEquals("Header Group Col 2", groupCols.get(1).getHeader());
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep#init(net.sf.reportengine.core.algorithm.AlgoContext)}.
	 */
	@Test
	public void testInit_LessPreferencesThanMetadataWithGroups() {
		
		//set up user preferences
		Map<String, ColumnPreferences> userPrefs = new HashMap<String, ColumnPreferences>(); 
		
		ColumnPreferences column2Prefs = new ColumnPreferences(); 
		column2Prefs.group().header("Header Group Col 2").horizAlign(HorizAlign.RIGHT); 
		userPrefs.put("COL2", column2Prefs);
		
		mockAlgoInput.put(IOKeys.USER_COLUMN_PREFERENCES, userPrefs);  
		
		//the class/method under test
		//new AutodetectConfigInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		StepResult stepResult1 = new ConstrDataColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		reportContext.add(stepResult1);
		StepResult stepResult2 = new ConstrGroupColsFromMetadataAndUserPrefsInitStep().init(new StepInput(mockAlgoInput, reportContext)); 
		
		//test the result
		List<DataColumn> dataCols = (List<DataColumn>)stepResult1.getValue();  //mockAlgoInput.get(IOKeys.DATA_COLS);
		Assert.assertNotNull(dataCols);
		Assert.assertEquals(1, dataCols.size()); 
		Assert.assertEquals("col1label", dataCols.get(0).getHeader());
		
		List<GroupColumn> groupCols = (List<GroupColumn>)stepResult2.getValue(); //mockAlgoInput.get(IOKeys.GROUP_COLS); 
		Assert.assertNotNull(groupCols);
		Assert.assertEquals(1, groupCols.size());
		Assert.assertEquals("Header Group Col 2", groupCols.get(0).getHeader());
	}
}