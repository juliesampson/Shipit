/**
 * 
 */
package orders;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.CoreMatchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import config.ShippingConfigDefaults;

/**
 * @author Julie Sampson
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class ZipCodeBoundsTest {

	ZipCodeRange zipCodeRange;
	final int minBounds = ShippingConfigDefaults.MINIMUM_BOUND_VALUE;
	final int maxBounds = ShippingConfigDefaults.MAXIMUM_BOUND_VALUE;
	
	@Rule
	public ExpectedException expEx = ExpectedException.none() ;

	@Parameters(name = "{index}:ZipCodeRangeBounds(lower={0})upper={1}")
	public static Collection<Object[]> data() {
	        return Arrays.asList(new Object[][] {     
	                 {0, 500}, {90000, 100000}, {1000, 0}, {100000, 5000}, {10000, 5000}, 
	                 {-1,-1}, {100001, 100001}, {-1, 0}, {100002, 100003}  
	           });
	 }

	private int lowerBound;
	private int upperBound;
	
	public ZipCodeBoundsTest(int lower, int upper) {
		
		this.lowerBound = lower;
		this.upperBound = upper;
	}
	
	
	@Test 
	public void testZipCodeRangeIntInt() throws ZipCodeRangeException {
		
		expEx.expect(ZipCodeRangeException.class);
		expEx.expectMessage(CoreMatchers.containsString("Invalid"));
		zipCodeRange = new ZipCodeRange(lowerBound, upperBound);
	}
	
	/**
	 * Test method for {@link orders.ZipCodeRange#ZipCodeRange()}.
	 */
	@Test
	public final void testZipCodeRange() {
		
		zipCodeRange = new ZipCodeRange();
		assertEquals(minBounds, zipCodeRange.getLowerBound());
		assertEquals(minBounds, zipCodeRange.getUpperBound());
	}

	/**
	 * Test method for {@link orders.ZipCodeRange#SetBoundsWithValidation()}.
	 */
	@Test
	public void testSetBoundsWithValidation() throws ZipCodeRangeException {
		
		expEx.expect(ZipCodeRangeException.class);
		expEx.expectMessage(CoreMatchers.containsString("Invalid"));
		zipCodeRange = new ZipCodeRange(lowerBound, upperBound);
	}
	
	/**
	 * Test method for {@link orders.ZipCodeRange#toString()}.
	 */
	@Test
	public final void testToString() {
		
		try {
			zipCodeRange = new ZipCodeRange(77777, 88888);
		} 
		catch (ZipCodeRangeException ex) {
			ex.printStackTrace();
		}
		assertThat(zipCodeRange.toString(), CoreMatchers.containsString("77777"));
	}

	
}
