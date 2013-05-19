/**
 * 
 */
package net.sf.reportengine.core.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestInMemorySortStep {
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.InMemorySortStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	@Test
	public void testExecute() {
		InMemorySortStep classUnderTest = new InMemorySortStep(); 
		
		AlgorithmContext mockContext = new DefaultAlgorithmContext(); 
		//mockContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		//mockContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
		mockAlgoInput.put(IOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
		
		classUnderTest.init(mockAlgoInput, mockContext); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_6); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_6)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_1); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_1)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_2); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_2)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_3); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_3)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_4); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_4)); 
		
		//mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_5); 
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_5)); 
		
		
		//
		classUnderTest.exit(mockAlgoInput, mockContext); 
		
		List<NewRowEvent> result = (List<NewRowEvent>)mockContext.get(ContextKeys.IN_MEM_SORTED_RESULT); 
		assertNotNull(result); 
		assertEquals(6, result.size());
		
		System.out.println("result :"+ result);
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_4).equals(result.get(0)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_5).equals(result.get(1)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_3).equals(result.get(2)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_1).equals(result.get(3)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_2).equals(result.get(4)));
		assertTrue(new NewRowEvent(Scenario1.ROW_OF_DATA_6).equals(result.get(5)));
		
	}
}
