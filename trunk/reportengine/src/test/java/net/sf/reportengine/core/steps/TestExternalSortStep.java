package net.sf.reportengine.core.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;

import org.junit.Test;

public class TestExternalSortStep {
	
	private static final int MAX_ROWS_IN_MEMORY = 4;
	
	@Test
	public void testExecute() throws Exception{
		
		DefaultReportContext mockContext = new DefaultReportContext(); 
		mockContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS); 
		mockContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		
		ExternalSortPreparationStep classUnderTest = new ExternalSortPreparationStep(MAX_ROWS_IN_MEMORY); 
		classUnderTest.init(mockContext); 
		
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_1));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_2));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_3));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_4));
		
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_5));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_6));
		
		classUnderTest.exit(mockContext); 
		
		List<File> filesResultedFromExternalSorting = (List<File>)mockContext.get(ContextKeys.SORTED_FILES); 
		assertNotNull(filesResultedFromExternalSorting); 
		assertEquals(2, filesResultedFromExternalSorting.size());
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(filesResultedFromExternalSorting.get(0))); 
		NewRowEventWrapper newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_4.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_3.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_2.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject();
		assertNotNull(newRowEventWrapper);
		assertTrue(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_1.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		
		//second file 
		input = new ObjectInputStream(new FileInputStream(filesResultedFromExternalSorting.get(1))); 
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_5.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertTrue(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_6.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
	}
}
