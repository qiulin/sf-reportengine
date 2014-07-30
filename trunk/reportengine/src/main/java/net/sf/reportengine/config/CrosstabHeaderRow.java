/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * This is part of the header of the final pivot table that gets its data from an input column. 
 * 
 * As an example: 
 * For an input like 
 * <pre>
 * 	N.America, Penguins, 100
 * 	N.America, Lions, 200
 * 	N.America, Camels, 10
 * 	N.America, Lions, 200
 * 	S.America, Penguins, 300
 * 	S.America, Camels,5
 * 	S.America, Lions, 150, 
 * 	Europe, Penguins, 0, 
 * </pre>			
 * If you define the second column as the source for the header row, you can get a pivot table like: 
 * <pre>
 * -----------------------------------------
 * Continent | Penguins | Lions  | Camels  |
 * -----------------------------------------
 * N. America|  100     |  200  |  10      |
 * -----------------------------------------
 * S. America|  200     |  300  |  5       |
 * -----------------------------------------
 * Europe ...
 * -----------------------------------------
 * 	</pre>
 * where 
 * <pre>
 * ------------------------------
 * | Penguins | Lions  | Camels  |
 * ------------------------------
 *</pre>
 *is the crosstab header row 
 * 
 * @see DefaultCrosstabHeaderRow
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public interface CrosstabHeaderRow {
	
	/**
	 * this method returns the value for the current data row (taken from newRowEvent)
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
}
