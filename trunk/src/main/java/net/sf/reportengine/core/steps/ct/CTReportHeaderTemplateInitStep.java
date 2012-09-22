/*
 * Created on 29.01.2005
 */
package net.sf.reportengine.core.steps.ct;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.Locator;


/**
 * <p>
 * 	Creates the template of the report header. 
 * (please keep in mind that the generated template is not ready to print because contains a lot of duplicated values)
 * <br>
 * Example : Here is an example of template  after execute method:
 * <br>
 *	1	1	1	1	1	1	2	2	2	2	2	2 <br> 
 *  11	11	11	12	12	12	21	21	21	22	22	22<br>
 *  a	b	c	a	b	c	a	b	c	a	b	c <br>
 * </p>
 * 
 * Another example (this time with totals) can be:
 * <br/>
 * Given the distinct values:	<br/> 
 * North, South, East, West		<br/> 
 * M,F							<br/> 
 * 20,50,80						<br/> 
 * 
 * The resulting header template will be: 
 * 
 * North	North	North	North	North	North	North	North	North	South ... 	Grand Total
 * 	M		M		M		M		F		F		F		F		Total	...			Space
 * 20		50		80		Total	20		50		80		Total	Space	...			Space
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 */
public class CTReportHeaderTemplateInitStep implements IAlgorithmInitStep{
    
    /**
     * this is the id for a a reference to String[][] representing 
     * the header template of the crossTab report
     */
    public static final String CONTEXT_KEY_CT_HEADER_TEMPLATE = "net.sf.reportengine.ctHeaderTemplate";
    
    /**
     * this coefficients represent the meta data for the report header
     */
    //public static final String CONTEXT_KEY_CT_COEFICIENTS = "net.sf.reportengine.ctCoeficients";
    
    /**
	 * the template for the report header (Warning : contains a lot of duplicates)
	 */
	//private String[][] headerTemplate = null;
	
	/**
	 * the distinct values to be processed
	 */
	//private String[][] distinctXValues = null;
	
	/**
	 * cross tab report coefficients
	 */
	//private CrossTabCoefficients coefficients = null;
    
	/**
	 *  constructor
	 */	
	public CTReportHeaderTemplateInitStep() {
		super();
		//empty implementation
	}
	
	
	/**
     * implementation of IAlgorithmInitStep.init() method
     */
	public void init(IAlgorithmContext context) {
        //String[][] distinctXValues = (String[][])context.get(DistinctValuesDetectorStep.CONTEXT_KEY_HEADER_DISTINCT_VALUES);
        
        Boolean headerHasTotals = (Boolean)context.get(CrossTabReport.CONTEXT_KEY_CROSSTAB_HEADER_HAS_TOTALS);
        
        //TODO: the following 6 rows should be optimized. Their only meaning is to compute the column count
        IDataColumn[] dataColumns = (IDataColumn[])context.get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
		IGroupingColumn[] groupColumns = (IGroupingColumn[])context.get(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS);
        int colCount = dataColumns.length;
		if(groupColumns != null){
			colCount += groupColumns.length; 
		}
        
        CtMetadata ctMetadata = (CtMetadata)context.get(CrossTabReport.CONTEXT_KEY_CROSSTAB_METADATA);
        //CrossTabCoefficients coefficients = new CrossTabCoefficients(distinctXValues, headerHasTotals);
        
        String[][] headerTemplate = createHeaderTemplate(ctMetadata, colCount,  headerHasTotals);

        //context.set(CONTEXT_KEY_CT_COEFICIENTS, coefficients);
		context.set(CONTEXT_KEY_CT_HEADER_TEMPLATE, headerTemplate);
	}
	
