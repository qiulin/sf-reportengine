/*
 * Created on 08.01.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

/**
 * dummy implementation for ICalculator. The result of <source>getResult()</source>
 * will always be blank space 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
class DummyCalculator implements ICalculator {
    
    
    /**
     * the constructor 
     *
     */
    DummyCalculator(){
        
    }
    
    /**
     * init method (empty)
     */
    public void init(){};
    
    /**
     * compute method (does nothing)
     */
    public void compute(Object value){};
    
    /**
     * result getter . Always return blank space
     */
    public Object getResult(){
        return "";
    }
   
    /**
     * clone
     */
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    };
    
    
}
