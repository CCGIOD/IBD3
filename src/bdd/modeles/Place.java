package bdd.modeles;

public class Place {

	private int num ;
	private int rang;
	
	public Place(int rang, int num) {
		this.setNum(num) ;
		this.setRang(rang) ;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}
}
