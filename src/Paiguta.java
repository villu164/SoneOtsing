import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Paiguta {
	private Maatriks maatriks;
	private ArrayList<Lahend> lahendid2 = new ArrayList<Lahend>();
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
	
	public boolean leiaSoned(){
		boolean on_veel = false;
		for(Map.Entry<String, Lahend> lahend : lahendid.entrySet()){
		    if (!lahend.getValue().isLeitud()){
		    	System.out.print(lahend.getValue() + " ");
		    	on_veel = true;
		    }
		}
		System.out.println();
		return on_veel;
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

	public String toString(){
		return maatriks.toString();
	}
}
