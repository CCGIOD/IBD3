package bdd.modeles;

/**
 * Le modèle d'un spectacle.
 */
public class Spectacle {

	/**
	 * Le numéro du spectacle.
	 */
	private int num ;

	/**
	 * Le nom du spectacle.
	 */
	private String nom;

	/**
	 * Constructeur.
	 */
	public Spectacle(int num, String nom) {
		this.setNum(num) ;
		this.setNom(nom) ;
	}

	/**
	 * Getter.
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Setter.
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * Getter.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
}
