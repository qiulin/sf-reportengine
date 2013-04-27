package net.sf.reportengine.out;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class TestFreemarkerOutput {
	
	
	
	@Test
	public void testTemplateFilename() {
		FreemarkerOutput classUnderTest = new FreemarkerOutput("./target/ftlOutputFilenameConstructor.html"); 
		classUnderTest.open();
		
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.outputTitle(new TitleProps("title", 3));
		
		classUnderTest.startHeaderRow(new RowProps(0));
		classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,0").colspan(1).build());
		classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,1").colspan(1).build());
		classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,2").colspan(1).build());
		classUnderTest.endHeaderRow(); 
		
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,0").colspan(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,1").colspan(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,2").colspan(1).build());
		classUnderTest.endDataRow();
		
		classUnderTest.startDataRow(new RowProps(1));
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 3,0").colspan(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 3,1").colspan(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 3,2").colspan(1).build());
		classUnderTest.endDataRow();
		
		classUnderTest.startDataRow(new RowProps(2));
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 4,0").colspan(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 4,1").colspan(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder(" cell 4,2").colspan(1).build());
		classUnderTest.endDataRow();
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
	
	@Test
	public void testTemplateWriter() {
		try {
			FreemarkerOutput classUnderTest = new FreemarkerOutput(new FileWriter("./target/ftlOutputWriterConstructor.html"));
		
			classUnderTest.open();
			classUnderTest.startReport(new ReportProps());
			classUnderTest.outputTitle(new TitleProps("test title", 3));
			
			classUnderTest.startHeaderRow(new RowProps(0));
			classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,0").colspan(1).build());
			classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,1").colspan(1).build());
			classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,2").colspan(1).build());
			classUnderTest.endHeaderRow(); 
			
			classUnderTest.startDataRow(new RowProps(0));
			classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,0").colspan(1).build());
			classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,1").colspan(1).build());
			classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,2").colspan(1).build());
			classUnderTest.endDataRow();
			
			classUnderTest.endReport(); 
			classUnderTest.close(); 
		} catch (IOException e) {
			fail(e.getMessage()); 
		} 
	}
	
	@Test
	public void testTemplateNonDefaultConfiguration() {
		try {
			Configuration nonDefaultConfig = new Configuration(); 
			nonDefaultConfig.setObjectWrapper(new DefaultObjectWrapper()); 
			nonDefaultConfig.setTemplateLoader(new FileTemplateLoader(new File("./target/test-classes/freemarker")));
			
			FreemarkerOutput classUnderTest = new FreemarkerOutput(	new FileWriter("./target/ftlOutputCustomConfig.txt"), 
													nonDefaultConfig);
		
			classUnderTest.open();
			classUnderTest.startReport(new ReportProps()); 
			classUnderTest.outputTitle(new TitleProps("title test", 3));
			
			classUnderTest.startHeaderRow(new RowProps(0));
			classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,0").colspan(1).build());
			classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,1").colspan(1).build());
			classUnderTest.outputHeaderCell(new CellProps.Builder(" cell 1,2").colspan(1).build());
			classUnderTest.endHeaderRow(); 
			
			classUnderTest.startDataRow(new RowProps(0));
			classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,0").colspan(1).build());
			classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,1").colspan(1).build());
			classUnderTest.outputDataCell(new CellProps.Builder(" cell 2,2").colspan(1).build());
			classUnderTest.endDataRow();
			
			classUnderTest.endReport(); 
			classUnderTest.close(); 
		} catch (IOException e) {
			fail(e.getMessage()); 
		} 
	}
}
