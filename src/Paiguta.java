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
		System.out.println(suund);
		System.out.println("otsX:" + otsX);
		System.out.println("otsY:" + otsY);
		return maatriks.sees(ots);
	}

	public void pane(String sone, Point punkt, Suund suund){
		System.out.println(sone + " " + (kasMahub(sone, punkt, suund) ? "Mahub" : "Ei mahu") + "");
		//kasMahub(sone, punkt, suund);
	}
}
