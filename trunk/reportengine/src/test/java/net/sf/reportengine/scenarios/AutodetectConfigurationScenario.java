/**
 * 
 */
package net.sf.reportengine.scenarios;

import static org.mockito.Mockito.when;
import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.IReportInput;

import org.mockito.Mockito;

/**
 * @author dragos balan
 *
 */
public class AutodetectConfigurationScenario {
	
	public static ColumnMetadata[] COLUMN_METADATA = new ColumnMetadata[]{new ColumnMetadata(), new ColumnMetadata()};
	
	public static IReportInput INPUT = Mockito.mock(IReportInput.class); 
	
	public static void initScenario(){
		COLUMN_METADATA[0].setColumnId("col1"); 
		COLUMN_METADATA[0].setColumnLabel("col1label"); 
		COLUMN_METADATA[0].setHorizontalAlign(HorizontalAlign.CENTER);
		
		COLUMN_METADATA[1].setColumnId("col2"); 
		COLUMN_METADATA[1].setColumnLabel("col2label"); 
		COLUMN_METADATA[1].setHorizontalAlign(HorizontalAlign.LEFT);
		
		when(INPUT.getColumnMetadata()).thenReturn(COLUMN_METADATA);
		when(INPUT.hasMoreRows()).thenReturn(true, true, true, false); 
		when(INPUT.nextRow()).thenReturn(
				new Object[]{"value 11", "value 12"}, 
				new Object[]{"value 21", "value 22"}, 
				new Object[]{"value 31", "value 32"}); 
	}
}
