package orders;

import java.util.ArrayList;
import java.util.Arrays;
import config.ShippingConfigDefaults;
/**
 * This is a supporting class for common tasks and data management that pertain to
 * order fulfillment. 
 * <p>
 * @author Julie Sampson
 *
 */
public class ShippingHelper {

	/** 
	 * The set of all possible zip code values for a 5 digit number.
	 * <br>The element at index[zip_code_value] reflects the restricted status of a zip code for an item.
	**/
	private boolean [] zipCodesInRanges;
	
	/**
	 * int maxNumberOfZipCodes is all the 5 digit positive integers or 99999. Declared final, do not change.
	 */
	private static final int maxNumberOfZipCodes = ShippingConfigDefaults.MAX_NUMBER_OF_ZIP_CODES;
	
	/**
	 * Use defaults {@link ShippingConfigDefaults} to create a linear boolean array[99999 + 1] which serves as
	 * a 1-1 map to the set of all 5 digit zip codes and a discarded index[0]. 
	 * 
	 * <br>The array element at [zip code] is a boolean flag indicating if the zip code is restricted for a given item.  
	 * An item can have multiple geographic restrictions originating from multiple sources.
	 */
	public ShippingHelper() {
		zipCodesInRanges = new boolean [maxNumberOfZipCodes + 1];
		resetZipCodesInRangesToValue(false);
	}
	
	/**
	 * Set the start state for all elements in zipCodesInRanges[].
	 * <br>By default we setup this boolean array [100000] with every element false (0)
	 * <p>
	 * @param startState - the base state for zipCodesInRanges[]
	 */
	public void resetZipCodesInRangesToValue(boolean startState) {
		Arrays.fill(zipCodesInRanges, startState);
	
	}
	
	/**
	 * Add all the zip codes in the range (lower -> upper bounds) to the restricted set
	 * @param zipCodeRange - a valid {@link ZipCodeRange} object with bounds representing a contiguous set of zip codes to flag.
	 * @throws ZipCodeRangeException if the size is not in [minimumBound, maximumBound]
	 *
	 */
	public void setFlagForZipCodesInRange(ZipCodeRange zipCodeRange) throws ZipCodeRangeException {
		int length = zipCodeRange.getUpperBound() - zipCodeRange.getLowerBound() + 1; 
		for (int i = 0; i < length; i++) {
			zipCodesInRanges[zipCodeRange.getLowerBound() + i] = ShippingConfigDefaults.ZIP_CODE_FLAGGED_VALUE;
		}		
	}
	
	/**
	 * Returns the minimal set of ranges representing the restricted (flagged) zip codes processed since reset.
	 * <p> 
	 * <br>Execute a linear traversal starting at position [1] 
	 * <br>Detect lower and upper bounds by checking for a state change between elements in the traversal
	 * <br>Construct a {@link ZipCodeRange} object from detected bounds
	 * <br>Add range to the return set
	 * <p>
	 * @param useFlagValue - the value (0|1) that defines which zip codes are included in the constructed ranges.
	 * By default, the ranges that were flagged during range processing are set to (1|true). 
	 * To extract the set of ranges that represent unrestricted zip codes set useFlagValue = false. 
	 * @return ZipCodeRange [] is the minimal set of ranges (interval notation) to represent all zip codes 
	 * with the same flag state = (true|false)
	 * <p> 
	 *  @see ShippingHelper#resetZipCodesInRangesToValue 
	 */
	public ArrayList<ZipCodeRange> getReducedZipCodeRanges (boolean useFlagValue) throws ZipCodeRangeException {
		
		int currentIndex = 1;
		int lowerBoundIndex = 0;
		int upperBoundIndex = zipCodesInRanges.length - 1;
		
		ArrayList <ZipCodeRange> reduced = new ArrayList<ZipCodeRange>(); 

		while (currentIndex < zipCodesInRanges.length) {
			if (zipCodesInRanges[currentIndex] == useFlagValue) {
				lowerBoundIndex = currentIndex;
				
				while (currentIndex < zipCodesInRanges.length) {
					if ( (zipCodesInRanges[currentIndex] != useFlagValue) || (currentIndex == zipCodesInRanges.length - 1)) {
						upperBoundIndex = currentIndex - 1;
						if (currentIndex == zipCodesInRanges.length - 1)
						{
							upperBoundIndex++;
						}
						ZipCodeRange range = new ZipCodeRange(lowerBoundIndex, upperBoundIndex);
						reduced.add(range);
						break;
					}
					currentIndex++;
				}
			}
			currentIndex++;
		}
		return reduced;
		
	}
	

}
