package bdd.modeles;

/**
 * Le modèle du caddie.
 */
public class Caddie {

	/**
	 * L'id de l'entrée du caddie.
	 */
	private int id;

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
	 * Le numéro de zone.
	 */
	private int zone;

	/**
	 * Le numéro de la zone.
	 */
	private String nomZ;

	/**
	 * La quantité de place pour cette représentation.
	 */
	private int qt;

	/**
	 * Constructeur.
	 */
	public Caddie (int id, String n, String d, int nS, int zone, String nomZ, int qt) {
		this.id=id;
		this.nom = n;
		this.date = d;
		this.numS = nS;
		this.zone=zone;
		this.qt=qt;
		this.nomZ=nomZ;
	}

	/**
	 * Getter.
	 */
	public String getNomZ() {
		return nomZ;
	}

	/**
	 * Setter.
	 */
	public void setNomZ(String nomZ) {
		this.nomZ = nomZ;
	}

	/**
	 * Getter.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter.
	 */
	public void setId(int id) {
		this.id = id;
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

	/**
	 * Getter.
	 */
	public int getQt() {
		return qt;
	}

	/**
	 * Setter.
	 */
	public void setQt(int qt) {
		this.qt = qt;
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
