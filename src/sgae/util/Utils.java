package sgae.util;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class Utils {
	// From
	// https://codereview.stackexchange.com/questions/69827/isnullorwhitespace-check-for-java
	public static boolean isStringNullOrEmptyOrWhitespace(CharSequence value) {
		if (value == null) {
			return true;
		}

		if (value.toString().equals("")) {
			return true;
		}

		/*
		 * From
		 * https://stackoverflow.com/questions/3247067/how-do-i-check-that-a-
		 * java-string-is-not-all-whitespaces trim() and other mentioned regular
		 * expression do not work for all types of whitespaces i.e: Unicode
		 * Character 'LINE SEPARATOR'
		 * http://www.fileformat.info/info/unicode/char/2028/index.htm Java
		 * functions Character.isWhitespace() covers all situations.
		 */
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isWhitespace(value.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	public static String testStringNullOrEmptyOrWhitespaceAndSet(String value, String errorMsg) throws ParseException {
		if (isStringNullOrEmptyOrWhitespace(value)) {
			throw new ParseException(errorMsg, 0);
		}
		return value;
	}
}
