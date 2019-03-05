package clueGame;

/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to generate an Exception
 * whenever the Config file has an incorrect format.
 *
 */

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Unable to read configuration file.");
	}

	public BadConfigFormatException(String arg0) {
		super(arg0);
	}
	
}
