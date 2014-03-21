package bdd.modeles;

public class Programme {

	private String nom;
	private String date;
	
	public Programme (String n, String d) {
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
	
	public void setPrix (String d) {
		this.date = d;
	}
}
