package bdd.modeles;

/**
 * Le modèle d'une place.
 */
public class Place {

	/**
	 * Le numéro de la place.
	 */
	private int num;

	/**
	 * Le numéro du rang.
	 */
	private int rang;

	/**
	 * Le numéro de la zone.
	 */
	private int zone;

	/**
	 * Constructeur.
	 */
	public Place(int rang, int num, int zone) {
		this.setNum(num) ;
		this.setRang(rang) ;
		this.setZone(zone);
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
	public int getRang() {
		return rang;
	}

	/**
	 * Setter.
	 */
	public void setRang(int rang) {
		this.rang = rang;
	}

	/**
	 * Getter.
	 */
	public int getZone() {
		return zone;
	}

	/**
	 * Setter.
	 */
	public void setZone(int zone) {
		this.zone = zone;
	}
}
