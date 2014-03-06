public class Suund {
	private int suund;
	private int[] vektor = {-1,0};
	
	public int getSuund() {
		return suund;
	}
	public int[] getVektor() {
		return vektor;
	}
	
	/*
	 Arrows
[←]  U+2190   &#8592;  LEFTWARDS ARROW
[↑]  U+2191   &#8593;  UPWARDS ARROW
[→]  U+2192   &#8594;  RIGHTWARDS ARROW
[↓]  U+2193   &#8595;  DOWNWARDS ARROW
[↔]  U+2194   &#8596;  LEFT RIGHT ARROW
[↕]  U+2195   &#8597;  UP DOWN ARROW
[↖]  U+2196   &#8598;  NORTH WEST ARROW
[↗]  U+2197   &#8599;  NORTH EAST ARROW
[↘]  U+2198   &#8600;  SOUTH EAST ARROW
[↙]  U+2199   &#8601;  SOUTH WEST ARROW
	 */

	private static final String[] suuna_teade = {"P6hi", "Kirre", "Ida", "Kagu", "L6una", "Edel", "L22s", "Loe"};
	private static final String[] nooled = {"↑", "↗", "→", "↘", "↓", "↙", "←", "↖"};
	private static final String[] jooned = {"|", "/", "-", "\\", "|", "/", "-", "\\"};
	private final int[] Nvektor = {-1,0};;
	private static final double nurk = Math.PI/4;
	
	
	private double vektori_pikkus(int[] vektor){
		return Math.sqrt(vektor[0]*vektor[0] + vektor[1]*vektor[1]);
	}
	private int teisenda(int[] vektor){
		double skalaar = Nvektor[0]*vektor[0] + Nvektor[0]*vektor[0];
		double Nnurk = Math.acos(skalaar / vektori_pikkus(vektor)*vektori_pikkus(Nvektor));
		return (int)Math.round(Nnurk/nurk);
	}
	private int[] keera(int[] alg_vektor, double alpha){
		int[] lopp_vektor = new int[2];
		double x = alg_vektor[0];
		double y = alg_vektor[1];
		lopp_vektor[0] = (int)Math.round(x*Math.cos(alpha) + y*Math.sin(alpha));
		lopp_vektor[1] = (int)Math.round(x*Math.sin(alpha) + y*Math.cos(alpha));
		return lopp_vektor;
	}
	
	Suund(int suund) {
		this.suund = suund;
		this.vektor = keera(this.vektor,-nurk*suund);
	}
	Suund(Kompass kompass) {
		this.suund = kompass.suund();
		this.vektor = keera(this.vektor,-nurk*suund);
	}
	
	public String toString(){
		return suuna_teade[suund];
	}
	public String toString(boolean vektoriga){
		if (vektoriga) return toString() + " Suunavektor=(" + this.vektor[0] + ";" + this.vektor[1] + ")";
		return toString();
	}
	
}