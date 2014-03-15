import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class Kirjuta {
	public static String vaikefail = "log.txt";
	public static boolean faili(String failinimi, String sisu){
		File fail = new java.io.File(failinimi); 
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(fail,true)); //append
			pw.println(sisu);
			pw.close();
			return true;
		} catch (Exception e) {Debug.out(e.toString());}
		return false;
	}
	public static boolean faili(String sisu){
		return faili(vaikefail, sisu);
	}
	public static void teade(Object s){
		faili(s.toString());
		System.out.println(s);
	}
}
