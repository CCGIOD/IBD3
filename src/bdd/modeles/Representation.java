package bdd.modeles;

public class Representation {

	private String nom;
	private String date;
	private int numS;
	
	public Representation (String n, String d, int nS) {
		this.nom = n;
		this.date = d;
		this.numS = nS;
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
