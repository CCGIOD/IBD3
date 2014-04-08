package bdd.exceptions;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Exception en rapport avec la base de données.
 * Le message associé à l'exception est explicite.
 * Traite les erreurs de paramêtres utiliser dans une fonction.
 */
@SuppressWarnings("serial")
public class BDExceptionParamError extends Exception {

	/**
	 * La liste des param d'erreurs.
	 */
	ArrayList<Integer> params = new ArrayList<Integer>();

	/**
	 * Le message d'erreur à transmettre.
	 */
	String errors = "";

	/**
	 * Le constructeur.
	 */
	public BDExceptionParamError(){
		super();
	}

	/**
	 * Le constructeur de BDExceptionParamError.
	 * @param message
	 * 		: Le message d'erreur à transmettre.
	 */
	public BDExceptionParamError (String message) {
		super("Erreur : "+message+".");
	}

	/**
	 * Ajoute un paramettre à l'erreur.
	 * @param indiceParam
	 * 		: L'indice du param.
	 * @param error
	 * 		: Le message d'erreur à transmettre.
	 */
	public void addError(int indiceParam, String error){
		params.add(indiceParam);
		errors+=error+"<br>";
	}

	/**
	 * Get la liste des indices d'erreurs.
	 * @return
	 * 		La liste des indices d'erreurs.
	 */
	public Collection<Integer> getParamsError(){
		return (Collection<Integer>)params ;
	}

	/**
	 * Get le message d'erreur associé au params.
	 * @return
	 * 		Le message d'erreur associé.
	 */
	public String getMessageError(){
		return errors;
	}
}
