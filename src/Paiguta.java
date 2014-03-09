import java.awt.Point;


public class Paiguta {
	private Maatriks maatriks;

	public Paiguta(int ridu, int veerge) {
		this.maatriks = new Maatriks(ridu,veerge);
	}

	public Maatriks maatriks() {
		return maatriks;
	}
	private boolean kasMahub(String sone, Point punkt, Suund suund){
		Point vektor = suund.getVektor();
		int otsX = punkt.x + vektor.x*sone.length();
		int otsY = punkt.y + vektor.y*sone.length();
		Point ots = new Point(otsX,otsY);
		return maatriks.sees(ots);
	}
	private Maatriks pluss_yks(String sone, Point punkt, Suund suund){
		Maatriks lisatud = new Maatriks(maatriks);
		Point vektor = suund.getVektor();
		for(int i = 0;i<sone.length();i++){
			lisatud.setTabel(punkt.x + vektor.x*i, punkt.y + vektor.y*i, sone.charAt(i));
		}
		return lisatud;
	}

	public void pane(String sone, Point punkt, Suund suund){
		System.out.println(sone + " " + (kasMahub(sone, punkt, suund) ? "Mahub" : "Ei mahu") + "");
		if (kasMahub(sone, punkt, suund)) System.out.println(pluss_yks(sone, punkt, suund));
		//kasMahub(sone, punkt, suund);
	}
}
