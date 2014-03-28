package bdd.modeles;

public class Caddie {

	private int id;
	private String nom;
	private String date;
	private int numS;
	private int zone;
	private String nomZ;
	private int qt;
	
	public Caddie (int id, String n, String d, int nS, int zone, String nomZ, int qt) {
		this.id=id;
		this.nom = n;
		this.date = d;
		this.numS = nS;
		this.zone=zone;
		this.qt=qt;
		this.nomZ=nomZ;
	}

	public String getNomZ() {
		return nomZ;
	}

	public void setNomZ(String nomZ) {
		this.nomZ = nomZ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public int getQt() {
		return qt;
	}

	public void setQt(int qt) {
		this.qt = qt;
	}

	public String getNom () {
		return this.nom;
	}
	
	public String getDate () {
		return this.date;
	}
	
	public void setNom (String n) {
		this.nom = n;
	}
	
	public void setDate (String d) {
		this.date = d;
	}

	public int getNumS() {
		return numS;
	}

	public void setNumS(int numS) {
		this.numS = numS;
	}
}
