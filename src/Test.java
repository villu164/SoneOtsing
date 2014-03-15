import java.awt.Point;

public class Test {
	
	public static void main(String[] args){
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
