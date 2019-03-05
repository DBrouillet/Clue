package clueGame;

/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to generate an Exception
 * whenever the Config file has an incorrect format.
 *
 */

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
