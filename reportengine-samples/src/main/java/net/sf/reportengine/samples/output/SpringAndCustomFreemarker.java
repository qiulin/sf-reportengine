/**
 * 
 */
package net.sf.reportengine.samples.output;

import net.sf.reportengine.FlatReport;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author balan
 *
 */
public class SpringAndCustomFreemarker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//load spring context from src/main/resources/application-context.xml
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-config-custom-freemarker-template.xml");
		FlatReport report = (FlatReport)context.getBean("customFreemarkerReport");
		report.execute();
	}
}
