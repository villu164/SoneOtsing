public enum Kompass {
	N(0), NE(1), E(2), SE(3), S(4), SW(5), W(6), NW(7);
/*
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

	private String[] suuna_teade = {"P6hi", "Kirre", "Ida", "Kagu", "L6una", "Edel", "L22s", "Loe"};
	private Kompass(int suund) {
		this.ilmakaar = suund;
	}
	int suund(){
		return ilmakaar;
	}
}