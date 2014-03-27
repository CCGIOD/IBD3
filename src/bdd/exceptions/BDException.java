package bdd.exceptions;

@SuppressWarnings("serial")
public class BDException extends Exception {
	
	public BDException (String message) {
		super("Erreur : "+message+".");
	}

}
