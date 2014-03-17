import java.awt.Point;

public class Otsi {

	public static void main(String[] args){
		Loe.tyhjenda();
		Abi.tervitustekst();
		boolean uuesti = true;
		Paiguta p = null;		
		String[] soned = Loe.anna_segatud_soned(args);
		while(uuesti) {
			boolean kysimus_kohanda = Loe.kysiJahEi("Kas soovid täpsustada ridu ja veerge(vaikimisi 10x10)? Jah või Ei(vaikimisi): ",false);
			if (kysimus_kohanda) p = new Paiguta(Loe.kysiNumberSuuremKuiNull("Sisesta ridu:"),Loe.kysiNumberSuuremKuiNull("Sisesta veerge:"));
			else p = new Paiguta(10,10);
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
					System.out.println("Paremale - E, vasakule - W, üles - N, alla - S, diagonaal paremale alla - SE, diagonaal vasakule alla -SW,"
							+ " diagonaal paremale üles - NE, diagonaal vasakule üles -NW");
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
					boolean kysimus = Loe.kysiJahEi("Kas mängime veel? Jah(vaikimisi) või Ei: ");
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
