package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.calc.ICalculatorsFactory;

public abstract class AbstractDataColumn implements IDataColumn {
	
	private String header; 
	private Format formatter; 
	private ICalculatorsFactory calculatorsFactory; 
	
	public AbstractDataColumn(String header){
		this(header, null); 
	}
	
	public AbstractDataColumn(String header, ICalculatorsFactory calculator){
		this(header, calculator, null); 
	}
	
	public AbstractDataColumn(String header, ICalculatorsFactory calculator, Format formatter){
		setHeader(header); 
		setFormatter(formatter); 
		setCalculator(calculator); 
	}
		
	public String getHeader() {
		return header;
	}

	public String getFormattedValue(Object value) {
		String result = "";
		if(value != null){
			if(formatter != null){
				result = formatter.format(value);
			}else{
				result = value.toString();
			}
		}
		return result; 
	}
	

	public ICalculatorsFactory getCalculator() {
		return calculatorsFactory;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}


	public void setCalculator(ICalculatorsFactory calculatorFactory) {
		this.calculatorsFactory = calculatorFactory;
	}

}
