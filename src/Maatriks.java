
public class Maatriks {
	private char[][] tabel;
	private int ridu;
	private int veerge;
	private char vahe = ' ';
	private char tyhi = 'X';
	
	public Maatriks(int ridu, int veerge) {
		this.ridu = ridu;
		this.veerge = veerge;
		this.tabel = new char[ridu][veerge];
		tyhjenda_tabel();
	}
	
	public void tyhjenda_tabel(){
		for(int i = 0;i<tabel.length;i++)
			for(int j = 0;j<tabel[i].length;j++)
				tabel[i][j] = tyhi;
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
	
}
