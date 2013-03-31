/**
 * 
 */
package net.sf.reportengine.scenarios;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ReportInput;

import org.mockito.Mockito;

/**
 * @author dragos balan
 *
 */
public class AutodetectConfigurationScenario {
	
	public static List<ColumnMetadata> COLUMN_METADATA = Arrays.asList(new ColumnMetadata(), new ColumnMetadata());
	
	public static ReportInput INPUT = Mockito.mock(ReportInput.class); 
	
	public static void initScenario(){
		COLUMN_METADATA.get(0).setColumnId("col1"); 
		COLUMN_METADATA.get(0).setColumnLabel("col1label"); 
		COLUMN_METADATA.get(0).setHorizontalAlign(HorizAlign.CENTER);
		
		COLUMN_METADATA.get(1).setColumnId("col2"); 
		COLUMN_METADATA.get(1).setColumnLabel("col2label"); 
		COLUMN_METADATA.get(1).setHorizontalAlign(HorizAlign.LEFT);
		
		when(INPUT.supportsMetadata()).thenReturn(true); 
		when(INPUT.getColumnMetadata()).thenReturn(COLUMN_METADATA);
		when(INPUT.hasMoreRows()).thenReturn(true, true, true, false); 
		when(INPUT.nextRow()).thenReturn(
				new Object[]{"value 11", "value 12"}, 
				new Object[]{"value 21", "value 22"}, 
				new Object[]{"value 31", "value 32"}); 
	}
}
