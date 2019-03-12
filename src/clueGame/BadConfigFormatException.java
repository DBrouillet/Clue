package clueGame;

/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson 
 * Class used to generate an Exception
 * whenever the Config file has an incorrect format.
 *
 */

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Unable to read configuration file.");
	}

	public BadConfigFormatException(String message) {
		super(message);
	}
	
}
