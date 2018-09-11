/**
 * 
 */
package orders;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Julie Sampson
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	ShippingHelperTest.class, 
	ZipCodeBoundsTest.class })

public class TSRangeRunner {

}
