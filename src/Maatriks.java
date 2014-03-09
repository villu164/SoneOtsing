import java.awt.Point;


public class Maatriks {
	private char[][] tabel;
	private int ridu;
	private int veerge;
	private char vahe = ' ';
	private char tyhi = '.';
	
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
	
	public String toString(){
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

	public int getRidu() {
		return ridu;
	}

	public int getVeerge() {
		return veerge;
	}
	public char[][] getTabel(){
		return tabel;
	}
	public void setTabel(int x, int y, char c){
		tabel[x][y] = c;
	}
	
}
