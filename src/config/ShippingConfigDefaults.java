package config;

/**
 * Global configuration constants with reasonable default values. 
 *
 * @author Julie Sampson
 *
 */
public final class ShippingConfigDefaults {
	
	/**
	 * Don't instantiate me.
	 */
	private ShippingConfigDefaults() {
		
	}

	public static final int MAX_NUMBER_OF_ZIP_CODES = 99999;
	public static final boolean ZIP_CODE_FLAGGED_VALUE = true;
	public static final boolean USE_9_DIGIT_ZIP = false;
	public static final int MINIMUM_BOUND_VALUE = 1;
	public static final int MAXIMUM_BOUND_VALUE = 99999;


}
