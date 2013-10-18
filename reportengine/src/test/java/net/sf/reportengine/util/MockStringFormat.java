package net.sf.reportengine.util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class MockStringFormat extends Format {

	@Override
	public StringBuffer format(	Object obj, 
								StringBuffer toAppendTo,
								FieldPosition pos) {
		return new StringBuffer("formatted ").append(obj);
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		throw new UnsupportedOperationException("parse object is not supported for MockStringFormat"); 
	}

}
