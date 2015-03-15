package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

public class StepInput {
	
	private final Map<IOKeys, Object> algoInput; 
	private final AlgoContext context; 
	
	
	public StepInput(Map<IOKeys, Object> algoInput, AlgoContext context){
		this.algoInput = algoInput; 
		this.context = context; 
	}
	
	
	public Object getAlgoInput(IOKeys key){
		return algoInput.get(key); 
	}
	
	public Object getContextParam(ContextKeys key){
		return context.get(key); 
	}
}
