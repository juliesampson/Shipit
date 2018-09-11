/**
 * 
 */
package orders;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import config.ShippingMessageConstants;

import org.junit.runners.MethodSorters;

/**
 * @author Julie Sampson
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class ShippingHelperTest {

	private ShippingHelper shelper;
	private String range1;
	private String range2;
	private String expected = "";
	
	@Parameters(name = "{index}ZCRangeHelper(input range1={0}) input range2={1} expected output= {2}")
	public static Collection<Object[]> data() {
	        return Arrays.asList(new Object[][] {     
	                 {"10000,10005", "10002,10009" , "10000,10009"}, {"20000,20005" , "19900,20001" , "19900,20005"},
	                 {"35000,36000", "35500,35505" , "35000,36000"}, {"40000,45000" , "39995,45005" , "39995,45005"}, 
	                 {"60001,60001", "60003,60005" , "[60001,60001][60003,60005]"}, {"70001,70002" , "70003,70005" , "70001,70005"}, 
	                 {"00001,00003", "00001,00003" , "00001,00003"}, {"99999,99999" , "99999,99999" , "99999,99999,"}
	           });
	 }

	
	public ShippingHelperTest (String range1, String range2, String expected) {
		
		this.range1 = range1;
		this.range2 = range2;
		this.expected = expected;
		shelper = new ShippingHelper();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		shelper.resetZipCodesInRangesToValue(false);
	}

	/**
	 * Test method for {@link orders.ShippingHelper#flagZipCodesFromRange(orders.ZipCodeRange)}.
	 */
	@Test
	public final void testASetFlagForZipCodesInRange() {
		try {
			ZipCodeRange zcr1 = new ZipCodeRange(range1);
			ZipCodeRange zcr2 = new ZipCodeRange(range2);
			shelper.setFlagForZipCodesInRange(zcr1);
			shelper.setFlagForZipCodesInRange(zcr2);
			
		} catch (ZipCodeRangeException zcre) {
			
		}
	}

	/**
	 * Test method for {@link orders.ShippingHelper#getReducedZipCodeRanges(boolean)}.
	 */
	@Test
	public final void testBGetReducedZipCodeRanges() {
		
		try {
			ArrayList<ZipCodeRange> zipCodeRanges = shelper.getReducedZipCodeRanges(true);
			for (ZipCodeRange zipCodeRange : zipCodeRanges ) {
				String range = (MessageFormat.format(ShippingMessageConstants.ZC_RANGE_OUTPUT_FILE_FORMAT,
						 		String.valueOf(zipCodeRange.getLowerBound()),
						 		String.valueOf(zipCodeRange.getUpperBound())));
				assertEquals(expected, range);

			}		
		} catch (ZipCodeRangeException zcre) {
			
		}
		
		
	}

}
