package orders;

/**
 * @author Julie Sampson
 * 
 */
public class ZipCodeRangeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ZipCodeRangeException() {

	}

	/**
	 * @param arg0
	 */
	public ZipCodeRangeException(String arg0) {
		super(arg0);

	}

	/**
	 * @param arg0
	 */
	public ZipCodeRangeException(Throwable arg0) {
		super(arg0);

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ZipCodeRangeException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ZipCodeRangeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);

	}

}
