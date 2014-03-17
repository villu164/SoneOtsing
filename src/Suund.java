import java.awt.Point;

public class Suund {
	private int suund;
	private Point vektor = new Point(-1,0);
	
	public int getSuund() {
		return suund;
	}
	public Point getVektor() {
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
	private Point keera(Point alg_vektor, double alpha){
		Point lopp_vektor = new Point();
		double x = alg_vektor.x;
		double y = alg_vektor.y;
		lopp_vektor.x = (int)Math.round(x*Math.cos(alpha) + y*Math.sin(alpha));
		lopp_vektor.y = (int)Math.round(x*Math.sin(alpha) + y*Math.cos(alpha));
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
		if (vektoriga) return toString() + " Suunavektor=(" + this.vektor.x + ";" + this.vektor.y + ")";
		return toString();
	}
	
}