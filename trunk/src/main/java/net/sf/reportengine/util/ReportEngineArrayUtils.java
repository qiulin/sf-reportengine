/*
 * Created on 29.01.2005
 */
package net.sf.reportengine.util;

import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;

/**
 * 
 * utility class 
 * @author dragos balan
 *
 */
public class ReportEngineArrayUtils {
    
     
    
    /**
     * empty private constructor 
     * the purpouse of this constructor is to avoid 
     * the default constructor which should be public 
     *
     */
    private ReportEngineArrayUtils(){
        
    }
	
	
    
    public static void outCalculatorsResults(ICalculator[] calculatorsArray, IReportOutput engineOut){
        for (int i = 0; i < calculatorsArray.length; i++) {
            engineOut.output(new CellProps(calculatorsArray[i].getResult()));
        }
    }
    
    /**
     * returns an array filled with values starting with startValue and ending with endValue<br>
     * Example: <br>
     *      generateConsecutiveNumbers(6,10) <br>
     *  returns an array containing : 6,7,8,9,10
     * @param startValue    the start value
     * @param endValue      the end value
     * @return              an array containing consecutive values
     */
    public static int[] generateConsecutiveNumbers(int startValue, int endValue){
        int[] result = new int[endValue - startValue + 1];
        for(int i=0; i< result.length; i++){
            result[i] = startValue + i;
        }
        return result;
    }
    
    public static boolean equalArrays(Object[] array1, Object[] array2){
        
        if(array1.length != array2.length){
            System.err.println(" the two arrays don't have the same length "+array1.length+" <> "+array2.length);
            
            return false;
        }
        boolean equal = true;
        for(int i=0; equal && i<array1.length ; i++){
            if(!array1[i].equals(array2[i])){
                System.err.println(" Found two distinct values "+array1[i]+" <> "+array2[i]);
                equal = false;
            }
        }
        return equal;
    }
    
    public static boolean equalArraysAsStrings(Object[] array1, Object[] array2){
       
        if(array1.length != array2.length){
            System.err.println(" the two arrays don't have the same length "+array1.length+" <> "+array2.length);
            
            return false;
        }
        boolean equal = true;
        for(int i=0; equal && i<array1.length ; i++){
            if(array1[i] == null && array2[i] == null){
                continue;
            }
            if(!array1[i].toString().equals(array2[i].toString())){
                System.err.println(" Found two distinct values "+array1[i]+" <> "+array2[i]);
                equal = false;
            }
        }
        return equal;
    }
    
    public static int[] stickArrays(int[] arr1, int[] arr2){
        int[] result = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        //logArray(result);
        return result;
    }
    
    public static String arrayToString(Object[] array, String delimiter){
        StringBuffer strBuffer = new StringBuffer();
        if (array == null) {
            strBuffer.append(" null ");
        }else{
            strBuffer.append(array[0]);
            for (int i = 1; i < array.length; i++) {
                strBuffer.append(delimiter+ array[i]);
            }
            
        }
        return strBuffer.toString();
    }
    
    public static String arrayToString(Object[] array){
        return arrayToString(array, "\t");
    }
    
    
}
