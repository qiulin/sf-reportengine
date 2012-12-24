package net.sf.reportengine.out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;
import net.sf.reportengine.core.ReportContent;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class TestFreemarkerOutput extends TestCase {
	
	private FreemarkerOutput classUnderTest;
	
	protected void setUp() throws Exception {
		super.setUp();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testTemplateFilename() {
		classUnderTest = new FreemarkerOutput("target/ftlOutputFilenameConstructor.html"); 
		classUnderTest.open();
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE, 0)); 
		classUnderTest.output(new CellProps.Builder("test value").colspan(2).build());
		classUnderTest.output(new CellProps.Builder(" span 1").colspan(1).build()); 
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS, 1));
		classUnderTest.output(new CellProps.Builder(" cell 1,0").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 1,1").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 1,2").colspan(1).build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA, 2));
		classUnderTest.output(new CellProps.Builder(" cell 2,0").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 2,1").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 2,2").colspan(1).build());
		classUnderTest.endRow();
		
		classUnderTest.close(); 
	}
	
	
	public void testTemplateWriter() {
		try {
			classUnderTest = new FreemarkerOutput(new FileWriter("target/ftlOutputWriterConstructor.html"));
		
			classUnderTest.open();
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE, 0)); 
			classUnderTest.output(new CellProps.Builder("test value").colspan(2).build());
			classUnderTest.output(new CellProps.Builder(" span 1").colspan(1).build()); 
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS, 1));
			classUnderTest.output(new CellProps.Builder(" cell 1,0").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 1,1").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 1,2").colspan(1).build());
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA, 2));
			classUnderTest.output(new CellProps.Builder(" cell 2,0").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 2,1").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 2,2").colspan(1).build());
			classUnderTest.endRow();
			
			classUnderTest.close(); 
		} catch (IOException e) {
			fail(e.getMessage()); 
		} 
	}
	
	public void testTemplateNonDefaultConfiguration() {
		try {
			Configuration nonDefaultConfig = new Configuration(); 
			nonDefaultConfig.setObjectWrapper(new DefaultObjectWrapper()); 
			nonDefaultConfig.setTemplateLoader(new FileTemplateLoader(new File("target/test-classes/freemarker")));
			
			classUnderTest = new FreemarkerOutput(	new FileWriter("target/ftlOutputCustomConfig.txt"), 
													nonDefaultConfig);
		
			classUnderTest.open();
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE, 0)); 
			classUnderTest.output(new CellProps.Builder("test value").colspan(2).build());
			classUnderTest.output(new CellProps.Builder(" span 1").colspan(1).build()); 
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS, 1));
			classUnderTest.output(new CellProps.Builder(" cell 1,0").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 1,1").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 1,2").colspan(1).build());
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA, 2));
			classUnderTest.output(new CellProps.Builder(" cell 2,0").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 2,1").colspan(1).build());
			classUnderTest.output(new CellProps.Builder(" cell 2,2").colspan(1).build());
			classUnderTest.endRow();
			
			classUnderTest.close(); 
		} catch (IOException e) {
			fail(e.getMessage()); 
		} 
	}
}
