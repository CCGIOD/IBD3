package bdd.modeles;

public class Zone {

	private int num ;
	private String nomC;
	
	public Zone(int num, String nomC) {
		this.setNum(num) ;
		this.setNomC(nomC) ;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNomC() {
		return nomC;
	}

	public void setNomC(String nomC) {
		this.nomC = nomC;
	}
}
