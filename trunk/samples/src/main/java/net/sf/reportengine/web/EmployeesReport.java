/**
 * 
 */
package net.sf.reportengine.web;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletResponse;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.ExcelOutput;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author balan
 *
 */
@Controller
public class EmployeesReport {
	
	
	/**
	 * Selects the home page and populates the model with a message
	 */
//	@RequestMapping(value = "/report", method = RequestMethod.GET)
//	@ResponseBody
//	public String createReport(Model model) {
//		FlatReport report = new FlatReport(); 
//		report.setIn(new TextInput(new StringReader("john,doe,worker,1000")));
//		TxtOutput output = new TxtOutput(); 
//		report.setOut(new TxtOutput());
//		
//		report.addDataColumn(new DefaultDataColumn(0));
//		report.addDataColumn(new DefaultDataColumn(1));
//		report.addDataColumn(new DefaultDataColumn(2));
//		report.addDataColumn(new DefaultDataColumn(3));
//		
//		report.execute(); 
//		return output.getOutputWriter().toString();
//	}
	
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public void getReport(HttpServletResponse response) {
	    try {
	      
	    	FlatReport report = new FlatReport(); 
			report.setIn(new TextInput(new StringReader("john,doe,worker,1000")));
			ExcelOutput output = new ExcelOutput(response.getOutputStream()); 
			report.setOut(output);
			
			report.addDataColumn(new DefaultDataColumn(0));
			report.addDataColumn(new DefaultDataColumn(1));
			report.addDataColumn(new DefaultDataColumn(2));
			report.addDataColumn(new DefaultDataColumn(3));
			
			report.execute(); 
	    	
			response.flushBuffer();
	    } catch (IOException ex) {
	      throw new RuntimeException("IOError writing file to output stream");
	    }
	}
}
