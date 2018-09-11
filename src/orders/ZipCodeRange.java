package orders;

import config.ShippingConfigDefaults;
import config.ShippingMessageConstants;
import java.text.MessageFormat;

/**
 * Interval that includes a contiguous range of 5 digit Zip Code (ZC) values including both end points. 
 * <br>It is a required component of an item's shipping address for most US locations. 
 * <p> The ZC was introduced in 1963 by the USPS to ensure items "zipped" to their delivery 
 * destinations:
 * <br> {@link <a href="https://en.wikipedia.org/wiki/ZIP_Code">WIKI Zip</a>}
 * <p>
 * Examples:
 * <br>[95603,95699] - OK
 * <br>[84112,84112] - OK
 * <br>[94122,94111] - INVALID (upper bound < lower bound)
 * 
 * @author Julie Sampson
 *
 */

public class ZipCodeRange  {

	private int lowerBound;
	private int upperBound;

	/**
	 * Constructor execution results in a default range that includes zip code 00001.
	 * <p>  
	 * After execution the lowerBound and upperBound are set to application minimum bound property
	 * <br>
	 * @see ShippingMessageConstants
	 * 
	 */
	public ZipCodeRange() {
		
		lowerBound = upperBound = ShippingConfigDefaults.MINIMUM_BOUND_VALUE;
	}
	
	/**
	 * Construct a ZipCodeRange object with valid lower and upper bounds.
	 * <p>
	 * Bounds validated on:
	 * <br>Comparative value with each other to each other: lower !> upper 
	 * <br>Value compared to range min/max: [MINIMUM_BOUND_VALUE <= (lower | upper) <= MINIMUM_BOUND_VALUE]
	 * 
	 * @param lowerBound  
	 * @param upperBound 
	 * @throws ZipCodeRangeException - invalid bounds settings
	 */
	public ZipCodeRange(int lower, int upper) throws ZipCodeRangeException {
		try {
			setBoundsWithValidation(lower, upper);
			
		} catch (ZipCodeRangeException zcex) {
			this.lowerBound = this.upperBound = ShippingConfigDefaults.MINIMUM_BOUND_VALUE;
			String message = zcex.getMessage() + MessageFormat.format(ShippingMessageConstants.ZC_RANGE_INIT_FAILURE,  String.valueOf(getLowerBound()), String.valueOf(getUpperBound()));
			throw new ZipCodeRangeException(message);
		}
		
	}

	/**
	 * Construct a ZipCodeRange object from the range input string.
	 * <br> 
	 * <p> Expected input format:
	 * <br>range == "98765,99766"
	 *
	 * @param range - a single range, with lower and upper bounds separated by a comma
	 * @throws ZipCodeRangeException 
	 */
	public ZipCodeRange (String range) throws ZipCodeRangeException {
		
		String [] zipCodes = range.split(",");
		
		if (zipCodes.length == 2) {
			try {
				setBoundsWithValidation(Integer.parseInt(zipCodes[0]), Integer.parseInt(zipCodes[1]));		
			} catch (NumberFormatException | ZipCodeRangeException nfe) {
				
				throw new ZipCodeRangeException(nfe.getMessage() + "\n" + MessageFormat.format(ShippingMessageConstants.ZC_RANGE_PARSE_ERROR, range));
			}
		}
	}

	/**
	 * Validate bounds before setting the fields. 
	 * If validation fails with the passed in bounds, then we set the range to have both upper and lower =  MIN_BOUND_VALUE
	 * <p>
	 * Conditions:
	 *<br> 	1) 0 < lower bound <= 99999
	 *<br> 	2) lower bound < = upper bound
	 *  
	 * @throws ZipCodeRangeException indicates failure to meet bound conditions 
	 * 
	 */
	public void setBoundsWithValidation(int lower, int upper) throws ZipCodeRangeException {
		
		int min = ShippingConfigDefaults.MINIMUM_BOUND_VALUE;
		int max = ShippingConfigDefaults.MAXIMUM_BOUND_VALUE;

		if ( lower  < min || max < lower ) {
			String msg = MessageFormat.format(ShippingMessageConstants.ZC_VALUE_INVALID_MSG, "lower bound", String.valueOf(lower));
			lowerBound = upperBound = min;
			throw new ZipCodeRangeException (msg);
		}
		else if ( upper  < min || max < upper ) {
			String msg = MessageFormat.format(ShippingMessageConstants.ZC_VALUE_INVALID_MSG, "upper bound", String.valueOf(upper));
			lowerBound = upperBound = min;
			throw new ZipCodeRangeException (msg);
		}
		else if ( lower > upper) {
			String msg = MessageFormat.format(ShippingMessageConstants.ZC_RANGE_BOUND_INVALID_MSG, String.valueOf(lower), String.valueOf(upper));
			lowerBound = upperBound = min;
			throw new ZipCodeRangeException (msg); 
		}
		else {
			lowerBound = lower;
			upperBound = upper;
		}		
	}
	
	/** 
	 * Construct a fixed format known string for this ZipCodeRange.
	 * <p>
	 * @return string with attribute names and values 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return MessageFormat.format(ShippingMessageConstants.ZC_RANGE_SELF_APPRAISAL, String.valueOf(lowerBound), String.valueOf(upperBound));
	}

	/**
	 * @return the lowerBound
	 */
	public int getLowerBound() {
		return this.lowerBound;
	}

	/**
	 * @return the upperBound
	 */
	public int getUpperBound() {
		return this.upperBound;
	}


}
