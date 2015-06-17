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
package net.sf.reportengine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.core.calc.GroupCalculators;

import org.junit.Test;

public class TestPivotTableBuilder {

	@Test
	public void testShowTotalsSetManually() {
		PivotTable table = new PivotTable.Builder() 
		
		//manually setting show totals to false and adding a column with a calculator 
		//shoud result in no showing of totals in the end 
			.showTotals(false)
			.addDataColumn(new DefaultDataColumn("", 0, GroupCalculators.AVG))
			.build(); 
		
		assertFalse(table.getShowTotals()); 
		assertTrue(table.getShowGrandTotal()); 
	}
	
	
	@Test
	public void testShowTotalsNotSetManuallyWithCalculators() {
		PivotTable table = new PivotTable.Builder() 
			
			//because we add a column having a calculator 
			//the Builder should set the showTotals to true
			.addDataColumn(new DefaultDataColumn("", 0, GroupCalculators.AVG))
			.build(); 
		
		assertTrue(table.getShowTotals()); 
		assertTrue(table.getShowGrandTotal()); 
	}
	
	@Test
	public void testShowTotalsNotSetManuallyAndNoCalculators() {
		PivotTable table = new PivotTable.Builder() 
			
			//because we add a column having a calculator 
			//the Builder should set the showTotals to true
			.addDataColumn(new DefaultDataColumn("", 0))
			.build(); 
		
		assertFalse(table.getShowTotals()); 
		assertFalse(table.getShowGrandTotal()); 
	}

}
