import java.awt.Point;

public enum Kompass {
	N(0), NE(1), E(2), SE(3), S(4), SW(5), W(6), NW(7), SUVALINE(-1);
/*
 * Kompass.valueOf(Kompass.suunad[1]) //et saada dynaamilist sisu
		Kompass
				     N
				     *
				   NW*NE
				W*********E
				   SW*SE
				     *
				     S 
     
 */
	private int ilmakaar;
	//me kasutame seda vektorit, et kavalamalt sonesid paigutada
	public static String kompass = "     N\n     *\n   NW*NE\nW*********E\n   SW*SE\n     *\n     S";
	public static int [][] vektor = {
			{-1,0},
			{-1,1},
			{0,1},
			{1,1},
			{1,0},
			{1,-1},
			{0,-1},
			{-1,-1}
	};
	private String[] suuna_teade = {"P6hi", "Kirre", "Ida", "Kagu", "L6una", "Edel", "L22s", "Loe"};
	public static String[] suunad = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
	private Kompass(int suund) {
		if (suund == -1) suund = (int)Math.round(Math.random()*7);
		this.ilmakaar = suund % 8;
	}
	int[] toVektor(){
		return vektor[ilmakaar % 8];
	}
	public Point toPoint(){
		return new Point(vektor[ilmakaar % 8][1],vektor[ilmakaar % 8][0]);
	}
	public String toString(){
		return suunad[ilmakaar];
	}
}