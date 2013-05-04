package net.sf.reportengine.core.steps;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
		assertArrayEquals(	Scenario1.ROW_OF_DATA_4, 
							newRowEventWrapper.getNewRowEvent().getInputDataRow());
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertArrayEquals(	Scenario1.ROW_OF_DATA_3, 
							newRowEventWrapper.getNewRowEvent().getInputDataRow());
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertArrayEquals(	Scenario1.ROW_OF_DATA_2, 
							newRowEventWrapper.getNewRowEvent().getInputDataRow());
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject();
		assertNotNull(newRowEventWrapper);
		assertTrue(newRowEventWrapper.isLast()); 
		assertArrayEquals(	Scenario1.ROW_OF_DATA_1, 
							newRowEventWrapper.getNewRowEvent().getInputDataRow());
		
		
		//second file 
		input = new ObjectInputStream(new FileInputStream(filesResultedFromExternalSorting.get(1))); 
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertArrayEquals(	Scenario1.ROW_OF_DATA_5, 
							newRowEventWrapper.getNewRowEvent().getInputDataRow());
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		assertNotNull(newRowEventWrapper);
		assertTrue(newRowEventWrapper.isLast()); 
		assertArrayEquals(	Scenario1.ROW_OF_DATA_6, 
							newRowEventWrapper.getNewRowEvent().getInputDataRow());
		
	}
}
