package bdd.modeles;

public class Representation {

	private String nom;
	private String date;
	
	public Representation (String n, String d) {
		this.nom = n;
		this.date = d;
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
}
