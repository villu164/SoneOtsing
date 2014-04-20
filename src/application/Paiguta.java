package application;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Paiguta {
	private Maatriks maatriks;
	private int min_pikkus = 3;

	Map<String, Lahend> lahendid = new HashMap<String, Lahend>();

	public Map<String, Lahend> getLahendid() {
		return lahendid;
	}
	
	public boolean leidub(Lahend lahend){
		boolean leidub = lahendid.containsKey(lahend.getSone());
		lahend.setLeitud(leidub);
		if (leidub) lahendid.get(lahend.getSone()).setLeitud(true);
		return leidub;
	}
	
	public String leiaSoned(){
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, Lahend> lahend : lahendid.entrySet()){
		    if (!lahend.getValue().isLeitud()){
		    	sb.append(lahend.getValue() + ", ");
		    }
		}
		return sb.toString();
	}

	public Paiguta(int ridu, int veerge) {
		this.maatriks = new Maatriks(ridu,veerge);
	}

	public Maatriks maatriks() {
		return maatriks;
	}
	
	public void lisaLahend(Lahend lahend){
		lahendid.put(lahend.getSone(),lahend);
	}
	public void lisaLahend(String sone, Point punkt, Kompass suund){
		Lahend lahend = new Lahend(sone,punkt,suund);
		lisaLahend(lahend);
	}
	
	public void show(String sone, Point punkt, Kompass suund){
		Paiguta p = new Paiguta(2*sone.length()-1,2*sone.length()-1);
		Lahend lahend = new Lahend(sone,new Point(sone.length()-1,sone.length()-1),suund);
		p.pane(lahend);
		Debug.out(p);
	}

	public boolean pane(Lahend lahend){

		if (lahend.getSone().length() < min_pikkus) return false;

		if (lahendid.containsKey(lahend.getSone().toUpperCase())) return false;
		Maatriks lisatud = (Maatriks) maatriks.clone();
		Point varupunkt = (Point) lahend.getPunkt().clone();
		Point suunapunkt = lahend.getSuund().toPoint();
		for(int i = 0;i<lahend.getSone().length();i++,varupunkt.translate(suunapunkt.x, suunapunkt.y)){
			boolean staatus = lisatud.setTabel(varupunkt, lahend.getSone().charAt(i));
			if (!staatus) return false;
		}
		maatriks = lisatud;
		lisaLahend(lahend);
		return true;
	}
	
	public void vota(Lahend lahend){
		Point varupunkt = (Point) lahend.getPunkt().clone();
		Point suunapunkt = lahend.getSuund().toPoint();
		char[] sone = lahend.getSone().toCharArray();
		for(int i = 0;i<sone.length;i++,varupunkt.translate(suunapunkt.x, suunapunkt.y)){
			sone[i] = maatriks.getTabel(varupunkt);
		}
		String sisu = new String(sone);
		lahend.setSone(sisu);
	}

	public Maatriks getMaatriks() {
		return maatriks;
	}

	public void setMaatriks(Maatriks maatriks) {
		this.maatriks = maatriks;
	}

	public void salvesta(){
		
	}
	public String toString(){
		return maatriks.toString();
	}
	
	//Klassi meetodid
	
	public static Paiguta riigid(){
		return riigid(10,16);
	}
	
	public static Paiguta riigid(int ridu, int veerge){
		String[] soned = Loe.anna_segatud_soned(Loe.riigid());
		Paiguta p = new Paiguta(ridu,veerge);
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
		
		return p;
	}
}
