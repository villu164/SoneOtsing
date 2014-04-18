package application;
import java.awt.Point;
import java.util.Arrays;
public class Lahend {
	private String sone;
	private Point punkt;
	private static char tyhi = '.';
	private boolean leitud = false;
	public boolean isLeitud() {
		return leitud;
	}
	public void setLeitud(boolean leitud) {
		this.leitud = leitud;
	}
	public String getSone() {
		return sone;
	}
	public void setSone(String sone) {
		this.sone = sone;
	}
	public Point getPunkt() {
		return punkt;
	}
	public void setPunkt(Point punkt) {
		this.punkt = punkt;
	}
	public Kompass getSuund() {
		return suund;
	}
	public void setSuund(Kompass suund) {
		this.suund = suund;
	}
	private Kompass suund;
	public Lahend(String sone, Point punkt, Kompass suund) {
		this.sone = sone;
		this.punkt = punkt;
		this.suund = suund;
	}
	public Lahend(int pikkus, Point punkt, Kompass suund) {
		this.sone = tyhiSone(pikkus);
		this.punkt = punkt;
		this.suund = suund;
	}
	
	public static String tyhiSone(int pikkus){
		char[] c = new char[pikkus];
		Arrays.fill(c, tyhi);
		return new String(c);
	}
	public static String tyhiSone(int pikkus, char tyhi){
		char[] c = new char[pikkus];
		Arrays.fill(c, tyhi);
		return new String(c);
	}
		
	public static Lahend parseLahend(String s){
		String[] eraldi = s.split(";");
		if (eraldi.length == 1) eraldi = s.split(",");
		if (eraldi.length == 1) eraldi = s.split(" ");
		Point punkt = new Point(Integer.parseInt(eraldi[1])-1,Integer.parseInt(eraldi[0])-1);
		Kompass suund = Kompass.valueOf(eraldi[2].toUpperCase());
		String sone = "";
		if (eraldi.length == 5) sone = eraldi[4];
		else {
			sone = tyhiSone(Integer.parseInt(eraldi[3]));
		}
		Lahend lahend = new Lahend(sone,punkt,suund);
		return lahend;
	}
	public boolean oige(){
		return leitud;
	}
	public String toString(){
		return toString(Debug.isDebug());
	}
	public String toString(boolean avalda){
		if (avalda) return (punkt.y + 1) + ";" + (punkt.x + 1) + ";" + suund + ";" + sone.length() + ";" + sone;
		return sone;
	}
	
}
