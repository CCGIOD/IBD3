package bdd.modeles;

/**
 * Modèle d'une zone.
 */
public class Zone {

	/**
	 * Le numéro.
	 */
	private int num;

	/**
	 * Le nom de la zone.
	 */
	private String nomC;

	/**
	 * Constructeur.
	 */
	public Zone(int num, String nomC) {
		this.setNum(num) ;
		this.setNomC(nomC) ;
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
	public String getNomC() {
		return nomC;
	}

	/**
	 * Setter.
	 */
	public void setNomC(String nomC) {
		this.nomC = nomC;
	}
}
