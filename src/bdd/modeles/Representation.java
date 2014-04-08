package bdd.modeles;

/**
 * Le modèle d'une représentation.
 */
public class Representation {

	/**
	 * Le nom du spectacle.
	 */
	private String nom;

	/**
	 * La date de la représentation.
	 */
	private String date;

	/**
	 * Le numéro du spectacle.
	 */
	private int numS;

	/**
	 * Constructeur.
	 */
	public Representation (String n, String d, int nS) {
		this.nom = n;
		this.date = d;
		this.numS = nS;
	}

	/**
	 * Getter.
	 */
	public String getNom () {
		return this.nom;
	}

	/**
	 * Getter.
	 */
	public String getDate () {
		return this.date;
	}

	/**
	 * Setter.
	 */
	public void setNom (String n) {
		this.nom = n;
	}

	/**
	 * Setter.
	 */
	public void setDate (String d) {
		this.date = d;
	}

	/**
	 * Getter.
	 */
	public int getNumS() {
		return numS;
	}

	/**
	 * Setter.
	 */
	public void setNumS(int numS) {
		this.numS = numS;
	}
}
