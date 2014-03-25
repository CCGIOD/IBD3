package bdd.modeles;

public class Spectacle {

	private int num ;
	private String nom;
	
	public Spectacle(int num, String nom) {
		this.setNum(num) ;
		this.setNom(nom) ;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
