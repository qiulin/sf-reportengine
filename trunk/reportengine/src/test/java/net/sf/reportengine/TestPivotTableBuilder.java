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
