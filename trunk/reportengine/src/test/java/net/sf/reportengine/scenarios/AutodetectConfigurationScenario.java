/**
 * 
 */
package net.sf.reportengine.scenarios;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.TableInput;

import org.mockito.Mockito;

/**
 * @author dragos balan
 *
 */
public class AutodetectConfigurationScenario {
	
	public static List<ColumnMetadata> COLUMN_METADATA = 
			Arrays.asList(	new ColumnMetadata("col1", "col1label", HorizAlign.CENTER), 
							new ColumnMetadata("col2", "col2label", HorizAlign.LEFT));
	
	public static TableInput INPUT = Mockito.mock(TableInput.class); 
	
	public static void initScenario(){
		
		when(INPUT.getColumnMetadata()).thenReturn(COLUMN_METADATA);
		when(INPUT.hasMoreRows()).thenReturn(true, true, true, false); 
		when(INPUT.nextRow()).thenReturn(
				Arrays.asList(new Object[]{"value 11", "value 12"}), 
				Arrays.asList(new Object[]{"value 21", "value 22"}), 
				Arrays.asList(new Object[]{"value 31", "value 32"})); 
	}
}
