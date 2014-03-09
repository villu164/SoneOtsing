import java.awt.Point;

public class Test {
	public static void main(String[] args){
		System.out.println(new Maatriks(3,5));
		Paiguta p = new Paiguta(10,10);
		Point punkt = new Point(1,5);
		for(int i = 0;i<8;i++){
			Suund s = new Suund(i);
			//System.out.println(s.toString(true));
			p.pane("tere", punkt, s);
		}
	}
}
