package bdd.modeles;

public class Place {

	private int num ;
	private int rang;
	private int zone;
	
	public Place(int rang, int num, int zone) {
		this.setNum(num) ;
		this.setRang(rang) ;
		this.setZone(zone);
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

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}
}
