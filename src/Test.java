import java.awt.Point;

public class Test {
	
	public static void main(String[] args){
<<<<<<< HEAD
		
		Paiguta p = new Paiguta(10,10);
		//Point punkt = new Point(5,5);
		//String[] soned = Loe.riigid();
		String[] soned =null;
		try {
			if (args.length == 0) {
				System.out.println("Vajad abi? Kirjuta help");
				System.out.println("Tere, olete tulnud ristsõna maailma. Kasutage sõnadega faili andes selle esimeseks argumendiks");
				soned = Loe.riigid();
				p = new Paiguta(40,40);
			}
			else {
				if (args[0].toLowerCase().equals("help")) {
					System.out.println("APPII!!!");
					System.exit(-1);
				}
				else soned = Loe.failist(args[0]);
			}
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
					Point punkt = new Point(i,j);
					for(int k = 0;k<8;k++){
						Suund suund = new Suund(k);
						boolean staatus = p.pane(sisestaSoned[s].toUpperCase(), punkt, suund);
						if (staatus) {
							//System.out.println(p.getMaatriks()); //comment in for debugging
							i=m.getRidu();
							j=m.getVeerge();
							k=8;
=======
		Loe.tyhjenda();
		Abi.tervitustekst();
		boolean uuesti = true;
		Paiguta p = null;		
		String[] soned = Loe.anna_segatud_soned(args);
		while(uuesti) {
			p = new Paiguta(Loe.kysiNumberSuuremKuiNull("Sisesta ridu:"),Loe.kysiNumberSuuremKuiNull("Sisesta veerge:"));
			Maatriks m = p.getMaatriks();
			for (int s = 0;s < soned.length;s++){
				for(int i = 0;i < m.getRidu();i++){
					for(int j=0;j< m.getVeerge();j++){
						Point punkt = new Point(j,i);
						for(int k = 0;k<Kompass.suunad.length;k++){
							Kompass suund = Kompass.valueOf(Kompass.suunad[k]);
							Lahend lahend = new Lahend(soned[s].toUpperCase(), punkt, suund);
							boolean staatus = p.pane(lahend);
							if (staatus) {
								i=m.getRidu();
								j=m.getVeerge();
								k=8;
							}
>>>>>>> FETCH_HEAD
						}
					}
				}
			}
			Kirjuta.teade(p);
			Kirjuta.teade(p.leiaSoned());
			Loe.tyhjenda();
			while(true){
				if (!p.leiaSoned().isEmpty()) {
					System.out.println(p.leiaSoned());
					System.out.println(Kompass.kompass);
					System.out.println();
					System.out.println(p);
					System.out.println("Näiteks 2 rida, 1 veerg, 5 tähte paremale on 2 1 E 5 ning samast kohast diagonaalselt paremale alla on 2 1 SE 5");
					Lahend lahend = Loe.kysiLahend("Sisesta lahend kujule (RIDA,VEERG,SUUND,PIKKUS): ");
					Loe.tyhjenda();
					p.vota(lahend);
					String tulemus = "aga kahjuks ei leidu sellist";
					if (p.leidub(lahend)) tulemus = "ja sa ei eksi";
					Kirjuta.teade("Pakkusid sone: '" + lahend.getSone() + "' " + tulemus);
				}
				else{
					Loe.tyhjenda();
					Kirjuta.teade("Sinu võit!");
					boolean kysimus = Loe.kysiJahEi("Kas mängime veel? Jah või Ei: ");
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
