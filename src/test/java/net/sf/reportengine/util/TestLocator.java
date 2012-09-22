/*
 * Created on 29.01.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.util;

import junit.framework.TestCase;

/**
 * <p>
 *  Locator class very important for Cross tab reports
 * </p>
 * @author dragos balan( dragos.balan@gmail.com)
 */
public class TestLocator extends TestCase {
	
    //for test 1
	public final static Object[][] testTemplate1 = new Object[][]{
  		new String[]{"11","11","11","11","11","11","12","12","12","12","12","12","13","13","13","13","13","13"},
		new String[]{"21","21","21","22","22","22","21","21","21","22","22","22","21","21","21","22","22","22"},
		new String[]{"31","32","33","31","32","33","31","32","33","31","32","33","31","32","33","31","32","33"},
	};
    private final int[] arrJumpTest1 = new int[]{6,3,1};    
    private final int DATA_LINE_COUNT_TEST1 = 2;
	
    
    //for test 2
	private final static Object[][] testTemplate2 = new Object[][]{
	   new String[]{"M",		"M",		"M",		"M",		"M",		"M",		"M",		"M",		"M",		"F",		"F",		"F",		"F",		"F",		"F",		"F",		"F",		"F"},
	   new String[]{"Orthodox",	"Orthodox",	"Orthodox",	"Catholic",	"Catholic",	"Catholic",	"Muslim",	"Muslim",	"Muslim",	"Orthodox",	"Orthodox",	"Orthodox",	"Catholic",	"Catholic",	"Catholic",	"Muslim",	"Muslim",	"Muslim"},
	   new String[]{"data1",	"data2",	"data3",	"data1",	"data2",	"data3",	"data1",	"data2",	"data3",	"data1",	"data2",	"data3",	"data1",	"data2",	"data3",	"data1",	"data2",	"data3"}
	};
    private final int[] jumpTest2 = new int[]{9,3,1};
	
	
	
	private Locator locator = null;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		 
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
    
    public void testLocate1(){
        
    }

	public void testLocate2() {
	    locator = new Locator(testTemplate2,jumpTest2/*,1*/);
	    locator.newResult(1);
	    Object[] lineToSearch = new Object[]{"Fake1","Fake2","M","Orthodox","data1","100"};
		locator.search(lineToSearch,2);
		System.out.print("aftel searching the line :");
		//ArrayUtils.logArray(lineToSearch);
		System.out.println("the result is ");
		MatrixUtils.logMatrix(locator.getResult());		
	}
    
    public void testLocate3(){
        locator = new Locator(testTemplate2,jumpTest2/*,1*/);
        locator.newResult(1);
        Object[] lineToSearch = new String[]{"Fake1","Fake2","M","Orthodox","1","2","3"};
        Object[][] multiLines = MatrixUtils.multiplyLines(lineToSearch,new String[]{"data1","data2","data3"});
        for (int i = 0; i < multiLines.length; i++) {
            locator.search(multiLines[i],2);
        }
        MatrixUtils.logMatrix(locator.getResult());
    }

}
