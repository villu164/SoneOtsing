import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Test {
	public static void main(String[] args){
		Loe.tyhjenda();
		boolean uuesti = true;
		Paiguta p = null;		
		String[] soned =null;
		while(uuesti) {
			try {
				if (args.length == 0) {
					System.out.println("Tere, olete tulnud ristsõna maailma. Kasutage sõnadega faili andes selle esimeseks argumendiks");
					soned = Loe.riigid();
					p = new Paiguta(Loe.kysiNumber("Sisesta ridu:"),Loe.kysiNumber("Sisesta veerge:"));
					//p = new Paiguta(4,4);
					//p.pane("Maailm", new Point(0,4), Kompass.valueOf(Kompass.suunad[4]).toPoint());
				}
				else soned = Loe.failist(args[0]);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(soned));
			Collections.shuffle(arrayList);
			String[] segatudSoned = arrayList.toArray(new String[arrayList.size()]);
			String[] sisestaSoned = segatudSoned;
			Maatriks m = p.getMaatriks();
			for (int s = 0;s < sisestaSoned.length;s++){
				for(int i = 0;i < m.getRidu();i++){
					for(int j=0;j< m.getVeerge();j++){
						Point punkt = new Point(j,i);
						for(int k = 0;k<Kompass.suunad.length;k++){
							Kompass suund = Kompass.valueOf(Kompass.suunad[k]);
							Lahend lahend = new Lahend(sisestaSoned[s].toUpperCase(), punkt, suund);
							boolean staatus = p.pane(lahend);
							//p.show(sisestaSoned[s].toUpperCase(), punkt, suund.toPoint());
							if (staatus) {
								//Debug.out(sisestaSoned[s]);
								//Debug.out(p.getMaatriks()); //comment in for debugging
								i=m.getRidu();
								j=m.getVeerge();
								k=8;
							}
						}
					}
				}
			}
			while(true){
				if (p.leiaSoned()) {
					System.out.println(Kompass.kompass);
					System.out.println();
					System.out.println(p);
					Lahend lahend = Loe.kysiLahend("Sisesta lahend kujule (RIDA,VEERG,SUUND,PIKKUS): ");
					Loe.tyhjenda();
					p.vota(lahend);
					String tulemus = "aga kahjuks ei leidu sellist";
					if (p.leidub(lahend)) tulemus = "ja sa ei eksi";
					System.out.println("Pakkusid sone: '" + lahend.getSone() + "' " + tulemus);
				}
				else{
					Loe.tyhjenda();
					boolean kysimus = Loe.kysiJahEi("Sinu võit. Kas mängime veel? Jah või Ei: ");
					if (!kysimus) {
						Abi.lahkumistekst();
						System.exit(0);
					}
					else {
						Loe.tyhjenda();
						break;
					}
				}
			}
		}
	}
}
