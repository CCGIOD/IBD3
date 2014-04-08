package bdd.exceptions;

/**
 * Exception en rapport avec la base de données.
 * Le message associé à l'exception est explicite.
 */
@SuppressWarnings("serial")
public class BDException extends Exception {

	/**
	 * Constructeur d'une erreur de BD.
	 * @param message
	 * 		: Le message d'erreur à transmettre.
	 */
	public BDException (String message) {
		super("Erreur : "+message+".");
	}

}
