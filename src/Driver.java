
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Properties;

import config.ShippingMessageConstants;
import orders.ShippingHelper;
import orders.ZipCodeRange;
import orders.ZipCodeRangeException;


/**
 * Wrapper to exercise a portion of the fulfillment process in respect to an item's
 * geographic restrictions as defined by Zip Codes
 * <p>
 * inputFileName: input data in .csv file with one zip code range per line. The input file can be easily changed
 * by updating the file.input.ranges property in {@link driver.properties}
 * <p>
 * outputFileName: output file for the reduced set of ranges. Defined in same place as input file name with file.output.ranges
 * <p>
 * helper: (ShippingHelper) new class that tracks issues arising from addressing and shipping
 *
 * @author Julie Sampson
 *
 */
public class Driver {

	private ShippingHelper helper;
	private String inputFileName;
	private String outputFileName;
	
	/**
	 * Entry point to run zip code ranges through the reducer.
	 * @param args - not used
	 */
	public static void main(String[] args) {

		Driver driver = new Driver();
		driver.run();

	}
	
	/**
	 *
	 */
	public Driver() {
		super();
	}

	/**
	 * Generate the reduced set of zip code ranges from the {@link ShippingHelper} and save
	 * them to the outputFile. If outputFile file == null, write them to stdout.
	 *
	 * @param outputFile
	 * @throws ZipCodeRangeException
	 * @throws IOException
	 */
	public void generateOutputRanges(String outputFile) throws ZipCodeRangeException, IOException {

		ArrayList<ZipCodeRange> zipCodeRanges = helper.getReducedZipCodeRanges(true);

		BufferedWriter buffer = null;

		buffer = new BufferedWriter(new FileWriter(outputFile));
		for (ZipCodeRange zipCodeRange : zipCodeRanges ) {
			String range = (MessageFormat.format(ShippingMessageConstants.ZC_RANGE_OUTPUT_FILE_FORMAT,
												 String.valueOf(zipCodeRange.getLowerBound()),
												 String.valueOf(zipCodeRange.getUpperBound())));
				buffer.write(range);
		}
		buffer.close();

		for (ZipCodeRange zipCodeRange : zipCodeRanges ) {
			System.out.println(MessageFormat.format(ShippingMessageConstants.ZC_RANGE_OUTPUT_CONSOLE_FORMAT,
													String.valueOf(zipCodeRange.getLowerBound()),
													String.valueOf(zipCodeRange.getUpperBound())));
		}
	}

	/**
	 * Setup and initialize variables and load resources to run tests.
	 * <br>Helper initialized
	 * <br>Properties loaded from "driver.properties" file to initialize the input and output file names
	 * <br>driver.properties should be installed at same at the root of project same level, but outside src folder
	 * <p>
	 * @throws FileNotFoundException when the "driver.properties" file could not be loaded
	 * <p>
	 * TODO: consider providing default values for test data files using this call instead
	 * {@link Properties.getProperty(String key, String defaultValue)}
	 *
	*/
	private void init() throws FileNotFoundException {
		helper = new ShippingHelper();
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream("./driver.properties");
		try {
			props.load(fis);
			fis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		inputFileName = props.getProperty("file.input.ranges");
		outputFileName = props.getProperty("file.output.ranges");
	}


	/**
	 * Load the zip code ranges from a 2 column csv file.
	 * <br>Open file resource at fileName
	 * <br>Read in a line (one range) at a time and then parse it
	 *
	 * @param fileName
	 *
	 */
	public void loadInputRanges(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		BufferedReader buffer = null;
		try {
			if (file.exists()) {
				FileInputStream fis = new FileInputStream (file);
				buffer = new BufferedReader(new InputStreamReader(fis));
				String range = "";
				while ((range = buffer.readLine())!= null) {
					loadZipCodesFromRange(range);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		} finally {
			try {
				buffer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Extract and flag a set of zip codes represented in the input string range.
	 * <br> A {@link ZipCodeRange} object is created and passed to the {@link ShippingHelper} to manage
	 *
	 * <p> Expected input format:
	 * <br>range == "98765,99766"
	 *
	 * @param range - a single range, with lower and upper bounds separated by a comma
	 *
	 */
	public void loadZipCodesFromRange (String range) {
		String [] zipCodes = range.split(",");
		if (zipCodes.length == 2) {
			try {
				ZipCodeRange zipCodeRange = new ZipCodeRange(Integer.parseInt(zipCodes[0]), Integer.parseInt(zipCodes[1]));
				helper.setFlagForZipCodesInRange(zipCodeRange);
			} catch (NumberFormatException  ex) {
				ex.printStackTrace();
			} catch (ZipCodeRangeException ex) {
				System.out.println("Problem parsing the String range: " + range + "\n" + ex.getLocalizedMessage());
			}
		}
	}

	/**
	 * Execute the driver utility to parse the zip code ranges and trim that set
	 * to the fewest number of ranges.
	 * <p>
	 * Call to init() requires input and output file setup. Exit out of test here if we catch
	 * that exception
	 */
	public void run () {

		try {
			init();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Couldn't find the driver.properties file. " + ex.getMessage());
			System.out.println("Exiting...");
			System.exit(-1);
		}

		try {
			loadInputRanges(inputFileName);
		} catch (FileNotFoundException ex) {
			System.out.println("Couldn't read the input file: " + ex.getMessage());
			System.out.println("Exiting...");
			System.exit(-1);
		}

		try {
			generateOutputRanges(outputFileName);

		} catch (ZipCodeRangeException ex) {
			System.out.println("Problem creating the reduced set of ranges " + ex.getMessage());
			System.out.println("Exiting...");
			System.exit(-1);

		} catch (IOException ex) {
			System.out.println("Couldn't write to the output file: " + ex.getMessage());
			System.out.println("Exiting...");
			System.exit(-1);
		}

	}
}


