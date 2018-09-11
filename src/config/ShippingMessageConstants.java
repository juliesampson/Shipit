package config;

/**
 * String constants for creating application messages with a consistent format and shared content. 
 * <p>
 * Relies on a message formatting service to populate strings {@link java.text.MessageFormat MessageFormat}
 * <p>
 * @author Julie Sampson
 * @see <a href="http://www.eclipse.org/articles/Article-Internationalization/how2I18n.html">DIY - Eclipse Plugin i18n</a>  
 * 
 */
public final class ShippingMessageConstants {

	/**
	 * Don't instantiate me.
	 */
	private ShippingMessageConstants() {
		
	}

	public static final String ZC_VALUE_INVALID_MSG = "Invalid Zip Code value. Expected: value[1-99999]. Actual: value of {0}  = {1}.\n";                                          
	public static final String ZC_RANGE_BOUND_INVALID_MSG = "Invalid Zip Code Range bounds. lower = {0} and upper = {1}.\n";                                          
	public static final String ZC_RANGE_SIZE_INVALID_MSG = "Invalid Zip Code Range size. Expected: size[1-99999]. Actual: size = {0}\n."; 
	public static final String ZC_RANGE_OUTPUT_CONSOLE_FORMAT = "[{0},{1}]";
	public static final String ZC_RANGE_OUTPUT_FILE_FORMAT = "{0},{1}\n";
	public static final String ZC_RANGE_INIT_FAILURE = 
			"Initialization of ZipCodeRange Object failed with bound values: [{0},{1}]. Creating object with defaults.\n";
	public static final String ZC_RANGE_SELF_APPRAISAL = "ZipCodeRange Object with lowerBound = {0}, and upperBound = {1}";
	public static final String ZC_RANGE_PARSE_ERROR = "Unable to parse range from string and ZipCodeRange object couldn't be created: range = {0}";
}
