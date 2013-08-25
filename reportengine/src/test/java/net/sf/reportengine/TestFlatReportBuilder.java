package net.sf.reportengine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.core.calc.Calculators;

import org.junit.Test;

public class TestFlatReportBuilder {

	@Test
	public void testShowTotalsSetManually() {
		FlatReport report = new FlatReport.Builder() 
		
		//manually setting show totals to false and adding a column with a calculator 
		//shoud result in no showing of totals in the end 
			.showTotals(false)
			.addDataColumn(new DefaultDataColumn("", 0, Calculators.AVG))
			.build(); 
		
		assertFalse(report.getShowTotals()); 
		assertTrue(report.getShowGrandTotal()); 
	}
	
	
	@Test
	public void testShowTotalsNotSetManuallyWithCalculators() {
		FlatReport report = new FlatReport.Builder() 
			
			//because we add a column having a calculator 
			//the Builder should set the showTotals to true
			.addDataColumn(new DefaultDataColumn("", 0, Calculators.AVG))
			.build(); 
		
		assertTrue(report.getShowTotals()); 
		assertTrue(report.getShowGrandTotal()); 
	}
	
	@Test
	public void testShowTotalsNotSetManuallyAndNoCalculators() {
		FlatReport report = new FlatReport.Builder() 
			
			//because we add a column having a calculator 
			//the Builder should set the showTotals to true
			.addDataColumn(new DefaultDataColumn("", 0))
			.build(); 
		
		assertFalse(report.getShowTotals()); 
		assertFalse(report.getShowGrandTotal()); 
	}
}