	/**
	 * writes (generates) a line in the template 
	 * @param lin  the line count 
	 */	
	protected String[] generateHeaderLine(int currentLineCnt, 
										boolean headerHasTotals, 
										int headerColsCnt,
										CtMetadata crossTabMetadata) {
		
		//String[] headerLine = new String[headerColsCnt];
		String[] headerLine = new String[headerColsCnt]; 
		int col = 0;
		int blockCount = 0;
		int colspan;
		
		//go through columns one by one
		while (col < headerColsCnt) {
			if (headerHasTotals && (blockCount > 0)) {
				
				//first compute the space locations 
				int[] spaceLocations = computeSpaceLocations(currentLineCnt, crossTabMetadata);
				
				//then fill the space locations into the headerTemplate
				for (int i = 0; i < spaceLocations.length; i++) {
					if ((blockCount % spaceLocations[i]) == 0) {
						//headerTemplate[lin][col++] = Locator.SPACE;
						headerLine[col++] = Locator.SPACE;
					}
				}
			}
			
			colspan = crossTabMetadata.getColspanForLevel(currentLineCnt);
			
			if (col + colspan <= headerColsCnt) {
				int offset = createChunk(	headerLine, /*this array will be modified*/
											currentLineCnt, 
											col, 
											headerHasTotals,
											crossTabMetadata
											);
				col += offset;
				blockCount++;
			}
		}
		return headerLine; 
	}

	/**
	 * writes a chunk of data and total to the header template 
	 * 
	 * @param headerLine
	 * @param curLine
	 * @param curColumn
	 * @param headerHasTotals
	 * 
	 * @return     the number of completed columns 
	 */
	protected int createChunk(String[] headerLine,
							int curLine, 
							int curColumn, 
							boolean headerHasTotals, 
							CtMetadata crossTabMetadata) {
		
		int colsWritten = 0;
		int colspan;
		int headerColumnCount = crossTabMetadata.getDistinctValuesCountForLevel(curLine);
		for (int col = 0; col < headerColumnCount; col++) {
			colspan = crossTabMetadata.getColspanForLevel(curLine);
			for (int i = 0; i < colspan; i++) {
				//headerTemplate[curLine][curColumn + colsWritten] = distinctHeaderValues[curLine][col];
				headerLine[curColumn+colsWritten] = (String)crossTabMetadata.getDistinctValuesForLevel(curLine).get(col);
				colsWritten++;
			}
		}

		if (headerHasTotals) {
			//we have to write the Total word
			//headerTemplate[curLine][curColumn + colsWritten] = Locator.TOTAL;
			headerLine[curColumn + colsWritten] = Locator.TOTAL;
			colsWritten ++  /* because we count also the total */;
		}
		
		return colsWritten;
	}

	/**
	 * generates a header template with spaces, totals, column names
	 * 
	 * @param distinctXvalues
	 * @param headerHasTotals
	 */
	protected String[][] createHeaderTemplate(CtMetadata crossTabMetaData, 
											int colCount, 
											boolean headerHasTotals) {
		int lineCount = crossTabMetaData.getHeaderRowsCount(); 
		//int colCount = crossTabMetaData.getTemplateColumnCount(); 
		
		String[][] headerTemplateResult = new String[lineCount][colCount];
		
		//generate each line
		for (int i = 0; i < lineCount; i++) {
			headerTemplateResult[i] = generateHeaderLine(i, 
														headerHasTotals, 
														colCount, 
														crossTabMetaData);
		}
		
		return headerTemplateResult;
	}

	/**
	 * generates the space location (as the index) for each line of the template ...
	 * 
	 * @param      	currentLineCount the current line of the template where the 
	 * @param 		the header metadata of the cross tab report
	 * @return     	the magic numbers (i.e. the places in the specified line where spaces will be stored)
	 */
	protected int[] computeSpaceLocations(int currentLineCount, CtMetadata crossTabMetadata) {
		if (currentLineCount == 0) {
			return new int[]{};
		}

		int[] result = new int[currentLineCount];
		result[0] = crossTabMetadata.getDistinctValuesCountForLevel(currentLineCount - 1);

		for (int i = 1; i < result.length; i++) {
			result[i] = result[i - 1] * crossTabMetadata.getDistinctValuesCountForLevel(currentLineCount - i - 1);
		}

		return result;
	}
	
    
}

