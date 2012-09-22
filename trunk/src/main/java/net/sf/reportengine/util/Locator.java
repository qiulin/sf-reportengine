package net.sf.reportengine.util;


/**
 * locator class
 */
public class Locator {

    /**
     * internal use
     */
    public final static String TOTAL = "net.sf.reportengine.total";

    /**
     * internal use
     */
    public final static String SPACE = "net.sf.reportengine.space";

    /**
     *
     */
    private int[] jump = null;

    /**
     *
     */
    private int resultRowsCount;

    /**
     *
     */
    private Object[][] template;

    /**
     *
     */
    private int lastLineToCompare;

    /**
     *
     */
    private Object[][] result;

    /**
     *
     * @param matrix 		    the matrix holding the data
     * @param jumpVector        the jump vector
     * @param resultRowsCount   the row count of the result
     */
    public Locator( Object[][] template,
            	    int[] jumpVector//,
            		/*int resultRowsCount*/) {
        this.jump = jumpVector;
        //this.resultRowsCount = resultRowsCount;
        this.template = template;
        lastLineToCompare = template.length - 1 /*- dataColumnsCount-1*/;        
        
      }

    /**
     * constructs and prepares a new result matrix having the specified
     * row count 
     * @param resultRowsCount   the row count of the result
     */
    public void newResult(int resultRowsCount) {
        this.resultRowsCount = resultRowsCount;
        result = constructZeroFilledMatrix(resultRowsCount, template[0].length);
    }

    /**
     * result getter
     * @return
     */
    public Object[][] getResult() {
        return result;
    }

    /**
     * locator method
     *
     * @param line				line holding the data
     * @param offset			offset of data
     * @param columnNamesOnX	whether the column names should be displayed on x axis
     * @param columnNames		the column names
     */
    public void locate(Object[] line,
            			int offset,
            			boolean columnNamesOnX,
            			String[] columnNames) {
        if (columnNamesOnX) {
            Object[][] multiLines = MatrixUtils.multiplyLines(line, columnNames);
             for (int i = 0; i < multiLines.length; i++) {
                    search(multiLines[i], offset);
             }
         } else {
                search(line, offset);
         }

        
    }

    /**
     * internal use
     *
     * @param lineToSearch	the line to be compared with the template 
     * @param offset		the offset of the
     */
    public void search(Object[] lineToSearch, int offset) {
        //System.err.print("search "+offset+" for line : ");
        //ArrayUtils.logArray(lineToSearch,System.err);
        boolean flag = true;
        int lin = 0;
        int colDest = 0;
        
        String templateValue ;
        String valueToSearch ;
        while (flag) {
            templateValue = (String)template[lin][colDest];
            valueToSearch = (String)lineToSearch[lin + offset];
            if (templateValue.equals(valueToSearch)) {
                if (lin == lastLineToCompare) {
                    //dest[DATA_LINE][colDest] = lineToSearch[DATA_LINE];
                    copyData(colDest, lineToSearch, offset);
                    flag = false;
                } else {
                    lin++;
                }
            } else {
                colDest += jump[lin];
            }
        }
    }

    /**
     * internal use
     *
     * @param lineToSearch
     * @param offset
     */
    public void searchLineWithTotals(Object[] lineToSearch, int offset) {
        //System.err.print("searching for linet: ");
        //ArrayUtils.logArray(lineToSearch,System.err);
        boolean flag = true;
        boolean searchForTotal = false;
        int lin = 0;
        int colDest = 0;
        
        String templateValue ;
        String valueToSearch ;
        while (flag) {
            
            assert lin+offset < lineToSearch.length : "1. locator bad situation : lin="+lin+" offset="+offset+" lineToSearch.length="+lineToSearch.length;
            valueToSearch = (String)lineToSearch[lin + offset];
            
            if (valueToSearch.equals(TOTAL)) {
                colDest += ((lin == 0) ? (template[lin].length - 1)
                        : (jump[lin - 1] - 1));
                searchForTotal = true;
            }
            
            assert lin < template.length : "2. locator bad situation : lin="+lin+" template.length="+template.length;
            assert colDest < template[lin].length : "3. locator bad situation : colDest="+colDest+" template["+lin+"].length = "+template[lin].length;
            templateValue = (String)template[lin][colDest];

            if (templateValue.equals(valueToSearch)) {
                if ((lin == lastLineToCompare) || searchForTotal) {
                    copyData(colDest, lineToSearch, offset);
                    flag = false;
                } else {
                    lin++;
                }
            } else {
                colDest += jump[lin];
            }
        }
    }

    /**
     * internal use
     *
     * @param column
     * @param sourceLine
     * @param offset
     */
    private void copyData(int column, Object[] sourceLine, int offset) {
        for (int i = 0; i < resultRowsCount; i++) {
            //System.out.println("copy from source "+(lastLineToCompare + i + 1 + offset)+" to result ["+i+"]["+column+"]");
            result[i][column] = sourceLine[lastLineToCompare + i + 1 + offset];
        }
    }

    /**
     * constructs a zero filled matrix
     *
     * @param lineCount the row count of the matrix
     * @param columnCount the column count of the matrix
     */
    private Object[][] constructZeroFilledMatrix(int lineCount, int columnCount) {
        Object[][] m = new Object[lineCount][columnCount];

        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                m[i][j] = "0";
            }
        }

        return m;
    }

}
