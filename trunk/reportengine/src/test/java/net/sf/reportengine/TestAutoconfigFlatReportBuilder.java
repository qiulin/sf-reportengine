package net.sf.reportengine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.reportengine.config.DefaultDataColumn;

import org.junit.Test;

public class TestAutoconfigFlatReportBuilder {

	@Test
	public void testShowTotalsSetManually() {
		AutoconfigFlatReport report = new AutoconfigFlatReport.Builder() 
			.showTotals(false)
			.showGrandTotal()
			.build(); 
		
		assertFalse(report.getShowTotals()); 
		assertTrue(report.getShowGrandTotal()); 
	}
	
	@Test
	public void testShowTotalsNotSetManually() {
		AutoconfigFlatReport report = new AutoconfigFlatReport.Builder() 
			.build(); 
		
		assertFalse(report.getShowTotals()); 
		assertFalse(report.getShowGrandTotal()); 
	}
}
