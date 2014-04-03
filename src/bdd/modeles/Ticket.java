package bdd.modeles;

public class Ticket {
	private int numDossier ;
	private int numTicket ;
	private int numS ;
	private String nomS ;
	private String dateRep ;
	private String dateEmission ;
	
	// place
	private int numPlace ;
	private int rangPlace;
	private int zonePlace;
	
	private int prix;
	
	public Ticket() {
		// TODO Stub du constructeur généré automatiquement
	}
	
	public int getNumDossier() {
		return numDossier;
	}
	public void setNumDossier(int numDossier) {
		this.numDossier = numDossier;
	}
	public int getNumTicket() {
		return numTicket;
	}
	public void setNumTicket(int numTicket) {
		this.numTicket = numTicket;
	}
	public int getNumS() {
		return numS;
	}
	public void setNumS(int numS) {
		this.numS = numS;
	}
	public String getNomS() {
		return nomS;
	}
	public void setNomS(String nomS) {
		this.nomS = nomS;
	}
	public String getDateRep() {
		return dateRep;
	}
	public void setDateRep(String dateRep) {
		this.dateRep = dateRep;
	}
	public String getDateEmission() {
		return dateEmission;
	}
	public void setDateEmission(String dateEmission) {
		this.dateEmission = dateEmission;
	}
	public int getNumPlace() {
		return numPlace;
	}
	public void setNumPlace(int numPlace) {
		this.numPlace = numPlace;
	}
	public int getRangPlace() {
		return rangPlace;
	}
	public void setRangPlace(int rangPlace) {
		this.rangPlace = rangPlace;
	}
	public int getZonePlace() {
		return zonePlace;
	}
	public void setZonePlace(int zonePlace) {
		this.zonePlace = zonePlace;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
	
	
}
