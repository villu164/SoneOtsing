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
	private boolean pluss_yks(String sone, Point punkt, Suund suund){
		Maatriks lisatud = new Maatriks(maatriks);
		Point vektor = suund.getVektor();
		for(int i = 0;i<sone.length();i++){
			int tabelX = punkt.x + vektor.x*i;
			int tabelY = punkt.y + vektor.y*i;
			boolean staatus = lisatud.setTabel(tabelX, tabelY, sone.charAt(i));
			if (!staatus) return false;
		}
		maatriks = lisatud;
		return true;
	}

	public Maatriks getMaatriks() {
		return maatriks;
	}

	public void setMaatriks(Maatriks maatriks) {
		this.maatriks = maatriks;
	}

	public boolean pane(String sone, Point punkt, Suund suund){
		return kasMahub(sone, punkt, suund) && pluss_yks(sone, punkt, suund);
	}
	public String toString(){
		return maatriks.toString();
	}
}
