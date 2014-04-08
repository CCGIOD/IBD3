package bdd.modeles;

/**
 * Modèle d'un ticket.
 */
public class Ticket {

	/**
	 * Le numéro de dossier.
	 */
	private int numDossier;

	/**
	 * Le numéro de ticket.
	 */
	private int numTicket;

	/**
	 * Le numéro de Spectacle.
	 */
	private int numS;

	/**
	 * Le nom du spectacle.
	 */
	private String nomS;

	/**
	 * La date de représentation.
	 */
	private String dateRep;

	/**
	 * La date d'émission.
	 */
	private String dateEmission;

	/**
	 * Le numéro de la place.
	 */
	private int numPlace;

	/**
	 * Le rang de la place.
	 */
	private int rangPlace;

	/**
	 * La zone de la place.
	 */
	private int zonePlace;

	/**
	 * Le prix de la place.
	 */
	private int prix;

	/**
	 * Constructeur.
	 */
	public Ticket() {}

	/**
	 * Getter.
	 */
	public int getNumDossier() {
		return numDossier;
	}

	/**
	 * Setter.
	 */
	public void setNumDossier(int numDossier) {
		this.numDossier = numDossier;
	}

	/**
	 * Getter.
	 */
	public int getNumTicket() {
		return numTicket;
	}

	/**
	 * Setter.
	 */
	public void setNumTicket(int numTicket) {
		this.numTicket = numTicket;
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

	/**
	 * Getter.
	 */
	public String getNomS() {
		return nomS;
	}

	/**
	 * Setter.
	 */
	public void setNomS(String nomS) {
		this.nomS = nomS;
	}

	/**
	 * Getter.
	 */
	public String getDateRep() {
		return dateRep;
	}

	/**
	 * Setter.
	 */
	public void setDateRep(String dateRep) {
		this.dateRep = dateRep;
	}

	/**
	 * Getter.
	 */
	public String getDateEmission() {
		return dateEmission;
	}

	/**
	 * Setter.
	 */
	public void setDateEmission(String dateEmission) {
		this.dateEmission = dateEmission;
	}

	/**
	 * Getter.
	 */
	public int getNumPlace() {
		return numPlace;
	}

	/**
	 * Setter.
	 */
	public void setNumPlace(int numPlace) {
		this.numPlace = numPlace;
	}

	/**
	 * Getter.
	 */
	public int getRangPlace() {
		return rangPlace;
	}

	/**
	 * Setter.
	 */
	public void setRangPlace(int rangPlace) {
		this.rangPlace = rangPlace;
	}

	/**
	 * Getter.
	 */
	public int getZonePlace() {
		return zonePlace;
	}

	/**
	 * Setter.
	 */
	public void setZonePlace(int zonePlace) {
		this.zonePlace = zonePlace;
	}

	/**
	 * Getter.
	 */
	public int getPrix() {
		return prix;
	}

	/**
	 * Setter.
	 */
	public void setPrix(int prix) {
		this.prix = prix;
	}
}
