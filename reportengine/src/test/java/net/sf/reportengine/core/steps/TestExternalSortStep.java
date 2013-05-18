package net.sf.reportengine.core.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;
import net.sf.reportengine.util.MatrixUtils;

import org.junit.Test;

public class TestExternalSortStep {
	
	private static final int MAX_ROWS_IN_MEMORY = 4;
	
	@Test
	public void testExecute() throws Exception{
		
		DefaultReportContext mockContext = new DefaultReportContext(); 
		Map<InputKeys, Object> mockAlgoInput = new EnumMap<InputKeys, Object>(InputKeys.class);
		
		//mockContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS); 
		//mockContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		mockAlgoInput.put(InputKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(InputKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
		
		ExternalSortPreparationStep classUnderTest = new ExternalSortPreparationStep(MAX_ROWS_IN_MEMORY); 
		classUnderTest.init(mockAlgoInput, mockContext); 
		
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_1));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_2));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_3));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_4));
		
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_5));
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_6));
		
		classUnderTest.exit(mockAlgoInput, mockContext); 
		
		List<File> filesResultedFromExternalSorting = (List<File>)mockContext.get(ContextKeys.SORTED_FILES); 
		assertNotNull(filesResultedFromExternalSorting); 
		assertEquals(2, filesResultedFromExternalSorting.size());
		
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(filesResultedFromExternalSorting.get(0))); 
		NewRowEventWrapper newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_4);
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_4.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_3);
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_3.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject() ;
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_1);
		assertNotNull(newRowEventWrapper);
		assertFalse(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_1.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		newRowEventWrapper = (NewRowEventWrapper)input.readObject();
		System.out.println("row read from file "+ newRowEventWrapper+ " is compared with "+Scenario1.ROW_OF_DATA_2);
		assertNotNull(newRowEventWrapper);
		assertTrue(newRowEventWrapper.isLast()); 
		assertTrue(Scenario1.ROW_OF_DATA_2.equals(newRowEventWrapper.getNewRowEvent().getInputDataRow()));
		
		
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
