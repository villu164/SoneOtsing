
public class Punkt {
	public int x;
	public int y;
	
	public Punkt(int rea_indeks, int veeru_indeks) {
		this.x = rea_indeks;
		this.y = veeru_indeks;
	}
	
	public int getReaIndeks() {
		return x;
	}
	public void setReaIndeks(int rea_indeks) {
		this.x = rea_indeks;
	}
	public int getVeeruIndeks() {
		return y;
	}
	public void setVeeruIndeks(int veeru_indeks) {
		this.y = veeru_indeks;
	}
}
