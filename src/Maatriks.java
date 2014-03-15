import java.awt.Point;


public class Maatriks {
	private char[][] tabel;
	private int ridu;
	private int veerge;
	private String vahe = "  ";
	private char tyhi = '.'; //see tyhi symbol tuleb ka dynaamiliseks teha
	
	public Maatriks(int ridu, int veerge) {		
		this.ridu = ridu;
		this.veerge = veerge;
		this.tabel = new char[ridu][veerge];
		tyhjenda_tabel();
	}
	
	public Maatriks(Maatriks m){
		this(m.getRidu(),m.getVeerge());
		for(int i = 0;i<tabel.length;i++)
			for(int j = 0;j<tabel[i].length;j++)
				tabel[i][j] = m.getTabel()[i][j];
	}
	
	public void tyhjenda_tabel(){
		for(int i = 0;i<tabel.length;i++)
			for(int j = 0;j<tabel[i].length;j++)
				tabel[i][j] = tyhi;
	}
	
	public boolean sees(Point p){
		return p.x >= 0 && p.y >= 0 && p.x < veerge && p.y < ridu;
	}

	public int getRidu() {
		return ridu;
	}

	public int getVeerge() {
		return veerge;
	}
	public char[][] getTabel(){
		return tabel;
	}
	public char getTabel(Point p){
		if (!sees(p)) return tyhi;
		return tabel[p.y][p.x];
	}
	public boolean setTabel(Point p, char c){
		//y,x, sest meil on rida,veerg, aga x=veerg
		if (!sees(p)) {
			return false; 
		}
		if (tabel[p.y][p.x] == c) return true;
		if (tabel[p.y][p.x] == tyhi) {
			tabel[p.y][p.x] = c;
			return true;
		}
		return false;
	}
	
	public Maatriks clone(){
		return new Maatriks(this);
	}
	public String toString2(){
		StringBuilder sb = new StringBuilder(ridu*veerge*2);
		for(char[] rida: tabel) {
			for(char karakter: rida){
				sb.append(karakter);
				sb.append(vahe);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public String toString(){
		StringBuilder sb = new StringBuilder(ridu*veerge*2);
		sb.append(" \t");
		for (int j = 0;j<tabel[0].length;j++) {
			sb.append(j+1);
			sb.append(vahe);
		}
		sb.append("\n");
		sb.append("\n");
		for (int i = 0;i<tabel.length;i++) {
			sb.append((i + 1) + "\t");
			for (int j = 0;j<tabel[i].length;j++) {
				sb.append(tabel[i][j]);
				sb.append(vahe);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
