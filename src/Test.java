import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Test {
	public static void main(String[] args){
		System.out.println(new Maatriks(3,5));
		Paiguta p = new Paiguta(40,40);
		//Point punkt = new Point(5,5);
		String[] soned = Loe.riigid();
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(soned));
		Collections.shuffle(arrayList);
		String[] segatudSoned = arrayList.toArray(new String[arrayList.size()]);
		String[] sisestaSoned = segatudSoned;
		Maatriks m = p.getMaatriks();
		for (int s = 0;s < sisestaSoned.length;s++){
			for(int i = 0;i < m.getRidu();i++){
				for(int j=0;j< m.getVeerge();j++){
					Point punkt = new Point(i,j);
					for(int k = 0;k<8;k++){
						Suund suund = new Suund(k);
						boolean staatus = p.pane(sisestaSoned[s].toUpperCase(), punkt, suund);
						if (staatus) {
							//System.out.println(p.getMaatriks()); //comment in for debugging
							i=m.getRidu();
							j=m.getVeerge();
							k=8;
						}
					}
				}
			}
		}
		System.out.println(p);

	}
}
