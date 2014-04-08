package bdd.exceptions;

/**
 * Exception en rapport avec la base de données.
 * Le message associé à l'exception est explicite.
 * Traite les erreurs d'état de la bd.
 */
@SuppressWarnings("serial")
public class BDExceptionIllegal extends BDException {

	/**
	 * Le constructeur d'une BDExceptionIllegal.
	 * @param message
	 * 		: Le message d'erreur à transmettre.
	 */
	public BDExceptionIllegal(String message) {
		super(message);
	}

}
